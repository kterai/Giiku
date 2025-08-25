package jp.co.apsa.giiku.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.apsa.giiku.domain.entity.Month;

/**
 * Monthのリポジトリインターフェース
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface MonthRepository extends JpaRepository<Month, Long> {

    /** 月番号で取得 */
    Optional<Month> findByMonthNumber(Integer monthNumber);
}
