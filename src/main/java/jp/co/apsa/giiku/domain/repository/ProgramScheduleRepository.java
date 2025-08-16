package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.ProgramSchedule;

import java.util.List;
import java.util.Optional;

/**
 * ProgramScheduleのリポジトリインターフェース
 */
@Repository
public interface ProgramScheduleRepository extends JpaRepository<ProgramSchedule, Long> {

    // カスタムクエリメソッドをここに追加

}
