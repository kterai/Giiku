package jp.co.apsa.giiku.dto;

public class LectureChapterStatsDto {
    private Long totalChapters;
    private Long activeChapters;
    private Long inactiveChapters;
    private Integer totalEstimatedMinutes;
    private Double averageChapterDuration;

    public LectureChapterStatsDto() {}

    public Long getTotalChapters() { return totalChapters; }
    public void setTotalChapters(Long totalChapters) { this.totalChapters = totalChapters; }

    public Long getActiveChapters() { return activeChapters; }
    public void setActiveChapters(Long activeChapters) { this.activeChapters = activeChapters; }

    public Long getInactiveChapters() { return inactiveChapters; }
    public void setInactiveChapters(Long inactiveChapters) { this.inactiveChapters = inactiveChapters; }

    public Integer getTotalEstimatedMinutes() { return totalEstimatedMinutes; }
    public void setTotalEstimatedMinutes(Integer totalEstimatedMinutes) { this.totalEstimatedMinutes = totalEstimatedMinutes; }

    public Double getAverageChapterDuration() { return averageChapterDuration; }
    public void setAverageChapterDuration(Double averageChapterDuration) { this.averageChapterDuration = averageChapterDuration; }
}