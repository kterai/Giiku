package jp.co.apsa.giiku.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ユーザーエンティティクラス。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    /** ユーザーID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** ユーザー名 */
    @NotBlank(message = "ユーザー名は必須です")
    @Size(max = 50, message = "ユーザー名は50文字以内で入力してください")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    /** パスワード */
    @NotBlank(message = "パスワードは必須です")
    @Size(max = 255, message = "パスワードは255文字以内で入力してください")
    @Column(name = "password", nullable = false)
    private String password;

    /** メールアドレス */
    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "有効なメールアドレス形式で入力してください")
    @Size(max = 255, message = "メールアドレスは255文字以内で入力してください")
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    /** 氏名 */
    @NotBlank(message = "氏名は必須です")
    @Size(max = 100, message = "氏名は100文字以内で入力してください")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /** 所属会社 */
    @NotNull(message = "会社は必須です")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    /** ロール */
    @NotBlank(message = "ロールは必須です")
    @Size(max = 20, message = "ロールは20文字以内で入力してください")
    @Column(name = "role", nullable = false, length = 20)
    private String role;

    /** 性別 */
    @NotNull(message = "性別は必須です")
    @Column(name = "gender", nullable = false)
    private Short gender;

    /** 誕生日 */
    @NotNull(message = "誕生日は必須です")
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    /** 有効フラグ */
    @NotNull(message = "有効フラグは必須です")
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    /** 作成者ID */
    @CreatedBy
    @NotNull(message = "作成者IDは必須です")
    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    /** 作成日時 */
    @CreatedDate
    @NotNull(message = "作成日時は必須です")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 更新者ID */
    @LastModifiedBy
    @NotNull(message = "更新者IDは必須です")
    @Column(name = "updated_by")
    private Long updatedBy;

    /** 更新日時 */
    @LastModifiedDate
    @NotNull(message = "更新日時は必須です")
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
