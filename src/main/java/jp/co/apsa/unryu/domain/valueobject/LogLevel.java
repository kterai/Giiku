/*
 * Copyright (c) 2024 株式会社アプサ (APSA Co., Ltd.)
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of
 * 株式会社アプサ ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with 株式会社アプサ.
 */
package jp.co.apsa.unryu.domain.valueobject;

import java.util.Objects;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ログレベルを表すValue Objectクラス。
 * 
 * <p>このクラスは、システムで使用されるログレベルを定義し、
 * レベル間の重要度比較、色分け表示、フィルタリング機能を提供します。</p>
 * 
 * <p>サポートされるログレベル:</p>
 * <ul>
 *   <li>ERROR - エラーレベル（最高重要度）</li>
 *   <li>WARN - 警告レベル</li>
 *   <li>INFO - 情報レベル</li>
 *   <li>DEBUG - デバッグレベル</li>
 *   <li>TRACE - トレースレベル（最低重要度）</li>
 * </ul>
 * 
 * <p>使用例:</p>
 * <pre>{@code
 * LogLevel errorLevel = LogLevel.of("ERROR");
 * LogLevel infoLevel = LogLevel.of("INFO");
 * 
 * // 重要度比較
 * boolean isMoreImportant = errorLevel.isMoreImportantThan(infoLevel); // true
 * 
 * // 色分け表示
 * String colorCode = errorLevel.getColorCode(); // "#FF0000"
 * 
 * // フィルタリング
 * List<LogLevel> filtered = LogLevel.filterByMinimumLevel(allLevels, LogLevel.of("WARN"));
 * }</pre>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public final class LogLevel {

    /**
     * ログレベルの定数定義
     */
    public static final LogLevel ERROR = new LogLevel("ERROR", 1, "#FF0000", "エラー");
    public static final LogLevel WARN = new LogLevel("WARN", 2, "#FFA500", "警告");
    public static final LogLevel INFO = new LogLevel("INFO", 3, "#0000FF", "情報");
    public static final LogLevel DEBUG = new LogLevel("DEBUG", 4, "#008000", "デバッグ");
    public static final LogLevel TRACE = new LogLevel("TRACE", 5, "#808080", "トレース");

    /**
     * 全ログレベルのリスト（重要度順）
     */
    private static final List<LogLevel> ALL_LEVELS = Arrays.asList(ERROR, WARN, INFO, DEBUG, TRACE);

    /**
     * ログレベル名
     */
    private final String level;

    /**
     * 重要度（数値が小さいほど重要）
     */
    private final int priority;

    /**
     * 色分け表示用のカラーコード
     */
    private final String colorCode;

    /**
     * 日本語表示名
     */
    private final String displayName;

    /**
     * プライベートコンストラクタ。
     * 
     * @param level ログレベル名
     * @param priority 重要度
     * @param colorCode カラーコード
     * @param displayName 日本語表示名
     */
    private LogLevel(String level, int priority, String colorCode, String displayName) {
        this.level = level;
        this.priority = priority;
        this.colorCode = colorCode;
        this.displayName = displayName;
    }

    /**
     * 文字列からLogLevelインスタンスを生成します。
     * 
     * @param level ログレベル文字列
     * @return LogLevelインスタンス
     * @throws IllegalArgumentException 無効なログレベルが指定された場合
     */
    public static LogLevel of(String level) {
        if (level == null) {
            throw new IllegalArgumentException("ログレベルはnullにできません");
        }

        String upperLevel = level.trim().toUpperCase();

        return ALL_LEVELS.stream()
                .filter(logLevel -> logLevel.level.equals(upperLevel))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                    String.format("無効なログレベルです: %s。有効な値: %s", 
                        level, getSupportedLevels())));
    }

    /**
     * ログレベル名を取得します。
     * 
     * @return ログレベル名
     */
    public String getLevel() {
        return level;
    }

    /**
     * 重要度を取得します。
     * 
     * @return 重要度（数値が小さいほど重要）
     */
    public int getPriority() {
        return priority;
    }

    /**
     * カラーコードを取得します。
     * 
     * @return HTMLカラーコード
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * 日本語表示名を取得します。
     * 
     * @return 日本語表示名
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 指定されたログレベルより重要かどうかを判定します。
     * 
     * @param other 比較対象のログレベル
     * @return このレベルがより重要な場合true
     * @throws IllegalArgumentException 比較対象がnullの場合
     */
    public boolean isMoreImportantThan(LogLevel other) {
        if (other == null) {
            throw new IllegalArgumentException("比較対象のログレベルはnullにできません");
        }
        return this.priority < other.priority;
    }

    /**
     * 指定されたログレベル以上の重要度かどうかを判定します。
     * 
     * @param other 比較対象のログレベル
     * @return このレベルが同等以上に重要な場合true
     * @throws IllegalArgumentException 比較対象がnullの場合
     */
    public boolean isAtLeastAsImportantAs(LogLevel other) {
        if (other == null) {
            throw new IllegalArgumentException("比較対象のログレベルはnullにできません");
        }
        return this.priority <= other.priority;
    }

    /**
     * このログレベルがERRORレベルかどうかを判定します。
     * 
     * @return ERRORレベルの場合true
     */
    public boolean isError() {
        return this.equals(ERROR);
    }

    /**
     * このログレベルがWARNレベル以上かどうかを判定します。
     * 
     * @return WARNレベル以上の場合true
     */
    public boolean isWarnOrHigher() {
        return isAtLeastAsImportantAs(WARN);
    }

    /**
     * このログレベルがINFOレベル以上かどうかを判定します。
     * 
     * @return INFOレベル以上の場合true
     */
    public boolean isInfoOrHigher() {
        return isAtLeastAsImportantAs(INFO);
    }

    /**
     * 指定された最小レベル以上のログレベルでフィルタリングします。
     * 
     * @param levels フィルタリング対象のログレベルリスト
     * @param minimumLevel 最小ログレベル
     * @return フィルタリング結果
     * @throws IllegalArgumentException 引数がnullの場合
     */
    public static List<LogLevel> filterByMinimumLevel(List<LogLevel> levels, LogLevel minimumLevel) {
        if (levels == null) {
            throw new IllegalArgumentException("ログレベルリストはnullにできません");
        }
        if (minimumLevel == null) {
            throw new IllegalArgumentException("最小ログレベルはnullにできません");
        }

        return levels.stream()
                .filter(level -> level.isAtLeastAsImportantAs(minimumLevel))
                .collect(Collectors.toList());
    }

    /**
     * 全ての利用可能なログレベルを取得します。
     * 
     * @return 全ログレベルのリスト（重要度順）
     */
    public static List<LogLevel> getAllLevels() {
        return List.copyOf(ALL_LEVELS);
    }

    /**
     * サポートされているログレベル名の文字列を取得します。
     * 
     * @return サポートされているログレベル名
     */
    public static String getSupportedLevels() {
        return ALL_LEVELS.stream()
                .map(LogLevel::getLevel)
                .collect(Collectors.joining(", "));
    }

    /**
     * 色付きの表示用文字列を生成します（HTML形式）。
     * 
     * @return HTML形式の色付き文字列
     */
    public String toColoredHtml() {
        return String.format("<span style=\"color: %s; font-weight: bold;\">[%s] %s</span>",
                colorCode, level, displayName);
    }

    /**
     * ANSI色コード付きの表示用文字列を生成します（コンソール表示用）。
     * 
     * @return ANSI色コード付き文字列
     */
    public String toColoredConsole() {
        String ansiColor = getAnsiColorCode();
        return String.format("%s[%s] %s\u001B[0m", ansiColor, level, displayName);
    }

    /**
     * ANSI色コードを取得します。
     * 
     * @return ANSI色コード
     */
    private String getAnsiColorCode() {
        switch (this.level) {
            case "ERROR": return "\u001B[31m"; // 赤
            case "WARN": return "\u001B[33m";  // 黄
            case "INFO": return "\u001B[34m";  // 青
            case "DEBUG": return "\u001B[32m"; // 緑
            case "TRACE": return "\u001B[37m"; // 白
            default: return "\u001B[0m";      // デフォルト
        }
    }

    /**
     * ログレベルの詳細情報を取得します。
     * 
     * @return ログレベルの詳細情報
     */
    public String getDetailedInfo() {
        return String.format("LogLevel{level='%s', priority=%d, displayName='%s', colorCode='%s'}", 
                level, priority, displayName, colorCode);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LogLevel logLevel = (LogLevel) obj;
        return priority == logLevel.priority &&
               Objects.equals(level, logLevel.level) &&
               Objects.equals(colorCode, logLevel.colorCode) &&
               Objects.equals(displayName, logLevel.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, priority, colorCode, displayName);
    }

    @Override
    public String toString() {
        return String.format("LogLevel{level='%s', displayName='%s'}", level, displayName);
    }
}
