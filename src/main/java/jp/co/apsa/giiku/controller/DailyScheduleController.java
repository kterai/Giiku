package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.DailySchedule;
import jp.co.apsa.giiku.service.DailyScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 日次スケジュール管理コントローラー
 * 日次スケジュールのCRUD操作とカレンダー機能を提供
 */
@RestController
@RequestMapping("/api/daily-schedules")
@Validated
@CrossOrigin(origins = "*")
public class DailyScheduleController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(DailyScheduleController.class);

    @Autowired
    private DailyScheduleService dailyScheduleService;

    /**
     * 全日次スケジュール一覧取得
     * 
     * @param page ページ番号（0から開始）
     * @param size ページサイズ
     * @param sortBy ソート項目
     * @param sortDir ソート方向（ASC/DESC）
     * @return 日次スケジュール一覧
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllDailySchedules(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(defaultValue = "scheduleDate") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        logger.info("日次スケジュール一覧取得: page={}, size={}, sortBy={}, sortDir={}", 
                   page, size, sortBy, sortDir);

        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDir);
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

            Page<DailySchedule> schedulePage = dailyScheduleService.findAll(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("schedules", schedulePage.getContent());
            response.put("currentPage", schedulePage.getNumber());
            response.put("totalPages", schedulePage.getTotalPages());
            response.put("totalElements", schedulePage.getTotalElements());
            response.put("hasNext", schedulePage.hasNext());
            response.put("hasPrevious", schedulePage.hasPrevious());

            logger.info("日次スケジュール一覧取得成功: 総件数={}", schedulePage.getTotalElements());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("日次スケジュール一覧取得エラー", e);
            return createErrorResponse("日次スケジュール一覧の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 日次スケジュール詳細取得
     * 
     * @param id 日次スケジュールID
     * @return 日次スケジュール詳細
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getDailyScheduleById(@PathVariable Long id) {
        logger.info("日次スケジュール詳細取得: id={}", id);

        try {
            Optional<DailySchedule> schedule = dailyScheduleService.findById(id);

            if (schedule.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("schedule", schedule.get());
                logger.info("日次スケジュール詳細取得成功: id={}", id);
                return ResponseEntity.ok(response);
            } else {
                logger.warn("日次スケジュールが見つかりません: id={}", id);
                return createErrorResponse("指定された日次スケジュールが見つかりません", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            logger.error("日次スケジュール詳細取得エラー: id={}", id, e);
            return createErrorResponse("日次スケジュール詳細の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 日次スケジュール新規作成
     * 
     * @param schedule 日次スケジュール情報
     * @return 作成された日次スケジュール
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createDailySchedule(@Valid @RequestBody DailySchedule schedule) {
        logger.info("日次スケジュール新規作成: date={}, title={}", 
                   schedule.getScheduleDate(), schedule.getTitle());

        try {
            DailySchedule savedSchedule = dailyScheduleService.save(schedule);

            Map<String, Object> response = new HashMap<>();
            response.put("schedule", savedSchedule);
            response.put("message", "日次スケジュールを正常に作成しました");

            logger.info("日次スケジュール新規作成成功: id={}, date={}, title={}", 
                       savedSchedule.getId(), savedSchedule.getScheduleDate(), savedSchedule.getTitle());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logger.error("日次スケジュール新規作成エラー: date={}, title={}", 
                        schedule.getScheduleDate(), schedule.getTitle(), e);
            return createErrorResponse("日次スケジュールの作成に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 日次スケジュール更新
     * 
     * @param id 日次スケジュールID
     * @param schedule 更新する日次スケジュール情報
     * @return 更新された日次スケジュール
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateDailySchedule(
            @PathVariable Long id, 
            @Valid @RequestBody DailySchedule schedule) {

        logger.info("日次スケジュール更新: id={}, date={}, title={}", 
                   id, schedule.getScheduleDate(), schedule.getTitle());

        try {
            Optional<DailySchedule> existingSchedule = dailyScheduleService.findById(id);

            if (existingSchedule.isPresent()) {
                schedule.setId(id);
                DailySchedule updatedSchedule = dailyScheduleService.save(schedule);

                Map<String, Object> response = new HashMap<>();
                response.put("schedule", updatedSchedule);
                response.put("message", "日次スケジュールを正常に更新しました");

                logger.info("日次スケジュール更新成功: id={}, date={}, title={}", 
                           updatedSchedule.getId(), updatedSchedule.getScheduleDate(), updatedSchedule.getTitle());
                return ResponseEntity.ok(response);
            } else {
                logger.warn("更新対象の日次スケジュールが見つかりません: id={}", id);
                return createErrorResponse("指定された日次スケジュールが見つかりません", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            logger.error("日次スケジュール更新エラー: id={}", id, e);
            return createErrorResponse("日次スケジュールの更新に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 日次スケジュール削除
     * 
     * @param id 日次スケジュールID
     * @return 削除結果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteDailySchedule(@PathVariable Long id) {
        logger.info("日次スケジュール削除: id={}", id);

        try {
            Optional<DailySchedule> existingSchedule = dailyScheduleService.findById(id);

            if (existingSchedule.isPresent()) {
                dailyScheduleService.deleteById(id);

                Map<String, Object> response = new HashMap<>();
                response.put("message", "日次スケジュールを正常に削除しました");
                response.put("deletedId", id);

                logger.info("日次スケジュール削除成功: id={}", id);
                return ResponseEntity.ok(response);
            } else {
                logger.warn("削除対象の日次スケジュールが見つかりません: id={}", id);
                return createErrorResponse("指定された日次スケジュールが見つかりません", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            logger.error("日次スケジュール削除エラー: id={}", id, e);
            return createErrorResponse("日次スケジュールの削除に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 指定日のスケジュール一覧取得
     * 
     * @param date 対象日付（yyyy-MM-dd形式）
     * @return 指定日のスケジュール一覧
     */
    @GetMapping("/date/{date}")
    public ResponseEntity<Map<String, Object>> getSchedulesByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        logger.info("指定日のスケジュール一覧取得: date={}", date);

        try {
            List<DailySchedule> schedules = dailyScheduleService.findByScheduleDate(date);

            Map<String, Object> response = new HashMap<>();
            response.put("schedules", schedules);
            response.put("date", date);
            response.put("count", schedules.size());

            logger.info("指定日のスケジュール一覧取得成功: date={}, 件数={}", date, schedules.size());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("指定日のスケジュール一覧取得エラー: date={}", date, e);
            return createErrorResponse("指定日のスケジュール一覧の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 期間内のスケジュール一覧取得
     * 
     * @param startDate 開始日（yyyy-MM-dd形式）
     * @param endDate 終了日（yyyy-MM-dd形式）
     * @param page ページ番号
     * @param size ページサイズ
     * @return 期間内のスケジュール一覧
     */
    @GetMapping("/range")
    public ResponseEntity<Map<String, Object>> getSchedulesByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "50") @Min(1) int size) {

        logger.info("期間内のスケジュール一覧取得: startDate={}, endDate={}, page={}, size={}", 
                   startDate, endDate, page, size);

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "scheduleDate", "startTime"));
            Page<DailySchedule> schedulePage = dailyScheduleService.findByScheduleDateBetween(
                startDate, endDate, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("schedules", schedulePage.getContent());
            response.put("currentPage", schedulePage.getNumber());
            response.put("totalPages", schedulePage.getTotalPages());
            response.put("totalElements", schedulePage.getTotalElements());
            response.put("startDate", startDate);
            response.put("endDate", endDate);

            logger.info("期間内のスケジュール一覧取得成功: startDate={}, endDate={}, 総件数={}", 
                       startDate, endDate, schedulePage.getTotalElements());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("期間内のスケジュール一覧取得エラー: startDate={}, endDate={}", startDate, endDate, e);
            return createErrorResponse("期間内のスケジュール一覧の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 今日のスケジュール一覧取得
     * 
     * @return 今日のスケジュール一覧
     */
    @GetMapping("/today")
    public ResponseEntity<Map<String, Object>> getTodaySchedules() {
        LocalDate today = LocalDate.now();
        logger.info("今日のスケジュール一覧取得: date={}", today);

        try {
            List<DailySchedule> schedules = dailyScheduleService.findByScheduleDate(today);

            Map<String, Object> response = new HashMap<>();
            response.put("schedules", schedules);
            response.put("date", today);
            response.put("count", schedules.size());

            logger.info("今日のスケジュール一覧取得成功: date={}, 件数={}", today, schedules.size());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("今日のスケジュール一覧取得エラー: date={}", today, e);
            return createErrorResponse("今日のスケジュール一覧の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 明日のスケジュール一覧取得
     * 
     * @return 明日のスケジュール一覧
     */
    @GetMapping("/tomorrow")
    public ResponseEntity<Map<String, Object>> getTomorrowSchedules() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        logger.info("明日のスケジュール一覧取得: date={}", tomorrow);

        try {
            List<DailySchedule> schedules = dailyScheduleService.findByScheduleDate(tomorrow);

            Map<String, Object> response = new HashMap<>();
            response.put("schedules", schedules);
            response.put("date", tomorrow);
            response.put("count", schedules.size());

            logger.info("明日のスケジュール一覧取得成功: date={}, 件数={}", tomorrow, schedules.size());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("明日のスケジュール一覧取得エラー: date={}", tomorrow, e);
            return createErrorResponse("明日のスケジュール一覧の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 今週のスケジュール一覧取得
     * 
     * @return 今週のスケジュール一覧
     */
    @GetMapping("/this-week")
    public ResponseEntity<Map<String, Object>> getThisWeekSchedules() {
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.minusDays(now.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        logger.info("今週のスケジュール一覧取得: startDate={}, endDate={}", startOfWeek, endOfWeek);

        try {
            List<DailySchedule> schedules = dailyScheduleService.findByScheduleDateBetween(startOfWeek, endOfWeek);

            Map<String, Object> response = new HashMap<>();
            response.put("schedules", schedules);
            response.put("startDate", startOfWeek);
            response.put("endDate", endOfWeek);
            response.put("count", schedules.size());

            logger.info("今週のスケジュール一覧取得成功: startDate={}, endDate={}, 件数={}", 
                       startOfWeek, endOfWeek, schedules.size());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("今週のスケジュール一覧取得エラー: startDate={}, endDate={}", startOfWeek, endOfWeek, e);
            return createErrorResponse("今週のスケジュール一覧の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 学生のスケジュール一覧取得
     * 
     * @param studentId 学生ID
     * @param startDate 開始日（オプション）
     * @param endDate 終了日（オプション）
     * @param page ページ番号
     * @param size ページサイズ
     * @return 学生のスケジュール一覧
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<Map<String, Object>> getStudentSchedules(
            @PathVariable Long studentId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) int size) {

        logger.info("学生のスケジュール一覧取得: studentId={}, startDate={}, endDate={}, page={}, size={}", 
                   studentId, startDate, endDate, page, size);

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "scheduleDate", "startTime"));
            Page<DailySchedule> schedulePage;

            if (startDate != null && endDate != null) {
                schedulePage = dailyScheduleService.findByStudentIdAndScheduleDateBetween(
                    studentId, startDate, endDate, pageable);
            } else {
                schedulePage = dailyScheduleService.findByStudentId(studentId, pageable);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("schedules", schedulePage.getContent());
            response.put("currentPage", schedulePage.getNumber());
            response.put("totalPages", schedulePage.getTotalPages());
            response.put("totalElements", schedulePage.getTotalElements());
            response.put("studentId", studentId);
            if (startDate != null) response.put("startDate", startDate);
            if (endDate != null) response.put("endDate", endDate);

            logger.info("学生のスケジュール一覧取得成功: studentId={}, 総件数={}", 
                       studentId, schedulePage.getTotalElements());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("学生のスケジュール一覧取得エラー: studentId={}", studentId, e);
            return createErrorResponse("学生のスケジュール一覧の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * スケジュール検索
     * 
     * @param keyword 検索キーワード
     * @param page ページ番号
     * @param size ページサイズ
     * @return 検索結果
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchSchedules(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size) {

        logger.info("スケジュール検索: keyword={}, page={}, size={}", keyword, page, size);

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "scheduleDate"));
            Page<DailySchedule> searchResults = dailyScheduleService.searchSchedules(keyword, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("schedules", searchResults.getContent());
            response.put("currentPage", searchResults.getNumber());
            response.put("totalPages", searchResults.getTotalPages());
            response.put("totalElements", searchResults.getTotalElements());
            response.put("keyword", keyword);

            logger.info("スケジュール検索成功: keyword={}, 件数={}", keyword, searchResults.getTotalElements());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("スケジュール検索エラー: keyword={}", keyword, e);
            return createErrorResponse("スケジュールの検索に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * スケジュール統計情報取得
     * 
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 統計情報
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getScheduleStatistics(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        logger.info("スケジュール統計情報取得: startDate={}, endDate={}", startDate, endDate);

        try {
            Map<String, Object> statistics = dailyScheduleService.getStatistics(startDate, endDate);

            Map<String, Object> response = new HashMap<>();
            response.put("statistics", statistics);
            response.put("startDate", startDate);
            response.put("endDate", endDate);

            logger.info("スケジュール統計情報取得成功: startDate={}, endDate={}", startDate, endDate);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("スケジュール統計情報取得エラー: startDate={}, endDate={}", startDate, endDate, e);
            return createErrorResponse("スケジュール統計情報の取得に失敗しました", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}