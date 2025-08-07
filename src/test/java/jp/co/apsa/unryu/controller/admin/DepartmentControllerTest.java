package jp.co.apsa.unryu.controller.admin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import jp.co.apsa.unryu.controller.AbstractControllerTest;

/**
 * DepartmentController の統合テスト。
 */
public class DepartmentControllerTest extends AbstractControllerTest {

    @Test
    void listDisplaysForAdmin() throws Exception {
        mockMvc.perform(get("/admin/departments")
                .with(admin()))
                .andExpect(status().isOk())
                .andExpect(view().name("department_list"))
                .andExpect(model().attributeExists("departments"));
    }

    @Test
    void deleteFailWithReferences() throws Exception {
        mockMvc.perform(post("/admin/departments/1/delete")
                .with(admin())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("department_detail"))
                .andExpect(model().attributeExists("deleteError"));
    }
}
