package jp.co.apsa.giiku.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.apsa.giiku.domain.entity.Day;

/**
 * Dayのリポジトリインターフェース
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface DayRepository extends JpaRepository<Day, Long> {

    /** 日番号で取得 */
    Optional<Day> findByDayNumber(Integer dayNumber);

    /** 週IDで一覧取得 */
    List<Day> findByWeekId(Long weekId);
}
