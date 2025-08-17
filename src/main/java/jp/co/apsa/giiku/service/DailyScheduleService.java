package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.DailySchedule;
import jp.co.apsa.giiku.domain.entity.ProgramSchedule;
import jp.co.apsa.giiku.domain.repository.DailyScheduleRepository;
import jp.co.apsa.giiku.domain.repository.ProgramScheduleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.Map;

/**
 * DailySchedule（日次スケジュール）に関するビジネスロジックを提供するサービスクラス。
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class DailyScheduleService {

    @Autowired
    private DailyScheduleRepository dailyScheduleRepository;

    @Autowired
    private ProgramScheduleRepository programScheduleRepository;

    /**
     * 全ての日次スケジュールを取得
     *
     * @return 日次スケジュールのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<DailySchedule> findAll() {
        return dailyScheduleRepository.findAll();
    }

    /**
     * 全ての日次スケジュールをページング取得します。
     *
     * @param pageable ページング情報
     * @return ページングされた日次スケジュール
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Page<DailySchedule> findAll(Pageable pageable) {
        return dailyScheduleRepository.findAll(pageable);
    }

    /**
     * IDで日次スケジュールを取得
     * 
     * @param id 日次スケジュールID
     * @return Optional<DailySchedule>
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Optional<DailySchedule> findById(Long id) {
        return dailyScheduleRepository.findById(id);
    }

    /**
     * プログラムスケジュールIDで日次スケジュールを取得
     * 
     * @param programScheduleId プログラムスケジュールID
     * @return 日次スケジュールのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<DailySchedule> findByProgramScheduleId(Long programScheduleId) {
        return dailyScheduleRepository.findByProgramScheduleIdOrderByTargetDateAscStartTimeAsc(programScheduleId);
    }

    /**
     * 指定日の日次スケジュールを取得
     * 
     * @param scheduleDate スケジュール日
     * @return 日次スケジュールのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<DailySchedule> findByScheduleDate(LocalDate scheduleDate) {
        return dailyScheduleRepository.findByTargetDateOrderByStartTimeAsc(scheduleDate);
    }

    /**
     * 期間内の日次スケジュールを取得
     * 
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 日次スケジュールのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true) 
    public List<DailySchedule> findSchedulesWithinPeriod(LocalDate startDate, LocalDate endDate) {
        return dailyScheduleRepository.findByTargetDateBetweenOrderByTargetDateAscStartTimeAsc(startDate, endDate);
    }

    /**
     * 今日の日次スケジュールを取得
     * 
     * @return 今日の日次スケジュールのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<DailySchedule> findTodaySchedules() {
        LocalDate today = LocalDate.now();
        return findByScheduleDate(today);
    }

    /**
     * 今週の日次スケジュールを取得
     * 
     * @return 今週の日次スケジュールのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<DailySchedule> findThisWeekSchedules() {
        LocalDate startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return findSchedulesWithinPeriod(startOfWeek, endOfWeek);
    }

    /**
     * 複合条件で日次スケジュールを検索
     * 
     * @param programScheduleId プログラムスケジュールID（オプション）
     * @param scheduleDate スケジュール日（オプション）
     * @param dayOfWeek 曜日（オプション）
     * @param status ステータス（オプション）
     * @param pageable ページング情報
     * @return ページング対応の日次スケジュール
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Page<DailySchedule> searchSchedules(Long programScheduleId, LocalDate scheduleDate, 
                                             String dayOfWeek, String status, Pageable pageable) {
        Specification<DailySchedule> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (programScheduleId != null) {
                predicates.add(criteriaBuilder.equal(root.get("programScheduleId"), programScheduleId));
            }

            if (scheduleDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("targetDate"), scheduleDate));
            }

            if (status != null && !status.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("dailyStatus"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return dailyScheduleRepository.findAll(spec, pageable);
    }

    /**
     * 日次スケジュールを作成
     * 
     * @param dailySchedule 日次スケジュール
     * @return 保存された日次スケジュール
     * @throws IllegalArgumentException バリデーションエラー
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public DailySchedule save(DailySchedule dailySchedule) {
        validateDailySchedule(dailySchedule);

        // プログラムスケジュール存在チェック
        if (dailySchedule.getProgramScheduleId() != null) {
            Optional<ProgramSchedule> programSchedule = programScheduleRepository.findById(dailySchedule.getProgramScheduleId());
            if (!programSchedule.isPresent()) {
                throw new IllegalArgumentException("指定されたプログラムスケジュールが存在しません: " + dailySchedule.getProgramScheduleId());
            }
        }

        return dailyScheduleRepository.save(dailySchedule);
    }

    /**
     * 日次スケジュールを更新
     * 
     * @param id 日次スケジュールID
     * @param dailySchedule 更新する日次スケジュール
     * @return 更新された日次スケジュール
     * @throws IllegalArgumentException IDが存在しない場合
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public DailySchedule update(Long id, DailySchedule dailySchedule) {
        Optional<DailySchedule> existingSchedule = dailyScheduleRepository.findById(id);
        if (!existingSchedule.isPresent()) {
            throw new IllegalArgumentException("指定された日次スケジュールが存在しません: " + id);
        }

        dailySchedule.setId(id);
        validateDailySchedule(dailySchedule);

        return dailyScheduleRepository.save(dailySchedule);
    }

    /**
     * 日次スケジュールを削除
     * 
     * @param id 日次スケジュールID
     * @throws IllegalArgumentException IDが存在しない場合
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void delete(Long id) {
        if (!dailyScheduleRepository.existsById(id)) {
            throw new IllegalArgumentException("指定された日次スケジュールが存在しません: " + id);
        }
        dailyScheduleRepository.deleteById(id);
    }

    /** IDで削除（エイリアス） 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void deleteById(Long id) {
        delete(id);
    }

    /** 日付範囲で検索（ページング） 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Page<DailySchedule> findByScheduleDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        List<DailySchedule> list = dailyScheduleRepository.findByTargetDateBetweenOrderByTargetDateAscStartTimeAsc(startDate, endDate);
        return new PageImpl<>(list, pageable, list.size());
    }

    /** 日付範囲で検索 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<DailySchedule> findByScheduleDateBetween(LocalDate startDate, LocalDate endDate) {
        return dailyScheduleRepository.findByTargetDateBetweenOrderByTargetDateAscStartTimeAsc(startDate, endDate);
    }

    /** 学生IDと期間で検索（スタブ） 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Page<DailySchedule> findByStudentIdAndScheduleDateBetween(Long studentId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return Page.empty(pageable);
    }

    /** 学生IDで検索（スタブ） 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Page<DailySchedule> findByStudentId(Long studentId, Pageable pageable) {
        return Page.empty(pageable);
    }

    /** キーワード検索（スタブ） 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Page<DailySchedule> searchSchedules(String keyword, Pageable pageable) {
        return Page.empty(pageable);
    }

    /** 統計情報取得（スタブ） 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Map<String, Object> getStatistics(LocalDate startDate, LocalDate endDate) {
        return Collections.emptyMap();
    }

    /**
     * スケジュールのステータスを更新
     * 
     * @param id 日次スケジュールID
     * @param status 新しいステータス
     * @throws IllegalArgumentException IDが存在しない場合
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void updateStatus(Long id, String status) {
        Optional<DailySchedule> dailySchedule = dailyScheduleRepository.findById(id);
        if (!dailySchedule.isPresent()) {
            throw new IllegalArgumentException("指定された日次スケジュールが存在しません: " + id);
        }

        DailySchedule schedule = dailySchedule.get();
        schedule.setStatus(status);
        dailyScheduleRepository.save(schedule);
    }

    /**
     * プログラムスケジュールの日次スケジュール数を取得
     * 
     * @param programScheduleId プログラムスケジュールID
     * @return 日次スケジュール数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public long countByProgramScheduleId(Long programScheduleId) {
        return dailyScheduleRepository.countByProgramScheduleId(programScheduleId);
    }

    /**
     * 完了済み日次スケジュール数を取得
     * 
     * @param programScheduleId プログラムスケジュールID
     * @return 完了済み日次スケジュール数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public long countCompletedSchedules(Long programScheduleId) {
        return dailyScheduleRepository.countByProgramScheduleIdAndDailyStatus(programScheduleId, "COMPLETED");
    }

    /**
     * 曜日別のスケジュール取得
     * 
     * @param dayOfWeek 曜日（MONDAY, TUESDAY, etc.）
     * @return 曜日別の日次スケジュールのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<DailySchedule> findByDayOfWeek(String dayOfWeek) {
        return dailyScheduleRepository.findByDayOfWeekOrderByStartTimeAsc(dayOfWeek);
    }

    /**
     * 日次スケジュールのバリデーション
     * 
     * @param dailySchedule 検証対象の日次スケジュール
     * @throws IllegalArgumentException バリデーションエラー
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    private void validateDailySchedule(DailySchedule dailySchedule) {
        if (dailySchedule == null) {
            throw new IllegalArgumentException("日次スケジュールが null です");
        }

        if (dailySchedule.getProgramScheduleId() == null) {
            throw new IllegalArgumentException("プログラムスケジュールIDは必須です");
        }

        if (dailySchedule.getScheduleDate() == null) {
            throw new IllegalArgumentException("スケジュール日は必須です");
        }

        if (dailySchedule.getStartTime() == null) {
            throw new IllegalArgumentException("開始時刻は必須です");
        }

        if (dailySchedule.getEndTime() == null) {
            throw new IllegalArgumentException("終了時刻は必須です");
        }

        if (dailySchedule.getStartTime().isAfter(dailySchedule.getEndTime())) {
            throw new IllegalArgumentException("開始時刻は終了時刻より前である必要があります");
        }

        if (dailySchedule.getTitle() == null || dailySchedule.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("スケジュールタイトルは必須です");
        }
    }
}
