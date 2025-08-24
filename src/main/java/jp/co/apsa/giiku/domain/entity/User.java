package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * ユーザーエンティティ
 * システム内のユーザー情報を管理する基本エンティティ
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_username", columnList = "username", unique = true),
    @Index(name = "idx_email", columnList = "email", unique = true),
    @Index(name = "idx_company_id", columnList = "company_id"),
    @Index(name = "idx_active", columnList = "active"),
    @Index(name = "idx_role", columnList = "role")
})
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

    @NotBlank(message = "氏名は必須です")
    @Size(max = 100, message = "氏名は100文字以下で入力してください")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotNull(message = "会社IDは必須です")
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @NotBlank(message = "権限は必須です")
    @Size(max = 50, message = "権限は50文字以下で入力してください")
    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @NotNull(message = "性別は必須です")
    @Min(1)
    @Max(3)
    @Column(name = "gender", nullable = false)
    private Short gender;

    @NotNull(message = "誕生日は必須です")
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @NotNull(message = "アクティブ状態は必須です")
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @NotNull(message = "作成者IDは必須です")
    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull(message = "更新者IDは必須です")
    @Column(name = "updated_by", nullable = false)
    private Long updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /** User メソッド */
    public User() {
        this.active = true;
    }

    /** User メソッド */
    public User(String username, String password, String email, String name,
                Long companyId, String role, Short gender, LocalDate birthday,
                Long createdBy, Long updatedBy) {
        this();
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.companyId = companyId;
        this.role = role;
        this.gender = gender;
        this.birthday = birthday;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    // Getter and Setter methods
    /** getId メソッド */
    public Long getId() { return id; }
    /** setId メソッド */
    public void setId(Long id) { this.id = id; }
    /** getUsername メソッド */
    public String getUsername() { return username; }
    /** setUsername メソッド */
    public void setUsername(String username) { this.username = username; }
    /** getPassword メソッド */
    public String getPassword() { return password; }
    /** setPassword メソッド */
    public void setPassword(String password) { this.password = password; }
    /** getEmail メソッド */
    public String getEmail() { return email; }
    /** setEmail メソッド */
    public void setEmail(String email) { this.email = email; }
    /** getName メソッド */
    public String getName() { return name; }
    /** setName メソッド */
    public void setName(String name) { this.name = name; }
    /** getCompanyId メソッド */
    public Long getCompanyId() { return companyId; }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    /** getRole メソッド */
    public String getRole() { return role; }
    /** setRole メソッド */
    public void setRole(String role) { this.role = role; }
    /** getGender メソッド */
    public Short getGender() { return gender; }
    /** setGender メソッド */
    public void setGender(Short gender) { this.gender = gender; }
    /** getBirthday メソッド */
    public LocalDate getBirthday() { return birthday; }
    /** setBirthday メソッド */
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
    /** getActive メソッド */
    public Boolean getActive() { return active; }
    /** setActive メソッド */
    public void setActive(Boolean active) { this.active = active; }
    /** getCreatedBy メソッド */
    public Long getCreatedBy() { return createdBy; }
    /** setCreatedBy メソッド */
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
    /** getCreatedAt メソッド */
    public LocalDateTime getCreatedAt() { return createdAt; }
    /** setCreatedAt メソッド */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    /** getUpdatedBy メソッド */
    public Long getUpdatedBy() { return updatedBy; }
    /** setUpdatedBy メソッド */
    public void setUpdatedBy(Long updatedBy) { this.updatedBy = updatedBy; }
    /** getUpdatedAt メソッド */
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    /** setUpdatedAt メソッド */
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ビジネスメソッド
    /**
     * フルネームを取得
     * @return 氏名
     */
    public String getFullName() { return name; }

    /**
     * ユーザーが指定された権限を持っているかを判定
     * @param targetRole 確認したい権限
     * @return 権限を持っている場合true
     */
    public boolean hasRole(String targetRole) {
        return role != null && role.equals(targetRole);
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
               ", name='" + name + '\'' +
               ", companyId=" + companyId +
               ", role='" + role + '\'' +
               ", active=" + active +
               ", createdBy=" + createdBy +
               ", createdAt=" + createdAt +
               ", updatedBy=" + updatedBy +
               ", updatedAt=" + updatedAt +
               '}';
    }
}

