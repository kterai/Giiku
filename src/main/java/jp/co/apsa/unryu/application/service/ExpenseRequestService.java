package jp.co.apsa.unryu.application.service;

import jp.co.apsa.unryu.domain.entity.ExpenseRequestDetails;
import jp.co.apsa.unryu.domain.repository.ExpenseRequestRepository;
import jp.co.apsa.unryu.domain.valueobject.RequestStatus;
import jp.co.apsa.unryu.Constants.RequestStatusCodes;
import jp.co.apsa.unryu.domain.repository.UserRepository;
import jp.co.apsa.unryu.domain.entity.ApplicationType;
import jp.co.apsa.unryu.domain.repository.ApplicationTypeRepository;
import jp.co.apsa.unryu.domain.repository.ApprovalRouteRepository;
import jp.co.apsa.unryu.domain.entity.ApprovalRoute;
import jp.co.apsa.unryu.application.service.ApplicationService;
import jp.co.apsa.unryu.domain.repository.ApplicationRepository;
import jp.co.apsa.unryu.domain.entity.Application;
import jp.co.apsa.unryu.infrastructure.notification.SlackNotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 経費申請を管理するサービス。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class ExpenseRequestService {

    private final ExpenseRequestRepository expenseRequestRepository;
    private final UserRepository userRepository;
    private final SlackNotificationService slackNotificationService;
    private final ApplicationTypeRepository applicationTypeRepository;
    private final ApprovalRouteRepository approvalRouteRepository;
    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;

    /**
     * コンストラクタ。
     *
     * @param expenseRequestRepository 経費申請リポジトリ
     */
    @Autowired
    public ExpenseRequestService(ExpenseRequestRepository expenseRequestRepository,
                                 UserRepository userRepository,
                                 SlackNotificationService slackNotificationService,
                                 ApplicationTypeRepository applicationTypeRepository,
                                 ApprovalRouteRepository approvalRouteRepository,
                                 ApplicationService applicationService,
                                 ApplicationRepository applicationRepository) {
        this.expenseRequestRepository = expenseRequestRepository;
        this.userRepository = userRepository;
        this.slackNotificationService = slackNotificationService;
        this.applicationTypeRepository = applicationTypeRepository;
        this.approvalRouteRepository = approvalRouteRepository;
        this.applicationService = applicationService;
        this.applicationRepository = applicationRepository;
    }

    /**
     * 全ての経費申請を取得します。
     *
     * @return 経費申請リスト
     */
    @Transactional(readOnly = true)
    public List<ExpenseRequestDetails> findAll() {
        return expenseRequestRepository.findAll();
    }

    /**
     * 全ての経費申請をDTOで取得します。
     *
     * @return DTOリスト
     */
    @Transactional(readOnly = true)
    public java.util.List<jp.co.apsa.unryu.application.dto.ExpenseRequestDto> findAllDtos() {
        return expenseRequestRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * IDで経費申請を取得します。
     *
     * @param id 申請ID
     * @return 経費申請、存在しない場合はnull
     */
    @Transactional(readOnly = true)
    public ExpenseRequestDetails findById(Long id) {
        return expenseRequestRepository.findById(id).orElse(null);
    }

    /**
     * 申請IDで経費申請を取得します。
     *
     * @param applicationId 申請ID
     * @return 経費申請、存在しない場合はnull
     */
    @Transactional(readOnly = true)
    public ExpenseRequestDetails findByApplicationId(Long applicationId) {
        return expenseRequestRepository.findByApplicationId(applicationId).orElse(null);
    }

    /**
     * IDで経費申請を取得しDTOに変換します。
     *
     * @param id 申請ID
     * @return DTO、存在しない場合はnull
     */
    @Transactional(readOnly = true)
    public jp.co.apsa.unryu.application.dto.ExpenseRequestDto findDtoById(Long id) {
        ExpenseRequestDetails details = findById(id);
        if (details == null) {
            return null;
        }
        return toDto(details);
    }

    /**
     * 申請IDで経費申請をDTOとして取得します。
     *
     * @param applicationId 申請ID
     * @return DTO、存在しない場合はnull
     */
    @Transactional(readOnly = true)
    public jp.co.apsa.unryu.application.dto.ExpenseRequestDto findDtoByApplicationId(Long applicationId) {
        ExpenseRequestDetails details = findByApplicationId(applicationId);
        if (details == null) {
            return null;
        }
        return toDto(details);
    }

    /**
     * ステータスで経費申請を検索しDTOリストで返します。
     *
     * @param statuses ステータスリスト
     * @return DTOリスト
     */
    @Transactional(readOnly = true)
    public java.util.List<jp.co.apsa.unryu.application.dto.ExpenseRequestDto> findDtosByStatuses(java.util.List<String> statuses) {
        return expenseRequestRepository.findByStatusIn(statuses).stream()
                .map(this::toDto)
                .toList();
    }

    private jp.co.apsa.unryu.application.dto.ExpenseRequestDto toDto(ExpenseRequestDetails details) {
        return jp.co.apsa.unryu.application.dto.ExpenseRequestDto.builder()
                .id(details.getId())
                .applicantName(details.getApplicantName())
                .expenseDate(details.getExpenseDate())
                .amount(details.getAmount())
                .description(details.getDescription())
                .status(RequestStatus.fromCode(details.getStatus()).getDisplayName())
                .build();
    }

    /**
     * 経費申請を新規作成します。
     *
     * @param request 経費申請
     * @return 作成された申請
     */
    public ExpenseRequestDetails create(@Valid ExpenseRequestDetails request) {
        // 先に承認ルートから初期承認者を設定する
        initializeApproval(request);

        ApplicationType type = applicationTypeRepository.findByCode("EXPENSE")
                .orElseThrow(() -> new IllegalStateException("申請種別EXPENSEが存在しません"));

        var cmd = new jp.co.apsa.unryu.application.command.CreateApplicationCommand.Builder()
                .applicationType(type)
                .applicantId(request.getApplicantId())
                .approverId(request.getApproverId())
                .title("経費申請")
                .content(request.getDescription())
                .priority(jp.co.apsa.unryu.domain.valueobject.Priority.MEDIUM)
                .amount(request.getAmount())
                .currencyCode("JPY")
                .build();

        Long appId = applicationService.createApplication(cmd);
        request.setApplicationId(appId);
        Application appRef = applicationRepository.getReferenceById(appId);
        request.setApplication(appRef);

        request.setStatus(RequestStatusCodes.SUBMITTED);
        ExpenseRequestDetails saved = expenseRequestRepository.save(request);
        notifyApprover(saved);
        return saved;
    }

    /**
     * 承認処理を行います。
     *
     * @param id 申請ID
     * @return 更新後の申請
     */
    public ExpenseRequestDetails approve(Long id) {
        ExpenseRequestDetails req = findById(id);
        if (req != null) {
            proceedApproval(req);
        }
        return req;
    }

    /**
     * 却下処理を行います。
     *
     * @param id 申請ID
     * @return 更新後の申請
     */
    public ExpenseRequestDetails reject(Long id) {
        ExpenseRequestDetails req = findById(id);
        if (req != null) {
            req.setStatus(RequestStatusCodes.REJECTED);
            expenseRequestRepository.save(req);
            notifyApplicantRejected(req);
        }
        return req;
    }

    /** 承認者への通知を送信します。 */
    private void notifyApprover(ExpenseRequestDetails request) {
        if (request.getApproverId() != null) {
            userRepository.findById(request.getApproverId()).ifPresent(user ->
                    slackNotificationService.sendNotification(
                            user.getSlackUserId(),
                            "経費申請(ID: " + request.getApplicationId() + ") が提出されました"));
        }
    }

    /** 申請者へ承認通知を送信します。 */
    private void notifyApplicantApproved(ExpenseRequestDetails request) {
        if (request.getApplicantId() != null) {
            userRepository.findById(request.getApplicantId()).ifPresent(user ->
                    slackNotificationService.sendNotification(
                            user.getSlackUserId(),
                            "あなたの経費申請(ID: " + request.getApplicationId() + ") が承認されました"));
        }
    }

    /** 申請者へ却下通知を送信します。 */
    private void notifyApplicantRejected(ExpenseRequestDetails request) {
        if (request.getApplicantId() != null) {
            userRepository.findById(request.getApplicantId()).ifPresent(user ->
                    slackNotificationService.sendNotification(
                            user.getSlackUserId(),
                            "あなたの経費申請(ID: " + request.getApplicationId() + ") は却下されました"));
        }
    }

    /**
     * 承認ルートに基づいて初期承認者を設定します。
     */
    private void initializeApproval(ExpenseRequestDetails request) {
        ApplicationType type = applicationTypeRepository.findByCode("EXPENSE")
                .orElseThrow(() -> new IllegalStateException("申請種別EXPENSEが存在しません"));

        var routes = approvalRouteRepository.findByApplicationTypeId(type.getId());
        if (!routes.isEmpty()) {
            request.setCurrentStep(1);
            request.setApproverId(resolveApprover(routes.get(0), request));
        }
    }

    /**
     * 承認処理を進めます。
     */
    private void proceedApproval(ExpenseRequestDetails request) {
        ApplicationType type = applicationTypeRepository.findByCode("EXPENSE")
                .orElseThrow(() -> new IllegalStateException("申請種別EXPENSEが存在しません"));

        var routes = approvalRouteRepository.findByApplicationTypeId(type.getId());
        int currentStep = request.getCurrentStep() == null ? 1 : request.getCurrentStep();
        applicationService.completeApprovalStep(request.getApplicationId(), currentStep, request.getApproverId());

        int nextStep = currentStep + 1;
        if (nextStep <= routes.size()) {
            request.setCurrentStep(nextStep);
            request.setApproverId(resolveApprover(routes.get(nextStep - 1), request));
            request.setStatus(RequestStatusCodes.IN_PROGRESS);
            expenseRequestRepository.save(request);
            notifyApprover(request);
        } else {
            request.setStatus(RequestStatusCodes.APPROVED);
            expenseRequestRepository.save(request);
            applicationService.markApplicationApproved(request.getApplicationId());
            notifyApplicantApproved(request);
        }
    }

    /** 承認ルートから承認者を解決します。 */
    private Long resolveApprover(ApprovalRoute route, ExpenseRequestDetails request) {
        if (route.getSpecificApproverId() != null) {
            return route.getSpecificApproverId();
        }
        if (Boolean.TRUE.equals(route.getDepartmentHead()) && route.getApproverDepartmentId() != null) {
            return userRepository.findByDepartmentId(route.getApproverDepartmentId())
                    .stream().findFirst().map(u -> u.getId()).orElse(null);
        }
        if (route.getApproverDepartmentId() != null) {
            return userRepository.findByDepartmentId(route.getApproverDepartmentId())
                    .stream().findFirst().map(u -> u.getId()).orElse(null);
        }
        if (route.getApproverRole() != null) {
            return userRepository.findByRoleOrderByUsername(route.getApproverRole())
                    .stream().findFirst().map(u -> u.getId()).orElse(null);
        }
        return request.getApproverId();
    }
}
