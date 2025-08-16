package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * プログラムスケジュール作成DTO
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
    public ProgramScheduleCreateDto() {}

    public Long getProgramId() { return programId; }
    public void setProgramId(Long programId) { this.programId = programId; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getStartDateTime() { return startDateTime; }
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }

    public LocalDateTime getEndDateTime() { return endDateTime; }
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Long getInstructorId() { return instructorId; }
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}