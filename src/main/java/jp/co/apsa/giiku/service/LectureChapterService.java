package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.Chapter;
import jp.co.apsa.giiku.domain.entity.LectureChapterLink;
import jp.co.apsa.giiku.domain.repository.ChapterRepository;
import jp.co.apsa.giiku.domain.repository.LectureChapterLinkRepository;
import jp.co.apsa.giiku.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.dozermapper.core.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 講義チャプターサービス
 * チャプターおよびリンクのビジネスロジックを提供する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class LectureChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private LectureChapterLinkRepository lectureChapterLinkRepository;

    @Autowired
    private Mapper mapper;

    /**
     * チャプター一覧を取得する（ページング・ソート対応）
     *
     * @param page ページ番号
     * @param size ページサイズ
     * @param sortBy ソートキー
     * @param sortDir ソート方向
     * @return ページングされたチャプター一覧
     */
    public Page<LectureChapterResponseDto> getAllChapters(int page, int size, String sortBy, String sortDir) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Chapter> chapters = chapterRepository.findAll(pageable);
        return chapters.map(ch -> convertToResponseDto(ch, lectureChapterLinkRepository.findByChapterId(ch.getId()).orElse(null)));
    }

    /**
     * IDでチャプターを取得する
     *
     * @param id チャプターID
     * @return チャプター情報
     */
    public Optional<LectureChapterResponseDto> getChapterById(Long id) {
        return chapterRepository.findById(id)
                .map(chapter -> convertToResponseDto(chapter, lectureChapterLinkRepository.findByChapterId(id).orElse(null)));
    }

    /**
     * 講義IDでチャプターを取得する
     *
     * @param lectureId 講義ID
     * @param sortDir ソート方向
     * @return チャプター一覧
     */
    public List<LectureChapterResponseDto> getChaptersByLectureId(Long lectureId, String sortDir) {
        List<LectureChapterLink> links;
        if ("DESC".equalsIgnoreCase(sortDir)) {
            links = lectureChapterLinkRepository.findByLectureIdOrderBySortOrderDesc(lectureId);
        } else {
            links = lectureChapterLinkRepository.findByLectureIdOrderBySortOrder(lectureId);
        }
        return links.stream()
                .map(link -> convertToResponseDto(link.getChapter(), link))
                .collect(Collectors.toList());
    }

    /**
     * 新しいチャプターを作成する
     *
     * @param dto 作成用DTO
     * @return 作成されたチャプター
     */
    public LectureChapterResponseDto createChapter(LectureChapterCreateDto dto) {
        Chapter chapter = mapper.map(dto, Chapter.class);
        chapter.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        Chapter savedChapter = chapterRepository.save(chapter);

        LectureChapterLink link = new LectureChapterLink();
        link.setLectureId(dto.getLectureId());
        link.setChapter(savedChapter);
        link.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : getNextSortOrder(dto.getLectureId()));
        LectureChapterLink savedLink = lectureChapterLinkRepository.save(link);
        return convertToResponseDto(savedChapter, savedLink);
    }

    /**
     * チャプターを更新する
     *
     * @param id チャプターID
     * @param dto 更新用DTO
     * @return 更新されたチャプター
     */
    public Optional<LectureChapterResponseDto> updateChapter(Long id, LectureChapterUpdateDto dto) {
        return chapterRepository.findById(id)
                .map(chapter -> {
                    mapper.map(dto, chapter);
                    Chapter savedChapter = chapterRepository.save(chapter);
                    if (dto.getSortOrder() != null) {
                        lectureChapterLinkRepository.findByChapterId(id)
                                .ifPresent(link -> {
                                    link.setSortOrder(dto.getSortOrder());
                                    lectureChapterLinkRepository.save(link);
                                });
                    }
                    LectureChapterLink link = lectureChapterLinkRepository.findByChapterId(id).orElse(null);
                    return convertToResponseDto(savedChapter, link);
                });
    }

    /**
     * チャプターを削除する
     *
     * @param id チャプターID
     */
    public void deleteChapter(Long id) {
        lectureChapterLinkRepository.deleteByChapterId(id);
        chapterRepository.deleteById(id);
    }

    /**
     * チャプターの並び順を更新する
     *
     * @param lectureId 講義ID
     * @param chapterIds 並び順のチャプターIDリスト
     * @return 更新されたチャプター一覧
     */
    public List<LectureChapterResponseDto> reorderChapters(Long lectureId, List<Long> chapterIds) {
        for (int i = 0; i < chapterIds.size(); i++) {
            final int sortOrder = i + 1;
            Long chapterId = chapterIds.get(i);
            lectureChapterLinkRepository.findByChapterId(chapterId)
                    .ifPresent(link -> {
                        link.setSortOrder(sortOrder);
                        lectureChapterLinkRepository.save(link);
                    });
        }
        List<LectureChapterLink> updatedLinks = lectureChapterLinkRepository.findByLectureIdOrderBySortOrder(lectureId);
        return updatedLinks.stream()
                .map(link -> convertToResponseDto(link.getChapter(), link))
                .collect(Collectors.toList());
    }

    /**
     * チャプターを複製する
     *
     * @param id 複製元チャプターID
     * @param targetLectureId 複製先講義ID（nullの場合は同じ講義内で複製）
     * @return 複製されたチャプター
     */
    public LectureChapterResponseDto duplicateChapter(Long id, Long targetLectureId) {
        Chapter originalChapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("チャプターが見つかりません: " + id));
        Long lectureId = targetLectureId != null ? targetLectureId : lectureChapterLinkRepository.findByChapterId(id)
                .map(LectureChapterLink::getLectureId)
                .orElseThrow(() -> new RuntimeException("リンクが見つかりません: " + id));

        Chapter duplicatedChapter = mapper.map(originalChapter, Chapter.class);
        duplicatedChapter.setTitle(originalChapter.getTitle() + " (コピー)");
        duplicatedChapter.setChapterNumber(getNextChapterNumber(lectureId));
        Chapter savedChapter = chapterRepository.save(duplicatedChapter);

        LectureChapterLink link = new LectureChapterLink();
        link.setLectureId(lectureId);
        link.setChapter(savedChapter);
        link.setSortOrder(getNextSortOrder(lectureId));
        LectureChapterLink savedLink = lectureChapterLinkRepository.save(link);
        return convertToResponseDto(savedChapter, savedLink);
    }

    /**
     * 講義IDで既存のチャプター一覧を取得する（ソート順でソート）
     *
     * @param lectureId 講義ID
     * @return チャプター一覧
     */
    public List<Chapter> findByLectureIdOrderBySortOrder(Long lectureId) {
        return lectureChapterLinkRepository.findByLectureIdOrderBySortOrder(lectureId)
                .stream()
                .map(LectureChapterLink::getChapter)
                .collect(Collectors.toList());
    }

    /**
     * エンティティをレスポンスDTOに変換する
     *
     * @param chapter チャプターエンティティ
     * @param link リンクエンティティ
     * @return レスポンスDTO
     */
    private LectureChapterResponseDto convertToResponseDto(Chapter chapter, LectureChapterLink link) {
        LectureChapterResponseDto dto = mapper.map(chapter, LectureChapterResponseDto.class);
        if (link != null) {
            dto.setLectureId(link.getLectureId());
            dto.setSortOrder(link.getSortOrder());
        }
        return dto;
    }

    /**
     * 講義内の次のチャプター番号を取得する
     *
     * @param lectureId 講義ID
     * @return 次のチャプター番号
     */
    private Integer getNextChapterNumber(Long lectureId) {
        return lectureChapterLinkRepository.findMaxChapterNumberByLectureId(lectureId)
                .orElse(0) + 1;
    }

    /**
     * 講義内の次のソート順を取得する
     *
     * @param lectureId 講義ID
     * @return 次のソート順
     */
    private Integer getNextSortOrder(Long lectureId) {
        return lectureChapterLinkRepository.findMaxSortOrderByLectureId(lectureId)
                .orElse(0) + 1;
    }
}
