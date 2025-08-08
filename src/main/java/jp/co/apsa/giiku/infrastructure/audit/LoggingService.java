package jp.co.apsa.giiku.infrastructure.audit;

import jp.co.apsa.giiku.domain.port.AuditPort;
import jp.co.apsa.giiku.domain.valueobject.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



/**
 * Simple implementation of {@link AuditPort} that delegates to SLF4J.
 */
@Service
public class LoggingService implements AuditPort {

    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);

    @Override
    public void log(LogLevel level, String message) {
        switch (level.getLevel()) {
            case "ERROR":
                logger.error(message);
                break;
            case "WARN":
                logger.warn(message);
                break;
            case "INFO":
                logger.info(message);
                break;
            case "DEBUG":
                logger.debug(message);
                break;
            default:
                logger.trace(message);
                break;
        }
    }
}
