package jp.co.apsa.giiku.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * クイズ情報レスポンス用DTOクラス
 */
public class QuizResponse {

    private Long id;
    private String title;
    private String description;
    private Long trainingProgramId;
    private Long lectureId;
    private Long studentId;
    private Long instructorId;
    private Long companyId;
    private String quizStatus;
    private BigDecimal timeLimit;
    private BigDecimal maxScore;
    private BigDecimal passingScore;
    private Integer attemptLimit;
    private Integer currentAttempt;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime submissionTime;
    private BigDecimal actualScore;
    private String feedbackFromInstructor;
    private Boolean isRandomized;
    private Boolean showAnswersAfterSubmission;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;

    // デフォルトコンストラクタ
    public QuizResponse() {}

    // 全フィールドのコンストラクタ
    public QuizResponse(Long id, String title, String description, Long trainingProgramId,
                       Long lectureId, Long studentId, Long instructorId, Long companyId,
                       String quizStatus, BigDecimal timeLimit, BigDecimal maxScore,
                       BigDecimal passingScore, Integer attemptLimit, Integer currentAttempt,
                       LocalDateTime startTime, LocalDateTime endTime, LocalDateTime submissionTime,
                       BigDecimal actualScore, String feedbackFromInstructor, Boolean isRandomized,
                       Boolean showAnswersAfterSubmission, String notes, LocalDateTime createdAt,
                       LocalDateTime updatedAt, Long version) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.trainingProgramId = trainingProgramId;
        this.lectureId = lectureId;
        this.studentId = studentId;
        this.instructorId = instructorId;
        this.companyId = companyId;
        this.quizStatus = quizStatus;
        this.timeLimit = timeLimit;
        this.maxScore = maxScore;
        this.passingScore = passingScore;
        this.attemptLimit = attemptLimit;
        this.currentAttempt = currentAttempt;
        this.startTime = startTime;
        this.endTime = endTime;
        this.submissionTime = submissionTime;
        this.actualScore = actualScore;
        this.feedbackFromInstructor = feedbackFromInstructor;
        this.isRandomized = isRandomized;
        this.showAnswersAfterSubmission = showAnswersAfterSubmission;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    // ユーティリティメソッド
    public boolean isCompleted() {
        return "COMPLETED".equals(quizStatus) || "SUBMITTED".equals(quizStatus) || "GRADED".equals(quizStatus);
    }

    public boolean isPassed() {
        return actualScore != null && passingScore != null &&
               actualScore.compareTo(passingScore) >= 0;
    }

    public boolean isTimeUp() {
        return endTime != null && LocalDateTime.now().isAfter(endTime);
    }

    public boolean canRetry() {
        return attemptLimit == null || currentAttempt < attemptLimit;
    }

    public String getStatusDisplayName() {
        switch (quizStatus != null ? quizStatus : "UNKNOWN") {
            case "NOT_STARTED": return "未開始";
            case "IN_PROGRESS": return "進行中";
            case "COMPLETED": return "完了";
            case "SUBMITTED": return "提出済み";
            case "GRADED": return "採点済み";
            default: return "不明";
        }
    }

    // Getter and Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getTrainingProgramId() { return trainingProgramId; }
    public void setTrainingProgramId(Long trainingProgramId) { this.trainingProgramId = trainingProgramId; }

    public Long getLectureId() { return lectureId; }
    public void setLectureId(Long lectureId) { this.lectureId = lectureId; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getInstructorId() { return instructorId; }
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getQuizStatus() { return quizStatus; }
    public void setQuizStatus(String quizStatus) { this.quizStatus = quizStatus; }

    public BigDecimal getTimeLimit() { return timeLimit; }
    public void setTimeLimit(BigDecimal timeLimit) { this.timeLimit = timeLimit; }

    public BigDecimal getMaxScore() { return maxScore; }
    public void setMaxScore(BigDecimal maxScore) { this.maxScore = maxScore; }

    public BigDecimal getPassingScore() { return passingScore; }
    public void setPassingScore(BigDecimal passingScore) { this.passingScore = passingScore; }

    public Integer getAttemptLimit() { return attemptLimit; }
    public void setAttemptLimit(Integer attemptLimit) { this.attemptLimit = attemptLimit; }

    public Integer getCurrentAttempt() { return currentAttempt; }
    public void setCurrentAttempt(Integer currentAttempt) { this.currentAttempt = currentAttempt; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public LocalDateTime getSubmissionTime() { return submissionTime; }
    public void setSubmissionTime(LocalDateTime submissionTime) { this.submissionTime = submissionTime; }

    public BigDecimal getActualScore() { return actualScore; }
    public void setActualScore(BigDecimal actualScore) { this.actualScore = actualScore; }

    public String getFeedbackFromInstructor() { return feedbackFromInstructor; }
    public void setFeedbackFromInstructor(String feedbackFromInstructor) { this.feedbackFromInstructor = feedbackFromInstructor; }

    public Boolean getIsRandomized() { return isRandomized; }
    public void setIsRandomized(Boolean isRandomized) { this.isRandomized = isRandomized; }

    public Boolean getShowAnswersAfterSubmission() { return showAnswersAfterSubmission; }
    public void setShowAnswersAfterSubmission(Boolean showAnswersAfterSubmission) { this.showAnswersAfterSubmission = showAnswersAfterSubmission; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    @Override
    public String toString() {
        return "QuizResponse{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", quizStatus='" + quizStatus + "'" +
                ", studentId=" + studentId +
                ", instructorId=" + instructorId +
                ", maxScore=" + maxScore +
                ", actualScore=" + actualScore +
                ", currentAttempt=" + currentAttempt +
                "}";
    }
}
