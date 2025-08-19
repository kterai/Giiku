package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.LectureChapter;
import jp.co.apsa.giiku.domain.repository.LectureChapterRepository;
import jp.co.apsa.giiku.domain.repository.LectureRepository;
import jp.co.apsa.giiku.dto.LectureChapterCreateDto;
import jp.co.apsa.giiku.dto.LectureChapterUpdateDto;
import jp.co.apsa.giiku.dto.LectureChapterResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 講義チャプターに関するビジネスロジックを提供するサービス。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class LectureChapterService {

    @Autowired
    private LectureChapterRepository lectureChapterRepository;

    @Autowired
    private LectureRepository lectureRepository;

    /**
     * チャプター一覧をページング取得する。
     */
    @Transactional(readOnly = true)
    public Page<LectureChapterResponseDto> getAllChapters(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("DESC")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return lectureChapterRepository.findAll(PageRequest.of(page, size, sort))
                .map(this::toResponseDto);
    }

    /**
     * ID でチャプターを取得する。
     */
    @Transactional(readOnly = true)
    public Optional<LectureChapterResponseDto> getChapterById(Long id) {
        return lectureChapterRepository.findById(id).map(this::toResponseDto);
    }

    /**
     * 講義 ID に紐づくチャプターを並び順で取得する。
     */
    @Transactional(readOnly = true)
    public List<LectureChapterResponseDto> getChaptersByLectureId(Long lectureId, String sortDir) {
        List<LectureChapter> chapters = lectureChapterRepository.findByLectureIdOrderBySortOrderAsc(lectureId);
        if ("DESC".equalsIgnoreCase(sortDir)) {
            Collections.reverse(chapters);
        }
        return chapters.stream().map(this::toResponseDto).toList();
    }

    /**
     * 新しいチャプターを作成する。
     */
    public LectureChapterResponseDto createChapter(LectureChapterCreateDto dto) {
        if (!lectureRepository.existsById(dto.getLectureId())) {
            throw new IllegalArgumentException("指定された講義が存在しません: " + dto.getLectureId());
        }
        LectureChapter chapter = new LectureChapter();
        chapter.setLectureId(dto.getLectureId());
        chapter.setChapterNumber(dto.getChapterNumber());
        chapter.setTitle(dto.getTitle());
        chapter.setDescription(dto.getDescription());
        chapter.setDurationMinutes(dto.getDurationMinutes());
        chapter.setSortOrder(dto.getSortOrder());
        chapter.setIsActive(dto.getIsActive());
        return toResponseDto(lectureChapterRepository.save(chapter));
    }

    /**
     * チャプターを更新する。
     */
    public Optional<LectureChapterResponseDto> updateChapter(Long id, LectureChapterUpdateDto dto) {
        return lectureChapterRepository.findById(id).map(chapter -> {
            if (dto.getChapterNumber() != null) {
                chapter.setChapterNumber(dto.getChapterNumber());
            }
            if (dto.getTitle() != null) {
                chapter.setTitle(dto.getTitle());
            }
            if (dto.getDescription() != null) {
                chapter.setDescription(dto.getDescription());
            }
            if (dto.getDurationMinutes() != null) {
                chapter.setDurationMinutes(dto.getDurationMinutes());
            }
            if (dto.getSortOrder() != null) {
                chapter.setSortOrder(dto.getSortOrder());
            }
            if (dto.getIsActive() != null) {
                chapter.setIsActive(dto.getIsActive());
            }
            return toResponseDto(lectureChapterRepository.save(chapter));
        });
    }

    /**
     * チャプターを削除する。
     */
    public void deleteChapter(Long id) {
        lectureChapterRepository.deleteById(id);
    }

    /**
     * チャプターの表示順を更新する。
     */
    public List<LectureChapterResponseDto> reorderChapters(Long lectureId, List<Long> chapterIds) {
        List<LectureChapter> chapters = lectureChapterRepository.findByLectureIdOrderBySortOrderAsc(lectureId);
        if (chapters.size() != chapterIds.size()) {
            throw new IllegalArgumentException("チャプター数が一致しません");
        }
        // 並び替え
        int order = 1;
        for (Long chapterId : chapterIds) {
            LectureChapter chapter = chapters.stream()
                    .filter(c -> c.getId().equals(chapterId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("無効なチャプターID: " + chapterId));
            chapter.setSortOrder(order++);
            lectureChapterRepository.save(chapter);
        }
        return lectureChapterRepository.findByLectureIdOrderBySortOrderAsc(lectureId)
                .stream().map(this::toResponseDto).toList();
    }

    /**
     * チャプターを複製する。
     */
    public LectureChapterResponseDto duplicateChapter(Long id, Long targetLectureId) {
        LectureChapter original = lectureChapterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("チャプターが存在しません: " + id));
        Long lectureId = targetLectureId != null ? targetLectureId : original.getLectureId();
        LectureChapter copy = new LectureChapter();
        copy.setLectureId(lectureId);
        copy.setChapterNumber(original.getChapterNumber());
        copy.setTitle(original.getTitle());
        copy.setDescription(original.getDescription());
        copy.setDurationMinutes(original.getDurationMinutes());
        copy.setSortOrder(original.getSortOrder());
        copy.setIsActive(original.getIsActive());
        return toResponseDto(lectureChapterRepository.save(copy));
    }

    /**
     * エンティティをレスポンス DTO に変換する。
     */
    private LectureChapterResponseDto toResponseDto(LectureChapter chapter) {
        LectureChapterResponseDto dto = new LectureChapterResponseDto();
        dto.setId(chapter.getId());
        dto.setLectureId(chapter.getLectureId());
        dto.setChapterNumber(chapter.getChapterNumber());
        dto.setTitle(chapter.getTitle());
        dto.setDescription(chapter.getDescription());
        dto.setDurationMinutes(chapter.getDurationMinutes());
        dto.setSortOrder(chapter.getSortOrder());
        dto.setIsActive(chapter.getIsActive());
        dto.setCreatedAt(chapter.getCreatedAt());
        dto.setUpdatedAt(chapter.getUpdatedAt());
        return dto;
    }
}

