package jp.co.apsa.giiku.exception;

/**
 * バリデーションエラーが発生した際に投げられる例外クラス
 * フィールドレベルのバリデーション失敗時に詳細情報を提供します
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String fieldName;
    private final Object rejectedValue;
    private final String errorCode;

    /**
     * デフォルトコンストラクタ
     */
    public ValidationException() {
        super("Validation failed");
        this.fieldName = null;
        this.rejectedValue = null;
        this.errorCode = "VALIDATION_ERROR";
    }

    /**
     * メッセージ付きコンストラクタ
     */
    public ValidationException(String message) {
        super(message);
        this.fieldName = null;
        this.rejectedValue = null;
        this.errorCode = "VALIDATION_ERROR";
    }

    /**
     * メッセージと原因付きコンストラクタ
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.fieldName = null;
        this.rejectedValue = null;
        this.errorCode = "VALIDATION_ERROR";
    }

    /**
     * フィールド名付きコンストラクタ
     */
    public ValidationException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
        this.rejectedValue = null;
        this.errorCode = "FIELD_VALIDATION_ERROR";
    }

    /**
     * 完全コンストラクタ
     */
    public ValidationException(String fieldName, Object rejectedValue, String message) {
        super(message);
        this.fieldName = fieldName;
        this.rejectedValue = rejectedValue;
        this.errorCode = "FIELD_VALUE_ERROR";
    }

    /**
     * エラーコード付きコンストラクタ
     */
    public ValidationException(String fieldName, Object rejectedValue, String message, String errorCode) {
        super(message);
        this.fieldName = fieldName;
        this.rejectedValue = rejectedValue;
        this.errorCode = errorCode;
    }

    /**
     * フィールド名を取得
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * 拒否された値を取得
     */
    public Object getRejectedValue() {
        return rejectedValue;
    }

    /**
     * エラーコードを取得
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 必須フィールドエラー用ファクトリメソッド
     */
    public static ValidationException required(String fieldName) {
        return new ValidationException(fieldName, null, 
            "Field " + fieldName + " is required", "REQUIRED_FIELD");
    }

    /**
     * 不正な値エラー用ファクトリメソッド
     */
    public static ValidationException invalidValue(String fieldName, Object value) {
        return new ValidationException(fieldName, value, 
            "Invalid value for field " + fieldName, "INVALID_VALUE");
    }

    /**
     * 長さエラー用ファクトリメソッド
     */
    public static ValidationException lengthError(String fieldName, Object value, int maxLength) {
        return new ValidationException(fieldName, value, 
            "Field " + fieldName + " exceeds maximum length of " + maxLength, "LENGTH_ERROR");
    }

    /**
     * 範囲エラー用ファクトリメソッド
     */
    public static ValidationException rangeError(String fieldName, Object value, String range) {
        return new ValidationException(fieldName, value, 
            "Field " + fieldName + " is out of range: " + range, "RANGE_ERROR");
    }

    /**
     * フォーマットエラー用ファクトリメソッド
     */
    public static ValidationException formatError(String fieldName, Object value, String expectedFormat) {
        return new ValidationException(fieldName, value, 
            "Field " + fieldName + " does not match expected format: " + expectedFormat, "FORMAT_ERROR");
    }

    /**
     * 詳細情報を含む文字列表現を返す
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValidationException: ");
        sb.append(getMessage());

        if (errorCode != null) {
            sb.append(" [errorCode=").append(errorCode).append("]");
        }

        if (fieldName != null) {
            sb.append(" [field=").append(fieldName).append("]");
        }

        if (rejectedValue != null) {
            sb.append(" [rejectedValue=").append(rejectedValue).append("]");
        }

        return sb.toString();
    }
}
