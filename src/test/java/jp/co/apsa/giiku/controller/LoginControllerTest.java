package jp.co.apsa.giiku.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import jp.co.apsa.giiku.service.MonthService;
import jp.co.apsa.giiku.service.WeekService;
import jp.co.apsa.giiku.service.DayService;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@WebMvcTest(controllers = LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
@org.springframework.test.context.ContextConfiguration(classes = LoginControllerTest.TestConfig.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonthService monthService;

    @MockBean
    private WeekService weekService;

    @MockBean
    private DayService dayService;

    private CsrfToken csrfToken() {
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "token");
    }

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
    void rootRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void loginDisplaysPage() throws Exception {
        CsrfToken token = csrfToken();
        mockMvc.perform(get("/login")
                .requestAttr(CsrfToken.class.getName(), token)
                .requestAttr("_csrf", token))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void loginWithLogoutAddsAttribute() throws Exception {
        CsrfToken token = csrfToken();
        mockMvc.perform(get("/login").param("logout", "")
                .requestAttr(CsrfToken.class.getName(), token)
                .requestAttr("_csrf", token))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("logout"));
    }

    @Test
    void loginErrorAddsAttribute() throws Exception {
        CsrfToken token = csrfToken();
        mockMvc.perform(get("/login-error")
                .requestAttr(CsrfToken.class.getName(), token)
                .requestAttr("_csrf", token))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("loginError", true));
    }
}
