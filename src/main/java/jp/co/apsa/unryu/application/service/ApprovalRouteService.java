package jp.co.apsa.unryu.application.service;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.apsa.unryu.domain.entity.ApprovalRoute;
import jp.co.apsa.unryu.domain.repository.ApprovalRouteRepository;

/**
 * 承認ルートを管理するサービス。
 *
 * <p>申請種別ごとの承認ルートをCRUD操作します。</p>
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class ApprovalRouteService {

    private final ApprovalRouteRepository approvalRouteRepository;

    @Autowired
    public ApprovalRouteService(ApprovalRouteRepository approvalRouteRepository) {
        this.approvalRouteRepository = approvalRouteRepository;
    }

    /**
     * 指定された申請種別の承認ルートを取得します。
     *
     * @param applicationTypeId 申請種別ID
     * @return 承認ルート一覧
     */
    @Transactional(readOnly = true)
    public List<ApprovalRoute> findByApplicationTypeId(Long applicationTypeId) {
        return approvalRouteRepository.findByApplicationTypeId(applicationTypeId);
    }

    /**
     * IDで承認ルートを取得します。
     *
     * @param id 承認ルートID
     * @return 承認ルート、存在しない場合はnull
     */
    @Transactional(readOnly = true)
    public ApprovalRoute findById(Long id) {
        return approvalRouteRepository.findById(id).orElse(null);
    }

    /**
     * 承認ルートを新規作成します。
     *
     * @param approvalRoute 承認ルート
     * @return 作成された承認ルート
     */
    public ApprovalRoute create(@Valid ApprovalRoute approvalRoute) {
        return approvalRouteRepository.save(approvalRoute);
    }

    /**
     * 承認ルートを更新します。
     *
     * @param approvalRoute 承認ルート
     * @return 更新された承認ルート
     */
    public ApprovalRoute update(@Valid ApprovalRoute approvalRoute) {
        return approvalRouteRepository.save(approvalRoute);
    }

    /**
     * 承認ルートを削除します。
     *
     * @param id 承認ルートID
     */
    public void delete(Long id) {
        approvalRouteRepository.deleteById(id);
    }
}
