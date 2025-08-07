package jp.co.apsa.giiku.controller.admin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import jp.co.apsa.giiku.controller.AbstractControllerTest;

/**
 * PositionController の統合テスト。
 */
public class PositionControllerTest extends AbstractControllerTest {

    @Test
    void listDisplaysForAdmin() throws Exception {
        mockMvc.perform(get("/admin/positions")
                .with(admin()))
                .andExpect(status().isOk())
                .andExpect(view().name("position_list"))
                .andExpect(model().attributeExists("positions"));
    }

    @Test
    void deleteFailWithReferences() throws Exception {
        mockMvc.perform(post("/admin/positions/1/delete")
                .with(admin())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("position_detail"))
                .andExpect(model().attributeExists("deleteError"));
    }
}
