package jp.co.apsa.giiku.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import jp.co.apsa.giiku.controller.AbstractControllerTest;

/**
 * DashboardController の統合テスト。
 */
public class DashboardControllerTest extends AbstractControllerTest {

    @Test
    void unauthenticatedAccessRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void authenticatedAccessLoadsDashboard() throws Exception {
        mockMvc.perform(get("/dashboard")
                .with(admin()))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attributeExists("applications"))
                .andExpect(model().attributeExists("approvals"))
                .andExpect(model().attributeExists("chart"))
                .andExpect(model().attribute("title", "ダッシュボード"));
    }

    @Test
    void dashboardViewContainsHeading() throws Exception {
        mockMvc.perform(get("/dashboard")
                .with(admin()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("ダッシュボード")));
    }

    @Test
    void dashboardTableContainsExpectedColumns() throws Exception {
        mockMvc.perform(get("/dashboard")
                .with(SecurityMockMvcRequestPostProcessors.user("user1")
                        .password("user123")
                        .roles("USER")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("applications", org.hamcrest.Matchers.hasSize(5)))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("申請ID")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("申請種別")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("申請日")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("ステータス")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("出張申請")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("経費申請")));
    }
}
