/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.giiku.infrastructure.audit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.co.apsa.giiku.infrastructure.audit.Auditable;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.apsa.giiku.domain.entity.ApplicationType;
import jp.co.apsa.giiku.domain.port.AuditPort;
import jp.co.apsa.giiku.domain.valueobject.Priority;

/**
 * 監査ログサービス
 * 
 * <p>システム全体の監査ログを記録・管理するインフラストラクチャサービスです。
 * AOP（Aspect-Oriented Programming）を活用して、ビジネスロジックに影響を与えることなく
 * 包括的な監査ログを自動記録します。</p>
 * 
 * <p>このサービスは以下の機能を提供します：</p>
 * <ul>
 *   <li>データベース操作の自動ログ記録</li>
 *   <li>ビジネスイベントの追跡</li>
 *   <li>ユーザー操作の監査証跡</li>
 *   <li>セキュリティイベントの記録</li>
 *   <li>エラー・例外の詳細ログ</li>
 *   <li>JSON形式での構造化ログ</li>
 *   <li>非同期処理による性能最適化</li>
 *   <li>ログレベル管理</li>
 * </ul>
 * 
 * <p>Hexagonal ArchitectureのAdapterパターンを実装し、AuditPortインターフェースを実装します。
 * 監査要件やコンプライアンス要求に対応した包括的なログ管理を提供します。</p>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Aspect
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AuditLogService implements AuditPort {

    private static final Logger logger = LoggerFactory.getLogger(AuditLogService.class);
    private static final Logger auditLogger = LoggerFactory.getLogger("AUDIT");

    /**
     * 監査ログ有効フラグ
     */
    @Value("${audit.logging.enabled:true}")
    private boolean auditLoggingEnabled;

    /**
     * 監査ログレベル
     */
    @Value("${audit.logging.level:INFO}")
    private String auditLogLevel;

    /**
     * データベース操作ログ有効フラグ
     */
    @Value("${audit.database.enabled:true}")
    private boolean databaseAuditEnabled;

    /**
     * セキュリティログ有効フラグ
     */
    @Value("${audit.security.enabled:true}")
    private boolean securityAuditEnabled;

    /**
     * 非同期処理有効フラグ
     */
    @Value("${audit.async.enabled:true}")
    private boolean asyncProcessingEnabled;

    /**
     * ログ保持期間（日）
     */
    @Value("${audit.retention.days:365}")
    private int logRetentionDays;

    /**
     * アプリケーション名
     */
    @Value("${spring.application.name:giiku-system}")
    private String applicationName;

    private final ObjectMapper objectMapper;
    private final DateTimeFormatter dateTimeFormatter;

    /**
     * コンストラクタ
     * 
     * @param objectMapper JSON変換用マッパー
     */
    @Autowired
    public AuditLogService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * 申請作成の監査ログを記録します
     * 
     * @param applicationId 申請ID
     * @param applicantId 申請者ID
     * @param title 申請タイトル
     * @param applicationType 申請種別
     * @param priority 優先度
     * @param isUrgent 緊急フラグ
     * @param timestamp タイムスタンプ
     */
    @Override
    @Async
    public void recordApplicationCreation(Long applicationId, Long applicantId, String title, 
                                        ApplicationType applicationType, Priority priority, 
                                        Boolean isUrgent, LocalDateTime timestamp) {
        if (!auditLoggingEnabled) {
            return;
        }

        logger.debug("申請作成の監査ログを記録します。申請ID: {}", applicationId);

        try {
            AuditLogEntry logEntry = AuditLogEntry.builder()
                .eventType(AuditEventType.APPLICATION_CREATED)
                .entityType("Application")
                .entityId(applicationId.toString())
                .userId(applicantId.toString())
                .timestamp(timestamp)
                .details(createApplicationCreationDetails(applicationId, applicantId, title, 
                        applicationType, priority, isUrgent))
                .build();

            recordAuditLog(logEntry);

        } catch (Exception e) {
            logger.error("申請作成監査ログの記録中にエラーが発生しました。申請ID: {}, エラー: {}", 
                        applicationId, e.getMessage(), e);
        }
    }

    /**
     * 承認処理の監査ログを記録します
     * 
     * @param applicationId 申請ID
     * @param approverId 承認者ID
     * @param approvalStep 承認ステップ
     * @param decision 承認決定
     * @param comments コメント
     * @param timestamp タイムスタンプ
     */
    @Async
    public void recordApprovalAction(Long applicationId, Long approverId, Integer approvalStep, 
                                   String decision, String comments, LocalDateTime timestamp) {
        if (!auditLoggingEnabled) {
            return;
        }

        logger.debug("承認処理の監査ログを記録します。申請ID: {}, 承認者ID: {}", applicationId, approverId);

        try {
            AuditLogEntry logEntry = AuditLogEntry.builder()
                .eventType(AuditEventType.APPROVAL_ACTION)
                .entityType("ApplicationApproval")
                .entityId(applicationId.toString())
                .userId(approverId.toString())
                .timestamp(timestamp)
                .details(createApprovalActionDetails(applicationId, approverId, approvalStep, 
                        decision, comments))
                .build();

            recordAuditLog(logEntry);

        } catch (Exception e) {
            logger.error("承認処理監査ログの記録中にエラーが発生しました。申請ID: {}, エラー: {}", 
                        applicationId, e.getMessage(), e);
        }
    }

    /**
     * ユーザー操作の監査ログを記録します
     * 
     * @param userId ユーザーID
     * @param action 操作内容
     * @param entityType エンティティタイプ
     * @param entityId エンティティID
     * @param details 詳細情報
     */
    @Override
    @Async
    public void recordUserAction(String userId, String action, String entityType, 
                               String entityId, Map<String, Object> details) {
        if (!auditLoggingEnabled) {
            return;
        }

        logger.debug("ユーザー操作の監査ログを記録します。ユーザーID: {}, 操作: {}", userId, action);

        try {
            AuditLogEntry logEntry = AuditLogEntry.builder()
                .eventType(AuditEventType.USER_ACTION)
                .entityType(entityType)
                .entityId(entityId)
                .userId(userId)
                .action(action)
                .timestamp(LocalDateTime.now())
                .details(details != null ? details : new HashMap<>())
                .build();

            recordAuditLog(logEntry);

        } catch (Exception e) {
            logger.error("ユーザー操作監査ログの記録中にエラーが発生しました。ユーザーID: {}, エラー: {}", 
                        userId, e.getMessage(), e);
        }
    }

    /**
     * セキュリティイベントの監査ログを記録します
     * 
     * @param eventType イベントタイプ
     * @param userId ユーザーID
     * @param ipAddress IPアドレス
     * @param userAgent ユーザーエージェント
     * @param details 詳細情報
     */
    @Async
    public void recordSecurityEvent(String eventType, String userId, String ipAddress, 
                                  String userAgent, Map<String, Object> details) {
        if (!auditLoggingEnabled || !securityAuditEnabled) {
            return;
        }

        logger.info("セキュリティイベントの監査ログを記録します。イベント: {}, ユーザーID: {}", 
                   eventType, userId);

        try {
            Map<String, Object> securityDetails = new HashMap<>();
            securityDetails.put("ipAddress", ipAddress);
            securityDetails.put("userAgent", userAgent);
            if (details != null) {
                securityDetails.putAll(details);
            }

            AuditLogEntry logEntry = AuditLogEntry.builder()
                .eventType(AuditEventType.SECURITY_EVENT)
                .entityType("Security")
                .userId(userId)
                .action(eventType)
                .timestamp(LocalDateTime.now())
                .details(securityDetails)
                .severity(AuditSeverity.HIGH)
                .build();

            recordAuditLog(logEntry);

        } catch (Exception e) {
            logger.error("セキュリティイベント監査ログの記録中にエラーが発生しました。イベント: {}, エラー: {}", 
                        eventType, e.getMessage(), e);
        }
    }

    /**
     * データベース操作の監査ログを記録します
     * 
     * @param operation 操作種別
     * @param tableName テーブル名
     * @param recordId レコードID
     * @param userId ユーザーID
     * @param oldValues 変更前の値
     * @param newValues 変更後の値
     */
    @Async
    public void recordDatabaseOperation(String operation, String tableName, String recordId, 
                                      String userId, Map<String, Object> oldValues, 
                                      Map<String, Object> newValues) {
        if (!auditLoggingEnabled || !databaseAuditEnabled) {
            return;
        }

        logger.debug("データベース操作の監査ログを記録します。操作: {}, テーブル: {}", operation, tableName);

        try {
            Map<String, Object> dbDetails = new HashMap<>();
            dbDetails.put("operation", operation);
            dbDetails.put("tableName", tableName);
            dbDetails.put("recordId", recordId);
            if (oldValues != null) {
                dbDetails.put("oldValues", oldValues);
            }
            if (newValues != null) {
                dbDetails.put("newValues", newValues);
            }

            AuditLogEntry logEntry = AuditLogEntry.builder()
                .eventType(AuditEventType.DATABASE_OPERATION)
                .entityType(tableName)
                .entityId(recordId)
                .userId(userId)
                .action(operation)
                .timestamp(LocalDateTime.now())
                .details(dbDetails)
                .build();

            recordAuditLog(logEntry);

        } catch (Exception e) {
            logger.error("データベース操作監査ログの記録中にエラーが発生しました。操作: {}, エラー: {}", 
                        operation, e.getMessage(), e);
        }
    }

    /**
     * エラーイベントの監査ログを記録します
     * 
     * @param errorType エラータイプ
     * @param userId ユーザーID
     * @param errorMessage エラーメッセージ
     */
    @Override
    @Async
    public void recordError(String errorType, Long userId, String errorMessage) {
        if (!auditLoggingEnabled) {
            return;
        }

        logger.debug("エラーイベントの監査ログを記録します。エラータイプ: {}", errorType);

        try {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("errorType", errorType);
            errorDetails.put("errorMessage", errorMessage);
            errorDetails.put("stackTrace", Thread.currentThread().getStackTrace());

            AuditLogEntry logEntry = AuditLogEntry.builder()
                .eventType(AuditEventType.ERROR_EVENT)
                .entityType("Error")
                .userId(userId != null ? userId.toString() : "SYSTEM")
                .action(errorType)
                .timestamp(LocalDateTime.now())
                .details(errorDetails)
                .severity(AuditSeverity.HIGH)
                .build();

            recordAuditLog(logEntry);

        } catch (Exception e) {
            logger.error("エラーイベント監査ログの記録中にエラーが発生しました。エラータイプ: {}, エラー: {}", 
                        errorType, e.getMessage(), e);
        }
    }

    /**
     * AOP: メソッド実行後の監査ログ記録
     * 
     * @param joinPoint ジョインポイント
     * @param result 実行結果
     */
    @AfterReturning(pointcut = "@annotation(auditable)", returning = "result")
    public void logMethodExecution(JoinPoint joinPoint, Object result, Auditable auditable) {
        if (!auditLoggingEnabled) {
            return;
        }

        try {
            String methodName = joinPoint.getSignature().getName();
            String className = joinPoint.getTarget().getClass().getSimpleName();
            Object[] args = joinPoint.getArgs();

            Map<String, Object> methodDetails = new HashMap<>();
            methodDetails.put("className", className);
            methodDetails.put("methodName", methodName);
            methodDetails.put("arguments", args);
            methodDetails.put("result", result);
            if (auditable != null) {
                methodDetails.put("eventType", auditable.eventType());
            }

            AuditLogEntry logEntry = AuditLogEntry.builder()
                .eventType(AuditEventType.METHOD_EXECUTION)
                .entityType("Method")
                .action(className + "." + methodName)
                .timestamp(LocalDateTime.now())
                .details(methodDetails)
                .build();

            recordAuditLog(logEntry);

        } catch (Exception e) {
            logger.error("メソッド実行監査ログの記録中にエラーが発生しました: {}", e.getMessage(), e);
        }
    }

    /**
     * AOP: 例外発生時の監査ログ記録
     * 
     * @param joinPoint ジョインポイント
     * @param exception 発生した例外
     */
    @AfterThrowing(pointcut = "@annotation(auditable)",
                   throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Throwable exception, Auditable auditable) {
        if (!auditLoggingEnabled) {
            return;
        }

        try {
            String methodName = joinPoint.getSignature().getName();
            String className = joinPoint.getTarget().getClass().getSimpleName();

            Map<String, Object> exceptionDetails = new HashMap<>();
            exceptionDetails.put("className", className);
            exceptionDetails.put("methodName", methodName);
            exceptionDetails.put("exceptionType", exception.getClass().getSimpleName());
            exceptionDetails.put("exceptionMessage", exception.getMessage());
            if (auditable != null) {
                exceptionDetails.put("eventType", auditable.eventType());
            }

            AuditLogEntry logEntry = AuditLogEntry.builder()
                .eventType(AuditEventType.METHOD_EXCEPTION)
                .entityType("Exception")
                .action(className + "." + methodName)
                .timestamp(LocalDateTime.now())
                .details(exceptionDetails)
                .severity(AuditSeverity.HIGH)
                .build();

            recordAuditLog(logEntry);

        } catch (Exception e) {
            logger.error("メソッド例外監査ログの記録中にエラーが発生しました: {}", e.getMessage(), e);
        }
    }

    /**
     * 監査ログエントリを記録します
     * 
     * @param logEntry 監査ログエントリ
     */
    private void recordAuditLog(AuditLogEntry logEntry) {
        try {
            // ログエントリにメタデータを追加
            logEntry.setLogId(UUID.randomUUID().toString());
            logEntry.setApplicationName(applicationName);
            logEntry.setLogLevel(auditLogLevel);

            // JSON形式でログ出力
            String jsonLog = objectMapper.writeValueAsString(logEntry);

            // ログレベルに応じた出力
            switch (logEntry.getSeverity()) {
                case HIGH:
                    auditLogger.error("AUDIT: {}", jsonLog);
                    break;
                case MEDIUM:
                    auditLogger.warn("AUDIT: {}", jsonLog);
                    break;
                case LOW:
                default:
                    auditLogger.info("AUDIT: {}", jsonLog);
                    break;
            }

            logger.debug("監査ログが正常に記録されました。ログID: {}", logEntry.getLogId());

        } catch (Exception e) {
            logger.error("監査ログの記録中にエラーが発生しました: {}", e.getMessage(), e);
        }
    }

    /**
     * 申請作成詳細情報を作成します
     * 
     * @param applicationId 申請ID
     * @param applicantId 申請者ID
     * @param title 申請タイトル
     * @param applicationType 申請種別
     * @param priority 優先度
     * @param isUrgent 緊急フラグ
     * @return 詳細情報マップ
     */
    private Map<String, Object> createApplicationCreationDetails(Long applicationId, Long applicantId, 
                                                               String title, ApplicationType applicationType, 
                                                               Priority priority, Boolean isUrgent) {
        Map<String, Object> details = new HashMap<>();
        details.put("applicationId", applicationId);
        details.put("applicantId", applicantId);
        details.put("title", title);
        details.put("applicationType", applicationType.toString());
        details.put("priority", priority.toString());
        details.put("isUrgent", isUrgent);
        details.put("createdAt", LocalDateTime.now().format(dateTimeFormatter));
        return details;
    }

    /**
     * 承認処理詳細情報を作成します
     * 
     * @param applicationId 申請ID
     * @param approverId 承認者ID
     * @param approvalStep 承認ステップ
     * @param decision 承認決定
     * @param comments コメント
     * @return 詳細情報マップ
     */
    private Map<String, Object> createApprovalActionDetails(Long applicationId, Long approverId, 
                                                          Integer approvalStep, String decision, 
                                                          String comments) {
        Map<String, Object> details = new HashMap<>();
        details.put("applicationId", applicationId);
        details.put("approverId", approverId);
        details.put("approvalStep", approvalStep);
        details.put("decision", decision);
        details.put("comments", comments);
        details.put("processedAt", LocalDateTime.now().format(dateTimeFormatter));
        return details;
    }

    /**
     * 非同期で監査ログを記録します
     * 
     * @param logEntry 監査ログエントリ
     * @return CompletableFuture<Void>
     */
    public CompletableFuture<Void> recordAuditLogAsync(AuditLogEntry logEntry) {
        if (!asyncProcessingEnabled) {
            recordAuditLog(logEntry);
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.runAsync(() -> recordAuditLog(logEntry));
    }

    /**
     * 監査ログエントリクラス
     */
    public static class AuditLogEntry {
        private String logId;
        private String applicationName;
        private AuditEventType eventType;
        private String entityType;
        private String entityId;
        private String userId;
        private String action;
        private LocalDateTime timestamp;
        private Map<String, Object> details;
        private AuditSeverity severity = AuditSeverity.LOW;
        private String logLevel;

        // Builder pattern
        public static Builder builder() {
            return new Builder();
        }

        // Getters and Setters
        public String getLogId() { return logId; }
        public void setLogId(String logId) { this.logId = logId; }

        public String getApplicationName() { return applicationName; }
        public void setApplicationName(String applicationName) { this.applicationName = applicationName; }

        public AuditEventType getEventType() { return eventType; }
        public void setEventType(AuditEventType eventType) { this.eventType = eventType; }

        public String getEntityType() { return entityType; }
        public void setEntityType(String entityType) { this.entityType = entityType; }

        public String getEntityId() { return entityId; }
        public void setEntityId(String entityId) { this.entityId = entityId; }

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }

        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }

        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

        public Map<String, Object> getDetails() { return details; }
        public void setDetails(Map<String, Object> details) { this.details = details; }

        public AuditSeverity getSeverity() { return severity; }
        public void setSeverity(AuditSeverity severity) { this.severity = severity; }

        public String getLogLevel() { return logLevel; }
        public void setLogLevel(String logLevel) { this.logLevel = logLevel; }

        public static class Builder {
            private AuditLogEntry entry = new AuditLogEntry();

            public Builder eventType(AuditEventType eventType) {
                entry.eventType = eventType;
                return this;
            }

            public Builder entityType(String entityType) {
                entry.entityType = entityType;
                return this;
            }

            public Builder entityId(String entityId) {
                entry.entityId = entityId;
                return this;
            }

            public Builder userId(String userId) {
                entry.userId = userId;
                return this;
            }

            public Builder action(String action) {
                entry.action = action;
                return this;
            }

            public Builder timestamp(LocalDateTime timestamp) {
                entry.timestamp = timestamp;
                return this;
            }

            public Builder details(Map<String, Object> details) {
                entry.details = details;
                return this;
            }

            public Builder severity(AuditSeverity severity) {
                entry.severity = severity;
                return this;
            }

            public AuditLogEntry build() {
                return entry;
            }
        }
    }

    /**
     * 監査イベントタイプ列挙型
     */
    public enum AuditEventType {
        APPLICATION_CREATED,
        APPROVAL_ACTION,
        USER_ACTION,
        SECURITY_EVENT,
        DATABASE_OPERATION,
        METHOD_EXECUTION,
        METHOD_EXCEPTION,
        ERROR_EVENT,
        SYSTEM_EVENT
    }

    /**
     * 監査重要度列挙型
     */
    public enum AuditSeverity {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }
}
