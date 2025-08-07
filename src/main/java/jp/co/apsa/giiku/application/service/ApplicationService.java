/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.giiku.application.service;

import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jp.co.apsa.giiku.application.command.CreateApplicationCommand;
import jp.co.apsa.giiku.domain.entity.Application;
import jp.co.apsa.giiku.domain.entity.ApplicationApproval;
import jp.co.apsa.giiku.domain.entity.ApplicationApprovalRoute;
import jp.co.apsa.giiku.domain.entity.Department;
import jp.co.apsa.giiku.domain.entity.User;
import jp.co.apsa.giiku.domain.repository.ApplicationApprovalRepository;
import jp.co.apsa.giiku.domain.repository.ApplicationRepository;
import jp.co.apsa.giiku.domain.repository.ApplicationApprovalRouteRepository;
import jp.co.apsa.giiku.domain.repository.DepartmentRepository;
import jp.co.apsa.giiku.domain.repository.UserRepository;
import jp.co.apsa.giiku.domain.valueobject.ApplicationStatus;
import jp.co.apsa.giiku.domain.valueobject.ApprovalStatus;
import jp.co.apsa.giiku.domain.valueobject.ApprovalAction;
import jp.co.apsa.giiku.domain.port.AuditPort;
import jp.co.apsa.giiku.infrastructure.notification.SlackNotificationService;

/**
 * 申請アプリケーションサービス
 * 
 * <p>申請に関するビジネスロジックを処理するCQRS対応のアプリケーションサービスです。
 * Command Handlerパターンを実装し、申請作成から承認フロー初期化まで
 * 一連の処理を統合的に管理します。</p>
 * 
 * <p>このサービスは以下の責務を持ちます：</p>
 * <ul>
 *   <li>申請作成コマンドの処理</li>
 *   <li>ビジネスルールの検証と適用</li>
 *   <li>承認フローの初期化</li>
 *   <li>通知処理の実行</li>
 *   <li>監査ログの記録</li>
 *   <li>トランザクション境界の管理</li>
 * </ul>
 * 
 * <p>Hexagonal Architectureにおけるアプリケーション層のサービスとして、
 * ドメインロジックとインフラストラクチャ層を協調させます。</p>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Validated
@Transactional
public class ApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    private final ApplicationRepository applicationRepository;
    private final ApplicationApprovalRepository applicationApprovalRepository;
    private final ApplicationApprovalRouteRepository applicationApprovalRouteRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final SlackNotificationService slackNotificationService;
    private final AuditPort auditLogService;

    /**
     * コンストラクタ
     * 
     * @param applicationRepository 申請リポジトリ
     * @param applicationApprovalRepository 承認履歴リポジトリ
     * @param departmentRepository 部署リポジトリ
     * @param userRepository ユーザーリポジトリ
     * @param slackNotificationService Slack通知サービス
     * @param auditLogService 監査ログサービス
     */
    @Autowired
    public ApplicationService(
            ApplicationRepository applicationRepository,
            ApplicationApprovalRepository applicationApprovalRepository,
            ApplicationApprovalRouteRepository applicationApprovalRouteRepository,
            DepartmentRepository departmentRepository,
            UserRepository userRepository,
            SlackNotificationService slackNotificationService,
            AuditPort auditLogService) {
        this.applicationRepository = applicationRepository;
        this.applicationApprovalRepository = applicationApprovalRepository;
        this.applicationApprovalRouteRepository = applicationApprovalRouteRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.slackNotificationService = slackNotificationService;
        this.auditLogService = auditLogService;
    }

    /**
     * 申請作成コマンドを処理します
     * 
     * <p>申請作成の全体的なワークフローを管理し、以下の処理を順次実行します：</p>
     * <ol>
     *   <li>入力データの検証</li>
     *   <li>ビジネスルールの適用</li>
     *   <li>申請エンティティの作成</li>
     *   <li>承認フローの初期化</li>
     *   <li>通知の送信</li>
     *   <li>監査ログの記録</li>
     * </ol>
     * 
     * @param command 申請作成コマンド
     * @return 作成された申請のID
     * @throws IllegalArgumentException 入力データが不正な場合
     * @throws BusinessRuleViolationException ビジネスルール違反の場合
     * @throws ApplicationCreationException 申請作成に失敗した場合
     */
    @Transactional
    public Long createApplication(@Valid @NotNull CreateApplicationCommand command) {
        logger.info("申請作成処理を開始します。申請者ID: {}, タイトル: {}", 
                   command.getApplicantId(), command.getTitle());

        try {
            // 1. 入力データの検証
            validateCreateApplicationCommand(command);

            // 2. ビジネスルールの検証
            validateBusinessRules(command);

            // 3. 申請エンティティの作成
            Application application = createApplicationEntity(command);

            // 4. 申請の保存
            Application savedApplication = applicationRepository.save(application);
            logger.info("申請が正常に保存されました。申請ID: {}", savedApplication.getId());

            // 5. 承認フローの初期化
            initializeApprovalFlow(savedApplication);

            // 6. 通知の送信
            sendNotifications(savedApplication, command);

            // 7. 監査ログの記録
            recordAuditLog(savedApplication, command);

            logger.info("申請作成処理が正常に完了しました。申請ID: {}", savedApplication.getId());
            return savedApplication.getId();

        } catch (Exception e) {
            logger.error("申請作成処理中にエラーが発生しました。申請者ID: {}, エラー: {}", 
                        command.getApplicantId(), e.getMessage(), e);

            // 監査ログにエラーを記録
            auditLogService.recordError("APPLICATION_CREATION_FAILED", 
                                      command.getApplicantId(), 
                                      e.getMessage());

            throw new ApplicationCreationException("申請の作成に失敗しました", e);
        }
    }

    /**
     * 申請作成コマンドの入力検証を行います
     * 
     * @param command 申請作成コマンド
     * @throws IllegalArgumentException 入力データが不正な場合
     */
    private void validateCreateApplicationCommand(CreateApplicationCommand command) {
        logger.debug("申請作成コマンドの入力検証を開始します");

        // 申請者の存在確認
        Optional<User> applicant = userRepository.findById(command.getApplicantId());
        if (applicant.isEmpty()) {
            throw new IllegalArgumentException("指定された申請者が存在しません: " + command.getApplicantId());
        }

        // 承認者の存在確認
        Optional<User> approver = userRepository.findById(command.getApproverId());
        if (approver.isEmpty()) {
            throw new IllegalArgumentException("指定された承認者が存在しません: " + command.getApproverId());
        }

        // 部署の存在確認（部署IDが指定されている場合）
        if (command.getDepartmentId() != null) {
            Optional<Department> department = departmentRepository.findById(command.getDepartmentId());
            if (department.isEmpty()) {
                throw new IllegalArgumentException("指定された部署が存在しません: " + command.getDepartmentId());
            }
        }

        // 期限日の検証
        if (command.getDueDate() != null && command.getDueDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("期限日は現在日時より後である必要があります");
        }

        // 申請期間の検証
        if (command.hasPeriod()) {
            if (command.getEndDate().isBefore(command.getStartDate())) {
                throw new IllegalArgumentException("申請期間の終了日は開始日より後である必要があります");
            }
        }

        logger.debug("申請作成コマンドの入力検証が完了しました");
    }

    /**
     * ビジネスルールの検証を行います
     * 
     * @param command 申請作成コマンド
     * @throws BusinessRuleViolationException ビジネスルール違反の場合
     */
    private void validateBusinessRules(CreateApplicationCommand command) {
        logger.debug("ビジネスルールの検証を開始します");

        // 同一申請者による同一タイトルの申請の重複チェック
        boolean duplicateExists = applicationRepository.existsByApplicantIdAndTitle(
            command.getApplicantId(), command.getTitle());

        if (duplicateExists) {
            throw new BusinessRuleViolationException(
                "同一タイトルの申請が既に存在します: " + command.getTitle());
        }

        // 申請者と承認者が同一人物でないことの確認
        if (command.getApplicantId().equals(command.getApproverId())) {
            throw new BusinessRuleViolationException("申請者と承認者は異なる人物である必要があります");
        }

        // 緊急申請の場合の追加検証
        if (command.getIsUrgent()) {
            validateUrgentApplicationRules(command);
        }

        // 金額を伴う申請の場合の追加検証
        if (command.hasAmount()) {
            validateAmountApplicationRules(command);
        }

        logger.debug("ビジネスルールの検証が完了しました");
    }

    /**
     * 緊急申請のビジネスルールを検証します
     * 
     * @param command 申請作成コマンド
     * @throws BusinessRuleViolationException ビジネスルール違反の場合
     */
    private void validateUrgentApplicationRules(CreateApplicationCommand command) {
        // 緊急申請の場合は理由が必須
        if (command.getReason() == null || command.getReason().trim().isEmpty()) {
            throw new BusinessRuleViolationException("緊急申請の場合は申請理由が必須です");
        }

        // 緊急申請の場合は期限日が必須
        if (command.getDueDate() == null) {
            throw new BusinessRuleViolationException("緊急申請の場合は期限日が必須です");
        }

        // 緊急申請の期限は72時間以内
        LocalDateTime maxDueDate = LocalDateTime.now().plusHours(72);
        if (command.getDueDate().isAfter(maxDueDate)) {
            throw new BusinessRuleViolationException("緊急申請の期限は72時間以内である必要があります");
        }
    }

    /**
     * 金額を伴う申請のビジネスルールを検証します
     * 
     * @param command 申請作成コマンド
     * @throws BusinessRuleViolationException ビジネスルール違反の場合
     */
    private void validateAmountApplicationRules(CreateApplicationCommand command) {
        // 金額を伴う申請の場合は通貨コードが必須
        if (command.getCurrencyCode() == null || command.getCurrencyCode().trim().isEmpty()) {
            throw new BusinessRuleViolationException("金額を伴う申請の場合は通貨コードが必須です");
        }

        // 高額申請の場合の追加承認者設定チェック
        java.math.BigDecimal highAmountThreshold = new java.math.BigDecimal("1000000");
        if (command.getAmount().compareTo(highAmountThreshold) > 0) {
            // 高額申請の場合は部署IDが必須
            if (command.getDepartmentId() == null) {
                throw new BusinessRuleViolationException("高額申請の場合は部署の指定が必須です");
            }
        }
    }

    /**
     * 申請エンティティを作成します
     * 
     * @param command 申請作成コマンド
     * @return 作成された申請エンティティ
     */
    private Application createApplicationEntity(CreateApplicationCommand command) {
        logger.debug("申請エンティティの作成を開始します");

        Application.ApplicationBuilder builder = Application.builder()
            .applicationTypeId(command.getApplicationType().getId())
            .applicationType(command.getApplicationType())
            .applicantId(command.getApplicantId())
            .approverId(command.getApproverId())
            .title(command.getTitle())
            .content(command.getContent())
            .priority(command.getPriority().getCode())
            .status(ApplicationStatus.PENDING)
            .applicationDate(LocalDateTime.now())
            .isUrgent(command.getIsUrgent());

        // オプション項目の設定
        if (command.getDueDate() != null) {
            builder.dueDate(command.getDueDate());
        }

        if (command.getDepartmentId() != null) {
            builder.departmentId(command.getDepartmentId());
        }

        if (command.getReason() != null) {
            builder.reason(command.getReason());
        }

        if (command.hasAmount()) {
            builder.amount(command.getAmount())
                   .currencyCode(command.getCurrencyCode());
        }

        if (command.hasPeriod()) {
            builder.startDate(command.getStartDate())
                   .endDate(command.getEndDate());
        }

        if (command.getRemarks() != null) {
            builder.remarks(command.getRemarks());
        }

        Application application = builder.build();
        logger.debug("申請エンティティの作成が完了しました");

        return application;
    }

    /**
     * 承認フローを初期化します
     * 
     * @param application 作成された申請
     */
    private void initializeApprovalFlow(Application application) {
        logger.debug("承認フローの初期化を開始します。申請ID: {}", application.getId());

        // 第1承認ステップの作成
        ApplicationApproval firstApproval = ApplicationApproval.builder()
            .applicationId(application.getId())
            .stepOrder(1)
            .approverId(application.getApproverId())
            .status(ApprovalStatus.pending().getStatus().name())
            .isDelegated(false)
            .notificationSent(false)
            .createdAt(LocalDateTime.now())
            .build();

        applicationApprovalRepository.save(firstApproval);

        ApplicationApprovalRoute firstRoute = ApplicationApprovalRoute.builder()
            .applicationId(application.getId())
            .stepOrder(1)
            .application(application)
            .approverId(application.getApproverId())
            .status(ApprovalStatus.Status.PENDING.name())
            .build();
        applicationApprovalRouteRepository.save(firstRoute);

        // 高額申請の場合は追加承認ステップを作成
        if (application.getAmount() != null) {
            java.math.BigDecimal highAmountThreshold = new java.math.BigDecimal("1000000");
            if (application.getAmount().compareTo(highAmountThreshold) > 0) {
                createAdditionalApprovalSteps(application);
            }
        }

        logger.debug("承認フローの初期化が完了しました。申請ID: {}", application.getId());
    }

    /**
     * 高額申請用の追加承認ステップを作成します
     * 
     * @param application 申請
     */
    private void createAdditionalApprovalSteps(Application application) {
        // 部署マネージャーによる第2承認ステップ
        if (application.getDepartmentId() != null) {
            Optional<Department> department = departmentRepository.findById(application.getDepartmentId());
            if (department.isPresent() && department.get().getManagerId() != null) {
                ApplicationApproval secondApproval = ApplicationApproval.builder()
                    .applicationId(application.getId())
                    .stepOrder(2)
                    .approverId(department.get().getManagerId())
                    .status(ApprovalStatus.pending().getStatus().name())
                    .isDelegated(false)
                    .notificationSent(false)
                    .createdAt(LocalDateTime.now())
                    .build();

                applicationApprovalRepository.save(secondApproval);

                ApplicationApprovalRoute secondRoute = ApplicationApprovalRoute.builder()
                    .applicationId(application.getId())
                    .stepOrder(2)
                    .application(application)
                    .approverId(department.get().getManagerId())
                    .status(ApprovalStatus.Status.PENDING.name())
                    .build();
                applicationApprovalRouteRepository.save(secondRoute);
            }
        }
    }

    /**
     * 通知を送信します
     * 
     * @param application 作成された申請
     * @param command 申請作成コマンド
     */
    private void sendNotifications(Application application, CreateApplicationCommand command) {
        logger.debug("通知送信を開始します。申請ID: {}", application.getId());

        try {
            // 承認者への通知
            Optional<User> approver = userRepository.findById(application.getApproverId());
            if (approver.isPresent()) {
                String message = createApprovalNotificationMessage(application, approver.get());
                slackNotificationService.sendNotification(approver.get().getSlackUserId(), message);
            }

            // 緊急申請の場合は追加通知
            if (application.getIsUrgent()) {
                sendUrgentApplicationNotifications(application);
            }

            logger.debug("通知送信が完了しました。申請ID: {}", application.getId());

        } catch (Exception e) {
            logger.warn("通知送信中にエラーが発生しました。申請ID: {}, エラー: {}", 
                       application.getId(), e.getMessage(), e);
            // 通知エラーは申請作成を失敗させない
        }
    }

    /**
     * 承認通知メッセージを作成します
     * 
     * @param application 申請
     * @param approver 承認者
     * @return 通知メッセージ
     */
    private String createApprovalNotificationMessage(Application application, User approver) {
        StringBuilder message = new StringBuilder();
        message.append("📋 新しい申請が提出されました\n\n");
        message.append("**申請ID:** ").append(application.getId()).append("\n");
        message.append("**タイトル:** ").append(application.getTitle()).append("\n");
        message.append("**申請者:** ").append(application.getApplicantId()).append("\n");
        message.append("**優先度:** ").append(application.getPriority()).append("\n");

        if (application.getDueDate() != null) {
            message.append("**期限日:** ").append(application.getDueDate()).append("\n");
        }

        if (application.getIsUrgent()) {
            message.append("🚨 **緊急申請です** 🚨\n");
        }

        message.append("\n承認をお願いします。");

        return message.toString();
    }

    /**
     * 緊急申請の追加通知を送信します
     * 
     * @param application 申請
     */
    private void sendUrgentApplicationNotifications(Application application) {
        // 部署マネージャーへの通知
        if (application.getDepartmentId() != null) {
            Optional<Department> department = departmentRepository.findById(application.getDepartmentId());
            if (department.isPresent() && department.get().getManagerId() != null) {
                Optional<User> manager = userRepository.findById(department.get().getManagerId());
                if (manager.isPresent()) {
                    String urgentMessage = "🚨 緊急申請が提出されました: " + application.getTitle();
                    slackNotificationService.sendNotification(manager.get().getSlackUserId(), urgentMessage);
                }
            }
        }
    }

    /**
     * 監査ログを記録します
     * 
     * @param application 作成された申請
     * @param command 申請作成コマンド
     */
    private void recordAuditLog(Application application, CreateApplicationCommand command) {
        logger.debug("監査ログの記録を開始します。申請ID: {}", application.getId());

        try {
            auditLogService.recordApplicationCreation(
                application.getId(),
                application.getApplicantId(),
                application.getTitle(),
                application.getApplicationType(),
                command.getPriority(),
                application.getIsUrgent(),
                LocalDateTime.now()
            );

            logger.debug("監査ログの記録が完了しました。申請ID: {}", application.getId());

        } catch (Exception e) {
            logger.error("監査ログの記録中にエラーが発生しました。申請ID: {}, エラー: {}", 
                        application.getId(), e.getMessage(), e);
            // 監査ログエラーは申請作成を失敗させない
        }
    }

    /**
     * ステータスで申請を検索しDTOリストを返します。
     *
     * @param statuses ステータスリスト
     * @return DTOリスト
     */
    @Transactional(readOnly = true)
    public java.util.List<jp.co.apsa.giiku.application.dto.ApplicationDto> findDtosByStatuses(java.util.List<String> statuses) {
        return applicationRepository.findByStatusIn(statuses).stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * 申請者IDで申請を検索しDTOリストを返します。
     *
     * @param applicantId 申請者ID
     * @return DTOリスト
     */
    @Transactional(readOnly = true)
    public java.util.List<jp.co.apsa.giiku.application.dto.ApplicationDto> findDtosByApplicantId(Long applicantId) {
        return applicationRepository.findByApplicantIdOrderByApplicationDateDesc(applicantId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * IDで申請を取得しDTOに変換します。
     *
     * @param id 申請ID
     * @return DTO、存在しない場合はnull
     */
    @Transactional(readOnly = true)
    public jp.co.apsa.giiku.application.dto.ApplicationDto findDtoById(Long id) {
        return applicationRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    /**
     * 申請IDに紐づく承認履歴DTO一覧を取得します。
     *
     * @param applicationId 申請ID
     * @return 承認履歴DTOリスト
     */
    @Transactional(readOnly = true)
    public java.util.List<jp.co.apsa.giiku.application.dto.ApplicationApprovalDto> findApprovalDtosByApplicationId(Long applicationId) {
        return applicationApprovalRepository.findByApplicationIdOrderByStepOrder(applicationId)
                .stream()
                .map(this::toApprovalDto)
                .toList();
    }

    /**
     * 承認ステップを完了として記録します。
     *
     * @param applicationId 申請ID
     * @param stepOrder ステップ順序
     * @param approverId 承認者ID
     */
    @Transactional
    public void completeApprovalStep(Long applicationId, Integer stepOrder, Long approverId) {
        applicationApprovalRepository.findByApplicationIdAndStepOrder(applicationId, stepOrder)
                .ifPresent(approval -> {
                    approval.setApproverId(approverId);
                    approval.setStatus(ApprovalStatus.Status.APPROVED.name());
                    approval.setAction(ApprovalAction.APPROVE.getCode());
                    approval.setApprovedAt(LocalDateTime.now());
                    applicationApprovalRepository.save(approval);
                });
    }

    /**
     * 申請を承認済みに更新します。
     *
     * @param applicationId 申請ID
     */
    @Transactional
    public void markApplicationApproved(Long applicationId) {
        applicationRepository.findById(applicationId).ifPresent(app -> {
            app.setStatus(ApplicationStatus.APPROVED);
            app.setApprovedAt(LocalDateTime.now());
            applicationRepository.save(app);
        });
    }

    /**
     * 指定した申請が取下げ可能か判定します。
     *
     * @param applicationId 申請ID
     * @param userId        ユーザーID
     * @return 取下げ可能な場合 true
     */
    @Transactional(readOnly = true)
    public boolean canWithdrawApplication(Long applicationId, Long userId) {
        return applicationRepository.findById(applicationId)
                .map(app -> {
                    if (!app.getApplicantId().equals(userId)) {
                        return false;
                    }
                    ApplicationStatus status = ApplicationStatus.of(app.getStatus());
                    if (!status.canWithdraw()) {
                        return false;
                    }
                    long approved = applicationApprovalRepository.countByApplicationIdAndStatus(
                            applicationId, ApprovalStatus.Status.APPROVED.name());
                    long rejected = applicationApprovalRepository.countByApplicationIdAndStatus(
                            applicationId, ApprovalStatus.Status.REJECTED.name());
                    return approved == 0 && rejected == 0;
                })
                .orElse(false);
    }

    /**
     * 申請を取下げます。
     *
     * <p>申請が未承認かつ申請者本人の場合のみ、状態を {@link jp.co.apsa.giiku.domain.valueobject.ApplicationStatus#WITHDRAWN}
     * に更新します。</p>
     *
     * @param applicationId 申請ID
     * @param applicantId   申請者ID
     * @throws BusinessRuleViolationException 取下げ不可の場合
     */
    @Transactional
    public void withdrawApplication(Long applicationId, Long applicantId) {
        applicationRepository.findById(applicationId).ifPresent(app -> {
            if (!app.getApplicantId().equals(applicantId)) {
                throw new BusinessRuleViolationException("自分の申請のみ取下げ可能です");
            }

            ApplicationStatus status = ApplicationStatus.of(app.getStatus());
            if (!status.canWithdraw()) {
                throw new BusinessRuleViolationException("この申請は取下げできません");
            }

            long approvedCount = applicationApprovalRepository.countByApplicationIdAndStatus(
                    applicationId, ApprovalStatus.Status.APPROVED.name());
            if (approvedCount > 0) {
                throw new BusinessRuleViolationException("既に承認された申請は取下げできません");
            }

            app.setStatus(ApplicationStatus.WITHDRAWN);
            applicationRepository.save(app);
        });
    }

    private jp.co.apsa.giiku.application.dto.ApplicationDto toDto(Application app) {
        String applicantName = null;
        if (app.getApplicant() != null) {
            applicantName = app.getApplicant().getName();
        }
        String typeName = null;
        if (app.getApplicationType() != null) {
            typeName = app.getApplicationType().getName();
        }
        return jp.co.apsa.giiku.application.dto.ApplicationDto.builder()
                .id(app.getId())
                .typeName(typeName)
                .title(app.getTitle())
                .applicantName(applicantName)
                .status(ApplicationStatus.of(app.getStatus()).getDisplayName())
                .content(app.getContent())
                .applicationNumber(app.getApplicationNumber())
                .currentStep(app.getCurrentStep())
                .formData(app.getFormData())
                .reason(app.getReason())
                .priority(app.getPriority())
                .requestedDate(app.getRequestedDate())
                .applicationDate(app.getApplicationDate())
                .approverId(app.getApproverId())
                .dueDate(app.getDueDate())
                .departmentId(app.getDepartmentId())
                .isUrgent(app.getIsUrgent())
                .amount(app.getAmount())
                .currencyCode(app.getCurrencyCode())
                .startDate(app.getStartDate())
                .endDate(app.getEndDate())
                .remarks(app.getRemarks())
                .approvedAt(app.getApprovedAt())
                .rejectedAt(app.getRejectedAt())
                .createdBy(app.getCreatedBy())
                .createdAt(app.getCreatedAt())
                .updatedBy(app.getUpdatedBy())
                .updatedAt(app.getUpdatedAt())
                .build();
    }

    private jp.co.apsa.giiku.application.dto.ApplicationApprovalDto toApprovalDto(jp.co.apsa.giiku.domain.entity.ApplicationApproval approval) {
        String approverName = null;
        if (approval.getApprover() != null) {
            approverName = approval.getApprover().getName();
        }
        return jp.co.apsa.giiku.application.dto.ApplicationApprovalDto.builder()
                .stepOrder(approval.getStepOrder())
                .approverName(approverName)
                .status(ApprovalStatus.of(approval.getStatus()).getDisplayName())
                .approvedAt(approval.getApprovedAt())
                .build();
    }

    /**
     * ビジネスルール違反例外
     */
    public static class BusinessRuleViolationException extends RuntimeException {
        public BusinessRuleViolationException(String message) {
            super(message);
        }

        public BusinessRuleViolationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * 申請作成例外
     */
    public static class ApplicationCreationException extends RuntimeException {
        public ApplicationCreationException(String message) {
            super(message);
        }

        public ApplicationCreationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
