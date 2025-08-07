package jp.co.apsa.unryu.application.command;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

/**
 * 申請更新用のCommandクラス
 * 
 * <p>このクラスは、CQRS（Command Query Responsibility Segregation）パターンにおける
 * Commandオブジェクトとして設計されています。申請の更新処理に必要な情報を
 * 不変オブジェクト（Immutable Object）として保持します。</p>
 * 
 * <h3>CQRSパターンについて</h3>
 * <ul>
 *   <li>Command: データの変更操作を表現するオブジェクト</li>
 *   <li>Query: データの取得操作を表現するオブジェクト</li>
 *   <li>責任の分離により、読み取りと書き込みの最適化が可能</li>
 * </ul>
 * 
 * <h3>設計原則</h3>
 * <ul>
 *   <li>不変性（Immutability）: 一度作成されたオブジェクトは変更不可</li>
 *   <li>バリデーション: 不正なデータの混入を防止</li>
 *   <li>明確な責任: 申請更新のみに特化した設計</li>
 * </ul>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 * 
 * @see jp.co.apsa.unryu.application.service.ApplicationService
 * @see jp.co.apsa.unryu.domain.entity.Application
 */
@Data
@Builder
public final class UpdateApplicationCommand {

    /**
     * 申請ID
     * 
     * <p>更新対象となる申請を一意に識別するためのID。
     * 必須項目であり、nullや空文字は許可されません。</p>
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>@NotBlank: null、空文字、空白文字のみの文字列を禁止</li>
     *   <li>@Size: 文字列の長さを制限（1-50文字）</li>
     *   <li>final修飾子: 不変性を保証</li>
     * </ul>
     */
    @NotBlank(message = "申請IDは必須です")
    @Size(min = 1, max = 50, message = "申請IDは1文字以上50文字以下で入力してください")
    private final String applicationId;

    /**
     * 申請タイトル
     * 
     * <p>申請の件名や概要を表すタイトル。
     * ユーザーが申請内容を把握しやすくするための重要な項目です。</p>
     */
    @NotBlank(message = "申請タイトルは必須です")
    @Size(min = 1, max = 200, message = "申請タイトルは1文字以上200文字以下で入力してください")
    private final String title;

    /**
     * 申請内容
     * 
     * <p>申請の詳細な内容や説明。
     * 承認者が判断するために必要な情報を含みます。</p>
     */
    @NotBlank(message = "申請内容は必須です")
    @Size(min = 1, max = 2000, message = "申請内容は1文字以上2000文字以下で入力してください")
    private final String content;

    /**
     * 申請カテゴリ
     * 
     * <p>申請の種類を分類するためのカテゴリ。
     * 例：休暇申請、経費申請、設備利用申請など</p>
     */
    @NotBlank(message = "申請カテゴリは必須です")
    @Size(min = 1, max = 50, message = "申請カテゴリは1文字以上50文字以下で入力してください")
    private final String category;

    /**
     * 優先度
     * 
     * <p>申請の緊急度や重要度を表す。
     * 値の範囲：1（低）〜5（高）</p>
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>プリミティブ型（int）ではなくラッパークラス（Integer）を使用</li>
     *   <li>nullチェックが可能になる</li>
     *   <li>@NotNullでnullを明示的に禁止</li>
     * </ul>
     */
    @NotNull(message = "優先度は必須です")
    private final Integer priority;

    /**
     * 希望処理日
     * 
     * <p>申請者が希望する処理完了日。
     * 承認者のスケジュール調整の参考として使用されます。</p>
     */
    private final LocalDateTime desiredProcessingDate;

    /**
     * 更新者ID
     * 
     * <p>申請を更新するユーザーのID。
     * 監査ログやセキュリティ管理のために必要です。</p>
     */
    @NotBlank(message = "更新者IDは必須です")
    @Size(min = 1, max = 50, message = "更新者IDは1文字以上50文字以下で入力してください")
    private final String updatedBy;

    /**
     * 更新理由
     * 
     * <p>申請を更新する理由や背景。
     * 変更履歴の管理や監査のために記録されます。</p>
     */
    @Size(max = 500, message = "更新理由は500文字以下で入力してください")
    private final String updateReason;

    /**
     * 添付ファイルリスト
     * 
     * <p>申請に関連する添付ファイルの情報。
     * ファイル名、パス、サイズなどの情報を含みます。</p>
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>@Valid: リスト内の各要素に対してもバリデーションを実行</li>
     *   <li>Listインターface使用により実装の柔軟性を確保</li>
     * </ul>
     */
    @Valid
    private final List<AttachmentInfo> attachments;

    /**
     * 追加属性
     * 
     * <p>申請の種類に応じて動的に設定される追加情報。
     * 例：経費申請の場合の金額、休暇申請の場合の期間など</p>
     */
    private final Map<String, Object> additionalAttributes;

    /**
     * 申請の基本バリデーションを実行します。
     * 
     * <p>このメソッドは、アノテーションベースのバリデーションでは
     * 表現できない複雑なビジネスルールをチェックします。</p>
     * 
     * <h4>チェック項目：</h4>
     * <ul>
     *   <li>優先度の範囲チェック（1-5）</li>
     *   <li>希望処理日の妥当性チェック</li>
     *   <li>添付ファイルの制限チェック</li>
     * </ul>
     * 
     * @throws IllegalArgumentException バリデーションエラーが発生した場合
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>メソッドチェーンによる複数条件の検証</li>
     *   <li>早期リターンパターンの活用</li>
     *   <li>明確なエラーメッセージの提供</li>
     * </ul>
     */
    public void validate() {
        validatePriority();
        validateDesiredProcessingDate();
        validateAttachments();
        validateAdditionalAttributes();
    }

    /**
     * 優先度の妥当性をチェックします。
     * 
     * @throws IllegalArgumentException 優先度が1-5の範囲外の場合
     */
    private void validatePriority() {
        if (priority != null && (priority < 1 || priority > 5)) {
            throw new IllegalArgumentException(
                "優先度は1から5の範囲で指定してください。現在の値: " + priority
            );
        }
    }

    /**
     * 希望処理日の妥当性をチェックします。
     * 
     * @throws IllegalArgumentException 希望処理日が過去の日付の場合
     */
    private void validateDesiredProcessingDate() {
        if (desiredProcessingDate != null && desiredProcessingDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                "希望処理日は現在日時以降を指定してください。指定された日時: " + desiredProcessingDate
            );
        }
    }

    /**
     * 添付ファイルの制限をチェックします。
     * 
     * @throws IllegalArgumentException 添付ファイル数が制限を超える場合
     */
    private void validateAttachments() {
        if (attachments != null && attachments.size() > 10) {
            throw new IllegalArgumentException(
                "添付ファイルは10個まで登録可能です。現在の登録数: " + attachments.size()
            );
        }
    }

    /**
     * 追加属性の妥当性をチェックします。
     * 
     * @throws IllegalArgumentException 追加属性の数が制限を超える場合
     */
    private void validateAdditionalAttributes() {
        if (additionalAttributes != null && additionalAttributes.size() > 20) {
            throw new IllegalArgumentException(
                "追加属性は20個まで設定可能です。現在の設定数: " + additionalAttributes.size()
            );
        }
    }

    /**
     * 申請が緊急かどうかを判定します。
     * 
     * @return 優先度が4以上の場合true、それ以外はfalse
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>ビジネスロジックをCommandクラス内に実装</li>
     *   <li>nullセーフな実装（priority != null のチェック）</li>
     *   <li>明確な判定基準の文書化</li>
     * </ul>
     */
    public boolean isUrgent() {
        return priority != null && priority >= 4;
    }

    /**
     * 添付ファイルが存在するかどうかを判定します。
     * 
     * @return 添付ファイルが1つ以上存在する場合true、それ以外はfalse
     */
    public boolean hasAttachments() {
        return attachments != null && !attachments.isEmpty();
    }

    /**
     * 指定されたキーの追加属性を取得します。
     * 
     * @param key 取得したい属性のキー
     * @return 属性値（存在しない場合はnull）
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>nullセーフなMap操作</li>
     *   <li>ジェネリクスを使用した型安全性</li>
     * </ul>
     */
    public Object getAdditionalAttribute(String key) {
        return additionalAttributes != null ? additionalAttributes.get(key) : null;
    }

    /**
     * 添付ファイル情報を表現する内部クラス
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>static内部クラスの使用（外部クラスへの参照を持たない）</li>
     *   <li>関連性の高いクラスの内包</li>
     *   <li>カプセル化の実現</li>
     * </ul>
     */
    @Data
    @Builder
    public static final class AttachmentInfo {

        /**
         * ファイル名
         */
        @NotBlank(message = "ファイル名は必須です")
        @Size(min = 1, max = 255, message = "ファイル名は1文字以上255文字以下で入力してください")
        private final String fileName;

        /**
         * ファイルパス
         */
        @NotBlank(message = "ファイルパスは必須です")
        private final String filePath;

        /**
         * ファイルサイズ（バイト）
         */
        @NotNull(message = "ファイルサイズは必須です")
        private final Long fileSize;

        /**
         * MIMEタイプ
         */
        @NotBlank(message = "MIMEタイプは必須です")
        private final String mimeType;

        /**
         * ファイルサイズが制限内かどうかを判定します。
         * 
         * @return ファイルサイズが10MB以下の場合true、それ以外はfalse
         */
        public boolean isWithinSizeLimit() {
            final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
            return fileSize != null && fileSize <= MAX_FILE_SIZE;
        }
    }
}

/*
 * ========================================
 * 新人エンジニア向け学習ポイント
 * ========================================
 * 
 * 1. CQRSパターンの理解
 *    - Command: データ変更操作を表現
 *    - Query: データ取得操作を表現
 *    - 責任の分離により保守性向上
 * 
 * 2. 不変オブジェクト（Immutable Object）
 *    - finalフィールドによる不変性保証
 *    - スレッドセーフな設計
 *    - 予期しない状態変更の防止
 * 
 * 3. Lombokアノテーション
 *    - @Data: getter/setter/toString/equals/hashCodeを自動生成
 *    - @Builder: ビルダーパターンの実装を自動生成
 *    - コードの簡潔性と保守性の向上
 * 
 * 4. バリデーション戦略
 *    - アノテーションベース: 基本的な制約
 *    - メソッドベース: 複雑なビジネスルール
 *    - 多層防御によるデータ品質保証
 * 
 * 5. 設計原則
 *    - 単一責任原則: 申請更新のみに特化
 *    - 開放閉鎖原則: 拡張に開放、修正に閉鎖
 *    - 依存関係逆転原則: 抽象に依存、具象に依存しない
 * 
 * 6. エラーハンドリング
 *    - 明確なエラーメッセージ
 *    - 適切な例外の選択
 *    - 早期失敗（Fail Fast）の実践
 * 
 * 7. ドキュメンテーション
 *    - JavaDocによる詳細な説明
 *    - 使用例の提供
 *    - 設計意図の明確化
 */
