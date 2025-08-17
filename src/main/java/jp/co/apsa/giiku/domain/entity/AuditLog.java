/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.giiku.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 監査ログエンティティクラス
 *
 * データベースの変更履歴を記録する監査ログを管理します。
 * テーブルの操作（INSERT、UPDATE、DELETE）とその詳細を記録し、
 * データの変更追跡、セキュリティ監査、コンプライアンス対応を支援します。
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    /**
     * 監査ログID（主キー）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 対象テーブル名
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @NotBlank(message = "テーブル名は必須です")
    @Size(max = 100, message = "テーブル名は100文字以内で入力してください")
    @Column(name = "table_name", nullable = false, length = 100)
    private String tableName;

    /**
     * 操作種別（INSERT、UPDATE、DELETE）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @NotBlank(message = "操作種別は必須です")
    @Size(max = 10, message = "操作種別は10文字以内で入力してください")
    @Column(name = "operation", nullable = false, length = 10)
    private String operation;

    /**
     * イベントタイプ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 50, message = "イベントタイプは50文字以内で入力してください")
    @Column(name = "event_type", length = 50)
    private String eventType;

    /**
     * 対象レコードの主キー値
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 100, message = "主キー値は100文字以内で入力してください")
    @Column(name = "record_id", length = 100)
    private String recordId;

    /**
     * 変更前データ（JSON形式）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "old_values", columnDefinition = "TEXT")
    private String oldValues;

    /**
     * 変更後データ（JSON形式）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "new_values", columnDefinition = "TEXT")
    private String newValues;

    /**
     * 変更されたカラム名（カンマ区切り）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 1000, message = "変更カラム名は1000文字以内で入力してください")
    @Column(name = "changed_columns", length = 1000)
    private String changedColumns;

    /**
     * 操作実行ユーザーID
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 操作実行ユーザー名
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 100, message = "ユーザー名は100文字以内で入力してください")
    @Column(name = "username", length = 100)
    private String username;

    /**
     * セッションID
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 100, message = "セッションIDは100文字以内で入力してください")
    @Column(name = "session_id", length = 100)
    private String sessionId;

    /**
     * IPアドレス
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 45, message = "IPアドレスは45文字以内で入力してください")
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    /**
     * ユーザーエージェント
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 500, message = "ユーザーエージェントは500文字以内で入力してください")
    @Column(name = "user_agent", length = 500)
    private String userAgent;

    /**
     * 操作理由・備考
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 1000, message = "操作理由は1000文字以内で入力してください")
    @Column(name = "reason", length = 1000)
    private String reason;

    /**
     * トランザクションID
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 100, message = "トランザクションIDは100文字以内で入力してください")
    @Column(name = "transaction_id", length = 100)
    private String transactionId;

    /**
     * 操作実行日時
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @NotNull(message = "操作実行日時は必須です")
    @Column(name = "executed_at", nullable = false)
    private LocalDateTime executedAt;

    /**
     * アプリケーション名
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 50, message = "アプリケーション名は50文字以内で入力してください")
    @Column(name = "application_name", length = 50)
    private String applicationName;

    /**
     * 機能名・画面名
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 100, message = "機能名は100文字以内で入力してください")
    @Column(name = "function_name", length = 100)
    private String functionName;

    /**
     * 重要度レベル（LOW, MEDIUM, HIGH, CRITICAL）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 10, message = "重要度レベルは10文字以内で入力してください")
    @Column(name = "severity_level", length = 10)
    private String severityLevel;

    /**
     * 作成日時（ログ記録日時）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * エンティティ作成前の処理
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (executedAt == null) {
            executedAt = LocalDateTime.now();
        }
    }
}
