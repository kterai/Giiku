/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of APSA Corporation.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with APSA Corporation.
 */
package jp.co.apsa.giiku.domain.repository;

import jp.co.apsa.giiku.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * ユーザーリポジトリインターフェース
 * 
 * <p>
 * Hexagonal Architectureにおけるドメイン層のリポジトリインターフェースです。
 * ユーザーエンティティに対する永続化操作を定義します。
 * Spring Data JPAを使用してデータアクセス層との結合を実現します。
 * </p>
 * 
 * <p>
 * 主な機能:
 * <ul>
 *   <li>基本的なCRUD操作</li>
 *   <li>ユーザー名による検索</li>
 *   <li>権限別ユーザー検索</li>
 *   <li>アクティブユーザー検索</li>
 *   <li>メールアドレス重複チェック</li>
 * </ul>
 * </p>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
@Validated
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * ユーザー名でユーザーを検索します。
     * 
     * @param username 検索対象のユーザー名
     * @return 該当するユーザー（存在しない場合はEmpty）
     * @throws IllegalArgumentException ユーザー名がnullまたは空文字の場合
     */
    Optional<User> findByUsername(@NotBlank(message = "ユーザー名は必須です") String username);

    /**
     * 指定された権限を持つユーザー一覧を取得します。
     * 
     * @param roleName 権限名
     * @return 該当権限を持つユーザーリスト
     * @throws IllegalArgumentException 権限名がnullまたは空文字の場合
     */
    List<User> findByRoleOrderByUsername(@NotBlank(message = "ロールは必須です") String role);

    /**
     * 複数の権限のいずれかを持つユーザー一覧を取得します。
     * 
     * @param roleNames 権限名のリスト
     * @return 該当権限を持つユーザーリスト
     * @throws IllegalArgumentException 権限名リストがnullまたは空の場合
     */
    List<User> findByRoleInOrderByUsername(@NotEmpty(message = "ロールリストは必須です") List<String> roles);

    /**
     * アクティブなユーザー一覧を取得します。
     * 
     * @return アクティブなユーザーリスト
     */
    @Query("SELECT u FROM User u WHERE u.active = true ORDER BY u.username")
    List<User> findActiveUsers();

    /**
     * 非アクティブなユーザー一覧を取得します。
     * 
     * @return 非アクティブなユーザーリスト
     */
    @Query("SELECT u FROM User u WHERE u.active = false ORDER BY u.username")
    List<User> findInactiveUsers();

    /**
     * 指定されたメールアドレスが既に存在するかチェックします。
     * 
     * @param email チェック対象のメールアドレス
     * @return 存在する場合はtrue、存在しない場合はfalse
     * @throws IllegalArgumentException メールアドレスがnullまたは空文字の場合
     */
    boolean existsByEmail(@NotBlank(message = "メールアドレスは必須です") String email);

    /**
     * 指定されたユーザーID以外で、同じメールアドレスが存在するかチェックします。
     * ユーザー更新時の重複チェックに使用します。
     * 
     * @param email チェック対象のメールアドレス
     * @param userId 除外するユーザーID
     * @return 存在する場合はtrue、存在しない場合はfalse
     * @throws IllegalArgumentException メールアドレスがnullまたは空文字、またはユーザーIDがnullの場合
     */
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email AND u.id != :userId")
    boolean existsByEmailAndIdNot(@Param("email") @NotBlank(message = "メールアドレスは必須です") String email,
                                  @Param("userId") @NotNull(message = "ユーザーIDは必須です") Long userId);

    /**
     * メールアドレスでユーザーを検索します。
     * 
     * @param email 検索対象のメールアドレス
     * @return 該当するユーザー（存在しない場合はEmpty）
     * @throws IllegalArgumentException メールアドレスがnullまたは空文字の場合
     */
    Optional<User> findByEmail(@NotBlank(message = "メールアドレスは必須です") String email);

    /**
     * ユーザー名の部分一致検索を行います。
     * 
     * @param username 検索キーワード（部分一致）
     * @return 該当するユーザーリスト
     * @throws IllegalArgumentException ユーザー名がnullまたは空文字の場合
     */
    @Query("SELECT u FROM User u WHERE u.username LIKE %:username% ORDER BY u.username")
    List<User> findByUsernameContaining(@Param("username") @NotBlank(message = "ユーザー名は必須です") String username);

    /**
     * 名前（姓名）の部分一致検索を行います。
     * 
     * @param name 検索キーワード（部分一致）
     * @return 該当するユーザーリスト
     * @throws IllegalArgumentException 名前がnullまたは空文字の場合
     */
    @Query("SELECT u FROM User u WHERE u.name LIKE %:name% ORDER BY u.username")
    List<User> findByNameContaining(@Param("name") @NotBlank(message = "氏名は必須です") String name);

    /**
     * 作成日時が指定された期間内のユーザーを取得します。
     * 
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @return 該当するユーザーリスト
     * @throws IllegalArgumentException 開始日時または終了日時がnullの場合
     */
    @Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate ORDER BY u.createdAt")
    List<User> findUsersByCreatedAtBetween(@Param("startDate") @NotNull(message = "開始日時は必須です") java.time.LocalDateTime startDate,
                                          @Param("endDate") @NotNull(message = "終了日時は必須です") java.time.LocalDateTime endDate);

}
