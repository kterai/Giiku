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
    /**
     * StudentEnrollmentResponseDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public StudentEnrollmentResponseDto() {}
    /**
     * getId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getId() { return id; }
    /**
     * setId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setId(Long id) { this.id = id; }
    /**
     * getStudentId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getStudentId() { return studentId; }
    /**
     * setStudentId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    /**
     * getStudentName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getStudentName() { return studentName; }
    /**
     * setStudentName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStudentName(String studentName) { this.studentName = studentName; }
    /**
     * getProgramId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getProgramId() { return programId; }
    /**
     * setProgramId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setProgramId(Long programId) { this.programId = programId; }
    /**
     * getProgramName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getProgramName() { return programName; }
    /**
     * setProgramName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setProgramName(String programName) { this.programName = programName; }
    /**
     * getCompanyId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getCompanyId() { return companyId; }
    /**
     * setCompanyId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    /**
     * getCompanyName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getCompanyName() { return companyName; }
    /**
     * setCompanyName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCompanyName(String companyName) { this.companyName = companyName; }
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
     * getEnrollmentDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    /**
     * setEnrollmentDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
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
    /**
     * getInstructorName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getInstructorName() { return instructorName; }
    /**
     * setInstructorName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
    /**
     * getCreatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getCreatedAt() { return createdAt; }
    /**
     * setCreatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    /**
     * getUpdatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    /**
     * setUpdatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
