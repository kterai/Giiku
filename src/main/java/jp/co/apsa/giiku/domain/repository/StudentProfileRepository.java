package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.StudentProfile;

import java.util.List;
import java.util.Optional;

/**
 * StudentProfileのリポジトリインターフェース
 */
@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {

    // カスタムクエリメソッドをここに追加

}
