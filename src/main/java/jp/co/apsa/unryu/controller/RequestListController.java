package jp.co.apsa.unryu.controller;

import jp.co.apsa.unryu.application.service.ApplicationService;
import jp.co.apsa.unryu.domain.entity.User;
import jp.co.apsa.unryu.domain.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 自分の申請一覧画面を提供するコントローラー。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/requests")
public class RequestListController {

    private final ApplicationService applicationService;
    private final UserRepository userRepository;

    public RequestListController(ApplicationService applicationService,
                                 UserRepository userRepository) {
        this.applicationService = applicationService;
        this.userRepository = userRepository;
    }

    /**
     * 自分の申請一覧を表示します。
     *
     * @param model モデル
     * @return 一覧テンプレート
     */
    @GetMapping
    public String list(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElse(null);
        if (user != null) {
            model.addAttribute("requests", applicationService.findDtosByApplicantId(user.getId()));
        }
        model.addAttribute("title", "申請一覧");
        model.addAttribute("basePath", "/requests/");
        return "request_view_list";
    }
}
