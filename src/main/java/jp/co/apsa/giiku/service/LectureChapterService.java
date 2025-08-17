package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.LectureChapter;
import jp.co.apsa.giiku.domain.entity.Lecture;
import jp.co.apsa.giiku.domain.repository.LectureChapterRepository;
import jp.co.apsa.giiku.domain.repository.LectureRepository;
import jp.co.apsa.giiku.dto.LectureChapterCreateDto;
import jp.co.apsa.giiku.dto.LectureChapterUpdateDto;
import jp.co.apsa.giiku.dto.LectureChapterResponseDto;
import jp.co.apsa.giiku.dto.LectureChapterSearchDto;
import jp.co.apsa.giiku.dto.LectureChapterStatsDto;
import jp.co.apsa.giiku.dto.LectureChapterProgressDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageImpl;

/**
 * LectureChapter（講座チャプター）に関するビジネスロジックを提供するサービスクラス。
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
     * 全ての講座チャプターを取得
     *
     * @return 講座チャプターのリスト
     */
    @Transactional(readOnly = true)
    public List<LectureChapter> findAll() {
        return lectureChapterRepository.findAll();
    }

    /**
     * IDで講座チャプターを取得
     *
     * @param id 講座チャプターID
     * @return Optional<LectureChapter>
     */
    @Transactional(readOnly = true)
    public Optional<LectureChapter> findById(Long id) {
        return lectureChapterRepository.findById(id);
    }

    /**
     * 講座IDでチャプターを取得
     *
     * @param lectureId 講座ID
     * @return 講座チャプターのリスト（チャプター順序でソート）
     */
    @Transactional(readOnly = true)
    public List<LectureChapter> findByLectureId(Long lectureId) {
        return lectureChapterRepository.findByLectureIdOrderBySortOrderAsc(lectureId);
    }

    /**
     * アクティブなチャプターを取得
     *
     * @param lectureId 講座ID
     * @return アクティブな講座チャプターのリスト
     */
    @Transactional(readOnly = true)
    public List<LectureChapter> findActiveChapters(Long lectureId) {
        return lectureChapterRepository.findByLectureIdAndStatusOrderBySortOrderAsc(lectureId, "ACTIVE");
    }

    /**
     * タイトルでチャプターを検索（部分一致）
     *
     * @param title タイトル（部分検索）
     * @return 講座チャプターのリスト
     */
    @Transactional(readOnly = true)
    public List<LectureChapter> searchByTitle(String title) {
        return lectureChapterRepository.findByTitleContainingIgnoreCaseAndStatusOrderByTitleAsc(title, "ACTIVE");
    }

    /**
     * 複合条件でチャプターを検索
     *
     * @param lectureId 講座ID（オプション）
     * @param title タイトル（部分検索、オプション）
     * @param isActive アクティブフラグ（オプション）
     * @param pageable ページング情報
     * @return ページング対応の講座チャプター
     */
    @Transactional(readOnly = true)
    public Page<LectureChapter> searchChapters(Long lectureId, String title, Boolean isActive, Pageable pageable) {
        Specification<LectureChapter> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (lectureId != null) {
                predicates.add(criteriaBuilder.equal(root.get("lectureId"), lectureId));
            }

            if (title != null && !title.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")), 
                    "%" + title.toLowerCase() + "%"
                ));
            }

            if (isActive != null) {
                if (isActive) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), "ACTIVE"));
                } else {
                    predicates.add(criteriaBuilder.notEqual(root.get("status"), "ACTIVE"));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return lectureChapterRepository.findAll(spec, pageable);
    }

    /**
     * 講座チャプターを作成
     *
     * @param lectureChapter 講座チャプター
     * @return 保存された講座チャプター
     * @throws IllegalArgumentException バリデーションエラー
     */
    public LectureChapter save(LectureChapter lectureChapter) {
        validateLectureChapter(lectureChapter);

        // 講座存在チェック
        if (lectureChapter.getLectureId() != null) {
            Optional<Lecture> lecture = lectureRepository.findById(lectureChapter.getLectureId());
            if (!lecture.isPresent()) {
                throw new IllegalArgumentException("指定された講座が存在しません: " + lectureChapter.getLectureId());
            }
        }

        return lectureChapterRepository.save(lectureChapter);
    }

    /**
     * 講座チャプターを更新
     *
     * @param id 講座チャプターID
     * @param lectureChapter 更新する講座チャプター
     * @return 更新された講座チャプター
     * @throws IllegalArgumentException IDが存在しない場合
     */
    public LectureChapter update(Long id, LectureChapter lectureChapter) {
        Optional<LectureChapter> existingChapter = lectureChapterRepository.findById(id);
        if (!existingChapter.isPresent()) {
            throw new IllegalArgumentException("指定された講座チャプターが存在しません: " + id);
        }

        lectureChapter.setChapterId(id);
        validateLectureChapter(lectureChapter);

        return lectureChapterRepository.save(lectureChapter);
    }

    /**
     * 講座チャプターを論理削除（非アクティブ化）
     *
     * @param id 講座チャプターID
     * @throws IllegalArgumentException IDが存在しない場合
     */
    public void deactivate(Long id) {
        Optional<LectureChapter> lectureChapter = lectureChapterRepository.findById(id);
        if (!lectureChapter.isPresent()) {
            throw new IllegalArgumentException("指定された講座チャプターが存在しません: " + id);
        }

        LectureChapter chapter = lectureChapter.get();
        chapter.setStatus("INACTIVE");
        lectureChapterRepository.save(chapter);
    }

    /**
     * 講座チャプターを物理削除
     *
     * @param id 講座チャプターID
     * @throws IllegalArgumentException IDが存在しない場合
     */
    public void delete(Long id) {
        if (!lectureChapterRepository.existsById(id)) {
            throw new IllegalArgumentException("指定された講座チャプターが存在しません: " + id);
        }
        lectureChapterRepository.deleteById(id);
    }

    /**
     * 講座のチャプター数を取得
     *
     * @param lectureId 講座ID
     * @return チャプター数
     */
    @Transactional(readOnly = true)
    public long countByLectureId(Long lectureId) {
        return lectureChapterRepository.countByLectureIdAndStatus(lectureId, "ACTIVE");
    }

    /**
     * チャプター順序を更新
     *
     * @param id 講座チャプターID
     * @param newOrder 新しい順序
     * @throws IllegalArgumentException IDが存在しない場合
     */
    public void updateChapterOrder(Long id, Integer newOrder) {
        Optional<LectureChapter> lectureChapter = lectureChapterRepository.findById(id);
        if (!lectureChapter.isPresent()) {
            throw new IllegalArgumentException("指定された講座チャプターが存在しません: " + id);
        }

        LectureChapter chapter = lectureChapter.get();
        chapter.setSortOrder(newOrder);
        lectureChapterRepository.save(chapter);
    }

    /**
     * チャプター一覧取得（スタブ）
     * @param page ページ番号
     * @param size ページサイズ
     * @param sortBy ソート対象
     * @param sortDir ソート方向
     * @return チャプターDTOページ
     */
    @Transactional(readOnly = true)
    public Page<LectureChapterResponseDto> getAllChapters(int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<LectureChapter> chapters = lectureChapterRepository.findAll(pageable);
        List<LectureChapterResponseDto> content = chapters.map(this::toDto).getContent();
        return new PageImpl<>(content, chapters.getPageable(), chapters.getTotalElements());
    }

    /**
     * チャプター詳細取得（スタブ）
     * @param id チャプターID
     * @return チャプターDTO
     */
    @Transactional(readOnly = true)
    public Optional<LectureChapterResponseDto> getChapterById(Long id) {
        return lectureChapterRepository.findById(id).map(this::toDto);
    }

    /**
     * 講義別チャプター取得（スタブ）
     * @param lectureId 講義ID
     * @param sortBy ソート対象
     * @param sortDir ソート方向
     * @return チャプターDTOリスト
     */
    @Transactional(readOnly = true)
    public List<LectureChapterResponseDto> getChaptersByLectureId(Long lectureId, String sortBy, String sortDir) {
        List<LectureChapter> list = lectureChapterRepository.findByLectureIdOrderBySortOrderAsc(lectureId);
        return list.stream().map(this::toDto).toList();
    }

    /**
     * チャプター作成（スタブ）
     * @param dto 作成DTO
     * @return 作成結果DTO
     */
    public LectureChapterResponseDto createChapter(LectureChapterCreateDto dto) {
        LectureChapter chapter = new LectureChapter();
        chapter.setLectureId(dto.getLectureId());
        chapter.setTitle(dto.getTitle());
        chapter.setDescription(dto.getDescription());
        chapter.setSortOrder(dto.getOrderNumber());
        chapter.setDurationMinutes(dto.getEstimatedMinutes());
        chapter.setStatus("ACTIVE");
        LectureChapter saved = lectureChapterRepository.save(chapter);
        return toDto(saved);
    }

    /**
     * チャプター更新（スタブ）
     * @param id チャプターID
     * @param dto 更新DTO
     * @return 更新結果DTO
     */
    public Optional<LectureChapterResponseDto> updateChapter(Long id, LectureChapterUpdateDto dto) {
        Optional<LectureChapter> opt = lectureChapterRepository.findById(id);
        if (opt.isEmpty()) {
            return Optional.empty();
        }
        LectureChapter chapter = opt.get();
        if (dto.getTitle() != null) chapter.setTitle(dto.getTitle());
        if (dto.getDescription() != null) chapter.setDescription(dto.getDescription());
        if (dto.getOrderNumber() != null) chapter.setSortOrder(dto.getOrderNumber());
        if (dto.getEstimatedMinutes() != null) chapter.setDurationMinutes(dto.getEstimatedMinutes());
        if (dto.getIsActive() != null) {
            chapter.setStatus(dto.getIsActive() ? "ACTIVE" : "INACTIVE");
        }
        LectureChapter saved = lectureChapterRepository.save(chapter);
        return Optional.of(toDto(saved));
    }

    /**
     * チャプター削除（スタブ）
     * @param id チャプターID
     */
    public boolean deleteChapter(Long id) {
        if (!lectureChapterRepository.existsById(id)) {
            return false;
        }
        lectureChapterRepository.deleteById(id);
        return true;
    }

    /**
     * チャプター進捗取得（スタブ）
     * @param lectureId 講義ID
     * @param chapterId チャプターID
     * @return 進捗DTO
     */
    @Transactional(readOnly = true)
    public List<LectureChapterProgressDto> getChapterProgress(Long chapterId, Long studentId) {
        return new ArrayList<>();
    }

    /**
     * チャプター進捗更新（スタブ）
     * @param id チャプターID
     * @param dto 進捗DTO
     * @return 更新結果DTO
     */
    public LectureChapterProgressDto updateChapterProgress(Long id, LectureChapterProgressDto dto) {
        return new LectureChapterProgressDto();
    }

    /**
     * チャプター検索（スタブ）
     * @param searchDto 検索条件DTO
     * @param page ページ番号
     * @param size ページサイズ
     * @param sortBy ソート対象
     * @param sortDir ソート方向
     * @return チャプターDTOページ
     */
    @Transactional(readOnly = true)
    public Page<LectureChapterResponseDto> searchChapters(LectureChapterSearchDto searchDto,
                                                         int page, int size, String sortBy, String sortDir) {
        return getAllChapters(page, size, sortBy, sortDir);
    }

    /**
     * チャプター統計情報取得（スタブ）
     * @param lectureId 講義ID
     * @param period 期間
     * @return 統計DTO
     */
    @Transactional(readOnly = true)
    public LectureChapterStatsDto getChapterStats(Long lectureId, String period) {
        return new LectureChapterStatsDto();
    }

    /**
     * チャプター順序再配置（スタブ）
     * @param lectureId 講義ID
     * @param chapterIds チャプターIDリスト
     * @return 再配置結果DTOリスト
     */
    public List<LectureChapterResponseDto> reorderChapters(Long lectureId, List<Long> chapterIds) {
        return new ArrayList<>();
    }

    /**
     * チャプター複製（スタブ）
     * @param id 元チャプターID
     * @param targetLectureId 対象講義ID
     * @return 複製結果DTO
     */
    public LectureChapterResponseDto duplicateChapter(Long id, Long targetLectureId) {
        return new LectureChapterResponseDto();
    }

    private LectureChapterResponseDto toDto(LectureChapter chapter) {
        LectureChapterResponseDto dto = new LectureChapterResponseDto();
        dto.setId(chapter.getChapterId());
        dto.setLectureId(chapter.getLectureId());
        dto.setTitle(chapter.getTitle());
        dto.setDescription(chapter.getDescription());
        dto.setOrderNumber(chapter.getSortOrder());
        dto.setEstimatedMinutes(chapter.getDurationMinutes());
        dto.setIsActive("ACTIVE".equals(chapter.getStatus()));
        return dto;
    }

    /**
     * 講座チャプターのバリデーション
     *
     * @param lectureChapter 検証対象の講座チャプター
     * @throws IllegalArgumentException バリデーションエラー
     */
    private void validateLectureChapter(LectureChapter lectureChapter) {
        if (lectureChapter == null) {
            throw new IllegalArgumentException("講座チャプターが null です");
        }

        if (lectureChapter.getLectureId() == null) {
            throw new IllegalArgumentException("講座IDは必須です");
        }

        if (lectureChapter.getTitle() == null || lectureChapter.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("チャプタータイトルは必須です");
        }

        if (lectureChapter.getSortOrder() == null || lectureChapter.getSortOrder() <= 0) {
            throw new IllegalArgumentException("チャプター順序は正の数である必要があります");
        }

        if (lectureChapter.getDurationMinutes() != null && lectureChapter.getDurationMinutes() < 0) {
            throw new IllegalArgumentException("実施時間は0以上である必要があります");
        }
    }
}
