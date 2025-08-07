package jp.co.apsa.unryu.domain.valueobject;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link Priority} のユニットテスト。
 */
class PriorityTest {

    @Test
    @DisplayName("レベルとコードから取得")
    void testLookup() {
        assertThat(Priority.valueOf("HIGH")).isSameAs(Priority.HIGH);
        assertThat(Priority.fromLevel(5)).isSameAs(Priority.URGENT);
        assertThat(Priority.getDefault()).isSameAs(Priority.MEDIUM);
    }

    @Test
    @DisplayName("比較系メソッド")
    void testCompareAndFlags() {
        assertThat(Priority.URGENT.isHigherThan(Priority.HIGH)).isTrue();
        assertThat(Priority.LOW.isLowerThan(Priority.MEDIUM)).isTrue();
        assertThat(Priority.URGENT.isUrgent()).isTrue();
        assertThat(Priority.HIGH.isHighPriorityOrAbove()).isTrue();
        assertThat(Priority.LOW.isLowPriorityOrBelow()).isTrue();
    }

    @Test
    @DisplayName("その他メソッド")
    void testOthers() {
        assertThat(Priority.HIGH.getImportancePercentage()).isEqualTo(75);
        assertThat(Priority.HIGH.toHtmlString()).contains("高");
        assertThat(Priority.HIGH.compareTo(Priority.MEDIUM)).isGreaterThan(0);
    }

    @Test
    @DisplayName("異常系: 無効な入力")
    void testInvalid() {
        assertThatThrownBy(() -> Priority.valueOf("BAD"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Priority.fromLevel(0))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
