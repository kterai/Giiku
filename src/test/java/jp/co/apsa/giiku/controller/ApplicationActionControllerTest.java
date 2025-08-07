package jp.co.apsa.giiku.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.junit.jupiter.api.Test;

/**
 * ApplicationActionController の統合テスト。
 */
public class ApplicationActionControllerTest extends AbstractControllerTest {

    @Test
    void withdrawUpdatesStatus() throws Exception {
        mockMvc.perform(post("/applications/5/withdraw")
                .with(user("user1").password("user123").roles("USER"))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("WITHDRAWN"));
    }
}
