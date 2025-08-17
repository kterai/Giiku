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
    /**
     * ProgramScheduleResponseDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public ProgramScheduleResponseDto() {}
    /**
     * getId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getId() { return id; }
    /**
     * setId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setId(Long id) { this.id = id; }
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
     * getProgramName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getProgramName() { return programName; }
    /**
     * setProgramName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setProgramName(String programName) { this.programName = programName; }
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
     * getCompanyName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getCompanyName() { return companyName; }
    /**
     * setCompanyName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    /**
     * getTitle メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getTitle() { return title; }
    /**
     * setTitle メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTitle(String title) { this.title = title; }
    /**
     * getDescription メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getDescription() { return description; }
    /**
     * setDescription メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setDescription(String description) { this.description = description; }
    /**
     * getStartDateTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getStartDateTime() { return startDateTime; }
    /**
     * setStartDateTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }
    /**
     * getEndDateTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getEndDateTime() { return endDateTime; }
    /**
     * setEndDateTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }
    /**
     * getCapacity メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getCapacity() { return capacity; }
    /**
     * setCapacity メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    /**
     * getCurrentParticipants メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getCurrentParticipants() { return currentParticipants; }
    /**
     * setCurrentParticipants メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCurrentParticipants(Integer currentParticipants) { this.currentParticipants = currentParticipants; }
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
    /**
     * getInstructorName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getInstructorName() { return instructorName; }
    /**
     * setInstructorName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
    /**
     * getLocation メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getLocation() { return location; }
    /**
     * setLocation メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setLocation(String location) { this.location = location; }
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
     * getCreatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getCreatedAt() { return createdAt; }
    /**
     * setCreatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    /**
     * getUpdatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    /**
     * setUpdatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
