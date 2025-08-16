package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;

public class UserRoleCreateDto {

    @NotNull
    private Long userId;

    @NotBlank
    @Size(max = 50)
    private String roleName;

    @NotNull
    private Long companyId;

    @Size(max = 200)
    private String description;

    public UserRoleCreateDto() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}