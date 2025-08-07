package jp.co.apsa.unryu.controller.admin;

import jp.co.apsa.unryu.application.dto.ApplicationDto;
import jp.co.apsa.unryu.application.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 申請閲覧機能を提供するコントローラー。
 * 一般ユーザーには非公開で管理者のみアクセス可能です。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/admin/requests")
public class AdminRequestViewController {

    private final ApplicationService applicationService;

    public AdminRequestViewController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * 申請一覧を表示します。
     *
     * @param model モデル
     * @return 一覧テンプレート
     */
    @GetMapping
    public String list(Model model) {
        List<ApplicationDto> requests = applicationService.findDtosByStatuses(
                java.util.List.of(
                        jp.co.apsa.unryu.domain.valueobject.ApplicationStatus.PENDING,
                        jp.co.apsa.unryu.domain.valueobject.ApplicationStatus.IN_REVIEW,
                        jp.co.apsa.unryu.domain.valueobject.ApplicationStatus.APPROVED));
        model.addAttribute("title", "申請一覧");
        model.addAttribute("requests", requests);
        model.addAttribute("basePath", "/admin/requests/");
        return "request_view_list";
    }

}
