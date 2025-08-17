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
    /**
     * UserRoleSearchDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public UserRoleSearchDto() {}
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
}
