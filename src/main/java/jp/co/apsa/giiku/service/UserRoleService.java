package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.UserRole;
import jp.co.apsa.giiku.domain.entity.User;
import jp.co.apsa.giiku.domain.entity.Company;
import jp.co.apsa.giiku.domain.repository.UserRoleRepository;
import jp.co.apsa.giiku.domain.repository.UserRepository;
import jp.co.apsa.giiku.domain.repository.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserRoleサービスクラス
 * ユーザー役割管理機能を提供
 */
@Service
@Transactional
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * 全てのユーザー役割を取得
     */
    @Transactional(readOnly = true)
    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    /**
     * IDでユーザー役割を取得
     */
    @Transactional(readOnly = true)
    public Optional<UserRole> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }
        return userRoleRepository.findById(id);
    }

    /**
     * ユーザー役割を保存
     */
    public UserRole save(UserRole userRole) {
        validateUserRole(userRole);

        // 重複チェック
        if (isDuplicateRole(userRole.getUserId(), userRole.getRole(), userRole.getCompanyId())) {
            throw new IllegalArgumentException("このユーザーには既に同じ役割が割り当てられています");
        }

        if (userRole.getId() == null) {
            userRole.setCreatedAt(LocalDateTime.now());
        }
        userRole.setUpdatedAt(LocalDateTime.now());

        return userRoleRepository.save(userRole);
    }

    /**
     * ユーザー役割を更新
     */
    public UserRole update(Long id, UserRole userRole) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        UserRole existing = userRoleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ユーザー役割が見つかりません: " + id));

        validateUserRole(userRole);

        // 基本情報の更新
        existing.setRole(userRole.getRole());
        existing.setPermissions(userRole.getPermissions());
        existing.setIsActive(userRole.getIsActive());
        existing.setValidFrom(userRole.getValidFrom());
        existing.setValidTo(userRole.getValidTo());
        existing.setDescription(userRole.getDescription());
        existing.setUpdatedAt(LocalDateTime.now());

        return userRoleRepository.save(existing);
    }

    /**
     * ユーザー役割を論理削除
     */
    public void deactivate(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        UserRole userRole = userRoleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ユーザー役割が見つかりません: " + id));

        userRole.setIsActive(false);
        userRole.setUpdatedAt(LocalDateTime.now());
        userRoleRepository.save(userRole);
    }

    /**
     * ユーザー役割を物理削除
     */
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        if (!userRoleRepository.existsById(id)) {
            throw new RuntimeException("ユーザー役割が見つかりません: " + id);
        }

        userRoleRepository.deleteById(id);
    }

    /**
     * ユーザーIDで役割を検索
     */
    @Transactional(readOnly = true)
    public List<UserRole> findByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("ユーザーIDは必須です");
        }
        return userRoleRepository.findByUserIdAndIsActiveTrueOrderByCreatedAtDesc(userId);
    }

    /**
     * 企業IDで役割を検索
     */
    @Transactional(readOnly = true)
    public List<UserRole> findByCompanyId(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("企業IDは必須です");
        }
        return userRoleRepository.findByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(companyId);
    }

    /**
     * 役割タイプで検索
     */
    @Transactional(readOnly = true)
    public List<UserRole> findByRole(String role) {
        if (!StringUtils.hasText(role)) {
            throw new IllegalArgumentException("役割は必須です");
        }
        return userRoleRepository.findByRoleAndIsActiveTrueOrderByCreatedAtDesc(role);
    }

    /**
     * ユーザーの特定企業での役割を取得
     */
    @Transactional(readOnly = true)
    public List<UserRole> findByUserIdAndCompanyId(Long userId, Long companyId) {
        if (userId == null) {
            throw new IllegalArgumentException("ユーザーIDは必須です");
        }
        if (companyId == null) {
            throw new IllegalArgumentException("企業IDは必須です");
        }
        return userRoleRepository.findByUserIdAndCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(userId, companyId);
    }

    /**
     * アクティブな役割を取得
     */
    @Transactional(readOnly = true)
    public List<UserRole> findActiveRoles() {
        return userRoleRepository.findByIsActiveTrueOrderByCreatedAtDesc();
    }

    /**
     * 有効期間内の役割を取得
     */
    @Transactional(readOnly = true)
    public List<UserRole> findValidRoles() {
        LocalDateTime now = LocalDateTime.now();
        return userRoleRepository.findValidRoles(now);
    }

    /**
     * 特定の権限を持つユーザーを検索
     */
    @Transactional(readOnly = true)
    public List<UserRole> findByPermissionContaining(String permission) {
        if (!StringUtils.hasText(permission)) {
            throw new IllegalArgumentException("権限は必須です");
        }
        return userRoleRepository.findByPermissionsContainingAndIsActiveTrueOrderByCreatedAtDesc(permission);
    }

    /**
     * 複合条件での検索
     */
    @Transactional(readOnly = true)
    public Page<UserRole> findWithFilters(Long userId, Long companyId, String role, 
                                         String permission, Boolean isActive,
                                         LocalDateTime validFrom, LocalDateTime validTo,
                                         Pageable pageable) {

        Specification<UserRole> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("userId"), userId));
            }

            if (companyId != null) {
                predicates.add(criteriaBuilder.equal(root.get("companyId"), companyId));
            }

            if (StringUtils.hasText(role)) {
                predicates.add(criteriaBuilder.equal(root.get("role"), role));
            }

            if (StringUtils.hasText(permission)) {
                predicates.add(criteriaBuilder.like(root.get("permissions"), "%" + permission + "%"));
            }

            if (isActive != null) {
                predicates.add(criteriaBuilder.equal(root.get("isActive"), isActive));
            }

            if (validFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("validFrom"), validFrom));
            }

            if (validTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("validTo"), validTo));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return userRoleRepository.findAll(spec, pageable);
    }

    /**
     * ユーザーの権限チェック
     */
    @Transactional(readOnly = true)
    public boolean hasPermission(Long userId, Long companyId, String permission) {
        if (userId == null || companyId == null || !StringUtils.hasText(permission)) {
            return false;
        }

        List<UserRole> roles = findByUserIdAndCompanyId(userId, companyId);
        LocalDateTime now = LocalDateTime.now();

        return roles.stream()
            .filter(role -> role.getIsActive())
            .filter(role -> (role.getValidFrom() == null || role.getValidFrom().isBefore(now) || role.getValidFrom().isEqual(now)))
            .filter(role -> (role.getValidTo() == null || role.getValidTo().isAfter(now) || role.getValidTo().isEqual(now)))
            .anyMatch(role -> role.getPermissions() != null && role.getPermissions().contains(permission));
    }

    /**
     * ユーザーの役割チェック
     */
    @Transactional(readOnly = true)
    public boolean hasRole(Long userId, Long companyId, String role) {
        if (userId == null || companyId == null || !StringUtils.hasText(role)) {
            return false;
        }

        List<UserRole> roles = findByUserIdAndCompanyId(userId, companyId);
        LocalDateTime now = LocalDateTime.now();

        return roles.stream()
            .filter(userRole -> userRole.getIsActive())
            .filter(userRole -> (userRole.getValidFrom() == null || userRole.getValidFrom().isBefore(now) || userRole.getValidFrom().isEqual(now)))
            .filter(userRole -> (userRole.getValidTo() == null || userRole.getValidTo().isAfter(now) || userRole.getValidTo().isEqual(now)))
            .anyMatch(userRole -> role.equals(userRole.getRole()));
    }

    /**
     * ユーザー役割数をカウント
     */
    @Transactional(readOnly = true)
    public long countAll() {
        return userRoleRepository.count();
    }

    /**
     * アクティブなユーザー役割数をカウント
     */
    @Transactional(readOnly = true)
    public long countActive() {
        return userRoleRepository.countByIsActiveTrue();
    }

    /**
     * 企業別のユーザー役割数をカウント
     */
    @Transactional(readOnly = true)
    public long countByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("企業IDは必須です");
        }
        return userRoleRepository.countByCompanyIdAndIsActiveTrue(companyId);
    }

    /**
     * 重複役割チェック
     */
    private boolean isDuplicateRole(Long userId, String role, Long companyId) {
        return userRoleRepository.existsByUserIdAndRoleAndCompanyIdAndIsActiveTrue(userId, role, companyId);
    }

    /**
     * ユーザー役割のバリデーション
     */
    private void validateUserRole(UserRole userRole) {
        if (userRole == null) {
            throw new IllegalArgumentException("ユーザー役割は必須です");
        }

        if (userRole.getUserId() == null) {
            throw new IllegalArgumentException("ユーザーIDは必須です");
        }

        if (!StringUtils.hasText(userRole.getRole())) {
            throw new IllegalArgumentException("役割は必須です");
        }

        if (userRole.getCompanyId() == null) {
            throw new IllegalArgumentException("企業IDは必須です");
        }

        // ユーザー存在チェック
        if (!userRepository.existsById(userRole.getUserId())) {
            throw new IllegalArgumentException("指定されたユーザーが存在しません");
        }

        // 企業存在チェック
        if (!companyRepository.existsById(userRole.getCompanyId())) {
            throw new IllegalArgumentException("指定された企業が存在しません");
        }

        // 有効期間チェック
        if (userRole.getValidFrom() != null && userRole.getValidTo() != null) {
            if (userRole.getValidFrom().isAfter(userRole.getValidTo())) {
                throw new IllegalArgumentException("有効開始日は有効終了日より前に設定してください");
            }
        }

        // 役割の有効性チェック（定義された役割のみ許可）
        if (!isValidRole(userRole.getRole())) {
            throw new IllegalArgumentException("無効な役割です: " + userRole.getRole());
        }
    }

    /**
     * 有効な役割かチェック
     */
    private boolean isValidRole(String role) {
        List<String> validRoles = List.of(
            "ADMIN", "INSTRUCTOR", "STUDENT", "HR_MANAGER", "COMPANY_ADMIN", 
            "CONTENT_MANAGER", "SUPPORT_STAFF", "OBSERVER"
        );
        return validRoles.contains(role);
    }
}
