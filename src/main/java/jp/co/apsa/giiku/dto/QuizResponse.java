package jp.co.apsa.giiku.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * クイズ情報レスポンス用DTOクラス
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class QuizResponse {

    private Long id;
    private String title;
    private String description;
    private Long trainingProgramId;
    private Long chapterId;
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
    /** QuizResponse メソッド */
    public QuizResponse() {}

    // 全フィールドのコンストラクタ
    /** QuizResponse メソッド */
    public QuizResponse(Long id, String title, String description, Long trainingProgramId,
                       Long chapterId, Long studentId, Long instructorId, Long companyId,
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
        this.chapterId = chapterId;
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
    /** isCompleted メソッド */
    public boolean isCompleted() {
        return "COMPLETED".equals(quizStatus) || "SUBMITTED".equals(quizStatus) || "GRADED".equals(quizStatus);
    }
    /** isPassed メソッド */
    public boolean isPassed() {
        return actualScore != null && passingScore != null &&
               actualScore.compareTo(passingScore) >= 0;
    }
    /** isTimeUp メソッド */
    public boolean isTimeUp() {
        return endTime != null && LocalDateTime.now().isAfter(endTime);
    }
    /** canRetry メソッド */
    public boolean canRetry() {
        return attemptLimit == null || currentAttempt < attemptLimit;
    }
    /** getStatusDisplayName メソッド */
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
    /** getId メソッド */
    public Long getId() { return id; }
    /** setId メソッド */
    public void setId(Long id) { this.id = id; }
    /** getTitle メソッド */
    public String getTitle() { return title; }
    /** setTitle メソッド */
    public void setTitle(String title) { this.title = title; }
    /** getDescription メソッド */
    public String getDescription() { return description; }
    /** setDescription メソッド */
    public void setDescription(String description) { this.description = description; }
    /** getTrainingProgramId メソッド */
    public Long getTrainingProgramId() { return trainingProgramId; }
    /** setTrainingProgramId メソッド */
    public void setTrainingProgramId(Long trainingProgramId) { this.trainingProgramId = trainingProgramId; }
    /** getChapterId メソッド */
    public Long getChapterId() { return chapterId; }
    /** setChapterId メソッド */
    public void setChapterId(Long chapterId) { this.chapterId = chapterId; }
    /** getStudentId メソッド */
    public Long getStudentId() { return studentId; }
    /** setStudentId メソッド */
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    /** getInstructorId メソッド */
    public Long getInstructorId() { return instructorId; }
    /** setInstructorId メソッド */
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
    /** getCompanyId メソッド */
    public Long getCompanyId() { return companyId; }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    /** getQuizStatus メソッド */
    public String getQuizStatus() { return quizStatus; }
    /** setQuizStatus メソッド */
    public void setQuizStatus(String quizStatus) { this.quizStatus = quizStatus; }
    /** getTimeLimit メソッド */
    public BigDecimal getTimeLimit() { return timeLimit; }
    /** setTimeLimit メソッド */
    public void setTimeLimit(BigDecimal timeLimit) { this.timeLimit = timeLimit; }
    /** getMaxScore メソッド */
    public BigDecimal getMaxScore() { return maxScore; }
    /** setMaxScore メソッド */
    public void setMaxScore(BigDecimal maxScore) { this.maxScore = maxScore; }
    /** getPassingScore メソッド */
    public BigDecimal getPassingScore() { return passingScore; }
    /** setPassingScore メソッド */
    public void setPassingScore(BigDecimal passingScore) { this.passingScore = passingScore; }
    /** getAttemptLimit メソッド */
    public Integer getAttemptLimit() { return attemptLimit; }
    /** setAttemptLimit メソッド */
    public void setAttemptLimit(Integer attemptLimit) { this.attemptLimit = attemptLimit; }
    /** getCurrentAttempt メソッド */
    public Integer getCurrentAttempt() { return currentAttempt; }
    /** setCurrentAttempt メソッド */
    public void setCurrentAttempt(Integer currentAttempt) { this.currentAttempt = currentAttempt; }
    /** getStartTime メソッド */
    public LocalDateTime getStartTime() { return startTime; }
    /** setStartTime メソッド */
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    /** getEndTime メソッド */
    public LocalDateTime getEndTime() { return endTime; }
    /** setEndTime メソッド */
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    /** getSubmissionTime メソッド */
    public LocalDateTime getSubmissionTime() { return submissionTime; }
    /** setSubmissionTime メソッド */
    public void setSubmissionTime(LocalDateTime submissionTime) { this.submissionTime = submissionTime; }
    /** getActualScore メソッド */
    public BigDecimal getActualScore() { return actualScore; }
    /** setActualScore メソッド */
    public void setActualScore(BigDecimal actualScore) { this.actualScore = actualScore; }
    /** getFeedbackFromInstructor メソッド */
    public String getFeedbackFromInstructor() { return feedbackFromInstructor; }
    /** setFeedbackFromInstructor メソッド */
    public void setFeedbackFromInstructor(String feedbackFromInstructor) { this.feedbackFromInstructor = feedbackFromInstructor; }
    /** getIsRandomized メソッド */
    public Boolean getIsRandomized() { return isRandomized; }
    /** setIsRandomized メソッド */
    public void setIsRandomized(Boolean isRandomized) { this.isRandomized = isRandomized; }
    /** getShowAnswersAfterSubmission メソッド */
    public Boolean getShowAnswersAfterSubmission() { return showAnswersAfterSubmission; }
    /** setShowAnswersAfterSubmission メソッド */
    public void setShowAnswersAfterSubmission(Boolean showAnswersAfterSubmission) { this.showAnswersAfterSubmission = showAnswersAfterSubmission; }
    /** getNotes メソッド */
    public String getNotes() { return notes; }
    /** setNotes メソッド */
    public void setNotes(String notes) { this.notes = notes; }
    /** getCreatedAt メソッド */
    public LocalDateTime getCreatedAt() { return createdAt; }
    /** setCreatedAt メソッド */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    /** getUpdatedAt メソッド */
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    /** setUpdatedAt メソッド */
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    /** getVersion メソッド */
    public Long getVersion() { return version; }
    /** setVersion メソッド */
    public void setVersion(Long version) { this.version = version; }

    /** toString メソッド */
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
