/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 承認ルートエンティティクラス
 * 
 * 申請種別ごとの承認フローを定義します。
 * 承認の順序と承認者の役割・部署を管理し、
 * 複数段階の承認プロセスを実現します。
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "approval_routes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"applicationType"})
@EntityListeners(AuditingEntityListener.class)
public class ApprovalRoute {

    /**
     * 承認ルートID（主キー）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 申請種別ID
     */
    @NotNull(message = "申請種別は必須です")
    @Column(name = "application_type_id", nullable = false)
    private Long applicationTypeId;

    /**
     * 承認ステップの順序
     */
    @NotNull(message = "ステップ順序は必須です")
    @Column(name = "step_order", nullable = false)
    private Integer stepOrder;

    /**
     * 承認者の役割（MANAGER, DIRECTOR, ADMIN等）
     */
    @Size(max = 20, message = "承認者役割は20文字以内で入力してください")
    @Column(name = "approver_role", length = 20)
    private String approverRole;

    /**
     * 承認者の部署ID
     */
    @Column(name = "approver_department_id")
    private Long approverDepartmentId;

    /**
     * 特定承認者ID（特定の人を指定する場合）
     */
    @Column(name = "specific_approver_id")
    private Long specificApproverId;

    /**
     * 部署責任者を承認者とするかどうか
     */
    @Column(name = "department_head", nullable = false)
    private Boolean departmentHead = false;

    /**
     * 確認のみフラグ
     */
    @Column(name = "confirm_only", nullable = false)
    private Boolean confirmOnly = false;

    /**
     * 必須承認フラグ
     */
    @Column(name = "required", nullable = false)
    private Boolean required = true;

    /**
     * 並列承認フラグ（同一ステップ内での並列処理）
     */
    @Column(name = "parallel", nullable = false)
    private Boolean parallel = false;

    /**
     * アクティブフラグ
     */
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    /**
     * 作成者ID
     */
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    /**
     * 作成日時
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 更新者ID
     */
    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;

    /**
     * 更新日時
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // リレーション

    /**
     * 申請種別
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_type_id", insertable = false, updatable = false)
    private ApplicationType applicationType;

    /**
     * 承認者部署
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_department_id", insertable = false, updatable = false)
    private Department approverDepartment;

    /**
     * 特定承認者
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specific_approver_id", insertable = false, updatable = false)
    private User specificApprover;
}
