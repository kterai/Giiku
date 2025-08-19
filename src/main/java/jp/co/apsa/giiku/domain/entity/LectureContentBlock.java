package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 講義コンテンツブロックエンティティ
 * チャプター内の個別コンテンツを保持する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "lecture_content_blocks")
@Data
@EqualsAndHashCode(callSuper = true)
public class LectureContentBlock extends BaseEntity {

    /** チャプターID */
    @NotNull
    @Column(name = "chapter_id", nullable = false)
    private Long chapterId;

    /** ブロック種別 */
    @NotBlank
    @Size(max = 50)
    @Column(name = "block_type", nullable = false, length = 50)
    private String blockType;

    /** ブロックタイトル */
    @NotBlank
    @Size(max = 200)
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /** ブロック内容 */
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    /** 表示順 */
    @Column(name = "sort_order")
    private Integer sortOrder;
}
