package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 講義とチャプターの関連エンティティ
 * 講義とチャプターの紐付けと並び順を管理する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "lecture_chapter_links")
@Data
@EqualsAndHashCode(callSuper = true)
public class LectureChapterLink extends AuditableEntity {

    /** 講義ID */
    @NotNull
    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    /** 紐付くチャプター */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    /** 表示順 */
    @Column(name = "sort_order")
    private Integer sortOrder;
}
