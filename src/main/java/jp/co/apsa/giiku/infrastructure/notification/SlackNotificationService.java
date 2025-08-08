package jp.co.apsa.giiku.infrastructure.notification;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.apsa.giiku.domain.port.NotificationPort;

/**
 * Slack Webhook を利用した通知サービスの簡易実装。
 */
@Service
public class SlackNotificationService implements NotificationPort {

    private static final Logger logger = LoggerFactory.getLogger(SlackNotificationService.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${slack.webhook-url:}")
    private String webhookUrl;

    @Value("${slack.default-channel:#general}")
    private String defaultChannel;

    @Value("${slack.urgent-channel:#general}")
    private String urgentChannel;

    @Value("${slack.bot-username:Giiku}")
    private String botUsername;

    @Value("${slack.bot-icon::robot:}")
    private String botIcon;

    @Value("${slack.enabled:true}")
    private boolean notificationEnabled;

    public SlackNotificationService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean sendNotification(String userId, String message) {
        return sendNotification(userId, message, false);
    }

    @Override
    public boolean sendNotification(String userId, String message, boolean isUrgent) {
        if (!notificationEnabled || webhookUrl == null || webhookUrl.isEmpty()) {
            logger.warn("Slack notification disabled or webhook URL missing");
            return false;
        }
        try {
            String channel = isUrgent ? urgentChannel : defaultChannel;
            SlackMessage payload = new SlackMessage(channel, botUsername, botIcon,
                    (isUrgent ? "\uD83D\uDEA8 **緊急通知**\n" : "") + "<@" + userId + "> " + message);
            String body = objectMapper.writeValueAsString(payload);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, request, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (RestClientException | java.io.IOException e) {
            logger.error("Failed to send Slack notification", e);
            return false;
        }
    }

    @Override
    public CompletableFuture<Boolean> sendNotificationAsync(String userId, String message, boolean isUrgent) {
        return CompletableFuture.supplyAsync(() -> sendNotification(userId, message, isUrgent));
    }

    /**
     * Slack Webhook メッセージを表す内部クラス。
     */
    public static class SlackMessage {
        private String channel;
        private String username;
        private String icon_emoji;
        private String text;

        public SlackMessage() {
        }

        public SlackMessage(String channel, String username, String iconEmoji, String text) {
            this.channel = channel;
            this.username = username;
            this.icon_emoji = iconEmoji;
            this.text = text;
        }

        public String getChannel() {
            return channel;
        }

        public String getUsername() {
            return username;
        }

        public String getIcon_emoji() {
            return icon_emoji;
        }

        public String getText() {
            return text;
        }
    }
}
