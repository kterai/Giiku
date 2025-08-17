package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.Grade;

import java.util.List;
import java.util.Optional;

/**
 * Gradeのリポジトリインターフェース
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    // カスタムクエリメソッドをここに追加

}
