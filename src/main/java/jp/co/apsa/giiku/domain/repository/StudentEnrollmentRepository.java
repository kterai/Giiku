package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.apsa.giiku.domain.entity.StudentEnrollment;

import java.util.List;

/**
 * StudentEnrollmentのリポジトリインターフェース
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface StudentEnrollmentRepository
        extends JpaRepository<StudentEnrollment, Long>,
                JpaSpecificationExecutor<StudentEnrollment> {

    List<StudentEnrollment> findByStudentIdOrderByEnrollmentDateDesc(Long studentId);

    List<StudentEnrollment> findByProgramIdOrderByEnrollmentDateDesc(Long programId);

    List<StudentEnrollment> findByEnrollmentStatusOrderByEnrollmentDateDesc(String status);

    List<StudentEnrollment> findByEnrollmentStatusInOrderByEnrollmentDateDesc(List<String> statuses);

    List<StudentEnrollment> findByEnrollmentStatusOrderByCompletionDateDesc(String status);

    List<StudentEnrollment> findByStudentIdAndEnrollmentStatusInOrderByEnrollmentDateDesc(
            Long studentId, List<String> statuses);

    @Query("SELECT se.enrollmentStatus, COUNT(se) FROM StudentEnrollment se WHERE se.programId = :programId GROUP BY se.enrollmentStatus")
    List<Object[]> findEnrollmentStatsByProgramId(@Param("programId") Long programId);

    boolean existsByStudentIdAndProgramId(Long studentId, Long programId);

    long countByEnrollmentStatus(String status);
}
