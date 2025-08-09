package jp.co.apsa.giiku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 講義ページを表示するコントローラー。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/lecture")
public class LectureController extends AbstractController {

    /**
     * 指定された講義ページを表示します。
     *
     * @param page ページ名 (例: day1_lecture)
     * @return 対応するテンプレート
     */
    @GetMapping( path = {"/{page}","/{page}.html"})
    public String lecture(@PathVariable String page) {
        return "lecture/" + page;
    }
}

