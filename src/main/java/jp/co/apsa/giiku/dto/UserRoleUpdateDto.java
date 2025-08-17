package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;
/**
 * The UserRoleUpdateDto class.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class UserRoleUpdateDto {

    @Size(max = 50)
    private String roleName;

    @Size(max = 200)
    private String description;

    private Boolean isActive;
    /** UserRoleUpdateDto メソッド */
    public UserRoleUpdateDto() {}
    /** getRoleName メソッド */
    public String getRoleName() { return roleName; }
    /** setRoleName メソッド */
    public void setRoleName(String roleName) { this.roleName = roleName; }
    /** getDescription メソッド */
    public String getDescription() { return description; }
    /** setDescription メソッド */
    public void setDescription(String description) { this.description = description; }
    /** getIsActive メソッド */
    public Boolean getIsActive() { return isActive; }
    /** setIsActive メソッド */
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
