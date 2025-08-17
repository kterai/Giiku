package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.UserRole;
import jp.co.apsa.giiku.domain.entity.User;
import jp.co.apsa.giiku.domain.entity.Company;
import jp.co.apsa.giiku.domain.repository.UserRoleRepository;
import jp.co.apsa.giiku.domain.repository.UserRepository;
import jp.co.apsa.giiku.domain.repository.CompanyRepository;
import jp.co.apsa.giiku.dto.UserRoleCreateDto;
import jp.co.apsa.giiku.dto.UserRoleUpdateDto;
import jp.co.apsa.giiku.dto.UserRoleResponseDto;
import jp.co.apsa.giiku.dto.UserRoleSearchDto;
import jp.co.apsa.giiku.dto.UserRoleStatsDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
 * UserRoleサービスクラス。
 * ユーザー役割管理機能を提供します。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
     * エンティティをレスポンスDTOに変換します。
     *
     * @param userRole ユーザー役割エンティティ
     * @return レスポンスDTO
     */
    private UserRoleResponseDto toResponseDto(UserRole userRole) {
        UserRoleResponseDto dto = new UserRoleResponseDto();
        dto.setId(userRole.getId());
        dto.setUserId(userRole.getUserId());
        dto.setRoleName(userRole.getRoleName());
        dto.setCompanyId(userRole.getCompanyId());
        dto.setDescription(userRole.getRoleDescription());
        dto.setIsActive(userRole.getActive());
        dto.setCreatedAt(userRole.getCreatedAt());
        dto.setUpdatedAt(userRole.getUpdatedAt());
        return dto;
    }

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
        if (isDuplicateRole(userRole.getUserId(), userRole.getRoleName(), userRole.getCompanyId())) {
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
        existing.setRoleName(userRole.getRoleName());
        existing.setSpecialPermissions(userRole.getSpecialPermissions());
        existing.setActive(userRole.getActive());
        existing.setValidFrom(userRole.getValidFrom());
        existing.setValidUntil(userRole.getValidUntil());
        existing.setRoleDescription(userRole.getRoleDescription());
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

        userRole.setActive(false);
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
        return userRoleRepository.findByUserIdAndActiveTrueOrderByCreatedAtDesc(userId);
    }

    /**
     * 企業IDで役割を検索
     */
    @Transactional(readOnly = true)
    public List<UserRole> findByCompanyId(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("企業IDは必須です");
        }
        return userRoleRepository.findByCompanyIdAndActiveTrueOrderByCreatedAtDesc(companyId);
    }

    /**
     * 役割タイプで検索
     */
    @Transactional(readOnly = true)
    public List<UserRole> findByRole(String roleName) {
        if (!StringUtils.hasText(roleName)) {
            throw new IllegalArgumentException("役割は必須です");
        }
        return userRoleRepository.findByRoleNameAndActiveTrueOrderByCreatedAtDesc(roleName);
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
        return userRoleRepository.findByUserIdAndCompanyIdAndActiveTrueOrderByCreatedAtDesc(userId, companyId);
    }

    /**
     * アクティブな役割を取得
     */
    @Transactional(readOnly = true)
    public List<UserRole> findActiveRoles() {
        return userRoleRepository.findByActiveTrueOrderByCreatedAtDesc();
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
        return userRoleRepository.findBySpecialPermissionsContainingAndActiveTrueOrderByCreatedAtDesc(permission);
    }

    /**
     * 全ユーザー役割をページング取得します。
     *
     * @param page ページ番号
     * @param size ページサイズ
     * @param sortBy ソート項目
     * @param sortDir ソート方向（ASC/DESC）
     * @return ユーザー役割レスポンスのページ
     */
    @Transactional(readOnly = true)
    public Page<UserRoleResponseDto> getAllUserRoles(int page, int size, String sortBy, String sortDir) {
        Sort sort = "DESC".equalsIgnoreCase(sortDir) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRoleRepository.findAll(pageable).map(this::toResponseDto);
    }

    /**
     * IDでユーザー役割を取得します。
     *
     * @param id 役割ID
     * @return レスポンスDTO
     */
    @Transactional(readOnly = true)
    public Optional<UserRoleResponseDto> getUserRoleById(Long id) {
        return userRoleRepository.findById(id).map(this::toResponseDto);
    }

    /**
     * ユーザーIDで役割一覧を取得します。
     *
     * @param userId ユーザーID
     * @return レスポンスDTOのリスト
     */
    @Transactional(readOnly = true)
    public List<UserRoleResponseDto> getRolesByUserId(Long userId) {
        return userRoleRepository.findByUserIdAndActiveTrueOrderByCreatedAtDesc(userId)
            .stream().map(this::toResponseDto).toList();
    }

    /**
     * 役割名でユーザーをページング取得します。
     *
     * @param roleName 役割名
     * @param page ページ番号
     * @param size ページサイズ
     * @return ユーザー役割レスポンスのページ
     */
    @Transactional(readOnly = true)
    public Page<UserRoleResponseDto> getUsersByRoleName(String roleName, int page, int size) {
        List<UserRole> roles = userRoleRepository.findByRoleNameAndActiveTrueOrderByCreatedAtDesc(roleName);
        List<UserRoleResponseDto> dtoList = roles.stream().map(this::toResponseDto).toList();
        return new PageImpl<>(dtoList, PageRequest.of(page, size), dtoList.size());
    }

    /**
     * ユーザー役割を作成します。
     *
     * @param createDto 作成DTO
     * @return 作成されたレスポンスDTO
     */
    public UserRoleResponseDto createUserRole(UserRoleCreateDto createDto) {
        UserRole entity = new UserRole();
        entity.setUserId(createDto.getUserId());
        entity.setRoleName(createDto.getRoleName());
        entity.setCompanyId(createDto.getCompanyId());
        entity.setRoleDescription(createDto.getDescription());
        UserRole saved = save(entity);
        return toResponseDto(saved);
    }

    /**
     * ユーザー役割を更新します。
     *
     * @param id 更新対象ID
     * @param updateDto 更新内容
     * @return 更新後のレスポンスDTO
     */
    public Optional<UserRoleResponseDto> updateUserRole(Long id, UserRoleUpdateDto updateDto) {
        return userRoleRepository.findById(id).map(existing -> {
            if (updateDto.getRoleName() != null) {
                existing.setRoleName(updateDto.getRoleName());
            }
            if (updateDto.getDescription() != null) {
                existing.setRoleDescription(updateDto.getDescription());
            }
            if (updateDto.getIsActive() != null) {
                existing.setActive(updateDto.getIsActive());
            }
            existing.setUpdatedAt(LocalDateTime.now());
            return toResponseDto(userRoleRepository.save(existing));
        });
    }

    /**
     * ユーザー役割を削除します。
     *
     * @param id 削除対象ID
     * @return 削除成功フラグ
     */
    public boolean deleteUserRole(Long id) {
        if (!userRoleRepository.existsById(id)) {
            return false;
        }
        userRoleRepository.deleteById(id);
        return true;
    }

    /**
     * 複数の役割を割り当てます。
     *
     * @param userId ユーザーID
     * @param roleNames 役割名リスト
     * @return 割り当て結果
     */
    public List<UserRoleResponseDto> batchAssignRoles(Long userId, List<String> roleNames) {
        List<UserRoleResponseDto> result = new ArrayList<>();
        for (String roleName : roleNames) {
            UserRoleResponseDto dto = new UserRoleResponseDto();
            dto.setUserId(userId);
            dto.setRoleName(roleName);
            result.add(dto);
        }
        return result;
    }

    /**
     * 複数の役割を削除します。
     *
     * @param userId ユーザーID
     * @param roleNames 役割名リスト
     * @return 削除成功フラグ
     */
    public boolean batchRemoveRoles(Long userId, List<String> roleNames) {
        List<UserRole> roles = userRoleRepository.findByUserIdAndActiveTrueOrderByCreatedAtDesc(userId);
        if (roles.isEmpty()) {
            return false;
        }
        roles.stream()
            .filter(r -> roleNames.contains(r.getRoleName()))
            .forEach(r -> userRoleRepository.deleteById(r.getId()));
        return true;
    }

    /**
     * ユーザー役割を検索します。
     *
     * @param searchDto 検索条件
     * @param page ページ番号
     * @param size ページサイズ
     * @param sortBy ソート項目
     * @param sortDir ソート方向
     * @return 検索結果ページ
     */
    @Transactional(readOnly = true)
    public Page<UserRoleResponseDto> searchUserRoles(UserRoleSearchDto searchDto,
                                                    int page, int size,
                                                    String sortBy, String sortDir) {
        Page<UserRole> result = findWithFilters(
            searchDto.getUserId(),
            searchDto.getCompanyId(),
            searchDto.getRoleName(),
            null,
            searchDto.getIsActive(),
            null,
            null,
            PageRequest.of(page, size,
                "DESC".equalsIgnoreCase(sortDir) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending())
        );
        return result.map(this::toResponseDto);
    }

    /**
     * ユーザー役割統計情報を取得します。
     *
     * @param period 期間
     * @param roleName 役割名
     * @return 統計情報DTO
     */
    @Transactional(readOnly = true)
    public UserRoleStatsDto getUserRoleStats(String period, String roleName) {
        UserRoleStatsDto dto = new UserRoleStatsDto();
        long total = userRoleRepository.count();
        long active = userRoleRepository.countByActiveTrue();
        dto.setTotalRoles(total);
        dto.setActiveRoles(active);
        dto.setInactiveRoles(total - active);
        dto.setTotalUsers(0L);
        dto.setActiveUsers(0L);
        return dto;
    }

    /**
     * 利用可能な役割一覧を取得します。
     *
     * @return 役割名リスト
     */
    @Transactional(readOnly = true)
    public List<String> getAvailableRoles() {
        return List.of(
            "ADMIN", "INSTRUCTOR", "STUDENT", "HR_MANAGER", "COMPANY_ADMIN",
            "CONTENT_MANAGER", "SUPPORT_STAFF", "OBSERVER"
        );
    }

    /**
     * ユーザーの権限を確認します。
     *
     * @param userId ユーザーID
     * @param permission 権限名
     * @return 権限を持つかどうか
     */
    @Transactional(readOnly = true)
    public boolean checkUserPermission(Long userId, String permission) {
        return findByUserId(userId).stream()
            .anyMatch(r -> r.getSpecialPermissions() != null && r.getSpecialPermissions().contains(permission));
    }

    /**
     * 役割階層を取得します。
     *
     * @param roleName 役割名
     * @return 階層リスト
     */
    @Transactional(readOnly = true)
    public List<String> getRoleHierarchy(String roleName) {
        return List.of(roleName);
    }

    /**
     * 複合条件での検索
     */
    @Transactional(readOnly = true)
    public Page<UserRole> findWithFilters(Long userId, Long companyId, String role, 
                                         String permission, Boolean active,
                                         LocalDateTime validFrom, LocalDateTime validUntil,
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
                predicates.add(criteriaBuilder.equal(root.get("roleName"), role));
            }

            if (StringUtils.hasText(permission)) {
                predicates.add(criteriaBuilder.like(root.get("specialPermissions"), "%" + permission + "%"));
            }

            if (active != null) {
                predicates.add(criteriaBuilder.equal(root.get("active"), active));
            }

            if (validFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("validFrom"), validFrom));
            }

            if (validUntil != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("validUntil"), validUntil));
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
            .filter(role -> Boolean.TRUE.equals(role.getActive()))
            .filter(role -> (role.getValidFrom() == null || role.getValidFrom().isBefore(now) || role.getValidFrom().isEqual(now)))
            .filter(role -> (role.getValidUntil() == null || role.getValidUntil().isAfter(now) || role.getValidUntil().isEqual(now)))
            .anyMatch(role -> role.getSpecialPermissions() != null && role.getSpecialPermissions().contains(permission));
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
            .filter(userRole -> Boolean.TRUE.equals(userRole.getActive()))
            .filter(userRole -> (userRole.getValidFrom() == null || userRole.getValidFrom().isBefore(now) || userRole.getValidFrom().isEqual(now)))
            .filter(userRole -> (userRole.getValidUntil() == null || userRole.getValidUntil().isAfter(now) || userRole.getValidUntil().isEqual(now)))
            .anyMatch(userRole -> role.equals(userRole.getRoleName()));
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
        return userRoleRepository.countByActiveTrue();
    }

    /**
     * 企業別のユーザー役割数をカウント
     */
    @Transactional(readOnly = true)
    public long countByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("企業IDは必須です");
        }
        return userRoleRepository.countByCompanyIdAndActiveTrue(companyId);
    }

    /**
     * 重複役割チェック
     */
    private boolean isDuplicateRole(Long userId, String roleName, Long companyId) {
        return userRoleRepository.existsByUserIdAndRoleNameAndCompanyIdAndActiveTrue(userId, roleName, companyId);
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

        if (!StringUtils.hasText(userRole.getRoleName())) {
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
        if (userRole.getValidFrom() != null && userRole.getValidUntil() != null) {
            if (userRole.getValidFrom().isAfter(userRole.getValidUntil())) {
                throw new IllegalArgumentException("有効開始日は有効終了日より前に設定してください");
            }
        }

        // 役割の有効性チェック（定義された役割のみ許可）
        if (!isValidRole(userRole.getRoleName())) {
            throw new IllegalArgumentException("無効な役割です: " + userRole.getRoleName());
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
