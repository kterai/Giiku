package jp.co.apsa.giiku.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * LectureChapterProgressDto.
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class LectureChapterProgressDto {
    private Long chapterId;
    private String chapterTitle;
    private Long studentId;
    private String studentName;
    private BigDecimal progressRate;
    private Boolean isCompleted;
    private Integer timeSpentMinutes;
    private LocalDateTime lastAccessTime;
    private LocalDateTime completedAt;
    /**
     * LectureChapterProgressDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LectureChapterProgressDto() {}
    /**
     * getChapterId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getChapterId() { return chapterId; }
    /**
     * setChapterId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setChapterId(Long chapterId) { this.chapterId = chapterId; }
    /**
     * getChapterTitle メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getChapterTitle() { return chapterTitle; }
    /**
     * setChapterTitle メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setChapterTitle(String chapterTitle) { this.chapterTitle = chapterTitle; }
    /**
     * getStudentId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getStudentId() { return studentId; }
    /**
     * setStudentId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    /**
     * getStudentName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getStudentName() { return studentName; }
    /**
     * setStudentName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStudentName(String studentName) { this.studentName = studentName; }
    /**
     * getProgressRate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getProgressRate() { return progressRate; }
    /**
     * setProgressRate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setProgressRate(BigDecimal progressRate) { this.progressRate = progressRate; }

    /** completionRate エイリアス getter 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public BigDecimal getCompletionRate() { return progressRate; }

    /** completionRate エイリアス setter 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void setCompletionRate(BigDecimal completionRate) { this.progressRate = completionRate; }
    /**
     * getIsCompleted メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Boolean getIsCompleted() { return isCompleted; }
    /**
     * setIsCompleted メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }
    /**
     * getTimeSpentMinutes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getTimeSpentMinutes() { return timeSpentMinutes; }
    /**
     * setTimeSpentMinutes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTimeSpentMinutes(Integer timeSpentMinutes) { this.timeSpentMinutes = timeSpentMinutes; }
    /**
     * getLastAccessTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getLastAccessTime() { return lastAccessTime; }
    /**
     * setLastAccessTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setLastAccessTime(LocalDateTime lastAccessTime) { this.lastAccessTime = lastAccessTime; }
    /**
     * getCompletedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getCompletedAt() { return completedAt; }
    /**
     * setCompletedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}
