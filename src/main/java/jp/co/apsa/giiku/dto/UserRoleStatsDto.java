package jp.co.apsa.giiku.dto;

public class UserRoleStatsDto {
    private Long totalRoles;
    private Long activeRoles;
    private Long inactiveRoles;
    private Long totalUsers;

    public UserRoleStatsDto() {}

    public Long getTotalRoles() { return totalRoles; }
    public void setTotalRoles(Long totalRoles) { this.totalRoles = totalRoles; }

    public Long getActiveRoles() { return activeRoles; }
    public void setActiveRoles(Long activeRoles) { this.activeRoles = activeRoles; }

    public Long getInactiveRoles() { return inactiveRoles; }
    public void setInactiveRoles(Long inactiveRoles) { this.inactiveRoles = inactiveRoles; }

    public Long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(Long totalUsers) { this.totalUsers = totalUsers; }
}