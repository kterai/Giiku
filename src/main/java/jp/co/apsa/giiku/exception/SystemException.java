package jp.co.apsa.giiku.exception;

/**
 * システムレベルのエラーが発生した際に投げられる例外クラス
 * データベースエラー、設定エラー、外部システム連携エラーなどを扱います
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCode;
    private final String systemMessage;
    private final String component;
    private final String operation;

    /**
     * デフォルトコンストラクタ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public SystemException() {
        super("System error occurred");
        this.errorCode = "SYSTEM_ERROR";
        this.systemMessage = null;
        this.component = null;
        this.operation = null;
    }

    /**
     * メッセージ付きコンストラクタ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public SystemException(String message) {
        super(message);
        this.errorCode = "SYSTEM_ERROR";
        this.systemMessage = null;
        this.component = null;
        this.operation = null;
    }

    /**
     * メッセージと原因付きコンストラクタ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "SYSTEM_ERROR";
        this.systemMessage = null;
        this.component = null;
        this.operation = null;
    }

    /**
     * エラーコード付きコンストラクタ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public SystemException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.systemMessage = null;
        this.component = null;
        this.operation = null;
    }

    /**
     * エラーコードとシステムメッセージ付きコンストラクタ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public SystemException(String errorCode, String message, String systemMessage) {
        super(message);
        this.errorCode = errorCode;
        this.systemMessage = systemMessage;
        this.component = null;
        this.operation = null;
    }

    /**
     * 完全コンストラクタ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public SystemException(String errorCode, String message, String systemMessage, 
                          String component, String operation) {
        super(message);
        this.errorCode = errorCode;
        this.systemMessage = systemMessage;
        this.component = component;
        this.operation = operation;
    }

    /**
     * 完全コンストラクタ（原因付き）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public SystemException(String errorCode, String message, String systemMessage, 
                          String component, String operation, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.systemMessage = systemMessage;
        this.component = component;
        this.operation = operation;
    }

    /**
     * エラーコードを取得
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * システムメッセージを取得
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public String getSystemMessage() {
        return systemMessage;
    }

    /**
     * コンポーネント名を取得
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public String getComponent() {
        return component;
    }

    /**
     * 操作名を取得
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public String getOperation() {
        return operation;
    }

    /**
     * データベースエラー用ファクトリメソッド
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static SystemException databaseError(String message, Throwable cause) {
        return new SystemException("DB_ERROR", message, "Database operation failed", 
                                 "DatabaseLayer", "SQLExecution", cause);
    }

    /**
     * データベース接続エラー用ファクトリメソッド
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static SystemException databaseConnectionError(Throwable cause) {
        return new SystemException("DB_CONNECTION_ERROR", "Database connection failed", 
                                 "Unable to establish database connection", 
                                 "DatabaseLayer", "Connection", cause);
    }

    /**
     * 設定エラー用ファクトリメソッド
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static SystemException configurationError(String message) {
        return new SystemException("CONFIG_ERROR", message, "Configuration error detected", 
                                 "ConfigurationManager", "LoadConfig");
    }

    /**
     * 設定ファイル不存在エラー用ファクトリメソッド
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static SystemException configurationFileNotFound(String configFile) {
        return new SystemException("CONFIG_FILE_NOT_FOUND", 
                                 "Configuration file not found: " + configFile,
                                 "Required configuration file is missing",
                                 "ConfigurationManager", "LoadFile");
    }

    /**
     * 外部システムエラー用ファクトリメソッド
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static SystemException externalSystemError(String message, Throwable cause) {
        return new SystemException("EXTERNAL_ERROR", message, 
                                 "External system communication failed", 
                                 "ExternalSystemClient", "APICall", cause);
    }

    /**
     * APIタイムアウトエラー用ファクトリメソッド
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static SystemException apiTimeoutError(String apiName, int timeoutSeconds) {
        return new SystemException("API_TIMEOUT", 
                                 "API call timeout: " + apiName + " (" + timeoutSeconds + "s)",
                                 "External API did not respond within timeout period",
                                 "APIClient", apiName);
    }

    /**
     * リソース不足エラー用ファクトリメソッド
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static SystemException resourceExhausted(String resourceType, String details) {
        return new SystemException("RESOURCE_EXHAUSTED", 
                                 "Resource exhausted: " + resourceType,
                                 details,
                                 "SystemResourceManager", "ResourceAllocation");
    }

    /**
     * ファイルシステムエラー用ファクトリメソッド
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static SystemException fileSystemError(String operation, String path, Throwable cause) {
        return new SystemException("FILE_SYSTEM_ERROR", 
                                 "File system operation failed: " + operation + " on " + path,
                                 "Unable to perform file system operation",
                                 "FileSystemManager", operation, cause);
    }

    /**
     * セキュリティエラー用ファクトリメソッド
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static SystemException securityError(String message, String operation) {
        return new SystemException("SECURITY_ERROR", message,
                                 "Security violation detected",
                                 "SecurityManager", operation);
    }

    /**
     * 詳細情報を含む文字列表現を返す
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SystemException: ");
        sb.append(getMessage());

        if (errorCode != null) {
            sb.append(" [errorCode=").append(errorCode).append("]");
        }

        if (component != null) {
            sb.append(" [component=").append(component).append("]");
        }

        if (operation != null) {
            sb.append(" [operation=").append(operation).append("]");
        }

        if (systemMessage != null) {
            sb.append(" [systemMessage=").append(systemMessage).append("]");
        }

        return sb.toString();
    }
}
