package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.DailySchedule;

import java.util.List;
import java.util.Optional;

/**
 * DailyScheduleのリポジトリインターフェース
 */
@Repository
public interface DailyScheduleRepository extends JpaRepository<DailySchedule, Long> {

    // カスタムクエリメソッドをここに追加

}
