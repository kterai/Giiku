package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;

/**
 * 講義チャプター更新リクエスト DTO。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class LectureChapterUpdateDto {

    /** チャプター番号 */
    @Min(1)
    private Integer chapterNumber;

    /** タイトル */
    @Size(max = 200)
    private String title;

    /** 説明 */
    @Size(max = 1000)
    private String description;

    /** 所要時間（分） */
    @Min(1)
    private Integer durationMinutes;

    /** 表示順 */
    private Integer sortOrder;

    /** 有効フラグ */
    private Boolean isActive;

    /** デフォルトコンストラクタ */
    public LectureChapterUpdateDto() {}

    public Integer getChapterNumber() { return chapterNumber; }

    public void setChapterNumber(Integer chapterNumber) { this.chapterNumber = chapterNumber; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Integer getDurationMinutes() { return durationMinutes; }

    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public Integer getSortOrder() { return sortOrder; }

    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public Boolean getIsActive() { return isActive; }

    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
