package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.Quiz;
import jp.co.apsa.giiku.service.QuizService;
import jp.co.apsa.giiku.service.StudentAnswerService;
import jp.co.apsa.giiku.domain.entity.QuizQuestionBank;
import jp.co.apsa.giiku.domain.repository.QuizQuestionBankRepository;
import jp.co.apsa.giiku.domain.entity.StudentAnswer;
import jp.co.apsa.giiku.domain.entity.User;
import jp.co.apsa.giiku.domain.repository.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;
import jp.co.apsa.giiku.dto.QuizAnswerRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;

/**
 * クイズコントローラー
 *
 * LMSクイズ実行機能のREST APIエンドポイントを提供します。
 * クイズの開始、進行、提出、採点、統計取得などの機能を含みます。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuizController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    private QuizService quizService;

    @Autowired
    private StudentAnswerService studentAnswerService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private QuizQuestionBankRepository quizQuestionBankRepository;

    @Autowired
    private UserRepository userRepository;

    // ===== CRUD操作 =====

    /**
     * クイズ一覧をページング形式で取得
     *
     * @param pageable ページング情報
     * @return クイズ一覧のページ
     */
    @GetMapping
    public ResponseEntity<Page<Quiz>> getAllQuizzes(Pageable pageable) {
        try {
            logger.debug("クイズ一覧取得リクエスト: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
            Page<Quiz> quizzes = quizService.findAll(pageable);
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            logger.error("クイズ一覧取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * クイズIDでクイズ詳細を取得
     *
     * @param id クイズID
     * @return クイズ詳細
     */
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        try {
            logger.debug("クイズ詳細取得リクエスト: id={}", id);
            Optional<Quiz> quiz = quizService.findById(id);
            return quiz.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("クイズ詳細取得エラー: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 新しいクイズを作成
     *
     * @param quiz クイズ情報
     * @return 作成されたクイズ
     */
    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@Valid @RequestBody Quiz quiz) {
        try {
            logger.debug("クイズ作成リクエスト: title={}, studentId={}", 
                        quiz.getQuizTitle(), quiz.getStudentId());
            Quiz savedQuiz = quizService.save(quiz);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedQuiz);
        } catch (Exception e) {
            logger.error("クイズ作成エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * クイズ情報を更新
     *
     * @param id クイズID
     * @param quiz 更新するクイズ情報
     * @return 更新されたクイズ
     */
    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, 
                                          @Valid @RequestBody Quiz quiz) {
        try {
            logger.debug("クイズ更新リクエスト: id={}", id);
            quiz.setId(id);
            Quiz updatedQuiz = quizService.save(quiz);
            return ResponseEntity.ok(updatedQuiz);
        } catch (Exception e) {
            logger.error("クイズ更新エラー: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== クイズ実行機能 =====

    /**
     * クイズを開始
     *
     * @param id クイズID
     * @return 開始されたクイズ
     */
    @PostMapping("/{id}/start")
    public ResponseEntity<Quiz> startQuiz(@PathVariable Long id) {
        try {
            logger.debug("クイズ開始リクエスト: id={}", id);
            Quiz startedQuiz = quizService.startQuiz(id);
            return ResponseEntity.ok(startedQuiz);
        } catch (Exception e) {
            logger.error("クイズ開始エラー: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * クイズ回答を保存
     *
     * @param id クイズID
     * @param answers 回答データ
     * @return 更新されたクイズ
     */
    @PostMapping("/{id}/answer")
    public ResponseEntity<Quiz> saveAnswer(@PathVariable Long id,
                                          @RequestBody Map<String, Object> answers) {
        try {
            logger.debug("クイズ回答保存リクエスト: id={}", id);
            Quiz quiz = quizService.findById(id).orElse(null);
            if (quiz == null) {
                return ResponseEntity.notFound().build();
            }
            Long studentId = quiz.getStudentId();

            if (answers != null) {
                for (Map.Entry<String, Object> entry : answers.entrySet()) {
                    Long questionId = Long.parseLong(entry.getKey());
                    String answerText = entry.getValue() != null ? entry.getValue().toString() : null;
                    studentAnswerService.saveAnswer(id, questionId, studentId, answerText);
                }
            }

            messagingTemplate.convertAndSend("/topic/answers/" + id, answers);

            return ResponseEntity.ok(quiz);
        } catch (Exception e) {
            logger.error("クイズ回答保存エラー: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 個別のクイズ回答を受け付け判定します。
     *
     * @param questionId 質問ID
     * @param request    回答情報（quizId, studentId, answer）
     * @return 判定結果
     */
    @PostMapping("/questions/{questionId}/answer")
    public ResponseEntity<Map<String, Object>> answerQuestion(@PathVariable Long questionId,
                                                              @Valid @RequestBody QuizAnswerRequest request,
                                                              BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().build();
            }

            Long quizId = request.getQuizId();
            Long studentId = request.getStudentId();
            String answer = request.getAnswer();

            QuizQuestionBank question = quizQuestionBankRepository.findById(questionId)
                    .orElse(null);
            if (question == null) {
                return ResponseEntity.notFound().build();
            }

            boolean correct = question.getCorrectAnswer() != null
                    && question.getCorrectAnswer().trim().equalsIgnoreCase(answer != null ? answer.trim() : "");

            studentAnswerService.saveAnswer(quizId, questionId, studentId, answer);

            Map<String, Object> response = new HashMap<>();
            response.put("correct", correct);
            response.put("explanation", question.getExplanation());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("クイズ回答処理エラー: questionId={}", questionId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * クイズを提出
     *
     * @param id クイズID
     * @return 提出されたクイズ
     */
    @PostMapping("/{id}/submit")
    public ResponseEntity<Quiz> submitQuiz(@PathVariable Long id) {
        try {
            logger.debug("クイズ提出リクエスト: id={}", id);
            Quiz submittedQuiz = quizService.submitQuiz(id);
            return ResponseEntity.ok(submittedQuiz);
        } catch (Exception e) {
            logger.error("クイズ提出エラー: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * クイズを採点
     *
     * @param id クイズID
     * @return 採点されたクイズ
     */
    @PostMapping("/{id}/grade")
    public ResponseEntity<Quiz> gradeQuiz(@PathVariable Long id) {
        try {
            logger.debug("クイズ採点リクエスト: id={}", id);
            Quiz gradedQuiz = quizService.gradeQuiz(id);
            return ResponseEntity.ok(gradedQuiz);
        } catch (Exception e) {
            logger.error("クイズ採点エラー: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== 検索機能 =====

    /**
     * 学生IDでクイズ一覧を取得
     *
     * @param studentId 学生ID
     * @return クイズ一覧
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Quiz>> getQuizzesByStudent(@PathVariable Long studentId) {
        try {
            logger.debug("学生別クイズ取得リクエスト: studentId={}", studentId);
            List<Quiz> quizzes = quizService.findByStudentId(studentId);
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            logger.error("学生別クイズ取得エラー: studentId={}", studentId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 講師IDでクイズ一覧を取得
     *
     * @param instructorId 講師ID
     * @return クイズ一覧
     */
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Quiz>> getQuizzesByInstructor(@PathVariable Long instructorId) {
        try {
            logger.debug("講師別クイズ取得リクエスト: instructorId={}", instructorId);
            List<Quiz> quizzes = quizService.findByInstructorId(instructorId);
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            logger.error("講師別クイズ取得エラー: instructorId={}", instructorId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * クイズ状態でクイズ一覧を取得
     *
     * @param status クイズ状態
     * @return クイズ一覧
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Quiz>> getQuizzesByStatus(@PathVariable String status) {
        try {
            logger.debug("状態別クイズ取得リクエスト: status={}", status);
            List<Quiz> quizzes = quizService.findByQuizStatus(status);
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            logger.error("状態別クイズ取得エラー: status={}", status, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生の進行中クイズを取得
     *
     * @param studentId 学生ID
     * @return 進行中クイズ一覧
     */
    @GetMapping("/student/{studentId}/in-progress")
    public ResponseEntity<List<Quiz>> getInProgressQuizzes(@PathVariable Long studentId) {
        try {
            logger.debug("進行中クイズ取得リクエスト: studentId={}", studentId);
            List<Quiz> quizzes = quizService.findInProgressQuizzesByStudent(studentId);
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            logger.error("進行中クイズ取得エラー: studentId={}", studentId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生の完了クイズを取得
     *
     * @param studentId 学生ID
     * @return 完了クイズ一覧
     */
    @GetMapping("/student/{studentId}/completed")
    public ResponseEntity<List<Quiz>> getCompletedQuizzes(@PathVariable Long studentId) {
        try {
            logger.debug("完了クイズ取得リクエスト: studentId={}", studentId);
            List<Quiz> quizzes = quizService.findCompletedQuizzesByStudent(studentId);
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            logger.error("完了クイズ取得エラー: studentId={}", studentId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 質問IDで学生回答を取得
     *
     * @param questionId 質問ID
     * @return 学生名、回答内容、正誤の一覧
     */
    @GetMapping("/questions/{questionId}/answers")
    public ResponseEntity<List<Map<String, Object>>> getAnswersByQuestion(@PathVariable Long questionId) {
        try {
            logger.debug("回答一覧取得リクエスト: questionId={}", questionId);
            List<StudentAnswer> answers = studentAnswerService.getAnswersByQuestionId(questionId);
            String correctAnswer = quizQuestionBankRepository.findById(questionId)
                    .map(QuizQuestionBank::getCorrectAnswer)
                    .orElse("");
            List<Map<String, Object>> result = answers.stream()
                    .map(a -> {
                        String name = userRepository.findById(a.getStudentId())
                                .map(User::getName)
                                .orElse("不明");
                        Map<String, Object> map = new HashMap<>();
                        map.put("studentName", name);
                        map.put("answerText", a.getAnswerText());
                        map.put("correct", correctAnswer.equals(a.getAnswerText()));
                        return map;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("回答一覧取得エラー: questionId={}", questionId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== 統計・分析機能 =====

    /**
     * 企業内クイズ統計を取得
     *
     * @param companyId 企業ID
     * @return クイズ統計
     */
    @GetMapping("/company/{companyId}/stats")
    public ResponseEntity<List<Map<String, Object>>> getQuizStats(@PathVariable Long companyId) {
        try {
            logger.debug("クイズ統計取得リクエスト: companyId={}", companyId);
            List<Map<String, Object>> stats = quizService.getQuizStatistics(companyId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("クイズ統計取得エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 企業内平均スコアを取得
     *
     * @param companyId 企業ID
     * @return 平均スコア
     */
    @GetMapping("/company/{companyId}/average-score")
    public ResponseEntity<BigDecimal> getAverageScore(@PathVariable Long companyId) {
        try {
            logger.debug("平均スコア取得リクエスト: companyId={}", companyId);
            BigDecimal averageScore = quizService.calculateAverageScore(companyId);
            return ResponseEntity.ok(averageScore);
        } catch (Exception e) {
            logger.error("平均スコア取得エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生の平均スコアを取得
     *
     * @param studentId 学生ID
     * @return 学生の平均スコア
     */
    @GetMapping("/student/{studentId}/average-score")
    public ResponseEntity<BigDecimal> getStudentAverageScore(@PathVariable Long studentId) {
        try {
            logger.debug("学生平均スコア取得リクエスト: studentId={}", studentId);
            BigDecimal averageScore = quizService.calculateStudentAverageScore(studentId);
            return ResponseEntity.ok(averageScore);
        } catch (Exception e) {
            logger.error("学生平均スコア取得エラー: studentId={}", studentId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 制限時間超過クイズを取得
     *
     * @param companyId 企業ID
     * @return 制限時間超過クイズ一覧
     */
    @GetMapping("/company/{companyId}/overdue")
    public ResponseEntity<List<Quiz>> getOverdueQuizzes(@PathVariable Long companyId) {
        try {
            logger.debug("制限時間超過クイズ取得リクエスト: companyId={}", companyId);
            List<Quiz> quizzes = quizService.findOverdueQuizzes(companyId);
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            logger.error("制限時間超過クイズ取得エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 高得点クイズトップ取得
     *
     * @param companyId 企業ID
     * @param limit 取得件数
     * @return 高得点クイズ一覧
     */
    @GetMapping("/company/{companyId}/top-scores")
    public ResponseEntity<List<Quiz>> getTopScoringQuizzes(@PathVariable Long companyId,
                                                          @RequestParam(defaultValue = "10") Integer limit) {
        try {
            logger.debug("高得点クイズ取得リクエスト: companyId={}, limit={}", companyId, limit);
            List<Quiz> quizzes = quizService.findTopScoringQuizzes(companyId, limit);
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            logger.error("高得点クイズ取得エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
