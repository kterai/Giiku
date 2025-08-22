package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 学生成績サマリーエンティティ
 * student_grade_summariesテーブルと対応する
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "student_grade_summaries")
public class StudentGradeSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @Column(name = "exercise_score")
    private BigDecimal exerciseScore;

    @Column(name = "quiz_score")
    private BigDecimal quizScore;

    @Column(name = "mock_test_score")
    private BigDecimal mockTestScore;

    @Column(name = "total_score")
    private BigDecimal totalScore;

    @Column(name = "grade_status")
    private String gradeStatus;

    @Column(name = "calculated_at")
    private LocalDateTime calculatedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** デフォルトコンストラクタ */
    public StudentGradeSummary() {
    }

    /** @return id */
    public Long getId() {
        return id;
    }

    /** @param id 設定するID */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return studentId */
    public Long getStudentId() {
        return studentId;
    }

    /** @param studentId 学生ID */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    /** @return lectureId */
    public Long getLectureId() {
        return lectureId;
    }

    /** @param lectureId 講義ID */
    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    /** @return exerciseScore */
    public BigDecimal getExerciseScore() {
        return exerciseScore;
    }

    /** @param exerciseScore 演習得点 */
    public void setExerciseScore(BigDecimal exerciseScore) {
        this.exerciseScore = exerciseScore;
    }

    /** @return quizScore */
    public BigDecimal getQuizScore() {
        return quizScore;
    }

    /** @param quizScore クイズ得点 */
    public void setQuizScore(BigDecimal quizScore) {
        this.quizScore = quizScore;
    }

    /** @return mockTestScore */
    public BigDecimal getMockTestScore() {
        return mockTestScore;
    }

    /** @param mockTestScore 模擬試験得点 */
    public void setMockTestScore(BigDecimal mockTestScore) {
        this.mockTestScore = mockTestScore;
    }

    /** @return totalScore */
    public BigDecimal getTotalScore() {
        return totalScore;
    }

    /** @param totalScore 総合得点 */
    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    /** @return gradeStatus */
    public String getGradeStatus() {
        return gradeStatus;
    }

    /** @param gradeStatus 成績評価ステータス */
    public void setGradeStatus(String gradeStatus) {
        this.gradeStatus = gradeStatus;
    }

    /** @return calculatedAt */
    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }

    /** @param calculatedAt 集計日時 */
    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
    }

    /** @return createdBy */
    public Long getCreatedBy() {
        return createdBy;
    }

    /** @param createdBy 作成者ID */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /** @return createdAt */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** @param createdAt 作成日時 */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /** @return updatedBy */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /** @param updatedBy 更新者ID */
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /** @return updatedAt */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /** @param updatedAt 更新日時 */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

