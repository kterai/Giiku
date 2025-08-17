package jp.co.apsa.giiku.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
/**
 * The StudentEnrollmentUpdateDto class.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */

public class StudentEnrollmentUpdateDto {

    private String status;
    private LocalDate startDate;
    private LocalDate completionDate;
    private BigDecimal progressRate;
    private BigDecimal finalScore;
    private Boolean isPassed;
    private Long instructorId;
    /**
     * StudentEnrollmentUpdateDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public StudentEnrollmentUpdateDto() {}
    /**
     * getStatus メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getStatus() { return status; }
    /**
     * setStatus メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStatus(String status) { this.status = status; }
    /**
     * getStartDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDate getStartDate() { return startDate; }
    /**
     * setStartDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    /**
     * getCompletionDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDate getCompletionDate() { return completionDate; }
    /**
     * setCompletionDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }
    /**
     * getProgressRate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getProgressRate() { return progressRate; }
    /**
     * setProgressRate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setProgressRate(BigDecimal progressRate) { this.progressRate = progressRate; }
    /**
     * getFinalScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getFinalScore() { return finalScore; }
    /**
     * setFinalScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setFinalScore(BigDecimal finalScore) { this.finalScore = finalScore; }
    /**
     * getIsPassed メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Boolean getIsPassed() { return isPassed; }
    /**
     * setIsPassed メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setIsPassed(Boolean isPassed) { this.isPassed = isPassed; }
    /**
     * getInstructorId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getInstructorId() { return instructorId; }
    /**
     * setInstructorId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
}
