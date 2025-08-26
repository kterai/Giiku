package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.LectureChapter;
import jp.co.apsa.giiku.domain.repository.LectureChapterRepository;
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
 * 講義チャプターのビジネスロジックを提供する。
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

        Page<LectureChapter> chapters = lectureChapterRepository.findAll(pageable);
        return chapters.map(this::convertToResponseDto);
    }

    /**
     * IDでチャプターを取得する
     *
     * @param id チャプターID
     * @return チャプター情報
     */
    public Optional<LectureChapterResponseDto> getChapterById(Long id) {
        return lectureChapterRepository.findById(id)
                .map(this::convertToResponseDto);
    }

    /**
     * 講義IDでチャプターを取得する
     *
     * @param lectureId 講義ID
     * @param sortDir ソート方向
     * @return チャプター一覧
     */
    public List<LectureChapterResponseDto> getChaptersByLectureId(Long lectureId, String sortDir) {
        List<LectureChapter> chapters;
        if ("DESC".equalsIgnoreCase(sortDir)) {
            chapters = lectureChapterRepository.findByLectureIdOrderBySortOrderDesc(lectureId);
        } else {
            chapters = lectureChapterRepository.findByLectureIdOrderBySortOrder(lectureId);
        }
        return chapters.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * 新しいチャプターを作成する
     *
     * @param dto 作成用DTO
     * @return 作成されたチャプター
     */
    public LectureChapterResponseDto createChapter(LectureChapterCreateDto dto) {
        LectureChapter chapter = new LectureChapter();
        chapter.setLectureId(dto.getLectureId());
        chapter.setChapterNumber(dto.getChapterNumber());
        chapter.setTitle(dto.getTitle());
        chapter.setDescription(dto.getDescription());
        chapter.setDurationMinutes(dto.getDurationMinutes());
        chapter.setSortOrder(dto.getSortOrder());
        chapter.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        LectureChapter saved = lectureChapterRepository.save(chapter);
        return convertToResponseDto(saved);
    }

    /**
     * チャプターを更新する
     *
     * @param id チャプターID
     * @param dto 更新用DTO
     * @return 更新されたチャプター
     */
    public Optional<LectureChapterResponseDto> updateChapter(Long id, LectureChapterUpdateDto dto) {
        return lectureChapterRepository.findById(id)
                .map(chapter -> {
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

                    LectureChapter saved = lectureChapterRepository.save(chapter);
                    return convertToResponseDto(saved);
                });
    }

    /**
     * チャプターを削除する
     *
     * @param id チャプターID
     */
    public void deleteChapter(Long id) {
        lectureChapterRepository.deleteById(id);
    }

    /**
     * チャプターの並び順を更新する
     *
     * @param lectureId 講義ID
     * @param chapterIds 並び順のチャプターIDリスト
     * @return 更新されたチャプター一覧
     */
    public List<LectureChapterResponseDto> reorderChapters(Long lectureId, List<Long> chapterIds) {
        List<LectureChapter> chapters = lectureChapterRepository.findByLectureIdOrderBySortOrder(lectureId);

        // 並び順を更新
        for (int i = 0; i < chapterIds.size(); i++) {
            final int sortOrder = i + 1; // finalまたは事実上のfinalな変数を作成
            Long chapterId = chapterIds.get(i);
            chapters.stream()
                    .filter(chapter -> chapter.getId().equals(chapterId))
                    .findFirst()
                    .ifPresent(chapter -> chapter.setSortOrder(sortOrder)); // finalな変数を使用
        }

        // 更新されたチャプターを保存
        List<LectureChapter> savedChapters = lectureChapterRepository.saveAll(chapters);
        return savedChapters.stream()
                .map(this::convertToResponseDto)
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
        return lectureChapterRepository.findById(id)
                .map(originalChapter -> {
                    LectureChapter duplicatedChapter = mapper.map(originalChapter, LectureChapter.class);
                    duplicatedChapter.setLectureId(targetLectureId != null ? targetLectureId : originalChapter.getLectureId());
                    duplicatedChapter.setChapterNumber(getNextChapterNumber(duplicatedChapter.getLectureId()));
                    duplicatedChapter.setTitle(originalChapter.getTitle() + " (コピー)");
                    duplicatedChapter.setSortOrder(getNextSortOrder(duplicatedChapter.getLectureId()));
                    LectureChapter saved = lectureChapterRepository.save(duplicatedChapter);
                    return convertToResponseDto(saved);
                })
                .orElseThrow(() -> new RuntimeException("チャプターが見つかりません: " + id));
    }

    /**
     * 講義IDで既存のチャプター一覧を取得する（ソート順でソート）
     *
     * @param lectureId 講義ID
     * @return チャプター一覧
     */
    public List<LectureChapter> findByLectureIdOrderBySortOrder(Long lectureId) {
        return lectureChapterRepository.findByLectureIdOrderBySortOrder(lectureId);
    }

    /**
     * エンティティをレスポンスDTOに変換する
     *
     * @param chapter チャプターエンティティ
     * @return レスポンスDTO
     */
    private LectureChapterResponseDto convertToResponseDto(LectureChapter chapter) {
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

    /**
     * 講義内の次のチャプター番号を取得する
     *
     * @param lectureId 講義ID
     * @return 次のチャプター番号
     */
    private Integer getNextChapterNumber(Long lectureId) {
        return lectureChapterRepository.findMaxChapterNumberByLectureId(lectureId)
                .orElse(0) + 1;
    }

    /**
     * 講義内の次のソート順を取得する
     *
     * @param lectureId 講義ID
     * @return 次のソート順
     */
    private Integer getNextSortOrder(Long lectureId) {
        return lectureChapterRepository.findMaxSortOrderByLectureId(lectureId)
                .orElse(0) + 1;
    }
}
