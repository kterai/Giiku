package jp.co.apsa.giiku.controller.admin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import jp.co.apsa.giiku.controller.AbstractControllerTest;

/**
 * AdminRequestViewController の統合テスト。
 */
public class AdminRequestViewControllerTest extends AbstractControllerTest {

    @Test
    void listDisplaysForAdmin() throws Exception {
        mockMvc.perform(get("/admin/requests")
                .with(admin()))
                .andExpect(status().isOk())
                .andExpect(view().name("request_view_list"))
                .andExpect(model().attributeExists("requests"));
    }

    @Test
    void listForbiddenForUser() throws Exception {
        mockMvc.perform(get("/admin/requests")
                .with(user("user1").password("user1pass").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void detailDisplaysForAdmin() throws Exception {
        mockMvc.perform(get("/admin/requests/1")
                .with(admin()))
                .andExpect(status().isOk())
                .andExpect(view().name("request_view_detail"))
                .andExpect(model().attributeExists("request"));
    }
}
