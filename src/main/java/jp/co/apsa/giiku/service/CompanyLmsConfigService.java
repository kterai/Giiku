package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.CompanyLmsConfig;
import jp.co.apsa.giiku.domain.repository.CompanyLmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 企業LMS設定を管理するサービスクラス。
 * エンティティの基本的なCRUD操作を提供する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class CompanyLmsConfigService {

    @Autowired
    private CompanyLmsConfigRepository companyLmsConfigRepository;

    /**
     * 企業LMS設定一覧を取得する。
     *
     * @param pageable ページング情報
     * @return 設定一覧
     */
    @Transactional(readOnly = true)
    public Page<CompanyLmsConfig> getAllCompanyLmsConfigs(Pageable pageable) {
        return companyLmsConfigRepository.findAll(pageable);
    }

    /**
     * IDで企業LMS設定を取得する。
     *
     * @param id 設定ID
     * @return 企業LMS設定
     */
    @Transactional(readOnly = true)
    public CompanyLmsConfig getCompanyLmsConfigById(Long id) {
        return companyLmsConfigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CompanyLmsConfig not found: " + id));
    }

    /**
     * 企業IDで設定を取得する。
     *
     * @param companyId 企業ID
     * @return 企業LMS設定（存在しない場合は空）
     */
    @Transactional(readOnly = true)
    public Optional<CompanyLmsConfig> getCompanyLmsConfigByCompanyId(Long companyId) {
        List<CompanyLmsConfig> configs = companyLmsConfigRepository.findByCompanyId(companyId);
        return configs.stream().findFirst();
    }

    /**
     * 企業LMS設定を作成する。
     *
     * @param config 設定エンティティ
     * @return 保存された設定
     */
    public CompanyLmsConfig createCompanyLmsConfig(CompanyLmsConfig config) {
        return companyLmsConfigRepository.save(config);
    }

    /**
     * 企業LMS設定を更新する。
     *
     * @param config 更新する設定
     * @return 更新された設定
     */
    public CompanyLmsConfig updateCompanyLmsConfig(CompanyLmsConfig config) {
        Long id = config.getId();
        if (id == null || !companyLmsConfigRepository.existsById(id)) {
            throw new RuntimeException("CompanyLmsConfig not found: " + id);
        }
        return companyLmsConfigRepository.save(config);
    }

    /**
     * LMS機能の有効/無効を更新する。
     *
     * @param id 設定ID
     * @param enabled 有効フラグ
     */
    public void updateLmsEnabled(Long id, boolean enabled) {
        CompanyLmsConfig config = getCompanyLmsConfigById(id);
        config.setLmsEnabled(enabled);
        companyLmsConfigRepository.save(config);
    }

    /**
     * 企業のLMS機能が有効かどうかを判定する。
     *
     * @param companyId 企業ID
     * @return 有効な場合はtrue
     */
    @Transactional(readOnly = true)
    public boolean isLmsActiveForCompany(Long companyId) {
        return getCompanyLmsConfigByCompanyId(companyId)
                .map(CompanyLmsConfig::isLmsActive)
                .orElse(false);
    }
}

