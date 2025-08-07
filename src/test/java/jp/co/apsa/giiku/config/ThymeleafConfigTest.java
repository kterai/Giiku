package jp.co.apsa.giiku.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

/**
 * Thymeleaf設定のテスト。
 *
 * SpringSecurityDialect のBeanが存在することを検証する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:thymeleaf;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.flyway.enabled=false"
})
public class ThymeleafConfigTest {

    @Autowired
    ApplicationContext context;

    @Test
    void securityDialectBeanExists() {
        assertNotNull(context.getBean(SpringSecurityDialect.class));
    }
}
