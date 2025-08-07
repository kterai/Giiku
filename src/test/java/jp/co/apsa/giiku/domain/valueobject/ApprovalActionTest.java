package jp.co.apsa.giiku.domain.valueobject;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link ApprovalAction} のユニットテスト。
 */
class ApprovalActionTest {

    @Test
    @DisplayName("valueOf と各種判定")
    void testValueOf() {
        ApprovalAction action = ApprovalAction.valueOf("APPROVE");
        assertThat(action).isSameAs(ApprovalAction.APPROVE);
        assertThat(action.isApprovalAction()).isTrue();
        assertThat(action.isTerminalAction()).isTrue();
        assertThat(action.isRejectionAction()).isFalse();
        assertThat(action.getName()).isEqualTo("承認");
    }

    @Test
    @DisplayName("継続・否認アクション判定")
    void testContinuation() {
        ApprovalAction ret = ApprovalAction.RETURN;
        assertThat(ret.isContinuationAction()).isTrue();
        assertThat(ret.isRejectionAction()).isTrue();
    }

    @Test
    @DisplayName("異常系: 無効なコード")
    void testInvalid() {
        assertThatThrownBy(() -> ApprovalAction.valueOf("BAD"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
