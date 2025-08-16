package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * モックテスト情報の作成・更新リクエスト用DTOクラス
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
    public MockTestRequest() {}

    // 主要フィールドのコンストラクタ
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
    public Long getProgramId() { return programId; }
    public void setProgramId(Long programId) { this.programId = programId; }

    public String getTestType() { return testType; }
    public void setTestType(String testType) { this.testType = testType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public Integer getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(Integer totalQuestions) { this.totalQuestions = totalQuestions; }

    public BigDecimal getPassingScore() { return passingScore; }
    public void setPassingScore(BigDecimal passingScore) { this.passingScore = passingScore; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDateTime scheduledDate) { this.scheduledDate = scheduledDate; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Boolean getIsRandomized() { return isRandomized; }
    public void setIsRandomized(Boolean isRandomized) { this.isRandomized = isRandomized; }

    public Boolean getShowResults() { return showResults; }
    public void setShowResults(Boolean showResults) { this.showResults = showResults; }

    public Boolean getAllowRetake() { return allowRetake; }
    public void setAllowRetake(Boolean allowRetake) { this.allowRetake = allowRetake; }

    public Integer getMaxAttempts() { return maxAttempts; }
    public void setMaxAttempts(Integer maxAttempts) { this.maxAttempts = maxAttempts; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

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
