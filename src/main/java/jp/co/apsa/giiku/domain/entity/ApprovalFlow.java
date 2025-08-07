package jp.co.apsa.giiku.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 承認フローエンティティクラス
 *
 * <p>申請種別と部署の組み合わせごとに定義される承認フローを表します。</p>
 * <p>各フローは名称、説明、表示順序を持ち、アクティブ状態の管理や
 * 作成者・更新者の監査情報を保持します。</p>
 *
 * 新人エンジニア向け学習ポイント：
 * <ul>
 *   <li>JPAエンティティの基本定義</li>
 *   <li>リレーションの設定 (@ManyToOne)</li>
 *   <li>Spring Data JPA の監査機能</li>
 * </ul>
 *
 * @author 株式会社アプサ
 * @since 2025
 * @version 1.0
 */
@Entity
@Table(name = "approval_flows")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"applicationType", "department"})
@EntityListeners(AuditingEntityListener.class)
public class ApprovalFlow {

    /** 承認フローID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 申請種別ID */
    @NotNull(message = "申請種別は必須です")
    @Column(name = "application_type_id", nullable = false)
    private Long applicationTypeId;

    /** 部署ID (null の場合は全社共通) */
    @Column(name = "department_id")
    private Long departmentId;

    /** フロー名 */
    @NotBlank(message = "フロー名は必須です")
    @Size(max = 100, message = "フロー名は100文字以内で入力してください")
    @Column(name = "flow_name", nullable = false, length = 100)
    private String flowName;

    /** 説明 */
    @Size(max = 500, message = "説明は500文字以内で入力してください")
    @Column(name = "description", length = 500)
    private String description;

    /** アクティブフラグ */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /** 表示順序 */
    @Column(name = "display_order")
    private Integer displayOrder;

    /** 作成者ID */
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    /** 作成日時 */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 更新者ID */
    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;

    /** 更新日時 */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // リレーション

    /** 申請種別 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_type_id", insertable = false, updatable = false)
    private ApplicationType applicationType;

    /** 部署 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private Department department;
}
