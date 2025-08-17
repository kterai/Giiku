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
    /**
     * QuizResponse メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public QuizResponse() {}

    // 全フィールドのコンストラクタ
    /**
     * QuizResponse メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
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
    /**
     * isCompleted メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public boolean isCompleted() {
        return "COMPLETED".equals(quizStatus) || "SUBMITTED".equals(quizStatus) || "GRADED".equals(quizStatus);
    }
    /**
     * isPassed メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public boolean isPassed() {
        return actualScore != null && passingScore != null &&
               actualScore.compareTo(passingScore) >= 0;
    }
    /**
     * isTimeUp メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public boolean isTimeUp() {
        return endTime != null && LocalDateTime.now().isAfter(endTime);
    }
    /**
     * canRetry メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public boolean canRetry() {
        return attemptLimit == null || currentAttempt < attemptLimit;
    }
    /**
     * getStatusDisplayName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

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
    /**
     * getId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public Long getId() { return id; }
    /**
     * setId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setId(Long id) { this.id = id; }
    /**
     * getTitle メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getTitle() { return title; }
    /**
     * setTitle メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTitle(String title) { this.title = title; }
    /**
     * getDescription メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getDescription() { return description; }
    /**
     * setDescription メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setDescription(String description) { this.description = description; }
    /**
     * getTrainingProgramId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getTrainingProgramId() { return trainingProgramId; }
    /**
     * setTrainingProgramId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTrainingProgramId(Long trainingProgramId) { this.trainingProgramId = trainingProgramId; }
    /**
     * getLectureId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getLectureId() { return lectureId; }
    /**
     * setLectureId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setLectureId(Long lectureId) { this.lectureId = lectureId; }
    /**
     * getStudentId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getStudentId() { return studentId; }
    /**
     * setStudentId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    /**
     * getInstructorId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getInstructorId() { return instructorId; }
    /**
     * setInstructorId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
    /**
     * getCompanyId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getCompanyId() { return companyId; }
    /**
     * setCompanyId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    /**
     * getQuizStatus メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getQuizStatus() { return quizStatus; }
    /**
     * setQuizStatus メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setQuizStatus(String quizStatus) { this.quizStatus = quizStatus; }
    /**
     * getTimeLimit メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getTimeLimit() { return timeLimit; }
    /**
     * setTimeLimit メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTimeLimit(BigDecimal timeLimit) { this.timeLimit = timeLimit; }
    /**
     * getMaxScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getMaxScore() { return maxScore; }
    /**
     * setMaxScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setMaxScore(BigDecimal maxScore) { this.maxScore = maxScore; }
    /**
     * getPassingScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getPassingScore() { return passingScore; }
    /**
     * setPassingScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setPassingScore(BigDecimal passingScore) { this.passingScore = passingScore; }
    /**
     * getAttemptLimit メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getAttemptLimit() { return attemptLimit; }
    /**
     * setAttemptLimit メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setAttemptLimit(Integer attemptLimit) { this.attemptLimit = attemptLimit; }
    /**
     * getCurrentAttempt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getCurrentAttempt() { return currentAttempt; }
    /**
     * setCurrentAttempt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCurrentAttempt(Integer currentAttempt) { this.currentAttempt = currentAttempt; }
    /**
     * getStartTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getStartTime() { return startTime; }
    /**
     * setStartTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    /**
     * getEndTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getEndTime() { return endTime; }
    /**
     * setEndTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    /**
     * getSubmissionTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getSubmissionTime() { return submissionTime; }
    /**
     * setSubmissionTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setSubmissionTime(LocalDateTime submissionTime) { this.submissionTime = submissionTime; }
    /**
     * getActualScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getActualScore() { return actualScore; }
    /**
     * setActualScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setActualScore(BigDecimal actualScore) { this.actualScore = actualScore; }
    /**
     * getFeedbackFromInstructor メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getFeedbackFromInstructor() { return feedbackFromInstructor; }
    /**
     * setFeedbackFromInstructor メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setFeedbackFromInstructor(String feedbackFromInstructor) { this.feedbackFromInstructor = feedbackFromInstructor; }
    /**
     * getIsRandomized メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Boolean getIsRandomized() { return isRandomized; }
    /**
     * setIsRandomized メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setIsRandomized(Boolean isRandomized) { this.isRandomized = isRandomized; }
    /**
     * getShowAnswersAfterSubmission メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Boolean getShowAnswersAfterSubmission() { return showAnswersAfterSubmission; }
    /**
     * setShowAnswersAfterSubmission メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setShowAnswersAfterSubmission(Boolean showAnswersAfterSubmission) { this.showAnswersAfterSubmission = showAnswersAfterSubmission; }
    /**
     * getNotes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getNotes() { return notes; }
    /**
     * setNotes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setNotes(String notes) { this.notes = notes; }
    /**
     * getCreatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getCreatedAt() { return createdAt; }
    /**
     * setCreatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    /**
     * getUpdatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    /**
     * setUpdatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    /**
     * getVersion メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getVersion() { return version; }
    /**
     * setVersion メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setVersion(Long version) { this.version = version; }

    /**
     * toString メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
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
