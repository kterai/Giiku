package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.LectureGrade;

/**
 * LectureGradeのリポジトリインターフェース
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface LectureGradeRepository extends JpaRepository<LectureGrade, Long> {
    // カスタムクエリメソッドをここに追加
}

