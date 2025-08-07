package jp.co.apsa.unryu.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理メニューを提供するコントローラー。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * 管理メニュー画面を表示します。
     *
     * @param model モデル
     * @return 管理メニューテンプレート
     */
    @GetMapping
    public String index(Model model) {
        model.addAttribute("title", "管理メニュー");
        return "admin_home";
    }
}
