package jp.co.apsa.giiku.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@WebMvcTest(controllers = WeekController.class)
@AutoConfigureMockMvc(addFilters = false)
@org.springframework.test.context.ContextConfiguration(classes = WeekControllerTest.TestConfig.class)
class WeekControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @org.springframework.boot.autoconfigure.SpringBootApplication(
        exclude = {
            org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
            org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
            org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.class
        }
    )
    static class TestConfig {
    }

    @Test
    void weekPageReturnsView() throws Exception {
        mockMvc.perform(get("/week/week1"))
                .andExpect(status().isOk())
                .andExpect(view().name("week/week1"));
    }
}

