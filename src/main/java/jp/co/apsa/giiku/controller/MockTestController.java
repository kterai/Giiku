package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.MockTest;
import jp.co.apsa.giiku.domain.entity.MockTestResult;
import jp.co.apsa.giiku.service.MockTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 模擬試験管理コントローラー
 * 模擬試験のCRUD操作と結果管理を提供
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api/mock-tests")
@Validated
@CrossOrigin(origins = "*")
public class MockTestController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(MockTestController.class);

    @Autowired
    private MockTestService mockTestService;

    /**
     * 全模擬試験一覧取得
     *
     * @param page ページ番号（0から開始）
     * @param size ページサイズ
     * @param sortBy ソート項目
     * @param sortDir ソート方向（ASC/DESC）
     * @return 模擬試験一覧
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllMockTests(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        logger.info("模擬試験一覧取得: page={}, size={}, sortBy={}, sortDir={}", 
                   page, size, sortBy, sortDir);

        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDir);
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

            Page<MockTest> mockTestPage = mockTestService.findAll(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("mockTests", mockTestPage.getContent());
            response.put("currentPage", mockTestPage.getNumber());
            response.put("totalPages", mockTestPage.getTotalPages());
            response.put("totalElements", mockTestPage.getTotalElements());
            response.put("hasNext", mockTestPage.hasNext());
            response.put("hasPrevious", mockTestPage.hasPrevious());

            logger.info("模擬試験一覧取得成功: 総件数={}", mockTestPage.getTotalElements());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("模擬試験一覧取得エラー", e);
            return createErrorResponse("模擬試験一覧の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 模擬試験詳細取得
     *
     * @param id 模擬試験ID
     * @return 模擬試験詳細
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getMockTestById(@PathVariable Long id) {
        logger.info("模擬試験詳細取得: id={}", id);

        try {
            Optional<MockTest> mockTest = mockTestService.findById(id);

            if (mockTest.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("mockTest", mockTest.get());
                logger.info("模擬試験詳細取得成功: id={}", id);
                return ResponseEntity.ok(response);
            } else {
                logger.warn("模擬試験が見つかりません: id={}", id);
                return createErrorResponse("指定された模擬試験が見つかりません", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            logger.error("模擬試験詳細取得エラー: id={}", id, e);
            return createErrorResponse("模擬試験詳細の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 模擬試験新規作成
     *
     * @param mockTest 模擬試験情報
     * @return 作成された模擬試験
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createMockTest(@Valid @RequestBody MockTest mockTest) {
        logger.info("模擬試験新規作成: title={}", mockTest.getTitle());

        try {
            MockTest savedMockTest = mockTestService.save(mockTest);

            Map<String, Object> response = new HashMap<>();
            response.put("mockTest", savedMockTest);
            response.put("message", "模擬試験を正常に作成しました");

            logger.info("模擬試験新規作成成功: id={}, title={}", savedMockTest.getId(), savedMockTest.getTitle());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logger.error("模擬試験新規作成エラー: title={}", mockTest.getTitle(), e);
            return createErrorResponse("模擬試験の作成に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 模擬試験更新
     *
     * @param id 模擬試験ID
     * @param mockTest 更新する模擬試験情報
     * @return 更新された模擬試験
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateMockTest(
            @PathVariable Long id, 
            @Valid @RequestBody MockTest mockTest) {

        logger.info("模擬試験更新: id={}, title={}", id, mockTest.getTitle());

        try {
            Optional<MockTest> existingMockTest = mockTestService.findById(id);

            if (existingMockTest.isPresent()) {
                mockTest.setId(id);
                MockTest updatedMockTest = mockTestService.save(mockTest);

                Map<String, Object> response = new HashMap<>();
                response.put("mockTest", updatedMockTest);
                response.put("message", "模擬試験を正常に更新しました");

                logger.info("模擬試験更新成功: id={}, title={}", updatedMockTest.getId(), updatedMockTest.getTitle());
                return ResponseEntity.ok(response);
            } else {
                logger.warn("更新対象の模擬試験が見つかりません: id={}", id);
                return createErrorResponse("指定された模擬試験が見つかりません", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            logger.error("模擬試験更新エラー: id={}", id, e);
            return createErrorResponse("模擬試験の更新に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 模擬試験削除
     *
     * @param id 模擬試験ID
     * @return 削除結果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteMockTest(@PathVariable Long id) {
        logger.info("模擬試験削除: id={}", id);

        try {
            Optional<MockTest> existingMockTest = mockTestService.findById(id);

            if (existingMockTest.isPresent()) {
                mockTestService.deleteById(id);

                Map<String, Object> response = new HashMap<>();
                response.put("message", "模擬試験を正常に削除しました");
                response.put("deletedId", id);

                logger.info("模擬試験削除成功: id={}", id);
                return ResponseEntity.ok(response);
            } else {
                logger.warn("削除対象の模擬試験が見つかりません: id={}", id);
                return createErrorResponse("指定された模擬試験が見つかりません", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            logger.error("模擬試験削除エラー: id={}", id, e);
            return createErrorResponse("模擬試験の削除に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 学生の模擬試験受験
     *
     * @param mockTestId 模擬試験ID
     * @param studentId 学生ID
     * @param answers 解答データ
     * @return 受験結果
     */
    @PostMapping("/{mockTestId}/take/{studentId}")
    public ResponseEntity<Map<String, Object>> takeMockTest(
            @PathVariable Long mockTestId,
            @PathVariable Long studentId,
            @RequestBody Map<String, Object> answers) {

        logger.info("模擬試験受験開始: mockTestId={}, studentId={}", mockTestId, studentId);

        try {
            MockTestResult result = mockTestService.takeMockTest(mockTestId, studentId, answers);

            Map<String, Object> response = new HashMap<>();
            response.put("result", result);
            response.put("message", "模擬試験を正常に完了しました");

            logger.info("模擬試験受験完了: mockTestId={}, studentId={}, score={}", 
                       mockTestId, studentId, result.getScore());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("模擬試験受験エラー: mockTestId={}, studentId={}", mockTestId, studentId, e);
            return createErrorResponse("模擬試験の受験に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 学生の模擬試験結果一覧取得
     *
     * @param studentId 学生ID
     * @param page ページ番号
     * @param size ページサイズ
     * @return 模擬試験結果一覧
     */
    @GetMapping("/results/student/{studentId}")
    public ResponseEntity<Map<String, Object>> getStudentMockTestResults(
            @PathVariable Long studentId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {

        logger.info("学生の模擬試験結果一覧取得: studentId={}, page={}, size={}", studentId, page, size);

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "testDate"));
            Page<MockTestResult> resultPage = mockTestService.findResultsByStudentId(studentId, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("results", resultPage.getContent());
            response.put("currentPage", resultPage.getNumber());
            response.put("totalPages", resultPage.getTotalPages());
            response.put("totalElements", resultPage.getTotalElements());
            response.put("studentId", studentId);

            logger.info("学生の模擬試験結果一覧取得成功: studentId={}, 総件数={}", 
                       studentId, resultPage.getTotalElements());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("学生の模擬試験結果一覧取得エラー: studentId={}", studentId, e);
            return createErrorResponse("模擬試験結果一覧の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 模擬試験の全結果取得
     *
     * @param mockTestId 模擬試験ID
     * @param page ページ番号
     * @param size ページサイズ
     * @return 模擬試験の全結果
     */
    @GetMapping("/{mockTestId}/results")
    public ResponseEntity<Map<String, Object>> getMockTestResults(
            @PathVariable Long mockTestId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {

        logger.info("模擬試験結果一覧取得: mockTestId={}, page={}, size={}", mockTestId, page, size);

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "score"));
            Page<MockTestResult> resultPage = mockTestService.findResultsByMockTestId(mockTestId, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("results", resultPage.getContent());
            response.put("currentPage", resultPage.getNumber());
            response.put("totalPages", resultPage.getTotalPages());
            response.put("totalElements", resultPage.getTotalElements());
            response.put("mockTestId", mockTestId);

            logger.info("模擬試験結果一覧取得成功: mockTestId={}, 総件数={}", 
                       mockTestId, resultPage.getTotalElements());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("模擬試験結果一覧取得エラー: mockTestId={}", mockTestId, e);
            return createErrorResponse("模擬試験結果一覧の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 模擬試験統計情報取得
     *
     * @param mockTestId 模擬試験ID
     * @return 統計情報
     */
    @GetMapping("/{mockTestId}/statistics")
    public ResponseEntity<Map<String, Object>> getMockTestStatistics(@PathVariable Long mockTestId) {
        logger.info("模擬試験統計情報取得: mockTestId={}", mockTestId);

        try {
            Map<String, Object> statistics = mockTestService.getStatistics(mockTestId);

            Map<String, Object> response = new HashMap<>();
            response.put("statistics", statistics);
            response.put("mockTestId", mockTestId);

            logger.info("模擬試験統計情報取得成功: mockTestId={}", mockTestId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("模擬試験統計情報取得エラー: mockTestId={}", mockTestId, e);
            return createErrorResponse("模擬試験統計情報の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * アクティブな模擬試験一覧取得
     *
     * @return アクティブな模擬試験一覧
     */
    @GetMapping("/active")
    public ResponseEntity<Map<String, Object>> getActiveMockTests() {
        logger.info("アクティブな模擬試験一覧取得");

        try {
            List<MockTest> activeMockTests = mockTestService.findActiveMockTests();

            Map<String, Object> response = new HashMap<>();
            response.put("mockTests", activeMockTests);
            response.put("count", activeMockTests.size());

            logger.info("アクティブな模擬試験一覧取得成功: 件数={}", activeMockTests.size());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("アクティブな模擬試験一覧取得エラー", e);
            return createErrorResponse("アクティブな模擬試験一覧の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 模擬試験検索
     *
     * @param keyword 検索キーワード
     * @param page ページ番号
     * @param size ページサイズ
     * @return 検索結果
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchMockTests(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {

        logger.info("模擬試験検索: keyword={}, page={}, size={}", keyword, page, size);

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<MockTest> searchResults = mockTestService.searchMockTests(keyword, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("mockTests", searchResults.getContent());
            response.put("currentPage", searchResults.getNumber());
            response.put("totalPages", searchResults.getTotalPages());
            response.put("totalElements", searchResults.getTotalElements());
            response.put("keyword", keyword);

            logger.info("模擬試験検索成功: keyword={}, 件数={}", keyword, searchResults.getTotalElements());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("模擬試験検索エラー: keyword={}", keyword, e);
            return createErrorResponse("模擬試験の検索に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
