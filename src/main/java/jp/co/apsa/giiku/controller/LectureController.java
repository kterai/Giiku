package jp.co.apsa.giiku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 講義ページを表示するコントローラー。
 */
@Controller
@RequestMapping("/lecture")
public class LectureController {

    /**
     * 指定された講義ページを表示します。
     *
     * @param page ページ名 (例: day1_lecture)
     * @return 対応するテンプレート
     */
    @GetMapping("/{page}")
    public String lecture(@PathVariable String page) {
        return "lecture/" + page;
    }
}

