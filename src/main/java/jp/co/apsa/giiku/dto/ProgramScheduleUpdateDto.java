package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * プログラムスケジュール更新DTO
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class ProgramScheduleUpdateDto {

    @Size(max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Min(1)
    private Integer capacity;

    private Long instructorId;

    @Size(max = 200)
    private String location;

    // Constructors, getters, setters
    /** ProgramScheduleUpdateDto メソッド */
    public ProgramScheduleUpdateDto() {}
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
}
