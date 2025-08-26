package jp.co.apsa.giiku.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Map;

import jp.co.apsa.giiku.domain.entity.Day;
import jp.co.apsa.giiku.domain.entity.Lecture;
import jp.co.apsa.giiku.domain.entity.Month;
import jp.co.apsa.giiku.domain.entity.Week;
import jp.co.apsa.giiku.service.DayService;
import jp.co.apsa.giiku.service.LectureChapterService;
import jp.co.apsa.giiku.service.LectureGoalService;
import jp.co.apsa.giiku.service.LectureContentBlockService;
import jp.co.apsa.giiku.domain.entity.LectureChapter;
import jp.co.apsa.giiku.domain.entity.LectureGoal;
import jp.co.apsa.giiku.domain.entity.LectureContentBlock;
import jp.co.apsa.giiku.service.LectureService;
import jp.co.apsa.giiku.service.MonthService;
import jp.co.apsa.giiku.service.WeekService;

/**
 * 講義詳細ページを表示するコントローラー。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Controller
@RequestMapping("/lecture")
public class LectureViewController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LectureViewController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private LectureService lectureService;
    @Autowired
    private DayService dayService;
    @Autowired
    private WeekService weekService;
    @Autowired
    private MonthService monthService;

    @Autowired
    private LectureChapterService lectureChapterService;
    @Autowired
    private LectureGoalService lectureGoalService;
    @Autowired
    private LectureContentBlockService lectureContentBlockService;

    /**
     * 静的講義スライドを表示します。
     *
     * @param number 講義番号
     * @return テンプレート
     */
    @GetMapping("/lecture{number}.html")
    public String lectureSlide(@PathVariable Long number) {
        return "lecture/lecture" + number;
    }

    /**
     * 講義詳細を表示します。
     *
     * @param id 講義ID
     * @return テンプレート
     */
    @GetMapping("/{id}")
    public String lecture(@PathVariable Long id, Model model) {
        Lecture lecture = lectureService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        Day day = lecture.getDayId() != null ? dayService.findById(lecture.getDayId()).orElse(null) : null;
        Week week = day != null ? weekService.findById(day.getWeekId()).orElse(null) : null;
        Month month = week != null ? monthService.findById(week.getMonthId()).orElse(null) : null;

        setTitle(model, lecture.getTitle());
        model.addAttribute("pageTitle", lecture.getTitle());
        model.addAttribute("lecture", lecture);

        // 正規化されたテーブルからデータを取得
        logger.debug("=== Lecture Debug Info ===");
        logger.debug("Lecture ID: {}", lecture.getId());
        
        // 正規化テーブルからデータ取得
        List<LectureGoal> goals = lectureGoalService.findByLectureIdOrderBySortOrder(lecture.getId());
        List<LectureChapter> chapters = lectureChapterService.findByLectureIdOrderBySortOrder(lecture.getId());
        
        logger.debug("Found {} goals", goals.size());
        logger.debug("Found {} chapters", chapters.size());
        
        model.addAttribute("goals", goals);
        model.addAttribute("contentChapters", chapters);
        
        // 各チャプターのコンテンツブロックを取得
        Map<Long, List<LectureContentBlock>> chapterContentBlocks = new HashMap<>();
        for (LectureChapter chapter : chapters) {
            List<LectureContentBlock> blocks = lectureContentBlockService.findByChapterIdOrderBySortOrder(chapter.getId());
            chapterContentBlocks.put(chapter.getId(), blocks);
        }
        model.addAttribute("chapterContentBlocks", chapterContentBlocks);
        
        model.addAttribute("exercises", null);
        model.addAttribute("additionalResources", null);

        // JSONフィールドをパース
        logger.debug("=== Lecture Debug Info ===");
        logger.debug("Lecture ID: {}", lecture.getId());
        logger.debug("Goals JSON: '{}'", lecture.getGoals());
        logger.debug("Content Chapters JSON: '{}'", lecture.getContentChapters());
        logger.debug("Content Blocks JSON: '{}'", lecture.getContentBlocks());
        
        model.addAttribute("goals", parseJsonField(lecture.getGoals(), List.class));
        model.addAttribute("contentChapters", parseJsonField(lecture.getContentChapters(), List.class));
        model.addAttribute("contentBlocks", parseJsonField(lecture.getContentBlocks(), List.class));
        model.addAttribute("exercises", parseJsonField(getExercisesJson(lecture), List.class));
        model.addAttribute("additionalResources", parseJsonField(getAdditionalResourcesJson(lecture), List.class));
        
        // 前後の講義を取得
        setPreviousNextLectures(model, lecture);
        
        List<Map<String, Object>> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(Map.of("label", "ホーム", "url", "/", "last", false));
        if (month != null) {
            breadcrumbs.add(Map.of("label", month.getTitle(), "url", "/month/month" + month.getMonthNumber(), "last", false));
        }
        if (week != null) {
            breadcrumbs.add(Map.of("label", week.getWeekName(), "url", "/week/week" + week.getWeekNumber(), "last", false));
        }
        if (day != null) {
            breadcrumbs.add(Map.of("label", day.getDayName(), "url", "/day/day" + day.getDayNumber(), "last", false));
        }
        breadcrumbs.add(Map.of("label", lecture.getTitle(), "url", "/lecture/" + id, "last", true));
        model.addAttribute("breadcrumbs", breadcrumbs);
        
        return "lecture";
    }

    /**
     * JSONフィールドをパースして指定の型に変換
     */
    private <T> T parseJsonField(String jsonString, Class<T> targetType) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            logger.debug("JSON string is null or empty: '{}'", jsonString);
            return null;
        }
        try {
            return objectMapper.readValue(jsonString, targetType);
        } catch (Exception e) {
            logger.warn("Failed to parse JSON field: " + jsonString, e);
            return null;
        }
    }

    /**
     * 演習問題のJSONを取得（将来的にDBフィールドに追加予定）
     */
    private String getExercisesJson(Lecture lecture) {
        // 現時点では固定値、将来的にはlecture.getExercises()など
        return """
            [
                {
                    "title": "Javaの特徴理解",
                    "description": "以下の質問に答えてください：",
                    "questions": [
                        "Javaが「Write Once, Run Anywhere」と呼ばれる理由を、具体例を交えて説明してください。",
                        "JDK、JRE、JVMの違いと役割を説明してください。",
                        "なぜJavaでメモリ管理を意識する必要が少ないのか説明してください。"
                    ],
                    "answers": [
                        {
                            "question": "1. 「Write Once, Run Anywhere」の理由：",
                            "answer": "Javaコードは一度コンパイルするとバイトコードという中間言語に変換され、JVM（Java仮想マシン）上で実行される。JVMが各OS（Windows、Mac、Linux）に対応しているため、同じバイトコードがどのプラットフォームでも動作する。"
                        },
                        {
                            "question": "2. JDK、JRE、JVMの違い：",
                            "answer": "<ul><li><strong>JVM</strong>：Javaプログラムを実行する仮想マシン</li><li><strong>JRE</strong>：Java実行環境（JVM + ライブラリ）</li><li><strong>JDK</strong>：Java開発キット（JRE + 開発ツール）</li></ul>"
                        }
                    ]
                }
            ]
            """;
    }

    /**
     * 追加リソースのJSONを取得（将来的にDBフィールドに追加予定）
     */
    private String getAdditionalResourcesJson(Lecture lecture) {
        // 現時点では固定値、将来的にはlecture.getAdditionalResources()など
        return """
            [
                {
                    "title": "Oracle Java公式ドキュメント",
                    "description": "Java言語仕様とAPIドキュメント",
                    "url": "https://docs.oracle.com/javase/17/",
                    "linkText": "公式ドキュメントを見る"
                },
                {
                    "title": "Java Silver試験対策",
                    "description": "Oracle Certified Java Programmer Silver SE 17",
                    "url": "https://education.oracle.com/java-se-17-developer/pexam_1Z0-829",
                    "linkText": "試験詳細を確認"
                }
            ]
            """;
    }

    /**
     * 前後の講義を設定
     */
    private void setPreviousNextLectures(Model model, Lecture currentLecture) {
        try {
            // 同じ日の前後の講義を取得
            if (currentLecture.getDayId() != null) {
                List<Lecture> dayLectures = lectureService.findByDayIdOrderByLectureNumber(currentLecture.getDayId());
                
                for (int i = 0; i < dayLectures.size(); i++) {
                    if (dayLectures.get(i).getId().equals(currentLecture.getId())) {
                        if (i > 0) model.addAttribute("previousLecture", dayLectures.get(i - 1));
                        if (i < dayLectures.size() - 1) model.addAttribute("nextLecture", dayLectures.get(i + 1));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("Failed to set previous/next lectures", e);
        }
    }
}
