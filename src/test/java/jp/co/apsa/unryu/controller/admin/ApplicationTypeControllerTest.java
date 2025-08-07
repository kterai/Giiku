package jp.co.apsa.unryu.controller.admin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import jp.co.apsa.unryu.controller.AbstractControllerTest;

/**
 * ApplicationTypeController の統合テスト。
 */
public class ApplicationTypeControllerTest extends AbstractControllerTest {

    @Test
    void listDisplaysForAdmin() throws Exception {
        mockMvc.perform(get("/admin/application-types")
                .with(admin()))
                .andExpect(status().isOk())
                .andExpect(view().name("application_type_list"))
                .andExpect(model().attributeExists("types"));
    }

    @Test
    void createAndDeleteFailWithReferences() throws Exception {
        mockMvc.perform(post("/admin/application-types")
                .with(admin())
                .with(csrf())
                .param("name", "テスト種別")
                .param("code", "TEST"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/application-types"));

        mockMvc.perform(post("/admin/application-types/1/delete")
                .with(admin())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("application_type_detail"))
                .andExpect(model().attributeExists("deleteError"));
    }
}
