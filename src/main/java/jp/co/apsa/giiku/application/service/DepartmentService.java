package jp.co.apsa.giiku.application.service;

import java.util.List;
import java.util.Collections;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.apsa.giiku.domain.entity.Department;
import jp.co.apsa.giiku.domain.repository.ApprovalRouteRepository;
import jp.co.apsa.giiku.domain.repository.DepartmentRepository;
import jp.co.apsa.giiku.domain.repository.UserRepository;

/**
 * 部署を管理するサービス。
 *
 * <p>マスタメンテナンス画面用のCRUD処理を提供します。</p>
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final ApprovalRouteRepository approvalRouteRepository;

    /**
     * コンストラクタ。
     *
     * @param departmentRepository 部署リポジトリ
     * @param userRepository ユーザーリポジトリ
     * @param approvalRouteRepository 承認ルートリポジトリ
     */
    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository,
                             UserRepository userRepository,
                             ApprovalRouteRepository approvalRouteRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.approvalRouteRepository = approvalRouteRepository;
    }

    /**
     * 全ての部署を表示順に取得します。
     *
     * @return 部署リスト
     */
    @Transactional(readOnly = true)
    public List<Department> findAll() {
        return departmentRepository.findAllByOrderByDisplayOrder();
    }

    /**
     * IDで部署を取得します。
     *
     * @param id 部署ID
     * @return 部署、存在しない場合はnull
     */
    @Transactional(readOnly = true)
    public Department findById(Long id) {
        Department dept = departmentRepository.findById(id).orElse(null);
        if (dept != null && dept.getResponsibleUsers() != null) {
            dept.setResponsibleUserIds(
                    dept.getResponsibleUsers().stream()
                            .map(u -> u.getId())
                            .toList());
        }
        return dept;
    }

    /**
     * 部署を新規作成します。
     *
     * @param department 部署
     * @return 作成された部署
     */
    public Department create(@Valid Department department) {
        if (department.getResponsibleUserIds() != null) {
            department.setResponsibleUsers(
                    userRepository.findAllById(department.getResponsibleUserIds()));
        } else {
            department.setResponsibleUsers(Collections.emptyList());
        }
        return departmentRepository.save(department);
    }

    /**
     * 部署を更新します。
     *
     * @param department 部署
     * @return 更新された部署
     */
    public Department update(@Valid Department department) {
        if (department.getResponsibleUserIds() != null) {
            department.setResponsibleUsers(
                    userRepository.findAllById(department.getResponsibleUserIds()));
        } else {
            department.setResponsibleUsers(Collections.emptyList());
        }
        return departmentRepository.save(department);
    }

    /**
     * 部署を削除します。関連データが存在する場合は削除しません。
     *
     * @param id 部署ID
     * @return 削除できた場合true
     */
    public boolean delete(Long id) {
        if (departmentRepository.existsByParentId(id)
                || userRepository.existsByDepartmentId(id)
                || approvalRouteRepository.existsByApproverDepartmentId(id)) {
            return false;
        }
        departmentRepository.deleteById(id);
        return true;
    }
}
