package jp.co.apsa.giiku.dto;

public class UserRoleSearchDto {
    private Long userId;
    private String roleName;
    private Long companyId;
    private Boolean isActive;

    public UserRoleSearchDto() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}