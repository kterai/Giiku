package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.Grade;

import java.util.List;
import java.util.Optional;

/**
 * Gradeのリポジトリインターフェース
 */
@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    // カスタムクエリメソッドをここに追加

}
