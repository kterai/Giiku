package jp.co.apsa.giiku.dto;

import java.time.LocalDateTime;

/**
 * プログラムスケジュール検索DTO
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
    /** ProgramScheduleSearchDto メソッド */
    public ProgramScheduleSearchDto() {}
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
    /** getStartDate メソッド */
    public LocalDateTime getStartDate() { return startDate; }
    /** setStartDate メソッド */
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    /** getEndDate メソッド */
    public LocalDateTime getEndDate() { return endDate; }
    /** setEndDate メソッド */
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    /** getInstructorId メソッド */
    public Long getInstructorId() { return instructorId; }
    /** setInstructorId メソッド */
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }

    // ----- Alias setters used by controller -----
    /** setStartDateFrom メソッド */
    public void setStartDateFrom(String startDateFrom) { this.startDateFrom = startDateFrom; }
    /** setStartDateTo メソッド */
    public void setStartDateTo(String startDateTo) { this.startDateTo = startDateTo; }
    /** setLocation メソッド */
    public void setLocation(String location) { this.location = location; }
}
