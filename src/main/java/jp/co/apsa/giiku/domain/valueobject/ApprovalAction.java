/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.giiku.domain.valueobject;

import java.util.Objects;
import java.util.Arrays;

/**
 * 承認アクションを表すValue Objectクラス。
 * 
 * <p>このクラスは承認プロセスにおける各種アクションを定義し、
 * 不変オブジェクトとして実装されています。</p>
 * 
 * <p>利用可能なアクション:</p>
 * <ul>
 *   <li>APPROVE - 承認</li>
 *   <li>REJECT - 否認</li>
 *   <li>RETURN - 差し戻し</li>
 *   <li>DELEGATE - 代理承認</li>
 *   <li>SKIP - スキップ</li>
 *   <li>CANCEL - 取消</li>
 * </ul>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public final class ApprovalAction {

    /** 承認 */
    public static final ApprovalAction APPROVE = new ApprovalAction("APPROVE", "承認");

    /** 否認 */
    public static final ApprovalAction REJECT = new ApprovalAction("REJECT", "否認");

    /** 差し戻し */
    public static final ApprovalAction RETURN = new ApprovalAction("RETURN", "差し戻し");

    /** 代理承認 */
    public static final ApprovalAction DELEGATE = new ApprovalAction("DELEGATE", "代理承認");

    /** スキップ */
    public static final ApprovalAction SKIP = new ApprovalAction("SKIP", "スキップ");

    /** 取消 */
    public static final ApprovalAction CANCEL = new ApprovalAction("CANCEL", "取消");

    /** 利用可能な全てのアクション */
    private static final ApprovalAction[] VALUES = {
        APPROVE, REJECT, RETURN, DELEGATE, SKIP, CANCEL
    };

    /** アクションコード */
    private final String code;

    /** アクション名 */
    private final String name;

    /**
     * ApprovalActionのコンストラクタ。
     * 
     * @param code アクションコード
     * @param name アクション名
     * @throws IllegalArgumentException コードまたは名前がnullまたは空文字の場合
     */
    private ApprovalAction(String code, String name) {
        validateCode(code);
        validateName(name);
        this.code = code;
        this.name = name;
    }

    /**
     * コードからApprovalActionインスタンスを取得します。
     * 
     * @param code アクションコード
     * @return ApprovalActionインスタンス
     * @throws IllegalArgumentException 無効なコードの場合
     */
    public static ApprovalAction valueOf(String code) {
        if (code == null) {
            throw new IllegalArgumentException("コードはnullにできません");
        }

        return Arrays.stream(VALUES)
                .filter(action -> action.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                    "無効なアクションコードです: " + code));
    }

    /**
     * 利用可能な全てのApprovalActionを取得します。
     * 
     * @return ApprovalActionの配列
     */
    public static ApprovalAction[] values() {
        return VALUES.clone();
    }

    /**
     * アクションコードを取得します。
     * 
     * @return アクションコード
     */
    public String getCode() {
        return code;
    }

    /**
     * アクション名を取得します。
     * 
     * @return アクション名
     */
    public String getName() {
        return name;
    }

    /**
     * このアクションが承認系のアクション（承認または代理承認）かどうかを判定します。
     * 
     * @return 承認系のアクションの場合true
     */
    public boolean isApprovalAction() {
        return this == APPROVE || this == DELEGATE;
    }

    /**
     * このアクションが否認系のアクション（否認または差し戻し）かどうかを判定します。
     * 
     * @return 否認系のアクションの場合true
     */
    public boolean isRejectionAction() {
        return this == REJECT || this == RETURN;
    }

    /**
     * このアクションが処理終了系のアクション（承認、否認、代理承認、取消）かどうかを判定します。
     * 
     * @return 処理終了系のアクションの場合true
     */
    public boolean isTerminalAction() {
        return this == APPROVE || this == REJECT || this == DELEGATE || this == CANCEL;
    }

    /**
     * このアクションが処理継続系のアクション（差し戻し、スキップ）かどうかを判定します。
     * 
     * @return 処理継続系のアクションの場合true
     */
    public boolean isContinuationAction() {
        return this == RETURN || this == SKIP;
    }

    /**
     * コードのバリデーションを行います。
     * 
     * @param code 検証するコード
     * @throws IllegalArgumentException コードが無効な場合
     */
    private void validateCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("アクションコードはnullまたは空文字にできません");
        }
        if (!code.matches("^[A-Z_]+$")) {
            throw new IllegalArgumentException("アクションコードは大文字とアンダースコアのみ使用可能です: " + code);
        }
    }

    /**
     * 名前のバリデーションを行います。
     * 
     * @param name 検証する名前
     * @throws IllegalArgumentException 名前が無効な場合
     */
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("アクション名はnullまたは空文字にできません");
        }
    }

    /**
     * オブジェクトの等価性を判定します。
     * 
     * @param obj 比較対象のオブジェクト
     * @return 等価な場合true
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ApprovalAction that = (ApprovalAction) obj;
        return Objects.equals(code, that.code) && Objects.equals(name, that.name);
    }

    /**
     * オブジェクトのハッシュコードを取得します。
     * 
     * @return ハッシュコード
     */
    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    /**
     * オブジェクトの文字列表現を取得します。
     * 
     * @return 文字列表現
     */
    @Override
    public String toString() {
        return "ApprovalAction{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
