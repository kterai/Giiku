package jp.co.apsa.unryu.domain.valueobject;

import static org.assertj.core.api.Assertions.*;

import java.time.Year;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link ApplicationNumber} のユニットテスト。
 */
class ApplicationNumberTest {

    @Test
    @DisplayName("文字列とパーツ指定コンストラクタ")
    void testConstructors() {
        int year = Year.now().getValue();
        ApplicationNumber num1 = new ApplicationNumber("UN", year, 1);
        assertThat(num1.getValue()).isEqualTo(String.format("UN-%04d-%06d", year, 1));
        ApplicationNumber num2 = new ApplicationNumber(num1.getValue());
        assertThat(num2).isEqualTo(num1);
        assertThat(num1.getTypeName()).isEqualTo("雲龍システム");
    }

    @Test
    @DisplayName("シーケンス操作")
    void testSequenceAndYear() {
        int year = Year.now().getValue();
        ApplicationNumber num = new ApplicationNumber("AP", year, 5);
        assertThat(num.nextSequence().getSequence()).isEqualTo(6);
        assertThat(num.withYear(year + 1).getYear()).isEqualTo(year + 1);
        assertThat(num.isYearOf(year)).isTrue();
    }

    @Test
    @DisplayName("異常系: 無効な入力")
    void testInvalid() {
        assertThatThrownBy(() -> new ApplicationNumber("XX-2020-000001"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ApplicationNumber("ZZ", 2010, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
