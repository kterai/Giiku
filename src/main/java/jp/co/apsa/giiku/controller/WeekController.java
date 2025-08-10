package jp.co.apsa.giiku.controller;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 週別ページを表示するコントローラー。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/week")
public class WeekController  extends AbstractController{

    /**
     * 指定された週ページを表示します。
     *
     * @param page ページ名 (例: week1)
     * @return 対応するテンプレート
     */
    @GetMapping(path = {"/{page}","/{page}.html"})
    public String week(@PathVariable String page, Model model) {
        setTitle(model, "週別詳細");
        String title = "第" + page.replace("week", "") + "週目";
        int monthNumber = ((NumberUtils.toInt(page.replace("week", ""), 1)) / 4 + 1);
        String monthTitle = "第" + monthNumber + "ヶ月目";
        model.addAttribute("pageTitle", title);
        model.addAttribute("breadcrumb", List.of(
                Map.of("label", "ホーム", "href", "/"),
                Map.of("label", monthTitle, "href", "/month" + monthNumber),
                Map.of("label", title)
        ));
        return "week/" + page;
    }
}

