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
    /** LectureChapterCreateDto メソッド */
    public LectureChapterCreateDto() {}
    /** getLectureId メソッド */
    public Long getLectureId() { return lectureId; }
    /** setLectureId メソッド */
    public void setLectureId(Long lectureId) { this.lectureId = lectureId; }
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
}
