package jp.co.apsa.giiku.domain.repository;

import jp.co.apsa.giiku.domain.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 監査ログリポジトリインターフェース
 * 
 * 新人エンジニア向け学習ポイント：
 * - 監査ログの設計と実装
 * - 大量データの効率的な検索方法
 * - ページネーション機能の実装
 * - 日時範囲検索の実装
 * - セキュリティ監査機能の重要性
 * 
 * @author 株式会社アプサ
 * @since 2025
 * @version 1.0
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    /**
     * ユーザーIDで監査ログを検索（ページネーション対応）
     * 
     * 学習ポイント：
     * - 大量データの効率的な表示
     * - Pageableによるページネーション
     * - 降順ソート（最新順）
     * 
     * @param userId ユーザーID
     * @param pageable ページネーション情報
     * @return 監査ログのページ
     */
    Page<AuditLog> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * テーブル名とレコードIDで監査ログを検索
     *
     * @param tableName テーブル名
     * @param recordId レコードID
     * @param pageable ページネーション情報
     * @return 監査ログのページ
     */
    Page<AuditLog> findByTableNameAndRecordIdOrderByCreatedAtDesc(
        String tableName, String recordId, Pageable pageable);

    /**
     * イベントタイプで監査ログを検索
     * 
     * @param eventType イベントタイプ
     * @param pageable ページネーション情報
     * @return 監査ログのページ
     */
    Page<AuditLog> findByEventTypeOrderByCreatedAtDesc(String eventType, Pageable pageable);

    /**
     * 日時範囲で監査ログを検索
     * 
     * 学習ポイント：
     * - 日時範囲検索の実装
     * - パフォーマンスを考慮したクエリ
     * - インデックスの重要性
     * 
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @param pageable ページネーション情報
     * @return 監査ログのページ
     */
    Page<AuditLog> findByCreatedAtBetweenOrderByCreatedAtDesc(
        LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * 複合条件での監査ログ検索
     * 
     * @param userId ユーザーID（null許可）
     * @param tableName テーブル名（null許可）
     * @param eventType イベントタイプ（null許可）
     * @param startDate 開始日時（null許可）
     * @param endDate 終了日時（null許可）
     * @param pageable ページネーション情報
     * @return 監査ログのページ
     */
    @Query("SELECT al FROM AuditLog al " +
           "WHERE (:userId IS NULL OR al.userId = :userId) " +
           "AND (:tableName IS NULL OR al.tableName = :tableName) " +
           "AND (:eventType IS NULL OR al.eventType = :eventType) " +
           "AND (:startDate IS NULL OR al.createdAt >= :startDate) " +
           "AND (:endDate IS NULL OR al.createdAt <= :endDate) " +
           "ORDER BY al.createdAt DESC")
    Page<AuditLog> findByComplexConditions(
        @Param("userId") Long userId,
        @Param("tableName") String tableName,
        @Param("eventType") String eventType,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        Pageable pageable);

    /**
     * IPアドレスで監査ログを検索
     * セキュリティ監査でのIP追跡用
     * 
     * @param ipAddress IPアドレス
     * @param pageable ページネーション情報
     * @return 監査ログのページ
     */
    Page<AuditLog> findByIpAddressOrderByCreatedAtDesc(String ipAddress, Pageable pageable);

    /**
     * セッションIDで監査ログを検索
     * 
     * @param sessionId セッションID
     * @return 監査ログリスト
     */
    List<AuditLog> findBySessionIdOrderByCreatedAtAsc(String sessionId);

    /**
     * 特定期間の監査ログ件数を取得
     * 統計・レポート用
     * 
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @return ログ件数
     */
    @Query("SELECT COUNT(al) FROM AuditLog al " +
           "WHERE al.createdAt BETWEEN :startDate AND :endDate")
    long countByCreatedAtBetween(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate);

    /**
     * イベントタイプ別の集計データを取得
     * 
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @return イベントタイプ別件数
     */
    @Query("SELECT al.eventType, COUNT(al) AS cnt FROM AuditLog al " +
           "WHERE al.createdAt BETWEEN :startDate AND :endDate " +
           "GROUP BY al.eventType " +
           "ORDER BY cnt DESC")
    List<Object[]> countByEventTypeAndCreatedAtBetween(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate);

    /**
     * ユーザー別の活動集計データを取得
     * 
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @return ユーザー別活動件数
     */
    @Query("SELECT al.userId, COUNT(al) AS cnt FROM AuditLog al " +
           "WHERE al.createdAt BETWEEN :startDate AND :endDate " +
           "GROUP BY al.userId " +
           "ORDER BY cnt DESC")
    List<Object[]> countByUserIdAndCreatedAtBetween(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate);

    /**
     * 古い監査ログを削除（データ保持ポリシー用）
     * 
     * 学習ポイント：
     * - データ保持ポリシーの実装
     * - 大量データの効率的な削除
     * - パフォーマンスを考慮したバッチ処理
     * 
     * @param cutoffDate 削除対象の境界日時
     * @return 削除された件数
     */
    @Query("DELETE FROM AuditLog al WHERE al.createdAt < :cutoffDate")
    int deleteByCreatedAtBefore(@Param("cutoffDate") LocalDateTime cutoffDate);

    /**
     * 最新の監査ログを取得
     * システム状態確認用
     * 
     * @param limit 件数
     * @return 最新の監査ログリスト
     */
    @Query(value = "SELECT * FROM audit_logs ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<AuditLog> findLatestLogs(@Param("limit") int limit);
}
