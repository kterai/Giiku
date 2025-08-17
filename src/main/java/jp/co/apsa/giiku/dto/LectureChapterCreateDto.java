package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;
/**
 * The LectureChapterCreateDto class.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */

public class LectureChapterCreateDto {

    @NotNull
    private Long lectureId;

    @NotBlank
    @Size(max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotNull
    @Min(1)
    private Integer orderNumber;

    @Min(1)
    private Integer estimatedMinutes;

    @Size(max = 500)
    private String contentUrl;

    @Size(max = 50)
    private String contentType;
    /**
     * LectureChapterCreateDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LectureChapterCreateDto() {}
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
}
