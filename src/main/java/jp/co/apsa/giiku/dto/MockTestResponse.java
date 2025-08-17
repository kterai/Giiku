package jp.co.apsa.giiku.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * モックテスト情報レスポンス用DTOクラス
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class MockTestResponse {

    private Long testId;
    private Long programId;
    private String testType;
    private String title;
    private String description;
    private Integer durationMinutes;
    private Integer totalQuestions;
    private BigDecimal passingScore;
    private String status;
    private LocalDateTime scheduledDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isRandomized;
    private Boolean showResults;
    private Boolean allowRetake;
    private Integer maxAttempts;
    private Long companyId;
    private String notes;
    private BigDecimal maxScore;
    private Integer questionsPerPage;
    private Boolean allowNavigation;
    private Boolean autoSubmit;
    private String instructions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;

    // 統計情報フィールド
    private Integer totalParticipants;
    private Integer completedParticipants;
    private BigDecimal averageScore;
    private BigDecimal highestScore;
    private BigDecimal lowestScore;

    // デフォルトコンストラクタ
    /**
     * MockTestResponse メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public MockTestResponse() {}

    // 全フィールドのコンストラクタ
    /**
     * MockTestResponse メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public MockTestResponse(Long testId, Long programId, String testType, String title,
                           String description, Integer durationMinutes, Integer totalQuestions,
                           BigDecimal passingScore, String status, LocalDateTime scheduledDate,
                           LocalDateTime startTime, LocalDateTime endTime, Boolean isRandomized,
                           Boolean showResults, Boolean allowRetake, Integer maxAttempts,
                           Long companyId, String notes, BigDecimal maxScore,
                           Integer questionsPerPage, Boolean allowNavigation, Boolean autoSubmit,
                           String instructions, LocalDateTime createdAt, LocalDateTime updatedAt,
                           Long version) {
        this.testId = testId;
        this.programId = programId;
        this.testType = testType;
        this.title = title;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.totalQuestions = totalQuestions;
        this.passingScore = passingScore;
        this.status = status;
        this.scheduledDate = scheduledDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isRandomized = isRandomized;
        this.showResults = showResults;
        this.allowRetake = allowRetake;
        this.maxAttempts = maxAttempts;
        this.companyId = companyId;
        this.notes = notes;
        this.maxScore = maxScore;
        this.questionsPerPage = questionsPerPage;
        this.allowNavigation = allowNavigation;
        this.autoSubmit = autoSubmit;
        this.instructions = instructions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    // ユーティリティメソッド
    /**
     * isActive メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public boolean isActive() {
        return "ACTIVE".equals(status);
    }
    /**
     * isScheduled メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public boolean isScheduled() {
        return scheduledDate != null && LocalDateTime.now().isBefore(scheduledDate);
    }
    /**
     * isInProgress メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public boolean isInProgress() {
        LocalDateTime now = LocalDateTime.now();
        return startTime != null && endTime != null &&
               now.isAfter(startTime) && now.isBefore(endTime);
    }
    /**
     * isCompleted メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public boolean isCompleted() {
        return endTime != null && LocalDateTime.now().isAfter(endTime);
    }
    /**
     * getTypeDisplayName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getTypeDisplayName() {
        switch (testType != null ? testType : "UNKNOWN") {
            case "PRACTICE": return "練習テスト";
            case "MIDTERM": return "中間テスト";
            case "FINAL": return "期末テスト";
            case "QUIZ": return "クイズ";
            case "ASSESSMENT": return "アセスメント";
            default: return "不明";
        }
    }
    /**
     * getStatusDisplayName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getStatusDisplayName() {
        switch (status != null ? status : "UNKNOWN") {
            case "DRAFT": return "下書き";
            case "ACTIVE": return "有効";
            case "INACTIVE": return "無効";
            case "ARCHIVED": return "アーカイブ";
            default: return "不明";
        }
    }
    /**
     * getRemainingTimeMinutes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getRemainingTimeMinutes() {
        if (endTime == null) return null;
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(endTime)) return 0L;
        return java.time.Duration.between(now, endTime).toMinutes();
    }

    // Getter and Setter methods
    /**
     * getTestId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public Long getTestId() { return testId; }
    /**
     * setTestId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTestId(Long testId) { this.testId = testId; }
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
     * getQuestionsPerPage メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getQuestionsPerPage() { return questionsPerPage; }
    /**
     * setQuestionsPerPage メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setQuestionsPerPage(Integer questionsPerPage) { this.questionsPerPage = questionsPerPage; }
    /**
     * getAllowNavigation メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Boolean getAllowNavigation() { return allowNavigation; }
    /**
     * setAllowNavigation メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setAllowNavigation(Boolean allowNavigation) { this.allowNavigation = allowNavigation; }
    /**
     * getAutoSubmit メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Boolean getAutoSubmit() { return autoSubmit; }
    /**
     * setAutoSubmit メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setAutoSubmit(Boolean autoSubmit) { this.autoSubmit = autoSubmit; }
    /**
     * getInstructions メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getInstructions() { return instructions; }
    /**
     * setInstructions メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setInstructions(String instructions) { this.instructions = instructions; }
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

    // 統計情報のGetterとSetter
    /**
     * getTotalParticipants メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public Integer getTotalParticipants() { return totalParticipants; }
    /**
     * setTotalParticipants メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTotalParticipants(Integer totalParticipants) { this.totalParticipants = totalParticipants; }
    /**
     * getCompletedParticipants メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getCompletedParticipants() { return completedParticipants; }
    /**
     * setCompletedParticipants メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCompletedParticipants(Integer completedParticipants) { this.completedParticipants = completedParticipants; }
    /**
     * getAverageScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getAverageScore() { return averageScore; }
    /**
     * setAverageScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setAverageScore(BigDecimal averageScore) { this.averageScore = averageScore; }
    /**
     * getHighestScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getHighestScore() { return highestScore; }
    /**
     * setHighestScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setHighestScore(BigDecimal highestScore) { this.highestScore = highestScore; }
    /**
     * getLowestScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getLowestScore() { return lowestScore; }
    /**
     * setLowestScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setLowestScore(BigDecimal lowestScore) { this.lowestScore = lowestScore; }

    /**
     * toString メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    @Override
    public String toString() {
        return "MockTestResponse{" +
                "testId=" + testId +
                ", programId=" + programId +
                ", testType='" + testType + "'" +
                ", title='" + title + "'" +
                ", status='" + status + "'" +
                ", durationMinutes=" + durationMinutes +
                ", totalQuestions=" + totalQuestions +
                ", passingScore=" + passingScore +
                ", totalParticipants=" + totalParticipants +
                "}";
    }
}
