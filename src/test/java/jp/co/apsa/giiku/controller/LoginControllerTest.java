package jp.co.apsa.giiku.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import jp.co.apsa.giiku.controller.AbstractControllerTest;

/**
 * LoginController の統合テスト。
 */
public class LoginControllerTest extends AbstractControllerTest {

    @Test
    void loginPageDisplays() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void loginSuccessRedirectsToDashboard() throws Exception {
        mockMvc.perform(formLogin().loginProcessingUrl("/login-process")
                .user("username", "admin")
                .password("password", "admin123"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/dashboard"));
    }

    @Test
    void loginFailureShowsError() throws Exception {
        mockMvc.perform(formLogin().loginProcessingUrl("/login-process")
                .user("username", "admin")
                .password("password", "wrong"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/login-error"));

        mockMvc.perform(get("/login-error"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("loginError", true));
    }
}
