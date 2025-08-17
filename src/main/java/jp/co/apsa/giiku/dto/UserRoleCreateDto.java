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
    /**
     * UserRoleCreateDto メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public UserRoleCreateDto() {}
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
}
