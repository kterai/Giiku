package jp.co.apsa.unryu.domain.repository;

import jp.co.apsa.unryu.domain.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 役職リポジトリインターフェース
 * 
 * 新人エンジニア向け学習ポイント：
 * - マスタデータの管理方法
 * - 階層構造を持つデータの扱い
 * - 有効/無効フラグによるソフト削除
 * - 表示順序による並び制御
 * 
 * @author 株式会社アプサ
 * @since 2025
 * @version 1.0
 */
@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    /**
     * 有効な役職を表示順序でソートして取得
     * 
     * 学習ポイント：
     * - マスタデータの基本的な取得方法
     * - 論理削除（ソフト削除）の実装
     * - 表示順序による制御
     * 
     * @return 有効な役職リスト
     */
    List<Position> findByIsActiveTrueOrderByDisplayOrderAsc();

    /**
     * 役職コードで検索
     * 
     * @param positionCode 役職コード
     * @return 役職
     */
    Optional<Position> findByPositionCodeAndIsActiveTrue(String positionCode);

    /**
     * 役職名で検索（部分一致、大文字小文字区別なし）
     * 
     * @param positionName 役職名
     * @return 役職リスト
     */
    List<Position> findByPositionNameContainingIgnoreCaseAndIsActiveTrueOrderByDisplayOrderAsc(
        String positionName);

    /**
     * 階層レベルで役職を検索
     * 
     * 学習ポイント：
     * - 組織階層の管理
     * - 数値による階層表現
     * - 階層に基づく権限制御の基礎
     * 
     * @param hierarchyLevel 階層レベル
     * @return 役職リスト
     */
    List<Position> findByHierarchyLevelAndIsActiveTrueOrderByDisplayOrderAsc(
        Integer hierarchyLevel);

    /**
     * 指定レベル以上の役職を取得
     * 承認権限チェックなどで使用
     * 
     * @param hierarchyLevel 最小階層レベル
     * @return 役職リスト
     */
    List<Position> findByHierarchyLevelGreaterThanEqualAndIsActiveTrueOrderByHierarchyLevelDescDisplayOrderAsc(
        Integer hierarchyLevel);

    /**
     * 管理職フラグで役職を検索
     * 
     * @param isManager 管理職フラグ
     * @return 役職リスト
     */
    List<Position> findByIsManagerAndIsActiveTrueOrderByHierarchyLevelDescDisplayOrderAsc(
        Boolean isManager);

    /**
     * 役職コードの重複チェック
     * 
     * @param positionCode 役職コード
     * @param excludeId 除外するID（更新時に自分自身を除外）
     * @return 重複する場合true
     */
    @Query("SELECT COUNT(p) > 0 FROM Position p " +
           "WHERE p.positionCode = :positionCode " +
           "AND p.isActive = true " +
           "AND (:excludeId IS NULL OR p.id != :excludeId)")
    boolean existsByPositionCodeExcludingId(
        @Param("positionCode") String positionCode,
        @Param("excludeId") Long excludeId);

    /**
     * 使用中の役職をチェック
     * 削除前にユーザーで使用中でないかチェック
     * 
     * @param positionId 役職ID
     * @return 使用中の場合true
     */
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.position.id = :positionId")
    boolean isUsedByUsers(@Param("positionId") Long positionId);

    /**
     * 表示順序の最大値を取得
     * 新規作成時の表示順序決定に使用
     * 
     * @return 最大表示順序
     */
    @Query("SELECT COALESCE(MAX(p.displayOrder), 0) FROM Position p WHERE p.isActive = true")
    Integer findMaxDisplayOrder();

    /**
     * 階層レベル範囲内の役職を取得
     * 
     * @param minLevel 最小階層レベル
     * @param maxLevel 最大階層レベル
     * @return 役職リスト
     */
    List<Position> findByHierarchyLevelBetweenAndIsActiveTrueOrderByHierarchyLevelAscDisplayOrderAsc(
        Integer minLevel, Integer maxLevel);

    /**
     * 役職統計情報を取得
     * 各役職に所属するユーザー数を取得
     * 
     * @return 役職別ユーザー数
     */
    @Query("SELECT p.positionName, COUNT(u) FROM Position p " +
           "LEFT JOIN User u ON u.position.id = p.id AND u.active = true " +
           "WHERE p.isActive = true " +
           "GROUP BY p.id, p.positionName, p.hierarchyLevel, p.displayOrder " +
           "ORDER BY p.hierarchyLevel DESC, p.displayOrder ASC")
    List<Object[]> findPositionUserCounts();

    /**
     * 承認権限を持つ役職を取得
     * 承認フロー設定時に使用
     * 
     * @return 承認権限を持つ役職リスト
     */
    @Query("SELECT p FROM Position p " +
           "WHERE p.isActive = true " +
           "AND (p.isManager = true OR p.hierarchyLevel >= :minApprovalLevel) " +
           "ORDER BY p.hierarchyLevel DESC, p.displayOrder ASC")
    List<Position> findPositionsWithApprovalAuthority(@Param("minApprovalLevel") Integer minApprovalLevel);
}
