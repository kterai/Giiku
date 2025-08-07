package jp.co.apsa.giiku.controller.admin;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.apsa.giiku.application.service.ApprovalRouteService;
import jp.co.apsa.giiku.application.service.ApplicationTypeService;
import jp.co.apsa.giiku.application.service.DepartmentService;
import jp.co.apsa.giiku.application.service.UserAdminService;
import jp.co.apsa.giiku.domain.entity.ApprovalRoute;

/**
 * 承認ルートを管理するコントローラー。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/admin/application-types/{typeId}/routes")
public class ApprovalRouteController {

    private final ApprovalRouteService approvalRouteService;
    private final ApplicationTypeService applicationTypeService;
    private final DepartmentService departmentService;
    private final UserAdminService userAdminService;

    @Autowired
    public ApprovalRouteController(ApprovalRouteService approvalRouteService,
                                   ApplicationTypeService applicationTypeService,
                                   DepartmentService departmentService,
                                   UserAdminService userAdminService) {
        this.approvalRouteService = approvalRouteService;
        this.applicationTypeService = applicationTypeService;
        this.departmentService = departmentService;
        this.userAdminService = userAdminService;
    }

    /** 一覧表示 */
    @GetMapping
    public String list(@PathVariable Long typeId, Model model) {
        model.addAttribute("title", "承認ルート一覧");
        model.addAttribute("applicationType", applicationTypeService.findById(typeId));
        model.addAttribute("routes", approvalRouteService.findByApplicationTypeId(typeId));
        return "approval_route_list";
    }

    /** 新規作成フォーム */
    @GetMapping("/new")
    public String formNew(@PathVariable Long typeId, Model model) {
        ApprovalRoute route = new ApprovalRoute();
        route.setApplicationTypeId(typeId);
        populate(model);
        model.addAttribute("title", "承認ルート作成");
        model.addAttribute("applicationType", applicationTypeService.findById(typeId));
        model.addAttribute("approvalRoute", route);
        return "approval_route_detail";
    }

    /** 編集フォーム */
    @GetMapping("/{routeId}")
    public String formEdit(@PathVariable Long typeId, @PathVariable Long routeId, Model model) {
        model.addAttribute("title", "承認ルート編集");
        model.addAttribute("applicationType", applicationTypeService.findById(typeId));
        model.addAttribute("approvalRoute", approvalRouteService.findById(routeId));
        populate(model);
        return "approval_route_detail";
    }

    /** 登録・更新処理 */
    @PostMapping
    public String save(@PathVariable Long typeId, @Valid ApprovalRoute approvalRoute, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", approvalRoute.getId() == null ? "承認ルート作成" : "承認ルート編集");
            model.addAttribute("applicationType", applicationTypeService.findById(typeId));
            populate(model);
            return "approval_route_detail";
        }
        approvalRoute.setApplicationTypeId(typeId);
        if (approvalRoute.getId() == null) {
            approvalRouteService.create(approvalRoute);
        } else {
            approvalRouteService.update(approvalRoute);
        }
        return "redirect:/admin/application-types/" + typeId + "/routes";
    }

    /** 削除処理 */
    @PostMapping("/{routeId}/delete")
    public String delete(@PathVariable Long typeId, @PathVariable Long routeId) {
        approvalRouteService.delete(routeId);
        return "redirect:/admin/application-types/" + typeId + "/routes";
    }

    private void populate(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("users", userAdminService.findApprovers());
    }
}
