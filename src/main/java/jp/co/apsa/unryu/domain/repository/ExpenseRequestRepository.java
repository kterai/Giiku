package jp.co.apsa.unryu.domain.repository;

import jp.co.apsa.unryu.domain.entity.ExpenseRequestDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 経費申請リポジトリ。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface ExpenseRequestRepository extends JpaRepository<ExpenseRequestDetails, Long> {

    /**
     * 指定されたステータスの経費申請一覧を取得します。
     *
     * @param statuses 取得対象のステータスリスト
     * @return 該当する経費申請一覧
     */
    java.util.List<ExpenseRequestDetails> findByStatusIn(java.util.List<String> statuses);

    /**
     * 申請IDで経費申請を取得します。
     *
     * @param applicationId 申請ID
     * @return 経費申請
     */
    java.util.Optional<ExpenseRequestDetails> findByApplicationId(Long applicationId);
}
