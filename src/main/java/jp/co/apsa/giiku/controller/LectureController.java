package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.Lecture;
import jp.co.apsa.giiku.domain.service.LectureService;
import jp.co.apsa.giiku.dto.LectureCreateDto;
import jp.co.apsa.giiku.dto.LectureUpdateDto;
import jp.co.apsa.giiku.dto.LectureResponseDto;
import jp.co.apsa.giiku.dto.LectureSearchDto;
import jp.co.apsa.giiku.dto.LectureStatsDto;

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
import java.util.Optional;

/**
 * 講義管理コントローラー
 * 講義のCRUD操作、検索、統計機能を提供
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LectureController {

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

    @Autowired
    private LectureService lectureService;

    /**
     * 講義一覧取得
     * GET /api/lectures
     */
    @GetMapping("/lectures")
    public ResponseEntity<?> getAllLectures(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        try {
            logger.info("講義一覧取得開始 - page: {}, size: {}, sortBy: {}, sortDir: {}", 
                       page, size, sortBy, sortDir);

            Page<LectureResponseDto> lectures = lectureService.getAllLectures(
                page, size, sortBy, sortDir);

            logger.info("講義一覧取得成功 - 総件数: {}", lectures.getTotalElements());
            return ResponseEntity.ok(lectures);

        } catch (Exception e) {
            logger.error("講義一覧取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("講義一覧の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 講義詳細取得
     * GET /api/lectures/{id}
     */
    @GetMapping("/lectures/{id}")
    public ResponseEntity<?> getLectureById(@PathVariable Long id) {
        try {
            logger.info("講義詳細取得開始 - ID: {}", id);

            Optional<LectureResponseDto> lecture = lectureService.getLectureById(id);

            if (lecture.isPresent()) {
                logger.info("講義詳細取得成功 - ID: {}", id);
                return ResponseEntity.ok(lecture.get());
            } else {
                logger.warn("講義が見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("講義詳細取得エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("講義詳細の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * プログラム別講義取得
     * GET /api/training-programs/{programId}/lectures
     */
    @GetMapping("/training-programs/{programId}/lectures")
    public ResponseEntity<?> getLecturesByProgramId(
            @PathVariable Long programId,
            @RequestParam(defaultValue = "lectureOrder") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        try {
            logger.info("プログラム別講義取得開始 - プログラムID: {}", programId);

            List<LectureResponseDto> lectures = lectureService.getLecturesByProgramId(
                programId, sortBy, sortDir);

            logger.info("プログラム別講義取得成功 - プログラムID: {}, 講義数: {}", 
                       programId, lectures.size());
            return ResponseEntity.ok(lectures);

        } catch (Exception e) {
            logger.error("プログラム別講義取得エラー - プログラムID: {}", programId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("プログラム別講義の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 講義作成
     * POST /api/lectures
     */
    @PostMapping("/lectures")
    public ResponseEntity<?> createLecture(@Valid @RequestBody LectureCreateDto createDto) {
        try {
            logger.info("講義作成開始 - タイトル: {}, プログラムID: {}", 
                       createDto.getTitle(), createDto.getProgramId());

            LectureResponseDto createdLecture = lectureService.createLecture(createDto);

            logger.info("講義作成成功 - ID: {}, タイトル: {}", 
                       createdLecture.getId(), createdLecture.getTitle());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLecture);

        } catch (IllegalArgumentException e) {
            logger.warn("講義作成バリデーションエラー: {}", e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("講義作成エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("講義の作成に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 講義更新
     * PUT /api/lectures/{id}
     */
    @PutMapping("/lectures/{id}")
    public ResponseEntity<?> updateLecture(
            @PathVariable Long id, 
            @Valid @RequestBody LectureUpdateDto updateDto) {

        try {
            logger.info("講義更新開始 - ID: {}", id);

            Optional<LectureResponseDto> updatedLecture = 
                lectureService.updateLecture(id, updateDto);

            if (updatedLecture.isPresent()) {
                logger.info("講義更新成功 - ID: {}", id);
                return ResponseEntity.ok(updatedLecture.get());
            } else {
                logger.warn("更新対象講義が見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (IllegalArgumentException e) {
            logger.warn("講義更新バリデーションエラー - ID: {}, エラー: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("講義更新エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("講義の更新に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 講義削除
     * DELETE /api/lectures/{id}
     */
    @DeleteMapping("/lectures/{id}")
    public ResponseEntity<?> deleteLecture(@PathVariable Long id) {
        try {
            logger.info("講義削除開始 - ID: {}", id);

            boolean deleted = lectureService.deleteLecture(id);

            if (deleted) {
                logger.info("講義削除成功 - ID: {}", id);
                return ResponseEntity.noContent().build();
            } else {
                logger.warn("削除対象講義が見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("講義削除エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("講義の削除に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 講義検索
     * GET /api/lectures/search
     */
    @GetMapping("/lectures/search")
    public ResponseEntity<?> searchLectures(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long programId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) Long instructorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        try {
            logger.info("講義検索開始 - タイトル: {}, プログラムID: {}, ステータス: {}", 
                       title, programId, status);

            LectureSearchDto searchDto = new LectureSearchDto();
            searchDto.setTitle(title);
            searchDto.setDescription(description);
            searchDto.setProgramId(programId);
            searchDto.setStatus(status);
            searchDto.setDifficulty(difficulty);
            searchDto.setInstructorId(instructorId);

            Page<LectureResponseDto> searchResult = 
                lectureService.searchLectures(searchDto, page, size, sortBy, sortDir);

            logger.info("講義検索成功 - 検索結果件数: {}", searchResult.getTotalElements());
            return ResponseEntity.ok(searchResult);

        } catch (Exception e) {
            logger.error("講義検索エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("講義検索に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 講義統計情報取得
     * GET /api/lectures/stats
     */
    @GetMapping("/lectures/stats")
    public ResponseEntity<?> getLectureStats(
            @RequestParam(required = false) Long programId,
            @RequestParam(required = false) String period) {

        try {
            logger.info("講義統計情報取得開始 - プログラムID: {}, 期間: {}", programId, period);

            LectureStatsDto stats = lectureService.getLectureStats(programId, period);

            logger.info("講義統計情報取得成功 - 総講義数: {}, 平均評価: {}", 
                       stats.getTotalLectures(), stats.getAverageRating());
            return ResponseEntity.ok(stats);

        } catch (Exception e) {
            logger.error("講義統計情報取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("講義統計情報の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 講義順序更新
     * PUT /api/training-programs/{programId}/lectures/reorder
     */
    @PutMapping("/training-programs/{programId}/lectures/reorder")
    public ResponseEntity<?> reorderLectures(
            @PathVariable Long programId,
            @RequestBody List<Long> lectureIds) {

        try {
            logger.info("講義順序更新開始 - プログラムID: {}, 講義数: {}", 
                       programId, lectureIds.size());

            List<LectureResponseDto> reorderedLectures = 
                lectureService.reorderLectures(programId, lectureIds);

            logger.info("講義順序更新成功 - プログラムID: {}", programId);
            return ResponseEntity.ok(reorderedLectures);

        } catch (IllegalArgumentException e) {
            logger.warn("講義順序更新バリデーションエラー - プログラムID: {}, エラー: {}", 
                       programId, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("講義順序更新エラー - プログラムID: {}", programId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("講義順序の更新に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 講義複製
     * POST /api/lectures/{id}/duplicate
     */
    @PostMapping("/lectures/{id}/duplicate")
    public ResponseEntity<?> duplicateLecture(
            @PathVariable Long id,
            @RequestParam(required = false) Long targetProgramId) {

        try {
            logger.info("講義複製開始 - 元講義ID: {}, 対象プログラムID: {}", id, targetProgramId);

            LectureResponseDto duplicatedLecture = 
                lectureService.duplicateLecture(id, targetProgramId);

            logger.info("講義複製成功 - 新講義ID: {}", duplicatedLecture.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(duplicatedLecture);

        } catch (IllegalArgumentException e) {
            logger.warn("講義複製バリデーションエラー - ID: {}, エラー: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("講義複製エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("講義の複製に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 講義状態更新
     * PUT /api/lectures/{id}/status
     */
    @PutMapping("/lectures/{id}/status")
    public ResponseEntity<?> updateLectureStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String reason) {

        try {
            logger.info("講義状態更新開始 - ID: {}, ステータス: {}", id, status);

            LectureResponseDto updatedLecture = 
                lectureService.updateLectureStatus(id, status, reason);

            logger.info("講義状態更新成功 - ID: {}, 新ステータス: {}", id, status);
            return ResponseEntity.ok(updatedLecture);

        } catch (IllegalArgumentException e) {
            logger.warn("講義状態更新バリデーションエラー - ID: {}, エラー: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("講義状態更新エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("講義状態の更新に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 講義評価取得
     * GET /api/lectures/{id}/ratings
     */
    @GetMapping("/lectures/{id}/ratings")
    public ResponseEntity<?> getLectureRatings(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            logger.info("講義評価取得開始 - 講義ID: {}", id);

            Page<LectureRatingDto> ratings = lectureService.getLectureRatings(id, page, size);

            logger.info("講義評価取得成功 - 講義ID: {}, 評価数: {}", 
                       id, ratings.getTotalElements());
            return ResponseEntity.ok(ratings);

        } catch (Exception e) {
            logger.error("講義評価取得エラー - 講義ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("講義評価の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 講義受講者取得
     * GET /api/lectures/{id}/students
     */
    @GetMapping("/lectures/{id}/students")
    public ResponseEntity<?> getLectureStudents(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "enrollmentDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {

        try {
            logger.info("講義受講者取得開始 - 講義ID: {}", id);

            Page<StudentLectureDto> students = lectureService.getLectureStudents(
                id, page, size, sortBy, sortDir);

            logger.info("講義受講者取得成功 - 講義ID: {}, 受講者数: {}", 
                       id, students.getTotalElements());
            return ResponseEntity.ok(students);

        } catch (Exception e) {
            logger.error("講義受講者取得エラー - 講義ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("講義受講者の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 講義進捗サマリー取得
     * GET /api/lectures/{id}/progress-summary
     */
    @GetMapping("/lectures/{id}/progress-summary")
    public ResponseEntity<?> getLectureProgressSummary(@PathVariable Long id) {
        try {
            logger.info("講義進捗サマリー取得開始 - 講義ID: {}", id);

            LectureProgressSummaryDto progressSummary = 
                lectureService.getLectureProgressSummary(id);

            logger.info("講義進捗サマリー取得成功 - 講義ID: {}, 完了率: {}%", 
                       id, progressSummary.getCompletionRate());
            return ResponseEntity.ok(progressSummary);

        } catch (Exception e) {
            logger.error("講義進捗サマリー取得エラー - 講義ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("講義進捗サマリーの取得に失敗しました: " + e.getMessage());
        }
    }
}