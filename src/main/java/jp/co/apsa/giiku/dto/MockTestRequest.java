package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * モックテスト情報の作成・更新リクエスト用DTOクラス
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class MockTestRequest {

    @NotNull(message = "プログラムIDは必須です")
    private Long programId;

    @NotBlank(message = "テストタイプは必須です")
    @Size(max = 20, message = "テストタイプは20文字以内で入力してください")
    private String testType;

    @NotBlank(message = "タイトルは必須です")
    @Size(max = 200, message = "タイトルは200文字以内で入力してください")
    private String title;

    @Size(max = 1000, message = "説明は1000文字以内で入力してください")
    private String description;

    @Min(value = 1, message = "テスト時間は1分以上で入力してください")
    @Max(value = 600, message = "テスト時間は600分以下で入力してください")
    private Integer durationMinutes;

    @Min(value = 1, message = "問題数は1以上で入力してください")
    private Integer totalQuestions;

    @DecimalMin(value = "0.0", message = "合格スコアは0以上で入力してください")
    @DecimalMax(value = "100.0", message = "合格スコアは100以下で入力してください")
    private BigDecimal passingScore;

    @Size(max = 20, message = "ステータスは20文字以内で入力してください")
    private String status;

    private LocalDateTime scheduledDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean isRandomized;

    private Boolean showResults;

    private Boolean allowRetake;

    @Min(value = 0, message = "再受験制限は0以上で入力してください")
    private Integer maxAttempts;

    @NotNull(message = "会社IDは必須です")
    private Long companyId;

    @Size(max = 500, message = "備考は500文字以内で入力してください")
    private String notes;

    // デフォルトコンストラクタ
    /** MockTestRequest メソッド */
    public MockTestRequest() {}

    // 主要フィールドのコンストラクタ
    /** MockTestRequest メソッド */
    public MockTestRequest(Long programId, String testType, String title, String description,
                          Integer durationMinutes, Integer totalQuestions, BigDecimal passingScore,
                          String status, Long companyId) {
        this.programId = programId;
        this.testType = testType;
        this.title = title;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.totalQuestions = totalQuestions;
        this.passingScore = passingScore;
        this.status = status;
        this.companyId = companyId;
    }

    // Getter and Setter methods
    /** getProgramId メソッド */
    public Long getProgramId() { return programId; }
    /** setProgramId メソッド */
    public void setProgramId(Long programId) { this.programId = programId; }
    /** getTestType メソッド */
    public String getTestType() { return testType; }
    /** setTestType メソッド */
    public void setTestType(String testType) { this.testType = testType; }
    /** getTitle メソッド */
    public String getTitle() { return title; }
    /** setTitle メソッド */
    public void setTitle(String title) { this.title = title; }
    /** getDescription メソッド */
    public String getDescription() { return description; }
    /** setDescription メソッド */
    public void setDescription(String description) { this.description = description; }
    /** getDurationMinutes メソッド */
    public Integer getDurationMinutes() { return durationMinutes; }
    /** setDurationMinutes メソッド */
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    /** getTotalQuestions メソッド */
    public Integer getTotalQuestions() { return totalQuestions; }
    /** setTotalQuestions メソッド */
    public void setTotalQuestions(Integer totalQuestions) { this.totalQuestions = totalQuestions; }
    /** getPassingScore メソッド */
    public BigDecimal getPassingScore() { return passingScore; }
    /** setPassingScore メソッド */
    public void setPassingScore(BigDecimal passingScore) { this.passingScore = passingScore; }
    /** getStatus メソッド */
    public String getStatus() { return status; }
    /** setStatus メソッド */
    public void setStatus(String status) { this.status = status; }
    /** getScheduledDate メソッド */
    public LocalDateTime getScheduledDate() { return scheduledDate; }
    /** setScheduledDate メソッド */
    public void setScheduledDate(LocalDateTime scheduledDate) { this.scheduledDate = scheduledDate; }
    /** getStartTime メソッド */
    public LocalDateTime getStartTime() { return startTime; }
    /** setStartTime メソッド */
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    /** getEndTime メソッド */
    public LocalDateTime getEndTime() { return endTime; }
    /** setEndTime メソッド */
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    /** getIsRandomized メソッド */
    public Boolean getIsRandomized() { return isRandomized; }
    /** setIsRandomized メソッド */
    public void setIsRandomized(Boolean isRandomized) { this.isRandomized = isRandomized; }
    /** getShowResults メソッド */
    public Boolean getShowResults() { return showResults; }
    /** setShowResults メソッド */
    public void setShowResults(Boolean showResults) { this.showResults = showResults; }
    /** getAllowRetake メソッド */
    public Boolean getAllowRetake() { return allowRetake; }
    /** setAllowRetake メソッド */
    public void setAllowRetake(Boolean allowRetake) { this.allowRetake = allowRetake; }
    /** getMaxAttempts メソッド */
    public Integer getMaxAttempts() { return maxAttempts; }
    /** setMaxAttempts メソッド */
    public void setMaxAttempts(Integer maxAttempts) { this.maxAttempts = maxAttempts; }
    /** getCompanyId メソッド */
    public Long getCompanyId() { return companyId; }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    /** getNotes メソッド */
    public String getNotes() { return notes; }
    /** setNotes メソッド */
    public void setNotes(String notes) { this.notes = notes; }

    /** toString メソッド */
    @Override
    public String toString() {
        return "MockTestRequest{" +
                "programId=" + programId +
                ", testType='" + testType + "'" +
                ", title='" + title + "'" +
                ", durationMinutes=" + durationMinutes +
                ", totalQuestions=" + totalQuestions +
                ", passingScore=" + passingScore +
                ", status='" + status + "'" +
                ", companyId=" + companyId +
                "}";
    }
}
