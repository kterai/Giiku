package jp.co.apsa.giiku.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import jp.co.apsa.giiku.domain.entity.LectureGoal;
import jp.co.apsa.giiku.domain.repository.LectureGoalRepository;

@Service
@Transactional
public class LectureGoalService {

    @Autowired
    private LectureGoalRepository lectureGoalRepository;

    public List<LectureGoal> findByLectureIdOrderBySortOrder(Long lectureId) {
        return lectureGoalRepository.findByLectureIdOrderBySortOrder(lectureId);
    }
}
