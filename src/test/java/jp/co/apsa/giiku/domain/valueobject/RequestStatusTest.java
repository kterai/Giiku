package jp.co.apsa.giiku.domain.valueobject;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link RequestStatus} のユニットテスト。
 */
class RequestStatusTest {

    @Test
    @DisplayName("コードから取得")
    void testFromCode() {
        assertThat(RequestStatus.fromCode("SUBMITTED")).isSameAs(RequestStatus.SUBMITTED);
        assertThat(RequestStatus.values()).contains(RequestStatus.REJECTED);
    }

    @Test
    @DisplayName("異常系: 無効なコード")
    void testInvalid() {
        assertThatThrownBy(() -> RequestStatus.fromCode("BAD"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
