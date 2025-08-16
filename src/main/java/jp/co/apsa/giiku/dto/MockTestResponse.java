package jp.co.apsa.giiku.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * モックテスト情報レスポンス用DTOクラス
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
    public MockTestResponse() {}

    // 全フィールドのコンストラクタ
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
    public boolean isActive() {
        return "ACTIVE".equals(status);
    }

    public boolean isScheduled() {
        return scheduledDate != null && LocalDateTime.now().isBefore(scheduledDate);
    }

    public boolean isInProgress() {
        LocalDateTime now = LocalDateTime.now();
        return startTime != null && endTime != null &&
               now.isAfter(startTime) && now.isBefore(endTime);
    }

    public boolean isCompleted() {
        return endTime != null && LocalDateTime.now().isAfter(endTime);
    }

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

    public String getStatusDisplayName() {
        switch (status != null ? status : "UNKNOWN") {
            case "DRAFT": return "下書き";
            case "ACTIVE": return "有効";
            case "INACTIVE": return "無効";
            case "ARCHIVED": return "アーカイブ";
            default: return "不明";
        }
    }

    public Long getRemainingTimeMinutes() {
        if (endTime == null) return null;
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(endTime)) return 0L;
        return java.time.Duration.between(now, endTime).toMinutes();
    }

    // Getter and Setter methods
    public Long getTestId() { return testId; }
    public void setTestId(Long testId) { this.testId = testId; }

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

    public BigDecimal getMaxScore() { return maxScore; }
    public void setMaxScore(BigDecimal maxScore) { this.maxScore = maxScore; }

    public Integer getQuestionsPerPage() { return questionsPerPage; }
    public void setQuestionsPerPage(Integer questionsPerPage) { this.questionsPerPage = questionsPerPage; }

    public Boolean getAllowNavigation() { return allowNavigation; }
    public void setAllowNavigation(Boolean allowNavigation) { this.allowNavigation = allowNavigation; }

    public Boolean getAutoSubmit() { return autoSubmit; }
    public void setAutoSubmit(Boolean autoSubmit) { this.autoSubmit = autoSubmit; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    // 統計情報のGetterとSetter
    public Integer getTotalParticipants() { return totalParticipants; }
    public void setTotalParticipants(Integer totalParticipants) { this.totalParticipants = totalParticipants; }

    public Integer getCompletedParticipants() { return completedParticipants; }
    public void setCompletedParticipants(Integer completedParticipants) { this.completedParticipants = completedParticipants; }

    public BigDecimal getAverageScore() { return averageScore; }
    public void setAverageScore(BigDecimal averageScore) { this.averageScore = averageScore; }

    public BigDecimal getHighestScore() { return highestScore; }
    public void setHighestScore(BigDecimal highestScore) { this.highestScore = highestScore; }

    public BigDecimal getLowestScore() { return lowestScore; }
    public void setLowestScore(BigDecimal lowestScore) { this.lowestScore = lowestScore; }

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
