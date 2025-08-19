package jp.co.apsa.giiku.dto;

/**
 * 講義チャプター検索用 DTO。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class LectureChapterSearchDto {
    private Long lectureId;
    private String title;
    private String description;
    private Boolean isActive;

    /** デフォルトコンストラクタ */
    public LectureChapterSearchDto() {}

    public Long getLectureId() { return lectureId; }

    public void setLectureId(Long lectureId) { this.lectureId = lectureId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Boolean getIsActive() { return isActive; }

    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
