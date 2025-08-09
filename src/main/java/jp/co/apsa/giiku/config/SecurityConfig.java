package jp.co.apsa.giiku.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import jp.co.apsa.giiku.application.service.UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 技育システム Spring Security設定クラス
 * 
 * Spring Security 6.x準拠のセキュリティ設定
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    /**
     * セキュリティフィルターチェーン設定
     * 
     * @param http HttpSecurity設定オブジェクト
     * @return SecurityFilterChain セキュリティフィルターチェーン
     * @throws Exception 設定エラー
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .userDetailsService(userService)
            // CSRF設定
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(new AntPathRequestMatcher("/api/**")) // API エンドポイントはCSRF無効
            )

            // 認証設定
            .authorizeHttpRequests(authz -> authz
                // ログイン関連は認証不要
                .requestMatchers(
                    new AntPathRequestMatcher("/login"),
                    new AntPathRequestMatcher("/login-error"),
                    new AntPathRequestMatcher("/logout"),
                    new AntPathRequestMatcher("/error")
                ).permitAll()

                // 管理者機能は管理者権限必要
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")

                // その他は認証必要
                .anyRequest().authenticated()
            )

            // ログイン設定
            .formLogin(form -> form
                .loginPage("/login")                    // カスタムログインページ
                .loginProcessingUrl("/login-process")   // ログイン処理URL
                .usernameParameter("username")          // ユーザー名パラメータ
                .passwordParameter("password")          // パスワードパラメータ
                .defaultSuccessUrl("/dashboard", true)       // ログイン成功時のリダイレクト先
                .failureUrl("/login-error")             // ログイン失敗時のリダイレクト先
                .permitAll()
            )

            // ログアウト設定
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")      // ログアウト成功時のリダイレクト先
                .invalidateHttpSession(true)            // セッション無効化
                .deleteCookies("JSESSIONID")            // Cookie削除
                .permitAll()
            )

            // セッション管理
            .sessionManagement(session -> session
                .maximumSessions(1)                     // 同時セッション数制限
                .maxSessionsPreventsLogin(false)        // 新しいログインを優先
            );

        return http.build();
    }

    /**
     * 静的リソースをセキュリティ対象外にする設定
     *
     * @return WebSecurityCustomizer 静的リソース除外設定
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     * パスワードエンコーダー設定
     * 
     * @return PasswordEncoder BCryptパスワードエンコーダー
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
