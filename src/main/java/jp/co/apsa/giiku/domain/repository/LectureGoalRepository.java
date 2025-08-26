package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import jp.co.apsa.giiku.domain.entity.LectureGoal;

@Repository
public interface LectureGoalRepository extends JpaRepository<LectureGoal, Long> {
    
    List<LectureGoal> findByLectureIdOrderBySortOrder(Long lectureId);
}
