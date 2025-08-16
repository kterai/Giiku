package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.Grade;
import jp.co.apsa.giiku.domain.entity.StudentProfile;
import jp.co.apsa.giiku.domain.entity.TrainingProgram;
import jp.co.apsa.giiku.domain.repository.GradeRepository;
import jp.co.apsa.giiku.domain.repository.StudentProfileRepository;
import jp.co.apsa.giiku.domain.repository.TrainingProgramRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Gradeサービスクラス
 * 成績管理機能を提供
 */
@Service
@Transactional
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    /**
     * 全ての成績を取得
     */
    @Transactional(readOnly = true)
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    /**
     * IDで成績を取得
     */
    @Transactional(readOnly = true)
    public Optional<Grade> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }
        return gradeRepository.findById(id);
    }

    /**
     * 成績を保存
     */
    public Grade save(Grade grade) {
        validateGrade(grade);

        if (grade.getId() == null) {
            grade.setCreatedAt(LocalDateTime.now());
        }
        grade.setUpdatedAt(LocalDateTime.now());

        return gradeRepository.save(grade);
    }

    /**
     * 成績を更新
     */
    public Grade update(Long id, Grade grade) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        Grade existing = gradeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("成績が見つかりません: " + id));

        validateGrade(grade);

        // 基本情報の更新
        existing.setScore(grade.getScore());
        existing.setGradeLetter(grade.getGradeLetter());
        existing.setFeedback(grade.getFeedback());
        existing.setAssessmentType(grade.getAssessmentType());
        existing.setMaxScore(grade.getMaxScore());
        existing.setWeightPercentage(grade.getWeightPercentage());
        existing.setGradedAt(grade.getGradedAt());
        existing.setUpdatedAt(LocalDateTime.now());

        return gradeRepository.save(existing);
    }

    /**
     * 成績を削除
     */
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        if (!gradeRepository.existsById(id)) {
            throw new RuntimeException("成績が見つかりません: " + id);
        }

        gradeRepository.deleteById(id);
    }

    /**
     * 学生IDで成績を検索
     */
    @Transactional(readOnly = true)
    public List<Grade> findByStudentId(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("学生IDは必須です");
        }
        return gradeRepository.findByStudentIdOrderByGradedAtDesc(studentId);
    }

    /**
     * プログラムIDで成績を検索
     */
    @Transactional(readOnly = true)
    public List<Grade> findByProgramId(Long programId) {
        if (programId == null) {
            throw new IllegalArgumentException("プログラムIDは必須です");
        }
        return gradeRepository.findByProgramIdOrderByGradedAtDesc(programId);
    }

    /**
     * 学生とプログラムで成績を検索
     */
    @Transactional(readOnly = true)
    public List<Grade> findByStudentIdAndProgramId(Long studentId, Long programId) {
        if (studentId == null || programId == null) {
            throw new IllegalArgumentException("学生IDとプログラムIDは必須です");
        }
        return gradeRepository.findByStudentIdAndProgramIdOrderByGradedAtDesc(studentId, programId);
    }

    /**
     * 評価タイプで検索
     */
    @Transactional(readOnly = true)
    public List<Grade> findByAssessmentType(String assessmentType) {
        if (!StringUtils.hasText(assessmentType)) {
            throw new IllegalArgumentException("評価タイプは必須です");
        }
        return gradeRepository.findByAssessmentTypeOrderByGradedAtDesc(assessmentType);
    }

    /**
     * 成績レターで検索
     */
    @Transactional(readOnly = true)
    public List<Grade> findByGradeLetter(String gradeLetter) {
        if (!StringUtils.hasText(gradeLetter)) {
            throw new IllegalArgumentException("成績レターは必須です");
        }
        return gradeRepository.findByGradeLetterOrderByGradedAtDesc(gradeLetter);
    }

    /**
     * 学生の平均スコアを取得
     */
    @Transactional(readOnly = true)
    public Double getStudentAverageScore(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("学生IDは必須です");
        }
        return gradeRepository.findAverageScoreByStudentId(studentId);
    }

    /**
     * 学生のプログラム別平均スコアを取得
     */
    @Transactional(readOnly = true)
    public Double getStudentProgramAverageScore(Long studentId, Long programId) {
        if (studentId == null || programId == null) {
            throw new IllegalArgumentException("学生IDとプログラムIDは必須です");
        }
        return gradeRepository.findAverageScoreByStudentIdAndProgramId(studentId, programId);
    }

    /**
     * プログラムの平均スコアを取得
     */
    @Transactional(readOnly = true)
    public Double getProgramAverageScore(Long programId) {
        if (programId == null) {
            throw new IllegalArgumentException("プログラムIDは必須です");
        }
        return gradeRepository.findAverageScoreByProgramId(programId);
    }

    /**
     * 評価タイプ別平均スコアを取得
     */
    @Transactional(readOnly = true)
    public Double getAverageScoreByAssessmentType(String assessmentType) {
        if (!StringUtils.hasText(assessmentType)) {
            throw new IllegalArgumentException("評価タイプは必須です");
        }
        return gradeRepository.findAverageScoreByAssessmentType(assessmentType);
    }

    /**
     * 学生の成績統計を取得
     */
    @Transactional(readOnly = true)
    public List<Object[]> getStudentGradeStatistics(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("学生IDは必須です");
        }
        return gradeRepository.findGradeStatisticsByStudentId(studentId);
    }

    /**
     * プログラムの成績統計を取得
     */
    @Transactional(readOnly = true)
    public List<Object[]> getProgramGradeStatistics(Long programId) {
        if (programId == null) {
            throw new IllegalArgumentException("プログラムIDは必須です");
        }
        return gradeRepository.findGradeStatisticsByProgramId(programId);
    }

    /**
     * 成績分布を取得
     */
    @Transactional(readOnly = true)
    public List<Object[]> getGradeDistribution() {
        return gradeRepository.findGradeDistribution();
    }

    /**
     * プログラム別成績分布を取得
     */
    @Transactional(readOnly = true)
    public List<Object[]> getGradeDistributionByProgram(Long programId) {
        if (programId == null) {
            throw new IllegalArgumentException("プログラムIDは必須です");
        }
        return gradeRepository.findGradeDistributionByProgramId(programId);
    }

    /**
     * 最高スコアと最低スコアを取得
     */
    @Transactional(readOnly = true)
    public Object[] getScoreRange(Long programId) {
        if (programId == null) {
            throw new IllegalArgumentException("プログラムIDは必須です");
        }
        return gradeRepository.findScoreRangeByProgramId(programId);
    }

    /**
     * 複合条件での検索
     */
    @Transactional(readOnly = true)
    public Page<Grade> findWithFilters(Long studentId, Long programId, String assessmentType,
                                      String gradeLetter, Double scoreMin, Double scoreMax,
                                      LocalDateTime gradedAfter, LocalDateTime gradedBefore,
                                      Pageable pageable) {

        Specification<Grade> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (studentId != null) {
                predicates.add(criteriaBuilder.equal(root.get("studentId"), studentId));
            }

            if (programId != null) {
                predicates.add(criteriaBuilder.equal(root.get("programId"), programId));
            }

            if (StringUtils.hasText(assessmentType)) {
                predicates.add(criteriaBuilder.equal(root.get("assessmentType"), assessmentType));
            }

            if (StringUtils.hasText(gradeLetter)) {
                predicates.add(criteriaBuilder.equal(root.get("gradeLetter"), gradeLetter));
            }

            if (scoreMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("score"), scoreMin));
            }

            if (scoreMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("score"), scoreMax));
            }

            if (gradedAfter != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("gradedAt"), gradedAfter));
            }

            if (gradedBefore != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("gradedAt"), gradedBefore));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return gradeRepository.findAll(spec, pageable);
    }

    /**
     * 成績レターを自動計算
     */
    public String calculateGradeLetter(Double score, Double maxScore) {
        if (score == null || maxScore == null || maxScore == 0.0) {
            return "F";
        }

        double percentage = (score / maxScore) * 100.0;

        if (percentage >= 90.0) return "A";
        else if (percentage >= 80.0) return "B";
        else if (percentage >= 70.0) return "C";
        else if (percentage >= 60.0) return "D";
        else return "F";
    }

    /**
     * 成績レターを更新
     */
    public Grade updateGradeLetter(Long gradeId) {
        if (gradeId == null) {
            throw new IllegalArgumentException("成績IDは必須です");
        }

        Grade grade = gradeRepository.findById(gradeId)
            .orElseThrow(() -> new RuntimeException("成績が見つかりません: " + gradeId));

        String gradeLetter = calculateGradeLetter(grade.getScore(), grade.getMaxScore());
        grade.setGradeLetter(gradeLetter);
        grade.setUpdatedAt(LocalDateTime.now());

        return gradeRepository.save(grade);
    }

    /**
     * 成績数をカウント
     */
    @Transactional(readOnly = true)
    public long countAll() {
        return gradeRepository.count();
    }

    /**
     * 学生別の成績数をカウント
     */
    @Transactional(readOnly = true)
    public long countByStudent(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("学生IDは必須です");
        }
        return gradeRepository.countByStudentId(studentId);
    }

    /**
     * プログラム別の成績数をカウント
     */
    @Transactional(readOnly = true)
    public long countByProgram(Long programId) {
        if (programId == null) {
            throw new IllegalArgumentException("プログラムIDは必須です");
        }
        return gradeRepository.countByProgramId(programId);
    }

    /**
     * 評価タイプ別の成績数をカウント
     */
    @Transactional(readOnly = true)
    public long countByAssessmentType(String assessmentType) {
        if (!StringUtils.hasText(assessmentType)) {
            throw new IllegalArgumentException("評価タイプは必須です");
        }
        return gradeRepository.countByAssessmentType(assessmentType);
    }

    /**
     * 成績のバリデーション
     */
    private void validateGrade(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("成績は必須です");
        }

        if (grade.getStudentId() == null) {
            throw new IllegalArgumentException("学生IDは必須です");
        }

        if (grade.getProgramId() == null) {
            throw new IllegalArgumentException("プログラムIDは必須です");
        }

        if (grade.getScore() == null) {
            throw new IllegalArgumentException("スコアは必須です");
        }

        if (grade.getMaxScore() == null) {
            throw new IllegalArgumentException("最大スコアは必須です");
        }

        if (!StringUtils.hasText(grade.getAssessmentType())) {
            throw new IllegalArgumentException("評価タイプは必須です");
        }

        // 学生存在チェック
        if (!studentProfileRepository.existsById(grade.getStudentId())) {
            throw new IllegalArgumentException("指定された学生が存在しません");
        }

        // プログラム存在チェック
        if (!trainingProgramRepository.existsById(grade.getProgramId())) {
            throw new IllegalArgumentException("指定されたプログラムが存在しません");
        }

        // スコアの範囲チェック
        if (grade.getScore() < 0.0) {
            throw new IllegalArgumentException("スコアは0以上で入力してください");
        }

        if (grade.getMaxScore() <= 0.0) {
            throw new IllegalArgumentException("最大スコアは正の値で入力してください");
        }

        if (grade.getScore() > grade.getMaxScore()) {
            throw new IllegalArgumentException("スコアは最大スコア以下で入力してください");
        }

        // 重み付けの範囲チェック
        if (grade.getWeightPercentage() != null && 
            (grade.getWeightPercentage() < 0.0 || grade.getWeightPercentage() > 100.0)) {
            throw new IllegalArgumentException("重み付けは0-100の範囲で入力してください");
        }

        // 評価タイプの有効性チェック
        if (!isValidAssessmentType(grade.getAssessmentType())) {
            throw new IllegalArgumentException("無効な評価タイプです: " + grade.getAssessmentType());
        }

        // 成績レターの有効性チェック
        if (StringUtils.hasText(grade.getGradeLetter()) && !isValidGradeLetter(grade.getGradeLetter())) {
            throw new IllegalArgumentException("無効な成績レターです: " + grade.getGradeLetter());
        }
    }

    /**
     * 有効な評価タイプかチェック
     */
    private boolean isValidAssessmentType(String assessmentType) {
        List<String> validTypes = List.of(
            "QUIZ", "TEST", "ASSIGNMENT", "PROJECT", "PRESENTATION", 
            "FINAL_EXAM", "MIDTERM_EXAM", "HOMEWORK", "PARTICIPATION"
        );
        return validTypes.contains(assessmentType);
    }

    /**
     * 有効な成績レターかチェック
     */
    private boolean isValidGradeLetter(String gradeLetter) {
        List<String> validGrades = List.of("A", "B", "C", "D", "F", "A+", "A-", "B+", "B-", "C+", "C-", "D+", "D-");
        return validGrades.contains(gradeLetter);
    }
}
