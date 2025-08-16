package jp.co.apsa.giiku.dto;

import java.time.LocalDate;

public class StudentEnrollmentSearchDto {
    private Long studentId;
    private Long programId;
    private Long companyId;
    private String status;
    private LocalDate enrollmentDateFrom;
    private LocalDate enrollmentDateTo;
    private Boolean isPassed;

    public StudentEnrollmentSearchDto() {}

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getProgramId() { return programId; }
    public void setProgramId(Long programId) { this.programId = programId; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getEnrollmentDateFrom() { return enrollmentDateFrom; }
    public void setEnrollmentDateFrom(LocalDate enrollmentDateFrom) { this.enrollmentDateFrom = enrollmentDateFrom; }

    public LocalDate getEnrollmentDateTo() { return enrollmentDateTo; }
    public void setEnrollmentDateTo(LocalDate enrollmentDateTo) { this.enrollmentDateTo = enrollmentDateTo; }

    public Boolean getIsPassed() { return isPassed; }
    public void setIsPassed(Boolean isPassed) { this.isPassed = isPassed; }
}