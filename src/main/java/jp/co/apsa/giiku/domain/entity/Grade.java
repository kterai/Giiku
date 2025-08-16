package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 成績エンティティ
 * LMS機能における成績評価管理を行う
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
    public Grade() {}

    // Getter and Setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getTrainingProgramId() {
        return trainingProgramId;
    }

    public void setTrainingProgramId(Long trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getMockTestId() {
        return mockTestId;
    }

    public void setMockTestId(Long mockTestId) {
        this.mockTestId = mockTestId;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public BigDecimal getRawScore() {
        return rawScore;
    }

    public void setRawScore(BigDecimal rawScore) {
        this.rawScore = rawScore;
    }

    public BigDecimal getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(BigDecimal maxScore) {
        this.maxScore = maxScore;
    }

    public BigDecimal getPercentageScore() {
        return percentageScore;
    }

    public void setPercentageScore(BigDecimal percentageScore) {
        this.percentageScore = percentageScore;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public BigDecimal getGradePoints() {
        return gradePoints;
    }

    public void setGradePoints(BigDecimal gradePoints) {
        this.gradePoints = gradePoints;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Boolean getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }

    public BigDecimal getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(BigDecimal passingScore) {
        this.passingScore = passingScore;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Long getGradedBy() {
        return gradedBy;
    }

    public void setGradedBy(Long gradedBy) {
        this.gradedBy = gradedBy;
    }

    public LocalDateTime getGradedAt() {
        return gradedAt;
    }

    public void setGradedAt(LocalDateTime gradedAt) {
        this.gradedAt = gradedAt;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getIsLateSubmission() {
        return isLateSubmission;
    }

    public void setIsLateSubmission(Boolean isLateSubmission) {
        this.isLateSubmission = isLateSubmission;
    }

    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

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
