/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.unryu.infrastructure.notification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.apsa.unryu.domain.entity.Application;
import jp.co.apsa.unryu.domain.entity.User;
import jp.co.apsa.unryu.domain.port.NotificationPort;

/**
 * Slack通知サービス
 * 
 * <p>Slack APIを使用した通知機能を提供するインフラストラクチャサービスです。
 * Hexagonal ArchitectureのAdapterパターンを実装し、NotificationPortインターフェースを実装します。
 * Webhook APIを使用してSlackチャンネルやユーザーに通知を送信します。</p>
 * 
 * <p>このサービスは以下の機能を提供します：</p>
 * <ul>
 *   <li>Slack Webhook API連携</li>
 *   <li>テンプレートベースのメッセージ作成</li>
 *   <li>緊急通知の優先処理</li>
 *   <li>送信失敗時の自動リトライ</li>
 *   <li>通知ログの記録</li>
 *   <li>非同期通知処理</li>
 * </ul>
 * 
 * <p>設定値は外部プロパティファイルから取得し、環境に応じた柔軟な設定が可能です。</p>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
public class SlackNotificationService implements NotificationPort {

    private static final Logger logger = LoggerFactory.getLogger(SlackNotificationService.class);

    /**
     * Slack Webhook URL
     */
    @Value("${unryu.slack.webhook-url}")
    private String webhookUrl;

    /**
     * Slack チャンネル名（デフォルト）
     */
    @Value("${unryu.slack.default-channel:#general}")
    private String defaultChannel;

    /**
     * 緊急通知用チャンネル名
     */
    @Value("${unryu.slack.urgent-channel:#urgent}")
    private String urgentChannel;

    /**
     * 通知送信者名
     */
    @Value("${unryu.slack.bot.username:Unryu System}")
    private String botUsername;

    /**
     * 通知送信者アイコン
     */
    @Value("${unryu.slack.bot.icon::robot_face:}")
    private String botIcon;

    /**
     * リトライ最大回数
     */
    @Value("${unryu.slack.retry.max-attempts:3}")
    private int maxRetryAttempts;

    /**
     * リトライ間隔（ミリ秒）
     */
    @Value("${unryu.slack.retry.delay:1000}")
    private long retryDelay;

    /**
     * 通知有効フラグ
     */
    @Value("${unryu.slack.enabled:true}")
    private boolean notificationEnabled;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final DateTimeFormatter dateTimeFormatter;

    /**
     * コンストラクタ
     * 
     * @param restTemplate REST通信用テンプレート
     * @param objectMapper JSON変換用マッパー
     */
    public SlackNotificationService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 初期化処理
     */
    @PostConstruct
    public void initialize() {
        logger.info("SlackNotificationService を初期化しています");
        logger.info("通知有効フラグ: {}", notificationEnabled);
        logger.info("デフォルトチャンネル: {}", defaultChannel);
        logger.info("緊急通知チャンネル: {}", urgentChannel);

        if (!notificationEnabled) {
            logger.warn("Slack通知が無効化されています");
        }
    }

    /**
     * ユーザーに通知を送信します
     * 
     * @param userId ユーザーID
     * @param message メッセージ
     * @return 送信成功の場合true
     */
    @Override
    public boolean sendNotification(String userId, String message) {
        return sendNotification(userId, message, false);
    }

    /**
     * ユーザーに通知を送信します（緊急度指定可能）
     * 
     * @param userId ユーザーID
     * @param message メッセージ
     * @param isUrgent 緊急通知フラグ
     * @return 送信成功の場合true
     */
    public boolean sendNotification(String userId, String message, boolean isUrgent) {
        if (!notificationEnabled) {
            logger.debug("通知が無効化されているため、送信をスキップします");
            return true;
        }

        logger.info("Slack通知を送信します。ユーザーID: {}, 緊急: {}", userId, isUrgent);

        try {
            SlackMessage slackMessage = createSlackMessage(userId, message, isUrgent);
            return sendSlackMessage(slackMessage);

        } catch (Exception e) {
            logger.error("Slack通知の送信中にエラーが発生しました。ユーザーID: {}, エラー: {}", 
                        userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * 申請作成通知を送信します
     * 
     * @param application 申請
     * @param approver 承認者
     * @return 送信成功の場合true
     */
    public boolean sendApplicationCreatedNotification(Application application, User approver) {
        logger.info("申請作成通知を送信します。申請ID: {}, 承認者ID: {}", 
                   application.getId(), approver.getId());

        String message = createApplicationCreatedMessage(application, approver);
        return sendNotification(approver.getSlackUserId(), message, application.getIsUrgent());
    }

    /**
     * 承認完了通知を送信します
     * 
     * @param application 申請
     * @param applicant 申請者
     * @return 送信成功の場合true
     */
    public boolean sendApplicationApprovedNotification(Application application, User applicant) {
        logger.info("承認完了通知を送信します。申請ID: {}, 申請者ID: {}", 
                   application.getId(), applicant.getId());

        String message = createApplicationApprovedMessage(application, applicant);
        return sendNotification(applicant.getSlackUserId(), message, false);
    }

    /**
     * 申請却下通知を送信します
     * 
     * @param application 申請
     * @param applicant 申請者
     * @param reason 却下理由
     * @return 送信成功の場合true
     */
    public boolean sendApplicationRejectedNotification(Application application, User applicant, String reason) {
        logger.info("申請却下通知を送信します。申請ID: {}, 申請者ID: {}", 
                   application.getId(), applicant.getId());

        String message = createApplicationRejectedMessage(application, applicant, reason);
        return sendNotification(applicant.getSlackUserId(), message, true);
    }

    /**
     * 期限切れ通知を送信します
     * 
     * @param application 申請
     * @param approver 承認者
     * @return 送信成功の場合true
     */
    public boolean sendApplicationOverdueNotification(Application application, User approver) {
        logger.info("期限切れ通知を送信します。申請ID: {}, 承認者ID: {}", 
                   application.getId(), approver.getId());

        String message = createApplicationOverdueMessage(application, approver);
        return sendNotification(approver.getSlackUserId(), message, true);
    }

    /**
     * 非同期で通知を送信します
     * 
     * @param userId ユーザーID
     * @param message メッセージ
     * @param isUrgent 緊急通知フラグ
     * @return CompletableFuture<Boolean>
     */
    public CompletableFuture<Boolean> sendNotificationAsync(String userId, String message, boolean isUrgent) {
        return CompletableFuture.supplyAsync(() -> sendNotification(userId, message, isUrgent));
    }

    /**
     * Slackメッセージオブジェクトを作成します
     * 
     * @param userId ユーザーID
     * @param message メッセージ
     * @param isUrgent 緊急通知フラグ
     * @return Slackメッセージオブジェクト
     */
    private SlackMessage createSlackMessage(String userId, String message, boolean isUrgent) {
        SlackMessage slackMessage = new SlackMessage();
        slackMessage.setChannel(isUrgent ? urgentChannel : defaultChannel);
        slackMessage.setUsername(botUsername);
        slackMessage.setIconEmoji(botIcon);
        slackMessage.setText(formatMessage(userId, message, isUrgent));

        return slackMessage;
    }

    /**
     * メッセージをフォーマットします
     * 
     * @param userId ユーザーID
     * @param message メッセージ
     * @param isUrgent 緊急通知フラグ
     * @return フォーマット済みメッセージ
     */
    private String formatMessage(String userId, String message, boolean isUrgent) {
        StringBuilder formattedMessage = new StringBuilder();

        if (isUrgent) {
            formattedMessage.append("🚨 **緊急通知** 🚨\n\n");
        }

        formattedMessage.append("<@").append(userId).append("> さん\n\n");
        formattedMessage.append(message);
        formattedMessage.append("\n\n");
        formattedMessage.append("送信時刻: ").append(LocalDateTime.now().format(dateTimeFormatter));

        return formattedMessage.toString();
    }

    /**
     * 申請作成通知メッセージを作成します
     * 
     * @param application 申請
     * @param approver 承認者
     * @return 通知メッセージ
     */
    private String createApplicationCreatedMessage(Application application, User approver) {
        StringBuilder message = new StringBuilder();
        message.append("📋 **新しい申請が提出されました**\n\n");
        message.append("**申請ID:** ").append(application.getId()).append("\n");
        message.append("**タイトル:** ").append(application.getTitle()).append("\n");
        message.append("**申請者:** ").append(application.getApplicantId()).append("\n");
        message.append("**申請種別:** ").append(application.getApplicationType()).append("\n");
        message.append("**優先度:** ").append(application.getPriority()).append("\n");
        message.append("**申請日時:** ").append(application.getApplicationDate().format(dateTimeFormatter)).append("\n");

        if (application.getDueDate() != null) {
            message.append("**期限日:** ").append(application.getDueDate().format(dateTimeFormatter)).append("\n");
        }

        if (application.getAmount() != null) {
            message.append("**金額:** ").append(application.getAmount())
                   .append(" ").append(application.getCurrencyCode()).append("\n");
        }

        message.append("\n**申請内容:**\n");
        message.append("```\n").append(application.getContent()).append("\n```\n\n");
        message.append("承認をお願いします。");

        return message.toString();
    }

    /**
     * 承認完了通知メッセージを作成します
     * 
     * @param application 申請
     * @param applicant 申請者
     * @return 通知メッセージ
     */
    private String createApplicationApprovedMessage(Application application, User applicant) {
        StringBuilder message = new StringBuilder();
        message.append("✅ **申請が承認されました**\n\n");
        message.append("**申請ID:** ").append(application.getId()).append("\n");
        message.append("**タイトル:** ").append(application.getTitle()).append("\n");
        message.append("**承認日時:** ").append(LocalDateTime.now().format(dateTimeFormatter)).append("\n\n");
        message.append("申請が正常に承認されました。");

        return message.toString();
    }

    /**
     * 申請却下通知メッセージを作成します
     * 
     * @param application 申請
     * @param applicant 申請者
     * @param reason 却下理由
     * @return 通知メッセージ
     */
    private String createApplicationRejectedMessage(Application application, User applicant, String reason) {
        StringBuilder message = new StringBuilder();
        message.append("❌ **申請が却下されました**\n\n");
        message.append("**申請ID:** ").append(application.getId()).append("\n");
        message.append("**タイトル:** ").append(application.getTitle()).append("\n");
        message.append("**却下日時:** ").append(LocalDateTime.now().format(dateTimeFormatter)).append("\n");
        message.append("**却下理由:** ").append(reason).append("\n\n");
        message.append("必要に応じて申請内容を修正して再申請してください。");

        return message.toString();
    }

    /**
     * 期限切れ通知メッセージを作成します
     * 
     * @param application 申請
     * @param approver 承認者
     * @return 通知メッセージ
     */
    private String createApplicationOverdueMessage(Application application, User approver) {
        StringBuilder message = new StringBuilder();
        message.append("⏰ **申請の期限が切れています**\n\n");
        message.append("**申請ID:** ").append(application.getId()).append("\n");
        message.append("**タイトル:** ").append(application.getTitle()).append("\n");
        message.append("**期限日:** ").append(application.getDueDate().format(dateTimeFormatter)).append("\n");
        message.append("**申請者:** ").append(application.getApplicantId()).append("\n\n");
        message.append("至急、承認処理をお願いします。");

        return message.toString();
    }

    /**
     * Slackメッセージを送信します（リトライ機能付き）
     * 
     * @param slackMessage Slackメッセージ
     * @return 送信成功の場合true
     */
    @Retryable(value = {RestClientException.class}, 
               maxAttempts = 3, 
               backoff = @Backoff(delay = 1000, multiplier = 2))
    private boolean sendSlackMessage(SlackMessage slackMessage) {
        try {
            logger.debug("Slackメッセージを送信します: {}", slackMessage.getChannel());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String jsonPayload = objectMapper.writeValueAsString(slackMessage);
            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Slack通知の送信が成功しました");
                return true;
            } else {
                logger.warn("Slack通知の送信が失敗しました。ステータス: {}, レスポンス: {}", 
                           response.getStatusCode(), response.getBody());
                return false;
            }

        } catch (Exception e) {
            logger.error("Slack通知の送信中にエラーが発生しました: {}", e.getMessage(), e);
            throw new RestClientException("Slack通知送信エラー", e);
        }
    }

    /**
     * Slackメッセージクラス
     */
    public static class SlackMessage {
        private String channel;
        private String username;
        private String text;
        private String iconEmoji;

        // Getters and Setters
        public String getChannel() { return channel; }
        public void setChannel(String channel) { this.channel = channel; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }

        public String getIconEmoji() { return iconEmoji; }
        public void setIconEmoji(String iconEmoji) { this.iconEmoji = iconEmoji; }

        @Override
        public String toString() {
            return "SlackMessage{" +
                   "channel='" + channel + '\'' +
                   ", username='" + username + '\'' +
                   ", text='" + text + '\'' +
                   ", iconEmoji='" + iconEmoji + '\'' +
                   '}';
        }
    }

}
