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
    /** StudentEnrollmentUpdateDto メソッド */
    public StudentEnrollmentUpdateDto() {}
    /** getStatus メソッド */
    public String getStatus() { return status; }
    /** setStatus メソッド */
    public void setStatus(String status) { this.status = status; }
    /** getStartDate メソッド */
    public LocalDate getStartDate() { return startDate; }
    /** setStartDate メソッド */
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    /** getCompletionDate メソッド */
    public LocalDate getCompletionDate() { return completionDate; }
    /** setCompletionDate メソッド */
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }
    /** getProgressRate メソッド */
    public BigDecimal getProgressRate() { return progressRate; }
    /** setProgressRate メソッド */
    public void setProgressRate(BigDecimal progressRate) { this.progressRate = progressRate; }
    /** getFinalScore メソッド */
    public BigDecimal getFinalScore() { return finalScore; }
    /** setFinalScore メソッド */
    public void setFinalScore(BigDecimal finalScore) { this.finalScore = finalScore; }
    /** getIsPassed メソッド */
    public Boolean getIsPassed() { return isPassed; }
    /** setIsPassed メソッド */
    public void setIsPassed(Boolean isPassed) { this.isPassed = isPassed; }
    /** getInstructorId メソッド */
    public Long getInstructorId() { return instructorId; }
    /** setInstructorId メソッド */
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
}
