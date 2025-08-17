package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.StudentProfile;

import java.util.List;
import java.util.Optional;

/**
 * StudentProfile のリポジトリインターフェース。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {

    /**
     * 学生IDでプロフィールを検索します。
     *
     * @param studentId 学生ID
     * @return 該当する学生プロフィール（存在しない場合は空）
     */
    Optional<StudentProfile> findByStudentId(Long studentId);

    /**
     * 学生番号でプロフィールを検索します。
     *
     * @param studentNumber 学生番号
     * @return 該当する学生プロフィール（存在しない場合は空）
     */
    Optional<StudentProfile> findByStudentNumber(String studentNumber);

    /**
     * 企業IDでプロフィールを検索します。
     *
     * @param companyId 企業ID
     * @return 学生プロフィールのリスト
     */
    List<StudentProfile> findByCompanyId(Long companyId);

    /**
     * 企業IDでプロフィールをページング検索します。
     *
     * @param companyId 企業ID
     * @param pageable  ページ情報
     * @return 学生プロフィールのページ
     */
    Page<StudentProfile> findByCompanyId(Long companyId, Pageable pageable);

    /**
     * 在籍状況でプロフィールを検索します。
     *
     * @param enrollmentStatus 在籍状況
     * @return 学生プロフィールのリスト
     */
    List<StudentProfile> findByEnrollmentStatus(String enrollmentStatus);

    /**
     * 学生番号の存在を確認します。
     *
     * @param studentNumber 学生番号
     * @return 存在する場合は true
     */
    boolean existsByStudentNumber(String studentNumber);

    /**
     * 企業IDと在籍状況で学生数をカウントします。
     *
     * @param companyId        企業ID
     * @param enrollmentStatus 在籍状況
     * @return 該当する学生数
     */
    long countByCompanyIdAndEnrollmentStatus(Long companyId, String enrollmentStatus);

    /**
     * 企業IDごとの学年別学生数を集計します。
     *
     * @param companyId 企業ID
     * @return 学年と人数の配列リスト
     */
    @Query("SELECT sp.gradeLevel, COUNT(sp) FROM StudentProfile sp WHERE sp.companyId = :companyId GROUP BY sp.gradeLevel")
    List<Object[]> countByGradeLevelAndCompanyId(Long companyId);
}
