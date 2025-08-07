package jp.co.apsa.giiku.controller;

import jakarta.validation.Valid;
import jp.co.apsa.giiku.application.service.ApplicationService;
import jp.co.apsa.giiku.application.service.ExpenseRequestService;
import jp.co.apsa.giiku.application.dto.ExpenseRequestDto;
import jp.co.apsa.giiku.domain.entity.ExpenseRequestDetails;
import jp.co.apsa.giiku.domain.repository.UserRepository;
import jp.co.apsa.giiku.domain.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 経費申請画面を提供するコントローラー。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/expense")
public class ExpenseRequestController {

    private final ExpenseRequestService expenseRequestService;
    private final ApplicationService applicationService;
    private final UserRepository userRepository;

    public ExpenseRequestController(ExpenseRequestService expenseRequestService,
                                   ApplicationService applicationService,
                                   UserRepository userRepository) {
        this.expenseRequestService = expenseRequestService;
        this.applicationService = applicationService;
        this.userRepository = userRepository;
    }

    /**
     * 経費申請フォームを表示します。
     *
     * @param model モデル
     * @return フォームテンプレート
     */
    @GetMapping("/apply")
    public String form(Model model) {
        model.addAttribute("title", "経費申請");
        model.addAttribute("expenseRequest", new ExpenseRequestDetails());
        return "expense_request_form";
    }

    /**
     * 経費申請を登録します。
     *
     * @param expenseRequest 入力データ
     * @param result 検証結果
     * @param model モデル
     * @return リダイレクト先
     */
    @PostMapping(value = "/apply", consumes = "application/json")
    public String submitJson(@RequestBody @Valid ExpenseRequestDetails expenseRequest,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", "経費申請");
            model.addAttribute("expenseRequest", expenseRequest);
            return "expense_request_form";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElse(null);
        if (user != null) {
            expenseRequest.setApplicantId(user.getId());
            expenseRequest.setApplicantName(user.getName());
            expenseRequest.setApproverId(user.getSupervisorId());
        }
        expenseRequestService.create(expenseRequest);
        return "redirect:/requests";
    }

    /**
     * 承認待ち経費申請一覧を表示します。
     *
     * @param model モデル
     * @return 一覧テンプレート
     */
    @GetMapping("/approve/list")
    public String list(Model model) {
        model.addAttribute("title", "経費申請承認");
        model.addAttribute("requests", expenseRequestService.findAllDtos());
        return "expense_request_list";
    }

    /**
     * 承認画面を表示します。
     *
     * @param id 申請ID
     * @param model モデル
     * @return 承認テンプレート
     */
    @GetMapping("/approve/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("title", "経費申請承認");
        model.addAttribute("application", applicationService.findDtoById(id));
        model.addAttribute("approvalSteps", applicationService.findApprovalDtosByApplicationId(id));
        ExpenseRequestDto dto = expenseRequestService.findDtoByApplicationId(id);
        model.addAttribute("expense", dto);
        model.addAttribute("request", dto);
        return "expense_request_approval";
    }

    /**
     * Ajaxで承認・却下処理を実行します。
     *
     * @param id 申請ID
     * @param action approve/reject
     * @return 更新後の申請
     */
    @PostMapping("/approve/{id}/action")
    @ResponseBody
    public ExpenseRequestDto action(@PathVariable Long id, @RequestParam String action) {
        ExpenseRequestDetails result = null;
        if ("approve".equals(action)) {
            result = expenseRequestService.approve(id);
        } else if ("reject".equals(action)) {
            result = expenseRequestService.reject(id);
        }
        if (result == null) {
            return null;
        }
        return expenseRequestService.findDtoById(result.getId());
    }
}
