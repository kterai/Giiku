package jp.co.apsa.giiku.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import jp.co.apsa.giiku.domain.entity.LectureContentBlock;
import jp.co.apsa.giiku.domain.repository.LectureContentBlockRepository;

/**
 * 講義コンテンツブロックに関するサービスクラス.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class LectureContentBlockService {

    @Autowired
    private LectureContentBlockRepository lectureContentBlockRepository;

    public List<LectureContentBlock> findByChapterIdOrderBySortOrder(Long chapterId) {
        return lectureContentBlockRepository.findByChapterIdOrderBySortOrder(chapterId);
    }
}
