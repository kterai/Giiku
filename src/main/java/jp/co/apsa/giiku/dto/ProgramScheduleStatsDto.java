package jp.co.apsa.giiku.dto;

/**
 * プログラムスケジュール統計DTO
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class ProgramScheduleStatsDto {
    private Long totalSchedules;
    private Long activeSchedules;
    private Long completedSchedules;
    private Long totalParticipants;
    private Double averageCapacityUtilization;
    private Double completionRate;
    /**
     * ProgramScheduleStatsDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public ProgramScheduleStatsDto() {}
    /**
     * getTotalSchedules メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getTotalSchedules() { return totalSchedules; }
    /**
     * setTotalSchedules メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTotalSchedules(Long totalSchedules) { this.totalSchedules = totalSchedules; }
    /**
     * getActiveSchedules メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getActiveSchedules() { return activeSchedules; }
    /**
     * setActiveSchedules メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setActiveSchedules(Long activeSchedules) { this.activeSchedules = activeSchedules; }
    /**
     * getCompletedSchedules メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getCompletedSchedules() { return completedSchedules; }
    /**
     * setCompletedSchedules メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCompletedSchedules(Long completedSchedules) { this.completedSchedules = completedSchedules; }
    /**
     * getTotalParticipants メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getTotalParticipants() { return totalParticipants; }
    /**
     * setTotalParticipants メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTotalParticipants(Long totalParticipants) { this.totalParticipants = totalParticipants; }
    /**
     * getAverageCapacityUtilization メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Double getAverageCapacityUtilization() { return averageCapacityUtilization; }
    /**
     * setAverageCapacityUtilization メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setAverageCapacityUtilization(Double averageCapacityUtilization) { this.averageCapacityUtilization = averageCapacityUtilization; }

    /** 完了率取得 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public Double getCompletionRate() { return completionRate; }
    /**
     * setCompletionRate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCompletionRate(Double completionRate) { this.completionRate = completionRate; }
}
