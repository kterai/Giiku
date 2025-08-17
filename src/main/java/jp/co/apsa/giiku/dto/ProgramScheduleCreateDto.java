package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * プログラムスケジュール作成DTO
 *
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
    /**
     * ProgramScheduleCreateDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public ProgramScheduleCreateDto() {}
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

    // ---- Alias getters for controller compatibility ----
    /**
     * getStartDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public LocalDateTime getStartDate() { return this.startDateTime; }
    /**
     * getEndDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public LocalDateTime getEndDate() { return this.endDateTime; }
}
