/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.giiku.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 申請エンティティクラス
 * 
 * ユーザーが提出した申請の情報を管理します。
 * 申請の状態管理、フォームデータの保持、承認フローとの連携を行います。
 * 動的フォームシステムに対応し、JSON形式でフォームデータを格納します。
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"applicationType", "applicant", "approvals", "attachments", "department"})
@EntityListeners(AuditingEntityListener.class)
public class Application {

    /**
     * 申請ID（主キー）
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
     * 申請者ID
     */
    @NotNull(message = "申請者は必須です")
    @Column(name = "applicant_id", nullable = false)
    private Long applicantId;

    /**
     * 申請番号（自動生成される一意識別子）
     */
    @Size(max = 50, message = "申請番号は50文字以内で入力してください")
    @Column(name = "application_number", unique = true, length = 50)
    private String applicationNumber;

    /**
     * 申請タイトル
     */
    @NotBlank(message = "申請タイトルは必須です")
    @Size(max = 200, message = "申請タイトルは200文字以内で入力してください")
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /**
     * 申請内容
     */
    @NotBlank(message = "申請内容は必須です")
    @Size(max = 4000, message = "申請内容は4000文字以内で入力してください")
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    /**
     * 申請状態（DRAFT, SUBMITTED, IN_PROGRESS, APPROVED, REJECTED, CANCELLED）
     */
    @NotBlank(message = "申請状態は必須です")
    @Size(max = 20, message = "申請状態は20文字以内で入力してください")
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    /**
     * 現在の承認ステップ
     */
    @Column(name = "current_step")
    private Integer currentStep;

    /**
     * フォームデータ（JSON形式）
     * 動的フォームの入力内容を格納
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "form_data", columnDefinition = "jsonb")
    private String formData;

    /**
     * 申請理由・備考
     */
    @Size(max = 1000, message = "申請理由は1000文字以内で入力してください")
    @Column(name = "reason", length = 1000)
    private String reason;

    /**
     * 緊急度（LOW, NORMAL, HIGH, URGENT）
     */
    @Size(max = 10, message = "緊急度は10文字以内で入力してください")
    @Column(name = "priority", length = 10)
    private String priority;

    /**
     * 希望処理日
     */
    @Column(name = "requested_date")
    private LocalDateTime requestedDate;

    /**
     * 申請日時
     */
    @Column(name = "application_date")
    private LocalDateTime applicationDate;

    /**
     * 承認者ID
     */
    @Column(name = "approver_id")
    private Long approverId;

    /**
     * 期限日
     */
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    /** 部署ID */
    @Column(name = "department_id")
    private Long departmentId;

    /** 緊急フラグ */
    @Column(name = "is_urgent")
    @Default
    private Boolean isUrgent = false;

    /** 申請金額 */
    @Column(name = "amount")
    private java.math.BigDecimal amount;

    /** 通貨コード */
    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    /** 申請期間開始日 */
    @Column(name = "start_date")
    private LocalDateTime startDate;

    /** 申請期間終了日 */
    @Column(name = "end_date")
    private LocalDateTime endDate;

    /** 備考 */
    @Column(name = "remarks", length = 2000)
    private String remarks;

    /**
     * 承認完了日時
     */
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    /**
     * 却下日時
     */
    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

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
     * 申請者
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", insertable = false, updatable = false)
    private User applicant;

    /**
     * 承認履歴一覧
     */
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ApplicationApproval> approvals;

    /**
     * 添付ファイル一覧
     */
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ApplicationAttachment> attachments;

    /**
     * 部署
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private Department department;
}
