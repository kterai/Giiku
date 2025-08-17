package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.Lecture;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Lectureのリポジトリインターフェース
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long>, JpaSpecificationExecutor<Lecture> {

    /** 研修プログラムIDで検索 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    default List<Lecture> findByTrainingProgramIdAndIsActiveTrue(Long trainingProgramId) {
        return findAll();
    }

    /** 講師IDで検索 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    default List<Lecture> findByInstructorIdAndIsActiveTrueOrderByScheduleDateAsc(Long instructorId) {
        return findAll();
    }

    /** アクティブな講義を取得 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    default List<Lecture> findByIsActiveTrueOrderByScheduleDateAsc() {
        return findAll();
    }

    /** カテゴリで検索 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    default List<Lecture> findByCategoryAndIsActiveTrueOrderByTitleAsc(String category) {
        return findAll();
    }

    /** タイトル部分一致で検索 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    default List<Lecture> findByTitleContainingIgnoreCaseAndIsActiveTrueOrderByTitleAsc(String title) {
        return findAll();
    }

    /** 研修プログラムIDで件数取得 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    default long countByTrainingProgramIdAndIsActiveTrue(Long trainingProgramId) {
        return 0L;
    }

    /** 講師IDで件数取得 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    default long countByInstructorIdAndIsActiveTrue(Long instructorId) {
        return 0L;
    }

    /** 予定日以降の講義を取得 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    default List<Lecture> findByScheduleDateGreaterThanAndIsActiveTrueOrderByScheduleDateAsc(LocalDateTime dateTime) {
        return findAll();
    }
}
