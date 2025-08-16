package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.LectureChapter;

import java.util.List;
import java.util.Optional;

/**
 * LectureChapterのリポジトリインターフェース
 */
@Repository
public interface LectureChapterRepository extends JpaRepository<LectureChapter, Long> {

    // カスタムクエリメソッドをここに追加

}
