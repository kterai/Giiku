package jp.co.apsa.giiku.dto;

import java.time.LocalDateTime;

/**
 * 講義チャプター応答 DTO。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class LectureChapterResponseDto {

    private Long id;
    private Long lectureId;
    private Integer chapterNumber;
    private String title;
    private String description;
    private Integer durationMinutes;
    private Integer sortOrder;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /** デフォルトコンストラクタ */
    public LectureChapterResponseDto() {}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getLectureId() { return lectureId; }

    public void setLectureId(Long lectureId) { this.lectureId = lectureId; }

    public Integer getChapterNumber() { return chapterNumber; }

    public void setChapterNumber(Integer chapterNumber) { this.chapterNumber = chapterNumber; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Integer getDurationMinutes() { return durationMinutes; }

    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public Integer getSortOrder() { return sortOrder; }

    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public Boolean getIsActive() { return isActive; }

    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
