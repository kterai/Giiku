package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.QuizQuestionBank;
import jp.co.apsa.giiku.service.QuizQuestionBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * クイズ問題バンクコントローラー
 *
 * クイズ問題の取得APIを提供します。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api/quiz-question-banks")
public class QuizQuestionBankController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(QuizQuestionBankController.class);

    @Autowired
    private QuizQuestionBankService quizQuestionBankService;

    /** チャプターIDでクイズ問題を取得 */
    @GetMapping("/chapter/{chapterId}")
    public ResponseEntity<List<QuizQuestionBank>> getByChapter(@PathVariable Long chapterId) {
        List<QuizQuestionBank> questions = quizQuestionBankService.findByChapterId(chapterId);
        return ResponseEntity.ok(questions);
    }

    /** 講義IDでクイズ問題を取得 */
    @GetMapping("/lecture/{lectureId}")
    public ResponseEntity<List<QuizQuestionBank>> getByLecture(@PathVariable Long lectureId) {
        logger.debug("Fetching quiz questions via API for lectureId={}", lectureId);
        List<QuizQuestionBank> questions = quizQuestionBankService.findByLectureId(lectureId);
        logger.debug("Returning {} quiz questions for lectureId={}", questions.size(), lectureId);
        return ResponseEntity.ok(questions);
    }
}
