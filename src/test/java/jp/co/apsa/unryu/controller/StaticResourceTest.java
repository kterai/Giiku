package jp.co.apsa.unryu.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import jp.co.apsa.unryu.controller.AbstractControllerTest;

/**
 * 静的リソースのアクセスに関する統合テスト。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class StaticResourceTest extends AbstractControllerTest {

    @Test
    void webjarsResourceAccessibleWithoutAuth() throws Exception {
        mockMvc.perform(get("/webjars/bootstrap/dist/css/bootstrap.min.css"))
                .andExpect(status().isOk());
    }
}
