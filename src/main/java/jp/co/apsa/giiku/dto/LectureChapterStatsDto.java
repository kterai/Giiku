package jp.co.apsa.giiku.dto;

/**
 * LectureChapterStatsDto.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class LectureChapterStatsDto {
    private Long totalChapters;
    private Long activeChapters;
    private Long inactiveChapters;
    private Integer totalEstimatedMinutes;
    private Double averageChapterDuration;
    private Double averageCompletionRate;
    /** LectureChapterStatsDto メソッド */
    public LectureChapterStatsDto() {}
    /** getTotalChapters メソッド */
    public Long getTotalChapters() { return totalChapters; }
    /** setTotalChapters メソッド */
    public void setTotalChapters(Long totalChapters) { this.totalChapters = totalChapters; }
    /** getActiveChapters メソッド */
    public Long getActiveChapters() { return activeChapters; }
    /** setActiveChapters メソッド */
    public void setActiveChapters(Long activeChapters) { this.activeChapters = activeChapters; }
    /** getInactiveChapters メソッド */
    public Long getInactiveChapters() { return inactiveChapters; }
    /** setInactiveChapters メソッド */
    public void setInactiveChapters(Long inactiveChapters) { this.inactiveChapters = inactiveChapters; }
    /** getTotalEstimatedMinutes メソッド */
    public Integer getTotalEstimatedMinutes() { return totalEstimatedMinutes; }
    /** setTotalEstimatedMinutes メソッド */
    public void setTotalEstimatedMinutes(Integer totalEstimatedMinutes) { this.totalEstimatedMinutes = totalEstimatedMinutes; }
    /** getAverageChapterDuration メソッド */
    public Double getAverageChapterDuration() { return averageChapterDuration; }
    /** setAverageChapterDuration メソッド */
    public void setAverageChapterDuration(Double averageChapterDuration) { this.averageChapterDuration = averageChapterDuration; }
    /** getAverageCompletionRate メソッド */
    public Double getAverageCompletionRate() { return averageCompletionRate; }
    /** setAverageCompletionRate メソッド */
    public void setAverageCompletionRate(Double averageCompletionRate) { this.averageCompletionRate = averageCompletionRate; }
}
