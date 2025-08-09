package jp.co.apsa.giiku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ログイン関連画面を提供するコントローラー。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
public class LoginController  extends AbstractController{

    /**
     * ルートアクセス時はログインページへリダイレクトします。
     *
     * @return ログインページへのリダイレクト
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    /**
     * ログインページを表示します。
     *
     * @param logout ログアウト後フラグ
     * @return ログインテンプレート
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (logout != null) {
            model.addAttribute("logout", true);
        }
        return "login";
    }

    /**
     * ログインエラー時にログインページを表示します。
     *
     * @param model モデル
     * @return ログインテンプレート
     */
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    // ダッシュボード画面は DashboardController で処理します
}
