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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 申請種別エンティティクラス
 * 
 * 申請の種類と、その申請で使用するフォーム設定を管理します。
 * 各申請種別に対応する承認ルートと動的フォーム設定を保持します。
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "application_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"approvalRoutes", "applications"})
@EntityListeners(AuditingEntityListener.class)
public class ApplicationType {

    /**
     * 申請種別ID（主キー）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 申請種別名
     */
    @NotBlank(message = "申請種別名は必須です")
    @Size(max = 100, message = "申請種別名は100文字以内で入力してください")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * 申請種別コード（一意識別子）
     */
    @Size(max = 50, message = "申請種別コードは50文字以内で入力してください")
    @Column(name = "code", unique = true, length = 50)
    private String code;

    /**
     * 申請種別の説明
     */
    @Size(max = 500, message = "説明は500文字以内で入力してください")
    @Column(name = "description", length = 500)
    private String description;

    /**
     * フォーム設定（JSON形式）
     * 動的フォームの項目定義、バリデーションルール等を格納
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "form_config", columnDefinition = "jsonb")
    private String formConfig;

    /**
     * 表示順序
     */
    @Column(name = "display_order")
    private Integer displayOrder;

    /**
     * アクティブフラグ
     */
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    /**
     * 自動承認フラグ
     */
    @Column(name = "auto_approve", nullable = false)
    private Boolean autoApprove = false;

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
     * 承認ルート一覧
     */
    @OneToMany(mappedBy = "applicationType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ApprovalRoute> approvalRoutes;

    /**
     * 申請一覧
     */
    @OneToMany(mappedBy = "applicationType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications;
}
