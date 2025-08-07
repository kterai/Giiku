/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.unryu.domain.valueobject;

import java.util.Objects;
import java.util.Set;
import java.util.Map;

/**
 * 申請ステータスを表すValue Objectクラス。
 * 
 * <p>このクラスは申請の状態を管理するための不変オブジェクトです。
 * 申請のライフサイクルにおける各段階を表現し、ビジネスルールに基づいた
 * 状態遷移の検証機能を提供します。</p>
 * 
 * <p>利用可能なステータス:</p>
 * <ul>
 *   <li>DRAFT - 下書き状態</li>
 *   <li>PENDING - 承認待ち状態</li>
 *   <li>IN_REVIEW - 承認中状態</li>
 *   <li>APPROVED - 承認済み状態</li>
 *   <li>REJECTED - 否認状態</li>
 *   <li>WITHDRAWN - 取下げ状態</li>
 *   <li>EXPIRED - 期限切れ状態</li>
 * </ul>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public final class ApplicationStatus {

    /** 下書き状態 */
    public static final String DRAFT = "DRAFT";

    /** 承認待ち状態 */
    public static final String PENDING = "PENDING";

    /** 承認中状態 */
    public static final String IN_REVIEW = "IN_REVIEW";

    /** 承認済み状態 */
    public static final String APPROVED = "APPROVED";

    /** 否認状態 */
    public static final String REJECTED = "REJECTED";

    /** 取下げ状態 */
    public static final String WITHDRAWN = "WITHDRAWN";

    /** 期限切れ状態 */
    public static final String EXPIRED = "EXPIRED";

    /** ステータス表示名のマッピング */
    private static final Map<String, String> DISPLAY_NAMES = Map.ofEntries(
        Map.entry(DRAFT, "下書き"),
        Map.entry(PENDING, "承認待ち"),
        Map.entry(IN_REVIEW, "承認中"),
        Map.entry(APPROVED, "承認済み"),
        Map.entry(REJECTED, "否認"),
        Map.entry(WITHDRAWN, "取下げ"),
        Map.entry(EXPIRED, "期限切れ")
    );

    /** 有効なステータス値のセット */
    private static final Set<String> VALID_STATUSES = Set.of(
        DRAFT, PENDING, IN_REVIEW, APPROVED, REJECTED, WITHDRAWN, EXPIRED
    );

    /** 終了状態のセット（これ以上遷移できない状態） */
    private static final Set<String> FINAL_STATUSES = Set.of(
        APPROVED, REJECTED, WITHDRAWN, EXPIRED
    );

    /** 申請可能な状態のセット */
    private static final Set<String> SUBMITTABLE_STATUSES = Set.of(
        DRAFT
    );

    /** 承認処理可能な状態のセット */
    private static final Set<String> APPROVABLE_STATUSES = Set.of(
        PENDING, IN_REVIEW
    );

    /** ステータス値 */
    private final String value;

    /**
     * ApplicationStatusインスタンスを生成します。
     * 
     * @param value ステータス値
     * @throws IllegalArgumentException 無効なステータス値が指定された場合
     */
    public ApplicationStatus(String value) {
        validateStatus(value);
        this.value = value;
    }

    /**
     * ステータス値を取得します。
     * 
     * @return ステータス値
     */
    public String getValue() {
        return value;
    }

    /**
     * 日本語表示名を取得します。
     *
     * @return 日本語表示名
     */
    public String getDisplayName() {
        return DISPLAY_NAMES.getOrDefault(value, value);
    }

    /**
     * 下書き状態かどうかを判定します。
     * 
     * @return 下書き状態の場合true
     */
    public boolean isDraft() {
        return DRAFT.equals(value);
    }

    /**
     * 承認待ち状態かどうかを判定します。
     *
     * @return 承認待ち状態の場合true
     */
    public boolean isPending() {
        return PENDING.equals(value);
    }

    /**
     * 承認中状態かどうかを判定します。
     * 
     * @return 承認中状態の場合true
     */
    public boolean isInReview() {
        return IN_REVIEW.equals(value);
    }

    /**
     * 承認済み状態かどうかを判定します。
     * 
     * @return 承認済み状態の場合true
     */
    public boolean isApproved() {
        return APPROVED.equals(value);
    }

    /**
     * 否認状態かどうかを判定します。
     * 
     * @return 否認状態の場合true
     */
    public boolean isRejected() {
        return REJECTED.equals(value);
    }

    /**
     * 取下げ状態かどうかを判定します。
     * 
     * @return 取下げ状態の場合true
     */
    public boolean isWithdrawn() {
        return WITHDRAWN.equals(value);
    }

    /**
     * 期限切れ状態かどうかを判定します。
     * 
     * @return 期限切れ状態の場合true
     */
    public boolean isExpired() {
        return EXPIRED.equals(value);
    }

    /**
     * 終了状態（これ以上遷移できない状態）かどうかを判定します。
     * 
     * @return 終了状態の場合true
     */
    public boolean isFinalStatus() {
        return FINAL_STATUSES.contains(value);
    }

    /**
     * 申請可能な状態かどうかを判定します。
     * 
     * @return 申請可能な状態の場合true
     */
    public boolean canSubmit() {
        return SUBMITTABLE_STATUSES.contains(value);
    }

    /**
     * 承認処理可能な状態かどうかを判定します。
     * 
     * @return 承認処理可能な状態の場合true
     */
    public boolean canApprove() {
        return APPROVABLE_STATUSES.contains(value);
    }

    /**
     * 取下げ可能な状態かどうかを判定します。
     * 
     * @return 取下げ可能な状態の場合true
     */
    public boolean canWithdraw() {
        return !isFinalStatus() && !isDraft();
    }

    /**
     * 指定されたステータスへの遷移が可能かどうかを判定します。
     * 
     * @param targetStatus 遷移先ステータス
     * @return 遷移可能な場合true
     * @throws IllegalArgumentException 無効なステータス値が指定された場合
     */
    public boolean canTransitionTo(ApplicationStatus targetStatus) {
        Objects.requireNonNull(targetStatus, "Target status must not be null");

        String target = targetStatus.getValue();

        // 同じステータスへの遷移は不可
        if (value.equals(target)) {
            return false;
        }

        // 終了状態からの遷移は不可
        if (isFinalStatus()) {
            return false;
        }

        // ビジネスルールに基づく遷移可能性の判定
        switch (value) {
            case DRAFT:
                return PENDING.equals(target);
            case PENDING:
                return IN_REVIEW.equals(target) || WITHDRAWN.equals(target) || EXPIRED.equals(target);
            case IN_REVIEW:
                return APPROVED.equals(target) || REJECTED.equals(target) || WITHDRAWN.equals(target) || EXPIRED.equals(target);
            default:
                return false;
        }
    }

    /**
     * ステータス値のバリデーションを行います。
     * 
     * @param status バリデーション対象のステータス値
     * @throws IllegalArgumentException 無効なステータス値の場合
     */
    private void validateStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Status must not be null");
        }

        if (status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status must not be empty");
        }

        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException(
                String.format("Invalid status: %s. Valid statuses are: %s", 
                    status, VALID_STATUSES)
            );
        }
    }

    /**
     * 有効なステータス値の一覧を取得します。
     * 
     * @return 有効なステータス値のセット
     */
    public static Set<String> getValidStatuses() {
        return Set.copyOf(VALID_STATUSES);
    }

    /**
     * 指定された文字列から ApplicationStatus インスタンスを生成します。
     * 
     * @param value ステータス値
     * @return ApplicationStatus インスタンス
     * @throws IllegalArgumentException 無効なステータス値が指定された場合
     */
    public static ApplicationStatus of(String value) {
        return new ApplicationStatus(value);
    }

    /**
     * 下書き状態の ApplicationStatus インスタンスを生成します。
     * 
     * @return 下書き状態の ApplicationStatus
     */
    public static ApplicationStatus draft() {
        return new ApplicationStatus(DRAFT);
    }

    /**
     * 申請済み状態の ApplicationStatus インスタンスを生成します。
     * 
     * @return 申請済み状態の ApplicationStatus
     */
    public static ApplicationStatus pending() {
        return new ApplicationStatus(PENDING);
    }

    /**
     * 承認中状態の ApplicationStatus インスタンスを生成します。
     * 
     * @return 承認中状態の ApplicationStatus
     */
    public static ApplicationStatus inReview() {
        return new ApplicationStatus(IN_REVIEW);
    }

    /**
     * 承認済み状態の ApplicationStatus インスタンスを生成します。
     * 
     * @return 承認済み状態の ApplicationStatus
     */
    public static ApplicationStatus approved() {
        return new ApplicationStatus(APPROVED);
    }

    /**
     * 否認状態の ApplicationStatus インスタンスを生成します。
     * 
     * @return 否認状態の ApplicationStatus
     */
    public static ApplicationStatus rejected() {
        return new ApplicationStatus(REJECTED);
    }

    /**
     * 取下げ状態の ApplicationStatus インスタンスを生成します。
     * 
     * @return 取下げ状態の ApplicationStatus
     */
    public static ApplicationStatus withdrawn() {
        return new ApplicationStatus(WITHDRAWN);
    }

    /**
     * 期限切れ状態の ApplicationStatus インスタンスを生成します。
     * 
     * @return 期限切れ状態の ApplicationStatus
     */
    public static ApplicationStatus expired() {
        return new ApplicationStatus(EXPIRED);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ApplicationStatus that = (ApplicationStatus) obj;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.format("ApplicationStatus{value='%s'}", value);
    }
}
