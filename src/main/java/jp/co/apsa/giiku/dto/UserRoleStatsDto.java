package jp.co.apsa.giiku.dto;

/**
 * ユーザー役割統計情報を表すDTO。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class UserRoleStatsDto {
    /** 総役割数 */
    private Long totalRoles;
    /** アクティブな役割数 */
    private Long activeRoles;
    /** 非アクティブな役割数 */
    private Long inactiveRoles;
    /** 総ユーザー数 */
    private Long totalUsers;
    /** アクティブユーザー数 */
    private Long activeUsers;

    /** デフォルトコンストラクタ */
    public UserRoleStatsDto() {}

    public Long getTotalRoles() { return totalRoles; }
    public void setTotalRoles(Long totalRoles) { this.totalRoles = totalRoles; }

    public Long getActiveRoles() { return activeRoles; }
    public void setActiveRoles(Long activeRoles) { this.activeRoles = activeRoles; }

    public Long getInactiveRoles() { return inactiveRoles; }
    public void setInactiveRoles(Long inactiveRoles) { this.inactiveRoles = inactiveRoles; }

    public Long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(Long totalUsers) { this.totalUsers = totalUsers; }

    public Long getActiveUsers() { return activeUsers; }
    public void setActiveUsers(Long activeUsers) { this.activeUsers = activeUsers; }
}