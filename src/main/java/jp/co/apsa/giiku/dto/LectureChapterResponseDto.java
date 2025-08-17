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
    /**
     * LectureChapterResponseDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LectureChapterResponseDto() {}
    /**
     * getId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getId() { return id; }
    /**
     * setId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setId(Long id) { this.id = id; }
    /**
     * getLectureId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getLectureId() { return lectureId; }
    /**
     * setLectureId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setLectureId(Long lectureId) { this.lectureId = lectureId; }
    /**
     * getLectureTitle メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getLectureTitle() { return lectureTitle; }
    /**
     * setLectureTitle メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setLectureTitle(String lectureTitle) { this.lectureTitle = lectureTitle; }
    /**
     * getTitle メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getTitle() { return title; }
    /**
     * setTitle メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTitle(String title) { this.title = title; }
    /**
     * getDescription メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getDescription() { return description; }
    /**
     * setDescription メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setDescription(String description) { this.description = description; }
    /**
     * getOrderNumber メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getOrderNumber() { return orderNumber; }
    /**
     * setOrderNumber メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }
    /**
     * getEstimatedMinutes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getEstimatedMinutes() { return estimatedMinutes; }
    /**
     * setEstimatedMinutes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setEstimatedMinutes(Integer estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }
    /**
     * getContentUrl メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getContentUrl() { return contentUrl; }
    /**
     * setContentUrl メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setContentUrl(String contentUrl) { this.contentUrl = contentUrl; }
    /**
     * getContentType メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getContentType() { return contentType; }
    /**
     * setContentType メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setContentType(String contentType) { this.contentType = contentType; }
    /**
     * getIsActive メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Boolean getIsActive() { return isActive; }
    /**
     * setIsActive メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    /**
     * getCreatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getCreatedAt() { return createdAt; }
    /**
     * setCreatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    /**
     * getUpdatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    /**
     * setUpdatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
