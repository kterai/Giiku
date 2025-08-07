package jp.co.apsa.unryu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.AfterAll;

/**
 * コントローラーテストの共通基盤を提供します。
 * テストコンテナの設定およびログインヘルパーをまとめます。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("autotest")
@Sql(scripts = "classpath:db/testdata/autotest_sample_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/testdata/autotest_sample_data_cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public abstract class AbstractControllerTest {

    private static final boolean USE_EXTERNAL_DB = System.getenv("DATABASE_URL") != null;

    static PostgreSQLContainer<?> postgres;

    static {
        if (!USE_EXTERNAL_DB) {
            postgres = new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("unryu_db_autotest")
                    .withUsername("postgres")
                    .withPassword("postgres");
            postgres.start();
        }
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        if (USE_EXTERNAL_DB) {
            registry.add("spring.datasource.url", () -> System.getenv("DATABASE_URL"));
            registry.add("spring.datasource.username", () -> System.getenv().getOrDefault("DATABASE_USERNAME", "postgres"));
            registry.add("spring.datasource.password", () -> System.getenv().getOrDefault("DATABASE_PASSWORD", "postgres"));
            registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        } else {
            registry.add("spring.datasource.url", postgres::getJdbcUrl);
            registry.add("spring.datasource.username", postgres::getUsername);
            registry.add("spring.datasource.password", postgres::getPassword);
            registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
        }
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
        registry.add("spring.flyway.enabled", () -> "true");
    }

    @AfterAll
    static void stopContainer() {
        if (postgres != null) {
            postgres.stop();
        }
    }

    @Autowired
    protected MockMvc mockMvc;

    /**
     * 管理者ユーザーとして認証するヘルパーを返します。
     *
     * @return 管理者ユーザーのリクエストポストプロセッサ
     */
    protected SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor admin() {
        return SecurityMockMvcRequestPostProcessors.user("admin")
                .password("admin123")
                .roles("ADMIN");
    }
}
