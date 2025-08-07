package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 申請承認ルートエンティティ。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "application_approval_routes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"application"})
@EntityListeners(AuditingEntityListener.class)
public class ApplicationApprovalRoute {

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 申請ID */
    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    /** 承認ステップ順序 */
    @Column(name = "step_order", nullable = false)
    private Integer stepOrder;

    /** 申請 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", insertable = false, updatable = false)
    private Application application;

    @Column(name = "approver_id")
    private Long approverId;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "action", length = 20)
    private String action;

    @Column(name = "comment", length = 1000)
    private String comment;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
