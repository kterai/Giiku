package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * クイズ情報の作成・更新リクエスト用DTOクラス
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class QuizRequest {

    @NotBlank(message = "タイトルは必須です")
    @Size(max = 200, message = "タイトルは200文字以内で入力してください")
    private String title;

    @Size(max = 1000, message = "説明は1000文字以内で入力してください")
    private String description;

    private Long trainingProgramId;

    private Long chapterId;

    private Long studentId;

    @NotNull(message = "講師IDは必須です")
    private Long instructorId;

    @NotNull(message = "会社IDは必須です")
    private Long companyId;

    @Size(max = 20, message = "クイズ状況は20文字以内で入力してください")
    private String quizStatus;

    @DecimalMin(value = "0.0", message = "制限時間は0以上で入力してください")
    private BigDecimal timeLimit;

    @DecimalMin(value = "0.0", message = "最大スコアは0以上で入力してください")
    private BigDecimal maxScore;

    @DecimalMin(value = "0.0", message = "合格スコアは0以上で入力してください")
    private BigDecimal passingScore;

    @Min(value = 0, message = "試行回数制限は0以上で入力してください")
    private Integer attemptLimit;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime submissionTime;

    @Size(max = 500, message = "備考は500文字以内で入力してください")
    private String notes;

    // デフォルトコンストラクタ
    /** QuizRequest メソッド */
    public QuizRequest() {}

    // 主要フィールドのコンストラクタ
    /** QuizRequest メソッド */
    public QuizRequest(String title, String description, Long instructorId, Long companyId,
                      String quizStatus, BigDecimal timeLimit, BigDecimal maxScore,
                      BigDecimal passingScore, Integer attemptLimit) {
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.companyId = companyId;
        this.quizStatus = quizStatus;
        this.timeLimit = timeLimit;
        this.maxScore = maxScore;
        this.passingScore = passingScore;
        this.attemptLimit = attemptLimit;
    }

    // Getter and Setter methods
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
    /** getNotes メソッド */
    public String getNotes() { return notes; }
    /** setNotes メソッド */
    public void setNotes(String notes) { this.notes = notes; }

    /** toString メソッド */
    @Override
    public String toString() {
        return "QuizRequest{" +
                "title='" + title + "'" +
                ", description='" + description + "'" +
                ", instructorId=" + instructorId +
                ", companyId=" + companyId +
                ", quizStatus='" + quizStatus + "'" +
                ", maxScore=" + maxScore +
                ", passingScore=" + passingScore +
                "}";
    }
}
