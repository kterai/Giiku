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
    /**
     * MockTestRequest メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public MockTestRequest() {}

    // 主要フィールドのコンストラクタ
    /**
     * MockTestRequest メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
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
    /**
     * getProgramId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public Long getProgramId() { return programId; }
    /**
     * setProgramId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setProgramId(Long programId) { this.programId = programId; }
    /**
     * getTestType メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getTestType() { return testType; }
    /**
     * setTestType メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTestType(String testType) { this.testType = testType; }
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
     * getDurationMinutes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getDurationMinutes() { return durationMinutes; }
    /**
     * setDurationMinutes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    /**
     * getTotalQuestions メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getTotalQuestions() { return totalQuestions; }
    /**
     * setTotalQuestions メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTotalQuestions(Integer totalQuestions) { this.totalQuestions = totalQuestions; }
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
     * getStatus メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getStatus() { return status; }
    /**
     * setStatus メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStatus(String status) { this.status = status; }
    /**
     * getScheduledDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getScheduledDate() { return scheduledDate; }
    /**
     * setScheduledDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setScheduledDate(LocalDateTime scheduledDate) { this.scheduledDate = scheduledDate; }
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
     * getShowResults メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Boolean getShowResults() { return showResults; }
    /**
     * setShowResults メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setShowResults(Boolean showResults) { this.showResults = showResults; }
    /**
     * getAllowRetake メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Boolean getAllowRetake() { return allowRetake; }
    /**
     * setAllowRetake メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setAllowRetake(Boolean allowRetake) { this.allowRetake = allowRetake; }
    /**
     * getMaxAttempts メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getMaxAttempts() { return maxAttempts; }
    /**
     * setMaxAttempts メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setMaxAttempts(Integer maxAttempts) { this.maxAttempts = maxAttempts; }
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
