package jp.co.apsa.giiku.exception;

/**
 * システム例外を表すクラス。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class SystemException extends Exception {

    /**
     * メッセージを指定して例外を生成します。
     *
     * @param message エラーメッセージ
     */
    public SystemException(String message) {
        super(message);
    }

    /**
     * メッセージと原因例外を指定して例外を生成します。
     *
     * @param message エラーメッセージ
     * @param cause 原因例外
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
