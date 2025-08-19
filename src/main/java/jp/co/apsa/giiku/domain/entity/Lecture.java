package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 講義エンティティ
 * 講義の基本情報を保持する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "lectures")
@Data
@EqualsAndHashCode(callSuper = true)
public class Lecture extends BaseEntity {

    /** 講義タイトル */
    @NotBlank
    @Size(max = 200)
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /** 講義概要 */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** 所要時間（分） */
    @Min(1)
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    /** 難易度レベル */
    @Size(max = 50)
    @Column(name = "difficulty_level", length = 50)
    private String difficultyLevel;

    /** 有効フラグ */
    @Column(name = "is_active")
    private Boolean isActive = true;
}
