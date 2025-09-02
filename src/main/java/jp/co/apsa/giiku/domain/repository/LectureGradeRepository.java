package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.LectureGrade;
import java.util.Optional;

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

    /**
     * 講義IDで成績を検索
     *
     * @param lectureId 講義ID
     * @return 成績情報
     */
    Optional<LectureGrade> findByLectureId(Long lectureId);
}

