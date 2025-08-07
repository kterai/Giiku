package jp.co.apsa.unryu.controller;

import jp.co.apsa.unryu.application.service.ApplicationService;
import jp.co.apsa.unryu.application.service.TravelRequestService;
import jp.co.apsa.unryu.application.service.ExpenseRequestService;
import jp.co.apsa.unryu.domain.valueobject.ApplicationStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 承認一覧画面を提供するコントローラー。
 *
 * <p>申請種別に依存せず、applications テーブルの情報のみを利用して
 * 承認待ち申請の一覧を表示します。</p>
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping({"/approvals", "/admin/approvals"})
public class ApprovalController {

    private final ApplicationService applicationService;
    private final TravelRequestService travelRequestService;
    private final ExpenseRequestService expenseRequestService;

    public ApprovalController(ApplicationService applicationService,
                              TravelRequestService travelRequestService,
                              ExpenseRequestService expenseRequestService) {
        this.applicationService = applicationService;
        this.travelRequestService = travelRequestService;
        this.expenseRequestService = expenseRequestService;
    }

    /**
     * 承認待ち申請一覧を表示します。
     *
     * @param model モデル
     * @return 一覧テンプレート
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("title", "承認一覧");
        model.addAttribute(
                "requests",
                applicationService.findDtosByStatuses(
                        List.of(ApplicationStatus.PENDING, ApplicationStatus.IN_REVIEW)));
        return "approval_list";
    }

    /**
     * 申請承認画面を表示します。
     *
     * @param id 申請ID
     * @param model モデル
     * @return 承認テンプレート
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("title", "申請承認");
        model.addAttribute("request", applicationService.findDtoById(id));
        model.addAttribute("approvalSteps", applicationService.findApprovalDtosByApplicationId(id));
        model.addAttribute("travel", travelRequestService.findDtoByApplicationId(id));
        model.addAttribute("expense", expenseRequestService.findDtoByApplicationId(id));
        return "approval_detail";
    }
}
