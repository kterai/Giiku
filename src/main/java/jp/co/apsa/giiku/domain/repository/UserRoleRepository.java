package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.UserRole;

import java.time.LocalDateTime;
import java.util.List;

/**
 * UserRoleのリポジトリインターフェース。
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {

    List<UserRole> findByUserIdAndActiveTrueOrderByCreatedAtDesc(Long userId);

    List<UserRole> findByCompanyIdAndActiveTrueOrderByCreatedAtDesc(Long companyId);

    List<UserRole> findByRoleNameAndActiveTrueOrderByCreatedAtDesc(String roleName);

    List<UserRole> findByUserIdAndCompanyIdAndActiveTrueOrderByCreatedAtDesc(Long userId, Long companyId);

    List<UserRole> findByActiveTrueOrderByCreatedAtDesc();

    @Query("SELECT ur FROM UserRole ur WHERE (ur.validFrom IS NULL OR ur.validFrom <= :now) " +
           "AND (ur.validUntil IS NULL OR ur.validUntil >= :now) AND ur.active = true ORDER BY ur.createdAt DESC")
    List<UserRole> findValidRoles(@Param("now") LocalDateTime now);

    List<UserRole> findBySpecialPermissionsContainingAndActiveTrueOrderByCreatedAtDesc(String permission);

    long countByActiveTrue();

    long countByCompanyIdAndActiveTrue(Long companyId);

    boolean existsByUserIdAndRoleNameAndCompanyIdAndActiveTrue(Long userId, String roleName, Long companyId);
}
