package jp.co.apsa.giiku.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.apsa.giiku.domain.entity.Week;

/**
 * Weekのリポジトリインターフェース
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface WeekRepository extends JpaRepository<Week, Long> {

    /** 週番号で取得 */
    Optional<Week> findByWeekNumber(Integer weekNumber);

    /** 月IDで一覧取得 */
    List<Week> findByMonthId(Long monthId);
}
