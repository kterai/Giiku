package jp.co.apsa.giiku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 日別ページを表示するコントローラー。
 */
@Controller
@RequestMapping("/day")
public class DayController {

    /**
     * 指定された日付ページを表示します。
     *
     * @param page ページ名 (例: day1)
     * @return 対応するテンプレート
     */
    @GetMapping("/{page}")
    public String day(@PathVariable String page) {
        return "day/" + page;
    }
}

