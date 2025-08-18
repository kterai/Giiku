package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.Lecture;
import jp.co.apsa.giiku.service.LectureService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 講義ページを表示するコントローラー。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/lecture")
public class LecturePageController extends AbstractController {

    /** 講義サービス */
    @Autowired
    private LectureService lectureService;

    /**
     * 指定された講義ページを表示します。
     *
     * @param page ページ名 (例: lecture1)
     * @return 対応するテンプレート
     */
    @GetMapping(path = {"/{page}","/{page}.html"})
    public String lecture(@PathVariable String page, Model model) {
        setTitle(model, "講義詳細");
        int lectureId = NumberUtils.toInt(page.replace("lecture", ""), 1);
        Lecture lecture = lectureService.findById((long) lectureId).orElse(null);
        String title = "Lecture " + lectureId;
        model.addAttribute("pageTitle", title);
        model.addAttribute("lecture", lecture);
        model.addAttribute("breadcrumb", List.of(
                Map.of("label", "ホーム", "href", "/"),
                Map.of("label", "講義", "href", "/lecture"),
                Map.of("label", title)
        ));
        return "lecture/detail";
    }
}
