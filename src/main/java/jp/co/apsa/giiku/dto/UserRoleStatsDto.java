package jp.co.apsa.giiku.dto;

/**
 * ユーザー役割統計情報を表すDTO。
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class UserRoleStatsDto {
    /** 総役割数 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    private Long totalRoles;
    /** アクティブな役割数 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    private Long activeRoles;
    /** 非アクティブな役割数 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    private Long inactiveRoles;
    /** 総ユーザー数 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    private Long totalUsers;
    /** アクティブユーザー数 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    private Long activeUsers;

    /** デフォルトコンストラクタ 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public UserRoleStatsDto() {}
    /**
     * getTotalRoles メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getTotalRoles() { return totalRoles; }
    /**
     * setTotalRoles メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTotalRoles(Long totalRoles) { this.totalRoles = totalRoles; }
    /**
     * getActiveRoles メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getActiveRoles() { return activeRoles; }
    /**
     * setActiveRoles メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setActiveRoles(Long activeRoles) { this.activeRoles = activeRoles; }
    /**
     * getInactiveRoles メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getInactiveRoles() { return inactiveRoles; }
    /**
     * setInactiveRoles メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setInactiveRoles(Long inactiveRoles) { this.inactiveRoles = inactiveRoles; }
    /**
     * getTotalUsers メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getTotalUsers() { return totalUsers; }
    /**
     * setTotalUsers メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setTotalUsers(Long totalUsers) { this.totalUsers = totalUsers; }
    /**
     * getActiveUsers メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getActiveUsers() { return activeUsers; }
    /**
     * setActiveUsers メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setActiveUsers(Long activeUsers) { this.activeUsers = activeUsers; }
}
