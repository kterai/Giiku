package jp.co.apsa.unryu.domain.repository;

import jp.co.apsa.unryu.domain.entity.ApplicationApprovalRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ApplicationApprovalRoute リポジトリ。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface ApplicationApprovalRouteRepository extends JpaRepository<ApplicationApprovalRoute, Long> {
}
