package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.DailySchedule;

import java.time.LocalDate;
import java.util.List;

/**
 * 日次スケジュールのリポジトリインターフェース。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface DailyScheduleRepository extends JpaRepository<DailySchedule, Long> {

    /**
     * プログラムスケジュールIDで検索し、日付と開始時刻で昇順ソートします。
     *
     * @param programScheduleId プログラムスケジュールID
     * @return 該当する日次スケジュール一覧
     */
    List<DailySchedule> findByProgramScheduleIdOrderByTargetDateAscStartTimeAsc(Long programScheduleId);

    /**
     * 指定された日付のスケジュールを開始時刻順に取得します。
     *
     * @param targetDate 対象日
     * @return 日次スケジュール一覧
     */
    List<DailySchedule> findByTargetDateOrderByStartTimeAsc(LocalDate targetDate);

    /**
     * 日付範囲でスケジュールを検索し、日付と開始時刻でソートします。
     *
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 日次スケジュール一覧
     */
    List<DailySchedule> findByTargetDateBetweenOrderByTargetDateAscStartTimeAsc(LocalDate startDate, LocalDate endDate);

    /**
     * 指定されたプログラムスケジュールに紐づく日次スケジュール数を取得します。
     *
     * @param programScheduleId プログラムスケジュールID
     * @return スケジュール数
     */
    long countByProgramScheduleId(Long programScheduleId);

    /**
     * 指定されたプログラムスケジュールとステータスに一致する日次スケジュール数を取得します。
     *
     * @param programScheduleId プログラムスケジュールID
     * @param dailyStatus 日次ステータス
     * @return スケジュール数
     */
    long countByProgramScheduleIdAndDailyStatus(Long programScheduleId, String dailyStatus);

    /**
     * 曜日でスケジュールを検索し開始時刻順に取得します。
     *
     * @param dayOfWeek 曜日（MONDAY, TUESDAY 等）
     * @return 日次スケジュール一覧
     */
    @Query("SELECT d FROM DailySchedule d WHERE FUNCTION('DAY_OF_WEEK', d.targetDate) = FUNCTION('DAY_OF_WEEK', CAST(:dayOfWeek AS date)) ORDER BY d.startTime ASC")
    List<DailySchedule> findByDayOfWeekOrderByStartTimeAsc(@Param("dayOfWeek") String dayOfWeek);
}
