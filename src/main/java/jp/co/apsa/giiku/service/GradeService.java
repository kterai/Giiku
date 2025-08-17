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
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Transactional(readOnly = true)
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Grade> findAll(Pageable pageable) {
        return gradeRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Grade> findById(Long id) {
        return gradeRepository.findById(id);
    }

    public Grade save(Grade grade) {
        if (grade.getId() == null) {
            grade.setCreatedAt(LocalDateTime.now());
        }
        grade.setUpdatedAt(LocalDateTime.now());
        return gradeRepository.save(grade);
    }

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

    public void delete(Long id) {
        gradeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Grade> findByStudentId(Long studentId) {
        return gradeRepository.findAll().stream()
                .filter(g -> studentId.equals(g.getStudentId()))
                .sorted((a, b) -> b.getGradedAt().compareTo(a.getGradedAt()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Grade> findByProgramId(Long programId) {
        return gradeRepository.findAll().stream()
                .filter(g -> programId.equals(g.getTrainingProgramId()))
                .sorted((a, b) -> b.getGradedAt().compareTo(a.getGradedAt()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Grade> findByStudentIdAndProgramId(Long studentId, Long programId) {
        return gradeRepository.findAll().stream()
                .filter(g -> studentId.equals(g.getStudentId()) && programId.equals(g.getTrainingProgramId()))
                .sorted((a, b) -> b.getGradedAt().compareTo(a.getGradedAt()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Grade> findByAssessmentType(String assessmentType) {
        return gradeRepository.findAll().stream()
                .filter(g -> assessmentType.equals(g.getAssessmentType()))
                .sorted((a, b) -> b.getGradedAt().compareTo(a.getGradedAt()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Grade> findByGradeLetter(String gradeLetter) {
        return gradeRepository.findAll().stream()
                .filter(g -> gradeLetter.equals(g.getLetterGrade()))
                .sorted((a, b) -> b.getGradedAt().compareTo(a.getGradedAt()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Grade> findByInstructorId(Long instructorId) {
        return gradeRepository.findAll().stream()
                .filter(g -> instructorId.equals(g.getInstructorId()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Grade> findByGradeStatus(String gradeStatus) {
        return findByGradeLetter(gradeStatus);
    }

    @Transactional(readOnly = true)
    public List<Grade> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return gradeRepository.findAll().stream()
                .filter(g -> g.getGradedAt() != null &&
                        (start == null || !g.getGradedAt().isBefore(start)) &&
                        (end == null || !g.getGradedAt().isAfter(end)))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateStudentGPA(Long studentId) {
        return BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateWeightedStudentGPA(Long studentId) {
        return BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateCompanyAverageGPA(Long companyId) {
        return BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAssessmentTypeStatistics(Long companyId) {
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getGradeDistribution(Long companyId) {
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getStudentGradeStatistics(Long companyId) {
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getInstructorGradingStatistics(Long companyId) {
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getTopPerformingStudents(Long companyId, Integer limit) {
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getStudentsAtRisk(Long companyId, BigDecimal threshold) {
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getGradeTrendAnalysis(Long companyId, LocalDateTime start, LocalDateTime end) {
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Grade> findPendingGrades(Long companyId) {
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<Grade> findPendingGradesByInstructor(Long instructorId) {
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public Double getStudentAverageScore(Long studentId) {
        return gradeRepository.findAll().stream()
                .filter(g -> studentId.equals(g.getStudentId()) && g.getRawScore() != null)
                .mapToDouble(g -> g.getRawScore().doubleValue())
                .average().orElse(0.0);
    }

    @Transactional(readOnly = true)
    public Double getStudentProgramAverageScore(Long studentId, Long programId) {
        return gradeRepository.findAll().stream()
                .filter(g -> studentId.equals(g.getStudentId()) && programId.equals(g.getTrainingProgramId()) && g.getRawScore() != null)
                .mapToDouble(g -> g.getRawScore().doubleValue())
                .average().orElse(0.0);
    }

    @Transactional(readOnly = true)
    public Double getProgramAverageScore(Long programId) {
        return gradeRepository.findAll().stream()
                .filter(g -> programId.equals(g.getTrainingProgramId()) && g.getRawScore() != null)
                .mapToDouble(g -> g.getRawScore().doubleValue())
                .average().orElse(0.0);
    }

    @Transactional(readOnly = true)
    public Double getAverageScoreByAssessmentType(String assessmentType) {
        return gradeRepository.findAll().stream()
                .filter(g -> assessmentType.equals(g.getAssessmentType()) && g.getRawScore() != null)
                .mapToDouble(g -> g.getRawScore().doubleValue())
                .average().orElse(0.0);
    }

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

    @Transactional(readOnly = true)
    public long countAll() {
        return gradeRepository.count();
    }

    @Transactional(readOnly = true)
    public long countByStudent(Long studentId) {
        return gradeRepository.findAll().stream().filter(g -> studentId.equals(g.getStudentId())).count();
    }

    @Transactional(readOnly = true)
    public long countByProgram(Long programId) {
        return gradeRepository.findAll().stream().filter(g -> programId.equals(g.getTrainingProgramId())).count();
    }

    @Transactional(readOnly = true)
    public long countByAssessmentType(String assessmentType) {
        return gradeRepository.findAll().stream().filter(g -> assessmentType.equals(g.getAssessmentType())).count();
    }
}

