package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.Lecture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Lectureのリポジトリインターフェース
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long>, JpaSpecificationExecutor<Lecture> {

    /** 日IDで講義一覧を取得 */
    List<Lecture> findByDayId(Long dayId);

    /**
     * 指定された日IDの講義を講義番号順で取得
     */
    @Query("SELECT l FROM Lecture l WHERE l.dayId = :dayId ORDER BY l.lectureNumber ASC")
    List<Lecture> findByDayIdOrderByLectureNumber(@Param("dayId") Long dayId);

    default List<Lecture> findByTrainingProgramIdAndIsActiveTrue(Long trainingProgramId) {
        return findAll();
    }

    default List<Lecture> findByInstructorIdAndIsActiveTrueOrderByScheduleDateAsc(Long instructorId) {
        return findAll();
    }

    default List<Lecture> findByIsActiveTrueOrderByScheduleDateAsc() {
        return findAll();
    }

    default List<Lecture> findByCategoryAndIsActiveTrueOrderByTitleAsc(String category) {
        return findAll();
    }

    default List<Lecture> findByTitleContainingIgnoreCaseAndIsActiveTrueOrderByTitleAsc(String title) {
        return findAll();
    }

    default long countByTrainingProgramIdAndIsActiveTrue(Long trainingProgramId) {
        return 0L;
    }

    default long countByInstructorIdAndIsActiveTrue(Long instructorId) {
        return 0L;
    }

    default List<Lecture> findByScheduleDateGreaterThanAndIsActiveTrueOrderByScheduleDateAsc(LocalDateTime dateTime) {
        return findAll();
    }
}
