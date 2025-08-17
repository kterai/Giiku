package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * ユーザーエンティティ
 * システム内のユーザー情報を管理する基本エンティティ
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_username", columnList = "username", unique = true),
    @Index(name = "idx_email", columnList = "email", unique = true),
    @Index(name = "idx_company_id", columnList = "company_id"),
    @Index(name = "idx_active", columnList = "active"),
    @Index(name = "idx_role", columnList = "role")
})
/** The User class. */
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "ユーザー名は必須です")
    @Size(min = 3, max = 50, message = "ユーザー名は3文字以上50文字以下で入力してください")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank(message = "パスワードは必須です")
    @Size(min = 8, max = 255, message = "パスワードは8文字以上で入力してください")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "有効なメールアドレスを入力してください")
    @Size(max = 100, message = "メールアドレスは100文字以下で入力してください")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "名前（名）は必須です")
    @Size(max = 50, message = "名前（名）は50文字以下で入力してください")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "名前（姓）は必須です")
    @Size(max = 50, message = "名前（姓）は50文字以下で入力してください")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotNull(message = "会社IDは必須です")
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @NotBlank(message = "権限は必須です")
    @Size(max = 50, message = "権限は50文字以下で入力してください")
    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @NotNull(message = "アクティブ状態は必須です")
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Size(max = 500, message = "プロフィール画像URLは500文字以下で入力してください")
    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;

    // コンストラクタ
    /** User メソッド */
    public User() {
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    /** User メソッド */
    public User(String username, String password, String email, String firstName, 
                String lastName, Long companyId, String role) {
        this();
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyId = companyId;
        this.role = role;
    }

    // Getter and Setter methods
    /** getId メソッド */
    public Long getId() {
        return id;
    }
    /** setId メソッド */
    public void setId(Long id) {
        this.id = id;
    }
    /** getUsername メソッド */
    public String getUsername() {
        return username;
    }
    /** setUsername メソッド */
    public void setUsername(String username) {
        this.username = username;
    }
    /** getPassword メソッド */
    public String getPassword() {
        return password;
    }
    /** setPassword メソッド */
    public void setPassword(String password) {
        this.password = password;
    }
    /** getEmail メソッド */
    public String getEmail() {
        return email;
    }
    /** setEmail メソッド */
    public void setEmail(String email) {
        this.email = email;
    }
    /** getFirstName メソッド */
    public String getFirstName() {
        return firstName;
    }
    /** setFirstName メソッド */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /** getLastName メソッド */
    public String getLastName() {
        return lastName;
    }
    /** setLastName メソッド */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /** getCompanyId メソッド */
    public Long getCompanyId() {
        return companyId;
    }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    /** getRole メソッド */
    public String getRole() {
        return role;
    }
    /** setRole メソッド */
    public void setRole(String role) {
        this.role = role;
    }
    /** getActive メソッド */
    public Boolean getActive() {
        return active;
    }
    /** setActive メソッド */
    public void setActive(Boolean active) {
        this.active = active;
    }
    /** getCreatedAt メソッド */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    /** setCreatedAt メソッド */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    /** getUpdatedAt メソッド */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    /** setUpdatedAt メソッド */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    /** getLastLoginAt メソッド */
    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }
    /** setLastLoginAt メソッド */
    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
    /** getProfileImageUrl メソッド */
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    /** setProfileImageUrl メソッド */
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    // ビジネスメソッド
    /**
     * フルネームを取得
     * @return 姓 + 名のフルネーム
     */
    public String getFullName() {
        return lastName + " " + firstName;
    }

    /**
     * アクティブなユーザーかどうかを判定
     * @return アクティブな場合true
     */
    public boolean isActive() {
        return active != null && active;
    }

    /**
     * ユーザーが指定された権限を持っているかを判定
     * @param targetRole 確認したい権限
     * @return 権限を持っている場合true
     */
    public boolean hasRole(String targetRole) {
        return role != null && role.equals(targetRole);
    }

    /** 最終ログイン時刻を更新 */
    public void updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }

    /** ユーザーを無効化 */
    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    /** ユーザーを有効化 */
    public void activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    // Object methods
    /** equals メソッド */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && 
               Objects.equals(username, user.username) && 
               Objects.equals(email, user.email);
    }

    /** hashCode メソッド */
    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }

    /** toString メソッド */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", companyId=" + companyId +
                ", role='" + role + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", lastLoginAt=" + lastLoginAt +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                '}';
    }
}
