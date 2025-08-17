package jp.co.apsa.giiku.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * The StudentEnrollmentResponseDto class.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class StudentEnrollmentResponseDto {

    private Long id;
    private Long studentId;
    private String studentName;
    private Long programId;
    private String programName;
    private Long companyId;
    private String companyName;
    private String status;
    private LocalDate enrollmentDate;
    private LocalDate startDate;
    private LocalDate completionDate;
    private BigDecimal progressRate;
    private BigDecimal finalScore;
    private Boolean isPassed;
    private Long instructorId;
    private String instructorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    /** StudentEnrollmentResponseDto メソッド */
    public StudentEnrollmentResponseDto() {}
    /** getId メソッド */
    public Long getId() { return id; }
    /** setId メソッド */
    public void setId(Long id) { this.id = id; }
    /** getStudentId メソッド */
    public Long getStudentId() { return studentId; }
    /** setStudentId メソッド */
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    /** getStudentName メソッド */
    public String getStudentName() { return studentName; }
    /** setStudentName メソッド */
    public void setStudentName(String studentName) { this.studentName = studentName; }
    /** getProgramId メソッド */
    public Long getProgramId() { return programId; }
    /** setProgramId メソッド */
    public void setProgramId(Long programId) { this.programId = programId; }
    /** getProgramName メソッド */
    public String getProgramName() { return programName; }
    /** setProgramName メソッド */
    public void setProgramName(String programName) { this.programName = programName; }
    /** getCompanyId メソッド */
    public Long getCompanyId() { return companyId; }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    /** getCompanyName メソッド */
    public String getCompanyName() { return companyName; }
    /** setCompanyName メソッド */
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    /** getStatus メソッド */
    public String getStatus() { return status; }
    /** setStatus メソッド */
    public void setStatus(String status) { this.status = status; }
    /** getEnrollmentDate メソッド */
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    /** setEnrollmentDate メソッド */
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
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
    /** getInstructorName メソッド */
    public String getInstructorName() { return instructorName; }
    /** setInstructorName メソッド */
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
    /** getCreatedAt メソッド */
    public LocalDateTime getCreatedAt() { return createdAt; }
    /** setCreatedAt メソッド */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    /** getUpdatedAt メソッド */
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    /** setUpdatedAt メソッド */
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
