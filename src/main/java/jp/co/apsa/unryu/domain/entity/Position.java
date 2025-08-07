package jp.co.apsa.unryu.domain.entity;

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
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 役職エンティティクラス
 *
 * <p>組織内の役職情報を管理します。階層レベルや管理職フラグを
 * 保持し、承認権限の判定などに利用されます。</p>
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "positions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"users"})
@EntityListeners(AuditingEntityListener.class)
public class Position {

    /** 役職ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 役職名 */
    @NotBlank
    @Size(max = 100)
    @Column(name = "position_name", nullable = false, length = 100)
    private String positionName;

    /** 役職コード */
    @Size(max = 50)
    @Column(name = "position_code", unique = true, length = 50)
    private String positionCode;

    /** 階層レベル（数値が大きいほど上位） */
    @Column(name = "hierarchy_level")
    private Integer hierarchyLevel;

    /** 管理職フラグ */
    @Column(name = "is_manager", nullable = false)
    private Boolean isManager = false;

    /** 表示順序 */
    @Column(name = "display_order")
    private Integer displayOrder;

    /** 有効フラグ */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

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

    /** 所属ユーザー一覧 */
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;
}
