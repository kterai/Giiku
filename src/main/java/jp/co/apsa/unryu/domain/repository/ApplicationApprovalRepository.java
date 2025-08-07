/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.unryu.domain.repository;

import jp.co.apsa.unryu.domain.entity.ApplicationApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 承認履歴リポジトリインターフェース
 * 
 * <p>承認履歴エンティティに対するデータアクセス操作を定義します。
 * Spring Data JPAを使用してCRUD操作およびカスタムクエリを提供します。
 * Hexagonal Architectureのポート（インターフェース）として機能します。</p>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface ApplicationApprovalRepository extends JpaRepository<ApplicationApproval, Long> {

    /**
     * 申請IDで承認履歴を検索します
     * 
     * @param applicationId 申請ID
     * @return 該当する承認履歴のリスト
     */
    List<ApplicationApproval> findByApplicationId(Long applicationId);

    /**
     * 申請IDで承認履歴を承認ステップ順で検索します
     * 
     * @param applicationId 申請ID
     * @return 承認ステップ順でソートされた承認履歴のリスト
     */
    List<ApplicationApproval> findByApplicationIdOrderByStepOrder(Long applicationId);

    /**
     * 承認者IDで承認履歴を検索します
     * 
     * @param approverId 承認者ID
     * @return 該当する承認履歴のリスト
     */
    List<ApplicationApproval> findByApproverId(Long approverId);

    /**
     * 承認者IDで承認履歴を承認日時の降順で検索します
     * 
     * @param approverId 承認者ID
     * @return 承認日時降順でソートされた承認履歴のリスト
     */
    List<ApplicationApproval> findByApproverIdOrderByApprovedAtDesc(Long approverId);

    /**
     * 承認ステップで承認履歴を検索します
     * 
     * @param stepOrder 承認ステップ
     * @return 該当する承認履歴のリスト
     */
    List<ApplicationApproval> findByStepOrder(Integer stepOrder);

    /**
     * 承認ステータスで承認履歴を検索します
     * 
     * @param status 承認ステータス
     * @return 該当する承認履歴のリスト
     */
    List<ApplicationApproval> findByStatus(String status);

    /**
     * 承認ステータスで承認履歴を承認日時の昇順で検索します
     * 
     * @param status 承認ステータス
     * @return 承認日時昇順でソートされた承認履歴のリスト
     */
    List<ApplicationApproval> findByStatusOrderByApprovedAt(String status);

    /**
     * 承認日時の範囲で承認履歴を検索します
     * 
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @return 該当する承認履歴のリスト
     */
    List<ApplicationApproval> findByApprovedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 承認日時の範囲で承認履歴を承認日時の降順で検索します
     * 
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @return 承認日時降順でソートされた承認履歴のリスト
     */
    List<ApplicationApproval> findByApprovedAtBetweenOrderByApprovedAtDesc(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 申請IDと承認ステップで承認履歴を検索します
     * 
     * @param applicationId 申請ID
     * @param stepOrder 承認ステップ
     * @return 該当する承認履歴のOptional
     */
    Optional<ApplicationApproval> findByApplicationIdAndStepOrder(Long applicationId, Integer stepOrder);

    /**
     * 申請IDと承認者IDで承認履歴を検索します
     * 
     * @param applicationId 申請ID
     * @param approverId 承認者ID
     * @return 該当する承認履歴のリスト
     */
    List<ApplicationApproval> findByApplicationIdAndApproverId(Long applicationId, Long approverId);

    /**
     * 承認者IDとステータスで承認履歴を検索します
     * 
     * @param approverId 承認者ID
     * @param status 承認ステータス
     * @return 該当する承認履歴のリスト
     */
    List<ApplicationApproval> findByApproverIdAndStatus(Long approverId, String status);

    /**
     * 代理承認者IDで承認履歴を検索します
     *
     * @param delegatedTo 代理承認者ID
     * @return 該当する承認履歴のリスト
     */
    List<ApplicationApproval> findByDelegatedTo(Long delegatedTo);

    /**
     * 代理承認者IDで承認履歴を承認日時の降順で検索します
     *
     * @param delegatedTo 代理承認者ID
     * @return 承認日時降順でソートされた承認履歴のリスト
     */
    List<ApplicationApproval> findByDelegatedToOrderByApprovedAtDesc(Long delegatedTo);

    /**
     * 代理承認フラグで承認履歴を検索します
     * 
     * @param isDelegated 代理承認フラグ
     * @return 該当する承認履歴のリスト
     */
    List<ApplicationApproval> findByIsDelegated(Boolean isDelegated);

    /**
     * 申請の承認フローを追跡します（全承認ステップの履歴）
     * 
     * @param applicationId 申請ID
     * @return 承認フローの詳細情報
     */
    @Query(value = """
        SELECT
            aa.id,
            aa.application_id,
            aa.step_order,
            aa.approver_id,
            aa.delegated_to,
            aa.status,
            aa.approved_at,
            aa.comment,
            aa.is_delegated,
            aa.created_at,
            aa.updated_at,
            CASE 
                WHEN aa.status = 'PENDING' THEN '承認待ち'
                WHEN aa.status = 'APPROVED' THEN '承認済み'
                WHEN aa.status = 'REJECTED' THEN '却下'
                WHEN aa.status = 'SKIPPED' THEN 'スキップ'
                ELSE '不明'
            END as status_display,
            CASE 
                WHEN aa.is_delegated = true THEN '代理承認'
                ELSE '通常承認'
            END as approval_type
        FROM application_approvals aa
        WHERE aa.application_id = :applicationId
        ORDER BY aa.step_order ASC
        """, nativeQuery = true)
    List<Object[]> getApprovalFlow(@Param("applicationId") Long applicationId);

    /**
     * 承認者の承認統計情報を取得します
     * 
     * @param approverId 承認者ID
     * @return 承認統計情報
     */
    @Query(value = """
        SELECT 
            COUNT(*) as total_approvals,
            COUNT(CASE WHEN status = 'APPROVED' THEN 1 END) as approved_count,
            COUNT(CASE WHEN status = 'REJECTED' THEN 1 END) as rejected_count,
            COUNT(CASE WHEN status = 'PENDING' THEN 1 END) as pending_count,
            COUNT(CASE WHEN is_delegated = true THEN 1 END) as delegated_count,
            AVG(CASE WHEN status IN ('APPROVED', 'REJECTED') AND approved_at IS NOT NULL
                THEN EXTRACT(EPOCH FROM (approved_at - created_at))/3600 END) as avg_processing_hours
        FROM application_approvals 
        WHERE approver_id = :approverId
        """, nativeQuery = true)
    Object[] getApprovalStatsByApprover(@Param("approverId") Long approverId);

    /**
     * 指定期間内の承認処理時間統計を取得します
     * 
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @return 承認処理時間統計
     */
    @Query(value = """
        SELECT
            step_order,
            COUNT(*) as step_count,
            AVG(CASE WHEN approved_at IS NOT NULL
                THEN EXTRACT(EPOCH FROM (approved_at - created_at))/3600 END) as avg_hours,
            MIN(CASE WHEN approved_at IS NOT NULL
                THEN EXTRACT(EPOCH FROM (approved_at - created_at))/3600 END) as min_hours,
            MAX(CASE WHEN approved_at IS NOT NULL
                THEN EXTRACT(EPOCH FROM (approved_at - created_at))/3600 END) as max_hours
        FROM application_approvals
        WHERE created_at BETWEEN :startDate AND :endDate
        AND status IN ('APPROVED', 'REJECTED')
        GROUP BY step_order
        ORDER BY step_order
        """, nativeQuery = true)
    List<Object[]> getProcessingTimeStatsByStep(@Param("startDate") LocalDateTime startDate, 
                                               @Param("endDate") LocalDateTime endDate);

    /**
     * 承認待ちの承認履歴を優先度順で取得します
     * 
     * @return 優先度順でソートされた承認待ち履歴のリスト
     */
    @Query("""
        SELECT aa FROM ApplicationApproval aa 
        JOIN Application a ON aa.applicationId = a.id 
        WHERE aa.status = 'PENDING' 
        ORDER BY a.priority DESC, aa.createdAt ASC
        """)
    List<ApplicationApproval> findPendingApprovalsByPriority();

    /**
     * 指定承認者の承認待ち履歴を優先度順で取得します
     * 
     * @param approverId 承認者ID
     * @return 優先度順でソートされた承認待ち履歴のリスト
     */
    @Query("""
        SELECT aa FROM ApplicationApproval aa 
        JOIN Application a ON aa.applicationId = a.id 
        WHERE aa.approverId = :approverId AND aa.status = 'PENDING' 
        ORDER BY a.priority DESC, aa.createdAt ASC
        """)
    List<ApplicationApproval> findPendingApprovalsByApproverAndPriority(@Param("approverId") Long approverId);

    /**
     * 承認ステップ別の承認状況を取得します
     * 
     * @param applicationId 申請ID
     * @return 承認ステップ別状況のリスト
     */
    @Query(value = """
        WITH steps AS (
            SELECT DISTINCT step_order
            FROM application_approvals
            WHERE application_id = :applicationId
        )
        SELECT
            s.step_order,
            COALESCE(aa.status, 'NOT_STARTED') as status,
            aa.approver_id,
            aa.delegated_to,
            aa.approved_at,
            aa.is_delegated
        FROM steps s
        LEFT JOIN application_approvals aa ON s.step_order = aa.step_order
            AND aa.application_id = :applicationId
        ORDER BY s.step_order
        """, nativeQuery = true)
    List<Object[]> getApprovalStepStatus(@Param("applicationId") Long applicationId);

    /**
     * 長期間承認待ちの履歴を検索します
     * 
     * @param hours 時間数（この時間以上承認待ち）
     * @return 長期間承認待ちの履歴のリスト
     */
    @Query(value = """
        SELECT aa.* FROM application_approvals aa
        WHERE aa.status = 'PENDING' 
        AND aa.created_at < NOW() - INTERVAL ':hours HOUR'
        ORDER BY aa.created_at ASC
        """, nativeQuery = true)
    List<ApplicationApproval> findLongPendingApprovals(@Param("hours") Integer hours);

    /**
     * 代理承認の履歴を検索します
     * 
     * @param originalApproverId 元の承認者ID
     * @param delegatedTo 代理承認者ID
     * @return 代理承認履歴のリスト
     */
    List<ApplicationApproval> findByApproverIdAndDelegatedToAndIsDelegated(
        Long originalApproverId, Long delegatedTo, Boolean isDelegated);

    /**
     * 月別承認件数を取得します
     * 
     * @param year 年
     * @return 月別承認件数のリスト
     */
    @Query(value = """
        SELECT
            EXTRACT(MONTH FROM approved_at) as month,
            COUNT(*) as approval_count,
            COUNT(CASE WHEN status = 'APPROVED' THEN 1 END) as approved_count,
            COUNT(CASE WHEN status = 'REJECTED' THEN 1 END) as rejected_count
        FROM application_approvals
        WHERE EXTRACT(YEAR FROM approved_at) = :year
        AND approved_at IS NOT NULL
        GROUP BY EXTRACT(MONTH FROM approved_at)
        ORDER BY month
        """, nativeQuery = true)
    List<Object[]> getMonthlyApprovalCounts(@Param("year") Integer year);

    /**
     * 承認ステップ別統計を取得します
     * 
     * @return 承認ステップ別統計のリスト
     */
    @Query(value = """
        SELECT
            step_order,
            COUNT(*) as total_count,
            COUNT(CASE WHEN status = 'APPROVED' THEN 1 END) as approved_count,
            COUNT(CASE WHEN status = 'REJECTED' THEN 1 END) as rejected_count,
            COUNT(CASE WHEN status = 'PENDING' THEN 1 END) as pending_count,
            AVG(CASE WHEN approved_at IS NOT NULL
                THEN EXTRACT(EPOCH FROM (approved_at - created_at))/3600 END) as avg_processing_hours
        FROM application_approvals
        GROUP BY step_order
        ORDER BY step_order
        """, nativeQuery = true)
    List<Object[]> getApprovalStatsByStep();

    /**
     * 承認履歴の存在確認を行います
     * 
     * @param applicationId 申請ID
     * @param stepOrder 承認ステップ
     * @return 存在する場合true
     */
    boolean existsByApplicationIdAndStepOrder(Long applicationId, Integer stepOrder);

    /**
     * 指定された申請の承認完了確認を行います
     * 
     * @param applicationId 申請ID
     * @return 全ステップが承認済みの場合true
     */
    @Query("""
        SELECT CASE WHEN COUNT(aa) = COUNT(CASE WHEN aa.status = 'APPROVED' THEN 1 END) 
               THEN true ELSE false END
        FROM ApplicationApproval aa 
        WHERE aa.applicationId = :applicationId
        """)
    boolean isFullyApproved(@Param("applicationId") Long applicationId);

    /**
     * 指定された申請の承認件数を取得します
     * 
     * @param applicationId 申請ID
     * @param status 承認ステータス
     * @return 承認件数
     */
    long countByApplicationIdAndStatus(Long applicationId, String status);

    /**
     * 指定された承認者の未処理承認件数を取得します
     * 
     * @param approverId 承認者ID
     * @return 未処理承認件数
     */
    long countByApproverIdAndStatus(Long approverId, String status);

    /**
     * 代理承認件数を取得します
     * 
     * @param delegatedTo 代理承認者ID
     * @return 代理承認件数
     */
    long countByDelegatedToAndIsDelegated(Long delegatedTo, Boolean isDelegated);
}
