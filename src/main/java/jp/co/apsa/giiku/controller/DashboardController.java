package jp.co.apsa.giiku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.apsa.giiku.application.service.DashboardService;

/**
 * ダッシュボード画面を提供するコントローラー。
 * ユーザー数を表示するシンプルな実装です。
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
public class DashboardController extends AbstractController {

    private final DashboardService dashboardService;
    /**
     * DashboardController メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * ダッシュボードページを表示します。
     *
     * @param model モデル
     * @return ダッシュボードテンプレート
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping(path = {"/dashboard","/index.html"})
    public String dashboard(Model model) {
        model.addAttribute("userCount", dashboardService.countUsers());
        setTitle(model, "ダッシュボード");
        return "dashboard";
    }
}
