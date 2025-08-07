package jp.co.apsa.unryu.domain.repository;

import jp.co.apsa.unryu.domain.entity.TravelRequestDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 出張申請リポジトリ。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface TravelRequestRepository extends JpaRepository<TravelRequestDetails, Long> {

    /**
     * 指定されたステータスの出張申請一覧を取得します。
     *
     * @param statuses 取得対象のステータスリスト
     * @return 該当する出張申請一覧
     */
    java.util.List<TravelRequestDetails> findByStatusIn(java.util.List<String> statuses);

    /**
     * 申請IDで出張申請を取得します。
     *
     * @param applicationId 申請ID
     * @return 出張申請
     */
    java.util.Optional<TravelRequestDetails> findByApplicationId(Long applicationId);
}
