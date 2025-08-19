package jp.co.apsa.giiku.domain.repository;

import jp.co.apsa.giiku.domain.entity.LectureChapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    List<LectureChapter> findByLectureIdOrderBySortOrderAsc(Long lectureId);
}
