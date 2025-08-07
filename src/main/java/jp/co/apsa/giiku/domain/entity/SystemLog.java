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
 * システムログエンティティクラス
 * 
 * アプリケーションの動作ログを管理します。
 * エラー、警告、情報レベルのログを記録し、
 * システムの監視、トラブルシューティング、パフォーマンス分析を支援します。
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "system_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemLog {

    /**
     * システムログID（主キー）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * ログレベル（ERROR, WARN, INFO, DEBUG, TRACE）
     */
    @NotBlank(message = "ログレベルは必須です")
    @Size(max = 10, message = "ログレベルは10文字以内で入力してください")
    @Column(name = "log_level", nullable = false, length = 10)
    private String logLevel;

    /**
     * ログカテゴリ（AUTHENTICATION, AUTHORIZATION, BUSINESS, SYSTEM, SECURITY等）
     */
    @Size(max = 50, message = "ログカテゴリは50文字以内で入力してください")
    @Column(name = "category", length = 50)
    private String category;

    /**
     * ログメッセージ
     */
    @NotBlank(message = "ログメッセージは必須です")
    @Column(name = "message", columnDefinition = "TEXT", nullable = false)
    private String message;

    /**
     * 詳細情報（JSON形式）
     */
    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    /**
     * 例外スタックトレース
     */
    @Column(name = "stack_trace", columnDefinition = "TEXT")
    private String stackTrace;

    /**
     * 関連ユーザーID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 関連ユーザー名
     */
    @Size(max = 100, message = "ユーザー名は100文字以内で入力してください")
    @Column(name = "username", length = 100)
    private String username;

    /**
     * セッションID
     */
    @Size(max = 100, message = "セッションIDは100文字以内で入力してください")
    @Column(name = "session_id", length = 100)
    private String sessionId;

    /**
     * リクエストID（トレーサビリティ用）
     */
    @Size(max = 100, message = "リクエストIDは100文字以内で入力してください")
    @Column(name = "request_id", length = 100)
    private String requestId;

    /**
     * IPアドレス
     */
    @Size(max = 45, message = "IPアドレスは45文字以内で入力してください")
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    /**
     * ユーザーエージェント
     */
    @Size(max = 500, message = "ユーザーエージェントは500文字以内で入力してください")
    @Column(name = "user_agent", length = 500)
    private String userAgent;

    /**
     * HTTPメソッド
     */
    @Size(max = 10, message = "HTTPメソッドは10文字以内で入力してください")
    @Column(name = "http_method", length = 10)
    private String httpMethod;

    /**
     * リクエストURL
     */
    @Size(max = 1000, message = "リクエストURLは1000文字以内で入力してください")
    @Column(name = "request_url", length = 1000)
    private String requestUrl;

    /**
     * HTTPステータスコード
     */
    @Column(name = "http_status")
    private Integer httpStatus;

    /**
     * 処理時間（ミリ秒）
     */
    @Column(name = "processing_time")
    private Long processingTime;

    /**
     * アプリケーション名
     */
    @Size(max = 50, message = "アプリケーション名は50文字以内で入力してください")
    @Column(name = "application_name", length = 50)
    private String applicationName;

    /**
     * モジュール名
     */
    @Size(max = 100, message = "モジュール名は100文字以内で入力してください")
    @Column(name = "module_name", length = 100)
    private String moduleName;

    /**
     * 機能名・メソッド名
     */
    @Size(max = 100, message = "機能名は100文字以内で入力してください")
    @Column(name = "function_name", length = 100)
    private String functionName;

    /**
     * サーバー名・ホスト名
     */
    @Size(max = 100, message = "サーバー名は100文字以内で入力してください")
    @Column(name = "server_name", length = 100)
    private String serverName;

    /**
     * スレッド名
     */
    @Size(max = 100, message = "スレッド名は100文字以内で入力してください")
    @Column(name = "thread_name", length = 100)
    private String threadName;

    /**
     * ログ発生日時
     */
    @NotNull(message = "ログ発生日時は必須です")
    @Column(name = "logged_at", nullable = false)
    private LocalDateTime loggedAt;

    /**
     * 作成日時（レコード作成日時）
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * エンティティ作成前の処理
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (loggedAt == null) {
            loggedAt = LocalDateTime.now();
        }
    }
}
