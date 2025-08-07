package jp.co.apsa.giiku.application.service;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.apsa.giiku.domain.entity.Application;
import jp.co.apsa.giiku.domain.entity.ApplicationApproval;
import jp.co.apsa.giiku.application.dto.ChartDto;
import jp.co.apsa.giiku.domain.repository.ApplicationApprovalRepository;
import jp.co.apsa.giiku.domain.repository.ApplicationRepository;
import jp.co.apsa.giiku.domain.valueobject.ApprovalStatus;

/**
 * ダッシュボード関連のサービス。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationApprovalRepository approvalRepository;

    /**
     * コンストラクタ。
     *
     * @param applicationRepository 申請リポジトリ
     * @param approvalRepository 承認履歴リポジトリ
     */
    @Autowired
    public DashboardService(ApplicationRepository applicationRepository,
                            ApplicationApprovalRepository approvalRepository) {
        this.applicationRepository = applicationRepository;
        this.approvalRepository = approvalRepository;
    }

    /**
     * 指定された申請者の申請を申請日時の降順ですべて取得します。
     *
     * @param applicantId 申請者ID
     * @return 申請一覧
     */
    public List<Application> findRecentApplications(Long applicantId) {
        return applicationRepository
                .findByApplicantIdOrderByApplicationDateDesc(applicantId);
    }

    /**
     * 承認待ち申請を取得します。
     *
     * @param approverId 承認者ID
     * @return 承認待ち申請一覧
     */
    public List<ApplicationApproval> findPendingApprovals(Long approverId) {
        return approvalRepository.findByApproverIdAndStatus(
                approverId,
                ApprovalStatus.Status.PENDING.name());
    }

    /**
     * 月別申請件数チャートデータを生成します。
     *
     * @param applicantId 申請者ID
     * @return チャートデータ
     */
    public ChartDto getMonthlyApplicationChart(Long applicantId) {
        List<Application> apps = applicationRepository.findByApplicantId(applicantId);
        Map<Integer, Long> counts = apps.stream()
                .filter(a -> a.getApplicationDate() != null)
                .collect(Collectors.groupingBy(a -> a.getApplicationDate().getMonthValue(), Collectors.counting()));

        List<String> labels = IntStream.rangeClosed(1, 12)
                .mapToObj(Integer::toString)
                .collect(Collectors.toList());
        List<Long> data = labels.stream()
                .map(l -> counts.getOrDefault(Integer.parseInt(l), 0L))
                .collect(Collectors.toList());

        return ChartDto.builder()
                .labels(labels)
                .data(data)
                .build();
    }
}
