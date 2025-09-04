package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.TrainingProgram;
import jp.co.apsa.giiku.service.TrainingProgramService;
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
 * 研修プログラム管理コントローラー
 * 研修プログラムのCRUD操作とビジネスロジックを提供
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api/training-programs")
@Validated
public class TrainingProgramController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(TrainingProgramController.class);

    @Autowired
    private TrainingProgramService trainingProgramService;

    /**
     * 全研修プログラム一覧取得
     *
     * @param page ページ番号（0から開始）
     * @param size ページサイズ
     * @param sortBy ソート項目
     * @param sortDir ソート方向（ASC/DESC）
     * @return 研修プログラム一覧
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTrainingPrograms(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        logger.info("研修プログラム一覧取得: page={}, size={}, sortBy={}, sortDir={}", 
                   page, size, sortBy, sortDir);

        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDir);
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

            Page<TrainingProgram> programPage = trainingProgramService.findAll(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("programs", programPage.getContent());
            response.put("currentPage", programPage.getNumber());
            response.put("totalPages", programPage.getTotalPages());
            response.put("totalElements", programPage.getTotalElements());
            response.put("hasNext", programPage.hasNext());
            response.put("hasPrevious", programPage.hasPrevious());

            logger.info("研修プログラム一覧取得成功: 総件数={}", programPage.getTotalElements());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("研修プログラム一覧取得エラー", e);
            return createErrorResponse("研修プログラム一覧の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 研修プログラム詳細取得
     *
     * @param id 研修プログラムID
     * @return 研修プログラム詳細
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTrainingProgramById(@PathVariable Long id) {
        logger.info("研修プログラム詳細取得: id={}", id);

        try {
            Optional<TrainingProgram> program = trainingProgramService.findById(id);

            if (program.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("program", program.get());
                logger.info("研修プログラム詳細取得成功: id={}", id);
                return ResponseEntity.ok(response);
            } else {
                logger.warn("研修プログラムが見つかりません: id={}", id);
                return createErrorResponse("指定された研修プログラムが見つかりません", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            logger.error("研修プログラム詳細取得エラー: id={}", id, e);
            return createErrorResponse("研修プログラム詳細の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 研修プログラム新規作成
     *
     * @param program 研修プログラム情報
     * @return 作成された研修プログラム
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createTrainingProgram(@Valid @RequestBody TrainingProgram program) {
        logger.info("研修プログラム新規作成: name={}", program.getName());

        try {
            TrainingProgram savedProgram = trainingProgramService.save(program);

            Map<String, Object> response = new HashMap<>();
            response.put("program", savedProgram);
            response.put("message", "研修プログラムを正常に作成しました");

            logger.info("研修プログラム新規作成成功: id={}, name={}", savedProgram.getId(), savedProgram.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logger.error("研修プログラム新規作成エラー: name={}", program.getName(), e);
            return createErrorResponse("研修プログラムの作成に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 研修プログラム更新
     *
     * @param id 研修プログラムID
     * @param program 更新する研修プログラム情報
     * @return 更新された研修プログラム
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTrainingProgram(
            @PathVariable Long id, 
            @Valid @RequestBody TrainingProgram program) {

        logger.info("研修プログラム更新: id={}, name={}", id, program.getName());

        try {
            Optional<TrainingProgram> existingProgram = trainingProgramService.findById(id);

            if (existingProgram.isPresent()) {
                program.setId(id);
                TrainingProgram updatedProgram = trainingProgramService.save(program);

                Map<String, Object> response = new HashMap<>();
                response.put("program", updatedProgram);
                response.put("message", "研修プログラムを正常に更新しました");

                logger.info("研修プログラム更新成功: id={}, name={}", updatedProgram.getId(), updatedProgram.getName());
                return ResponseEntity.ok(response);
            } else {
                logger.warn("更新対象の研修プログラムが見つかりません: id={}", id);
                return createErrorResponse("指定された研修プログラムが見つかりません", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            logger.error("研修プログラム更新エラー: id={}", id, e);
            return createErrorResponse("研修プログラムの更新に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 研修プログラム削除
     *
     * @param id 研修プログラムID
     * @return 削除結果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteTrainingProgram(@PathVariable Long id) {
        logger.info("研修プログラム削除: id={}", id);

        try {
            Optional<TrainingProgram> existingProgram = trainingProgramService.findById(id);

            if (existingProgram.isPresent()) {
                trainingProgramService.deleteById(id);

                Map<String, Object> response = new HashMap<>();
                response.put("message", "研修プログラムを正常に削除しました");
                response.put("deletedId", id);

                logger.info("研修プログラム削除成功: id={}", id);
                return ResponseEntity.ok(response);
            } else {
                logger.warn("削除対象の研修プログラムが見つかりません: id={}", id);
                return createErrorResponse("指定された研修プログラムが見つかりません", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            logger.error("研修プログラム削除エラー: id={}", id, e);
            return createErrorResponse("研修プログラムの削除に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * アクティブな研修プログラム一覧取得
     *
     * @return アクティブな研修プログラム一覧
     */
    @GetMapping("/active")
    public ResponseEntity<Map<String, Object>> getActiveTrainingPrograms() {
        logger.info("アクティブな研修プログラム一覧取得");

        try {
            List<TrainingProgram> activePrograms = trainingProgramService.findActivePrograms();

            Map<String, Object> response = new HashMap<>();
            response.put("programs", activePrograms);
            response.put("count", activePrograms.size());

            logger.info("アクティブな研修プログラム一覧取得成功: 件数={}", activePrograms.size());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("アクティブな研修プログラム一覧取得エラー", e);
            return createErrorResponse("アクティブな研修プログラム一覧の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 研修プログラム検索
     *
     * @param keyword 検索キーワード
     * @param page ページ番号
     * @param size ページサイズ
     * @return 検索結果
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchTrainingPrograms(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {

        logger.info("研修プログラム検索: keyword={}, page={}, size={}", keyword, page, size);

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<TrainingProgram> searchResults = trainingProgramService.searchPrograms(keyword, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("programs", searchResults.getContent());
            response.put("currentPage", searchResults.getNumber());
            response.put("totalPages", searchResults.getTotalPages());
            response.put("totalElements", searchResults.getTotalElements());
            response.put("keyword", keyword);

            logger.info("研修プログラム検索成功: keyword={}, 件数={}", keyword, searchResults.getTotalElements());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("研修プログラム検索エラー: keyword={}", keyword, e);
            return createErrorResponse("研修プログラムの検索に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
