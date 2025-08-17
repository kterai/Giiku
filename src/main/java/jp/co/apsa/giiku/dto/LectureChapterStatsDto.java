package jp.co.apsa.giiku.dto;

/**
 * LectureChapterStatsDto.
 *
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
    /**
     * LectureChapterStatsDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LectureChapterStatsDto() {}
    /**
     * getTotalChapters メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getTotalChapters() { return totalChapters; }
    /**
     * setTotalChapters メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTotalChapters(Long totalChapters) { this.totalChapters = totalChapters; }
    /**
     * getActiveChapters メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getActiveChapters() { return activeChapters; }
    /**
     * setActiveChapters メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setActiveChapters(Long activeChapters) { this.activeChapters = activeChapters; }
    /**
     * getInactiveChapters メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getInactiveChapters() { return inactiveChapters; }
    /**
     * setInactiveChapters メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setInactiveChapters(Long inactiveChapters) { this.inactiveChapters = inactiveChapters; }
    /**
     * getTotalEstimatedMinutes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getTotalEstimatedMinutes() { return totalEstimatedMinutes; }
    /**
     * setTotalEstimatedMinutes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTotalEstimatedMinutes(Integer totalEstimatedMinutes) { this.totalEstimatedMinutes = totalEstimatedMinutes; }
    /**
     * getAverageChapterDuration メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Double getAverageChapterDuration() { return averageChapterDuration; }
    /**
     * setAverageChapterDuration メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setAverageChapterDuration(Double averageChapterDuration) { this.averageChapterDuration = averageChapterDuration; }
    /**
     * getAverageCompletionRate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Double getAverageCompletionRate() { return averageCompletionRate; }
    /**
     * setAverageCompletionRate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setAverageCompletionRate(Double averageCompletionRate) { this.averageCompletionRate = averageCompletionRate; }
}
