package jp.co.apsa.giiku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 月別ページを表示するコントローラー。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/month")
public class MonthController  extends AbstractController{

    /**
     * 指定された月ページを表示します。
     *
     * @param page ページ名 (例: month1)
     * @return 対応するテンプレート
     */
    @GetMapping(path = {"/{page}","/{page}.html"})
    public String month(@PathVariable String page, Model model) {
        setTitle(model, "月別カリキュラム");
        String title = "Month " + page.replace("month", "");
        model.addAttribute("pageTitle", title);
        model.addAttribute("breadcrumb", List.of(
                Map.of("label", "Home", "href", "/"),
                Map.of("label", "Month", "href", "/month"),
                Map.of("label", title)
        ));
        model.addAttribute("tabs", List.of(
                Map.of("label", "Tab1", "href", "#", "active", true),
                Map.of("label", "Tab2", "href", "#", "active", false)
        ));
        return "month/" + page;
    }
}

