package jp.co.apsa.giiku.dto;

import java.time.LocalDate;
/**
 * The StudentEnrollmentSearchDto class.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class StudentEnrollmentSearchDto {
    private Long studentId;
    private Long programId;
    private Long companyId;
    private String status;
    private LocalDate enrollmentDateFrom;
    private LocalDate enrollmentDateTo;
    private Boolean isPassed;
    /** StudentEnrollmentSearchDto メソッド */
    public StudentEnrollmentSearchDto() {}
    /** getStudentId メソッド */
    public Long getStudentId() { return studentId; }
    /** setStudentId メソッド */
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    /** getProgramId メソッド */
    public Long getProgramId() { return programId; }
    /** setProgramId メソッド */
    public void setProgramId(Long programId) { this.programId = programId; }
    /** getCompanyId メソッド */
    public Long getCompanyId() { return companyId; }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    /** getStatus メソッド */
    public String getStatus() { return status; }
    /** setStatus メソッド */
    public void setStatus(String status) { this.status = status; }
    /** getEnrollmentDateFrom メソッド */
    public LocalDate getEnrollmentDateFrom() { return enrollmentDateFrom; }
    /** setEnrollmentDateFrom メソッド */
    public void setEnrollmentDateFrom(LocalDate enrollmentDateFrom) { this.enrollmentDateFrom = enrollmentDateFrom; }
    /** getEnrollmentDateTo メソッド */
    public LocalDate getEnrollmentDateTo() { return enrollmentDateTo; }
    /** setEnrollmentDateTo メソッド */
    public void setEnrollmentDateTo(LocalDate enrollmentDateTo) { this.enrollmentDateTo = enrollmentDateTo; }
    /** getIsPassed メソッド */
    public Boolean getIsPassed() { return isPassed; }
    /** setIsPassed メソッド */
    public void setIsPassed(Boolean isPassed) { this.isPassed = isPassed; }
}
