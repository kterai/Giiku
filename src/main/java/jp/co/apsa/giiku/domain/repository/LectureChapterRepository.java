package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import jp.co.apsa.giiku.domain.entity.LectureChapter;

/**
 * LectureChapter のリポジトリインターフェース。
 * チャプター取得用の基本的なクエリメソッドを提供する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface LectureChapterRepository extends JpaRepository<LectureChapter, Long> {

    /**
     * 講義 ID でチャプターを取得し、並び順でソートする。
     *
     * @param lectureId 講義 ID
     * @return 該当チャプター一覧
     */
    List<LectureChapter> findByLectureIdOrderBySortOrder(Long lectureId);

    /**
     * 講義 ID でチャプターを取得し、並び順で降順ソートする。
     *
     * @param lectureId 講義 ID
     * @return 該当チャプター一覧
     */
    List<LectureChapter> findByLectureIdOrderBySortOrderDesc(Long lectureId);

    /**
     * 講義内の最大チャプター番号を取得する
     *
     * @param lectureId 講義ID
     * @return 最大チャプター番号
     */
    @Query("SELECT MAX(lc.chapterNumber) FROM LectureChapter lc WHERE lc.lectureId = :lectureId")
    Optional<Integer> findMaxChapterNumberByLectureId(@Param("lectureId") Long lectureId);

    /**
     * 講義内の最大ソート順を取得する
     *
     * @param lectureId 講義ID
     * @return 最大ソート順
     */
    @Query("SELECT MAX(lc.sortOrder) FROM LectureChapter lc WHERE lc.lectureId = :lectureId")
    Optional<Integer> findMaxSortOrderByLectureId(@Param("lectureId") Long lectureId);

    /**
     * 講義IDでアクティブなチャプターのみを取得する
     *
     * @param lectureId 講義ID
     * @return アクティブなチャプター一覧
     */
    List<LectureChapter> findByLectureIdAndIsActiveTrueOrderBySortOrder(Long lectureId);
}
