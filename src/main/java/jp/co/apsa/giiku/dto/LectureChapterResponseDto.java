package jp.co.apsa.giiku.dto;

import java.time.LocalDateTime;
/**
 * The LectureChapterResponseDto class.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class LectureChapterResponseDto {

    private Long id;
    private Long lectureId;
    private String lectureTitle;
    private String title;
    private String description;
    private Integer orderNumber;
    private Integer estimatedMinutes;
    private String contentUrl;
    private String contentType;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    /** LectureChapterResponseDto メソッド */
    public LectureChapterResponseDto() {}
    /** getId メソッド */
    public Long getId() { return id; }
    /** setId メソッド */
    public void setId(Long id) { this.id = id; }
    /** getLectureId メソッド */
    public Long getLectureId() { return lectureId; }
    /** setLectureId メソッド */
    public void setLectureId(Long lectureId) { this.lectureId = lectureId; }
    /** getLectureTitle メソッド */
    public String getLectureTitle() { return lectureTitle; }
    /** setLectureTitle メソッド */
    public void setLectureTitle(String lectureTitle) { this.lectureTitle = lectureTitle; }
    /** getTitle メソッド */
    public String getTitle() { return title; }
    /** setTitle メソッド */
    public void setTitle(String title) { this.title = title; }
    /** getDescription メソッド */
    public String getDescription() { return description; }
    /** setDescription メソッド */
    public void setDescription(String description) { this.description = description; }
    /** getOrderNumber メソッド */
    public Integer getOrderNumber() { return orderNumber; }
    /** setOrderNumber メソッド */
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }
    /** getEstimatedMinutes メソッド */
    public Integer getEstimatedMinutes() { return estimatedMinutes; }
    /** setEstimatedMinutes メソッド */
    public void setEstimatedMinutes(Integer estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }
    /** getContentUrl メソッド */
    public String getContentUrl() { return contentUrl; }
    /** setContentUrl メソッド */
    public void setContentUrl(String contentUrl) { this.contentUrl = contentUrl; }
    /** getContentType メソッド */
    public String getContentType() { return contentType; }
    /** setContentType メソッド */
    public void setContentType(String contentType) { this.contentType = contentType; }
    /** getIsActive メソッド */
    public Boolean getIsActive() { return isActive; }
    /** setIsActive メソッド */
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    /** getCreatedAt メソッド */
    public LocalDateTime getCreatedAt() { return createdAt; }
    /** setCreatedAt メソッド */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    /** getUpdatedAt メソッド */
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    /** setUpdatedAt メソッド */
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
