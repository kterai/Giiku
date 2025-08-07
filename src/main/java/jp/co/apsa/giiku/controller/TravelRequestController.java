package jp.co.apsa.giiku.controller;

import jakarta.validation.Valid;
import jp.co.apsa.giiku.application.service.ApplicationService;
import jp.co.apsa.giiku.application.service.TravelRequestService;
import jp.co.apsa.giiku.application.dto.TravelRequestDto;
import jp.co.apsa.giiku.domain.entity.TravelRequestDetails;
import jp.co.apsa.giiku.domain.repository.UserRepository;
import jp.co.apsa.giiku.domain.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 出張申請画面を提供するコントローラー。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/travel")
public class TravelRequestController {

    private final TravelRequestService travelRequestService;
    private final ApplicationService applicationService;
    private final UserRepository userRepository;

    public TravelRequestController(TravelRequestService travelRequestService,
                                   ApplicationService applicationService,
                                   UserRepository userRepository) {
        this.travelRequestService = travelRequestService;
        this.applicationService = applicationService;
        this.userRepository = userRepository;
    }

    /**
     * 出張申請フォームを表示します。
     *
     * @param model モデル
     * @return フォームテンプレート
     */
    @GetMapping("/apply")
    public String form(Model model) {
        model.addAttribute("title", "出張申請");
        model.addAttribute("travelRequest", new TravelRequestDetails());
        return "travel_request_form";
    }

    /**
     * 出張申請を登録します。
     *
     * @param travelRequest 入力データ
     * @param result 検証結果
     * @param model モデル
     * @return リダイレクト先
     */
    @PostMapping(value = "/apply", consumes = "application/json")
    public String submitJson(@RequestBody @Valid TravelRequestDetails travelRequest,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", "出張申請");
            model.addAttribute("travelRequest", travelRequest);
            return "travel_request_form";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElse(null);
        if (user != null) {
            travelRequest.setApplicantId(user.getId());
            travelRequest.setApplicantName(user.getName());
            travelRequest.setApproverId(user.getSupervisorId());
        }
        travelRequestService.create(travelRequest);
        return "redirect:/requests";
    }

    /**
     * 承認待ち出張申請一覧を表示します。
     *
     * @param model モデル
     * @return 一覧テンプレート
     */
    @GetMapping("/approve/list")
    public String list(Model model) {
        model.addAttribute("title", "出張申請承認");
        model.addAttribute("requests", travelRequestService.findAllDtos());
        return "travel_request_list";
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
        model.addAttribute("title", "出張申請承認");
        model.addAttribute("application", applicationService.findDtoById(id));
        model.addAttribute("approvalSteps", applicationService.findApprovalDtosByApplicationId(id));
        var travelDto = travelRequestService.findDtoByApplicationId(id);
        model.addAttribute("travel", travelDto);
        model.addAttribute("request", travelDto);
        return "travel_request_approval";
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
    public jp.co.apsa.giiku.application.dto.TravelRequestDto action(@PathVariable Long id, @RequestParam String action) {
        TravelRequestDetails result = null;
        if ("approve".equals(action)) {
            result = travelRequestService.approve(id);
        } else if ("reject".equals(action)) {
            result = travelRequestService.reject(id);
        }
        if (result == null) {
            return null;
        }
        return travelRequestService.findDtoById(result.getId());
    }
}
