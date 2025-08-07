package jp.co.apsa.giiku.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.junit.jupiter.api.Test;

/**
 * RequestViewController の統合テスト。
 */
public class RequestViewControllerTest extends AbstractControllerTest {

    @Test
    void detailDisplaysForUser() throws Exception {
        mockMvc.perform(get("/requests/1")
                .with(user("user1").password("user123").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("request_view_detail"))
                .andExpect(model().attributeExists("request"))
                .andExpect(model().attribute("canWithdraw", false));
    }

    @Test
    void withdrawAllowedForOwnUnapprovedRequest() throws Exception {
        mockMvc.perform(get("/requests/5")
                .with(user("user1").password("user123").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("request_view_detail"))
                .andExpect(model().attribute("canWithdraw", true));
    }
}
