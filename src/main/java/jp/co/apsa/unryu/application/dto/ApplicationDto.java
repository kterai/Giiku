package jp.co.apsa.unryu.application.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 申請情報を汎用的に表すDTO。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Data
@Builder
public class ApplicationDto {
    /** 申請ID */
    private Long id;
    /** 申請種別名 */
    private String typeName;
    /** 申請タイトル */
    private String title;
    /** 申請者名 */
    private String applicantName;
    /** ステータス */
    private String status;
    /** 申請内容 */
    private String content;
    /** 申請番号 */
    private String applicationNumber;
    /** 現在の承認ステップ */
    private Integer currentStep;
    /** フォームデータ */
    private String formData;
    /** 申請理由 */
    private String reason;
    /** 緊急度 */
    private String priority;
    /** 希望処理日 */
    private java.time.LocalDateTime requestedDate;
    /** 申請日時 */
    private java.time.LocalDateTime applicationDate;
    /** 承認者ID */
    private Long approverId;
    /** 期限日 */
    private java.time.LocalDateTime dueDate;
    /** 部署ID */
    private Long departmentId;
    /** 緊急フラグ */
    private Boolean isUrgent;
    /** 申請金額 */
    private java.math.BigDecimal amount;
    /** 通貨コード */
    private String currencyCode;
    /** 申請期間開始日 */
    private java.time.LocalDateTime startDate;
    /** 申請期間終了日 */
    private java.time.LocalDateTime endDate;
    /** 備考 */
    private String remarks;
    /** 承認完了日時 */
    private java.time.LocalDateTime approvedAt;
    /** 却下日時 */
    private java.time.LocalDateTime rejectedAt;
    /** 作成者ID */
    private Long createdBy;
    /** 作成日時 */
    private java.time.LocalDateTime createdAt;
    /** 更新者ID */
    private Long updatedBy;
    /** 更新日時 */
    private java.time.LocalDateTime updatedAt;
}
