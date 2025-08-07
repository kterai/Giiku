/*
 * Copyright (c) 2024 株式会社アプサ (APSA Co., Ltd.)
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of
 * APSA Co., Ltd. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with APSA Co., Ltd.
 */
package jp.co.apsa.unryu.domain.valueobject;

import java.util.Objects;
import java.util.Arrays;
import java.util.List;

/**
 * ユーザー権限を表すValue Objectクラス。
 * 
 * <p>このクラスは、システム内でのユーザーの権限レベルを定義し、
 * 階層的な権限管理を提供します。権限は以下の順序で階層化されています：</p>
 * 
 * <ul>
 *   <li>ADMIN (管理者) - レベル5：最高権限</li>
 *   <li>MANAGER (マネージャー) - レベル4：管理権限</li>
 *   <li>SUPERVISOR (スーパーバイザー) - レベル3：監督権限</li>
 *   <li>USER (一般ユーザー) - レベル2：基本権限</li>
 *   <li>GUEST (ゲストユーザー) - レベル1：最小権限</li>
 * </ul>
 * 
 * <p>このクラスはImmutableオブジェクトとして設計されており、
 * 一度作成されたインスタンスの状態は変更できません。</p>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public final class UserRole {

    /**
     * 管理者権限（最高レベル）
     */
    public static final UserRole ADMIN = new UserRole("ADMIN", "管理者", 5);

    /**
     * マネージャー権限
     */
    public static final UserRole MANAGER = new UserRole("MANAGER", "マネージャー", 4);

    /**
     * スーパーバイザー権限
     */
    public static final UserRole SUPERVISOR = new UserRole("SUPERVISOR", "スーパーバイザー", 3);

    /**
     * 一般ユーザー権限
     */
    public static final UserRole USER = new UserRole("USER", "一般ユーザー", 2);

    /**
     * ゲストユーザー権限（最低レベル）
     */
    public static final UserRole GUEST = new UserRole("GUEST", "ゲストユーザー", 1);

    /**
     * 利用可能な全ての権限のリスト
     */
    private static final List<UserRole> ALL_ROLES = Arrays.asList(
        ADMIN, MANAGER, SUPERVISOR, USER, GUEST
    );

    /** 権限コード */
    private final String code;

    /** 権限名 */
    private final String name;

    /** 権限レベル（数値が大きいほど高権限） */
    private final int level;

    /**
     * UserRoleのプライベートコンストラクタ。
     * 
     * @param code 権限コード
     * @param name 権限名
     * @param level 権限レベル
     * @throws IllegalArgumentException 引数が不正な場合
     */
    private UserRole(String code, String name, int level) {
        validateParameters(code, name, level);
        this.code = code;
        this.name = name;
        this.level = level;
    }

    /**
     * 権限コードから UserRole インスタンスを取得します。
     * 
     * @param code 権限コード
     * @return 対応する UserRole インスタンス
     * @throws IllegalArgumentException 無効な権限コードの場合
     */
    public static UserRole fromCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("権限コードは必須です");
        }

        String upperCode = code.trim().toUpperCase();
        return ALL_ROLES.stream()
            .filter(role -> role.code.equals(upperCode))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                "無効な権限コードです: " + code + 
                ". 有効な値: ADMIN, MANAGER, SUPERVISOR, USER, GUEST"));
    }

    /**
     * 権限コードを取得します。
     * 
     * @return 権限コード
     */
    public String getCode() {
        return code;
    }

    /**
     * 権限名を取得します。
     * 
     * @return 権限名
     */
    public String getName() {
        return name;
    }

    /**
     * 権限レベルを取得します。
     * 
     * @return 権限レベル（1-5の範囲、数値が大きいほど高権限）
     */
    public int getLevel() {
        return level;
    }

    /**
     * 指定された権限以上の権限を持つかどうかを判定します。
     * 
     * @param requiredRole 必要な権限
     * @return 指定された権限以上の場合true
     * @throws IllegalArgumentException requiredRoleがnullの場合
     */
    public boolean hasPermissionOf(UserRole requiredRole) {
        if (requiredRole == null) {
            throw new IllegalArgumentException("比較対象の権限は必須です");
        }
        return this.level >= requiredRole.level;
    }

    /**
     * 指定された権限より高い権限を持つかどうかを判定します。
     * 
     * @param otherRole 比較対象の権限
     * @return 指定された権限より高い場合true
     * @throws IllegalArgumentException otherRoleがnullの場合
     */
    public boolean isHigherThan(UserRole otherRole) {
        if (otherRole == null) {
            throw new IllegalArgumentException("比較対象の権限は必須です");
        }
        return this.level > otherRole.level;
    }

    /**
     * 管理者権限かどうかを判定します。
     * 
     * @return 管理者権限の場合true
     */
    public boolean isAdmin() {
        return this.equals(ADMIN);
    }

    /**
     * ゲスト権限かどうかを判定します。
     * 
     * @return ゲスト権限の場合true
     */
    public boolean isGuest() {
        return this.equals(GUEST);
    }

    /**
     * 利用可能な全ての権限のリストを取得します。
     * 
     * @return 権限のリスト（変更不可）
     */
    public static List<UserRole> getAllRoles() {
        return List.copyOf(ALL_ROLES);
    }

    /**
     * パラメータの妥当性を検証します。
     * 
     * @param code 権限コード
     * @param name 権限名
     * @param level 権限レベル
     * @throws IllegalArgumentException パラメータが不正な場合
     */
    private void validateParameters(String code, String name, int level) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("権限コードは必須です");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("権限名は必須です");
        }
        if (level < 1 || level > 5) {
            throw new IllegalArgumentException("権限レベルは1-5の範囲で指定してください");
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
        UserRole userRole = (UserRole) obj;
        return level == userRole.level &&
               Objects.equals(code, userRole.code) &&
               Objects.equals(name, userRole.name);
    }

    /**
     * オブジェクトのハッシュコードを計算します。
     * 
     * @return ハッシュコード
     */
    @Override
    public int hashCode() {
        return Objects.hash(code, name, level);
    }

    /**
     * オブジェクトの文字列表現を返します。
     * 
     * @return 文字列表現
     */
    @Override
    public String toString() {
        return String.format("UserRole{code='%s', name='%s', level=%d}", 
                           code, name, level);
    }
}
