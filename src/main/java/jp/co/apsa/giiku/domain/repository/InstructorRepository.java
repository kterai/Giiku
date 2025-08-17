package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.Instructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Instructorのリポジトリインターフェース
 * 講師情報の永続化とカスタムクエリメソッドを提供
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    /**
     * ユーザーIDで講師を検索
     *
     * @param userId ユーザーID
     * @return 該当する講師（存在する場合）
     */
    Optional<Instructor> findByUserId(Long userId);

    /**
     * 講師番号で講師を検索
     *
     * @param instructorNumber 講師番号
     * @return 該当する講師（存在する場合）
     */
    Optional<Instructor> findByInstructorNumber(String instructorNumber);

    /**
     * 部署IDで講師一覧を検索
     *
     * @param departmentId 部署ID
     * @return 該当部署の講師一覧
     */
    List<Instructor> findByDepartmentId(Long departmentId);

    /**
     * 講師ステータスで講師一覧を検索
     *
     * @param instructorStatus 講師ステータス
     * @return 該当ステータスの講師一覧
     */
    List<Instructor> findByInstructorStatus(String instructorStatus);


    /**
     * 専門分野で講師一覧を検索
     *
     * @param specialization 専門分野
     * @return 該当専門分野の講師一覧
     */
    List<Instructor> findBySpecializationContaining(String specialization);

    /**
     * 講師レベルで講師一覧を検索
     *
     * @param instructorLevel 講師レベル
     * @return 該当レベルの講師一覧
     */
    List<Instructor> findByInstructorLevel(Integer instructorLevel);

    /**
     * 講師レベルの範囲で講師一覧を検索
     *
     * @param minLevel 最小レベル
     * @param maxLevel 最大レベル
     * @return 該当レベル範囲の講師一覧
     */
    List<Instructor> findByInstructorLevelBetween(Integer minLevel, Integer maxLevel);

    /**
     * 評価スコアの範囲で講師一覧を検索
     *
     * @param minRating 最小評価スコア
     * @param maxRating 最大評価スコア
     * @return 該当評価範囲の講師一覧
     */
    List<Instructor> findByRatingScoreBetween(Double minRating, Double maxRating);

    /**
     * 最小評価スコア以上の講師一覧を取得
     *
     * @param minRating 最小評価スコア
     * @return 該当評価以上の講師一覧
     */
    List<Instructor> findByRatingScoreGreaterThanEqual(Double minRating);

    /**
     * 担当コース数で講師一覧を検索
     *
     * @param minCourses 最小担当コース数
     * @return 該当コース数以上の講師一覧
     */
    List<Instructor> findByAssignedCoursesCountGreaterThanEqual(Integer minCourses);

    /**
     * 担当学生数で講師一覧を検索
     *
     * @param minStudents 最小担当学生数
     * @return 該当学生数以上の講師一覧
     */
    List<Instructor> findByAssignedStudentsCountGreaterThanEqual(Integer minStudents);

    /**
     * 最終教育日が指定日以降の講師一覧を取得
     *
     * @param date 指定日
     * @return 最終教育日が指定日以降の講師一覧
     */
    List<Instructor> findByLastTeachingDateAfter(LocalDateTime date);

    /**
     * 最終教育日が指定日以前の講師一覧を取得
     *
     * @param date 指定日
     * @return 最終教育日が指定日以前の講師一覧
     */
    List<Instructor> findByLastTeachingDateBefore(LocalDateTime date);

    /**
     * 利用可能状況で講師一覧を検索
     *
     * @param availability 利用可能状況
     * @return 該当する利用可能状況の講師一覧
     */
    List<Instructor> findByAvailability(String availability);

    /**
     * 認定日の範囲で講師一覧を検索
     *
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 該当期間に認定された講師一覧
     */
    List<Instructor> findByCertificationDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * 部署とステータスで講師一覧を検索
     *
     * @param departmentId 部署ID
     * @param status ステータス
     * @return 該当する講師一覧
     */
    List<Instructor> findByDepartmentIdAndInstructorStatus(Long departmentId, String status);

    /**
     * 高評価講師一覧を取得（カスタムクエリ）
     *
     * @param minRating 最小評価スコア
     * @param minRatingCount 最小評価回数
     * @return 高評価講師一覧
     */
    @Query("SELECT i FROM Instructor i WHERE i.ratingScore >= :minRating AND i.ratingCount >= :minRatingCount AND i.instructorStatus = 'ACTIVE' ORDER BY i.ratingScore DESC, i.ratingCount DESC")
    List<Instructor> findHighRatedInstructors(@Param("minRating") Double minRating, @Param("minRatingCount") Integer minRatingCount);

    /**
     * 経験豊富な講師一覧を取得（カスタムクエリ）
     *
     * @param minLevel 最小講師レベル
     * @param minTeachingMinutes 最小教育時間
     * @return 経験豊富な講師一覧
     */
    @Query("SELECT i FROM Instructor i WHERE i.instructorLevel >= :minLevel AND i.totalTeachingMinutes >= :minTeachingMinutes AND i.instructorStatus = 'ACTIVE' ORDER BY i.instructorLevel DESC, i.totalTeachingMinutes DESC")
    List<Instructor> findExperiencedInstructors(@Param("minLevel") Integer minLevel, @Param("minTeachingMinutes") Integer minTeachingMinutes);

    /**
     * 専門分野とレベルで講師検索（カスタムクエリ）
     *
     * @param specialization 専門分野
     * @param minLevel 最小レベル
     * @return 該当する専門講師一覧
     */
    @Query("SELECT i FROM Instructor i WHERE i.specialization LIKE %:specialization% AND i.instructorLevel >= :minLevel AND i.instructorStatus = 'ACTIVE' ORDER BY i.instructorLevel DESC, i.ratingScore DESC")
    List<Instructor> findSpecializedInstructors(@Param("specialization") String specialization, @Param("minLevel") Integer minLevel);

    // JpaRepositoryから継承される基本メソッド:
    // - findAll()
    // - findById(Long id)
    // - save(Instructor entity)
    // - deleteById(Long id)
    // - existsById(Long id)
    // - count()
    // など
}
