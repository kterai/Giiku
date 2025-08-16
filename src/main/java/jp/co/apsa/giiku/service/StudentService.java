package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.StudentProfile;
import jp.co.apsa.giiku.domain.entity.User;
import jp.co.apsa.giiku.domain.entity.Company;
import jp.co.apsa.giiku.domain.repository.StudentProfileRepository;
import jp.co.apsa.giiku.domain.repository.UserRepository;
import jp.co.apsa.giiku.domain.repository.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Student（学生プロフィール）に関するビジネスロジックを提供するサービスクラス
 * 
 * @author Giiku LMS Team
 * @version 1.0
 * @since 2025-08-14
 */
@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * 全ての学生プロフィールを取得
     * 
     * @return 学生プロフィールのリスト
     */
    @Transactional(readOnly = true)
    public List<StudentProfile> findAll() {
        return studentProfileRepository.findAll();
    }

    /**
     * IDで学生プロフィールを取得
     * 
     * @param id 学生プロフィールID
     * @return Optional<StudentProfile>
     */
    @Transactional(readOnly = true)
    public Optional<StudentProfile> findById(Long id) {
        return studentProfileRepository.findById(id);
    }

    /**
     * ユーザーIDで学生プロフィールを取得
     * 
     * @param userId ユーザーID
     * @return Optional<StudentProfile>
     */
    @Transactional(readOnly = true)
    public Optional<StudentProfile> findByUserId(Long userId) {
        return studentProfileRepository.findByUserId(userId);
    }

    /**
     * 企業IDで学生プロフィールを取得
     * 
     * @param companyId 企業ID
     * @return 学生プロフィールのリスト
     */
    @Transactional(readOnly = true)
    public List<StudentProfile> findByCompanyId(Long companyId) {
        return studentProfileRepository.findByCompanyId(companyId);
    }

    /**
     * アクティブな学生プロフィールを取得
     * 
     * @return アクティブな学生プロフィールのリスト
     */
    @Transactional(readOnly = true)
    public List<StudentProfile> findActiveStudents() {
        return studentProfileRepository.findByIsActiveTrueOrderByEnrollmentDateDesc();
    }

    /**
     * レベルで学生プロフィールを検索
     * 
     * @param currentLevel レベル
     * @return 学生プロフィールのリスト
     */
    @Transactional(readOnly = true)
    public List<StudentProfile> findByCurrentLevel(String currentLevel) {
        return studentProfileRepository.findByCurrentLevelAndIsActiveTrueOrderByEnrollmentDateDesc(currentLevel);
    }

    /**
     * 目標で学生プロフィールを検索
     * 
     * @param goal 目標（部分検索）
     * @return 学生プロフィールのリスト
     */
    @Transactional(readOnly = true)
    public List<StudentProfile> searchByGoal(String goal) {
        return studentProfileRepository.findByGoalContainingIgnoreCaseAndIsActiveTrueOrderByEnrollmentDateDesc(goal);
    }

    /**
     * 複合条件で学生プロフィールを検索
     * 
     * @param companyId 企業ID（オプション）
     * @param currentLevel レベル（オプション）
     * @param goal 目標（部分検索、オプション）
     * @param isActive アクティブフラグ（オプション）
     * @param enrollmentDateFrom 入学日FROM（オプション）
     * @param enrollmentDateTo 入学日TO（オプション）
     * @param pageable ページング情報
     * @return ページング対応の学生プロフィール
     */
    @Transactional(readOnly = true)
    public Page<StudentProfile> searchStudents(Long companyId, String currentLevel, String goal, 
                                             Boolean isActive, LocalDate enrollmentDateFrom, 
                                             LocalDate enrollmentDateTo, Pageable pageable) {
        Specification<StudentProfile> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (companyId != null) {
                predicates.add(criteriaBuilder.equal(root.get("companyId"), companyId));
            }

            if (currentLevel != null && !currentLevel.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("currentLevel"), currentLevel));
            }

            if (goal != null && !goal.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("goal")), 
                    "%" + goal.toLowerCase() + "%"
                ));
            }

            if (isActive != null) {
                predicates.add(criteriaBuilder.equal(root.get("isActive"), isActive));
            }

            if (enrollmentDateFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("enrollmentDate"), enrollmentDateFrom));
            }

            if (enrollmentDateTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("enrollmentDate"), enrollmentDateTo));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return studentProfileRepository.findAll(spec, pageable);
    }

    /**
     * 学生プロフィールを作成
     * 
     * @param studentProfile 学生プロフィール
     * @return 保存された学生プロフィール
     * @throws IllegalArgumentException バリデーションエラー
     */
    public StudentProfile save(StudentProfile studentProfile) {
        validateStudentProfile(studentProfile);

        // ユーザー存在チェック
        if (studentProfile.getUserId() != null) {
            Optional<User> user = userRepository.findById(studentProfile.getUserId());
            if (!user.isPresent()) {
                throw new IllegalArgumentException("指定されたユーザーが存在しません: " + studentProfile.getUserId());
            }
        }

        // 企業存在チェック
        if (studentProfile.getCompanyId() != null) {
            Optional<Company> company = companyRepository.findById(studentProfile.getCompanyId());
            if (!company.isPresent()) {
                throw new IllegalArgumentException("指定された企業が存在しません: " + studentProfile.getCompanyId());
            }
        }

        // ユーザーIDの重複チェック
        if (studentProfile.getId() == null) { // 新規作成時のみ
            Optional<StudentProfile> existingProfile = studentProfileRepository.findByUserId(studentProfile.getUserId());
            if (existingProfile.isPresent()) {
                throw new IllegalArgumentException("このユーザーの学生プロフィールは既に存在します: " + studentProfile.getUserId());
            }
        }

        return studentProfileRepository.save(studentProfile);
    }

    /**
     * 学生プロフィールを更新
     * 
     * @param id 学生プロフィールID
     * @param studentProfile 更新する学生プロフィール
     * @return 更新された学生プロフィール
     * @throws IllegalArgumentException IDが存在しない場合
     */
    public StudentProfile update(Long id, StudentProfile studentProfile) {
        Optional<StudentProfile> existingProfile = studentProfileRepository.findById(id);
        if (!existingProfile.isPresent()) {
            throw new IllegalArgumentException("指定された学生プロフィールが存在しません: " + id);
        }

        studentProfile.setId(id);
        validateStudentProfile(studentProfile);

        return studentProfileRepository.save(studentProfile);
    }

    /**
     * 学生プロフィールを論理削除（非アクティブ化）
     * 
     * @param id 学生プロフィールID
     * @throws IllegalArgumentException IDが存在しない場合
     */
    public void deactivate(Long id) {
        Optional<StudentProfile> studentProfile = studentProfileRepository.findById(id);
        if (!studentProfile.isPresent()) {
            throw new IllegalArgumentException("指定された学生プロフィールが存在しません: " + id);
        }

        StudentProfile profile = studentProfile.get();
        profile.setIsActive(false);
        studentProfileRepository.save(profile);
    }

    /**
     * 学生プロフィールを物理削除
     * 
     * @param id 学生プロフィールID
     * @throws IllegalArgumentException IDが存在しない場合
     */
    public void delete(Long id) {
        if (!studentProfileRepository.existsById(id)) {
            throw new IllegalArgumentException("指定された学生プロフィールが存在しません: " + id);
        }
        studentProfileRepository.deleteById(id);
    }

    /**
     * 企業の学生数を取得
     * 
     * @param companyId 企業ID
     * @return 学生数
     */
    @Transactional(readOnly = true)
    public long countByCompanyId(Long companyId) {
        return studentProfileRepository.countByCompanyIdAndIsActiveTrue(companyId);
    }

    /**
     * レベル別学生数を取得
     * 
     * @param currentLevel レベル
     * @return 学生数
     */
    @Transactional(readOnly = true)
    public long countByCurrentLevel(String currentLevel) {
        return studentProfileRepository.countByCurrentLevelAndIsActiveTrue(currentLevel);
    }

    /**
     * 最近入学した学生を取得
     * 
     * @param limit 取得件数
     * @return 最近入学した学生のリスト
     */
    @Transactional(readOnly = true)
    public List<StudentProfile> findRecentlyEnrolledStudents(int limit) {
        return studentProfileRepository.findByIsActiveTrueOrderByEnrollmentDateDescLimit(limit);
    }

    /**
     * 学生プロフィールのバリデーション
     * 
     * @param studentProfile 検証対象の学生プロフィール
     * @throws IllegalArgumentException バリデーションエラー
     */
    private void validateStudentProfile(StudentProfile studentProfile) {
        if (studentProfile == null) {
            throw new IllegalArgumentException("学生プロフィールが null です");
        }

        if (studentProfile.getUserId() == null) {
            throw new IllegalArgumentException("ユーザーIDは必須です");
        }

        if (studentProfile.getCompanyId() == null) {
            throw new IllegalArgumentException("企業IDは必須です");
        }

        if (studentProfile.getEnrollmentDate() == null) {
            throw new IllegalArgumentException("入学日は必須です");
        }

        if (studentProfile.getCurrentLevel() == null || studentProfile.getCurrentLevel().trim().isEmpty()) {
            throw new IllegalArgumentException("現在のレベルは必須です");
        }
    }
}