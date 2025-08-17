package jp.co.apsa.giiku.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.apsa.giiku.domain.entity.TrainingProgram;

/**
 * TrainingProgramのリポジトリインターフェース。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long>,
        JpaSpecificationExecutor<TrainingProgram> {

    /**
     * 企業IDとステータスで研修プログラムを検索。
     *
     * @param companyId 企業ID
     * @param status    プログラムステータス
     * @return 該当する研修プログラム一覧
     */
    List<TrainingProgram> findByCompanyIdAndProgramStatus(Long companyId, String status);

    /**
     * 指定したステータスの研修プログラムを開始日順に取得。
     *
     * @param status プログラムステータス
     * @return 研修プログラム一覧
     */
    List<TrainingProgram> findByProgramStatusOrderByStartDateAsc(String status);

    /**
     * カテゴリとステータスで研修プログラムを検索。
     *
     * @param category カテゴリ
     * @param status   プログラムステータス
     * @return 研修プログラム一覧
     */
    List<TrainingProgram> findByCategoryAndProgramStatusOrderByProgramNameAsc(String category, String status);

    /**
     * レベルとステータスで研修プログラムを検索。
     *
     * @param level  レベル
     * @param status プログラムステータス
     * @return 研修プログラム一覧
     */
    List<TrainingProgram> findByLevelAndProgramStatusOrderByProgramNameAsc(String level, String status);

    /**
     * 企業IDとステータスで研修プログラム数をカウント。
     *
     * @param companyId 企業ID
     * @param status    プログラムステータス
     * @return プログラム数
     */
    long countByCompanyIdAndProgramStatus(Long companyId, String status);

    /**
     * 期間内の研修プログラムを取得。
     *
     * @param start 開始日
     * @param end   終了日
     * @return 研修プログラム一覧
     */
    @Query("SELECT t FROM TrainingProgram t WHERE (:start IS NULL OR t.startDate >= :start) " +
           "AND (:end IS NULL OR t.endDate <= :end)")
    List<TrainingProgram> findProgramsWithinPeriod(@Param("start") LocalDate start,
                                                   @Param("end") LocalDate end);
}
