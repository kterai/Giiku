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
    /** MockTestResponse メソッド */
    public MockTestResponse() {}

    // 全フィールドのコンストラクタ
    /** MockTestResponse メソッド */
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
    /** isActive メソッド */
    public boolean isActive() {
        return "ACTIVE".equals(status);
    }
    /** isScheduled メソッド */
    public boolean isScheduled() {
        return scheduledDate != null && LocalDateTime.now().isBefore(scheduledDate);
    }
    /** isInProgress メソッド */
    public boolean isInProgress() {
        LocalDateTime now = LocalDateTime.now();
        return startTime != null && endTime != null &&
               now.isAfter(startTime) && now.isBefore(endTime);
    }
    /** isCompleted メソッド */
    public boolean isCompleted() {
        return endTime != null && LocalDateTime.now().isAfter(endTime);
    }
    /** getTypeDisplayName メソッド */
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
    /** getStatusDisplayName メソッド */
    public String getStatusDisplayName() {
        switch (status != null ? status : "UNKNOWN") {
            case "DRAFT": return "下書き";
            case "ACTIVE": return "有効";
            case "INACTIVE": return "無効";
            case "ARCHIVED": return "アーカイブ";
            default: return "不明";
        }
    }
    /** getRemainingTimeMinutes メソッド */
    public Long getRemainingTimeMinutes() {
        if (endTime == null) return null;
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(endTime)) return 0L;
        return java.time.Duration.between(now, endTime).toMinutes();
    }

    // Getter and Setter methods
    /** getTestId メソッド */
    public Long getTestId() { return testId; }
    /** setTestId メソッド */
    public void setTestId(Long testId) { this.testId = testId; }
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
    /** getMaxScore メソッド */
    public BigDecimal getMaxScore() { return maxScore; }
    /** setMaxScore メソッド */
    public void setMaxScore(BigDecimal maxScore) { this.maxScore = maxScore; }
    /** getQuestionsPerPage メソッド */
    public Integer getQuestionsPerPage() { return questionsPerPage; }
    /** setQuestionsPerPage メソッド */
    public void setQuestionsPerPage(Integer questionsPerPage) { this.questionsPerPage = questionsPerPage; }
    /** getAllowNavigation メソッド */
    public Boolean getAllowNavigation() { return allowNavigation; }
    /** setAllowNavigation メソッド */
    public void setAllowNavigation(Boolean allowNavigation) { this.allowNavigation = allowNavigation; }
    /** getAutoSubmit メソッド */
    public Boolean getAutoSubmit() { return autoSubmit; }
    /** setAutoSubmit メソッド */
    public void setAutoSubmit(Boolean autoSubmit) { this.autoSubmit = autoSubmit; }
    /** getInstructions メソッド */
    public String getInstructions() { return instructions; }
    /** setInstructions メソッド */
    public void setInstructions(String instructions) { this.instructions = instructions; }
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

    // 統計情報のGetterとSetter
    /** getTotalParticipants メソッド */
    public Integer getTotalParticipants() { return totalParticipants; }
    /** setTotalParticipants メソッド */
    public void setTotalParticipants(Integer totalParticipants) { this.totalParticipants = totalParticipants; }
    /** getCompletedParticipants メソッド */
    public Integer getCompletedParticipants() { return completedParticipants; }
    /** setCompletedParticipants メソッド */
    public void setCompletedParticipants(Integer completedParticipants) { this.completedParticipants = completedParticipants; }
    /** getAverageScore メソッド */
    public BigDecimal getAverageScore() { return averageScore; }
    /** setAverageScore メソッド */
    public void setAverageScore(BigDecimal averageScore) { this.averageScore = averageScore; }
    /** getHighestScore メソッド */
    public BigDecimal getHighestScore() { return highestScore; }
    /** setHighestScore メソッド */
    public void setHighestScore(BigDecimal highestScore) { this.highestScore = highestScore; }
    /** getLowestScore メソッド */
    public BigDecimal getLowestScore() { return lowestScore; }
    /** setLowestScore メソッド */
    public void setLowestScore(BigDecimal lowestScore) { this.lowestScore = lowestScore; }

    /** toString メソッド */
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
