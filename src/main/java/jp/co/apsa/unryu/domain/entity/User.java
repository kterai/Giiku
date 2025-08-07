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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ユーザーエンティティクラス
 * 
 * システムを利用するユーザーの情報を管理します。
 * 認証、認可、部署所属、承認フローでの役割を担います。
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"department", "position", "supervisor", "applications", "approvals"})
@EntityListeners(AuditingEntityListener.class)
public class User {

    /**
     * ユーザーID（主キー）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * ユーザー名（ログイン用）
     */
    @NotBlank(message = "ユーザー名は必須です")
    @Size(max = 50, message = "ユーザー名は50文字以内で入力してください")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    /**
     * パスワード（ハッシュ化済み）
     */
    @NotBlank(message = "パスワードは必須です")
    @Size(max = 255, message = "パスワードは255文字以内で入力してください")
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * メールアドレス
     */
    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "正しいメールアドレス形式で入力してください")
    @Size(max = 100, message = "メールアドレスは100文字以内で入力してください")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    /**
     * 氏名
     */
    @NotBlank(message = "氏名は必須です")
    @Size(max = 100, message = "氏名は100文字以内で入力してください")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * 部署ID
     */
    @NotNull(message = "部署は必須です")
    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    /**
     * 役職ID
     */
    @Column(name = "position_id")
    private Long positionId;

    /**
     * 上長ユーザーID
     */
    @Column(name = "supervisor_id")
    private Long supervisorId;

    /**
     * 役割（ADMIN, MANAGER, USER等）
     */
    @NotBlank(message = "役割は必須です")
    @Size(max = 20, message = "役割は20文字以内で入力してください")
    @Column(name = "role", nullable = false, length = 20)
    private String role;

    /**
     * SlackユーザーID（通知連携用）
     */
    @Size(max = 50, message = "SlackユーザーIDは50文字以内で入力してください")
    @Column(name = "slack_id", length = 50)
    private String slackId;

    /**
     * アクティブフラグ
     */
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    /**
     * 承認者フラグ
     */
    @Column(name = "approver", nullable = false)
    private Boolean approver = false;

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
     * 所属部署
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private Department department;

    /**
     * 役職
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", insertable = false, updatable = false)
    private Position position;

    /**
     * 上長
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id", insertable = false, updatable = false)
    private User supervisor;

    /**
     * 申請一覧（申請者として）
     */
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications;

    /**
     * 承認履歴一覧（承認者として）
     */
    @OneToMany(mappedBy = "approver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ApplicationApproval> approvals;

    /**
     * Slack通知用のユーザーIDを取得します。
     *
     * @return SlackユーザーID
     */
    public String getSlackUserId() {
        return slackId;
    }
}
