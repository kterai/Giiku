package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.CompanyLmsConfig;

import java.util.List;
import java.util.Optional;

/**
 * CompanyLmsConfigのリポジトリインターフェース
 * LMS設定の永続化とカスタムクエリメソッドを提供
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

    /**
     * アクティブなLMS設定一覧を取得
     * 
     * @return アクティブなLMS設定一覧
     */
    List<CompanyLmsConfig> findByActiveTrue();

    /**
     * 指定された企業IDのアクティブなLMS設定を取得
     * 
     * @param companyId 企業ID
     * @return アクティブなLMS設定（存在する場合）
     */
    Optional<CompanyLmsConfig> findByCompanyIdAndActiveTrue(Long companyId);

    /**
     * 指定された企業IDの非アクティブなLMS設定一覧を取得
     * 
     * @param companyId 企業ID
     * @return 非アクティブなLMS設定一覧
     */
    List<CompanyLmsConfig> findByCompanyIdAndActiveFalse(Long companyId);

    /**
     * 指定された設定名と企業IDでLMS設定を検索
     * 
     * @param configName 設定名
     * @param companyId 企業ID
     * @return 該当するLMS設定（存在する場合）
     */
    Optional<CompanyLmsConfig> findByConfigNameAndCompanyId(String configName, Long companyId);

    /**
     * 指定された設定タイプの設定一覧を取得
     * 
     * @param configType 設定タイプ
     * @return 該当する設定一覧
     */
    List<CompanyLmsConfig> findByConfigType(String configType);

    /**
     * 指定された企業IDと設定タイプの設定一覧を取得
     * 
     * @param companyId 企業ID
     * @param configType 設定タイプ
     * @return 該当する設定一覧
     */
    List<CompanyLmsConfig> findByCompanyIdAndConfigType(Long companyId, String configType);

    // JpaRepositoryから継承される基本メソッド:
    // - findAll()
    // - findById(Long id)
    // - save(CompanyLmsConfig entity)
    // - deleteById(Long id)
    // - existsById(Long id)
    // - count()
    // など
}