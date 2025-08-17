package jp.co.apsa.giiku;

/**
 * システム全体で使用する定数クラス。
 *
 * <p>申請状態コードなど、汎用的な定数を管理します。</p>
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public final class Constants {

    private Constants() {
    }

    /** 申請状態コードを定義します。 */
    public static final class RequestStatusCodes {
        public static final String SUBMITTED = "SUBMITTED";
        public static final String IN_PROGRESS = "IN_PROGRESS";
        public static final String APPROVED = "APPROVED";
        public static final String REJECTED = "REJECTED";

        private RequestStatusCodes() {
        }
    }
}
