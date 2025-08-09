package jp.co.apsa.giiku.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 管理者向けページを表示するコントローラー。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends jp.co.apsa.giiku.controller.AbstractController {

    /**
     * 管理トップページを表示します。
     *
     * @param model モデル
     * @return 管理トップのテンプレート
     */
    @GetMapping
    public String index(Model model) {
        setTitle(model, "Admin");
        model.addAttribute("pageTitle", "Admin Top");
        model.addAttribute("breadcrumb", List.of(
                Map.of("label", "Home", "href", "/"),
                Map.of("label", "Admin")
        ));
        return "admin/index";
    }
}
