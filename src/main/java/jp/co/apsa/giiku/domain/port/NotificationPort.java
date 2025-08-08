package jp.co.apsa.giiku.domain.port;

import java.util.concurrent.CompletableFuture;

/**
 * Notification port abstraction.
 *
 * <p>Allows the application layer to send notifications without
 * depending on concrete infrastructure such as Slack.</p>
 */
public interface NotificationPort {

    /**
     * Send a normal notification message.
     *
     * @param userId  Slack user identifier
     * @param message message body
     * @return {@code true} when the message was accepted
     */
    boolean sendNotification(String userId, String message);

    /**
     * Send a notification message with urgency.
     *
     * @param userId  Slack user identifier
     * @param message message body
     * @param isUrgent {@code true} to use the urgent channel
     * @return {@code true} when the message was accepted
     */
    boolean sendNotification(String userId, String message, boolean isUrgent);

    /**
     * Asynchronously send a notification message.
     *
     * @param userId  Slack user identifier
     * @param message message body
     * @param isUrgent {@code true} to use the urgent channel
     * @return future returning the result of {@link #sendNotification(String, String, boolean)}
     */
    CompletableFuture<Boolean> sendNotificationAsync(String userId, String message, boolean isUrgent);
}
