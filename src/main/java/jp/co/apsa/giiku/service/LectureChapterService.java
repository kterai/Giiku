package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.LectureChapter;
import jp.co.apsa.giiku.domain.entity.Lecture;
import jp.co.apsa.giiku.domain.repository.LectureChapterRepository;
import jp.co.apsa.giiku.domain.repository.LectureRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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