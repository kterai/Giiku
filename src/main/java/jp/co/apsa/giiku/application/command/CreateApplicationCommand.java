/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.giiku.application.command;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.apsa.giiku.domain.entity.ApplicationType;
import jp.co.apsa.giiku.domain.valueobject.Priority;

/**
 * 申請作成コマンド
 * 
 * <p>申請作成に必要な情報を保持するCQRSコマンドクラスです。
 * 不変オブジェクトとして設計され、Bean Validationによる入力検証を提供します。
 * Hexagonal ArchitectureのApplication層でのコマンドとして機能します。</p>
 * 
 * <p>このクラスは以下の特徴を持ちます：</p>
 * <ul>
 *   <li>不変オブジェクト設計（Immutable）</li>
 *   <li>Builder パターンによる柔軟な構築</li>
 *   <li>Bean Validation による入力検証</li>
 *   <li>JSON シリアライゼーション対応</li>
 * </ul>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public final class CreateApplicationCommand {

    /**
     * 申請種別
     */
    @NotNull(message = "申請種別は必須です")
    private final ApplicationType applicationType;

    /**
     * 申請者ID
     */
    @NotNull(message = "申請者IDは必須です")
    @Positive(message = "申請者IDは正の値である必要があります")
    private final Long applicantId;

    /**
     * 承認者ID
     */
    @NotNull(message = "承認者IDは必須です")
    @Positive(message = "承認者IDは正の値である必要があります")
    private final Long approverId;

    /**
     * 申請タイトル
     */
    @NotBlank(message = "申請タイトルは必須です")
    @Size(max = 200, message = "申請タイトルは200文字以内で入力してください")
    private final String title;

    /**
     * 申請内容
     */
    @NotBlank(message = "申請内容は必須です")
    @Size(max = 4000, message = "申請内容は4000文字以内で入力してください")
    private final String content;

    /**
     * 優先度
     */
    @NotNull(message = "優先度は必須です")
    private final Priority priority;

    /**
     * 期限日
     */
    @Future(message = "期限日は未来の日時である必要があります")
    private final LocalDateTime dueDate;

    /**
     * 部署ID
     */
    @Positive(message = "部署IDは正の値である必要があります")
    private final Long departmentId;

    /**
     * 申請理由
     */
    @Size(max = 1000, message = "申請理由は1000文字以内で入力してください")
    private final String reason;

    /**
     * 添付ファイル情報リスト
     */
    @Valid
    private final List<AttachmentInfo> attachments;

    /**
     * フォームデータ（動的フィールド）
     */
    @Valid
    private final Map<String, Object> formData;

    /**
     * 緊急フラグ
     */
    @NotNull(message = "緊急フラグは必須です")
    private final Boolean isUrgent;

    /**
     * 申請金額（金額を伴う申請の場合）
     */
    @DecimalMin(value = "0.0", inclusive = false, message = "申請金額は0より大きい値である必要があります")
    private final java.math.BigDecimal amount;

    /**
     * 通貨コード（申請金額がある場合）
     */
    @Pattern(regexp = "^[A-Z]{3}$", message = "通貨コードは3文字のアルファベット大文字である必要があります")
    private final String currencyCode;

    /**
     * 申請期間開始日
     */
    private final LocalDateTime startDate;

    /**
     * 申請期間終了日
     */
    private final LocalDateTime endDate;

    /**
     * 備考
     */
    @Size(max = 2000, message = "備考は2000文字以内で入力してください")
    private final String remarks;

    /**
     * コンストラクタ（JSON デシリアライゼーション用）
     * 
     * @param applicationType 申請種別
     * @param applicantId 申請者ID
     * @param approverId 承認者ID
     * @param title 申請タイトル
     * @param content 申請内容
     * @param priority 優先度
     * @param dueDate 期限日
     * @param departmentId 部署ID
     * @param reason 申請理由
     * @param attachments 添付ファイル情報リスト
     * @param formData フォームデータ
     * @param isUrgent 緊急フラグ
     * @param amount 申請金額
     * @param currencyCode 通貨コード
     * @param startDate 申請期間開始日
     * @param endDate 申請期間終了日
     * @param remarks 備考
     */
    @JsonCreator
    public CreateApplicationCommand(
            @JsonProperty("applicationType") ApplicationType applicationType,
            @JsonProperty("applicantId") Long applicantId,
            @JsonProperty("approverId") Long approverId,
            @JsonProperty("title") String title,
            @JsonProperty("content") String content,
            @JsonProperty("priority") Priority priority,
            @JsonProperty("dueDate") LocalDateTime dueDate,
            @JsonProperty("departmentId") Long departmentId,
            @JsonProperty("reason") String reason,
            @JsonProperty("attachments") List<AttachmentInfo> attachments,
            @JsonProperty("formData") Map<String, Object> formData,
            @JsonProperty("isUrgent") Boolean isUrgent,
            @JsonProperty("amount") java.math.BigDecimal amount,
            @JsonProperty("currencyCode") String currencyCode,
            @JsonProperty("startDate") LocalDateTime startDate,
            @JsonProperty("endDate") LocalDateTime endDate,
            @JsonProperty("remarks") String remarks) {
        this.applicationType = applicationType;
        this.applicantId = applicantId;
        this.approverId = approverId;
        this.title = title;
        this.content = content;
        this.priority = priority;
        this.dueDate = dueDate;
        this.departmentId = departmentId;
        this.reason = reason;
        this.attachments = attachments != null ? List.copyOf(attachments) : List.of();
        this.formData = formData != null ? Map.copyOf(formData) : Map.of();
        this.isUrgent = isUrgent;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remarks = remarks;
    }

    /**
     * 申請種別を取得します
     * 
     * @return 申請種別
     */
    public ApplicationType getApplicationType() {
        return applicationType;
    }

    /**
     * 申請者IDを取得します
     * 
     * @return 申請者ID
     */
    public Long getApplicantId() {
        return applicantId;
    }

    /**
     * 承認者IDを取得します
     * 
     * @return 承認者ID
     */
    public Long getApproverId() {
        return approverId;
    }

    /**
     * 申請タイトルを取得します
     * 
     * @return 申請タイトル
     */
    public String getTitle() {
        return title;
    }

    /**
     * 申請内容を取得します
     * 
     * @return 申請内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 優先度を取得します
     * 
     * @return 優先度
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * 期限日を取得します
     * 
     * @return 期限日
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * 部署IDを取得します
     * 
     * @return 部署ID
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * 申請理由を取得します
     * 
     * @return 申請理由
     */
    public String getReason() {
        return reason;
    }

    /**
     * 添付ファイル情報リストを取得します
     * 
     * @return 添付ファイル情報リスト（不変）
     */
    public List<AttachmentInfo> getAttachments() {
        return attachments;
    }

    /**
     * フォームデータを取得します
     * 
     * @return フォームデータ（不変）
     */
    public Map<String, Object> getFormData() {
        return formData;
    }

    /**
     * 緊急フラグを取得します
     * 
     * @return 緊急フラグ
     */
    public Boolean getIsUrgent() {
        return isUrgent;
    }

    /**
     * 申請金額を取得します
     * 
     * @return 申請金額
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }

    /**
     * 通貨コードを取得します
     * 
     * @return 通貨コード
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * 申請期間開始日を取得します
     * 
     * @return 申請期間開始日
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * 申請期間終了日を取得します
     * 
     * @return 申請期間終了日
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * 備考を取得します
     * 
     * @return 備考
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 金額を伴う申請かどうかを判定します
     * 
     * @return 金額を伴う申請の場合true
     */
    public boolean hasAmount() {
        return amount != null && amount.compareTo(java.math.BigDecimal.ZERO) > 0;
    }

    /**
     * 期間を伴う申請かどうかを判定します
     * 
     * @return 期間を伴う申請の場合true
     */
    public boolean hasPeriod() {
        return startDate != null && endDate != null;
    }

    /**
     * 添付ファイルがあるかどうかを判定します
     * 
     * @return 添付ファイルがある場合true
     */
    public boolean hasAttachments() {
        return attachments != null && !attachments.isEmpty();
    }

    /**
     * フォームデータがあるかどうかを判定します
     * 
     * @return フォームデータがある場合true
     */
    public boolean hasFormData() {
        return formData != null && !formData.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateApplicationCommand that = (CreateApplicationCommand) o;
        return Objects.equals(applicationType, that.applicationType) &&
               Objects.equals(applicantId, that.applicantId) &&
               Objects.equals(approverId, that.approverId) &&
               Objects.equals(title, that.title) &&
               Objects.equals(content, that.content) &&
               Objects.equals(priority, that.priority) &&
               Objects.equals(dueDate, that.dueDate) &&
               Objects.equals(departmentId, that.departmentId) &&
               Objects.equals(reason, that.reason) &&
               Objects.equals(attachments, that.attachments) &&
               Objects.equals(formData, that.formData) &&
               Objects.equals(isUrgent, that.isUrgent) &&
               Objects.equals(amount, that.amount) &&
               Objects.equals(currencyCode, that.currencyCode) &&
               Objects.equals(startDate, that.startDate) &&
               Objects.equals(endDate, that.endDate) &&
               Objects.equals(remarks, that.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationType, applicantId, approverId, title, content, 
                          priority, dueDate, departmentId, reason, attachments, 
                          formData, isUrgent, amount, currencyCode, startDate, 
                          endDate, remarks);
    }

    @Override
    public String toString() {
        return "CreateApplicationCommand{" +
               "applicationType=" + applicationType +
               ", applicantId=" + applicantId +
               ", approverId=" + approverId +
               ", title='" + title + '\'' +
               ", content='" + content + '\'' +
               ", priority=" + priority +
               ", dueDate=" + dueDate +
               ", departmentId=" + departmentId +
               ", reason='" + reason + '\'' +
               ", attachments=" + attachments +
               ", formData=" + formData +
               ", isUrgent=" + isUrgent +
               ", amount=" + amount +
               ", currencyCode='" + currencyCode + '\'' +
               ", startDate=" + startDate +
               ", endDate=" + endDate +
               ", remarks='" + remarks + '\'' +
               '}';
    }

    /**
     * Builder クラス
     * 
     * <p>CreateApplicationCommand の構築を支援するBuilderパターンの実装です。
     * 必須フィールドの設定を強制し、オプションフィールドの柔軟な設定を可能にします。</p>
     */
    public static class Builder {
        private ApplicationType applicationType;
        private Long applicantId;
        private Long approverId;
        private String title;
        private String content;
        private Priority priority;
        private LocalDateTime dueDate;
        private Long departmentId;
        private String reason;
        private List<AttachmentInfo> attachments;
        private Map<String, Object> formData;
        private Boolean isUrgent = false;
        private java.math.BigDecimal amount;
        private String currencyCode;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String remarks;

        /**
         * 申請種別を設定します
         * 
         * @param applicationType 申請種別
         * @return Builder インスタンス
         */
        public Builder applicationType(ApplicationType applicationType) {
            this.applicationType = applicationType;
            return this;
        }

        /**
         * 申請者IDを設定します
         * 
         * @param applicantId 申請者ID
         * @return Builder インスタンス
         */
        public Builder applicantId(Long applicantId) {
            this.applicantId = applicantId;
            return this;
        }

        /**
         * 承認者IDを設定します
         * 
         * @param approverId 承認者ID
         * @return Builder インスタンス
         */
        public Builder approverId(Long approverId) {
            this.approverId = approverId;
            return this;
        }

        /**
         * 申請タイトルを設定します
         * 
         * @param title 申請タイトル
         * @return Builder インスタンス
         */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /**
         * 申請内容を設定します
         * 
         * @param content 申請内容
         * @return Builder インスタンス
         */
        public Builder content(String content) {
            this.content = content;
            return this;
        }

        /**
         * 優先度を設定します
         * 
         * @param priority 優先度
         * @return Builder インスタンス
         */
        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        /**
         * 期限日を設定します
         * 
         * @param dueDate 期限日
         * @return Builder インスタンス
         */
        public Builder dueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        /**
         * 部署IDを設定します
         * 
         * @param departmentId 部署ID
         * @return Builder インスタンス
         */
        public Builder departmentId(Long departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        /**
         * 申請理由を設定します
         * 
         * @param reason 申請理由
         * @return Builder インスタンス
         */
        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        /**
         * 添付ファイル情報リストを設定します
         * 
         * @param attachments 添付ファイル情報リスト
         * @return Builder インスタンス
         */
        public Builder attachments(List<AttachmentInfo> attachments) {
            this.attachments = attachments;
            return this;
        }

        /**
         * フォームデータを設定します
         * 
         * @param formData フォームデータ
         * @return Builder インスタンス
         */
        public Builder formData(Map<String, Object> formData) {
            this.formData = formData;
            return this;
        }

        /**
         * 緊急フラグを設定します
         * 
         * @param isUrgent 緊急フラグ
         * @return Builder インスタンス
         */
        public Builder isUrgent(Boolean isUrgent) {
            this.isUrgent = isUrgent;
            return this;
        }

        /**
         * 申請金額を設定します
         * 
         * @param amount 申請金額
         * @return Builder インスタンス
         */
        public Builder amount(java.math.BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        /**
         * 通貨コードを設定します
         * 
         * @param currencyCode 通貨コード
         * @return Builder インスタンス
         */
        public Builder currencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        /**
         * 申請期間開始日を設定します
         * 
         * @param startDate 申請期間開始日
         * @return Builder インスタンス
         */
        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        /**
         * 申請期間終了日を設定します
         * 
         * @param endDate 申請期間終了日
         * @return Builder インスタンス
         */
        public Builder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        /**
         * 備考を設定します
         * 
         * @param remarks 備考
         * @return Builder インスタンス
         */
        public Builder remarks(String remarks) {
            this.remarks = remarks;
            return this;
        }

        /**
         * CreateApplicationCommand インスタンスを構築します
         * 
         * @return CreateApplicationCommand インスタンス
         * @throws IllegalArgumentException 必須フィールドが未設定の場合
         */
        public CreateApplicationCommand build() {
            validateRequiredFields();
            return new CreateApplicationCommand(
                applicationType, applicantId, approverId, title, content,
                priority, dueDate, departmentId, reason, attachments,
                formData, isUrgent, amount, currencyCode, startDate,
                endDate, remarks
            );
        }

        /**
         * 必須フィールドの検証を行います
         * 
         * @throws IllegalArgumentException 必須フィールドが未設定の場合
         */
        private void validateRequiredFields() {
            if (applicationType == null) {
                throw new IllegalArgumentException("申請種別は必須です");
            }
            if (applicantId == null) {
                throw new IllegalArgumentException("申請者IDは必須です");
            }
            if (approverId == null) {
                throw new IllegalArgumentException("承認者IDは必須です");
            }
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("申請タイトルは必須です");
            }
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("申請内容は必須です");
            }
            if (priority == null) {
                throw new IllegalArgumentException("優先度は必須です");
            }
        }
    }

    /**
     * 添付ファイル情報クラス
     * 
     * <p>申請に添付されるファイルの情報を保持します。</p>
     */
    public static class AttachmentInfo {

        /**
         * ファイル名
         */
        @NotBlank(message = "ファイル名は必須です")
        @Size(max = 255, message = "ファイル名は255文字以内で入力してください")
        private final String fileName;

        /**
         * ファイルサイズ（バイト）
         */
        @NotNull(message = "ファイルサイズは必須です")
        @Positive(message = "ファイルサイズは正の値である必要があります")
        private final Long fileSize;

        /**
         * MIMEタイプ
         */
        @NotBlank(message = "MIMEタイプは必須です")
        private final String mimeType;

        /**
         * ファイルパス
         */
        @NotBlank(message = "ファイルパスは必須です")
        private final String filePath;

        /**
         * ファイル説明
         */
        @Size(max = 500, message = "ファイル説明は500文字以内で入力してください")
        private final String description;

        /**
         * コンストラクタ
         * 
         * @param fileName ファイル名
         * @param fileSize ファイルサイズ
         * @param mimeType MIMEタイプ
         * @param filePath ファイルパス
         * @param description ファイル説明
         */
        @JsonCreator
        public AttachmentInfo(
                @JsonProperty("fileName") String fileName,
                @JsonProperty("fileSize") Long fileSize,
                @JsonProperty("mimeType") String mimeType,
                @JsonProperty("filePath") String filePath,
                @JsonProperty("description") String description) {
            this.fileName = fileName;
            this.fileSize = fileSize;
            this.mimeType = mimeType;
            this.filePath = filePath;
            this.description = description;
        }

        public String getFileName() { return fileName; }
        public Long getFileSize() { return fileSize; }
        public String getMimeType() { return mimeType; }
        public String getFilePath() { return filePath; }
        public String getDescription() { return description; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AttachmentInfo that = (AttachmentInfo) o;
            return Objects.equals(fileName, that.fileName) &&
                   Objects.equals(fileSize, that.fileSize) &&
                   Objects.equals(mimeType, that.mimeType) &&
                   Objects.equals(filePath, that.filePath) &&
                   Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fileName, fileSize, mimeType, filePath, description);
        }

        @Override
        public String toString() {
            return "AttachmentInfo{" +
                   "fileName='" + fileName + '\'' +
                   ", fileSize=" + fileSize +
                   ", mimeType='" + mimeType + '\'' +
                   ", filePath='" + filePath + '\'' +
                   ", description='" + description + '\'' +
                   '}';
        }
    }
}
