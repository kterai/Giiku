/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
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
 * 部署エンティティクラス
 * 
 * 組織の部署情報を管理します。
 * 階層構造をサポートし、親部署と子部署の関係を表現できます。
 * 承認ルートの設定において重要な役割を果たします。
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"parent", "children", "manager", "responsibleUsers", "users", "approvalRoutes"})
@EntityListeners(AuditingEntityListener.class)
public class Department {

    /**
     * 部署ID（主キー）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 部署名
     */
    @NotBlank(message = "部署名は必須です")
    @Size(max = 100, message = "部署名は100文字以内で入力してください")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * 部署コード（一意識別子）
     */
    @Size(max = 20, message = "部署コードは20文字以内で入力してください")
    @Column(name = "code", unique = true, length = 20)
    private String code;

    /**
     * 親部署ID（階層構造用）
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 部署長ID
     */
    @Column(name = "manager_id")
    private Long managerId;

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
     * 親部署
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Department parent;

    /**
     * 子部署一覧
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Department> children;

    /**
     * 部署長
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", insertable = false, updatable = false)
    private User manager;

    /**
     * 部署責任者一覧
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "department_responsible_users",
        joinColumns = @JoinColumn(name = "department_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> responsibleUsers;

    /**
     * 部署責任者ID一覧（フォームバインド用）
     */
    @Transient
    private List<Long> responsibleUserIds;

    /**
     * 所属ユーザー一覧
     */
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    /**
     * 承認ルート一覧（承認者部署として）
     */
    @OneToMany(mappedBy = "approverDepartment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ApprovalRoute> approvalRoutes;
}
