package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.QuestionBank;
import jp.co.apsa.giiku.service.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 問題銀行コントローラー
 *
 * LMS問題管理機能のREST APIエンドポイントを提供します。
 * 問題の作成、更新、削除、検索、統計取得などの機能を含みます。
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api/question-banks")
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuestionBankController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionBankController.class);

    @Autowired
    private QuestionBankService questionBankService;

    // ===== CRUD操作 =====

    /**
     * 問題一覧をページング形式で取得
     * 
     * @param pageable ページング情報
     * @return 問題一覧のページ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping
    public ResponseEntity<Page<QuestionBank>> getAllQuestions(Pageable pageable) {
        try {
            logger.debug("問題一覧取得リクエスト: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
            Page<QuestionBank> questions = questionBankService.findAll(pageable);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            logger.error("問題一覧取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 問題IDで問題詳細を取得
     * 
     * @param id 問題ID
     * @return 問題詳細
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/{id}")
    public ResponseEntity<QuestionBank> getQuestionById(@PathVariable Long id) {
        try {
            logger.debug("問題詳細取得リクエスト: id={}", id);
            Optional<QuestionBank> question = questionBankService.findById(id);
            return question.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("問題詳細取得エラー: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 新しい問題を作成
     * 
     * @param questionBank 問題情報
     * @return 作成された問題
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @PostMapping
    public ResponseEntity<QuestionBank> createQuestion(@Valid @RequestBody QuestionBank questionBank) {
        try {
            logger.debug("問題作成リクエスト: questionType={}, category={}", 
                        questionBank.getQuestionType(), questionBank.getCategory());
            QuestionBank savedQuestion = questionBankService.save(questionBank);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
        } catch (Exception e) {
            logger.error("問題作成エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 問題情報を更新
     * 
     * @param id 問題ID
     * @param questionBank 更新する問題情報
     * @return 更新された問題
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @PutMapping("/{id}")
    public ResponseEntity<QuestionBank> updateQuestion(@PathVariable Long id, 
                                                       @Valid @RequestBody QuestionBank questionBank) {
        try {
            logger.debug("問題更新リクエスト: id={}", id);
            questionBank.setId(id);
            QuestionBank updatedQuestion = questionBankService.save(questionBank);
            return ResponseEntity.ok(updatedQuestion);
        } catch (Exception e) {
            logger.error("問題更新エラー: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 問題を削除（論理削除）
     * 
     * @param id 問題ID
     * @return 削除結果
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        try {
            logger.debug("問題削除リクエスト: id={}", id);
            questionBankService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("問題削除エラー: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== 検索機能 =====

    /**
     * 企業IDで問題一覧を取得
     * 
     * @param companyId 企業ID
     * @return 問題一覧
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<QuestionBank>> getQuestionsByCompany(@PathVariable Long companyId) {
        try {
            logger.debug("企業別問題取得リクエスト: companyId={}", companyId);
            List<QuestionBank> questions = questionBankService.findByCompanyId(companyId);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            logger.error("企業別問題取得エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 講師IDで問題一覧を取得
     * 
     * @param instructorId 講師ID
     * @return 問題一覧
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<QuestionBank>> getQuestionsByInstructor(@PathVariable Long instructorId) {
        try {
            logger.debug("講師別問題取得リクエスト: instructorId={}", instructorId);
            List<QuestionBank> questions = questionBankService.findByInstructorId(instructorId);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            logger.error("講師別問題取得エラー: instructorId={}", instructorId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 問題タイプで問題一覧を取得
     * 
     * @param questionType 問題タイプ
     * @return 問題一覧
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/type/{questionType}")
    public ResponseEntity<List<QuestionBank>> getQuestionsByType(@PathVariable String questionType) {
        try {
            logger.debug("問題タイプ別取得リクエスト: questionType={}", questionType);
            List<QuestionBank> questions = questionBankService.findByQuestionType(questionType);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            logger.error("問題タイプ別取得エラー: questionType={}", questionType, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 難易度で問題一覧を取得
     * 
     * @param difficultyLevel 難易度
     * @return 問題一覧
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/difficulty/{difficultyLevel}")
    public ResponseEntity<List<QuestionBank>> getQuestionsByDifficulty(@PathVariable String difficultyLevel) {
        try {
            logger.debug("難易度別問題取得リクエスト: difficultyLevel={}", difficultyLevel);
            List<QuestionBank> questions = questionBankService.findByDifficultyLevel(difficultyLevel);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            logger.error("難易度別問題取得エラー: difficultyLevel={}", difficultyLevel, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * カテゴリで問題一覧を取得
     * 
     * @param category カテゴリ
     * @return 問題一覧
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<QuestionBank>> getQuestionsByCategory(@PathVariable String category) {
        try {
            logger.debug("カテゴリ別問題取得リクエスト: category={}", category);
            List<QuestionBank> questions = questionBankService.findByCategory(category);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            logger.error("カテゴリ別問題取得エラー: category={}", category, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 問題文での部分一致検索
     * 
     * @param query 検索クエリ
     * @return 検索結果
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/search")
    public ResponseEntity<List<QuestionBank>> searchQuestions(@RequestParam String query) {
        try {
            logger.debug("問題検索リクエスト: query={}", query);
            List<QuestionBank> questions = questionBankService.searchByQuestionText(query);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            logger.error("問題検索エラー: query={}", query, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== 統計・分析機能 =====

    /**
     * 企業内カテゴリ別問題統計を取得
     * 
     * @param companyId 企業ID
     * @return カテゴリ別統計
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/company/{companyId}/stats/category")
    public ResponseEntity<List<Map<String, Object>>> getCategoryStats(@PathVariable Long companyId) {
        try {
            logger.debug("カテゴリ統計取得リクエスト: companyId={}", companyId);
            List<Map<String, Object>> stats = questionBankService.getCategoryStatistics(companyId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("カテゴリ統計取得エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 企業内難易度別問題統計を取得
     * 
     * @param companyId 企業ID
     * @return 難易度別統計
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/company/{companyId}/stats/difficulty")
    public ResponseEntity<List<Map<String, Object>>> getDifficultyStats(@PathVariable Long companyId) {
        try {
            logger.debug("難易度統計取得リクエスト: companyId={}", companyId);
            List<Map<String, Object>> stats = questionBankService.getDifficultyStatistics(companyId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("難易度統計取得エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 企業内問題タイプ別統計を取得
     * 
     * @param companyId 企業ID
     * @return 問題タイプ別統計
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/company/{companyId}/stats/type")
    public ResponseEntity<List<Map<String, Object>>> getTypeStats(@PathVariable Long companyId) {
        try {
            logger.debug("問題タイプ統計取得リクエスト: companyId={}", companyId);
            List<Map<String, Object>> stats = questionBankService.getTypeStatistics(companyId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("問題タイプ統計取得エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== クイズ用問題選択機能 =====

    /**
     * ランダムに問題を選択
     * 
     * @param companyId 企業ID
     * @param questionType 問題タイプ
     * @param difficultyLevel 難易度
     * @param limit 取得件数
     * @return ランダム選択された問題一覧
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/company/{companyId}/random")
    public ResponseEntity<List<QuestionBank>> getRandomQuestions(
            @PathVariable Long companyId,
            @RequestParam(required = false) String questionType,
            @RequestParam(required = false) String difficultyLevel,
            @RequestParam(defaultValue = "10") Integer limit) {
        try {
            logger.debug("ランダム問題取得リクエスト: companyId={}, type={}, difficulty={}, limit={}", 
                        companyId, questionType, difficultyLevel, limit);
            List<QuestionBank> questions = questionBankService.getRandomQuestions(
                companyId, questionType, difficultyLevel, limit);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            logger.error("ランダム問題取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
