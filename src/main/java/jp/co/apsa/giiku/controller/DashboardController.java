package jp.co.apsa.giiku.controller;

import java.util.List;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.apsa.giiku.application.service.DashboardService;
import jp.co.apsa.giiku.application.dto.ChartDto;
import jp.co.apsa.giiku.domain.entity.Application;
import jp.co.apsa.giiku.domain.entity.ApplicationApproval;
import jp.co.apsa.giiku.domain.entity.User;
import jp.co.apsa.giiku.domain.repository.UserRepository;

/**
 * ダッシュボード画面を提供するコントローラー。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserRepository userRepository;

    public DashboardController(DashboardService dashboardService,
                               UserRepository userRepository) {
        this.dashboardService = dashboardService;
        this.userRepository = userRepository;
    }

    /**
     * ダッシュボードページを表示します。
     *
     * @param model モデル
     * @return ダッシュボードテンプレート
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElse(null);

        List<Application> apps = Collections.emptyList();
        List<ApplicationApproval> approvals = Collections.emptyList();
        ChartDto chart = ChartDto.builder()
                .labels(Collections.emptyList())
                .data(Collections.emptyList())
                .build();

        if (user != null) {
            apps = dashboardService.findRecentApplications(user.getId());
            if (Boolean.TRUE.equals(user.getApprover())) {
                approvals = dashboardService.findPendingApprovals(user.getId());
            }
            chart = dashboardService.getMonthlyApplicationChart(user.getId());
        }

        model.addAttribute("applications", apps);
        model.addAttribute("approvals", approvals);
        model.addAttribute("chart", chart);
        model.addAttribute("title", "ダッシュボード");
        return "dashboard";
    }
}
