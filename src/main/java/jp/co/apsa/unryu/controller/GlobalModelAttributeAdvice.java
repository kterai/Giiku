package jp.co.apsa.unryu.controller;

import jp.co.apsa.unryu.application.service.ApplicationTypeService;
import jp.co.apsa.unryu.domain.entity.ApplicationType;
import jp.co.apsa.unryu.domain.entity.User;
import jp.co.apsa.unryu.domain.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * 画面共通のモデル属性を提供するアドバイス。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@ControllerAdvice
public class GlobalModelAttributeAdvice {

    private final ApplicationTypeService applicationTypeService;
    private final UserRepository userRepository;

    public GlobalModelAttributeAdvice(ApplicationTypeService applicationTypeService,
                                      UserRepository userRepository) {
        this.applicationTypeService = applicationTypeService;
        this.userRepository = userRepository;
    }

    /**
     * ナビゲーション表示用の申請種別リストを取得します。
     */
    @ModelAttribute("activeApplicationTypes")
    public List<ApplicationType> populateApplicationTypes() {
        return applicationTypeService.findAll();
    }

    /**
     * ログインユーザーの表示名を取得します。
     */
    @ModelAttribute("currentUserDisplayName")
    public String populateCurrentUserDisplayName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return "";
        }
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            return user.getName() + "(" + user.getUsername() + ")";
        }
        return username;
    }
}
