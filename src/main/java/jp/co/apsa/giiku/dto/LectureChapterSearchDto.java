package jp.co.apsa.giiku.dto;

/**
 * LectureChapterSearchDto.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class LectureChapterSearchDto {
    private Long lectureId;
    private String title;
    private String contentType;
    private Boolean isActive;
    private String description;
    private String status;
    private String difficulty;

    public LectureChapterSearchDto() {}

    public Long getLectureId() { return lectureId; }
    public void setLectureId(Long lectureId) { this.lectureId = lectureId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
}