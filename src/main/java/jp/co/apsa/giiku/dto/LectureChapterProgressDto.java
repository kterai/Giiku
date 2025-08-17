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

    public LectureChapterProgressDto() {}

    public Long getChapterId() { return chapterId; }
    public void setChapterId(Long chapterId) { this.chapterId = chapterId; }

    public String getChapterTitle() { return chapterTitle; }
    public void setChapterTitle(String chapterTitle) { this.chapterTitle = chapterTitle; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public BigDecimal getProgressRate() { return progressRate; }
    public void setProgressRate(BigDecimal progressRate) { this.progressRate = progressRate; }

    /** completionRate エイリアス getter */
    public BigDecimal getCompletionRate() { return progressRate; }

    /** completionRate エイリアス setter */
    public void setCompletionRate(BigDecimal completionRate) { this.progressRate = completionRate; }

    public Boolean getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }

    public Integer getTimeSpentMinutes() { return timeSpentMinutes; }
    public void setTimeSpentMinutes(Integer timeSpentMinutes) { this.timeSpentMinutes = timeSpentMinutes; }

    public LocalDateTime getLastAccessTime() { return lastAccessTime; }
    public void setLastAccessTime(LocalDateTime lastAccessTime) { this.lastAccessTime = lastAccessTime; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}