package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.TrainingProgram;

import java.util.List;
import java.util.Optional;

/**
 * TrainingProgramのリポジトリインターフェース
 */
@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {

    // カスタムクエリメソッドをここに追加

}
