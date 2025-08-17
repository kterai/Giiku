package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.ProgramSchedule;
import jp.co.apsa.giiku.service.ProgramScheduleService;
import jp.co.apsa.giiku.dto.ProgramScheduleCreateDto;
import jp.co.apsa.giiku.dto.ProgramScheduleUpdateDto;
import jp.co.apsa.giiku.dto.ProgramScheduleResponseDto;
import jp.co.apsa.giiku.dto.ProgramScheduleSearchDto;
import jp.co.apsa.giiku.dto.ProgramScheduleStatsDto;

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
 * プログラムスケジュール管理コントローラー
 * プログラムスケジュールのCRUD操作、検索、統計機能を提供
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProgramScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(ProgramScheduleController.class);

    @Autowired
    private ProgramScheduleService programScheduleService;

    /**
     * プログラムスケジュール一覧取得
     * GET /api/program-schedules
     */
    @GetMapping("/program-schedules")
    public ResponseEntity<?> getAllProgramSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        try {
            logger.info("プログラムスケジュール一覧取得開始 - page: {}, size: {}, sortBy: {}, sortDir: {}", 
                       page, size, sortBy, sortDir);

            Page<ProgramScheduleResponseDto> schedules = programScheduleService.getAllProgramSchedules(
                page, size, sortBy, sortDir);

            logger.info("プログラムスケジュール一覧取得成功 - 総件数: {}", schedules.getTotalElements());
            return ResponseEntity.ok(schedules);

        } catch (Exception e) {
            logger.error("プログラムスケジュール一覧取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("プログラムスケジュール一覧の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * プログラムスケジュール詳細取得
     * GET /api/program-schedules/{id}
     */
    @GetMapping("/program-schedules/{id}")
    public ResponseEntity<?> getProgramScheduleById(@PathVariable Long id) {
        try {
            logger.info("プログラムスケジュール詳細取得開始 - ID: {}", id);

            Optional<ProgramScheduleResponseDto> schedule = programScheduleService.getProgramScheduleById(id);

            if (schedule.isPresent()) {
                logger.info("プログラムスケジュール詳細取得成功 - ID: {}", id);
                return ResponseEntity.ok(schedule.get());
            } else {
                logger.warn("プログラムスケジュールが見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("プログラムスケジュール詳細取得エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("プログラムスケジュール詳細の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * プログラム別スケジュール取得
     * GET /api/training-programs/{programId}/schedules
     */
    @GetMapping("/training-programs/{programId}/schedules")
    public ResponseEntity<?> getSchedulesByProgramId(
            @PathVariable Long programId,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        try {
            logger.info("プログラム別スケジュール取得開始 - プログラムID: {}", programId);

            List<ProgramScheduleResponseDto> schedules = programScheduleService.getSchedulesByProgramId(
                programId, sortBy, sortDir);

            logger.info("プログラム別スケジュール取得成功 - プログラムID: {}, スケジュール数: {}", 
                       programId, schedules.size());
            return ResponseEntity.ok(schedules);

        } catch (Exception e) {
            logger.error("プログラム別スケジュール取得エラー - プログラムID: {}", programId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("プログラム別スケジュールの取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 期間別スケジュール取得
     * GET /api/program-schedules/by-period
     */
    @GetMapping("/program-schedules/by-period")
    public ResponseEntity<?> getSchedulesByPeriod(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) Long programId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            logger.info("期間別スケジュール取得開始 - 開始日: {}, 終了日: {}, プログラムID: {}", 
                       startDate, endDate, programId);

            Page<ProgramScheduleResponseDto> schedules = programScheduleService.getSchedulesByPeriod(
                startDate, endDate, programId, page, size);

            logger.info("期間別スケジュール取得成功 - 期間: {} ~ {}, 件数: {}", 
                       startDate, endDate, schedules.getTotalElements());
            return ResponseEntity.ok(schedules);

        } catch (Exception e) {
            logger.error("期間別スケジュール取得エラー - 期間: {} ~ {}", startDate, endDate, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("期間別スケジュールの取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * プログラムスケジュール作成
     * POST /api/program-schedules
     */
    @PostMapping("/program-schedules")
    public ResponseEntity<?> createProgramSchedule(@Valid @RequestBody ProgramScheduleCreateDto createDto) {
        try {
            logger.info("プログラムスケジュール作成開始 - プログラムID: {}, 開始日: {}", 
                       createDto.getProgramId(), createDto.getStartDate());

            ProgramScheduleResponseDto createdSchedule = programScheduleService.createProgramSchedule(createDto);

            logger.info("プログラムスケジュール作成成功 - ID: {}, プログラムID: {}", 
                       createdSchedule.getId(), createdSchedule.getProgramId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);

        } catch (IllegalArgumentException e) {
            logger.warn("プログラムスケジュール作成バリデーションエラー: {}", e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("プログラムスケジュール作成エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("プログラムスケジュールの作成に失敗しました: " + e.getMessage());
        }
    }

    /**
     * プログラムスケジュール更新
     * PUT /api/program-schedules/{id}
     */
    @PutMapping("/program-schedules/{id}")
    public ResponseEntity<?> updateProgramSchedule(
            @PathVariable Long id, 
            @Valid @RequestBody ProgramScheduleUpdateDto updateDto) {

        try {
            logger.info("プログラムスケジュール更新開始 - ID: {}", id);

            Optional<ProgramScheduleResponseDto> updatedSchedule = 
                programScheduleService.updateProgramSchedule(id, updateDto);

            if (updatedSchedule.isPresent()) {
                logger.info("プログラムスケジュール更新成功 - ID: {}", id);
                return ResponseEntity.ok(updatedSchedule.get());
            } else {
                logger.warn("更新対象プログラムスケジュールが見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (IllegalArgumentException e) {
            logger.warn("プログラムスケジュール更新バリデーションエラー - ID: {}, エラー: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("プログラムスケジュール更新エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("プログラムスケジュールの更新に失敗しました: " + e.getMessage());
        }
    }

    /**
     * プログラムスケジュール削除
     * DELETE /api/program-schedules/{id}
     */
    @DeleteMapping("/program-schedules/{id}")
    public ResponseEntity<?> deleteProgramSchedule(@PathVariable Long id) {
        try {
            logger.info("プログラムスケジュール削除開始 - ID: {}", id);

            boolean deleted = programScheduleService.deleteProgramSchedule(id);

            if (deleted) {
                logger.info("プログラムスケジュール削除成功 - ID: {}", id);
                return ResponseEntity.noContent().build();
            } else {
                logger.warn("削除対象プログラムスケジュールが見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("プログラムスケジュール削除エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("プログラムスケジュールの削除に失敗しました: " + e.getMessage());
        }
    }

    /**
     * スケジュール一括作成
     * POST /api/program-schedules/batch-create
     */
    @PostMapping("/program-schedules/batch-create")
    public ResponseEntity<?> batchCreateSchedules(@Valid @RequestBody List<ProgramScheduleCreateDto> createDtos) {
        try {
            logger.info("スケジュール一括作成開始 - 作成件数: {}", createDtos.size());

            List<ProgramScheduleResponseDto> createdSchedules = 
                programScheduleService.batchCreateSchedules(createDtos);

            logger.info("スケジュール一括作成成功 - 作成済み件数: {}", createdSchedules.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedules);

        } catch (IllegalArgumentException e) {
            logger.warn("スケジュール一括作成バリデーションエラー: {}", e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("スケジュール一括作成エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("スケジュールの一括作成に失敗しました: " + e.getMessage());
        }
    }

    /**
     * スケジュール検索
     * GET /api/program-schedules/search
     */
    @GetMapping("/program-schedules/search")
    public ResponseEntity<?> searchProgramSchedules(
            @RequestParam(required = false) Long programId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDateFrom,
            @RequestParam(required = false) String startDateTo,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Long instructorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        try {
            logger.info("スケジュール検索開始 - プログラムID: {}, ステータス: {}, 場所: {}", 
                       programId, status, location);

            ProgramScheduleSearchDto searchDto = new ProgramScheduleSearchDto();
            searchDto.setProgramId(programId);
            searchDto.setStatus(status);
            searchDto.setStartDateFrom(startDateFrom);
            searchDto.setStartDateTo(startDateTo);
            searchDto.setLocation(location);
            searchDto.setInstructorId(instructorId);

            Page<ProgramScheduleResponseDto> searchResult = 
                programScheduleService.searchProgramSchedules(searchDto, page, size, sortBy, sortDir);

            logger.info("スケジュール検索成功 - 検索結果件数: {}", searchResult.getTotalElements());
            return ResponseEntity.ok(searchResult);

        } catch (Exception e) {
            logger.error("スケジュール検索エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("スケジュール検索に失敗しました: " + e.getMessage());
        }
    }

    /**
     * スケジュール統計情報取得
     * GET /api/program-schedules/stats
     */
    @GetMapping("/program-schedules/stats")
    public ResponseEntity<?> getProgramScheduleStats(
            @RequestParam(required = false) Long programId,
            @RequestParam(required = false) String period) {

        try {
            logger.info("スケジュール統計情報取得開始 - プログラムID: {}, 期間: {}", programId, period);

            ProgramScheduleStatsDto stats = programScheduleService.getProgramScheduleStats(programId, period);

            logger.info("スケジュール統計情報取得成功 - 総スケジュール数: {}, 実施率: {}%", 
                       stats.getTotalSchedules(), stats.getCompletionRate());
            return ResponseEntity.ok(stats);

        } catch (Exception e) {
            logger.error("スケジュール統計情報取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("スケジュール統計情報の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * スケジュール競合チェック
     * POST /api/program-schedules/check-conflicts
     */
    @PostMapping("/program-schedules/check-conflicts")
    public ResponseEntity<?> checkScheduleConflicts(@Valid @RequestBody ProgramScheduleCreateDto scheduleDto) {
        try {
            logger.info("スケジュール競合チェック開始 - 開始日: {}, 終了日: {}", 
                       scheduleDto.getStartDate(), scheduleDto.getEndDate());

            List<ProgramScheduleResponseDto> conflicts = 
                programScheduleService.checkScheduleConflicts(scheduleDto);

            logger.info("スケジュール競合チェック完了 - 競合件数: {}", conflicts.size());
            return ResponseEntity.ok(new ConflictCheckResponse(conflicts));

        } catch (Exception e) {
            logger.error("スケジュール競合チェックエラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("スケジュール競合チェックに失敗しました: " + e.getMessage());
        }
    }

    /**
     * スケジュール複製
     * POST /api/program-schedules/{id}/duplicate
     */
    @PostMapping("/program-schedules/{id}/duplicate")
    public ResponseEntity<?> duplicateSchedule(
            @PathVariable Long id,
            @RequestParam String newStartDate,
            @RequestParam(required = false) String newEndDate) {

        try {
            logger.info("スケジュール複製開始 - 元スケジュールID: {}, 新開始日: {}", id, newStartDate);

            ProgramScheduleResponseDto duplicatedSchedule = 
                programScheduleService.duplicateSchedule(id, newStartDate, newEndDate);

            logger.info("スケジュール複製成功 - 新スケジュールID: {}", duplicatedSchedule.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(duplicatedSchedule);

        } catch (IllegalArgumentException e) {
            logger.warn("スケジュール複製バリデーションエラー - ID: {}, エラー: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("スケジュール複製エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("スケジュールの複製に失敗しました: " + e.getMessage());
        }
    }

    /** 競合チェック結果レスポンス用クラス */
    public static class ConflictCheckResponse {
        private List<ProgramScheduleResponseDto> conflicts;
        private boolean hasConflicts;
        /** ConflictCheckResponse メソッド */
        public ConflictCheckResponse(List<ProgramScheduleResponseDto> conflicts) {
            this.conflicts = conflicts;
            this.hasConflicts = conflicts != null && !conflicts.isEmpty();
        }
        /** getConflicts メソッド */
        public List<ProgramScheduleResponseDto> getConflicts() {
            return conflicts;
        }
        /** setConflicts メソッド */
        public void setConflicts(List<ProgramScheduleResponseDto> conflicts) {
            this.conflicts = conflicts;
            this.hasConflicts = conflicts != null && !conflicts.isEmpty();
        }
        /** isHasConflicts メソッド */
        public boolean isHasConflicts() {
            return hasConflicts;
        }
        /** setHasConflicts メソッド */
        public void setHasConflicts(boolean hasConflicts) {
            this.hasConflicts = hasConflicts;
        }
    }
}
