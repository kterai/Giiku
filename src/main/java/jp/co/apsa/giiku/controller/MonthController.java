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
import jp.co.apsa.giiku.service.MonthService;
import jp.co.apsa.giiku.service.WeekService;
/**
 * 月別ページを表示するコントローラー。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/month")
public class MonthController  extends AbstractController{

    /** 月サービス */
    @Autowired
    private MonthService monthService;

    /** 週サービス */
    @Autowired
    private WeekService weekService;

    /**
     * 指定された月ページを表示します。
     *
     * @param page ページ名 (例: month1)
     * @return 対応するテンプレート
     */
    @GetMapping(path = {"/{page}","/{page}.html"})
    public String month(@PathVariable String page, Model model) {
        int monthNumber = NumberUtils.toInt(page.replace("month", ""), 1);
        Month month = monthService.findByMonthNumber(monthNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        setTitle(model, month.getTitle());
        model.addAttribute("pageTitle", month.getTitle());
        model.addAttribute("month", month);
        model.addAttribute("weeks", weekService.findByMonthId(month.getId()));
        model.addAttribute("breadcrumbs", List.of(
                Map.of("label", "ホーム", "url", "/", "last", false),
                Map.of("label", month.getTitle(), "url", "/month/month" + monthNumber, "last", true)
        ));
        return "month/detail";
    }
}

