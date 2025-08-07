package jp.co.apsa.unryu.controller;

import jp.co.apsa.unryu.application.service.ApplicationService;
import jp.co.apsa.unryu.application.service.TravelRequestService;
import jp.co.apsa.unryu.application.service.ExpenseRequestService;
import jp.co.apsa.unryu.domain.entity.User;
import jp.co.apsa.unryu.domain.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 申請詳細画面を提供するコントローラー。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping({"/requests", "/admin/requests"})
public class RequestViewController {

    private final ApplicationService applicationService;
    private final TravelRequestService travelRequestService;
    private final ExpenseRequestService expenseRequestService;
    private final UserRepository userRepository;

    public RequestViewController(ApplicationService applicationService,
                                 TravelRequestService travelRequestService,
                                 ExpenseRequestService expenseRequestService,
                                 UserRepository userRepository) {
        this.applicationService = applicationService;
        this.travelRequestService = travelRequestService;
        this.expenseRequestService = expenseRequestService;
        this.userRepository = userRepository;
    }

    /**
     * 申請詳細を表示します。
     *
     * @param id 申請ID
     * @param model モデル
     * @return 詳細テンプレート
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("title", "申請詳細");
        model.addAttribute("request", applicationService.findDtoById(id));
        model.addAttribute("approvalSteps", applicationService.findApprovalDtosByApplicationId(id));
        model.addAttribute("travel", travelRequestService.findDtoByApplicationId(id));
        model.addAttribute("expense", expenseRequestService.findDtoByApplicationId(id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElse(null);
        boolean canWithdraw = false;
        if (user != null) {
            canWithdraw = applicationService.canWithdrawApplication(id, user.getId());
        }
        model.addAttribute("canWithdraw", canWithdraw);
        return "request_view_detail";
    }
}
