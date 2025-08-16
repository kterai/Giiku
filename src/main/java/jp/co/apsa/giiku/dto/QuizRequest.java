package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * クイズ情報の作成・更新リクエスト用DTOクラス
 */
public class QuizRequest {

    @NotBlank(message = "タイトルは必須です")
    @Size(max = 200, message = "タイトルは200文字以内で入力してください")
    private String title;

    @Size(max = 1000, message = "説明は1000文字以内で入力してください")
    private String description;

    private Long trainingProgramId;

    private Long lectureId;

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
    public QuizRequest() {}

    // 主要フィールドのコンストラクタ
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

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public LocalDateTime getSubmissionTime() { return submissionTime; }
    public void setSubmissionTime(LocalDateTime submissionTime) { this.submissionTime = submissionTime; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

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
