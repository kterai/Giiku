package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;

public class UserRoleUpdateDto {

    @Size(max = 50)
    private String roleName;

    @Size(max = 200)
    private String description;

    private Boolean isActive;

    public UserRoleUpdateDto() {}

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}