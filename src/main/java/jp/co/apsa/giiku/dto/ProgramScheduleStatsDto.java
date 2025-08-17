package jp.co.apsa.giiku.dto;

/**
 * プログラムスケジュール統計DTO
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
    /** ProgramScheduleStatsDto メソッド */
    public ProgramScheduleStatsDto() {}
    /** getTotalSchedules メソッド */
    public Long getTotalSchedules() { return totalSchedules; }
    /** setTotalSchedules メソッド */
    public void setTotalSchedules(Long totalSchedules) { this.totalSchedules = totalSchedules; }
    /** getActiveSchedules メソッド */
    public Long getActiveSchedules() { return activeSchedules; }
    /** setActiveSchedules メソッド */
    public void setActiveSchedules(Long activeSchedules) { this.activeSchedules = activeSchedules; }
    /** getCompletedSchedules メソッド */
    public Long getCompletedSchedules() { return completedSchedules; }
    /** setCompletedSchedules メソッド */
    public void setCompletedSchedules(Long completedSchedules) { this.completedSchedules = completedSchedules; }
    /** getTotalParticipants メソッド */
    public Long getTotalParticipants() { return totalParticipants; }
    /** setTotalParticipants メソッド */
    public void setTotalParticipants(Long totalParticipants) { this.totalParticipants = totalParticipants; }
    /** getAverageCapacityUtilization メソッド */
    public Double getAverageCapacityUtilization() { return averageCapacityUtilization; }
    /** setAverageCapacityUtilization メソッド */
    public void setAverageCapacityUtilization(Double averageCapacityUtilization) { this.averageCapacityUtilization = averageCapacityUtilization; }

    public Double getCompletionRate() { return completionRate; }
    /** setCompletionRate メソッド */
    public void setCompletionRate(Double completionRate) { this.completionRate = completionRate; }
}
