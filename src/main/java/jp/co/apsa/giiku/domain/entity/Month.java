package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 月エンティティ
 * カリキュラムの月情報を保持する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "months")
@Data
@EqualsAndHashCode(callSuper = true)
public class Month extends AuditableEntity {

    /** 月番号 */
    @NotNull
    @Column(name = "month_number", nullable = false, unique = true)
    private Integer monthNumber;

    /** 月タイトル */
    @NotBlank
    @Size(max = 255)
    @Column(name = "title", nullable = false)
    private String title;

    /** 説明 */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
