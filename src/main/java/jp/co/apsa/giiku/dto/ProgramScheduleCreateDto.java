package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * プログラムスケジュール作成DTO
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class ProgramScheduleCreateDto {

    @NotNull
    private Long programId;

    @NotNull
    private Long companyId;

    @NotBlank
    @Size(max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    @Min(1)
    private Integer capacity;

    private Long instructorId;

    @Size(max = 200)
    private String location;

    // Constructors, getters, setters
    /** ProgramScheduleCreateDto メソッド */
    public ProgramScheduleCreateDto() {}
    /** getProgramId メソッド */
    public Long getProgramId() { return programId; }
    /** setProgramId メソッド */
    public void setProgramId(Long programId) { this.programId = programId; }
    /** getCompanyId メソッド */
    public Long getCompanyId() { return companyId; }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    /** getTitle メソッド */
    public String getTitle() { return title; }
    /** setTitle メソッド */
    public void setTitle(String title) { this.title = title; }
    /** getDescription メソッド */
    public String getDescription() { return description; }
    /** setDescription メソッド */
    public void setDescription(String description) { this.description = description; }
    /** getStartDateTime メソッド */
    public LocalDateTime getStartDateTime() { return startDateTime; }
    /** setStartDateTime メソッド */
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }
    /** getEndDateTime メソッド */
    public LocalDateTime getEndDateTime() { return endDateTime; }
    /** setEndDateTime メソッド */
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }
    /** getCapacity メソッド */
    public Integer getCapacity() { return capacity; }
    /** setCapacity メソッド */
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    /** getInstructorId メソッド */
    public Long getInstructorId() { return instructorId; }
    /** setInstructorId メソッド */
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
    /** getLocation メソッド */
    public String getLocation() { return location; }
    /** setLocation メソッド */
    public void setLocation(String location) { this.location = location; }

    // ---- Alias getters for controller compatibility ----
    /** getStartDate メソッド */
    public LocalDateTime getStartDate() { return this.startDateTime; }
    /** getEndDate メソッド */
    public LocalDateTime getEndDate() { return this.endDateTime; }
}
