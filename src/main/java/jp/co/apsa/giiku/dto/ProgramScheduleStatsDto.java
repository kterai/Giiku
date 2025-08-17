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

    public ProgramScheduleStatsDto() {}

    public Long getTotalSchedules() { return totalSchedules; }
    public void setTotalSchedules(Long totalSchedules) { this.totalSchedules = totalSchedules; }

    public Long getActiveSchedules() { return activeSchedules; }
    public void setActiveSchedules(Long activeSchedules) { this.activeSchedules = activeSchedules; }

    public Long getCompletedSchedules() { return completedSchedules; }
    public void setCompletedSchedules(Long completedSchedules) { this.completedSchedules = completedSchedules; }

    public Long getTotalParticipants() { return totalParticipants; }
    public void setTotalParticipants(Long totalParticipants) { this.totalParticipants = totalParticipants; }

    public Double getAverageCapacityUtilization() { return averageCapacityUtilization; }
    public void setAverageCapacityUtilization(Double averageCapacityUtilization) { this.averageCapacityUtilization = averageCapacityUtilization; }

    /** 完了率取得 */
    public Double getCompletionRate() { return completionRate; }
    public void setCompletionRate(Double completionRate) { this.completionRate = completionRate; }
}