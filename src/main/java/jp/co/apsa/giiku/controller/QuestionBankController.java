package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.QuestionBank;
import jp.co.apsa.giiku.service.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * 問題銀行コントローラー
 *
 * 基本的な問題管理のREST APIを提供します。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api/question-banks")
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuestionBankController extends AbstractController {

    @Autowired
    private QuestionBankService questionBankService;

    /** 問題一覧をページング形式で取得 */
    @GetMapping
    public ResponseEntity<Page<QuestionBank>> getAllQuestions(Pageable pageable) {
        Page<QuestionBank> questions = questionBankService.findAll(pageable);
        return ResponseEntity.ok(questions);
    }

    /** 問題IDで問題詳細を取得 */
    @GetMapping("/{id}")
    public ResponseEntity<QuestionBank> getQuestionById(@PathVariable Long id) {
        Optional<QuestionBank> question = questionBankService.findById(id);
        return question.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /** 新しい問題を作成 */
    @PostMapping
    public ResponseEntity<QuestionBank> createQuestion(@Valid @RequestBody QuestionBank questionBank) {
        QuestionBank saved = questionBankService.save(questionBank);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** 問題情報を更新 */
    @PutMapping("/{id}")
    public ResponseEntity<QuestionBank> updateQuestion(@PathVariable Long id,
                                                       @Valid @RequestBody QuestionBank questionBank) {
        questionBank.setId(id);
        QuestionBank updated = questionBankService.update(id, questionBank);
        return ResponseEntity.ok(updated);
    }

    /** 問題を削除（物理削除） */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionBankService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** 問題を無効化（論理削除） */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateQuestion(@PathVariable Long id) {
        questionBankService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    /** 問題文の部分一致検索 */
    @GetMapping("/search")
    public ResponseEntity<List<QuestionBank>> searchQuestions(@RequestParam String query) {
        List<QuestionBank> questions = questionBankService.searchByQuestionText(query);
        return ResponseEntity.ok(questions);
    }

    /** チャプターIDで問題を取得 */
    @GetMapping("/chapter/{chapterId}")
    public ResponseEntity<List<QuestionBank>> getByChapter(@PathVariable Long chapterId) {
        List<QuestionBank> questions = questionBankService.findByChapterIdOrderByQuestionNumber(chapterId);
        return ResponseEntity.ok(questions);
    }

    /** 講義IDで問題を取得 */
    @GetMapping("/lecture/{lectureId}")
    public ResponseEntity<List<QuestionBank>> getByLecture(@PathVariable Long lectureId) {
        List<QuestionBank> questions = questionBankService.findByLectureId(lectureId);
        return ResponseEntity.ok(questions);
    }
}
