package jp.co.apsa.giiku.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.apsa.giiku.service.DayService;
import jp.co.apsa.giiku.service.MonthService;
import jp.co.apsa.giiku.service.WeekService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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

    /** 月サービス */
    @Autowired
    private MonthService monthService;

    /** 週サービス */
    @Autowired
    private WeekService weekService;

    /** 日サービス */
    @Autowired
    private DayService dayService;

    /**
     * モデルに画面タイトルを設定します。
     *
     * @param model モデル
     * @param title 画面タイトル
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
     */
    protected ResponseEntity<Map<String, Object>> createErrorResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", message);
        return new ResponseEntity<>(body, status);
    }

    /**
     * 共通メニューに表示する月別リンクのリストを提供します。
     *
     * @return 月別リンクのリスト
     */
    @ModelAttribute("navMonths")
    public List<Map<String, String>> navMonths() {
        return monthService.findAll().stream()
                .map(m -> Map.of("href", "/month/month" + m.getMonthNumber(), "label", m.getTitle()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 共通メニューに表示する週別リンクのリストを提供します。
     *
     * @return 週別リンクのリスト
     */
    @ModelAttribute("navWeeks")
    public List<Map<String, String>> navWeeks() {
        return weekService.findAll().stream()
                .map(w -> Map.of("href", "/week/week" + w.getWeekNumber(), "label", w.getWeekName()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 共通メニューに表示する日別リンクのリストを提供します。
     *
     * @return 日別リンクのリスト
     */
    @ModelAttribute("navDays")
    public List<Map<String, String>> navDays() {
        return dayService.findAll().stream()
                .map(d -> Map.of("href", "/day/day" + d.getDayNumber(), "label", d.getDayName()))
                .collect(java.util.stream.Collectors.toList());
    }
}
