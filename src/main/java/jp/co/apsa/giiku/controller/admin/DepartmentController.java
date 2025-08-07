package jp.co.apsa.giiku.controller.admin;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.apsa.giiku.application.service.DepartmentService;
import jp.co.apsa.giiku.application.service.UserAdminService;
import jp.co.apsa.giiku.domain.entity.Department;

/**
 * 部署マスタを管理するコントローラー。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/admin/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final UserAdminService userAdminService;
    public DepartmentController(DepartmentService departmentService,
                               UserAdminService userAdminService) {
        this.departmentService = departmentService;
        this.userAdminService = userAdminService;
    }

    /** 一覧表示 */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("title", "部署一覧");
        model.addAttribute("departments", departmentService.findAll());
        return "department_list";
    }

    /** 新規作成フォーム */
    @GetMapping("/new")
    public String formNew(Model model) {
        model.addAttribute("title", "部署作成");
        model.addAttribute("department", new Department());
        populate(model);
        return "department_detail";
    }

    /** 編集フォーム */
    @GetMapping("/{id}")
    public String formEdit(@PathVariable Long id, Model model) {
        model.addAttribute("title", "部署編集");
        model.addAttribute("department", departmentService.findById(id));
        populate(model);
        return "department_detail";
    }

    /** 登録・更新処理 */
    @PostMapping
    public String save(@Valid Department department, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", department.getId() == null ? "部署作成" : "部署編集");
            populate(model);
            return "department_detail";
        }
        if (department.getId() == null) {
            departmentService.create(department);
        } else {
            departmentService.update(department);
        }
        return "redirect:/admin/departments";
    }

    /** 削除処理 */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        boolean deleted = departmentService.delete(id);
        if (!deleted) {
            model.addAttribute("deleteError", true);
            model.addAttribute("department", departmentService.findById(id));
            populate(model);
            return "department_detail";
        }
        return "redirect:/admin/departments";
    }

    private void populate(Model model) {
        model.addAttribute("users", userAdminService.findAll());
    }
}
