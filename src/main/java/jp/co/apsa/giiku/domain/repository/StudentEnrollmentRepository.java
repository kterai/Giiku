package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.StudentEnrollment;

import java.util.List;
import java.util.Optional;

/**
 * StudentEnrollmentのリポジトリインターフェース
 */
@Repository
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {

    // カスタムクエリメソッドをここに追加

}
