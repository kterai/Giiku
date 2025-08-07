package jp.co.apsa.giiku.domain.valueobject;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link ApprovalStatus} のユニットテスト。
 */
class ApprovalStatusTest {

    @Test
    @DisplayName("文字列からの生成と判定")
    void testOfAndChecks() {
        ApprovalStatus status = ApprovalStatus.of("APPROVED");
        assertThat(status.isApproved()).isTrue();
        assertThat(status.getDisplayName()).isEqualTo("承認");

        ApprovalStatus delegated = ApprovalStatus.delegated();
        assertThat(delegated.isApproved()).isTrue();
        assertThat(delegated.isFinalState()).isTrue();
    }

    @Test
    @DisplayName("ステータス遷移判定")
    void testTransition() {
        ApprovalStatus pending = ApprovalStatus.pending();
        ApprovalStatus approved = ApprovalStatus.approved();
        ApprovalStatus returned = ApprovalStatus.returned();

        assertThat(pending.canTransitionTo(approved)).isTrue();
        assertThat(returned.canTransitionTo(ApprovalStatus.expired())).isTrue();
        assertThat(approved.canTransitionTo(pending)).isFalse();
    }

    @Test
    @DisplayName("異常系: 無効な値")
    void testInvalid() {
        assertThatThrownBy(() -> ApprovalStatus.of("BAD"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("equals と toString")
    void testEqualsAndString() {
        ApprovalStatus a = ApprovalStatus.rejected();
        ApprovalStatus b = ApprovalStatus.of("REJECTED");
        assertThat(a).isEqualTo(b);
        assertThat(a.toString()).contains("REJECTED");
    }
}
