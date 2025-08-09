package jp.co.apsa.giiku.domain.valueobject;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link SlackId} のユニットテスト。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
class SlackIdTest {

    @Test
    @DisplayName("ユーザーIDとチャンネルID判定")
    void testUserAndChannel() {
        SlackId user = new SlackId("john");
        assertThat(user.getValue()).isEqualTo("@john");
        assertThat(user.isUserId()).isTrue();
        assertThat(user.getUsername()).isEqualTo("john");

        SlackId channel = new SlackId("marketing-team");
        assertThat(channel.isChannelId()).isTrue();
        assertThat(channel.toMention()).isEqualTo("@marketing-team");
    }

    @Test
    @DisplayName("静的メソッド")
    void testStatics() {
        assertThat(SlackId.isValid("john")).isTrue();
        assertThat(SlackId.isValid("@bad id")).isFalse();

        Optional<SlackId> opt = SlackId.of("here");
        assertThat(opt).isPresent();
    }

    @Test
    @DisplayName("異常系: 無効な形式")
    void testInvalid() {
        assertThatThrownBy(() -> new SlackId("!bad"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
