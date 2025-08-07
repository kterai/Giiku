package jp.co.apsa.giiku.domain.port;

import java.time.LocalDateTime;
import java.util.Map;

import jp.co.apsa.giiku.domain.entity.ApplicationType;
import jp.co.apsa.giiku.domain.valueobject.Priority;

/**
 * AuditPort defines the contract for recording audit related events.
 *
 * <p>This interface is designed following the Hexagonal Architecture
 * so that application services can record audit information without
 * depending on a concrete infrastructure implementation.</p>
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public interface AuditPort {

    /**
     * Record the creation of an application.
     *
     * @param applicationId   ID of the created application
     * @param applicantId     ID of the user who created the application
     * @param title           application title
     * @param applicationType type of application
     * @param priority        priority of the application
     * @param isUrgent        whether the application is urgent
     * @param timestamp       timestamp of the creation event
     */
    void recordApplicationCreation(Long applicationId,
                                   Long applicantId,
                                   String title,
                                   ApplicationType applicationType,
                                   Priority priority,
                                   Boolean isUrgent,
                                   LocalDateTime timestamp);

    /**
     * Record a user action against a specific entity.
     *
     * @param userId     ID of the acting user
     * @param action     description of the action
     * @param entityType type of the target entity
     * @param entityId   ID of the target entity
     * @param details    additional details
     */
    void recordUserAction(String userId,
                          String action,
                          String entityType,
                          String entityId,
                          Map<String, Object> details);

    /**
     * Record an error event.
     *
     * @param errorType    classification of the error
     * @param userId       ID of the user if available
     * @param errorMessage message of the error
     */
    void recordError(String errorType, Long userId, String errorMessage);
}
