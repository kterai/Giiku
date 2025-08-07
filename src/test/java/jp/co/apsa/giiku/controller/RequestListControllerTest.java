package jp.co.apsa.giiku.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.junit.jupiter.api.Test;

/**
 * RequestListController の統合テスト。
 */
public class RequestListControllerTest extends AbstractControllerTest {

    @Test
    void listDisplaysForUser() throws Exception {
        mockMvc.perform(get("/requests").with(user("user1").password("user123").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("request_view_list"))
                .andExpect(model().attributeExists("requests"));
    }
}
