package jp.co.apsa.unryu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 雲流システム (Unryu System) メインアプリケーションクラス
 * 
 * Spring Boot 3.1.3ベースのエンタープライズアプリケーション
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@SpringBootApplication(scanBasePackages = "jp.co.apsa.unryu")
@EnableJpaRepositories(basePackages = "jp.co.apsa.unryu.domain.repository")
@EntityScan(basePackages = "jp.co.apsa.unryu.domain.entity")
@EnableTransactionManagement
public class UnryuSystemApplication {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(UnryuSystemApplication.class);

    /**
     * アプリケーションエントリーポイント
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        // システム起動ログ
        logger.info("=".repeat(50));
        logger.info("雲流システム (Unryu System) 起動中...");
        logger.info("Spring Boot Version: 3.1.3");
        logger.info("Package: jp.co.apsa.unryu");
        logger.info("=".repeat(50));

        try {
            // Spring Boot アプリケーション起動
            SpringApplication.run(UnryuSystemApplication.class, args);

            logger.info("✅ 雲流システム起動完了");

        } catch (Exception e) {
            logger.error("システム起動エラー", e);
            System.exit(1);
        }
    }
}
