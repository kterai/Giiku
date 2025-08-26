package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import jp.co.apsa.giiku.domain.entity.LectureContentBlock;


@Repository
public interface LectureContentBlockRepository extends JpaRepository<LectureContentBlock, Long> {
    
    List<LectureContentBlock> findByChapterIdOrderBySortOrder(Long chapterId);
}
