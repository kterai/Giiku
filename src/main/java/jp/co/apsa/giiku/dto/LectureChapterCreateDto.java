package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;

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

    public LectureChapterCreateDto() {}

    public Long getLectureId() { return lectureId; }
    public void setLectureId(Long lectureId) { this.lectureId = lectureId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getOrderNumber() { return orderNumber; }
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }

    public Integer getEstimatedMinutes() { return estimatedMinutes; }
    public void setEstimatedMinutes(Integer estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }

    public String getContentUrl() { return contentUrl; }
    public void setContentUrl(String contentUrl) { this.contentUrl = contentUrl; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
}