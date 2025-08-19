package jp.co.apsa.giiku.dto;

/**
 * 講義チャプター統計 DTO。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class LectureChapterStatsDto {
    private Long totalChapters;
    private Long activeChapters;
    private Long inactiveChapters;
    private Integer totalDurationMinutes;
    private Double averageChapterDuration;
    private Double averageCompletionRate;

    /** デフォルトコンストラクタ */
    public LectureChapterStatsDto() {}

    public Long getTotalChapters() { return totalChapters; }

    public void setTotalChapters(Long totalChapters) { this.totalChapters = totalChapters; }

    public Long getActiveChapters() { return activeChapters; }

    public void setActiveChapters(Long activeChapters) { this.activeChapters = activeChapters; }

    public Long getInactiveChapters() { return inactiveChapters; }

    public void setInactiveChapters(Long inactiveChapters) { this.inactiveChapters = inactiveChapters; }

    public Integer getTotalDurationMinutes() { return totalDurationMinutes; }

    public void setTotalDurationMinutes(Integer totalDurationMinutes) { this.totalDurationMinutes = totalDurationMinutes; }

    public Double getAverageChapterDuration() { return averageChapterDuration; }

    public void setAverageChapterDuration(Double averageChapterDuration) { this.averageChapterDuration = averageChapterDuration; }

    public Double getAverageCompletionRate() { return averageCompletionRate; }

    public void setAverageCompletionRate(Double averageCompletionRate) { this.averageCompletionRate = averageCompletionRate; }
}
