package jp.co.apsa.unryu.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import jp.co.apsa.unryu.config.SecurityConfig;
import jp.co.apsa.unryu.application.dto.ChartDto;
import jp.co.apsa.unryu.application.service.ApplicationTypeService;
import jp.co.apsa.unryu.application.service.DashboardService;
import jp.co.apsa.unryu.application.service.UserService;
import jp.co.apsa.unryu.domain.entity.User;
import jp.co.apsa.unryu.domain.repository.UserRepository;

import static org.mockito.Mockito.when;

/**
 * ダッシュボードメニュー表示のWebMvcテスト。
 */
@WebMvcTest(controllers = DashboardController.class)
@ContextConfiguration(classes = DashboardMenuViewTest.TestConfig.class)
public class DashboardMenuViewTest {

    @Configuration
    @Import({SecurityConfig.class, DashboardController.class, ThymeleafAutoConfiguration.class})
    static class TestConfig {
    }

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DashboardService dashboardService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    UserService userService;
    @MockBean
    ApplicationTypeService applicationTypeService;

    @Test
    void adminMenuHiddenForUserRole() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setName("ユーザー");
        user.setRole("USER");
        user.setApprover(false);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(applicationTypeService.findAll()).thenReturn(Collections.emptyList());
        when(dashboardService.findRecentApplications(1L)).thenReturn(Collections.emptyList());
        when(dashboardService.getMonthlyApplicationChart(1L)).thenReturn(ChartDto.builder().labels(Collections.emptyList()).data(Collections.emptyList()).build());

        mockMvc.perform(get("/dashboard").with(user("user1").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("マスタメンテナンス"))));
    }

    @Test
    void adminMenuVisibleForAdminRole() throws Exception {
        User admin = new User();
        admin.setId(2L);
        admin.setUsername("admin");
        admin.setName("管理者");
        admin.setRole("ADMIN");
        admin.setApprover(false);

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        when(applicationTypeService.findAll()).thenReturn(Collections.emptyList());
        when(dashboardService.findRecentApplications(2L)).thenReturn(Collections.emptyList());
        when(dashboardService.getMonthlyApplicationChart(2L)).thenReturn(ChartDto.builder().labels(Collections.emptyList()).data(Collections.emptyList()).build());

        mockMvc.perform(get("/dashboard").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("マスタメンテナンス")));
    }
}
