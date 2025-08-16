package jp.co.apsa.giiku.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 学生統計情報DTO
 * 
 * @author Generated
 * @version 1.0
 */
public class StudentStatistics {

    private Long totalStudents;
    private Long activeStudents;
    private Long inactiveStudents;
    private Long enrolledStudents;
    private Long completedStudents;
    private Long inProgressStudents;
    private BigDecimal averageProgress;
    private BigDecimal averageScore;
    private BigDecimal completionRate;
    private Integer totalCourses;
    private Integer activeCourses;
    private Long totalEnrollments;
    private Long totalCompletions;
    private LocalDateTime lastUpdated;
    private String companyName;
    private Long companyId;

    // デフォルトコンストラクタ
    public StudentStatistics() {
        this.lastUpdated = LocalDateTime.now();
    }

    // コンストラクタ
    public StudentStatistics(Long totalStudents, Long activeStudents, Long enrolledStudents, 
                           BigDecimal averageProgress, BigDecimal averageScore) {
        this();
        this.totalStudents = totalStudents;
        this.activeStudents = activeStudents;
        this.enrolledStudents = enrolledStudents;
        this.averageProgress = averageProgress;
        this.averageScore = averageScore;
        this.inactiveStudents = totalStudents - activeStudents;
    }

    // ビジネスロジックメソッド
    public boolean hasActiveStudents() {
        return activeStudents != null && activeStudents > 0;
    }

    public boolean hasHighCompletionRate() {
        return completionRate != null && completionRate.compareTo(BigDecimal.valueOf(80)) >= 0;
    }

    public void calculateCompletionRate() {
        if (totalEnrollments != null && totalEnrollments > 0 && totalCompletions != null) {
            this.completionRate = BigDecimal.valueOf(totalCompletions)
                .divide(BigDecimal.valueOf(totalEnrollments), 2, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        } else {
            this.completionRate = BigDecimal.ZERO;
        }
    }

    // Getter/Setter
    public Long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(Long totalStudents) { this.totalStudents = totalStudents; }

    public Long getActiveStudents() { return activeStudents; }
    public void setActiveStudents(Long activeStudents) { this.activeStudents = activeStudents; }

    public Long getInactiveStudents() { return inactiveStudents; }
    public void setInactiveStudents(Long inactiveStudents) { this.inactiveStudents = inactiveStudents; }

    public Long getEnrolledStudents() { return enrolledStudents; }
    public void setEnrolledStudents(Long enrolledStudents) { this.enrolledStudents = enrolledStudents; }

    public Long getCompletedStudents() { return completedStudents; }
    public void setCompletedStudents(Long completedStudents) { this.completedStudents = completedStudents; }

    public Long getInProgressStudents() { return inProgressStudents; }
    public void setInProgressStudents(Long inProgressStudents) { this.inProgressStudents = inProgressStudents; }

    public BigDecimal getAverageProgress() { return averageProgress; }
    public void setAverageProgress(BigDecimal averageProgress) { this.averageProgress = averageProgress; }

    public BigDecimal getAverageScore() { return averageScore; }
    public void setAverageScore(BigDecimal averageScore) { this.averageScore = averageScore; }

    public BigDecimal getCompletionRate() { return completionRate; }
    public void setCompletionRate(BigDecimal completionRate) { this.completionRate = completionRate; }

    public Integer getTotalCourses() { return totalCourses; }
    public void setTotalCourses(Integer totalCourses) { this.totalCourses = totalCourses; }

    public Integer getActiveCourses() { return activeCourses; }
    public void setActiveCourses(Integer activeCourses) { this.activeCourses = activeCourses; }

    public Long getTotalEnrollments() { return totalEnrollments; }
    public void setTotalEnrollments(Long totalEnrollments) { this.totalEnrollments = totalEnrollments; }

    public Long getTotalCompletions() { return totalCompletions; }
    public void setTotalCompletions(Long totalCompletions) { this.totalCompletions = totalCompletions; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    @Override
    public String toString() {
        return "StudentStatistics{" +
                "totalStudents=" + totalStudents +
                ", activeStudents=" + activeStudents +
                ", enrolledStudents=" + enrolledStudents +
                ", averageProgress=" + averageProgress +
                ", averageScore=" + averageScore +
                ", completionRate=" + completionRate +
                ", companyId=" + companyId +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}