package jp.co.apsa.giiku.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import jp.co.apsa.giiku.domain.entity.LectureChapter;
import jp.co.apsa.giiku.domain.repository.LectureChapterRepository;

@Service
@Transactional
public class LectureChapterService {

    @Autowired
    private LectureChapterRepository lectureChapterRepository;

    public List<LectureChapter> findByLectureIdOrderBySortOrder(Long lectureId) {
        return lectureChapterRepository.findByLectureIdOrderBySortOrder(lectureId);
    }
}
