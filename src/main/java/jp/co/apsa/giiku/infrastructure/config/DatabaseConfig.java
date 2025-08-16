package jp.co.apsa.giiku.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * データベース設定クラス
 */
@Configuration
@EnableJpaRepositories(basePackages = "jp.co.apsa.giiku.domain.repository")
@EnableTransactionManagement
public class DatabaseConfig {

    // データベース設定をここに追加

}
