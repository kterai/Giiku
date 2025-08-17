package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
/**
 * The StudentEnrollmentCreateDto class.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
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
    /** StudentEnrollmentCreateDto メソッド */
    public StudentEnrollmentCreateDto() {}
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
    /** getEnrollmentDate メソッド */
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    /** setEnrollmentDate メソッド */
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    /** getStartDate メソッド */
    public LocalDate getStartDate() { return startDate; }
    /** setStartDate メソッド */
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    /** getInstructorId メソッド */
    public Long getInstructorId() { return instructorId; }
    /** setInstructorId メソッド */
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
}
