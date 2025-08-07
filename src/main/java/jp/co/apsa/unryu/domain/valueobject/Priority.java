/*
 * Copyright (c) 2024 株式会社アプサ (APSA Co., Ltd.)
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of APSA Co., Ltd.
 * You shall not disclose such confidential information and shall use it only
 * in accordance with the terms of the license agreement you entered into with APSA.
 */
package jp.co.apsa.unryu.domain.valueobject;

import java.util.Objects;

/**
 * 優先度を表すValue Objectクラス
 * <p>
 * このクラスは業務における優先度レベルを表現し、以下の特徴を持ちます：
 * <ul>
 *   <li>不変オブジェクト（Immutable）</li>
 *   <li>5段階の優先度レベル（URGENT, HIGH, MEDIUM, LOW, VERY_LOW）</li>
 *   <li>優先度の比較機能</li>
 *   <li>色分け表示機能</li>
 *   <li>数値レベルによる定量的評価</li>
 * </ul>
 * 
 * <h3>使用例:</h3>
 * <pre>
 * Priority urgent = Priority.URGENT;
 * Priority high = Priority.HIGH;
 * 
 * // 比較
 * if (urgent.isHigherThan(high)) {
 *     System.out.println("緊急タスクを優先処理");
 * }
 * 
 * // 色分け表示
 * String color = urgent.getDisplayColor();
 * </pre>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public final class Priority implements Comparable<Priority> {

    /**
     * 緊急（最高優先度）
     * レベル: 5, 色: 赤
     */
    public static final Priority URGENT = new Priority("URGENT", "緊急", 5, "#FF0000");

    /**
     * 高優先度
     * レベル: 4, 色: オレンジ
     */
    public static final Priority HIGH = new Priority("HIGH", "高", 4, "#FF8C00");

    /**
     * 中優先度（標準）
     * レベル: 3, 色: 黄
     */
    public static final Priority MEDIUM = new Priority("MEDIUM", "中", 3, "#FFD700");

    /**
     * 低優先度
     * レベル: 2, 色: 緑
     */
    public static final Priority LOW = new Priority("LOW", "低", 2, "#32CD32");

    /**
     * 最低優先度
     * レベル: 1, 色: 青
     */
    public static final Priority VERY_LOW = new Priority("VERY_LOW", "最低", 1, "#4169E1");

    /** 全ての優先度の配列（レベル順） */
    private static final Priority[] VALUES = {VERY_LOW, LOW, MEDIUM, HIGH, URGENT};

    /** 優先度コード */
    private final String code;

    /** 優先度名（日本語） */
    private final String displayName;

    /** 優先度レベル（1-5、数値が大きいほど高優先度） */
    private final int level;

    /** 表示色（16進数カラーコード） */
    private final String displayColor;

    /**
     * Priorityオブジェクトを構築します
     * 
     * @param code 優先度コード（非null、非空文字）
     * @param displayName 表示名（非null、非空文字）
     * @param level 優先度レベル（1-5の範囲）
     * @param displayColor 表示色（非null、有効なカラーコード）
     * @throws IllegalArgumentException パラメータが無効な場合
     */
    private Priority(String code, String displayName, int level, String displayColor) {
        validateParameters(code, displayName, level, displayColor);

        this.code = code;
        this.displayName = displayName;
        this.level = level;
        this.displayColor = displayColor;
    }

    /**
     * コンストラクタパラメータのバリデーションを実行します
     * 
     * @param code 優先度コード
     * @param displayName 表示名
     * @param level 優先度レベル
     * @param displayColor 表示色
     * @throws IllegalArgumentException パラメータが無効な場合
     */
    private void validateParameters(String code, String displayName, int level, String displayColor) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("優先度コードは必須です");
        }
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("表示名は必須です");
        }
        if (level < 1 || level > 5) {
            throw new IllegalArgumentException("優先度レベルは1-5の範囲で指定してください: " + level);
        }
        if (displayColor == null || !displayColor.matches("^#[0-9A-Fa-f]{6}$")) {
            throw new IllegalArgumentException("表示色は有効な16進数カラーコード形式で指定してください: " + displayColor);
        }
    }

    /**
     * 優先度コードを取得します
     * 
     * @return 優先度コード（例: "URGENT", "HIGH"）
     */
    public String getCode() {
        return code;
    }

    /**
     * 表示名を取得します
     * 
     * @return 日本語表示名（例: "緊急", "高"）
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 優先度レベルを取得します
     * 
     * @return 優先度レベル（1-5、数値が大きいほど高優先度）
     */
    public int getLevel() {
        return level;
    }

    /**
     * 表示色を取得します
     * 
     * @return 16進数カラーコード（例: "#FF0000"）
     */
    public String getDisplayColor() {
        return displayColor;
    }

    /**
     * この優先度が指定された優先度より高いかどうかを判定します
     * 
     * @param other 比較対象の優先度（非null）
     * @return この優先度の方が高い場合はtrue
     * @throws IllegalArgumentException otherがnullの場合
     */
    public boolean isHigherThan(Priority other) {
        if (other == null) {
            throw new IllegalArgumentException("比較対象の優先度がnullです");
        }
        return this.level > other.level;
    }

    /**
     * この優先度が指定された優先度より低いかどうかを判定します
     * 
     * @param other 比較対象の優先度（非null）
     * @return この優先度の方が低い場合はtrue
     * @throws IllegalArgumentException otherがnullの場合
     */
    public boolean isLowerThan(Priority other) {
        if (other == null) {
            throw new IllegalArgumentException("比較対象の優先度がnullです");
        }
        return this.level < other.level;
    }

    /**
     * この優先度が緊急レベルかどうかを判定します
     * 
     * @return 緊急レベルの場合はtrue
     */
    public boolean isUrgent() {
        return this == URGENT;
    }

    /**
     * この優先度が高優先度以上かどうかを判定します
     * 
     * @return 高優先度以上の場合はtrue
     */
    public boolean isHighPriorityOrAbove() {
        return this.level >= HIGH.level;
    }

    /**
     * この優先度が低優先度以下かどうかを判定します
     * 
     * @return 低優先度以下の場合はtrue
     */
    public boolean isLowPriorityOrBelow() {
        return this.level <= LOW.level;
    }

    /**
     * 優先度の重要度を百分率で取得します
     * 
     * @return 重要度（0-100%）
     */
    public int getImportancePercentage() {
        return (level - 1) * 25; // 1→0%, 2→25%, 3→50%, 4→75%, 5→100%
    }

    /**
     * 全ての優先度を配列で取得します（レベル順）
     * 
     * @return 優先度配列のコピー
     */
    public static Priority[] values() {
        return VALUES.clone();
    }

    /**
     * コードから優先度を取得します
     * 
     * @param code 優先度コード（非null）
     * @return 対応する優先度
     * @throws IllegalArgumentException 無効なコードの場合
     */
    public static Priority valueOf(String code) {
        if (code == null) {
            throw new IllegalArgumentException("優先度コードがnullです");
        }

        for (Priority priority : VALUES) {
            if (priority.code.equals(code)) {
                return priority;
            }
        }

        throw new IllegalArgumentException("無効な優先度コードです: " + code);
    }

    /**
     * レベルから優先度を取得します
     * 
     * @param level 優先度レベル（1-5）
     * @return 対応する優先度
     * @throws IllegalArgumentException 無効なレベルの場合
     */
    public static Priority fromLevel(int level) {
        if (level < 1 || level > 5) {
            throw new IllegalArgumentException("無効な優先度レベルです: " + level);
        }

        for (Priority priority : VALUES) {
            if (priority.level == level) {
                return priority;
            }
        }

        throw new IllegalStateException("システムエラー: レベル " + level + " に対応する優先度が見つかりません");
    }

    /**
     * デフォルトの優先度（MEDIUM）を取得します
     * 
     * @return デフォルト優先度
     */
    public static Priority getDefault() {
        return MEDIUM;
    }

    @Override
    public int compareTo(Priority other) {
        if (other == null) {
            throw new NullPointerException("比較対象の優先度がnullです");
        }
        return Integer.compare(this.level, other.level);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Priority priority = (Priority) obj;
        return level == priority.level &&
               Objects.equals(code, priority.code) &&
               Objects.equals(displayName, priority.displayName) &&
               Objects.equals(displayColor, priority.displayColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, displayName, level, displayColor);
    }

    @Override
    public String toString() {
        return String.format("Priority{code='%s', displayName='%s', level=%d, color='%s'}", 
                           code, displayName, level, displayColor);
    }

    /**
     * 詳細な文字列表現を取得します
     * 
     * @return 詳細情報を含む文字列
     */
    public String toDetailString() {
        return String.format("%s(%s) - レベル:%d, 重要度:%d%%, 色:%s", 
                           displayName, code, level, getImportancePercentage(), displayColor);
    }

    /**
     * HTML表示用の文字列を取得します
     * 
     * @return HTML形式の文字列
     */
    public String toHtmlString() {
        return String.format("<span style=\"color: %s; font-weight: bold;\">%s</span>",
                           displayColor, displayName);
    }
}
