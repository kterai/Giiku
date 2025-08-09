package jp.co.apsa.giiku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 週別ページを表示するコントローラー。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/week")
public class WeekController {

    /**
     * 指定された週ページを表示します。
     *
     * @param page ページ名 (例: week1)
     * @return 対応するテンプレート
     */
    @GetMapping("/{page}")
    public String week(@PathVariable String page) {
        return "week/" + page;
    }
}

