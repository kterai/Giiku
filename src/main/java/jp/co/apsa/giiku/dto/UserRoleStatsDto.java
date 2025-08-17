package jp.co.apsa.giiku.dto;

/**
 * ユーザー役割統計情報を表すDTO。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class UserRoleStatsDto {
    private Long totalRoles;
    private Long activeRoles;
    private Long inactiveRoles;
    private Long totalUsers;
    private Long activeUsers;

    public UserRoleStatsDto() {}
    /** getTotalRoles メソッド */
    public Long getTotalRoles() { return totalRoles; }
    /** setTotalRoles メソッド */
    public void setTotalRoles(Long totalRoles) { this.totalRoles = totalRoles; }
    /** getActiveRoles メソッド */
    public Long getActiveRoles() { return activeRoles; }
    /** setActiveRoles メソッド */
    public void setActiveRoles(Long activeRoles) { this.activeRoles = activeRoles; }
    /** getInactiveRoles メソッド */
    public Long getInactiveRoles() { return inactiveRoles; }
    /** setInactiveRoles メソッド */
    public void setInactiveRoles(Long inactiveRoles) { this.inactiveRoles = inactiveRoles; }
    /** getTotalUsers メソッド */
    public Long getTotalUsers() { return totalUsers; }
    /** setTotalUsers メソッド */
    public void setTotalUsers(Long totalUsers) { this.totalUsers = totalUsers; }
    /** getActiveUsers メソッド */
    public Long getActiveUsers() { return activeUsers; }
    /** setActiveUsers メソッド */
    public void setActiveUsers(Long activeUsers) { this.activeUsers = activeUsers; }
}
