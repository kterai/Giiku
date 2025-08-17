package jp.co.apsa.giiku.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * コントローラーの共通処理を提供する抽象基底クラス。
 * 各画面で共通となるタイトル設定などのヘルパーを提供します。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public abstract class AbstractController {

    /**
     * モデルに画面タイトルを設定します。
     *
     * @param model モデル
     * @param title 画面タイトル
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    protected void setTitle(Model model, String title) {
        model.addAttribute("title", title);
    }

    /**
     * エラーレスポンスを生成します。
     *
     * @param message エラーメッセージ
     * @param status  HTTPステータス
     * @return エラー情報を含むレスポンス
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    protected ResponseEntity<Map<String, Object>> createErrorResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", message);
        return new ResponseEntity<>(body, status);
    }
}
