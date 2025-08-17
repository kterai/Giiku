package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.Grade;
import jp.co.apsa.giiku.domain.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Gradeサービスクラス
 * 成績管理機能を提供
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    /** 全ての成績を取得 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    /** ページング対応全件取得 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Page<Grade> findAll(Pageable pageable) {
        return gradeRepository.findAll(pageable);
    }

    /** IDで成績を取得 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Optional<Grade> findById(Long id) {
        return gradeRepository.findById(id);
    }

    /** 成績を保存 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public Grade save(Grade grade) {
        if (grade.getId() == null) {
            grade.setCreatedAt(LocalDateTime.now());
        }
        grade.setUpdatedAt(LocalDateTime.now());
        return gradeRepository.save(grade);
    }

    /** 成績を更新 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public Grade update(Long id, Grade grade) {
        Grade existing = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("成績が見つかりません: " + id));

        existing.setRawScore(grade.getRawScore());
        existing.setLetterGrade(grade.getLetterGrade());
        existing.setFeedback(grade.getFeedback());
        existing.setAssessmentType(grade.getAssessmentType());
        existing.setMaxScore(grade.getMaxScore());
        existing.setWeight(grade.getWeight());
        existing.setGradedAt(grade.getGradedAt());
        existing.setUpdatedAt(LocalDateTime.now());
        return gradeRepository.save(existing);
    }

    /** 成績を削除 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void delete(Long id) {
        gradeRepository.deleteById(id);
    }

    /** 学生IDで成績を検索 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Grade> findByStudentId(Long studentId) {
        return gradeRepository.findAll().stream()
                .filter(g -> studentId.equals(g.getStudentId()))
                .sorted((a, b) -> b.getGradedAt().compareTo(a.getGradedAt()))
                .collect(Collectors.toList());
    }

    /** プログラムIDで成績を検索 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Grade> findByProgramId(Long programId) {
        return gradeRepository.findAll().stream()
                .filter(g -> programId.equals(g.getTrainingProgramId()))
                .sorted((a, b) -> b.getGradedAt().compareTo(a.getGradedAt()))
                .collect(Collectors.toList());
    }

    /** 学生とプログラムで成績を検索 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Grade> findByStudentIdAndProgramId(Long studentId, Long programId) {
        return gradeRepository.findAll().stream()
                .filter(g -> studentId.equals(g.getStudentId()) && programId.equals(g.getTrainingProgramId()))
                .sorted((a, b) -> b.getGradedAt().compareTo(a.getGradedAt()))
                .collect(Collectors.toList());
    }

    /** 評価タイプで検索 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Grade> findByAssessmentType(String assessmentType) {
        return gradeRepository.findAll().stream()
                .filter(g -> assessmentType.equals(g.getAssessmentType()))
                .sorted((a, b) -> b.getGradedAt().compareTo(a.getGradedAt()))
                .collect(Collectors.toList());
    }

    /** 成績レターで検索 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Grade> findByGradeLetter(String gradeLetter) {
        return gradeRepository.findAll().stream()
                .filter(g -> gradeLetter.equals(g.getLetterGrade()))
                .sorted((a, b) -> b.getGradedAt().compareTo(a.getGradedAt()))
                .collect(Collectors.toList());
    }

    /** 教員IDで検索 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Grade> findByInstructorId(Long instructorId) {
        return gradeRepository.findAll().stream()
                .filter(g -> instructorId.equals(g.getInstructorId()))
                .collect(Collectors.toList());
    }

    /** 成績ステータスで検索 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Grade> findByGradeStatus(String gradeStatus) {
        return findByGradeLetter(gradeStatus);
    }

    /** 期間で検索 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Grade> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return gradeRepository.findAll().stream()
                .filter(g -> g.getGradedAt() != null &&
                        (start == null || !g.getGradedAt().isBefore(start)) &&
                        (end == null || !g.getGradedAt().isAfter(end)))
                .collect(Collectors.toList());
    }

    /** 学生GPA計算 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public BigDecimal calculateStudentGPA(Long studentId) {
        return BigDecimal.ZERO;
    }

    /** 加重GPA計算 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public BigDecimal calculateWeightedStudentGPA(Long studentId) {
        return BigDecimal.ZERO;
    }

    /** 企業平均GPA計算 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public BigDecimal calculateCompanyAverageGPA(Long companyId) {
        return BigDecimal.ZERO;
    }

    /** 評価タイプ統計 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAssessmentTypeStatistics(Long companyId) {
        return new ArrayList<>();
    }

    /** 成績分布 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getGradeDistribution(Long companyId) {
        return new ArrayList<>();
    }

    /** 学生成績統計 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getStudentGradeStatistics(Long companyId) {
        return new ArrayList<>();
    }

    /** 教員採点統計 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getInstructorGradingStatistics(Long companyId) {
        return new ArrayList<>();
    }

    /** 成績上位学生取得 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getTopPerformingStudents(Long companyId, Integer limit) {
        return new ArrayList<>();
    }

    /** 成績不振学生取得 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getStudentsAtRisk(Long companyId, BigDecimal threshold) {
        return new ArrayList<>();
    }

    /** 成績トレンド分析 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getGradeTrendAnalysis(Long companyId, LocalDateTime start, LocalDateTime end) {
        return new ArrayList<>();
    }

    /** 未採点成績取得 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Grade> findPendingGrades(Long companyId) {
        return new ArrayList<>();
    }

    /** 教員別未採点成績取得 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<Grade> findPendingGradesByInstructor(Long instructorId) {
        return new ArrayList<>();
    }

    /** 学生平均スコア 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Double getStudentAverageScore(Long studentId) {
        return gradeRepository.findAll().stream()
                .filter(g -> studentId.equals(g.getStudentId()) && g.getRawScore() != null)
                .mapToDouble(g -> g.getRawScore().doubleValue())
                .average().orElse(0.0);
    }

    /** 学生プログラム平均スコア 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Double getStudentProgramAverageScore(Long studentId, Long programId) {
        return gradeRepository.findAll().stream()
                .filter(g -> studentId.equals(g.getStudentId()) && programId.equals(g.getTrainingProgramId()) && g.getRawScore() != null)
                .mapToDouble(g -> g.getRawScore().doubleValue())
                .average().orElse(0.0);
    }

    /** プログラム平均スコア 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Double getProgramAverageScore(Long programId) {
        return gradeRepository.findAll().stream()
                .filter(g -> programId.equals(g.getTrainingProgramId()) && g.getRawScore() != null)
                .mapToDouble(g -> g.getRawScore().doubleValue())
                .average().orElse(0.0);
    }

    /** 評価タイプ平均スコア 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Double getAverageScoreByAssessmentType(String assessmentType) {
        return gradeRepository.findAll().stream()
                .filter(g -> assessmentType.equals(g.getAssessmentType()) && g.getRawScore() != null)
                .mapToDouble(g -> g.getRawScore().doubleValue())
                .average().orElse(0.0);
    }

    /** 成績レターを自動計算 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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

    /** 成績レターを更新 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public Grade updateGradeLetter(Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new RuntimeException("成績が見つかりません: " + gradeId));
        String gradeLetter = calculateGradeLetter(
                grade.getRawScore() != null ? grade.getRawScore().doubleValue() : null,
                grade.getMaxScore() != null ? grade.getMaxScore().doubleValue() : null);
        grade.setLetterGrade(gradeLetter);
        grade.setUpdatedAt(LocalDateTime.now());
        return gradeRepository.save(grade);
    }

    /** 全件カウント 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public long countAll() {
        return gradeRepository.count();
    }

    /** 学生別件数 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public long countByStudent(Long studentId) {
        return gradeRepository.findAll().stream().filter(g -> studentId.equals(g.getStudentId())).count();
    }

    /** プログラム別件数 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public long countByProgram(Long programId) {
        return gradeRepository.findAll().stream().filter(g -> programId.equals(g.getTrainingProgramId())).count();
    }

    /** 評価タイプ別件数 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public long countByAssessmentType(String assessmentType) {
        return gradeRepository.findAll().stream().filter(g -> assessmentType.equals(g.getAssessmentType())).count();
    }
}

