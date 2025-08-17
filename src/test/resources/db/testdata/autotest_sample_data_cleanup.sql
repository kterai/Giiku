-- 技育システム テストデータ削除スクリプト
TRUNCATE TABLE audit_logs,
             users,
             companies
    RESTART IDENTITY CASCADE;
