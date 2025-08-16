package jp.co.apsa.giiku.dto;

import java.math.BigDecimal;

public class StudentEnrollmentStatsDto {
    private Long totalEnrollments;
    private Long activeEnrollments;
    private Long completedEnrollments;
    private Long passedEnrollments;
    private BigDecimal passRate;
    private BigDecimal averageScore;
    private BigDecimal averageProgress;

    public StudentEnrollmentStatsDto() {}

    public Long getTotalEnrollments() { return totalEnrollments; }
    public void setTotalEnrollments(Long totalEnrollments) { this.totalEnrollments = totalEnrollments; }

    public Long getActiveEnrollments() { return activeEnrollments; }
    public void setActiveEnrollments(Long activeEnrollments) { this.activeEnrollments = activeEnrollments; }

    public Long getCompletedEnrollments() { return completedEnrollments; }
    public void setCompletedEnrollments(Long completedEnrollments) { this.completedEnrollments = completedEnrollments; }

    public Long getPassedEnrollments() { return passedEnrollments; }
    public void setPassedEnrollments(Long passedEnrollments) { this.passedEnrollments = passedEnrollments; }

    public BigDecimal getPassRate() { return passRate; }
    public void setPassRate(BigDecimal passRate) { this.passRate = passRate; }

    public BigDecimal getAverageScore() { return averageScore; }
    public void setAverageScore(BigDecimal averageScore) { this.averageScore = averageScore; }

    public BigDecimal getAverageProgress() { return averageProgress; }
    public void setAverageProgress(BigDecimal averageProgress) { this.averageProgress = averageProgress; }
}