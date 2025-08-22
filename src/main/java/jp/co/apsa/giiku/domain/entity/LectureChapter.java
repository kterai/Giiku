package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 講義チャプターエンティティ
 * 各講義に紐づくチャプター情報を保持する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "lecture_chapters")
@Data
@EqualsAndHashCode(callSuper = true)
public class LectureChapter extends AuditableEntity {

    /** 講義ID */
    @NotNull
    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    /** チャプター番号 */
    @NotNull
    @Min(1)
    @Column(name = "chapter_number", nullable = false)
    private Integer chapterNumber;

    /** チャプタータイトル */
    @NotBlank
    @Size(max = 200)
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /** チャプター説明 */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** チャプター所要時間（分） */
    @Min(1)
    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    /** 表示順 */
    @Column(name = "sort_order")
    private Integer sortOrder;

    /** 有効フラグ */
    @Column(name = "is_active")
    private Boolean isActive = true;
}
