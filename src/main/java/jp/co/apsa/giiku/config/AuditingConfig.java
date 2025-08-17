package jp.co.apsa.giiku.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPAオーディティングを有効にする設定クラス。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "securityAuditorAware")
public class AuditingConfig {
}
