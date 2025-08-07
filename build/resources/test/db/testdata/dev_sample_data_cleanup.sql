-- 雲流システム テストデータ削除スクリプト
TRUNCATE TABLE audit_logs,
             application_attachments,
             application_approvals,
             travel_request_details,
             expense_request_details,
             applications,
             approval_routes,
             application_types,
             department_responsible_users,
             users,
             positions,
             departments
    RESTART IDENTITY CASCADE;
