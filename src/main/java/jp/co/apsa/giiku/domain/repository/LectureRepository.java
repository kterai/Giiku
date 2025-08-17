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
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long>, JpaSpecificationExecutor<Lecture> {

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
