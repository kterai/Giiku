/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.giiku.domain.repository;

import jp.co.apsa.giiku.domain.entity.ApprovalRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 承認ルートリポジトリインターフェース
 * 
 * <p>承認ルートエンティティに対するデータアクセス操作を提供します。
 * Hexagonal Architectureのポート（インターフェース）として機能し、
 * ドメイン層とインフラストラクチャ層の境界を定義します。</p>
 * 
 * <p>主な機能：</p>
 * <ul>
 *   <li>基本的なCRUD操作</li>
 *   <li>申請種別による検索</li>
 *   <li>ステップ順序による検索</li>
 *   <li>承認者権限による検索</li>
 *   <li>承認者部署による検索</li>
 *   <li>アクティブルートの検索</li>
 *   <li>必須ステップの検索</li>
 * </ul>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface ApprovalRouteRepository extends JpaRepository<ApprovalRoute, Long> {

    /**
     * 申請種別IDで承認ルートを検索します。
     * 
     * @param applicationTypeId 申請種別ID
     * @return 該当する承認ルートのリスト
     * @throws IllegalArgumentException 申請種別IDがnullの場合
     */
    @Query("SELECT ar FROM ApprovalRoute ar WHERE ar.applicationTypeId = :applicationTypeId ORDER BY ar.stepOrder ASC")
    List<ApprovalRoute> findByApplicationTypeId(@Param("applicationTypeId") Long applicationTypeId);

    /**
     * 申請種別IDとアクティブ状態で承認ルートを検索します。
     * 
     * @param applicationTypeId 申請種別ID
     * @param isActive アクティブ状態（true: アクティブ, false: 非アクティブ）
     * @return 該当する承認ルートのリスト
     * @throws IllegalArgumentException パラメータがnullの場合
     */
    @Query("SELECT ar FROM ApprovalRoute ar WHERE ar.applicationTypeId = :applicationTypeId AND ar.active = :active ORDER BY ar.stepOrder ASC")
    List<ApprovalRoute> findByApplicationTypeIdAndActive(@Param("applicationTypeId") Long applicationTypeId,
                                                        @Param("active") Boolean active);

    /**
     * ステップ順序で承認ルートを検索します。
     * 
     * @param stepOrder ステップ順序
     * @return 該当する承認ルートのリスト
     * @throws IllegalArgumentException ステップ順序がnullまたは負の値の場合
     */
    @Query("SELECT ar FROM ApprovalRoute ar WHERE ar.stepOrder = :stepOrder")
    List<ApprovalRoute> findByStepOrder(@Param("stepOrder") Integer stepOrder);

    /**
     * 承認者権限で承認ルートを検索します。
     * 
     * @param approverAuthority 承認者権限
     * @return 該当する承認ルートのリスト
     * @throws IllegalArgumentException 承認者権限がnullまたは空文字の場合
     */
    @Query("SELECT ar FROM ApprovalRoute ar WHERE ar.approverRole = :approverRole ORDER BY ar.stepOrder ASC")
    List<ApprovalRoute> findByApproverRole(@Param("approverRole") String approverRole);

    /**
     * 承認者部署で承認ルートを検索します。
     * 
     * @param approverDepartment 承認者部署
     * @return 該当する承認ルートのリスト
     * @throws IllegalArgumentException 承認者部署がnullまたは空文字の場合
     */
    @Query("SELECT ar FROM ApprovalRoute ar WHERE ar.approverDepartmentId = :approverDepartmentId ORDER BY ar.stepOrder ASC")
    List<ApprovalRoute> findByApproverDepartmentId(@Param("approverDepartmentId") Long approverDepartmentId);

    /**
     * アクティブな承認ルートを全て検索します。
     * 
     * @return アクティブな承認ルートのリスト
     */
    @Query("SELECT ar FROM ApprovalRoute ar WHERE ar.active = true ORDER BY ar.applicationTypeId ASC, ar.stepOrder ASC")
    List<ApprovalRoute> findAllActiveRoutes();

    /**
     * 必須ステップの承認ルートを検索します。
     * 
     * @return 必須ステップの承認ルートのリスト
     */
    @Query("SELECT ar FROM ApprovalRoute ar WHERE ar.required = true ORDER BY ar.applicationTypeId ASC, ar.stepOrder ASC")
    List<ApprovalRoute> findAllRequiredSteps();

    /**
     * 申請種別IDと必須フラグで承認ルートを検索します。
     * 
     * @param applicationTypeId 申請種別ID
     * @param isRequired 必須フラグ（true: 必須, false: 任意）
     * @return 該当する承認ルートのリスト
     * @throws IllegalArgumentException パラメータがnullの場合
     */
    @Query("SELECT ar FROM ApprovalRoute ar WHERE ar.applicationTypeId = :applicationTypeId AND ar.required = :required ORDER BY ar.stepOrder ASC")
    List<ApprovalRoute> findByApplicationTypeIdAndRequired(@Param("applicationTypeId") Long applicationTypeId,
                                                          @Param("required") Boolean required);

    /**
     * 申請種別IDと承認者権限で承認ルートを検索します。
     * 
     * @param applicationTypeId 申請種別ID
     * @param approverAuthority 承認者権限
     * @return 該当する承認ルートのリスト
     * @throws IllegalArgumentException パラメータがnullまたは空文字の場合
     */
    @Query("SELECT ar FROM ApprovalRoute ar WHERE ar.applicationTypeId = :applicationTypeId AND ar.approverRole = :approverRole ORDER BY ar.stepOrder ASC")
    List<ApprovalRoute> findByApplicationTypeIdAndApproverRole(@Param("applicationTypeId") Long applicationTypeId,
                                                              @Param("approverRole") String approverRole);

    /**
     * 申請種別IDと承認者部署で承認ルートを検索します。
     * 
     * @param applicationTypeId 申請種別ID
     * @param approverDepartment 承認者部署
     * @return 該当する承認ルートのリスト
     * @throws IllegalArgumentException パラメータがnullまたは空文字の場合
     */
    @Query("SELECT ar FROM ApprovalRoute ar WHERE ar.applicationTypeId = :applicationTypeId AND ar.approverDepartmentId = :approverDepartmentId ORDER BY ar.stepOrder ASC")
    List<ApprovalRoute> findByApplicationTypeIdAndApproverDepartmentId(@Param("applicationTypeId") Long applicationTypeId,
                                                                      @Param("approverDepartmentId") Long approverDepartmentId);

    /**
     * 申請種別IDで最大ステップ順序を取得します。
     * 
     * @param applicationTypeId 申請種別ID
     * @return 最大ステップ順序（該当データがない場合は0）
     * @throws IllegalArgumentException 申請種別IDがnullの場合
     */
    @Query("SELECT COALESCE(MAX(ar.stepOrder), 0) FROM ApprovalRoute ar WHERE ar.applicationTypeId = :applicationTypeId")
    Integer findMaxStepOrderByApplicationTypeId(@Param("applicationTypeId") Long applicationTypeId);

    /**
     * 申請種別IDとステップ順序で承認ルートを検索します。
     * 
     * @param applicationTypeId 申請種別ID
     * @param stepOrder ステップ順序
     * @return 該当する承認ルート（存在しない場合はOptional.empty()）
     * @throws IllegalArgumentException パラメータがnullまたは負の値の場合
     */
    @Query("SELECT ar FROM ApprovalRoute ar WHERE ar.applicationTypeId = :applicationTypeId AND ar.stepOrder = :stepOrder")
    Optional<ApprovalRoute> findByApplicationTypeIdAndStepOrder(@Param("applicationTypeId") Long applicationTypeId, 
                                                               @Param("stepOrder") Integer stepOrder);

    /**
     * 承認者権限と部署の組み合わせで承認ルートを検索します。
     * 
     * @param approverAuthority 承認者権限
     * @param approverDepartment 承認者部署
     * @return 該当する承認ルートのリスト
     * @throws IllegalArgumentException パラメータがnullまたは空文字の場合
     */
    @Query("SELECT ar FROM ApprovalRoute ar WHERE ar.approverRole = :approverRole AND ar.approverDepartmentId = :approverDepartmentId ORDER BY ar.applicationTypeId ASC, ar.stepOrder ASC")
    List<ApprovalRoute> findByApproverRoleAndDepartmentId(@Param("approverRole") String approverRole,
                                                         @Param("approverDepartmentId") Long approverDepartmentId);

    /**
     * 申請種別IDでアクティブかつ必須の承認ルートを検索します。
     * 
     * @param applicationTypeId 申請種別ID
     * @return 該当する承認ルートのリスト
     * @throws IllegalArgumentException 申請種別IDがnullの場合
     */
    @Query("SELECT ar FROM ApprovalRoute ar WHERE ar.applicationTypeId = :applicationTypeId AND ar.active = true AND ar.required = true ORDER BY ar.stepOrder ASC")
    List<ApprovalRoute> findActiveRequiredRoutesByApplicationTypeId(@Param("applicationTypeId") Long applicationTypeId);

    /**
     * 指定された申請種別を参照する承認ルートが存在するか確認します。
     *
     * @param applicationTypeId 申請種別ID
     * @return 存在する場合true
     */
    boolean existsByApplicationTypeId(Long applicationTypeId);

    /**
     * 指定された部署を参照する承認ルートが存在するか確認します。
     *
     * @param approverDepartmentId 承認者部署ID
     * @return 存在する場合true
     */
    boolean existsByApproverDepartmentId(Long approverDepartmentId);
}
