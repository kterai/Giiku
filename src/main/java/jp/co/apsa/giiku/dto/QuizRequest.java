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
    /**
     * QuizRequest メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public QuizRequest() {}

    // 主要フィールドのコンストラクタ
    /**
     * QuizRequest メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
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
     * toString メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
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
