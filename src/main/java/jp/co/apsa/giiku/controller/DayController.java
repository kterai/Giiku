package jp.co.apsa.giiku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

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

    /**
     * 指定された日付ページを表示します。
     *
     * @param page ページ名 (例: day1)
     * @return 対応するテンプレート
     */
    @GetMapping(path = {"/{page}","/{page}.html"})
    public String day(@PathVariable String page, Model model) {
        setTitle(model, "日別カリキュラム");
        String title = "Day " + page.replace("day", "");
        model.addAttribute("pageTitle", title);
        model.addAttribute("breadcrumb", List.of(
                Map.of("label", "Home", "href", "/"),
                Map.of("label", "Day", "href", "/day"),
                Map.of("label", title)
        ));
        return "day/" + page;
    }
}

