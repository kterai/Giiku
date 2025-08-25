package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 週エンティティ
 * 月に紐づく週の情報を保持する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "weeks")
@Data
@EqualsAndHashCode(callSuper = true)
public class Week extends AuditableEntity {

    /** 月ID */
    @NotNull
    @Column(name = "month_id", nullable = false)
    private Long monthId;

    /** 週番号 */
    @NotNull
    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;

    /** 週名称 */
    @NotBlank
    @Size(max = 100)
    @Column(name = "week_name", nullable = false)
    private String weekName;

    /** 説明 */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
