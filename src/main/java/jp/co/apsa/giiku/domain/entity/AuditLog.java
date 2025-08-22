/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 監査ログエンティティクラス
 *
 * データベースの操作履歴を管理する監査ログテーブルに対応するエンティティ。
 * INSERT、UPDATE、DELETE の各操作と変更内容を記録する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
public class AuditLog {

    /** 監査ログID（主キー） */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 対象テーブル名 */
    @NotBlank
    @Size(max = 100)
    @Column(name = "table_name", nullable = false, length = 100)
    private String tableName;

    /** 操作種別（INSERT、UPDATE、DELETE） */
    @NotBlank
    @Size(max = 20)
    @Column(name = "operation_type", nullable = false, length = 20)
    private String operationType;

    /** 対象レコードID */
    @Column(name = "record_id")
    private Long recordId;

    /** 変更前データ（JSON形式） */
    @Column(name = "old_values", columnDefinition = "jsonb")
    private String oldValues;

    /** 変更後データ（JSON形式） */
    @Column(name = "new_values", columnDefinition = "jsonb")
    private String newValues;

    /** 変更者 */
    @Size(max = 100)
    @Column(name = "changed_by", length = 100)
    private String changedBy;

    /** 変更日時 */
    @Column(name = "change_timestamp")
    private LocalDateTime changeTimestamp;

    /** 作成者ID */
    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    /** 作成日時 */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /** 更新者ID */
    @NotNull
    @Column(name = "updated_by", nullable = false)
    private Long updatedBy;

    /** 更新日時 */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /** エンティティ作成前処理 */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        if (this.changeTimestamp == null) {
            this.changeTimestamp = this.createdAt;
        }
    }

    /** エンティティ更新前処理 */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
