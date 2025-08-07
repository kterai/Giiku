package jp.co.apsa.giiku.domain.valueobject;

import jp.co.apsa.giiku.Constants.RequestStatusCodes;
import java.util.List;
import java.util.Objects;

/**
 * 申請の状態を表す値オブジェクト。
 * <p>状態は英語のコードで管理され、表示は日本語となります。</p>
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public final class RequestStatus {

    /** 申請済み */
    public static final RequestStatus SUBMITTED = new RequestStatus(RequestStatusCodes.SUBMITTED, "申請済み");

    /** 承認中 */
    public static final RequestStatus IN_PROGRESS = new RequestStatus(RequestStatusCodes.IN_PROGRESS, "承認中");

    /** 承認済み */
    public static final RequestStatus APPROVED = new RequestStatus(RequestStatusCodes.APPROVED, "承認済み");

    /** 却下 */
    public static final RequestStatus REJECTED = new RequestStatus(RequestStatusCodes.REJECTED, "却下");

    /** 全ての状態 */
    private static final List<RequestStatus> VALUES = List.of(
            SUBMITTED, IN_PROGRESS, APPROVED, REJECTED
    );

    /** コード */
    private final String code;
    /** 表示名 */
    private final String displayName;

    private RequestStatus(String code, String displayName) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("ステータスコードは必須です");
        }
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("表示名は必須です");
        }
        this.code = code;
        this.displayName = displayName;
    }

    /**
     * コードから状態を取得します。
     *
     * @param code ステータスコード
     * @return 対応する状態
     * @throws IllegalArgumentException 無効なコードの場合
     */
    public static RequestStatus fromCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("ステータスコードは必須です");
        }
        return VALUES.stream()
                .filter(s -> s.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("無効なステータスコード: " + code));
    }

    /**
     * 利用可能な状態一覧を取得します。
     *
     * @return 状態一覧
     */
    public static List<RequestStatus> values() {
        return List.copyOf(VALUES);
    }

    /** コードを取得します。 */
    public String getCode() {
        return code;
    }

    /** 日本語表示名を取得します。 */
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestStatus that = (RequestStatus) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return String.format("RequestStatus{code='%s', displayName='%s'}", code, displayName);
    }
}
