package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 監査情報を保持する基底クラス
 * 共通の作成者・更新者・作成日時・更新日時を提供する
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AuditableEntity extends BaseEntity {

    /**
     * 作成者ID
     * レコードを作成したユーザーのID
     */
    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    /**
     * 作成日時
     * レコードが作成された日時
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 更新者ID
     * レコードを最後に更新したユーザーのID
     */
    @Column(name = "updated_by")
    private Long updatedBy;

    /**
     * 更新日時
     * レコードが最後に更新された日時
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
