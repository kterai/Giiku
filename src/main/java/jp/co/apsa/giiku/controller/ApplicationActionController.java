package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.application.dto.ApplicationDto;
import jp.co.apsa.giiku.application.service.ApplicationService;
import jp.co.apsa.giiku.domain.entity.User;
import jp.co.apsa.giiku.domain.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 申請操作を提供するコントローラー。
 *
 * <p>申請の取下げなど、共通の操作を扱います。</p>
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/applications")
public class ApplicationActionController {

    private final ApplicationService applicationService;
    private final UserRepository userRepository;

    public ApplicationActionController(ApplicationService applicationService,
                                       UserRepository userRepository) {
        this.applicationService = applicationService;
        this.userRepository = userRepository;
    }

    /**
     * 申請を取下げます。
     *
     * @param id 申請ID
     * @return 更新後の申請DTO
     */
    @PostMapping("/{id}/withdraw")
    @ResponseBody
    public ApplicationDto withdraw(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElse(null);
        if (user == null) {
            return null;
        }
        applicationService.withdrawApplication(id, user.getId());
        return applicationService.findDtoById(id);
    }
}
