package jp.co.apsa.giiku.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.AuditorAware;

import jp.co.apsa.giiku.domain.entity.User;
import jp.co.apsa.giiku.domain.repository.UserRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 認証情報から監査ユーザーIDを取得する実装クラス。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Component
public class SecurityAuditorAware implements AuditorAware<Long> {

    private final UserRepository userRepository;

    @Autowired
    public SecurityAuditorAware(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        String username = authentication.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.map(User::getId);
    }
}
