package jp.co.apsa.giiku.controller.admin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import jp.co.apsa.giiku.controller.AbstractControllerTest;

/**
 * ApprovalRouteController の統合テスト。
 */
public class ApprovalRouteControllerTest extends AbstractControllerTest {

    @Test
    void listDisplaysForAdmin() throws Exception {
        mockMvc.perform(get("/admin/application-types/1/routes")
                .with(admin()))
                .andExpect(status().isOk())
                .andExpect(view().name("approval_route_list"))
                .andExpect(model().attributeExists("routes"));
    }
}
