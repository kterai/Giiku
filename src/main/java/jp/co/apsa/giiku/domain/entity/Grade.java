package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 成績エンティティ
 * LMS機能における成績評価管理を行う
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "grade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "training_program_id")
    private Long trainingProgramId;

    @Column(name = "lecture_id")
    private Long lectureId;

    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "mock_test_id")
    private Long mockTestId;

    @Column(name = "instructor_id")
    private Long instructorId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "assessment_type", nullable = false, length = 50)
    private String assessmentType; // QUIZ, MOCK_TEST, ASSIGNMENT, FINAL_EXAM, PARTICIPATION, ATTENDANCE

    @Column(name = "assessment_title", length = 200)
    private String assessmentTitle;

    @Column(name = "raw_score", precision = 10, scale = 2)
    private BigDecimal rawScore;

    @Column(name = "max_score", precision = 10, scale = 2)
    private BigDecimal maxScore;

    @Column(name = "percentage_score", precision = 5, scale = 2)
    private BigDecimal percentageScore;

    @Column(name = "letter_grade", length = 5)
    private String letterGrade; // A+, A, A-, B+, B, B-, C+, C, C-, D, F

    @Column(name = "grade_points", precision = 3, scale = 2)
    private BigDecimal gradePoints; // GPA用のポイント (4.0, 3.7, 3.3, etc.)

    @Column(name = "weight", precision = 5, scale = 2)
    private BigDecimal weight = BigDecimal.valueOf(1.0); // 重み付け

    @Column(name = "is_passed")
    private Boolean isPassed = false;

    @Column(name = "passing_score", precision = 5, scale = 2)
    private BigDecimal passingScore = BigDecimal.valueOf(70.0);

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "graded_by")
    private Long gradedBy; // 採点者のユーザーID

    @Column(name = "graded_at")
    private LocalDateTime gradedAt;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "is_late_submission")
    private Boolean isLateSubmission = false;

    @Column(name = "attempt_number")
    private Integer attemptNumber = 1;

    @Column(name = "max_attempts")
    private Integer maxAttempts = 1;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // デフォルトコンストラクタ
    /** Grade メソッド */
    public Grade() {}

    // Getter and Setter methods
    /** getId メソッド */
    public Long getId() {
        return id;
    }
    /** setId メソッド */
    public void setId(Long id) {
        this.id = id;
    }
    /** getStudentId メソッド */
    public Long getStudentId() {
        return studentId;
    }
    /** setStudentId メソッド */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    /** getTrainingProgramId メソッド */
    public Long getTrainingProgramId() {
        return trainingProgramId;
    }
    /** setTrainingProgramId メソッド */
    public void setTrainingProgramId(Long trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
    }
    /** getLectureId メソッド */
    public Long getLectureId() {
        return lectureId;
    }
    /** setLectureId メソッド */
    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }
    /** getQuizId メソッド */
    public Long getQuizId() {
        return quizId;
    }
    /** setQuizId メソッド */
    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
    /** getMockTestId メソッド */
    public Long getMockTestId() {
        return mockTestId;
    }
    /** setMockTestId メソッド */
    public void setMockTestId(Long mockTestId) {
        this.mockTestId = mockTestId;
    }
    /** getInstructorId メソッド */
    public Long getInstructorId() {
        return instructorId;
    }
    /** setInstructorId メソッド */
    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
    /** getCompanyId メソッド */
    public Long getCompanyId() {
        return companyId;
    }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    /** getAssessmentType メソッド */
    public String getAssessmentType() {
        return assessmentType;
    }
    /** setAssessmentType メソッド */
    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }
    /** getAssessmentTitle メソッド */
    public String getAssessmentTitle() {
        return assessmentTitle;
    }
    /** setAssessmentTitle メソッド */
    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }
    /** getRawScore メソッド */
    public BigDecimal getRawScore() {
        return rawScore;
    }
    /** setRawScore メソッド */
    public void setRawScore(BigDecimal rawScore) {
        this.rawScore = rawScore;
    }
    /** getMaxScore メソッド */
    public BigDecimal getMaxScore() {
        return maxScore;
    }
    /** setMaxScore メソッド */
    public void setMaxScore(BigDecimal maxScore) {
        this.maxScore = maxScore;
    }
    /** getPercentageScore メソッド */
    public BigDecimal getPercentageScore() {
        return percentageScore;
    }
    /** setPercentageScore メソッド */
    public void setPercentageScore(BigDecimal percentageScore) {
        this.percentageScore = percentageScore;
    }
    /** getLetterGrade メソッド */
    public String getLetterGrade() {
        return letterGrade;
    }
    /** setLetterGrade メソッド */
    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }
    /** getGradePoints メソッド */
    public BigDecimal getGradePoints() {
        return gradePoints;
    }
    /** setGradePoints メソッド */
    public void setGradePoints(BigDecimal gradePoints) {
        this.gradePoints = gradePoints;
    }
    /** getWeight メソッド */
    public BigDecimal getWeight() {
        return weight;
    }
    /** setWeight メソッド */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    /** getIsPassed メソッド */
    public Boolean getIsPassed() {
        return isPassed;
    }
    /** setIsPassed メソッド */
    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }
    /** getPassingScore メソッド */
    public BigDecimal getPassingScore() {
        return passingScore;
    }
    /** setPassingScore メソッド */
    public void setPassingScore(BigDecimal passingScore) {
        this.passingScore = passingScore;
    }
    /** getComments メソッド */
    public String getComments() {
        return comments;
    }
    /** setComments メソッド */
    public void setComments(String comments) {
        this.comments = comments;
    }
    /** getFeedback メソッド */
    public String getFeedback() {
        return feedback;
    }
    /** setFeedback メソッド */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    /** getGradedBy メソッド */
    public Long getGradedBy() {
        return gradedBy;
    }
    /** setGradedBy メソッド */
    public void setGradedBy(Long gradedBy) {
        this.gradedBy = gradedBy;
    }
    /** getGradedAt メソッド */
    public LocalDateTime getGradedAt() {
        return gradedAt;
    }
    /** setGradedAt メソッド */
    public void setGradedAt(LocalDateTime gradedAt) {
        this.gradedAt = gradedAt;
    }
    /** getSubmittedAt メソッド */
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    /** setSubmittedAt メソッド */
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
    /** getDueDate メソッド */
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    /** setDueDate メソッド */
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    /** getIsLateSubmission メソッド */
    public Boolean getIsLateSubmission() {
        return isLateSubmission;
    }
    /** setIsLateSubmission メソッド */
    public void setIsLateSubmission(Boolean isLateSubmission) {
        this.isLateSubmission = isLateSubmission;
    }
    /** getAttemptNumber メソッド */
    public Integer getAttemptNumber() {
        return attemptNumber;
    }
    /** setAttemptNumber メソッド */
    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }
    /** getMaxAttempts メソッド */
    public Integer getMaxAttempts() {
        return maxAttempts;
    }
    /** setMaxAttempts メソッド */
    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
    /** getIsActive メソッド */
    public Boolean getIsActive() {
        return isActive;
    }
    /** setIsActive メソッド */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    /** getCreatedAt メソッド */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    /** setCreatedAt メソッド */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    /** getUpdatedAt メソッド */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    /** setUpdatedAt メソッド */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (isActive == null) {
            isActive = true;
        }
        if (isPassed == null) {
            isPassed = false;
        }
        if (isLateSubmission == null) {
            isLateSubmission = false;
        }
        if (attemptNumber == null) {
            attemptNumber = 1;
        }
        if (maxAttempts == null) {
            maxAttempts = 1;
        }
        if (weight == null) {
            weight = BigDecimal.valueOf(1.0);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /** toString メソッド */
    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", assessmentType='" + assessmentType + "'" +
                ", assessmentTitle='" + assessmentTitle + "'" +
                ", rawScore=" + rawScore +
                ", maxScore=" + maxScore +
                ", percentageScore=" + percentageScore +
                ", letterGrade='" + letterGrade + "'" +
                ", isPassed=" + isPassed +
                '}';
    }
}
