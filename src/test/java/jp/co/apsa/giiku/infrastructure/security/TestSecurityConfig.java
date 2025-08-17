package jp.co.apsa.giiku.infrastructure.security;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * テスト用セキュリティ設定クラス
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@TestConfiguration
@EnableWebSecurity
public class TestSecurityConfig {

    /**
     * filterChain メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll()
            )
            .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
