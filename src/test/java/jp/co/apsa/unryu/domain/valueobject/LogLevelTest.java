package jp.co.apsa.unryu.domain.valueobject;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link LogLevel} のユニットテスト。
 */
class LogLevelTest {

    @Test
    @DisplayName("レベル取得と比較")
    void testOfAndCompare() {
        LogLevel error = LogLevel.of("error");
        LogLevel warn = LogLevel.WARN;
        assertThat(error.isMoreImportantThan(warn)).isTrue();
        assertThat(error.isError()).isTrue();
        assertThat(warn.isWarnOrHigher()).isTrue();
    }

    @Test
    @DisplayName("フィルタリングと色付き文字列")
    void testFilterAndColor() {
        List<LogLevel> filtered = LogLevel.filterByMinimumLevel(LogLevel.getAllLevels(), LogLevel.INFO);
        assertThat(filtered).containsExactly(LogLevel.ERROR, LogLevel.WARN, LogLevel.INFO);
        assertThat(LogLevel.ERROR.toColoredHtml()).contains("#FF0000");
        assertThat(LogLevel.INFO.toColoredConsole()).contains("[INFO]");
    }

    @Test
    @DisplayName("異常系: 無効なレベル")
    void testInvalid() {
        assertThatThrownBy(() -> LogLevel.of("BAD"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
