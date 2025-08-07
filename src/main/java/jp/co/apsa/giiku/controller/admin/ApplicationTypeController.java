package jp.co.apsa.giiku.controller.admin;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.apsa.giiku.application.service.ApplicationTypeService;
import jp.co.apsa.giiku.domain.entity.ApplicationType;

/**
 * 申請種別マスタを管理するコントローラー。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/admin/application-types")
public class ApplicationTypeController {

    private final ApplicationTypeService applicationTypeService;

    public ApplicationTypeController(ApplicationTypeService applicationTypeService) {
        this.applicationTypeService = applicationTypeService;
    }

    /** 一覧表示 */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("title", "申請種別一覧");
        model.addAttribute("types", applicationTypeService.findAll());
        return "application_type_list";
    }

    /** 新規作成フォーム */
    @GetMapping("/new")
    public String formNew(Model model) {
        model.addAttribute("title", "申請種別作成");
        model.addAttribute("applicationType", new ApplicationType());
        return "application_type_detail";
    }

    /** 編集フォーム */
    @GetMapping("/{id}")
    public String formEdit(@PathVariable Long id, Model model) {
        model.addAttribute("title", "申請種別編集");
        model.addAttribute("applicationType", applicationTypeService.findById(id));
        return "application_type_detail";
    }

    /** 登録・更新処理 */
    @PostMapping
    public String save(@Valid ApplicationType applicationType, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", applicationType.getId() == null ? "申請種別作成" : "申請種別編集");
            return "application_type_detail";
        }
        if (applicationType.getId() == null) {
            applicationTypeService.create(applicationType);
        } else {
            applicationTypeService.update(applicationType);
        }
        return "redirect:/admin/application-types";
    }

    /** 削除処理 */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        boolean deleted = applicationTypeService.delete(id);
        if (!deleted) {
            model.addAttribute("deleteError", true);
            model.addAttribute("applicationType", applicationTypeService.findById(id));
            return "application_type_detail";
        }
        return "redirect:/admin/application-types";
    }
}
