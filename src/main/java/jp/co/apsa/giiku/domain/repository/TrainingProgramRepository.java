package jp.co.apsa.giiku.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import jp.co.apsa.giiku.domain.entity.TrainingProgram;

/**
 * TrainingProgramのリポジトリインターフェース。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long>,
        JpaSpecificationExecutor<TrainingProgram> {

    /** 企業IDで研修プログラムを検索 */
    List<TrainingProgram> findByCompanyId(Long companyId);

    /** アクティブな研修プログラムを取得 */
    List<TrainingProgram> findByIsActiveTrue();

    /** 企業IDかつアクティブな研修プログラムを検索 */
    List<TrainingProgram> findByCompanyIdAndIsActiveTrue(Long companyId);

    /** 企業IDで研修プログラム数をカウント */
    long countByCompanyId(Long companyId);
}
