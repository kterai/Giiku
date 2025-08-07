/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.unryu.domain.valueobject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Slack IDを表すValue Objectクラス。
 * 
 * <p>このクラスはSlack IDの形式検証、メンション形式への変換、
 * ユーザーIDとチャンネルIDの区別などの機能を提供します。</p>
 * 
 * <p>Slack IDは以下の形式に従います：</p>
 * <ul>
 *   <li>@で始まる</li>
 *   <li>英数字、ハイフン、アンダースコアのみ使用可能</li>
 *   <li>長さは3文字以上50文字以下</li>
 *   <li>ユーザーID: @username形式</li>
 *   <li>チャンネルID: @channel形式（通常は#channelだが、@形式も対応）</li>
 * </ul>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public final class SlackId {

    /** Slack IDの最小長（@を含む） */
    private static final int MIN_LENGTH = 3;

    /** Slack IDの最大長（@を含む） */
    private static final int MAX_LENGTH = 50;

    /** Slack IDの形式を検証する正規表現パターン */
    private static final Pattern SLACK_ID_PATTERN = Pattern.compile("^@[a-zA-Z0-9_-]+$");

    /** チャンネルIDを示すプレフィックスパターン */
    private static final Pattern CHANNEL_PATTERN = Pattern.compile("^@(channel|here|everyone|.*-.*|.*_team.*|.*-team.*)$", Pattern.CASE_INSENSITIVE);

    /** Slack IDの値 */
    private final String value;

    /**
     * Slack IDを作成します。
     * 
     * @param value Slack IDの文字列値
     * @throws IllegalArgumentException Slack IDの形式が不正な場合
     * @throws NullPointerException valueがnullの場合
     */
    public SlackId(String value) {
        this.value = validateAndNormalize(value);
    }

    /**
     * Slack IDの値を検証し、正規化します。
     * 
     * @param value 検証対象の値
     * @return 正規化されたSlack ID
     * @throws IllegalArgumentException 形式が不正な場合
     * @throws NullPointerException valueがnullの場合
     */
    private String validateAndNormalize(String value) {
        Objects.requireNonNull(value, "Slack ID cannot be null");

        String trimmed = value.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Slack ID cannot be empty");
        }

        // @で始まらない場合は自動的に追加
        if (!trimmed.startsWith("@")) {
            trimmed = "@" + trimmed;
        }

        // 長さチェック
        if (trimmed.length() < MIN_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Slack ID must be at least %d characters long (including @)", MIN_LENGTH));
        }

        if (trimmed.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Slack ID must be at most %d characters long (including @)", MAX_LENGTH));
        }

        // 形式チェック
        if (!SLACK_ID_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException(
                "Slack ID must start with @ and contain only alphanumeric characters, hyphens, and underscores");
        }

        return trimmed;
    }

    /**
     * Slack IDの値を取得します。
     * 
     * @return Slack IDの文字列値
     */
    public String getValue() {
        return value;
    }

    /**
     * メンション形式の文字列を取得します。
     * 
     * @return メンション形式の文字列（@付き）
     */
    public String toMention() {
        return value;
    }

    /**
     * @を除いたユーザー名部分を取得します。
     * 
     * @return @を除いたユーザー名
     */
    public String getUsername() {
        return value.substring(1);
    }

    /**
     * このSlack IDがユーザーIDかどうかを判定します。
     * 
     * @return ユーザーIDの場合true、チャンネルIDの場合false
     */
    public boolean isUserId() {
        return !isChannelId();
    }

    /**
     * このSlack IDがチャンネルIDかどうかを判定します。
     * 
     * <p>以下の条件でチャンネルIDと判定します：</p>
     * <ul>
     *   <li>@channel, @here, @everyoneの特別なチャンネル</li>
     *   <li>ハイフンを含む名前（一般的なチャンネル命名規則）</li>
     *   <li>_teamまたは-teamを含む名前</li>
     * </ul>
     * 
     * @return チャンネルIDの場合true、ユーザーIDの場合false
     */
    public boolean isChannelId() {
        return CHANNEL_PATTERN.matcher(value).matches();
    }

    /**
     * 特別なチャンネル（@channel, @here, @everyone）かどうかを判定します。
     * 
     * @return 特別なチャンネルの場合true
     */
    public boolean isSpecialChannel() {
        String username = getUsername().toLowerCase();
        return "channel".equals(username) || "here".equals(username) || "everyone".equals(username);
    }

    /**
     * Slack IDの形式が有効かどうかを静的に検証します。
     * 
     * @param value 検証対象の文字列
     * @return 有効な形式の場合true
     */
    public static boolean isValid(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        try {
            new SlackId(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 文字列からSlack IDを安全に作成します。
     * 
     * @param value Slack IDの文字列値
     * @return 作成に成功した場合はSlackIdのOptional、失敗した場合は空のOptional
     */
    public static java.util.Optional<SlackId> of(String value) {
        try {
            return java.util.Optional.of(new SlackId(value));
        } catch (IllegalArgumentException | NullPointerException e) {
            return java.util.Optional.empty();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SlackId slackId = (SlackId) obj;
        return Objects.equals(value, slackId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.format("SlackId{value='%s', type='%s'}", 
            value, isChannelId() ? "CHANNEL" : "USER");
    }
}
