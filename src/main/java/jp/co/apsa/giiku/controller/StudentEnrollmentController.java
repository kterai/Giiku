package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.StudentEnrollment;
import jp.co.apsa.giiku.service.StudentEnrollmentService;
import jp.co.apsa.giiku.dto.StudentEnrollmentCreateDto;
import jp.co.apsa.giiku.dto.StudentEnrollmentUpdateDto;
import jp.co.apsa.giiku.dto.StudentEnrollmentResponseDto;
import jp.co.apsa.giiku.dto.StudentEnrollmentSearchDto;
import jp.co.apsa.giiku.dto.StudentEnrollmentStatsDto;

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
import java.util.Optional;

/**
 * 学生登録管理コントローラー
 * 学生のコース・プログラム登録のCRUD操作、検索、統計機能を提供
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class StudentEnrollmentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentEnrollmentController.class);

    @Autowired
    private StudentEnrollmentService studentEnrollmentService;

    /**
     * 学生登録一覧取得
     * GET /api/student-enrollments
     */
    @GetMapping("/student-enrollments")
    public ResponseEntity<?> getAllStudentEnrollments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        try {
            logger.info("学生登録一覧取得開始 - page: {}, size: {}, sortBy: {}, sortDir: {}", 
                       page, size, sortBy, sortDir);

            Page<StudentEnrollmentResponseDto> enrollments = studentEnrollmentService.getAllStudentEnrollments(
                page, size, sortBy, sortDir);

            logger.info("学生登録一覧取得成功 - 総件数: {}", enrollments.getTotalElements());
            return ResponseEntity.ok(enrollments);

        } catch (Exception e) {
            logger.error("学生登録一覧取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("学生登録一覧の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 学生登録詳細取得
     * GET /api/student-enrollments/{id}
     */
    @GetMapping("/student-enrollments/{id}")
    public ResponseEntity<?> getStudentEnrollmentById(@PathVariable Long id) {
        try {
            logger.info("学生登録詳細取得開始 - ID: {}", id);

            Optional<StudentEnrollmentResponseDto> enrollment = studentEnrollmentService.getStudentEnrollmentById(id);

            if (enrollment.isPresent()) {
                logger.info("学生登録詳細取得成功 - ID: {}", id);
                return ResponseEntity.ok(enrollment.get());
            } else {
                logger.warn("学生登録が見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("学生登録詳細取得エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("学生登録詳細の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 学生別登録情報取得
     * GET /api/students/{studentId}/enrollments
     */
    @GetMapping("/students/{studentId}/enrollments")
    public ResponseEntity<?> getEnrollmentsByStudentId(
            @PathVariable Long studentId,
            @RequestParam(defaultValue = "enrollmentDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {

        try {
            logger.info("学生別登録情報取得開始 - 学生ID: {}", studentId);

            List<StudentEnrollmentResponseDto> enrollments = studentEnrollmentService.getEnrollmentsByStudentId(
                studentId, sortBy, sortDir);

            logger.info("学生別登録情報取得成功 - 学生ID: {}, 登録数: {}", 
                       studentId, enrollments.size());
            return ResponseEntity.ok(enrollments);

        } catch (Exception e) {
            logger.error("学生別登録情報取得エラー - 学生ID: {}", studentId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("学生別登録情報の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * プログラム別登録学生取得
     * GET /api/training-programs/{programId}/enrollments
     */
    @GetMapping("/training-programs/{programId}/enrollments")
    public ResponseEntity<?> getEnrollmentsByProgramId(
            @PathVariable Long programId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "enrollmentDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {

        try {
            logger.info("プログラム別登録学生取得開始 - プログラムID: {}", programId);

            Page<StudentEnrollmentResponseDto> enrollments = studentEnrollmentService.getEnrollmentsByProgramId(
                programId, page, size, sortBy, sortDir);

            logger.info("プログラム別登録学生取得成功 - プログラムID: {}, 登録学生数: {}", 
                       programId, enrollments.getTotalElements());
            return ResponseEntity.ok(enrollments);

        } catch (Exception e) {
            logger.error("プログラム別登録学生取得エラー - プログラムID: {}", programId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("プログラム別登録学生の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 学生登録作成
     * POST /api/student-enrollments
     */
    @PostMapping("/student-enrollments")
    public ResponseEntity<?> createStudentEnrollment(@Valid @RequestBody StudentEnrollmentCreateDto createDto) {
        try {
            logger.info("学生登録作成開始 - 学生ID: {}, プログラムID: {}", 
                       createDto.getStudentId(), createDto.getProgramId());

            StudentEnrollmentResponseDto createdEnrollment = studentEnrollmentService.createStudentEnrollment(createDto);

            logger.info("学生登録作成成功 - ID: {}, 学生ID: {}, プログラムID: {}", 
                       createdEnrollment.getId(), createdEnrollment.getStudentId(), 
                       createdEnrollment.getProgramId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEnrollment);

        } catch (IllegalArgumentException e) {
            logger.warn("学生登録作成バリデーションエラー: {}", e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("学生登録作成エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("学生登録の作成に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 学生登録更新
     * PUT /api/student-enrollments/{id}
     */
    @PutMapping("/student-enrollments/{id}")
    public ResponseEntity<?> updateStudentEnrollment(
            @PathVariable Long id, 
            @Valid @RequestBody StudentEnrollmentUpdateDto updateDto) {

        try {
            logger.info("学生登録更新開始 - ID: {}", id);

            Optional<StudentEnrollmentResponseDto> updatedEnrollment = 
                studentEnrollmentService.updateStudentEnrollment(id, updateDto);

            if (updatedEnrollment.isPresent()) {
                logger.info("学生登録更新成功 - ID: {}", id);
                return ResponseEntity.ok(updatedEnrollment.get());
            } else {
                logger.warn("更新対象学生登録が見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (IllegalArgumentException e) {
            logger.warn("学生登録更新バリデーションエラー - ID: {}, エラー: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("学生登録更新エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("学生登録の更新に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 学生登録削除
     * DELETE /api/student-enrollments/{id}
     */
    @DeleteMapping("/student-enrollments/{id}")
    public ResponseEntity<?> deleteStudentEnrollment(@PathVariable Long id) {
        try {
            logger.info("学生登録削除開始 - ID: {}", id);

            boolean deleted = studentEnrollmentService.deleteStudentEnrollment(id);

            if (deleted) {
                logger.info("学生登録削除成功 - ID: {}", id);
                return ResponseEntity.noContent().build();
            } else {
                logger.warn("削除対象学生登録が見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("学生登録削除エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("学生登録の削除に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 学生一括登録
     * POST /api/training-programs/{programId}/batch-enroll
     */
    @PostMapping("/training-programs/{programId}/batch-enroll")
    public ResponseEntity<?> batchEnrollStudents(
            @PathVariable Long programId,
            @RequestBody List<Long> studentIds) {

        try {
            logger.info("学生一括登録開始 - プログラムID: {}, 学生数: {}", 
                       programId, studentIds.size());

            List<StudentEnrollmentResponseDto> enrollments = 
                studentEnrollmentService.batchEnrollStudents(programId, studentIds);

            logger.info("学生一括登録成功 - プログラムID: {}, 登録済み学生数: {}", 
                       programId, enrollments.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(enrollments);

        } catch (IllegalArgumentException e) {
            logger.warn("学生一括登録バリデーションエラー - プログラムID: {}, エラー: {}", 
                       programId, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("学生一括登録エラー - プログラムID: {}", programId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("学生一括登録に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 学生登録状態更新
     * PUT /api/student-enrollments/{id}/status
     */
    @PutMapping("/student-enrollments/{id}/status")
    public ResponseEntity<?> updateEnrollmentStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String reason) {

        try {
            logger.info("学生登録状態更新開始 - ID: {}, ステータス: {}", id, status);

            StudentEnrollmentResponseDto updatedEnrollment = 
                studentEnrollmentService.updateEnrollmentStatus(id, status, reason);

            logger.info("学生登録状態更新成功 - ID: {}, 新ステータス: {}", id, status);
            return ResponseEntity.ok(updatedEnrollment);

        } catch (IllegalArgumentException e) {
            logger.warn("学生登録状態更新バリデーションエラー - ID: {}, エラー: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("学生登録状態更新エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("学生登録状態の更新に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 学生登録検索
     * GET /api/student-enrollments/search
     */
    @GetMapping("/student-enrollments/search")
    public ResponseEntity<?> searchStudentEnrollments(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long programId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String enrollmentDateFrom,
            @RequestParam(required = false) String enrollmentDateTo,
            @RequestParam(required = false) String completionDateFrom,
            @RequestParam(required = false) String completionDateTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "enrollmentDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {

        try {
            logger.info("学生登録検索開始 - 学生ID: {}, プログラムID: {}, ステータス: {}", 
                       studentId, programId, status);

            StudentEnrollmentSearchDto searchDto = new StudentEnrollmentSearchDto();
            searchDto.setStudentId(studentId);
            searchDto.setProgramId(programId);
            searchDto.setStatus(status);
            searchDto.setEnrollmentDateFrom(enrollmentDateFrom);
            searchDto.setEnrollmentDateTo(enrollmentDateTo);
            searchDto.setCompletionDateFrom(completionDateFrom);
            searchDto.setCompletionDateTo(completionDateTo);

            Page<StudentEnrollmentResponseDto> searchResult = 
                studentEnrollmentService.searchStudentEnrollments(searchDto, page, size, sortBy, sortDir);

            logger.info("学生登録検索成功 - 検索結果件数: {}", searchResult.getTotalElements());
            return ResponseEntity.ok(searchResult);

        } catch (Exception e) {
            logger.error("学生登録検索エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("学生登録検索に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 学生登録統計情報取得
     * GET /api/student-enrollments/stats
     */
    @GetMapping("/student-enrollments/stats")
    public ResponseEntity<?> getStudentEnrollmentStats(
            @RequestParam(required = false) Long programId,
            @RequestParam(required = false) String period) {

        try {
            logger.info("学生登録統計情報取得開始 - プログラムID: {}, 期間: {}", programId, period);

            StudentEnrollmentStatsDto stats = studentEnrollmentService.getStudentEnrollmentStats(programId, period);

            logger.info("学生登録統計情報取得成功 - 総登録数: {}, 完了率: {}%", 
                       stats.getTotalEnrollments(), stats.getCompletionRate());
            return ResponseEntity.ok(stats);

        } catch (Exception e) {
            logger.error("学生登録統計情報取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("学生登録統計情報の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 進捗別登録状況取得
     * GET /api/student-enrollments/progress-summary
     */
    @GetMapping("/student-enrollments/progress-summary")
    public ResponseEntity<?> getEnrollmentProgressSummary(
            @RequestParam(required = false) Long programId,
            @RequestParam(required = false) Long studentId) {

        try {
            logger.info("進捗別登録状況取得開始 - プログラムID: {}, 学生ID: {}", programId, studentId);

            Map<String, Long> progressSummary = 
                studentEnrollmentService.getEnrollmentProgressSummary(programId, studentId);

            logger.info("進捗別登録状況取得成功 - ステータス種類数: {}", progressSummary.size());
            return ResponseEntity.ok(progressSummary);

        } catch (Exception e) {
            logger.error("進捗別登録状況取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("進捗別登録状況の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 登録期限チェック
     * GET /api/student-enrollments/deadline-check
     */
    @GetMapping("/student-enrollments/deadline-check")
    public ResponseEntity<?> checkEnrollmentDeadlines(
            @RequestParam(required = false) Integer daysBeforeDeadline) {

        try {
            int days = (daysBeforeDeadline != null) ? daysBeforeDeadline : 7; // デフォルト7日前
            logger.info("登録期限チェック開始 - {}日前の期限チェック", days);

            List<StudentEnrollmentResponseDto> nearDeadlineEnrollments = 
                studentEnrollmentService.checkEnrollmentDeadlines(days);

            logger.info("登録期限チェック完了 - 期限近接登録数: {}", nearDeadlineEnrollments.size());
            return ResponseEntity.ok(nearDeadlineEnrollments);

        } catch (Exception e) {
            logger.error("登録期限チェックエラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("登録期限チェックに失敗しました: " + e.getMessage());
        }
    }
}