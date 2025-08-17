package jp.co.apsa.giiku.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * LectureChapterProgressDto.
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
    /** LectureChapterProgressDto メソッド */
    public LectureChapterProgressDto() {}
    /** getChapterId メソッド */
    public Long getChapterId() { return chapterId; }
    /** setChapterId メソッド */
    public void setChapterId(Long chapterId) { this.chapterId = chapterId; }
    /** getChapterTitle メソッド */
    public String getChapterTitle() { return chapterTitle; }
    /** setChapterTitle メソッド */
    public void setChapterTitle(String chapterTitle) { this.chapterTitle = chapterTitle; }
    /** getStudentId メソッド */
    public Long getStudentId() { return studentId; }
    /** setStudentId メソッド */
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    /** getStudentName メソッド */
    public String getStudentName() { return studentName; }
    /** setStudentName メソッド */
    public void setStudentName(String studentName) { this.studentName = studentName; }
    /** getProgressRate メソッド */
    public BigDecimal getProgressRate() { return progressRate; }
    /** setProgressRate メソッド */
    public void setProgressRate(BigDecimal progressRate) { this.progressRate = progressRate; }

    public BigDecimal getCompletionRate() { return progressRate; }

    public void setCompletionRate(BigDecimal completionRate) { this.progressRate = completionRate; }
    /** getIsCompleted メソッド */
    public Boolean getIsCompleted() { return isCompleted; }
    /** setIsCompleted メソッド */
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }
    /** getTimeSpentMinutes メソッド */
    public Integer getTimeSpentMinutes() { return timeSpentMinutes; }
    /** setTimeSpentMinutes メソッド */
    public void setTimeSpentMinutes(Integer timeSpentMinutes) { this.timeSpentMinutes = timeSpentMinutes; }
    /** getLastAccessTime メソッド */
    public LocalDateTime getLastAccessTime() { return lastAccessTime; }
    /** setLastAccessTime メソッド */
    public void setLastAccessTime(LocalDateTime lastAccessTime) { this.lastAccessTime = lastAccessTime; }
    /** getCompletedAt メソッド */
    public LocalDateTime getCompletedAt() { return completedAt; }
    /** setCompletedAt メソッド */
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}
