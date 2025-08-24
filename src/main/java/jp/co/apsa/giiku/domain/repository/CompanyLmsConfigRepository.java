package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.CompanyLmsConfig;

import java.util.List;
import java.util.Optional;

/**
 * CompanyLmsConfigのリポジトリインターフェース
 * LMS設定の永続化とカスタムクエリメソッドを提供
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface CompanyLmsConfigRepository extends JpaRepository<CompanyLmsConfig, Long> {

    /**
     * 指定された企業IDのLMS設定一覧を取得
     *
     * @param companyId 企業ID
     * @return 該当企業のLMS設定一覧
     */
    List<CompanyLmsConfig> findByCompanyId(Long companyId);

    // JpaRepositoryから継承される基本メソッド:
    // - findAll()
    // - findById(Long id)
    // - save(CompanyLmsConfig entity)
    // - deleteById(Long id)
    // - existsById(Long id)
    // - count()
    // など
}
