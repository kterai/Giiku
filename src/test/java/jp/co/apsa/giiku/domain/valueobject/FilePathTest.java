package jp.co.apsa.giiku.domain.valueobject;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link FilePath} のユニットテスト。
 */
class FilePathTest {

    @Test
    @DisplayName("基本操作")
    void testBasic() {
        FilePath path = new FilePath("/home/test/data.txt");
        assertThat(path.isAbsolute()).isTrue();
        assertThat(path.getFileName()).isEqualTo("data.txt");
        assertThat(path.getExtension()).isEqualTo("txt");
        assertThat(path.getDirectoryPath()).endsWith("/home/test");
        assertThat(path.hasExtension("txt")).isTrue();
        assertThat(path.isSafe()).isTrue();
    }

    @Test
    @DisplayName("相対パス計算と結合")
    void testRelativizeResolve() {
        FilePath base = new FilePath("/home");
        FilePath child = new FilePath("/home/docs/file.pdf");
        FilePath relative = child.relativize(base);
        assertThat(relative.getOriginalPath()).isEqualTo("docs/file.pdf");

        FilePath resolved = base.resolve("docs/file.pdf");
        assertThat(resolved.getFileName()).isEqualTo("file.pdf");
        assertThat(resolved.isAbsolute()).isTrue();
    }

    @Test
    @DisplayName("異常系: 不正パス")
    void testInvalid() {
        assertThatThrownBy(() -> new FilePath("../etc/passwd"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
