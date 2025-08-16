package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Objects;

/**
 * ユーザーロールエンティティ
 * システム内のユーザーの権限とロール情報を管理するエンティティ
 * 
 * @author Giiku System
 * @version 1.0
 * @since 2025-08-16
 */
@Entity
@Table(name = "user_roles", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_role_name", columnList = "role_name"),
    @Index(name = "idx_company_id", columnList = "company_id"),
    @Index(name = "idx_active", columnList = "active"),
    @Index(name = "idx_user_role_unique", columnList = "user_id,role_name", unique = true)
})
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRole extends BaseEntity {

    /**
     * ユーザーID（Userテーブルとの外部キー）
     */
    @NotNull(message = "ユーザーIDは必須です")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * ロール名
     * ADMIN: システム管理者
     * COMPANY_ADMIN: 会社管理者
     * INSTRUCTOR: 講師
     * STUDENT: 学生
     * SUPPORT: サポート担当
     */
    @NotBlank(message = "ロール名は必須です")
    @Size(max = 50, message = "ロール名は50文字以下で入力してください")
    @Column(name = "role_name", nullable = false, length = 50)
    private String roleName;

    /**
     * 会社ID（ロールが適用される会社のスコープ）
     */
    @Column(name = "company_id")
    private Long companyId;

    /**
     * ロールの説明
     */
    @Size(max = 255, message = "ロールの説明は255文字以下で入力してください")
    @Column(name = "role_description", length = 255)
    private String roleDescription;

    /**
     * 権限レベル
     * 1: 最高権限（システム管理者）
     * 2: 会社管理者権限
     * 3: 講師権限
     * 4: 学生権限
     * 5: ゲスト権限
     */
    @NotNull(message = "権限レベルは必須です")
    @Min(value = 1, message = "権限レベルは1以上で入力してください")
    @Max(value = 5, message = "権限レベルは5以下で入力してください")
    @Column(name = "permission_level", nullable = false)
    private Integer permissionLevel;

    /**
     * アクティブ状態
     */
    @NotNull(message = "アクティブ状態は必須です")
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    /**
     * ロールの有効期限開始日
     */
    @Column(name = "valid_from")
    private java.time.LocalDateTime validFrom;

    /**
     * ロールの有効期限終了日
     */
    @Column(name = "valid_until")
    private java.time.LocalDateTime validUntil;

    /**
     * 付与者のユーザーID
     */
    @Column(name = "granted_by_user_id")
    private Long grantedByUserId;

    /**
     * 特別権限フラグ（JSON形式で複数権限を格納）
     */
    @Size(max = 1000, message = "特別権限は1000文字以下で入力してください")
    @Column(name = "special_permissions", length = 1000)
    private String specialPermissions;

    /**
     * 備考・メモ
     */
    @Size(max = 500, message = "備考は500文字以下で入力してください")
    @Column(name = "notes", length = 500)
    private String notes;

    // デフォルトコンストラクタ
    public UserRole() {
        super();
        this.active = true;
        this.validFrom = java.time.LocalDateTime.now();
    }

    // コンストラクタ（必須フィールド）
    public UserRole(Long userId, String roleName, Integer permissionLevel) {
        this();
        this.userId = userId;
        this.roleName = roleName;
        this.permissionLevel = permissionLevel;
    }

    // コンストラクタ（会社スコープ付き）
    public UserRole(Long userId, String roleName, Integer permissionLevel, Long companyId) {
        this(userId, roleName, permissionLevel);
        this.companyId = companyId;
    }

    /**
     * ロール名の列挙型定数
     */
    public static class RoleName {
        public static final String ADMIN = "ADMIN";
        public static final String COMPANY_ADMIN = "COMPANY_ADMIN";
        public static final String INSTRUCTOR = "INSTRUCTOR";
        public static final String STUDENT = "STUDENT";
        public static final String SUPPORT = "SUPPORT";
    }

    /**
     * 権限レベルの列挙型定数
     */
    public static class PermissionLevel {
        public static final int SYSTEM_ADMIN = 1;
        public static final int COMPANY_ADMIN = 2;
        public static final int INSTRUCTOR = 3;
        public static final int STUDENT = 4;
        public static final int GUEST = 5;
    }

    /**
     * システム管理者権限かどうかを判定
     */
    public boolean isSystemAdmin() {
        return RoleName.ADMIN.equals(this.roleName) && 
               PermissionLevel.SYSTEM_ADMIN == this.permissionLevel;
    }

    /**
     * 会社管理者権限かどうかを判定
     */
    public boolean isCompanyAdmin() {
        return RoleName.COMPANY_ADMIN.equals(this.roleName) && 
               PermissionLevel.COMPANY_ADMIN == this.permissionLevel;
    }

    /**
     * 講師権限かどうかを判定
     */
    public boolean isInstructor() {
        return RoleName.INSTRUCTOR.equals(this.roleName) && 
               PermissionLevel.INSTRUCTOR == this.permissionLevel;
    }

    /**
     * 学生権限かどうかを判定
     */
    public boolean isStudent() {
        return RoleName.STUDENT.equals(this.roleName) && 
               PermissionLevel.STUDENT == this.permissionLevel;
    }

    /**
     * ロールが現在有効かどうかを判定
     */
    public boolean isValidNow() {
        if (!this.active) {
            return false;
        }

        java.time.LocalDateTime now = java.time.LocalDateTime.now();

        if (this.validFrom != null && now.isBefore(this.validFrom)) {
            return false;
        }

        if (this.validUntil != null && now.isAfter(this.validUntil)) {
            return false;
        }

        return true;
    }

    /**
     * 指定された権限レベル以上の権限を持つかどうかを判定
     */
    public boolean hasPermissionLevel(int requiredLevel) {
        return this.active && this.permissionLevel != null && 
               this.permissionLevel <= requiredLevel && isValidNow();
    }

    /**
     * 会社スコープ内での権限かどうかを判定
     */
    public boolean hasCompanyScope(Long targetCompanyId) {
        if (isSystemAdmin()) {
            return true; // システム管理者は全ての会社にアクセス可能
        }

        return this.companyId != null && this.companyId.equals(targetCompanyId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        UserRole userRole = (UserRole) obj;
        return Objects.equals(userId, userRole.userId) &&
               Objects.equals(roleName, userRole.roleName) &&
               Objects.equals(companyId, userRole.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, roleName, companyId);
    }

    @Override
    public String toString() {
        return "UserRole{" +
               "id=" + getId() +
               ", userId=" + userId +
               ", roleName='" + roleName + "'" +
               ", companyId=" + companyId +
               ", permissionLevel=" + permissionLevel +
               ", active=" + active +
               ", validFrom=" + validFrom +
               ", validUntil=" + validUntil +
               "}";
    }
}
