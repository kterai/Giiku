package jp.co.apsa.giiku.dto;

import java.time.LocalDateTime;

/**
 * プログラムスケジュールレスポンスDTO
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class ProgramScheduleResponseDto {

    private Long id;
    private Long programId;
    private String programName;
    private Long companyId;
    private String companyName;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Integer capacity;
    private Integer currentParticipants;
    private Long instructorId;
    private String instructorName;
    private String location;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors, getters, setters
    /** ProgramScheduleResponseDto メソッド */
    public ProgramScheduleResponseDto() {}
    /** getId メソッド */
    public Long getId() { return id; }
    /** setId メソッド */
    public void setId(Long id) { this.id = id; }
    /** getProgramId メソッド */
    public Long getProgramId() { return programId; }
    /** setProgramId メソッド */
    public void setProgramId(Long programId) { this.programId = programId; }
    /** getProgramName メソッド */
    public String getProgramName() { return programName; }
    /** setProgramName メソッド */
    public void setProgramName(String programName) { this.programName = programName; }
    /** getCompanyId メソッド */
    public Long getCompanyId() { return companyId; }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    /** getCompanyName メソッド */
    public String getCompanyName() { return companyName; }
    /** setCompanyName メソッド */
    public void setCompanyName(String companyName) { this.companyName = companyName; }
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
    /** getCurrentParticipants メソッド */
    public Integer getCurrentParticipants() { return currentParticipants; }
    /** setCurrentParticipants メソッド */
    public void setCurrentParticipants(Integer currentParticipants) { this.currentParticipants = currentParticipants; }
    /** getInstructorId メソッド */
    public Long getInstructorId() { return instructorId; }
    /** setInstructorId メソッド */
    public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
    /** getInstructorName メソッド */
    public String getInstructorName() { return instructorName; }
    /** setInstructorName メソッド */
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
    /** getLocation メソッド */
    public String getLocation() { return location; }
    /** setLocation メソッド */
    public void setLocation(String location) { this.location = location; }
    /** getStatus メソッド */
    public String getStatus() { return status; }
    /** setStatus メソッド */
    public void setStatus(String status) { this.status = status; }
    /** getCreatedAt メソッド */
    public LocalDateTime getCreatedAt() { return createdAt; }
    /** setCreatedAt メソッド */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    /** getUpdatedAt メソッド */
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    /** setUpdatedAt メソッド */
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
