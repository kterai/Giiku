/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.giiku.domain.repository;

import jp.co.apsa.giiku.domain.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 申請リポジトリインターフェース
 * 
 * <p>申請エンティティに対するデータアクセス操作を定義します。
 * Spring Data JPAを使用してCRUD操作およびカスタムクエリを提供します。
 * Hexagonal Architectureのポート（インターフェース）として機能します。</p>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    /**
     * 申請者IDで申請を検索します
     * 
     * @param applicantId 申請者ID
     * @return 該当する申請のリスト
     */
    List<Application> findByApplicantId(Long applicantId);

    /**
     * 申請者IDで申請を申請日時の降順で検索します
     * 
     * @param applicantId 申請者ID
     * @return 申請日時降順でソートされた申請のリスト
     */
    List<Application> findByApplicantIdOrderByApplicationDateDesc(Long applicantId);

    /**
     * 申請ステータスで申請を検索します
     * 
     * @param status 申請ステータス
     * @return 該当する申請のリスト
     */
    List<Application> findByStatus(String status);

    /**
     * 申請ステータスで申請を申請日時の昇順で検索します
     * 
     * @param status 申請ステータス
     * @return 申請日時昇順でソートされた申請のリスト
     */
    List<Application> findByStatusOrderByApplicationDate(String status);

    /**
     * 承認者IDで申請を検索します
     * 
     * @param approverId 承認者ID
     * @return 該当する申請のリスト
     */
    List<Application> findByApproverId(Long approverId);

    /**
     * 承認者IDで申請を申請日時の降順で検索します
     * 
     * @param approverId 承認者ID
     * @return 申請日時降順でソートされた申請のリスト
     */
    List<Application> findByApproverIdOrderByApplicationDateDesc(Long approverId);

    /**
     * 申請日時の範囲で申請を検索します
     * 
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @return 該当する申請のリスト
     */
    List<Application> findByApplicationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 申請日時の範囲で申請を申請日時の降順で検索します
     * 
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @return 申請日時降順でソートされた申請のリスト
     */
    List<Application> findByApplicationDateBetweenOrderByApplicationDateDesc(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 申請者IDとステータスで申請を検索します
     * 
     * @param applicantId 申請者ID
     * @param status 申請ステータス
     * @return 該当する申請のリスト
     */
    List<Application> findByApplicantIdAndStatus(Long applicantId, String status);

    /**
     * 承認者IDとステータスで申請を検索します
     * 
     * @param approverId 承認者ID
     * @param status 申請ステータス
     * @return 該当する申請のリスト
     */
    List<Application> findByApproverIdAndStatus(Long approverId, String status);

    /**
     * 申請タイトルで部分一致検索します
     * 
     * @param title 申請タイトル（部分一致）
     * @return 該当する申請のリスト
     */
    List<Application> findByTitleContaining(String title);

    /**
     * 申請内容で部分一致検索します
     * 
     * @param content 申請内容（部分一致）
     * @return 該当する申請のリスト
     */
    List<Application> findByContentContaining(String content);

    /**
     * 申請タイトルまたは内容で部分一致検索します
     * 
     * @param keyword 検索キーワード
     * @return 該当する申請のリスト
     */
    @Query("SELECT a FROM Application a WHERE a.title LIKE %:keyword% OR a.content LIKE %:keyword%")
    List<Application> findByTitleOrContentContaining(@Param("keyword") String keyword);

    /**
     * 緊急度で申請を検索します
     * 
     * @param priority 緊急度
     * @return 該当する申請のリスト
     */
    List<Application> findByPriority(String priority);

    /**
     * 緊急度で申請を申請日時の昇順で検索します
     * 
     * @param priority 緊急度
     * @return 申請日時昇順でソートされた申請のリスト
     */
    List<Application> findByPriorityOrderByApplicationDate(String priority);

    /**
     * 指定された緊急度以上の申請を検索します
     * 
     * @param minPriority 最小緊急度
     * @return 該当する申請のリスト
     */
    List<Application> findByPriorityGreaterThanEqual(String minPriority);

    /**
     * 期限日で申請を検索します
     * 
     * @param dueDate 期限日
     * @return 該当する申請のリスト
     */
    List<Application> findByDueDate(LocalDateTime dueDate);

    /**
     * 期限日が指定日以前の申請を検索します
     * 
     * @param dueDate 期限日
     * @return 該当する申請のリスト
     */
    List<Application> findByDueDateBefore(LocalDateTime dueDate);

    /**
     * 期限日が指定日以降の申請を検索します
     * 
     * @param dueDate 期限日
     * @return 該当する申請のリスト
     */
    List<Application> findByDueDateAfter(LocalDateTime dueDate);

    /**
     * 期限切れの未承認申請を検索します
     * 
     * @param currentDate 現在日時
     * @return 期限切れの未承認申請のリスト
     */
    @Query("SELECT a FROM Application a WHERE a.dueDate < :currentDate AND a.status IN ('PENDING', 'IN_REVIEW')")
    List<Application> findOverdueApplications(@Param("currentDate") LocalDateTime currentDate);

    /**
     * 承認待ちの申請を優先度順で検索します
     * 
     * @return 優先度順でソートされた承認待ち申請のリスト
     */
    @Query("SELECT a FROM Application a WHERE a.status = 'PENDING' ORDER BY a.priority DESC, a.applicationDate ASC")
    List<Application> findPendingApplicationsByPriority();

    /**
     * 指定された承認者の承認待ち申請を優先度順で検索します
     * 
     * @param approverId 承認者ID
     * @return 優先度順でソートされた承認待ち申請のリスト
     */
    @Query("SELECT a FROM Application a WHERE a.approverId = :approverId AND a.status = 'PENDING' ORDER BY a.priority DESC, a.applicationDate ASC")
    List<Application> findPendingApplicationsByApproverAndPriority(@Param("approverId") Long approverId);

    /**
     * 申請者の統計情報を取得します
     * 
     * @param applicantId 申請者ID
     * @return 申請統計情報
     */
    @Query(value = """
        SELECT 
            COUNT(*) as total_applications,
            COUNT(CASE WHEN status = 'APPROVED' THEN 1 END) as approved_count,
            COUNT(CASE WHEN status = 'REJECTED' THEN 1 END) as rejected_count,
            COUNT(CASE WHEN status = 'PENDING' THEN 1 END) as pending_count,
            AVG(CASE WHEN status = 'APPROVED' AND approved_at IS NOT NULL
                THEN EXTRACT(EPOCH FROM (approved_at - application_date))/86400 END) as avg_approval_days
        FROM applications 
        WHERE applicant_id = :applicantId
        """, nativeQuery = true)
    Object[] getApplicationStatsByApplicant(@Param("applicantId") Long applicantId);

    /**
     * 承認者の統計情報を取得します
     * 
     * @param approverId 承認者ID
     * @return 承認統計情報
     */
    @Query(value = """
        SELECT 
            COUNT(*) as total_applications,
            COUNT(CASE WHEN status = 'APPROVED' THEN 1 END) as approved_count,
            COUNT(CASE WHEN status = 'REJECTED' THEN 1 END) as rejected_count,
            COUNT(CASE WHEN status = 'PENDING' THEN 1 END) as pending_count,
            AVG(CASE WHEN approved_at IS NOT NULL
                THEN EXTRACT(EPOCH FROM (approved_at - application_date))/86400 END) as avg_processing_days
        FROM applications 
        WHERE approver_id = :approverId
        """, nativeQuery = true)
    Object[] getApplicationStatsByApprover(@Param("approverId") Long approverId);

    /**
     * 月別申請件数を取得します
     * 
     * @param year 年
     * @return 月別申請件数のリスト
     */
    @Query(value = """
        SELECT 
            EXTRACT(MONTH FROM application_date) as month,
            COUNT(*) as application_count
        FROM applications 
        WHERE EXTRACT(YEAR FROM application_date) = :year
        GROUP BY EXTRACT(MONTH FROM application_date)
        ORDER BY month
        """, nativeQuery = true)
    List<Object[]> getMonthlyApplicationCounts(@Param("year") Integer year);

    /**
     * ステータス別申請件数を取得します
     * 
     * @return ステータス別申請件数のリスト
     */
    @Query("SELECT a.status, COUNT(a) FROM Application a GROUP BY a.status")
    List<Object[]> getApplicationCountsByStatus();

    /**
     * 申請の存在確認を行います
     * 
     * @param applicantId 申請者ID
     * @param title 申請タイトル
     * @return 存在する場合true
     */
    boolean existsByApplicantIdAndTitle(Long applicantId, String title);

    /**
     * 指定された期間内の申請件数を取得します
     * 
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @return 申請件数
     */
    long countByApplicationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 指定されたステータスの申請件数を取得します
     * 
     * @param status 申請ステータス
     * @return 申請件数
     */
    long countByStatus(String status);

    /**
     * 指定されたステータスの申請一覧を取得します。
     *
     * @param statuses ステータスリスト
     * @return 該当する申請一覧
     */
    @Query("SELECT a FROM Application a LEFT JOIN FETCH a.applicationType LEFT JOIN FETCH a.applicant WHERE a.status IN :statuses")
    java.util.List<Application> findByStatusIn(@Param("statuses") java.util.List<String> statuses);

    /**
     * 指定された承認者の未処理申請件数を取得します
     * 
     * @param approverId 承認者ID
     * @return 未処理申請件数
     */
    @Query("SELECT COUNT(a) FROM Application a WHERE a.approverId = :approverId AND a.status = 'PENDING'")
    long countPendingApplicationsByApprover(@Param("approverId") Long approverId);

    /**
     * 指定された申請種別を使用している申請が存在するか確認します。
     *
     * @param applicationTypeId 申請種別ID
     * @return 存在する場合true
     */
    boolean existsByApplicationTypeId(Long applicationTypeId);
}
