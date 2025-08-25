package jp.co.apsa.giiku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.apsa.giiku.domain.entity.Day;
import jp.co.apsa.giiku.domain.entity.Lecture;
import jp.co.apsa.giiku.domain.entity.Month;
import jp.co.apsa.giiku.domain.entity.Week;
import jp.co.apsa.giiku.service.DayService;
import jp.co.apsa.giiku.service.LectureService;
import jp.co.apsa.giiku.service.MonthService;
import jp.co.apsa.giiku.service.WeekService;

/**
 * 講義詳細ページを表示するコントローラー。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/lecture")
public class LectureViewController extends AbstractController {

    @Autowired
    private LectureService lectureService;
    @Autowired
    private DayService dayService;
    @Autowired
    private WeekService weekService;
    @Autowired
    private MonthService monthService;

    /**
     * 講義詳細を表示します。
     *
     * @param id 講義ID
     * @return テンプレート
     */
    @GetMapping("/{id}")
    public String lecture(@PathVariable Long id, Model model) {
        Lecture lecture = lectureService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Day day = lecture.getDayId() != null ? dayService.findById(lecture.getDayId()).orElse(null) : null;
        Week week = day != null ? weekService.findById(day.getWeekId()).orElse(null) : null;
        Month month = week != null ? monthService.findById(week.getMonthId()).orElse(null) : null;

        setTitle(model, lecture.getTitle());
        model.addAttribute("pageTitle", lecture.getTitle());
        model.addAttribute("lecture", lecture);

        List<Map<String, Object>> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(Map.of("label", "ホーム", "url", "/", "last", false));
        if (month != null) {
            breadcrumbs.add(Map.of("label", month.getTitle(), "url", "/month/month" + month.getMonthNumber(), "last", false));
        }
        if (week != null) {
            breadcrumbs.add(Map.of("label", week.getWeekName(), "url", "/week/week" + week.getWeekNumber(), "last", false));
        }
        if (day != null) {
            breadcrumbs.add(Map.of("label", day.getDayName(), "url", "/day/day" + day.getDayNumber(), "last", false));
        }
        breadcrumbs.add(Map.of("label", lecture.getTitle(), "url", "/lecture/" + id, "last", true));
        model.addAttribute("breadcrumbs", breadcrumbs);
        return "lecture";
    }
}
