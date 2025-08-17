package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.exception.SystemException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 例外ハンドリングを提供するアドバイスクラス。
 * 例外発生時に共通エラーページを表示します。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * SystemExceptionを処理します。
     *
     * @param ex 発生した例外
     * @param model モデル
     * @return エラーテンプレート
     */
    @ExceptionHandler(SystemException.class)
    public String handleSystemException(SystemException ex, Model model) {
        logger.error("SystemException occurred", ex);
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("stackTrace", getStackTrace(ex));
        return "error";
    }

    /**
     * その他の例外を処理します。
     *
     * @param ex 発生した例外
     * @param model モデル
     * @return エラーテンプレート
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        logger.error("Unhandled exception occurred", ex);
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("stackTrace", getStackTrace(ex));
        return "error";
    }

    private String getStackTrace(Throwable ex) {
        return ExceptionUtils.getStackTrace(ex);
    }
}
