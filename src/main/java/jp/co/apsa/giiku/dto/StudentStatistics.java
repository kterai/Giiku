package jp.co.apsa.giiku.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 学生統計情報DTO
 *
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
    /**
     * StudentStatistics メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public StudentStatistics() {
        this.lastUpdated = LocalDateTime.now();
    }

    // コンストラクタ
    /**
     * StudentStatistics メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
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
    /**
     * hasActiveStudents メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public boolean hasActiveStudents() {
        return activeStudents != null && activeStudents > 0;
    }
    /**
     * hasHighCompletionRate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public boolean hasHighCompletionRate() {
        return completionRate != null && completionRate.compareTo(BigDecimal.valueOf(80)) >= 0;
    }
    /**
     * calculateCompletionRate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

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
    /**
     * getTotalStudents メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public Long getTotalStudents() { return totalStudents; }
    /**
     * setTotalStudents メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTotalStudents(Long totalStudents) { this.totalStudents = totalStudents; }
    /**
     * getActiveStudents メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getActiveStudents() { return activeStudents; }
    /**
     * setActiveStudents メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setActiveStudents(Long activeStudents) { this.activeStudents = activeStudents; }
    /**
     * getInactiveStudents メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getInactiveStudents() { return inactiveStudents; }
    /**
     * setInactiveStudents メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setInactiveStudents(Long inactiveStudents) { this.inactiveStudents = inactiveStudents; }
    /**
     * getEnrolledStudents メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getEnrolledStudents() { return enrolledStudents; }
    /**
     * setEnrolledStudents メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setEnrolledStudents(Long enrolledStudents) { this.enrolledStudents = enrolledStudents; }
    /**
     * getCompletedStudents メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getCompletedStudents() { return completedStudents; }
    /**
     * setCompletedStudents メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCompletedStudents(Long completedStudents) { this.completedStudents = completedStudents; }
    /**
     * getInProgressStudents メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getInProgressStudents() { return inProgressStudents; }
    /**
     * setInProgressStudents メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setInProgressStudents(Long inProgressStudents) { this.inProgressStudents = inProgressStudents; }
    /**
     * getAverageProgress メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getAverageProgress() { return averageProgress; }
    /**
     * setAverageProgress メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setAverageProgress(BigDecimal averageProgress) { this.averageProgress = averageProgress; }
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
     * getCompletionRate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getCompletionRate() { return completionRate; }
    /**
     * setCompletionRate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCompletionRate(BigDecimal completionRate) { this.completionRate = completionRate; }
    /**
     * getTotalCourses メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getTotalCourses() { return totalCourses; }
    /**
     * setTotalCourses メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTotalCourses(Integer totalCourses) { this.totalCourses = totalCourses; }
    /**
     * getActiveCourses メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getActiveCourses() { return activeCourses; }
    /**
     * setActiveCourses メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setActiveCourses(Integer activeCourses) { this.activeCourses = activeCourses; }
    /**
     * getTotalEnrollments メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getTotalEnrollments() { return totalEnrollments; }
    /**
     * setTotalEnrollments メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTotalEnrollments(Long totalEnrollments) { this.totalEnrollments = totalEnrollments; }
    /**
     * getTotalCompletions メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getTotalCompletions() { return totalCompletions; }
    /**
     * setTotalCompletions メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTotalCompletions(Long totalCompletions) { this.totalCompletions = totalCompletions; }
    /**
     * getLastUpdated メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    /**
     * setLastUpdated メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
    /**
     * getCompanyName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getCompanyName() { return companyName; }
    /**
     * setCompanyName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCompanyName(String companyName) { this.companyName = companyName; }
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
     * toString メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
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
