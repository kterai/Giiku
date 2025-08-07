package jp.co.apsa.unryu.infrastructure.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for marking methods whose execution should be recorded by the audit log.
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
    /**
     * Logical event type to record in the audit log.
     */
    String eventType() default "";
}
