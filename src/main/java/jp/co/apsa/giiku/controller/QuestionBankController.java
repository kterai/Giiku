package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.QuestionBank;
import jp.co.apsa.giiku.dto.ExerciseAnswer;
import jp.co.apsa.giiku.service.QuestionBankService;
import jp.co.apsa.giiku.service.StudentAnswerService;
import jp.co.apsa.giiku.service.LectureGradeService;
import jp.co.apsa.giiku.domain.entity.StudentAnswer;
import jp.co.apsa.giiku.domain.entity.User;
import jp.co.apsa.giiku.domain.repository.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
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
public class QuestionBankController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionBankController.class);

    @Autowired
    private QuestionBankService questionBankService;

    @Autowired
    private StudentAnswerService studentAnswerService;

    @Autowired
    private LectureGradeService lectureGradeService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserRepository userRepository;

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
        logger.debug("Fetching questions via API for lectureId={}", lectureId);
        List<QuestionBank> questions = questionBankService.findByLectureId(lectureId);
        logger.debug("Returning {} questions for lectureId={}", questions.size(), lectureId);
        return ResponseEntity.ok(questions);
    }

    /**
     * 演習問題の回答を保存
     *
     * @param id 問題ID
     * @param exerciseAnswer 回答情報
     * @return レスポンス
     */
    @PostMapping("/{id}/answer")
    public ResponseEntity<Void> saveExerciseAnswer(@PathVariable Long id,
                                                   @RequestBody ExerciseAnswer exerciseAnswer) {
        logger.debug("Saving exercise answer: questionId={} studentId={}", id, exerciseAnswer.getStudentId());
        studentAnswerService.saveExerciseAnswer(id, exerciseAnswer.getStudentId(), exerciseAnswer.getAnswerText());
        if (exerciseAnswer.getLectureId() != null && exerciseAnswer.getCorrect() != null) {
            lectureGradeService.updateExerciseStats(exerciseAnswer.getLectureId(), exerciseAnswer.getCorrect());
        }
        messagingTemplate.convertAndSend("/topic/exercise-answers/" + id, exerciseAnswer);
        return ResponseEntity.ok().build();
    }

    /**
     * 質問ID別の学生回答一覧を取得
     *
     * @param id 質問ID
     * @return 学生名と回答内容の一覧
     */
    @GetMapping("/{id}/answers")
    public ResponseEntity<List<Map<String, String>>> getExerciseAnswers(@PathVariable Long id) {
        List<StudentAnswer> answers = studentAnswerService.getAnswersByQuestionId(id);
        List<Map<String, String>> result = answers.stream()
                .map(a -> {
                    String name = userRepository.findById(a.getStudentId())
                            .map(User::getName)
                            .orElse("不明");
                    Map<String, String> map = new HashMap<>();
                    map.put("studentName", name);
                    map.put("answerText", a.getAnswerText());
                    return map;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
