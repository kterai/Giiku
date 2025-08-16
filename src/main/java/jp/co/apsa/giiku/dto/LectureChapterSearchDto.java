package jp.co.apsa.giiku.dto;

public class LectureChapterSearchDto {
    private Long lectureId;
    private String title;
    private String contentType;
    private Boolean isActive;

    public LectureChapterSearchDto() {}

    public Long getLectureId() { return lectureId; }
    public void setLectureId(Long lectureId) { this.lectureId = lectureId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}