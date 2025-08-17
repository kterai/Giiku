package jp.co.apsa.giiku.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * HTTPクライアント用のRestTemplateを提供する設定クラス。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Configuration
public class RestTemplateConfig {

    /**
     * RestTemplateのBeanを生成します。
     *
     * @return RestTemplateインスタンス
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
