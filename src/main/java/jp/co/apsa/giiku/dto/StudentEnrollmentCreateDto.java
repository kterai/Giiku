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
    /**
     * StudentEnrollmentCreateDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public StudentEnrollmentCreateDto() {}
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
