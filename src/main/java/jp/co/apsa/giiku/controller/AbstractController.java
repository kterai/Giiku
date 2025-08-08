package jp.co.apsa.giiku.controller;

import org.springframework.ui.Model;

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
     */
    protected void setTitle(Model model, String title) {
        model.addAttribute("title", title);
    }
}
