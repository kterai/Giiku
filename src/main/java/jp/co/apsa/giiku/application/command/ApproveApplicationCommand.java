package jp.co.apsa.giiku.application.command;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import jp.co.apsa.giiku.domain.valueobject.ApprovalStatus;
import lombok.Builder;
import lombok.Data;

/**
 * 申請承認コマンド
 * 
 * 新人エンジニア向け学習ポイント：
 * - CQRSパターンにおけるCommandの役割
 * - Immutableオブジェクトの設計
 * - バリデーションアノテーションの使用
 * - ビジネスロジックとデータ転送の分離
 * 
 * @author 株式会社アプサ
 * @since 2025
 * @version 1.0
 */
@Data
@Builder
public class ApproveApplicationCommand {

    /**
     * 申請ID
     */
    @NotNull(message = "申請IDは必須です")
    private final Long applicationId;

    /**
     * 承認者ユーザーID
     */
    @NotNull(message = "承認者IDは必須です")
    private final Long approverId;

    /**
     * 承認ステップID
     */
    @NotNull(message = "承認ステップIDは必須です")
    private final Long approvalStepId;

    /**
     * 承認ステータス
     */
    @NotNull(message = "承認ステータスは必須です")
    private final ApprovalStatus approvalStatus;

    /**
     * 承認コメント
     */
    @Size(max = 1000, message = "承認コメントは1000文字以内で入力してください")
    private final String approvalComment;

    /**
     * 代理承認フラグ
     */
    @Builder.Default
    private final Boolean isProxyApproval = false;

    /**
     * 代理承認者ID（代理承認の場合）
     */
    private final Long proxyApproverId;

    /**
     * 承認日時
     * 通常は現在日時が設定されるが、テストや特殊ケース用に設定可能
     */
    private final LocalDateTime approvalDateTime;

    /**
     * IPアドレス
     * 監査ログ用
     */
    @Size(max = 45, message = "IPアドレスは45文字以内で入力してください")
    private final String ipAddress;

    /**
     * ユーザーエージェント
     * 監査ログ用
     */
    @Size(max = 500, message = "ユーザーエージェントは500文字以内で入力してください")
    private final String userAgent;

    /**
     * セッションID
     * 監査ログ用
     */
    @Size(max = 100, message = "セッションIDは100文字以内で入力してください")
    private final String sessionId;

    /**
     * 強制承認フラグ
     * 管理者による強制承認の場合にtrue
     */
    @Builder.Default
    private final Boolean isForceApproval = false;

    /**
     * 強制承認理由
     */
    @Size(max = 500, message = "強制承認理由は500文字以内で入力してください")
    private final String forceApprovalReason;

    /**
     * 添付ファイルパス
     * 承認時に追加される添付ファイル（承認印など）
     */
    private final String attachmentFilePath;

    /**
     * 次承認者への通知フラグ
     */
    @Builder.Default
    private final Boolean shouldNotifyNextApprover = true;

    /**
     * 申請者への通知フラグ
     */
    @Builder.Default
    private final Boolean shouldNotifyApplicant = true;

    /**
     * 承認時の追加メタデータ
     * JSONBフィールドに保存される追加情報
     */
    private final String additionalMetadata;

    /**
     * バリデーション：代理承認の場合は代理承認者IDが必須
     * 
     * 学習ポイント：
     * - カスタムバリデーションの実装
     * - ビジネスルールの検証
     * - 複数フィールド間の整合性チェック
     * 
     * @return バリデーション結果
     */
    public boolean isValid() {
        // 代理承認の場合は代理承認者IDが必須
        if (Boolean.TRUE.equals(isProxyApproval)) {
            if (proxyApproverId == null) {
                return false;
            }
            // 代理承認者と承認者は異なる必要がある
            if (proxyApproverId.equals(approverId)) {
                return false;
            }
        }

        // 強制承認の場合は理由が必須
        if (Boolean.TRUE.equals(isForceApproval)) {
            if (forceApprovalReason == null || forceApprovalReason.trim().isEmpty()) {
                return false;
            }
        }

        // 却下の場合はコメントが必須
        if (approvalStatus != null &&
            approvalStatus.getStatus() == ApprovalStatus.Status.REJECTED) {
            if (approvalComment == null || approvalComment.trim().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * 監査ログ用の説明文を生成
     * 
     * @return 説明文
     */
    public String generateAuditDescription() {
        StringBuilder description = new StringBuilder();

        description.append("申請承認処理: ");
        description.append("申請ID=").append(applicationId);
        description.append(", ステータス=").append(approvalStatus);

        if (Boolean.TRUE.equals(isProxyApproval)) {
            description.append(" (代理承認)");
        }

        if (Boolean.TRUE.equals(isForceApproval)) {
            description.append(" (強制承認)");
        }

        return description.toString();
    }

    /**
     * 承認日時を取得
     * 設定されていない場合は現在日時を返す
     * 
     * @return 承認日時
     */
    public LocalDateTime getEffectiveApprovalDateTime() {
        return approvalDateTime != null ? approvalDateTime : LocalDateTime.now();
    }

    /**
     * 通知が必要かどうかを判定
     * 
     * @return 通知が必要な場合true
     */
    public boolean requiresNotification() {
        return Boolean.TRUE.equals(shouldNotifyNextApprover) || 
               Boolean.TRUE.equals(shouldNotifyApplicant);
    }
}
