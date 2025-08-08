package jp.co.apsa.giiku.domain.port;

import jp.co.apsa.giiku.domain.valueobject.LogLevel;

/**
 * Port for application logging.
 */
public interface AuditPort {

    /**
     * Write a log entry with the specified level.
     *
     * @param level   log level
     * @param message message body
     */
    void log(LogLevel level, String message);
}
