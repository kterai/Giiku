package jp.co.apsa.giiku.dto;

import java.time.LocalDateTime;

/**
 * プログラムスケジュール検索DTO
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class ProgramScheduleSearchDto {
    private Long programId;
    private Long companyId;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long instructorId;
    private String startDateFrom;
    private String startDateTo;
    private String location;
    /**
     * ProgramScheduleSearchDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public ProgramScheduleSearchDto() {}
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
     * getStartDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getStartDate() { return startDate; }
    /**
     * setStartDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    /**
     * getEndDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getEndDate() { return endDate; }
    /**
     * setEndDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
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

    // ----- Alias setters used by controller -----
    /**
     * setStartDateFrom メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStartDateFrom(String startDateFrom) { this.startDateFrom = startDateFrom; }
    /**
     * setStartDateTo メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStartDateTo(String startDateTo) { this.startDateTo = startDateTo; }
    /**
     * setLocation メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setLocation(String location) { this.location = location; }
}
