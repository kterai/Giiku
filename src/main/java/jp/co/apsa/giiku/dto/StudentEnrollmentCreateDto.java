package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class StudentEnrollmentCreateDto {

    @NotNull
    private Long studentId;

    @NotNull
    private Long programId;

    @NotNull
    private Long companyId;

    private LocalDate enrollmentDate;

    private LocalDate startDate;

    private Long instructorId;

    public StudentEnrollmentCreateDto() {}

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getProgramId() { return programId; }
    public void setProgramId(Long programId) { this.programId = programId; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public Long getInstructorId() { return instructorId; }
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
}