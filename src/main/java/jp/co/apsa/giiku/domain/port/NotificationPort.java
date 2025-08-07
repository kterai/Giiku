package jp.co.apsa.giiku.domain.port;

import jp.co.apsa.giiku.domain.entity.Application;
import jp.co.apsa.giiku.domain.entity.User;

import java.util.concurrent.CompletableFuture;

/**
 * 通知機能を抽象化するポートインターフェース。
 *
 * <p>アプリケーション層から通知手段を意識せず利用するための
 * 契約を定義します。Slack などの外部サービスとの連携は
 * このインターフェースの実装に委ねます。</p>
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public interface NotificationPort {

    /**
     * 指定ユーザーへ通知を送信します。
     *
     * @param userId  通知先ユーザーID
     * @param message メッセージ
     * @return 送信成功の場合は true
     */
    boolean sendNotification(String userId, String message);

    /**
     * 緊急度を指定して通知を送信します。
     *
     * @param userId   通知先ユーザーID
     * @param message  メッセージ
     * @param isUrgent 緊急通知フラグ
     * @return 送信成功の場合は true
     */
    boolean sendNotification(String userId, String message, boolean isUrgent);

    /**
     * 非同期で通知を送信します。
     *
     * @param userId   通知先ユーザーID
     * @param message  メッセージ
     * @param isUrgent 緊急通知フラグ
     * @return 送信結果を表す Future
     */
    CompletableFuture<Boolean> sendNotificationAsync(String userId, String message, boolean isUrgent);

    /**
     * 申請作成の通知を送信します。
     *
     * @param application 対象申請
     * @param approver    承認者
     * @return 送信成功の場合は true
     */
    boolean sendApplicationCreatedNotification(Application application, User approver);

    /**
     * 承認完了の通知を送信します。
     *
     * @param application 対象申請
     * @param applicant   申請者
     * @return 送信成功の場合は true
     */
    boolean sendApplicationApprovedNotification(Application application, User applicant);

    /**
     * 申請却下の通知を送信します。
     *
     * @param application 対象申請
     * @param applicant   申請者
     * @param reason      却下理由
     * @return 送信成功の場合は true
     */
    boolean sendApplicationRejectedNotification(Application application, User applicant, String reason);

    /**
     * 申請期限切れの通知を送信します。
     *
     * @param application 対象申請
     * @param approver    承認者
     * @return 送信成功の場合は true
     */
    boolean sendApplicationOverdueNotification(Application application, User approver);
}
