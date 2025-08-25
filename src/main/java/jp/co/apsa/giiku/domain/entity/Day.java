package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日エンティティ
 * 週に紐づく日情報を保持する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "days")
@Data
@EqualsAndHashCode(callSuper = true)
public class Day extends AuditableEntity {

    /** 週ID */
    @NotNull
    @Column(name = "week_id", nullable = false)
    private Long weekId;

    /** 日番号 */
    @NotNull
    @Column(name = "day_number", nullable = false)
    private Integer dayNumber;

    /** 日名称 */
    @NotBlank
    @Size(max = 100)
    @Column(name = "day_name", nullable = false)
    private String dayName;

    /** 説明 */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
