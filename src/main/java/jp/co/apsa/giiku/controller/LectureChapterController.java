package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.LectureChapter;
import jp.co.apsa.giiku.service.LectureChapterService;
import jp.co.apsa.giiku.dto.LectureChapterCreateDto;
import jp.co.apsa.giiku.dto.LectureChapterUpdateDto;
import jp.co.apsa.giiku.dto.LectureChapterResponseDto;
import jp.co.apsa.giiku.dto.LectureChapterSearchDto;
import jp.co.apsa.giiku.dto.LectureChapterStatsDto;
import jp.co.apsa.giiku.dto.LectureChapterProgressDto;

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
 * 講義チャプター管理コントローラー
 * 講義チャプターのCRUD操作、進捗管理、検索、統計機能を提供
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LectureChapterController {

    private static final Logger logger = LoggerFactory.getLogger(LectureChapterController.class);

    @Autowired
    private LectureChapterService lectureChapterService;

    /**
     * チャプター一覧取得
     * GET /api/lecture-chapters
     */
    @GetMapping("/lecture-chapters")
    public ResponseEntity<?> getAllChapters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        try {
            logger.info("チャプター一覧取得開始 - page: {}, size: {}, sortBy: {}, sortDir: {}", 
                       page, size, sortBy, sortDir);

            Page<LectureChapterResponseDto> chapters = lectureChapterService.getAllChapters(
                page, size, sortBy, sortDir);

            logger.info("チャプター一覧取得成功 - 総件数: {}", chapters.getTotalElements());
            return ResponseEntity.ok(chapters);

        } catch (Exception e) {
            logger.error("チャプター一覧取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("チャプター一覧の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * チャプター詳細取得
     * GET /api/lecture-chapters/{id}
     */
    @GetMapping("/lecture-chapters/{id}")
    public ResponseEntity<?> getChapterById(@PathVariable Long id) {
        try {
            logger.info("チャプター詳細取得開始 - ID: {}", id);

            Optional<LectureChapterResponseDto> chapter = lectureChapterService.getChapterById(id);

            if (chapter.isPresent()) {
                logger.info("チャプター詳細取得成功 - ID: {}", id);
                return ResponseEntity.ok(chapter.get());
            } else {
                logger.warn("チャプターが見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("チャプター詳細取得エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("チャプター詳細の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 講義別チャプター取得
     * GET /api/lectures/{lectureId}/chapters
     */
    @GetMapping("/lectures/{lectureId}/chapters")
    public ResponseEntity<?> getChaptersByLectureId(
            @PathVariable Long lectureId,
            @RequestParam(defaultValue = "chapterOrder") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        try {
            logger.info("講義別チャプター取得開始 - 講義ID: {}", lectureId);

            List<LectureChapterResponseDto> chapters = lectureChapterService.getChaptersByLectureId(
                lectureId, sortBy, sortDir);

            logger.info("講義別チャプター取得成功 - 講義ID: {}, チャプター数: {}", 
                       lectureId, chapters.size());
            return ResponseEntity.ok(chapters);

        } catch (Exception e) {
            logger.error("講義別チャプター取得エラー - 講義ID: {}", lectureId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("講義別チャプターの取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * チャプター作成
     * POST /api/lecture-chapters
     */
    @PostMapping("/lecture-chapters")
    public ResponseEntity<?> createChapter(@Valid @RequestBody LectureChapterCreateDto createDto) {
        try {
            logger.info("チャプター作成開始 - タイトル: {}, 講義ID: {}", 
                       createDto.getTitle(), createDto.getLectureId());

            LectureChapterResponseDto createdChapter = lectureChapterService.createChapter(createDto);

            logger.info("チャプター作成成功 - ID: {}, タイトル: {}", 
                       createdChapter.getId(), createdChapter.getTitle());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdChapter);

        } catch (IllegalArgumentException e) {
            logger.warn("チャプター作成バリデーションエラー: {}", e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("チャプター作成エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("チャプターの作成に失敗しました: " + e.getMessage());
        }
    }

    /**
     * チャプター更新
     * PUT /api/lecture-chapters/{id}
     */
    @PutMapping("/lecture-chapters/{id}")
    public ResponseEntity<?> updateChapter(
            @PathVariable Long id, 
            @Valid @RequestBody LectureChapterUpdateDto updateDto) {

        try {
            logger.info("チャプター更新開始 - ID: {}", id);

            Optional<LectureChapterResponseDto> updatedChapter = 
                lectureChapterService.updateChapter(id, updateDto);

            if (updatedChapter.isPresent()) {
                logger.info("チャプター更新成功 - ID: {}", id);
                return ResponseEntity.ok(updatedChapter.get());
            } else {
                logger.warn("更新対象チャプターが見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (IllegalArgumentException e) {
            logger.warn("チャプター更新バリデーションエラー - ID: {}, エラー: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("チャプター更新エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("チャプターの更新に失敗しました: " + e.getMessage());
        }
    }

    /**
     * チャプター削除
     * DELETE /api/lecture-chapters/{id}
     */
    @DeleteMapping("/lecture-chapters/{id}")
    public ResponseEntity<?> deleteChapter(@PathVariable Long id) {
        try {
            logger.info("チャプター削除開始 - ID: {}", id);

            boolean deleted = lectureChapterService.deleteChapter(id);

            if (deleted) {
                logger.info("チャプター削除成功 - ID: {}", id);
                return ResponseEntity.noContent().build();
            } else {
                logger.warn("削除対象チャプターが見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("チャプター削除エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("チャプターの削除に失敗しました: " + e.getMessage());
        }
    }

    /**
     * チャプター進捗管理
     * GET /api/lecture-chapters/{id}/progress
     */
    @GetMapping("/lecture-chapters/{id}/progress")
    public ResponseEntity<?> getChapterProgress(
            @PathVariable Long id,
            @RequestParam(required = false) Long studentId) {

        try {
            logger.info("チャプター進捗取得開始 - チャプターID: {}, 学生ID: {}", id, studentId);

            List<LectureChapterProgressDto> progress = 
                lectureChapterService.getChapterProgress(id, studentId);

            logger.info("チャプター進捗取得成功 - チャプターID: {}, 進捗件数: {}", 
                       id, progress.size());
            return ResponseEntity.ok(progress);

        } catch (Exception e) {
            logger.error("チャプター進捗取得エラー - チャプターID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("チャプター進捗の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * チャプター進捗更新
     * PUT /api/lecture-chapters/{id}/progress
     */
    @PutMapping("/lecture-chapters/{id}/progress")
    public ResponseEntity<?> updateChapterProgress(
            @PathVariable Long id,
            @Valid @RequestBody LectureChapterProgressDto progressDto) {

        try {
            logger.info("チャプター進捗更新開始 - チャプターID: {}, 学生ID: {}", 
                       id, progressDto.getStudentId());

            LectureChapterProgressDto updatedProgress = 
                lectureChapterService.updateChapterProgress(id, progressDto);

            logger.info("チャプター進捗更新成功 - チャプターID: {}, 完了率: {}%", 
                       id, updatedProgress.getCompletionRate());
            return ResponseEntity.ok(updatedProgress);

        } catch (IllegalArgumentException e) {
            logger.warn("チャプター進捗更新バリデーションエラー - チャプターID: {}, エラー: {}", 
                       id, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("チャプター進捗更新エラー - チャプターID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("チャプター進捗の更新に失敗しました: " + e.getMessage());
        }
    }

    /**
     * チャプター検索機能
     * GET /api/lecture-chapters/search
     */
    @GetMapping("/lecture-chapters/search")
    public ResponseEntity<?> searchChapters(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long lectureId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String difficulty,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        try {
            logger.info("チャプター検索開始 - タイトル: {}, 講義ID: {}, ステータス: {}", 
                       title, lectureId, status);

            LectureChapterSearchDto searchDto = new LectureChapterSearchDto();
            searchDto.setTitle(title);
            searchDto.setDescription(description);
            searchDto.setLectureId(lectureId);
            searchDto.setStatus(status);
            searchDto.setDifficulty(difficulty);

            Page<LectureChapterResponseDto> searchResult = 
                lectureChapterService.searchChapters(searchDto, page, size, sortBy, sortDir);

            logger.info("チャプター検索成功 - 検索結果件数: {}", searchResult.getTotalElements());
            return ResponseEntity.ok(searchResult);

        } catch (Exception e) {
            logger.error("チャプター検索エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("チャプター検索に失敗しました: " + e.getMessage());
        }
    }

    /**
     * チャプター統計情報取得
     * GET /api/lecture-chapters/stats
     */
    @GetMapping("/lecture-chapters/stats")
    public ResponseEntity<?> getChapterStats(
            @RequestParam(required = false) Long lectureId,
            @RequestParam(required = false) String period) {

        try {
            logger.info("チャプター統計情報取得開始 - 講義ID: {}, 期間: {}", lectureId, period);

            LectureChapterStatsDto stats = lectureChapterService.getChapterStats(lectureId, period);

            logger.info("チャプター統計情報取得成功 - 総チャプター数: {}, 完了率: {}%", 
                       stats.getTotalChapters(), stats.getAverageCompletionRate());
            return ResponseEntity.ok(stats);

        } catch (Exception e) {
            logger.error("チャプター統計情報取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("チャプター統計情報の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 講義のチャプター順序更新
     * PUT /api/lectures/{lectureId}/chapters/reorder
     */
    @PutMapping("/lectures/{lectureId}/chapters/reorder")
    public ResponseEntity<?> reorderChapters(
            @PathVariable Long lectureId,
            @RequestBody List<Long> chapterIds) {

        try {
            logger.info("チャプター順序更新開始 - 講義ID: {}, チャプター数: {}", 
                       lectureId, chapterIds.size());

            List<LectureChapterResponseDto> reorderedChapters = 
                lectureChapterService.reorderChapters(lectureId, chapterIds);

            logger.info("チャプター順序更新成功 - 講義ID: {}", lectureId);
            return ResponseEntity.ok(reorderedChapters);

        } catch (IllegalArgumentException e) {
            logger.warn("チャプター順序更新バリデーションエラー - 講義ID: {}, エラー: {}", 
                       lectureId, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("チャプター順序更新エラー - 講義ID: {}", lectureId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("チャプター順序の更新に失敗しました: " + e.getMessage());
        }
    }

    /**
     * チャプター複製
     * POST /api/lecture-chapters/{id}/duplicate
     */
    @PostMapping("/lecture-chapters/{id}/duplicate")
    public ResponseEntity<?> duplicateChapter(
            @PathVariable Long id,
            @RequestParam(required = false) Long targetLectureId) {

        try {
            logger.info("チャプター複製開始 - 元チャプターID: {}, 対象講義ID: {}", id, targetLectureId);

            LectureChapterResponseDto duplicatedChapter = 
                lectureChapterService.duplicateChapter(id, targetLectureId);

            logger.info("チャプター複製成功 - 新チャプターID: {}", duplicatedChapter.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(duplicatedChapter);

        } catch (IllegalArgumentException e) {
            logger.warn("チャプター複製バリデーションエラー - ID: {}, エラー: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("チャプター複製エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("チャプターの複製に失敗しました: " + e.getMessage());
        }
    }
}
