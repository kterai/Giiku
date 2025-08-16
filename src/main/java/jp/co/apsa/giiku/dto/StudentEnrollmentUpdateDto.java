package jp.co.apsa.giiku.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class StudentEnrollmentUpdateDto {

    private String status;
    private LocalDate startDate;
    private LocalDate completionDate;
    private BigDecimal progressRate;
    private BigDecimal finalScore;
    private Boolean isPassed;
    private Long instructorId;

    public StudentEnrollmentUpdateDto() {}

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }

    public BigDecimal getProgressRate() { return progressRate; }
    public void setProgressRate(BigDecimal progressRate) { this.progressRate = progressRate; }

    public BigDecimal getFinalScore() { return finalScore; }
    public void setFinalScore(BigDecimal finalScore) { this.finalScore = finalScore; }

    public Boolean getIsPassed() { return isPassed; }
    public void setIsPassed(Boolean isPassed) { this.isPassed = isPassed; }

    public Long getInstructorId() { return instructorId; }
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
}