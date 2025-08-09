package jp.co.apsa.giiku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 月別ページを表示するコントローラー。
 */
@Controller
@RequestMapping("/month")
public class MonthController {

    /**
     * 指定された月ページを表示します。
     *
     * @param page ページ名 (例: month1)
     * @return 対応するテンプレート
     */
    @GetMapping("/{page}")
    public String month(@PathVariable String page) {
        return "month/" + page;
    }
}

