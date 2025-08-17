package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.MockTest;

import java.util.List;
import java.util.Optional;

/**
 * MockTestのリポジトリインターフェース
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface MockTestRepository extends JpaRepository<MockTest, Long>,
        JpaSpecificationExecutor<MockTest> {

    List<MockTest> findByCompanyIdAndIsActiveTrue(Long companyId);

    List<MockTest> findByProgramIdAndIsActiveTrue(Long programId);

    List<MockTest> findByTestTypeAndIsActiveTrue(String testType);

    List<MockTest> findByDifficultyLevelAndIsActiveTrue(String difficultyLevel);

    List<MockTest> findByIsActiveTrueOrderByCreatedAtDesc();

    List<MockTest> findByTitleContainingIgnoreCaseAndIsActiveTrue(String title);

    long countByIsActiveTrue();

    long countByCompanyIdAndIsActiveTrue(Long companyId);
}
