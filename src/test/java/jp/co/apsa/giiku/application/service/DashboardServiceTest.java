package jp.co.apsa.giiku.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.co.apsa.giiku.application.dto.ChartDto;
import jp.co.apsa.giiku.domain.entity.Application;
import jp.co.apsa.giiku.domain.entity.ApplicationApproval;
import jp.co.apsa.giiku.domain.repository.ApplicationApprovalRepository;
import jp.co.apsa.giiku.domain.repository.ApplicationRepository;
import jp.co.apsa.giiku.domain.valueobject.ApprovalStatus;

/**
 * {@link DashboardService} のユニットテスト。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    ApplicationRepository applicationRepository;
    @Mock
    ApplicationApprovalRepository approvalRepository;

    @InjectMocks
    DashboardService dashboardService;

    @Test
    @DisplayName("最近の申請取得")
    void testFindRecentApplications() {
        Application app = Application.builder()
                .id(1L)
                .applicantId(1L)
                .applicationTypeId(1L)
                .applicationDate(LocalDateTime.now())
                .build();
        when(applicationRepository.findByApplicantIdOrderByApplicationDateDesc(1L))
                .thenReturn(Collections.singletonList(app));

        List<Application> result = dashboardService.findRecentApplications(1L);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("承認待ち申請取得")
    void testFindPendingApprovals() {
        ApplicationApproval approval = ApplicationApproval.builder()
                .id(1L)
                .applicationId(1L)
                .approverId(2L)
                .stepOrder(1)
                .status(ApprovalStatus.Status.PENDING.name())
                .build();
        when(approvalRepository.findByApproverIdAndStatus(2L, ApprovalStatus.Status.PENDING.name()))
                .thenReturn(Collections.singletonList(approval));

        List<ApplicationApproval> result = dashboardService.findPendingApprovals(2L);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getApproverId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("月別申請件数チャート")
    void testGetMonthlyApplicationChart() {
        Application jan = Application.builder()
                .id(1L)
                .applicantId(1L)
                .applicationTypeId(1L)
                .applicationDate(LocalDateTime.of(2025, 1, 10, 0, 0))
                .build();
        Application feb = Application.builder()
                .id(2L)
                .applicantId(1L)
                .applicationTypeId(1L)
                .applicationDate(LocalDateTime.of(2025, 2, 5, 0, 0))
                .build();
        Application nullDate = Application.builder()
                .id(3L)
                .applicantId(1L)
                .applicationTypeId(1L)
                .build();
        when(applicationRepository.findByApplicantId(1L)).thenReturn(Arrays.asList(jan, feb, nullDate));

        ChartDto chart = dashboardService.getMonthlyApplicationChart(1L);
        assertThat(chart.getLabels()).hasSize(12);
        assertThat(chart.getData().get(0)).isEqualTo(1L);
        assertThat(chart.getData().get(1)).isEqualTo(1L);
        assertThat(chart.getData().subList(2, 12)).allMatch(v -> v == 0L);
    }
}
