package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.Lecture;
import jp.co.apsa.giiku.domain.entity.TrainingProgram;
import jp.co.apsa.giiku.domain.entity.User;
import jp.co.apsa.giiku.domain.repository.LectureRepository;
import jp.co.apsa.giiku.domain.repository.TrainingProgramRepository;
import jp.co.apsa.giiku.domain.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Lecture（講座）に関するビジネスロジックを提供するサービスクラス
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 全ての講座を取得
     *
     * @return 講座のリスト
     */
    @Transactional(readOnly = true)
    public List<Lecture> findAll() {
        return lectureRepository.findAll();
    }

    /**
     * IDで講座を取得
     *
     * @param id 講座ID
     * @return Optional<Lecture>
     */
    @Transactional(readOnly = true)
    public Optional<Lecture> findById(Long id) {
        return lectureRepository.findById(id);
    }

    /**
     * 研修プログラムIDで講座を取得
     *
     * @param trainingProgramId 研修プログラムID
     * @return 講座のリスト
     */
    @Transactional(readOnly = true)
    public List<Lecture> findByTrainingProgramId(Long trainingProgramId) {
        return lectureRepository.findByTrainingProgramIdAndIsActiveTrue(trainingProgramId);
    }

    /**
     * 講師IDで講座を取得
     *
     * @param instructorId 講師ID
     * @return 講座のリスト
     */
    @Transactional(readOnly = true)
    public List<Lecture> findByInstructorId(Long instructorId) {
        return lectureRepository.findByInstructorIdAndIsActiveTrueOrderByScheduleDateAsc(instructorId);
    }

    /**
     * アクティブな講座を取得
     *
     * @return アクティブな講座のリスト
     */
    @Transactional(readOnly = true)
    public List<Lecture> findActiveWebinars() {
        return lectureRepository.findByIsActiveTrueOrderByScheduleDateAsc();
    }

    /**
     * カテゴリで講座を検索
     *
     * @param category カテゴリ
     * @return 講座のリスト
     */
    @Transactional(readOnly = true)
    public List<Lecture> findByCategory(String category) {
        return lectureRepository.findByCategoryAndIsActiveTrueOrderByTitleAsc(category);
    }

    /**
     * タイトルで講座を検索（部分一致）
     *
     * @param title タイトル（部分検索）
     * @return 講座のリスト
     */
    @Transactional(readOnly = true)
    public List<Lecture> searchByTitle(String title) {
        return lectureRepository.findByTitleContainingIgnoreCaseAndIsActiveTrueOrderByTitleAsc(title);
    }

    /**
     * 複合条件で講座を検索
     *
     * @param trainingProgramId 研修プログラムID（オプション）
     * @param instructorId 講師ID（オプション）
     * @param category カテゴリ（オプション）
     * @param title タイトル（部分検索、オプション）
     * @param isActive アクティブフラグ（オプション）
     * @param pageable ページング情報
     * @return ページング対応の講座
     */
    @Transactional(readOnly = true)
    public Page<Lecture> searchLectures(Long trainingProgramId, Long instructorId, String category, 
                                       String title, Boolean isActive, Pageable pageable) {
        Specification<Lecture> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (trainingProgramId != null) {
                predicates.add(criteriaBuilder.equal(root.get("trainingProgramId"), trainingProgramId));
            }

            if (instructorId != null) {
                predicates.add(criteriaBuilder.equal(root.get("instructorId"), instructorId));
            }

            if (category != null && !category.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
            }

            if (title != null && !title.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")), 
                    "%" + title.toLowerCase() + "%"
                ));
            }

            if (isActive != null) {
                predicates.add(criteriaBuilder.equal(root.get("isActive"), isActive));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return lectureRepository.findAll(spec, pageable);
    }

    /**
     * 講座を作成
     *
     * @param lecture 講座
     * @return 保存された講座
     * @throws IllegalArgumentException バリデーションエラー
     */
    public Lecture save(Lecture lecture) {
        validateLecture(lecture);

        // 研修プログラム存在チェック
        if (lecture.getTrainingProgramId() != null) {
            Optional<TrainingProgram> trainingProgram = trainingProgramRepository.findById(lecture.getTrainingProgramId());
            if (!trainingProgram.isPresent()) {
                throw new IllegalArgumentException("指定された研修プログラムが存在しません: " + lecture.getTrainingProgramId());
            }
        }

        // 講師存在チェック
        if (lecture.getInstructorId() != null) {
            Optional<User> instructor = userRepository.findById(lecture.getInstructorId());
            if (!instructor.isPresent()) {
                throw new IllegalArgumentException("指定された講師が存在しません: " + lecture.getInstructorId());
            }
        }

        return lectureRepository.save(lecture);
    }

    /**
     * 講座を更新
     *
     * @param id 講座ID
     * @param lecture 更新する講座
     * @return 更新された講座
     * @throws IllegalArgumentException IDが存在しない場合
     */
    public Lecture update(Long id, Lecture lecture) {
        Optional<Lecture> existingLecture = lectureRepository.findById(id);
        if (!existingLecture.isPresent()) {
            throw new IllegalArgumentException("指定された講座が存在しません: " + id);
        }

        lecture.setId(id);
        validateLecture(lecture);

        return lectureRepository.save(lecture);
    }

    /**
     * 講座を論理削除（非アクティブ化）
     *
     * @param id 講座ID
     * @throws IllegalArgumentException IDが存在しない場合
     */
    public void deactivate(Long id) {
        Optional<Lecture> lecture = lectureRepository.findById(id);
        if (!lecture.isPresent()) {
            throw new IllegalArgumentException("指定された講座が存在しません: " + id);
        }

        Lecture webinar = lecture.get();
        webinar.setIsActive(false);
        lectureRepository.save(webinar);
    }

    /**
     * 講座を物理削除
     *
     * @param id 講座ID
     * @throws IllegalArgumentException IDが存在しない場合
     */
    public void delete(Long id) {
        if (!lectureRepository.existsById(id)) {
            throw new IllegalArgumentException("指定された講座が存在しません: " + id);
        }
        lectureRepository.deleteById(id);
    }

    /**
     * 研修プログラムの講座数を取得
     *
     * @param trainingProgramId 研修プログラムID
     * @return 講座数
     */
    @Transactional(readOnly = true)
    public long countByTrainingProgramId(Long trainingProgramId) {
        return lectureRepository.countByTrainingProgramIdAndIsActiveTrue(trainingProgramId);
    }

    /**
     * 講師の講座数を取得
     *
     * @param instructorId 講師ID
     * @return 講座数
     */
    @Transactional(readOnly = true)
    public long countByInstructorId(Long instructorId) {
        return lectureRepository.countByInstructorIdAndIsActiveTrue(instructorId);
    }

    /**
     * 今後予定されている講座を取得
     *
     * @return 今後の講座のリスト
     */
    @Transactional(readOnly = true)
    public List<Lecture> findUpcomingLectures() {
        LocalDateTime now = LocalDateTime.now();
        return lectureRepository.findByScheduleDateGreaterThanAndIsActiveTrueOrderByScheduleDateAsc(now);
    }

    /**
     * 講座のバリデーション
     *
     * @param lecture 検証対象の講座
     * @throws IllegalArgumentException バリデーションエラー
     */
    private void validateLecture(Lecture lecture) {
        if (lecture == null) {
            throw new IllegalArgumentException("講座が null です");
        }

        if (lecture.getTitle() == null || lecture.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("講座タイトルは必須です");
        }

        if (lecture.getTrainingProgramId() == null) {
            throw new IllegalArgumentException("研修プログラムIDは必須です");
        }

        if (lecture.getInstructorId() == null) {
            throw new IllegalArgumentException("講師IDは必須です");
        }

        if (lecture.getScheduleDate() == null) {
            throw new IllegalArgumentException("スケジュール日時は必須です");
        }

        if (lecture.getDurationMinutes() != null && lecture.getDurationMinutes() <= 0) {
            throw new IllegalArgumentException("実施時間は正の数である必要があります");
        }
    }
}
