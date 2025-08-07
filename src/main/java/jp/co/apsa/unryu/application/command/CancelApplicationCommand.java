package jp.co.apsa.unryu.application.command;

import java.time.LocalDateTime;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

/**
 * 申請キャンセル用のCommandクラス
 * 
 * <p>このクラスは、CQRS（Command Query Responsibility Segregation）パターンにおける
 * Commandオブジェクトとして設計されています。申請のキャンセル処理に必要な情報を
 * 不変オブジェクト（Immutable Object）として保持します。</p>
 * 
 * <h3>CQRSパターンにおけるキャンセル操作の特徴</h3>
 * <ul>
 *   <li>冪等性（Idempotency）: 同じキャンセル操作を複数回実行しても結果が同じ</li>
 *   <li>監査証跡: キャンセル理由と実行者の記録による透明性確保</li>
 *   <li>状態遷移: 申請状態の適切な管理と整合性保証</li>
 * </ul>
 * 
 * <h3>設計原則</h3>
 * <ul>
 *   <li>単一責任原則: 申請キャンセルのみに特化した設計</li>
 *   <li>不変性（Immutability）: 一度作成されたオブジェクトは変更不可</li>
 *   <li>バリデーション: 不正なキャンセル操作の防止</li>
 *   <li>監査対応: キャンセル操作の完全な追跡可能性</li>
 * </ul>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 * 
 * @see jp.co.apsa.unryu.application.service.ApplicationService
 * @see jp.co.apsa.unryu.domain.entity.Application
 * @see jp.co.apsa.unryu.domain.valueobject.ApplicationStatus
 */
@Data
@Builder
public final class CancelApplicationCommand {

    /**
     * 申請ID
     * 
     * <p>キャンセル対象となる申請を一意に識別するためのID。
     * 必須項目であり、nullや空文字は許可されません。</p>
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>@NotBlank: null、空文字、空白文字のみの文字列を禁止</li>
     *   <li>@Size: 文字列の長さを制限（1-50文字）</li>
     *   <li>final修飾子: 不変性を保証</li>
     *   <li>一意性: システム内で申請を特定する重要な識別子</li>
     * </ul>
     */
    @NotBlank(message = "申請IDは必須です")
    @Size(min = 1, max = 50, message = "申請IDは1文字以上50文字以下で入力してください")
    private final String applicationId;

    /**
     * キャンセル理由
     * 
     * <p>申請をキャンセルする理由や背景。
     * 監査ログや後続の分析のために必須項目として設定されています。</p>
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>必須項目: キャンセル操作の透明性確保のため</li>
     *   <li>文字数制限: 適切な理由記述を促進</li>
     *   <li>監査対応: 後からキャンセル理由を追跡可能</li>
     * </ul>
     */
    @NotBlank(message = "キャンセル理由は必須です")
    @Size(min = 10, max = 1000, message = "キャンセル理由は10文字以上1000文字以下で入力してください")
    private final String cancelReason;

    /**
     * キャンセル実行者ID
     * 
     * <p>申請をキャンセルするユーザーのID。
     * セキュリティ管理と監査ログのために必要です。</p>
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>責任の明確化: 誰がキャンセルしたかを記録</li>
     *   <li>権限チェック: 適切な権限を持つユーザーかの検証</li>
     *   <li>監査証跡: セキュリティインシデント調査に活用</li>
     * </ul>
     */
    @NotBlank(message = "キャンセル実行者IDは必須です")
    @Size(min = 1, max = 50, message = "キャンセル実行者IDは1文字以上50文字以下で入力してください")
    private final String cancelledBy;

    /**
     * キャンセル種別
     * 
     * <p>キャンセルの種類を分類するための項目。
     * 例：申請者都合、システム都合、承認者判断など</p>
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>分類管理: キャンセル理由の体系的な管理</li>
     *   <li>統計分析: キャンセル傾向の分析に活用</li>
     *   <li>プロセス改善: 頻繁なキャンセル理由の特定</li>
     * </ul>
     */
    @NotBlank(message = "キャンセル種別は必須です")
    @Size(min = 1, max = 30, message = "キャンセル種別は1文字以上30文字以下で入力してください")
    private final String cancelType;

    /**
     * 緊急キャンセルフラグ
     * 
     * <p>緊急でキャンセルが必要かどうかを示すフラグ。
     * 通常の承認プロセスをスキップする場合に使用されます。</p>
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>プロセス制御: 緊急時の特別な処理フロー</li>
     *   <li>権限管理: 緊急キャンセルの実行権限</li>
     *   <li>監査強化: 緊急キャンセルの厳格な記録</li>
     * </ul>
     */
    @NotNull(message = "緊急キャンセルフラグは必須です")
    private final Boolean isUrgentCancel;

    /**
     * 承認者への通知要否
     * 
     * <p>キャンセル時に承認者への通知が必要かどうかを示すフラグ。
     * 承認プロセス中の申請の場合は通常true。</p>
     */
    @NotNull(message = "承認者通知フラグは必須です")
    private final Boolean notifyApprovers;

    /**
     * 関連申請ID
     * 
     * <p>このキャンセルに関連する他の申請のID。
     * 連続する申請や関連申請がある場合に使用されます。</p>
     */
    @Size(max = 50, message = "関連申請IDは50文字以下で入力してください")
    private final String relatedApplicationId;

    /**
     * キャンセル予定日時
     * 
     * <p>キャンセル処理を実行する予定の日時。
     * 即座にキャンセルしない場合の予約キャンセルに使用されます。</p>
     */
    private final LocalDateTime scheduledCancelDateTime;

    /**
     * 追加属性
     * 
     * <p>キャンセル処理に関する追加情報。
     * システム固有の情報や拡張データを格納します。</p>
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>拡張性: 将来的な機能追加への対応</li>
     *   <li>柔軟性: 様々なキャンセルシナリオに対応</li>
     *   <li>Map使用: キー・バリュー形式での柔軟なデータ格納</li>
     * </ul>
     */
    private final Map<String, Object> additionalAttributes;

    /**
     * キャンセルコマンドの基本バリデーションを実行します。
     * 
     * <p>このメソッドは、アノテーションベースのバリデーションでは
     * 表現できない複雑なビジネスルールをチェックします。</p>
     * 
     * <h4>チェック項目：</h4>
     * <ul>
     *   <li>キャンセル理由の妥当性チェック</li>
     *   <li>キャンセル種別の有効性チェック</li>
     *   <li>緊急キャンセルの条件チェック</li>
     *   <li>予定日時の妥当性チェック</li>
     * </ul>
     * 
     * @throws IllegalArgumentException バリデーションエラーが発生した場合
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>複合バリデーション: 複数フィールドの関連性チェック</li>
     *   <li>ビジネスルール: ドメイン固有の制約の実装</li>
     *   <li>早期失敗: 問題を早期に検出して処理を停止</li>
     * </ul>
     */
    public void validate() {
        validateCancelReason();
        validateCancelType();
        validateUrgentCancelConditions();
        validateScheduledDateTime();
        validateAdditionalAttributes();
    }

    /**
     * キャンセル理由の妥当性をチェックします。
     * 
     * @throws IllegalArgumentException キャンセル理由が不適切な場合
     */
    private void validateCancelReason() {
        if (cancelReason != null) {
            // 禁止キーワードのチェック
            String[] forbiddenWords = {"テスト", "test", "適当", "なんとなく"};
            String lowerCaseReason = cancelReason.toLowerCase();

            for (String forbidden : forbiddenWords) {
                if (lowerCaseReason.contains(forbidden.toLowerCase())) {
                    throw new IllegalArgumentException(
                        "キャンセル理由に不適切な表現が含まれています: " + forbidden
                    );
                }
            }

            // 最小限の意味のある内容かチェック
            if (cancelReason.trim().length() < 10) {
                throw new IllegalArgumentException(
                    "キャンセル理由は具体的な内容を10文字以上で記述してください"
                );
            }
        }
    }

    /**
     * キャンセル種別の有効性をチェックします。
     * 
     * @throws IllegalArgumentException キャンセル種別が無効な場合
     */
    private void validateCancelType() {
        if (cancelType != null) {
            String[] validTypes = {
                "申請者都合", "システム都合", "承認者判断", "期限切れ", 
                "重複申請", "要件変更", "緊急事態", "その他"
            };

            boolean isValid = false;
            for (String validType : validTypes) {
                if (validType.equals(cancelType)) {
                    isValid = true;
                    break;
                }
            }

            if (!isValid) {
                throw new IllegalArgumentException(
                    "無効なキャンセル種別です。有効な値: " + String.join(", ", validTypes)
                );
            }
        }
    }

    /**
     * 緊急キャンセルの条件をチェックします。
     * 
     * @throws IllegalArgumentException 緊急キャンセルの条件が不適切な場合
     */
    private void validateUrgentCancelConditions() {
        if (Boolean.TRUE.equals(isUrgentCancel)) {
            // 緊急キャンセルの場合、特定の種別のみ許可
            String[] urgentValidTypes = {"緊急事態", "システム都合", "承認者判断"};
            boolean isUrgentTypeValid = false;

            for (String urgentType : urgentValidTypes) {
                if (urgentType.equals(cancelType)) {
                    isUrgentTypeValid = true;
                    break;
                }
            }

            if (!isUrgentTypeValid) {
                throw new IllegalArgumentException(
                    "緊急キャンセルは特定の種別でのみ実行可能です: " + 
                    String.join(", ", urgentValidTypes)
                );
            }

            // 緊急キャンセルの場合、理由の最小文字数を増加
            if (cancelReason != null && cancelReason.trim().length() < 20) {
                throw new IllegalArgumentException(
                    "緊急キャンセルの場合、理由は20文字以上で詳細に記述してください"
                );
            }
        }
    }

    /**
     * 予定日時の妥当性をチェックします。
     * 
     * @throws IllegalArgumentException 予定日時が不適切な場合
     */
    private void validateScheduledDateTime() {
        if (scheduledCancelDateTime != null) {
            LocalDateTime now = LocalDateTime.now();

            // 過去の日時は指定不可
            if (scheduledCancelDateTime.isBefore(now)) {
                throw new IllegalArgumentException(
                    "キャンセル予定日時は現在日時以降を指定してください。指定された日時: " + 
                    scheduledCancelDateTime
                );
            }

            // 緊急キャンセルの場合、予定日時は設定不可
            if (Boolean.TRUE.equals(isUrgentCancel)) {
                throw new IllegalArgumentException(
                    "緊急キャンセルの場合、予定日時は設定できません。即座に実行されます。"
                );
            }

            // 予定日時が1年以上先は不可
            LocalDateTime oneYearLater = now.plusYears(1);
            if (scheduledCancelDateTime.isAfter(oneYearLater)) {
                throw new IllegalArgumentException(
                    "キャンセル予定日時は1年以内で指定してください"
                );
            }
        }
    }

    /**
     * 追加属性の妥当性をチェックします。
     * 
     * @throws IllegalArgumentException 追加属性が制限を超える場合
     */
    private void validateAdditionalAttributes() {
        if (additionalAttributes != null && additionalAttributes.size() > 15) {
            throw new IllegalArgumentException(
                "追加属性は15個まで設定可能です。現在の設定数: " + additionalAttributes.size()
            );
        }
    }

    /**
     * 緊急キャンセルかどうかを判定します。
     * 
     * @return 緊急キャンセルの場合true、それ以外はfalse
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>Boolean型のnullセーフな判定</li>
     *   <li>ビジネスロジックの明確な表現</li>
     *   <li>メソッド名による意図の明確化</li>
     * </ul>
     */
    public boolean isUrgentCancel() {
        return Boolean.TRUE.equals(isUrgentCancel);
    }

    /**
     * 予約キャンセルかどうかを判定します。
     * 
     * @return 予約キャンセルの場合true、それ以外はfalse
     */
    public boolean isScheduledCancel() {
        return scheduledCancelDateTime != null && !isUrgentCancel();
    }

    /**
     * 承認者への通知が必要かどうかを判定します。
     * 
     * @return 通知が必要な場合true、それ以外はfalse
     */
    public boolean shouldNotifyApprovers() {
        return Boolean.TRUE.equals(notifyApprovers);
    }

    /**
     * 関連申請が存在するかどうかを判定します。
     * 
     * @return 関連申請が存在する場合true、それ以外はfalse
     */
    public boolean hasRelatedApplication() {
        return relatedApplicationId != null && !relatedApplicationId.trim().isEmpty();
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
     *   <li>防御的プログラミング</li>
     * </ul>
     */
    public Object getAdditionalAttribute(String key) {
        return additionalAttributes != null ? additionalAttributes.get(key) : null;
    }

    /**
     * キャンセル処理の優先度を計算します。
     * 
     * @return 優先度（1-10、10が最高優先度）
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>複合条件による優先度計算</li>
     *   <li>ビジネスルールの数値化</li>
     *   <li>処理順序の決定ロジック</li>
     * </ul>
     */
    public int calculateCancelPriority() {
        int priority = 5; // 基本優先度

        // 緊急キャンセルの場合は最高優先度
        if (isUrgentCancel()) {
            priority = 10;
        }

        // キャンセル種別による調整
        if ("緊急事態".equals(cancelType)) {
            priority = Math.max(priority, 9);
        } else if ("システム都合".equals(cancelType)) {
            priority = Math.max(priority, 7);
        } else if ("承認者判断".equals(cancelType)) {
            priority = Math.max(priority, 6);
        }

        // 予約キャンセルの場合は優先度を下げる
        if (isScheduledCancel()) {
            priority = Math.max(priority - 2, 1);
        }

        return priority;
    }

    /**
     * キャンセル処理のサマリー情報を生成します。
     * 
     * @return キャンセル処理のサマリー文字列
     * 
     * <h4>学習ポイント：</h4>
     * <ul>
     *   <li>StringBuilder使用による効率的な文字列構築</li>
     *   <li>条件分岐による動的な情報生成</li>
     *   <li>ログ出力やUI表示用の情報整理</li>
     * </ul>
     */
    public String generateCancelSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("申請キャンセル - ID: ").append(applicationId);
        summary.append(", 種別: ").append(cancelType);
        summary.append(", 実行者: ").append(cancelledBy);

        if (isUrgentCancel()) {
            summary.append(" [緊急]");
        }

        if (isScheduledCancel()) {
            summary.append(" [予約: ").append(scheduledCancelDateTime).append("]");
        }

        if (hasRelatedApplication()) {
            summary.append(" [関連: ").append(relatedApplicationId).append("]");
        }

        summary.append(" - 優先度: ").append(calculateCancelPriority());

        return summary.toString();
    }
}

/*
 * ========================================
 * 新人エンジニア向け学習ポイント
 * ========================================
 * 
 * 1. CQRSパターンにおけるキャンセル操作
 *    - 冪等性: 同じ操作を複数回実行しても結果が同じ
 *    - 監査証跡: すべてのキャンセル操作を記録
 *    - 状態管理: 申請状態の適切な遷移
 * 
 * 2. 不変オブジェクト（Immutable Object）の利点
 *    - スレッドセーフ: 並行処理での安全性
 *    - 予測可能性: オブジェクトの状態が変わらない
 *    - デバッグ容易性: 状態変更による副作用がない
 * 
 * 3. バリデーション戦略の階層化
 *    - アノテーションレベル: 基本的な制約（必須、文字数等）
 *    - メソッドレベル: 複雑なビジネスルール
 *    - 複合バリデーション: 複数フィールドの関連性チェック
 * 
 * 4. Boolean型の適切な扱い
 *    - Boolean.TRUE.equals()パターン: nullセーフな判定
 *    - プリミティブ型との違い: nullの扱い
 *    - 三値論理: true/false/nullの考慮
 * 
 * 5. エラーハンドリングのベストプラクティス
 *    - 明確なエラーメッセージ: 問題の特定と解決策の提示
 *    - 早期失敗: 問題を早期に検出
 *    - 例外の適切な選択: RuntimeExceptionの使い分け
 * 
 * 6. ビジネスロジックの実装
 *    - 優先度計算: 複数条件による数値化
 *    - 状態判定: 明確な判定メソッド
 *    - サマリー生成: 情報の整理と表示
 * 
 * 7. 拡張性を考慮した設計
 *    - 追加属性: 将来的な機能追加への対応
 *    - 種別管理: 新しいキャンセル種別の追加容易性
 *    - インターフェース設計: 他のCommandとの一貫性
 * 
 * 8. セキュリティ考慮事項
 *    - 実行者の記録: 責任の明確化
 *    - 権限チェック: 適切な権限を持つユーザーのみ実行可能
 *    - 監査ログ: セキュリティインシデント対応
 * 
 * 9. パフォーマンス考慮事項
 *    - StringBuilder使用: 効率的な文字列操作
 *    - 早期リターン: 不要な処理の回避
 *    - メモリ効率: 不要なオブジェクト生成の抑制
 * 
 * 10. テスタビリティ
 *     - 純粋関数: 副作用のないメソッド設計
 *     - 境界値テスト: バリデーションロジックの検証
 *     - モックフレンドリー: 依存関係の注入容易性
 */
