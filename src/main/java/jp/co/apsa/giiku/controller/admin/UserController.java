package jp.co.apsa.giiku.controller.admin;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.apsa.giiku.application.service.UserAdminService;
import jp.co.apsa.giiku.domain.entity.User;

/**
 * ユーザーマスタを管理するコントローラー。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserAdminService userAdminService;
    /**
     * UserController メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public UserController(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    /** 一覧表示 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("title", "ユーザー一覧");
        model.addAttribute("users", userAdminService.findAll());
        return "user_list";
    }

    /** 新規作成フォーム 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/new")
    public String formNew(Model model) {
        model.addAttribute("title", "ユーザー作成");
        model.addAttribute("user", new User());
        return "user_detail";
    }

    /** 編集フォーム 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/{id}")
    public String formEdit(@PathVariable Long id, Model model) {
        model.addAttribute("title", "ユーザー編集");
        model.addAttribute("user", userAdminService.findById(id));
        return "user_detail";
    }

    /** 登録・更新処理 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @PostMapping
    public String save(@Valid User user,
                       BindingResult result,
                       @RequestParam(required = false) String newPassword,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", user.getId() == null ? "ユーザー作成" : "ユーザー編集");
            return "user_detail";
        }
        if (newPassword != null && !newPassword.isBlank()) {
            user.setPassword(newPassword);
        }
        if (user.getId() == null) {
            userAdminService.create(user);
        } else {
            userAdminService.update(user);
        }
        return "redirect:/admin/users";
    }

    /** 削除処理 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userAdminService.delete(id);
        return "redirect:/admin/users";
    }
}
