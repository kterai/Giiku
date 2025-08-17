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
    /**
     * StudentEnrollmentSearchDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public StudentEnrollmentSearchDto() {}
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
     * getEnrollmentDateFrom メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDate getEnrollmentDateFrom() { return enrollmentDateFrom; }
    /**
     * setEnrollmentDateFrom メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setEnrollmentDateFrom(LocalDate enrollmentDateFrom) { this.enrollmentDateFrom = enrollmentDateFrom; }
    /**
     * getEnrollmentDateTo メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDate getEnrollmentDateTo() { return enrollmentDateTo; }
    /**
     * setEnrollmentDateTo メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setEnrollmentDateTo(LocalDate enrollmentDateTo) { this.enrollmentDateTo = enrollmentDateTo; }
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
}
