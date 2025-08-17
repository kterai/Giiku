package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.LectureChapter;

import java.util.List;

/**
 * LectureChapterのリポジトリインターフェース。
 * チャプター検索や統計取得用のクエリメソッドを提供する。
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface LectureChapterRepository extends JpaRepository<LectureChapter, Long>,
        JpaSpecificationExecutor<LectureChapter> {

    /**
     * 講義IDでチャプターを取得し、並び順でソートする。
     *
     * @param lectureId 講義ID
     * @return 該当チャプター一覧
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    List<LectureChapter> findByLectureIdOrderBySortOrderAsc(Long lectureId);

    /**
     * 講義IDとステータスでチャプターを取得し、並び順でソートする。
     *
     * @param lectureId 講義ID
     * @param status    チャプターステータス
     * @return 該当チャプター一覧
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    List<LectureChapter> findByLectureIdAndStatusOrderBySortOrderAsc(Long lectureId, String status);

    /**
     * タイトルで部分一致検索を行い、ステータスとタイトルでソートする。
     *
     * @param title  タイトルキーワード
     * @param status ステータス
     * @return 該当チャプター一覧
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    List<LectureChapter> findByTitleContainingIgnoreCaseAndStatusOrderByTitleAsc(String title, String status);

    /**
     * 講義IDとステータスでチャプター件数を取得する。
     *
     * @param lectureId 講義ID
     * @param status    ステータス
     * @return 件数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    long countByLectureIdAndStatus(Long lectureId, String status);
}
