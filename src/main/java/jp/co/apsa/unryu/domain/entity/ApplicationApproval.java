/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.unryu.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
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
 * 申請承認履歴エンティティクラス
 * 
 * 申請に対する承認者の承認・却下の履歴を管理します。
 * 承認フローの各ステップでの承認状況を記録し、
 * 承認プロセスの透明性と追跡可能性を確保します。
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "application_approvals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"application", "approver", "delegatedApprover"})
@EntityListeners(AuditingEntityListener.class)
public class ApplicationApproval {

    /**
     * 承認履歴ID（主キー）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 申請ID
     */
    @NotNull(message = "申請IDは必須です")
    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    /**
     * 承認ステップの順序
     */
    @NotNull(message = "ステップ順序は必須です")
    @Column(name = "step_order", nullable = false)
    private Integer stepOrder;

    /**
     * 承認者ID
     */
    @NotNull(message = "承認者IDは必須です")
    @Column(name = "approver_id", nullable = false)
    private Long approverId;

    /**
     * 承認状態（PENDING, APPROVED, REJECTED, DELEGATED, SKIPPED）
     */
    @NotBlank(message = "承認状態は必須です")
    @Size(max = 20, message = "承認状態は20文字以内で入力してください")
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    /**
     * 承認アクション（APPROVE, REJECT, DELEGATE, RETURN）
     */
    @Size(max = 20, message = "承認アクションは20文字以内で入力してください")
    @Column(name = "action", length = 20)
    private String action;

    /**
     * 承認コメント
     */
    @Size(max = 1000, message = "コメントは1000文字以内で入力してください")
    @Column(name = "comment", length = 1000)
    private String comment;

    /**
     * 承認期限
     */
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    /**
     * 承認日時
     */
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    /**
     * 却下日時
     */
    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    /**
     * 委任先承認者ID
     */
    @Column(name = "delegated_to")
    private Long delegatedTo;

    /**
     * 委任日時
     */
    @Column(name = "delegated_at")
    private LocalDateTime delegatedAt;

    /**
     * 代理承認フラグ
     */
    @Builder.Default
    @Column(name = "is_delegated", nullable = false)
    private Boolean isDelegated = false;

    /**
     * 通知送信フラグ
     */
    @Builder.Default
    @Column(name = "notification_sent", nullable = false)
    private Boolean notificationSent = false;

    /**
     * 通知送信日時
     */
    @Column(name = "notification_sent_at")
    private LocalDateTime notificationSentAt;

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
     * 申請
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", insertable = false, updatable = false)
    private Application application;

    /**
     * 承認者
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id", insertable = false, updatable = false)
    private User approver;

    /**
     * 委任先承認者
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delegated_to", insertable = false, updatable = false)
    private User delegatedApprover;
}
