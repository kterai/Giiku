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

import jp.co.apsa.giiku.domain.entity.Month;
import jp.co.apsa.giiku.domain.entity.Week;
import jp.co.apsa.giiku.service.DayService;
import jp.co.apsa.giiku.service.MonthService;
import jp.co.apsa.giiku.service.WeekService;
/**
 * 週別ページを表示するコントローラー。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/week")
public class WeekController  extends AbstractController{

    /** 週サービス */
    @Autowired
    private WeekService weekService;

    /** 日サービス */
    @Autowired
    private DayService dayService;

    /** 月サービス */
    @Autowired
    private MonthService monthService;

    /**
     * 指定された週ページを表示します。
     *
     * @param page ページ名 (例: week1)
     * @return 対応するテンプレート
     */
    @GetMapping(path = {"/{page}","/{page}.html"})
    public String week(@PathVariable String page, Model model) {
        int weekNumber = NumberUtils.toInt(page.replace("week", ""), 1);
        Week week = weekService.findByWeekNumber(weekNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Month month = monthService.findById(week.getMonthId()).orElse(null);

        setTitle(model, week.getWeekName());
        model.addAttribute("pageTitle", week.getWeekName());
        model.addAttribute("week", week);
        model.addAttribute("days", dayService.findByWeekId(week.getId()));
        model.addAttribute("breadcrumbs", List.of(
                Map.of("label", "ホーム", "url", "/", "last", false),
                Map.of("label", month != null ? month.getTitle() : "", "url", month != null ? "/month/month" + month.getMonthNumber() : "", "last", false),
                Map.of("label", week.getWeekName(), "url", "/week/week" + weekNumber, "last", true)
        ));
        return "week/detail";
    }
}

