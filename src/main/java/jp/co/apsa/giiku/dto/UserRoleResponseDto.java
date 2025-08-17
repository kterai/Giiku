package jp.co.apsa.giiku.dto;

import java.time.LocalDateTime;
/**
 * The UserRoleResponseDto class.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */

public class UserRoleResponseDto {

    private Long id;
    private Long userId;
    private String userName;
    private String roleName;
    private Long companyId;
    private String companyName;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    /**
     * UserRoleResponseDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public UserRoleResponseDto() {}
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
     * getUserId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getUserId() { return userId; }
    /**
     * setUserId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setUserId(Long userId) { this.userId = userId; }
    /**
     * getUserName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getUserName() { return userName; }
    /**
     * setUserName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setUserName(String userName) { this.userName = userName; }
    /**
     * getRoleName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getRoleName() { return roleName; }
    /**
     * setRoleName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setRoleName(String roleName) { this.roleName = roleName; }
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
     * getIsActive メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Boolean getIsActive() { return isActive; }
    /**
     * setIsActive メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
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
