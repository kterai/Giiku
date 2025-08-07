/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.unryu.domain.valueobject;

import java.util.Objects;

/**
 * 承認ステータスを表すValue Objectクラス
 * 
 * <p>このクラスは承認プロセスにおける各段階のステータスを表現します。
 * 不変オブジェクトとして設計されており、一度作成されたインスタンスの状態は変更できません。</p>
 * 
 * <p>利用可能なステータス:</p>
 * <ul>
 *   <li>PENDING - 承認待ち</li>
 *   <li>APPROVED - 承認</li>
 *   <li>REJECTED - 否認</li>
 *   <li>DELEGATED - 代理承認</li>
 *   <li>RETURNED - 差し戻し</li>
 *   <li>SKIPPED - スキップ</li>
 *   <li>EXPIRED - 期限切れ</li>
 * </ul>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public final class ApprovalStatus {

    /**
     * 承認ステータスの列挙値
     */
    public enum Status {
        /** 承認待ち */
        PENDING("承認待ち"),
        /** 承認 */
        APPROVED("承認"),
        /** 否認 */
        REJECTED("否認"),
        /** 代理承認 */
        DELEGATED("代理承認"),
        /** 差し戻し */
        RETURNED("差し戻し"),
        /** スキップ */
        SKIPPED("スキップ"),
        /** 期限切れ */
        EXPIRED("期限切れ");

        private final String displayName;

        Status(String displayName) {
            this.displayName = displayName;
        }

        /**
         * 表示名を取得します
         * @return 表示名
         */
        public String getDisplayName() {
            return displayName;
        }
    }

    /** 承認ステータス値 */
    private final Status status;

    /**
     * ApprovalStatusインスタンスを作成します
     * 
     * @param status 承認ステータス
     * @throws IllegalArgumentException statusがnullの場合
     */
    public ApprovalStatus(Status status) {
        validateStatus(status);
        this.status = status;
    }

    /**
     * 文字列からApprovalStatusインスタンスを作成します
     * 
     * @param statusString ステータス文字列
     * @return ApprovalStatusインスタンス
     * @throws IllegalArgumentException 無効なステータス文字列の場合
     */
    public static ApprovalStatus of(String statusString) {
        if (statusString == null || statusString.trim().isEmpty()) {
            throw new IllegalArgumentException("ステータス文字列は必須です");
        }

        try {
            Status status = Status.valueOf(statusString.trim().toUpperCase());
            return new ApprovalStatus(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("無効な承認ステータス: " + statusString, e);
        }
    }

    /**
     * 承認待ちステータスのインスタンスを作成します
     * 
     * @return 承認待ちのApprovalStatus
     */
    public static ApprovalStatus pending() {
        return new ApprovalStatus(Status.PENDING);
    }

    /**
     * 承認ステータスのインスタンスを作成します
     * 
     * @return 承認のApprovalStatus
     */
    public static ApprovalStatus approved() {
        return new ApprovalStatus(Status.APPROVED);
    }

    /**
     * 否認ステータスのインスタンスを作成します
     * 
     * @return 否認のApprovalStatus
     */
    public static ApprovalStatus rejected() {
        return new ApprovalStatus(Status.REJECTED);
    }

    /**
     * 代理承認ステータスのインスタンスを作成します
     * 
     * @return 代理承認のApprovalStatus
     */
    public static ApprovalStatus delegated() {
        return new ApprovalStatus(Status.DELEGATED);
    }

    /**
     * 差し戻しステータスのインスタンスを作成します
     * 
     * @return 差し戻しのApprovalStatus
     */
    public static ApprovalStatus returned() {
        return new ApprovalStatus(Status.RETURNED);
    }

    /**
     * スキップステータスのインスタンスを作成します
     * 
     * @return スキップのApprovalStatus
     */
    public static ApprovalStatus skipped() {
        return new ApprovalStatus(Status.SKIPPED);
    }

    /**
     * 期限切れステータスのインスタンスを作成します
     * 
     * @return 期限切れのApprovalStatus
     */
    public static ApprovalStatus expired() {
        return new ApprovalStatus(Status.EXPIRED);
    }

    /**
     * 承認ステータス値を取得します
     * 
     * @return 承認ステータス
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 表示名を取得します
     * 
     * @return 表示名
     */
    public String getDisplayName() {
        return status.getDisplayName();
    }

    /**
     * 承認が完了しているかどうかを判定します
     * 
     * @return 承認完了の場合true、それ以外はfalse
     */
    public boolean isApproved() {
        return status == Status.APPROVED || status == Status.DELEGATED;
    }

    /**
     * 承認が拒否されているかどうかを判定します
     * 
     * @return 拒否の場合true、それ以外はfalse
     */
    public boolean isRejected() {
        return status == Status.REJECTED;
    }

    /**
     * 承認待ち状態かどうかを判定します
     * 
     * @return 承認待ちの場合true、それ以外はfalse
     */
    public boolean isPending() {
        return status == Status.PENDING;
    }

    /**
     * 最終状態（完了または失効）かどうかを判定します
     * 
     * @return 最終状態の場合true、それ以外はfalse
     */
    public boolean isFinalState() {
        return status == Status.APPROVED || 
               status == Status.REJECTED || 
               status == Status.DELEGATED || 
               status == Status.EXPIRED;
    }

    /**
     * 次のステータスに遷移可能かどうかを判定します
     * 
     * @param nextStatus 次のステータス
     * @return 遷移可能な場合true、それ以外はfalse
     */
    public boolean canTransitionTo(ApprovalStatus nextStatus) {
        if (nextStatus == null) {
            return false;
        }

        return canTransitionTo(nextStatus.getStatus());
    }

    /**
     * 次のステータスに遷移可能かどうかを判定します
     * 
     * @param nextStatus 次のステータス
     * @return 遷移可能な場合true、それ以外はfalse
     */
    public boolean canTransitionTo(Status nextStatus) {
        if (nextStatus == null) {
            return false;
        }

        // ビジネスルール: ステータス遷移の制約
        switch (this.status) {
            case PENDING:
                // 承認待ちからは全てのステータスに遷移可能
                return true;
            case RETURNED:
                // 差し戻しからは承認待ち、承認、否認、期限切れに遷移可能
                return nextStatus == Status.PENDING || 
                       nextStatus == Status.APPROVED || 
                       nextStatus == Status.REJECTED || 
                       nextStatus == Status.EXPIRED;
            case APPROVED:
            case REJECTED:
            case DELEGATED:
            case SKIPPED:
            case EXPIRED:
                // 最終状態からは遷移不可
                return false;
            default:
                return false;
        }
    }

    /**
     * ステータスのバリデーションを行います
     * 
     * @param status バリデーション対象のステータス
     * @throws IllegalArgumentException statusがnullの場合
     */
    private void validateStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("承認ステータスは必須です");
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
        ApprovalStatus that = (ApprovalStatus) obj;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @Override
    public String toString() {
        return "ApprovalStatus{" +
                "status=" + status +
                ", displayName='" + status.getDisplayName() + '\'' +
                '}';
    }
}
