package jp.co.apsa.giiku.dto;

/**
 * LectureChapterSearchDto.
 *
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
    /**
     * LectureChapterSearchDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LectureChapterSearchDto() {}
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
     * getStatus メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getStatus() { return status; }
    /**
     * setStatus メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStatus(String status) { this.status = status; }
    /**
     * getDifficulty メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getDifficulty() { return difficulty; }
    /**
     * setDifficulty メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
}
