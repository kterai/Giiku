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
    /** LectureChapterSearchDto メソッド */
    public LectureChapterSearchDto() {}
    /** getLectureId メソッド */
    public Long getLectureId() { return lectureId; }
    /** setLectureId メソッド */
    public void setLectureId(Long lectureId) { this.lectureId = lectureId; }
    /** getTitle メソッド */
    public String getTitle() { return title; }
    /** setTitle メソッド */
    public void setTitle(String title) { this.title = title; }
    /** getContentType メソッド */
    public String getContentType() { return contentType; }
    /** setContentType メソッド */
    public void setContentType(String contentType) { this.contentType = contentType; }
    /** getIsActive メソッド */
    public Boolean getIsActive() { return isActive; }
    /** setIsActive メソッド */
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    /** getDescription メソッド */
    public String getDescription() { return description; }
    /** setDescription メソッド */
    public void setDescription(String description) { this.description = description; }
    /** getStatus メソッド */
    public String getStatus() { return status; }
    /** setStatus メソッド */
    public void setStatus(String status) { this.status = status; }
    /** getDifficulty メソッド */
    public String getDifficulty() { return difficulty; }
    /** setDifficulty メソッド */
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
}
