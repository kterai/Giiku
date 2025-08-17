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
    /**
     * StudentEnrollmentStatsDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public StudentEnrollmentStatsDto() {}
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
     * getActiveEnrollments メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getActiveEnrollments() { return activeEnrollments; }
    /**
     * setActiveEnrollments メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setActiveEnrollments(Long activeEnrollments) { this.activeEnrollments = activeEnrollments; }
    /**
     * getCompletedEnrollments メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getCompletedEnrollments() { return completedEnrollments; }
    /**
     * setCompletedEnrollments メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCompletedEnrollments(Long completedEnrollments) { this.completedEnrollments = completedEnrollments; }
    /**
     * getPassedEnrollments メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getPassedEnrollments() { return passedEnrollments; }
    /**
     * setPassedEnrollments メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setPassedEnrollments(Long passedEnrollments) { this.passedEnrollments = passedEnrollments; }
    /**
     * getPassRate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getPassRate() { return passRate; }
    /**
     * setPassRate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setPassRate(BigDecimal passRate) { this.passRate = passRate; }
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
}
