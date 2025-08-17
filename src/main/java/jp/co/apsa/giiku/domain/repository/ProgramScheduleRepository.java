package jp.co.apsa.giiku.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import jp.co.apsa.giiku.domain.entity.ProgramSchedule;

/**
 * プログラムスケジュールのリポジトリインターフェース。
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface ProgramScheduleRepository extends JpaRepository<ProgramSchedule, Long>,
        JpaSpecificationExecutor<ProgramSchedule> {

    /**
     * 指定した研修プログラムのスケジュールを開始日の昇順で取得します。
     *
     * @param trainingProgramId 研修プログラムID
     * @return スケジュールのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    List<ProgramSchedule> findByTrainingProgramIdOrderByStartDateAsc(Long trainingProgramId);

    /**
     * 指定期間内に開始されるスケジュールを取得します。
     *
     * @param startDate 期間開始日
     * @param endDate   期間終了日
     * @return スケジュールのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    List<ProgramSchedule> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * 指定した研修プログラムに紐づくスケジュール数を取得します。
     *
     * @param trainingProgramId 研修プログラムID
     * @return スケジュール数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    long countByTrainingProgramId(Long trainingProgramId);

    /**
     * 指定した研修プログラムの特定ステータスのスケジュール数を取得します。
     *
     * @param trainingProgramId 研修プログラムID
     * @param scheduleStatus    スケジュールステータス
     * @return スケジュール数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    long countByTrainingProgramIdAndScheduleStatus(Long trainingProgramId, String scheduleStatus);
}
