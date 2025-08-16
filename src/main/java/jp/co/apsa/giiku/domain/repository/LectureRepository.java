package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.Lecture;

import java.util.List;
import java.util.Optional;

/**
 * Lectureのリポジトリインターフェース
 */
@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    // カスタムクエリメソッドをここに追加

}
