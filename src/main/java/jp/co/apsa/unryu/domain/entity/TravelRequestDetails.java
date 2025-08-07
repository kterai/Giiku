package jp.co.apsa.unryu.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 出張申請エンティティ。
 *
 * <p>サンプル実装用に最小限のフィールドのみ保持します。</p>
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "travel_request_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class TravelRequestDetails implements org.springframework.data.domain.Persistable<Long> {

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 申請ID */
    @Column(name = "application_id")
    private Long applicationId;

    /** 申請 */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", insertable = false, updatable = false)
    private Application application;

    /** 申請者ID */
    @Column(name = "applicant_id")
    private Long applicantId;

    /** 承認者ID */
    @Column(name = "approver_id")
    private Long approverId;

    /** 申請者名 */
    @NotBlank(message = "申請者名は必須です")
    @Column(name = "applicant_name", nullable = false, length = 100)
    private String applicantName;

    /** 出張先 */
    @NotBlank(message = "出張先は必須です")
    @Column(name = "destination", nullable = false, length = 200)
    private String destination;

    /** 出発日 */
    @NotNull(message = "出発日は必須です")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /** 帰着日 */
    @NotNull(message = "帰着日は必須です")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /** 交通手段 */
    @NotBlank(message = "交通手段は必須です")
    @Column(name = "transport", nullable = false, length = 20)
    private String transport;

    /** 前払い希望 */
    @Column(name = "need_advance")
    @Default
    private Boolean needAdvance = false;

    /** 出張目的 */
    @Size(max = 1000, message = "目的は1000文字以内で入力してください")
    @Column(name = "purpose", length = 1000)
    private String purpose;

    /** 概算費用 */
    @Column(name = "estimated_cost")
    private BigDecimal estimatedCost;

    /** ステータス */
    @Column(name = "status", length = 20)
    private String status;

    /** 現在の承認ステップ */
    @Column(name = "current_step")
    private Integer currentStep;


    /** 作成日時 */
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /** 更新日時 */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    @Transient
    public boolean isNew() {
        return id == null;
    }
}
