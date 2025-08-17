package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;
/**
 * The UserRoleCreateDto class.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
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
    /** UserRoleCreateDto メソッド */
    public UserRoleCreateDto() {}
    /** getUserId メソッド */
    public Long getUserId() { return userId; }
    /** setUserId メソッド */
    public void setUserId(Long userId) { this.userId = userId; }
    /** getRoleName メソッド */
    public String getRoleName() { return roleName; }
    /** setRoleName メソッド */
    public void setRoleName(String roleName) { this.roleName = roleName; }
    /** getCompanyId メソッド */
    public Long getCompanyId() { return companyId; }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    /** getDescription メソッド */
    public String getDescription() { return description; }
    /** setDescription メソッド */
    public void setDescription(String description) { this.description = description; }
}
