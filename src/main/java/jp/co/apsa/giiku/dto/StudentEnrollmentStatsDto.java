package jp.co.apsa.giiku.dto;

import java.math.BigDecimal;
/**
 * The StudentEnrollmentStatsDto class.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class StudentEnrollmentStatsDto {
    private Long totalEnrollments;
    private Long activeEnrollments;
    private Long completedEnrollments;
    private Long passedEnrollments;
    private BigDecimal passRate;
    private BigDecimal averageScore;
    private BigDecimal averageProgress;
    /** StudentEnrollmentStatsDto メソッド */
    public StudentEnrollmentStatsDto() {}
    /** getTotalEnrollments メソッド */
    public Long getTotalEnrollments() { return totalEnrollments; }
    /** setTotalEnrollments メソッド */
    public void setTotalEnrollments(Long totalEnrollments) { this.totalEnrollments = totalEnrollments; }
    /** getActiveEnrollments メソッド */
    public Long getActiveEnrollments() { return activeEnrollments; }
    /** setActiveEnrollments メソッド */
    public void setActiveEnrollments(Long activeEnrollments) { this.activeEnrollments = activeEnrollments; }
    /** getCompletedEnrollments メソッド */
    public Long getCompletedEnrollments() { return completedEnrollments; }
    /** setCompletedEnrollments メソッド */
    public void setCompletedEnrollments(Long completedEnrollments) { this.completedEnrollments = completedEnrollments; }
    /** getPassedEnrollments メソッド */
    public Long getPassedEnrollments() { return passedEnrollments; }
    /** setPassedEnrollments メソッド */
    public void setPassedEnrollments(Long passedEnrollments) { this.passedEnrollments = passedEnrollments; }
    /** getPassRate メソッド */
    public BigDecimal getPassRate() { return passRate; }
    /** setPassRate メソッド */
    public void setPassRate(BigDecimal passRate) { this.passRate = passRate; }
    /** getAverageScore メソッド */
    public BigDecimal getAverageScore() { return averageScore; }
    /** setAverageScore メソッド */
    public void setAverageScore(BigDecimal averageScore) { this.averageScore = averageScore; }
    /** getAverageProgress メソッド */
    public BigDecimal getAverageProgress() { return averageProgress; }
    /** setAverageProgress メソッド */
    public void setAverageProgress(BigDecimal averageProgress) { this.averageProgress = averageProgress; }
}
