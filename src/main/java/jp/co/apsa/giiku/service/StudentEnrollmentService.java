package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.StudentEnrollment;
import jp.co.apsa.giiku.domain.entity.StudentProfile;
import jp.co.apsa.giiku.domain.entity.TrainingProgram;
import jp.co.apsa.giiku.domain.repository.StudentEnrollmentRepository;
import jp.co.apsa.giiku.domain.repository.StudentProfileRepository;
import jp.co.apsa.giiku.domain.repository.TrainingProgramRepository;

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
 * StudentEnrollmentサービスクラス
 * 学生の受講登録管理機能を提供
 */
@Service
@Transactional
public class StudentEnrollmentService {

    @Autowired
    private StudentEnrollmentRepository studentEnrollmentRepository;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    /**
     * 全ての受講登録を取得
     */
    @Transactional(readOnly = true)
    public List<StudentEnrollment> findAll() {
        return studentEnrollmentRepository.findAll();
    }

    /**
     * IDで受講登録を取得
     */
    @Transactional(readOnly = true)
    public Optional<StudentEnrollment> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }
        return studentEnrollmentRepository.findById(id);
    }

    /**
     * 受講登録を保存
     */
    public StudentEnrollment save(StudentEnrollment enrollment) {
        validateEnrollment(enrollment);

        // 重複チェック
        if (isDuplicateEnrollment(enrollment.getStudentId(), enrollment.getProgramId())) {
            throw new IllegalArgumentException("この学生は既に当該プログラムに登録済みです");
        }

        if (enrollment.getId() == null) {
            enrollment.setEnrollmentDate(LocalDateTime.now());
            enrollment.setStatus("ENROLLED");
        }
        enrollment.setUpdatedAt(LocalDateTime.now());

        return studentEnrollmentRepository.save(enrollment);
    }

    /**
     * 受講登録を更新
     */
    public StudentEnrollment update(Long id, StudentEnrollment enrollment) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        StudentEnrollment existing = studentEnrollmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("受講登録が見つかりません: " + id));

        validateEnrollment(enrollment);

        // 基本情報の更新（学生IDとプログラムIDは変更不可）
        existing.setStatus(enrollment.getStatus());
        existing.setProgress(enrollment.getProgress());
        existing.setCompletionDate(enrollment.getCompletionDate());
        existing.setFinalGrade(enrollment.getFinalGrade());
        existing.setNotes(enrollment.getNotes());
        existing.setUpdatedAt(LocalDateTime.now());

        return studentEnrollmentRepository.save(existing);
    }

    /**
     * 受講登録を削除
     */
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        if (!studentEnrollmentRepository.existsById(id)) {
            throw new RuntimeException("受講登録が見つかりません: " + id);
        }

        studentEnrollmentRepository.deleteById(id);
    }

    /**
     * 学生IDで受講登録を検索
     */
    @Transactional(readOnly = true)
    public List<StudentEnrollment> findByStudentId(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("学生IDは必須です");
        }
        return studentEnrollmentRepository.findByStudentIdOrderByEnrollmentDateDesc(studentId);
    }

    /**
     * プログラムIDで受講登録を検索
     */
    @Transactional(readOnly = true)
    public List<StudentEnrollment> findByProgramId(Long programId) {
        if (programId == null) {
            throw new IllegalArgumentException("プログラムIDは必須です");
        }
        return studentEnrollmentRepository.findByProgramIdOrderByEnrollmentDateDesc(programId);
    }

    /**
     * ステータスで受講登録を検索
     */
    @Transactional(readOnly = true)
    public List<StudentEnrollment> findByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("ステータスは必須です");
        }
        return studentEnrollmentRepository.findByStatusOrderByEnrollmentDateDesc(status);
    }

    /**
     * アクティブな受講登録を取得（ENROLLED、IN_PROGRESS）
     */
    @Transactional(readOnly = true)
    public List<StudentEnrollment> findActiveEnrollments() {
        return studentEnrollmentRepository.findByStatusInOrderByEnrollmentDateDesc(
            List.of("ENROLLED", "IN_PROGRESS"));
    }

    /**
     * 完了した受講登録を取得
     */
    @Transactional(readOnly = true)
    public List<StudentEnrollment> findCompletedEnrollments() {
        return studentEnrollmentRepository.findByStatusOrderByCompletionDateDesc("COMPLETED");
    }

    /**
     * 学生の進行中受講登録を取得
     */
    @Transactional(readOnly = true)
    public List<StudentEnrollment> findStudentActiveEnrollments(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("学生IDは必須です");
        }
        return studentEnrollmentRepository.findByStudentIdAndStatusInOrderByEnrollmentDateDesc(
            studentId, List.of("ENROLLED", "IN_PROGRESS"));
    }

    /**
     * プログラムの受講状況レポート
     */
    @Transactional(readOnly = true)
    public List<Object[]> getProgramEnrollmentReport(Long programId) {
        if (programId == null) {
            throw new IllegalArgumentException("プログラムIDは必須です");
        }
        return studentEnrollmentRepository.findEnrollmentStatsByProgramId(programId);
    }

    /**
     * 複合条件での検索
     */
    @Transactional(readOnly = true)
    public Page<StudentEnrollment> findWithFilters(Long studentId, Long programId, String status,
                                                  LocalDateTime enrollmentDateFrom,
                                                  LocalDateTime enrollmentDateTo,
                                                  Double progressMin, Double progressMax,
                                                  Pageable pageable) {

        Specification<StudentEnrollment> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (studentId != null) {
                predicates.add(criteriaBuilder.equal(root.get("studentId"), studentId));
            }

            if (programId != null) {
                predicates.add(criteriaBuilder.equal(root.get("programId"), programId));
            }

            if (status != null && !status.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (enrollmentDateFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("enrollmentDate"), enrollmentDateFrom));
            }

            if (enrollmentDateTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("enrollmentDate"), enrollmentDateTo));
            }

            if (progressMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("progress"), progressMin));
            }

            if (progressMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("progress"), progressMax));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return studentEnrollmentRepository.findAll(spec, pageable);
    }

    /**
     * 進捗更新
     */
    public StudentEnrollment updateProgress(Long id, Double progress) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        if (progress == null || progress < 0.0 || progress > 100.0) {
            throw new IllegalArgumentException("進捗は0-100の範囲で入力してください");
        }

        StudentEnrollment enrollment = studentEnrollmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("受講登録が見つかりません: " + id));

        enrollment.setProgress(progress);
        enrollment.setUpdatedAt(LocalDateTime.now());

        // 進捗に応じてステータスを自動更新
        if (progress == 0.0) {
            enrollment.setStatus("ENROLLED");
        } else if (progress == 100.0) {
            enrollment.setStatus("COMPLETED");
            enrollment.setCompletionDate(LocalDateTime.now());
        } else {
            enrollment.setStatus("IN_PROGRESS");
        }

        return studentEnrollmentRepository.save(enrollment);
    }

    /**
     * 受講登録数をカウント
     */
    @Transactional(readOnly = true)
    public long countAll() {
        return studentEnrollmentRepository.count();
    }

    /**
     * ステータス別の受講登録数をカウント
     */
    @Transactional(readOnly = true)
    public long countByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("ステータスは必須です");
        }
        return studentEnrollmentRepository.countByStatus(status);
    }

    /**
     * 重複受講登録チェック
     */
    private boolean isDuplicateEnrollment(Long studentId, Long programId) {
        return studentEnrollmentRepository.existsByStudentIdAndProgramId(studentId, programId);
    }

    /**
     * 受講登録のバリデーション
     */
    private void validateEnrollment(StudentEnrollment enrollment) {
        if (enrollment == null) {
            throw new IllegalArgumentException("受講登録は必須です");
        }

        if (enrollment.getStudentId() == null) {
            throw new IllegalArgumentException("学生IDは必須です");
        }

        if (enrollment.getProgramId() == null) {
            throw new IllegalArgumentException("プログラムIDは必須です");
        }

        // 学生存在チェック
        if (!studentProfileRepository.existsById(enrollment.getStudentId())) {
            throw new IllegalArgumentException("指定された学生が存在しません");
        }

        // プログラム存在チェック
        if (!trainingProgramRepository.existsById(enrollment.getProgramId())) {
            throw new IllegalArgumentException("指定されたプログラムが存在しません");
        }

        // 進捗の範囲チェック
        if (enrollment.getProgress() != null && 
            (enrollment.getProgress() < 0.0 || enrollment.getProgress() > 100.0)) {
            throw new IllegalArgumentException("進捗は0-100の範囲で入力してください");
        }

        // 成績の範囲チェック
        if (enrollment.getFinalGrade() != null && 
            (enrollment.getFinalGrade() < 0.0 || enrollment.getFinalGrade() > 100.0)) {
            throw new IllegalArgumentException("最終成績は0-100の範囲で入力してください");
        }
    }
}
