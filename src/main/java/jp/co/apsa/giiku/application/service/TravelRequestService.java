package jp.co.apsa.giiku.application.service;

import jp.co.apsa.giiku.domain.entity.TravelRequestDetails;
import jp.co.apsa.giiku.domain.repository.TravelRequestRepository;
import jp.co.apsa.giiku.domain.valueobject.RequestStatus;
import jp.co.apsa.giiku.Constants.RequestStatusCodes;
import jp.co.apsa.giiku.domain.repository.UserRepository;
import jp.co.apsa.giiku.domain.entity.ApplicationType;
import jp.co.apsa.giiku.domain.repository.ApplicationTypeRepository;
import jp.co.apsa.giiku.domain.repository.ApprovalRouteRepository;
import jp.co.apsa.giiku.domain.entity.ApprovalRoute;
import jp.co.apsa.giiku.application.service.ApplicationService;
import jp.co.apsa.giiku.domain.repository.ApplicationRepository;
import jp.co.apsa.giiku.domain.entity.Application;
import jp.co.apsa.giiku.infrastructure.notification.SlackNotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 出張申請を管理するサービス。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class TravelRequestService {

    private final TravelRequestRepository travelRequestRepository;
    private final UserRepository userRepository;
    private final SlackNotificationService slackNotificationService;
    private final ApplicationTypeRepository applicationTypeRepository;
    private final ApprovalRouteRepository approvalRouteRepository;
    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;

    /**
     * コンストラクタ。
     *
     * @param travelRequestRepository 出張申請リポジトリ
     */
    @Autowired
    public TravelRequestService(TravelRequestRepository travelRequestRepository,
                                UserRepository userRepository,
                                SlackNotificationService slackNotificationService,
                                ApplicationTypeRepository applicationTypeRepository,
                                ApprovalRouteRepository approvalRouteRepository,
                                ApplicationService applicationService,
                                ApplicationRepository applicationRepository) {
        this.travelRequestRepository = travelRequestRepository;
        this.userRepository = userRepository;
        this.slackNotificationService = slackNotificationService;
        this.applicationTypeRepository = applicationTypeRepository;
        this.approvalRouteRepository = approvalRouteRepository;
        this.applicationService = applicationService;
        this.applicationRepository = applicationRepository;
    }

    /**
     * 全ての出張申請を取得します。
     *
     * @return 出張申請リスト
     */
    @Transactional(readOnly = true)
    public List<TravelRequestDetails> findAll() {
        return travelRequestRepository.findAll();
    }

    /**
     * 全ての出張申請をDTOで取得します。
     *
     * @return DTOリスト
     */
    @Transactional(readOnly = true)
    public java.util.List<jp.co.apsa.giiku.application.dto.TravelRequestDto> findAllDtos() {
        return travelRequestRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * IDで出張申請を取得します。
     *
     * @param id 申請ID
     * @return 出張申請、存在しない場合はnull
     */
    @Transactional(readOnly = true)
    public TravelRequestDetails findById(Long id) {
        return travelRequestRepository.findById(id).orElse(null);
    }

    /**
     * 申請IDで出張申請を取得します。
     *
     * @param applicationId 申請ID
     * @return 出張申請、存在しない場合はnull
     */
    @Transactional(readOnly = true)
    public TravelRequestDetails findByApplicationId(Long applicationId) {
        return travelRequestRepository.findByApplicationId(applicationId).orElse(null);
    }

    /**
     * IDで出張申請を取得しDTOに変換します。
     *
     * @param id 申請ID
     * @return DTO、存在しない場合はnull
     */
    @Transactional(readOnly = true)
    public jp.co.apsa.giiku.application.dto.TravelRequestDto findDtoById(Long id) {
        TravelRequestDetails details = findById(id);
        if (details == null) {
            return null;
        }
        return toDto(details);
    }

    /**
     * 申請IDで出張申請をDTOとして取得します。
     *
     * @param applicationId 申請ID
     * @return DTO、存在しない場合はnull
     */
    @Transactional(readOnly = true)
    public jp.co.apsa.giiku.application.dto.TravelRequestDto findDtoByApplicationId(Long applicationId) {
        TravelRequestDetails details = findByApplicationId(applicationId);
        if (details == null) {
            return null;
        }
        return toDto(details);
    }

    /**
     * ステータスで出張申請を検索しDTOリストで返します。
     *
     * @param statuses ステータスリスト
     * @return DTOリスト
     */
    @Transactional(readOnly = true)
    public java.util.List<jp.co.apsa.giiku.application.dto.TravelRequestDto> findDtosByStatuses(java.util.List<String> statuses) {
        return travelRequestRepository.findByStatusIn(statuses).stream()
                .map(this::toDto)
                .toList();
    }

    private jp.co.apsa.giiku.application.dto.TravelRequestDto toDto(TravelRequestDetails details) {
        return jp.co.apsa.giiku.application.dto.TravelRequestDto.builder()
                .id(details.getId())
                .applicantName(details.getApplicantName())
                .destination(details.getDestination())
                .startDate(details.getStartDate())
                .endDate(details.getEndDate())
                .transport(details.getTransport())
                .purpose(details.getPurpose())
                .estimatedCost(details.getEstimatedCost())
                .status(RequestStatus.fromCode(details.getStatus()).getDisplayName())
                .build();
    }

    /**
     * 出張申請を新規作成します。
     *
     * @param request 出張申請
     * @return 作成された申請
     */
    public TravelRequestDetails create(@Valid TravelRequestDetails request) {
        // 先に承認ルートから初期承認者を設定する
        initializeApproval(request);

        ApplicationType type = applicationTypeRepository.findByCode("TRAVEL")
                .orElseThrow(() -> new IllegalStateException("申請種別TRAVELが存在しません"));

        var cmd = new jp.co.apsa.giiku.application.command.CreateApplicationCommand.Builder()
                .applicationType(type)
                .applicantId(request.getApplicantId())
                .approverId(request.getApproverId())
                .title("出張申請")
                .content(request.getPurpose() != null ? request.getPurpose() : request.getDestination())
                .priority(jp.co.apsa.giiku.domain.valueobject.Priority.MEDIUM)
                .build();

        Long appId = applicationService.createApplication(cmd);
        request.setApplicationId(appId);
        Application appRef = applicationRepository.getReferenceById(appId);
        request.setApplication(appRef);

        request.setStatus(RequestStatusCodes.SUBMITTED);
        TravelRequestDetails saved = travelRequestRepository.save(request);
        notifyApprover(saved);
        return saved;
    }

    /**
     * 承認処理を行います。
     *
     * @param id 申請ID
     * @return 更新後の申請
     */
    public TravelRequestDetails approve(Long id) {
        TravelRequestDetails req = findById(id);
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
    public TravelRequestDetails reject(Long id) {
        TravelRequestDetails req = findById(id);
        if (req != null) {
            req.setStatus(RequestStatusCodes.REJECTED);
            travelRequestRepository.save(req);
            notifyApplicantRejected(req);
        }
        return req;
    }

    /** 承認者への通知を送信します。 */
    private void notifyApprover(TravelRequestDetails request) {
        if (request.getApproverId() != null) {
            userRepository.findById(request.getApproverId()).ifPresent(user ->
                    slackNotificationService.sendNotification(
                            user.getSlackUserId(),
                            "出張申請(ID: " + request.getApplicationId() + ") が提出されました"));
        }
    }

    /** 申請者へ承認通知を送信します。 */
    private void notifyApplicantApproved(TravelRequestDetails request) {
        if (request.getApplicantId() != null) {
            userRepository.findById(request.getApplicantId()).ifPresent(user ->
                    slackNotificationService.sendNotification(
                            user.getSlackUserId(),
                            "あなたの出張申請(ID: " + request.getApplicationId() + ") が承認されました"));
        }
    }

    /** 申請者へ却下通知を送信します。 */
    private void notifyApplicantRejected(TravelRequestDetails request) {
        if (request.getApplicantId() != null) {
            userRepository.findById(request.getApplicantId()).ifPresent(user ->
                    slackNotificationService.sendNotification(
                            user.getSlackUserId(),
                            "あなたの出張申請(ID: " + request.getApplicationId() + ") は却下されました"));
        }
    }

    /**
     * 承認ルートに基づいて初期承認者を設定します。
     */
    private void initializeApproval(TravelRequestDetails request) {
        ApplicationType type = applicationTypeRepository.findByCode("TRAVEL")
                .orElseThrow(() -> new IllegalStateException("申請種別TRAVELが存在しません"));

        var routes = approvalRouteRepository.findByApplicationTypeId(type.getId());
        if (!routes.isEmpty()) {
            request.setCurrentStep(1);
            request.setApproverId(resolveApprover(routes.get(0), request));
        }
    }

    /**
     * 承認処理を進めます。
     */
    private void proceedApproval(TravelRequestDetails request) {
        ApplicationType type = applicationTypeRepository.findByCode("TRAVEL")
                .orElseThrow(() -> new IllegalStateException("申請種別TRAVELが存在しません"));

        var routes = approvalRouteRepository.findByApplicationTypeId(type.getId());
        int currentStep = request.getCurrentStep() == null ? 1 : request.getCurrentStep();
        applicationService.completeApprovalStep(request.getApplicationId(), currentStep, request.getApproverId());

        int nextStep = currentStep + 1;
        if (nextStep <= routes.size()) {
            request.setCurrentStep(nextStep);
            request.setApproverId(resolveApprover(routes.get(nextStep - 1), request));
            request.setStatus(RequestStatusCodes.IN_PROGRESS);
            travelRequestRepository.save(request);
            notifyApprover(request);
        } else {
            request.setStatus(RequestStatusCodes.APPROVED);
            travelRequestRepository.save(request);
            applicationService.markApplicationApproved(request.getApplicationId());
            notifyApplicantApproved(request);
        }
    }

    /** 承認ルートから承認者を解決します。 */
    private Long resolveApprover(ApprovalRoute route, TravelRequestDetails request) {
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
