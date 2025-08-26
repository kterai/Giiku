package jp.co.apsa.giiku.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import jp.co.apsa.giiku.service.MonthService;
import jp.co.apsa.giiku.service.WeekService;
import jp.co.apsa.giiku.service.DayService;
import org.springframework.test.web.servlet.MockMvc;

import jp.co.apsa.giiku.domain.entity.Week;
import jp.co.apsa.giiku.domain.entity.Month;
import java.util.Collections;
import java.util.Optional;

/**
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@WebMvcTest(controllers = WeekController.class)
@AutoConfigureMockMvc(addFilters = false)
@org.springframework.test.context.ContextConfiguration(classes = WeekControllerTest.TestConfig.class)
class WeekControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonthService monthService;

    @MockBean
    private WeekService weekService;

    @MockBean
    private DayService dayService;

    @org.springframework.boot.autoconfigure.SpringBootApplication(
        exclude = {
            org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
            org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
            org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration.class
        }
    )
    static class TestConfig {
    }

    @Test
    void weekPageReturnsView() throws Exception {
        Week week = new Week();
        week.setId(1L);
        week.setMonthId(1L);
        week.setWeekNumber(1);
        week.setWeekName("Week1");

        Month month = new Month();
        month.setId(1L);
        month.setMonthNumber(1);
        month.setTitle("Month1");

        when(weekService.findByWeekNumber(1)).thenReturn(Optional.of(week));
        when(monthService.findById(1L)).thenReturn(Optional.of(month));
        when(dayService.findByWeekId(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/week/week1"))
                .andExpect(status().isOk())
                .andExpect(view().name("week"));
    }
}

