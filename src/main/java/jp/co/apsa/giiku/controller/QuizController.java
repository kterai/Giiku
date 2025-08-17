package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.Quiz;
import jp.co.apsa.giiku.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * クイズコントローラー
 *
 * LMSクイズ実行機能のREST APIエンドポイントを提供します。
 * クイズの開始、進行、提出、採点、統計取得などの機能を含みます。
 *
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

    // ===== CRUD操作 =====

    /**
     * クイズ一覧をページング形式で取得
     * 
     * @param pageable ページング情報
     * @return クイズ一覧のページ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @PostMapping("/{id}/answer")
    public ResponseEntity<Quiz> saveAnswer(@PathVariable Long id, 
                                          @RequestBody Map<String, Object> answers) {
        try {
            logger.debug("クイズ回答保存リクエスト: id={}", id);
            Quiz updatedQuiz = quizService.saveAnswers(id, answers);
            return ResponseEntity.ok(updatedQuiz);
        } catch (Exception e) {
            logger.error("クイズ回答保存エラー: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * クイズを提出
     * 
     * @param id クイズID
     * @return 提出されたクイズ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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

    // ===== 統計・分析機能 =====

    /**
     * 企業内クイズ統計を取得
     * 
     * @param companyId 企業ID
     * @return クイズ統計
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
