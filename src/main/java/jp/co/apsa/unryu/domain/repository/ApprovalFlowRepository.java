package jp.co.apsa.unryu.domain.repository;

import jp.co.apsa.unryu.domain.entity.ApprovalFlow;
import jp.co.apsa.unryu.domain.entity.ApplicationType;
import jp.co.apsa.unryu.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 承認フローリポジトリインターフェース
 * 
 * 新人エンジニア向け学習ポイント：
 * - リポジトリパターンの実装
 * - Spring Data JPAのクエリメソッド命名規則
 * - カスタムクエリの記述方法
 * - ドメイン駆動設計におけるリポジトリの役割
 * 
 * @author 株式会社アプサ
 * @since 2025
 * @version 1.0
 */
@Repository
public interface ApprovalFlowRepository extends JpaRepository<ApprovalFlow, Long> {

    /**
     * 申請種別と部署で承認フローを検索
     * 
     * 学習ポイント：
     * - 複合条件での検索
     * - null許容フィールドの扱い（部署が未指定の場合の全社共通フロー）
     * 
     * @param applicationType 申請種別
     * @param department 部署（nullの場合は全社共通）
     * @return 承認フロー
     */
    @Query("SELECT af FROM ApprovalFlow af " +
           "WHERE af.applicationType = :applicationType " +
           "AND (af.department = :department OR af.department IS NULL) " +
           "AND af.isActive = true " +
           "ORDER BY af.department DESC NULLS LAST, af.displayOrder ASC")
    Optional<ApprovalFlow> findByApplicationTypeAndDepartment(
        @Param("applicationType") ApplicationType applicationType,
        @Param("department") Department department);

    /**
     * 申請種別で有効な承認フローを全て取得
     * 
     * @param applicationType 申請種別
     * @return 承認フローリスト
     */
    List<ApprovalFlow> findByApplicationTypeAndIsActiveTrueOrderByDisplayOrderAsc(
        ApplicationType applicationType);

    /**
     * 部署で有効な承認フローを全て取得
     * 
     * @param department 部署
     * @return 承認フローリスト
     */
    List<ApprovalFlow> findByDepartmentAndIsActiveTrueOrderByDisplayOrderAsc(
        Department department);

    /**
     * 全社共通の承認フローを取得
     * 
     * @return 全社共通承認フローリスト
     */
    List<ApprovalFlow> findByDepartmentIsNullAndIsActiveTrueOrderByDisplayOrderAsc();

    /**
     * 承認フロー名で検索（部分一致）
     * 
     * @param flowName フロー名
     * @return 承認フローリスト
     */
    List<ApprovalFlow> findByFlowNameContainingIgnoreCaseAndIsActiveTrue(String flowName);

    /**
     * 作成者で承認フローを検索
     * 
     * @param createdBy 作成者ID
     * @return 承認フローリスト
     */
    List<ApprovalFlow> findByCreatedByOrderByCreatedAtDesc(Long createdBy);

    /**
     * 承認フローの重複チェック
     * 指定された申請種別・部署の組み合わせで既に承認フローが存在するかチェック
     * 
     * @param applicationType 申請種別
     * @param department 部署
     * @param excludeId 除外するID（更新時に自分自身を除外）
     * @return 存在する場合true
     */
    @Query("SELECT COUNT(af) > 0 FROM ApprovalFlow af " +
           "WHERE af.applicationType = :applicationType " +
           "AND af.department = :department " +
           "AND af.isActive = true " +
           "AND (:excludeId IS NULL OR af.id != :excludeId)")
    boolean existsByApplicationTypeAndDepartmentExcludingId(
        @Param("applicationType") ApplicationType applicationType,
        @Param("department") Department department,
        @Param("excludeId") Long excludeId);

    /**
     * 使用中の承認フローをチェック
     * 削除前に申請で使用中でないかチェック
     * 
     * @param approvalFlowId 承認フローID
     * @return 使用中の場合true
     */
    @Query("""
            SELECT COUNT(a) > 0
            FROM Application a
            WHERE EXISTS (
                SELECT 1 FROM ApprovalFlow af
                WHERE af.id = :approvalFlowId
                  AND af.applicationTypeId = a.applicationTypeId
                  AND (
                        (af.departmentId IS NULL AND a.departmentId IS NULL)
                     OR af.departmentId = a.departmentId
                  )
            )
            """)
    boolean isUsedInApplications(@Param("approvalFlowId") Long approvalFlowId);

    /**
     * 表示順序の最大値を取得
     * 新規作成時の表示順序決定に使用
     * 
     * @param applicationType 申請種別
     * @param department 部署
     * @return 最大表示順序
     */
    @Query("SELECT COALESCE(MAX(af.displayOrder), 0) FROM ApprovalFlow af " +
           "WHERE af.applicationType = :applicationType " +
           "AND af.department = :department")
    Integer findMaxDisplayOrder(
        @Param("applicationType") ApplicationType applicationType,
        @Param("department") Department department);
}
