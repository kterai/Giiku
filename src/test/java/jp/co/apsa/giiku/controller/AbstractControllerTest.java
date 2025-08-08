package jp.co.apsa.giiku.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

class AbstractControllerTest {

    private static class TestController extends AbstractController {
        public void callSetTitle(Model model, String title) {
            setTitle(model, title);
        }
    }

    @Test
    void setTitleAddsAttribute() {
        TestController controller = new TestController();
        Model model = new ConcurrentModel();
        controller.callSetTitle(model, "テストタイトル");
        assertThat(model.getAttribute("title")).isEqualTo("テストタイトル");
    }
}
