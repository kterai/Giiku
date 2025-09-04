package jp.co.apsa.giiku.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * アプリケーション設定クラス
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    /** addCorsMappings メソッド */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // すべてのオリジンを許可
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
