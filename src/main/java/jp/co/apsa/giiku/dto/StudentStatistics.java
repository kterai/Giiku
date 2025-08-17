package jp.co.apsa.giiku.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 学生統計情報DTO
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
    /** StudentStatistics メソッド */
    public StudentStatistics() {
        this.lastUpdated = LocalDateTime.now();
    }

    // コンストラクタ
    /** StudentStatistics メソッド */
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
    /** hasActiveStudents メソッド */
    public boolean hasActiveStudents() {
        return activeStudents != null && activeStudents > 0;
    }
    /** hasHighCompletionRate メソッド */
    public boolean hasHighCompletionRate() {
        return completionRate != null && completionRate.compareTo(BigDecimal.valueOf(80)) >= 0;
    }
    /** calculateCompletionRate メソッド */
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
    /** getTotalStudents メソッド */
    public Long getTotalStudents() { return totalStudents; }
    /** setTotalStudents メソッド */
    public void setTotalStudents(Long totalStudents) { this.totalStudents = totalStudents; }
    /** getActiveStudents メソッド */
    public Long getActiveStudents() { return activeStudents; }
    /** setActiveStudents メソッド */
    public void setActiveStudents(Long activeStudents) { this.activeStudents = activeStudents; }
    /** getInactiveStudents メソッド */
    public Long getInactiveStudents() { return inactiveStudents; }
    /** setInactiveStudents メソッド */
    public void setInactiveStudents(Long inactiveStudents) { this.inactiveStudents = inactiveStudents; }
    /** getEnrolledStudents メソッド */
    public Long getEnrolledStudents() { return enrolledStudents; }
    /** setEnrolledStudents メソッド */
    public void setEnrolledStudents(Long enrolledStudents) { this.enrolledStudents = enrolledStudents; }
    /** getCompletedStudents メソッド */
    public Long getCompletedStudents() { return completedStudents; }
    /** setCompletedStudents メソッド */
    public void setCompletedStudents(Long completedStudents) { this.completedStudents = completedStudents; }
    /** getInProgressStudents メソッド */
    public Long getInProgressStudents() { return inProgressStudents; }
    /** setInProgressStudents メソッド */
    public void setInProgressStudents(Long inProgressStudents) { this.inProgressStudents = inProgressStudents; }
    /** getAverageProgress メソッド */
    public BigDecimal getAverageProgress() { return averageProgress; }
    /** setAverageProgress メソッド */
    public void setAverageProgress(BigDecimal averageProgress) { this.averageProgress = averageProgress; }
    /** getAverageScore メソッド */
    public BigDecimal getAverageScore() { return averageScore; }
    /** setAverageScore メソッド */
    public void setAverageScore(BigDecimal averageScore) { this.averageScore = averageScore; }
    /** getCompletionRate メソッド */
    public BigDecimal getCompletionRate() { return completionRate; }
    /** setCompletionRate メソッド */
    public void setCompletionRate(BigDecimal completionRate) { this.completionRate = completionRate; }
    /** getTotalCourses メソッド */
    public Integer getTotalCourses() { return totalCourses; }
    /** setTotalCourses メソッド */
    public void setTotalCourses(Integer totalCourses) { this.totalCourses = totalCourses; }
    /** getActiveCourses メソッド */
    public Integer getActiveCourses() { return activeCourses; }
    /** setActiveCourses メソッド */
    public void setActiveCourses(Integer activeCourses) { this.activeCourses = activeCourses; }
    /** getTotalEnrollments メソッド */
    public Long getTotalEnrollments() { return totalEnrollments; }
    /** setTotalEnrollments メソッド */
    public void setTotalEnrollments(Long totalEnrollments) { this.totalEnrollments = totalEnrollments; }
    /** getTotalCompletions メソッド */
    public Long getTotalCompletions() { return totalCompletions; }
    /** setTotalCompletions メソッド */
    public void setTotalCompletions(Long totalCompletions) { this.totalCompletions = totalCompletions; }
    /** getLastUpdated メソッド */
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    /** setLastUpdated メソッド */
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
    /** getCompanyName メソッド */
    public String getCompanyName() { return companyName; }
    /** setCompanyName メソッド */
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    /** getCompanyId メソッド */
    public Long getCompanyId() { return companyId; }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    /** toString メソッド */
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
