package jp.co.apsa.giiku.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import jp.co.apsa.giiku.domain.entity.LectureContentBlock;
import jp.co.apsa.giiku.domain.repository.LectureContentBlockRepository;

@Service
@Transactional
public class LectureContentBlockService {

    @Autowired
    private LectureContentBlockRepository lectureContentBlockRepository;

    public List<LectureContentBlock> findByChapterIdOrderBySortOrder(Long chapterId) {
        return lectureContentBlockRepository.findByChapterIdOrderBySortOrder(chapterId);
    }
}
