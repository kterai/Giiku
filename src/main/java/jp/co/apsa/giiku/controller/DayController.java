package jp.co.apsa.giiku.controller;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import jp.co.apsa.giiku.domain.entity.Day;
import jp.co.apsa.giiku.domain.entity.Month;
import jp.co.apsa.giiku.domain.entity.Week;
import jp.co.apsa.giiku.service.DayService;
import jp.co.apsa.giiku.service.LectureService;
import jp.co.apsa.giiku.service.MonthService;
import jp.co.apsa.giiku.service.WeekService;
/**
 * 日別ページを表示するコントローラー。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/day")
public class DayController extends AbstractController {

    /** 日サービス */
    @Autowired
    private DayService dayService;

    /** 週サービス */
    @Autowired
    private WeekService weekService;

    /** 月サービス */
    @Autowired
    private MonthService monthService;

    /** 講義サービス */
    @Autowired
    private LectureService lectureService;

    /**
     * 指定された日付ページを表示します。
     *
     * @param page ページ名 (例: day1)
     * @return 対応するテンプレート
     */
    @GetMapping(path = {"/{page}","/{page}.html"})
    public String day(@PathVariable String page, Model model) {
        int dayNumber = NumberUtils.toInt(page.replace("day", ""), 1);
        Day day = dayService.findByDayNumber(dayNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Week week = weekService.findById(day.getWeekId()).orElse(null);
        Month month = week != null ? monthService.findById(week.getMonthId()).orElse(null) : null;

        setTitle(model, day.getDayName());
        model.addAttribute("pageTitle", day.getDayName());
        model.addAttribute("day", day);
        model.addAttribute("lectures", lectureService.findByDayId(day.getId()));
        model.addAttribute("breadcrumbs", List.of(
                Map.of("label", "ホーム", "url", "/", "last", false),
                Map.of("label", month != null ? month.getTitle() : "", "url", month != null ? "/month/month" + month.getMonthNumber() : "", "last", false),
                Map.of("label", week != null ? week.getWeekName() : "", "url", week != null ? "/week/week" + week.getWeekNumber() : "", "last", false),
                Map.of("label", day.getDayName(), "url", "/day/day" + dayNumber, "last", true)
        ));
        return "day";
    }
}

