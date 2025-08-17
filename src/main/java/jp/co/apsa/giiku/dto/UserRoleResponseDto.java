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
    /** UserRoleResponseDto メソッド */
    public UserRoleResponseDto() {}
    /** getId メソッド */
    public Long getId() { return id; }
    /** setId メソッド */
    public void setId(Long id) { this.id = id; }
    /** getUserId メソッド */
    public Long getUserId() { return userId; }
    /** setUserId メソッド */
    public void setUserId(Long userId) { this.userId = userId; }
    /** getUserName メソッド */
    public String getUserName() { return userName; }
    /** setUserName メソッド */
    public void setUserName(String userName) { this.userName = userName; }
    /** getRoleName メソッド */
    public String getRoleName() { return roleName; }
    /** setRoleName メソッド */
    public void setRoleName(String roleName) { this.roleName = roleName; }
    /** getCompanyId メソッド */
    public Long getCompanyId() { return companyId; }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    /** getCompanyName メソッド */
    public String getCompanyName() { return companyName; }
    /** setCompanyName メソッド */
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    /** getDescription メソッド */
    public String getDescription() { return description; }
    /** setDescription メソッド */
    public void setDescription(String description) { this.description = description; }
    /** getIsActive メソッド */
    public Boolean getIsActive() { return isActive; }
    /** setIsActive メソッド */
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    /** getCreatedAt メソッド */
    public LocalDateTime getCreatedAt() { return createdAt; }
    /** setCreatedAt メソッド */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    /** getUpdatedAt メソッド */
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    /** setUpdatedAt メソッド */
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
