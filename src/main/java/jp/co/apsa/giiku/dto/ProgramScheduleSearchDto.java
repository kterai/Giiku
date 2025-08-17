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

    public ProgramScheduleSearchDto() {}

    public Long getProgramId() { return programId; }
    public void setProgramId(Long programId) { this.programId = programId; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public Long getInstructorId() { return instructorId; }
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }

    // ----- Alias setters used by controller -----
    public void setStartDateFrom(String startDateFrom) { this.startDateFrom = startDateFrom; }
    public void setStartDateTo(String startDateTo) { this.startDateTo = startDateTo; }
    public void setLocation(String location) { this.location = location; }
}