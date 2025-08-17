package jp.co.apsa.giiku.dto;
/**
 * The UserRoleSearchDto class.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class UserRoleSearchDto {
    private Long userId;
    private String roleName;
    private Long companyId;
    private Boolean isActive;
    /** UserRoleSearchDto メソッド */
    public UserRoleSearchDto() {}
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
    /** getIsActive メソッド */
    public Boolean getIsActive() { return isActive; }
    /** setIsActive メソッド */
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
