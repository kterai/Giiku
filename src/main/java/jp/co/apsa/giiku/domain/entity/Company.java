package jp.co.apsa.giiku.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 会社エンティティクラス。
 */
@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Company {

    /** 会社ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 会社名 */
    @NotBlank(message = "会社名は必須です")
    @Size(max = 100, message = "会社名は100文字以内で入力してください")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /** 会社コード */
    @Size(max = 20, message = "会社コードは20文字以内で入力してください")
    @Column(name = "code", unique = true, length = 20)
    private String code;

    /** 有効フラグ */
    @NotNull(message = "有効フラグは必須です")
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    /** 作成者ID */
    @CreatedBy
    @NotNull(message = "作成者IDは必須です")
    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    /** 作成日時 */
    @CreatedDate
    @NotNull(message = "作成日時は必須です")
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /** 更新者ID */
    @LastModifiedBy
    @NotNull(message = "更新者IDは必須です")
    @Column(name = "updated_by")
    private Long updatedBy;

    /** 更新日時 */
    @LastModifiedDate
    @NotNull(message = "更新日時は必須です")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
