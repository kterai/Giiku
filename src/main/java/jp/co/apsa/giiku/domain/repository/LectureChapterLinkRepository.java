package jp.co.apsa.giiku.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.apsa.giiku.domain.entity.LectureChapterLink;

/**
 * 講義とチャプターの関連リポジトリ。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface LectureChapterLinkRepository extends JpaRepository<LectureChapterLink, Long> {

    /**
     * 講義IDでチャプターリンクを取得（昇順）
     *
     * @param lectureId 講義ID
     * @return チャプターリンク一覧
     */
    @Query("SELECT l FROM LectureChapterLink l JOIN FETCH l.chapter c WHERE l.lectureId = :lectureId ORDER BY l.sortOrder")
    List<LectureChapterLink> findByLectureIdOrderBySortOrder(@Param("lectureId") Long lectureId);

    /**
     * 講義IDでチャプターリンクを取得（降順）
     *
     * @param lectureId 講義ID
     * @return チャプターリンク一覧
     */
    @Query("SELECT l FROM LectureChapterLink l JOIN FETCH l.chapter c WHERE l.lectureId = :lectureId ORDER BY l.sortOrder DESC")
    List<LectureChapterLink> findByLectureIdOrderBySortOrderDesc(@Param("lectureId") Long lectureId);

    /**
     * 講義内の最大チャプター番号を取得する。
     *
     * @param lectureId 講義ID
     * @return 最大チャプター番号
     */
    @Query("SELECT MAX(c.chapterNumber) FROM LectureChapterLink l JOIN l.chapter c WHERE l.lectureId = :lectureId")
    Optional<Integer> findMaxChapterNumberByLectureId(@Param("lectureId") Long lectureId);

    /**
     * 講義内の最大ソート順を取得する。
     *
     * @param lectureId 講義ID
     * @return 最大ソート順
     */
    @Query("SELECT MAX(l.sortOrder) FROM LectureChapterLink l WHERE l.lectureId = :lectureId")
    Optional<Integer> findMaxSortOrderByLectureId(@Param("lectureId") Long lectureId);

    /**
     * チャプターIDでリンクを取得する。
     *
     * @param chapterId チャプターID
     * @return リンク情報
     */
    Optional<LectureChapterLink> findByChapterId(Long chapterId);

    /**
     * 講義IDでアクティブなチャプターリンクを取得する。
     *
     * @param lectureId 講義ID
     * @return アクティブなチャプターリンク一覧
     */
    @Query("SELECT l FROM LectureChapterLink l JOIN FETCH l.chapter c WHERE l.lectureId = :lectureId AND c.isActive = true ORDER BY l.sortOrder")
    List<LectureChapterLink> findByLectureIdAndChapterIsActiveTrueOrderBySortOrder(@Param("lectureId") Long lectureId);

    /**
     * チャプターIDでリンクを削除する。
     *
     * @param chapterId チャプターID
     */
    void deleteByChapterId(Long chapterId);
}
