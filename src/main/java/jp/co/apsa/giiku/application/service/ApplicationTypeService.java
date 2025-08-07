package jp.co.apsa.giiku.application.service;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.apsa.giiku.domain.entity.ApplicationType;
import jp.co.apsa.giiku.domain.repository.ApplicationRepository;
import jp.co.apsa.giiku.domain.repository.ApplicationTypeRepository;
import jp.co.apsa.giiku.domain.repository.ApprovalRouteRepository;

/**
 * 申請種別を管理するサービス。
 *
 * <p>マスタメンテナンス画面用のCRUD処理を提供します。</p>
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class ApplicationTypeService {

    private final ApplicationTypeRepository applicationTypeRepository;
    private final ApplicationRepository applicationRepository;
    private final ApprovalRouteRepository approvalRouteRepository;

    /**
     * コンストラクタ。
     *
     * @param applicationTypeRepository 申請種別リポジトリ
     * @param applicationRepository 申請リポジトリ
     * @param approvalRouteRepository 承認ルートリポジトリ
     */
    @Autowired
    public ApplicationTypeService(ApplicationTypeRepository applicationTypeRepository,
                                  ApplicationRepository applicationRepository,
                                  ApprovalRouteRepository approvalRouteRepository) {
        this.applicationTypeRepository = applicationTypeRepository;
        this.applicationRepository = applicationRepository;
        this.approvalRouteRepository = approvalRouteRepository;
    }

    /**
     * 全ての申請種別を表示順に取得します。
     *
     * @return 申請種別リスト
     */
    @Transactional(readOnly = true)
    public List<ApplicationType> findAll() {
        return applicationTypeRepository.findAllByOrderByDisplayOrder();
    }

    /**
     * IDで申請種別を取得します。
     *
     * @param id 申請種別ID
     * @return 申請種別、存在しない場合はnull
     */
    @Transactional(readOnly = true)
    public ApplicationType findById(Long id) {
        return applicationTypeRepository.findById(id).orElse(null);
    }

    /**
     * 申請種別を新規作成します。
     *
     * @param applicationType 申請種別
     * @return 作成された申請種別
     */
    public ApplicationType create(@Valid ApplicationType applicationType) {
        return applicationTypeRepository.save(applicationType);
    }

    /**
     * 申請種別を更新します。
     *
     * @param applicationType 申請種別
     * @return 更新された申請種別
     */
    public ApplicationType update(@Valid ApplicationType applicationType) {
        return applicationTypeRepository.save(applicationType);
    }

    /**
     * 申請種別を削除します。関連データが存在する場合は削除しません。
     *
     * @param id 申請種別ID
     * @return 削除できた場合true
     */
    public boolean delete(Long id) {
        if (applicationRepository.existsByApplicationTypeId(id)
                || approvalRouteRepository.existsByApplicationTypeId(id)) {
            return false;
        }
        applicationTypeRepository.deleteById(id);
        return true;
    }
}
