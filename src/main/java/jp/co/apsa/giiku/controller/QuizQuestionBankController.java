package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.QuizQuestionBank;
import jp.co.apsa.giiku.service.QuizQuestionBankService;
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
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuizQuestionBankController extends AbstractController {

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
        List<QuizQuestionBank> questions = quizQuestionBankService.findByLectureId(lectureId);
        return ResponseEntity.ok(questions);
    }
}
