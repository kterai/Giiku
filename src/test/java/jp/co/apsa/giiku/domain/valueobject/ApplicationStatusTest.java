package jp.co.apsa.giiku.domain.valueobject;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link ApplicationStatus} のユニットテスト。
 */
class ApplicationStatusTest {

    @Test
    @DisplayName("正常系: ファクトリメソッド")
    void testFactoryMethods() {
        ApplicationStatus pending = ApplicationStatus.of("PENDING");
        assertThat(pending.isPending()).isTrue();
        assertThat(pending.getDisplayName()).isEqualTo("承認待ち");

        ApplicationStatus approved = ApplicationStatus.approved();
        assertThat(approved.isApproved()).isTrue();
        assertThat(approved.canSubmit()).isFalse();
    }

    @Test
    @DisplayName("状態遷移判定")
    void testCanTransition() {
        ApplicationStatus draft = ApplicationStatus.draft();
        ApplicationStatus pending = ApplicationStatus.pending();
        ApplicationStatus inReview = ApplicationStatus.inReview();
        ApplicationStatus approved = ApplicationStatus.approved();

        assertThat(draft.canTransitionTo(pending)).isTrue();
        assertThat(pending.canTransitionTo(inReview)).isTrue();
        assertThat(pending.canTransitionTo(approved)).isFalse();
        assertThat(approved.canTransitionTo(pending)).isFalse();
    }

    @Test
    @DisplayName("異常系: 無効なステータス")
    void testInvalidStatus() {
        assertThatThrownBy(() -> ApplicationStatus.of("UNKNOWN"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("equals と toString")
    void testEqualsAndToString() {
        ApplicationStatus a = ApplicationStatus.rejected();
        ApplicationStatus b = ApplicationStatus.of("REJECTED");
        assertThat(a).isEqualTo(b);
        assertThat(a.toString()).contains("REJECTED");
    }
}
