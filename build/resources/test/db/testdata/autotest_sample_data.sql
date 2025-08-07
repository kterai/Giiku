-- 雲流システム 開発用テストデータ
-- 各テーブルに少量のサンプルレコードを挿入します。

-- 部署
INSERT INTO departments (id, name, code, parent_id, manager_id, display_order, active, created_by, created_at, updated_by, updated_at) VALUES
(1, '営業部', 'SALES', NULL, NULL, 1, true, 1, NOW(), 1, NOW()),
(2, '総務部', 'ADMIN', NULL, NULL, 2, true, 1, NOW(), 1, NOW()),
(3, '第一開発部', 'DEV1', NULL, NULL, 3, true, 1, NOW(), 1, NOW()),
(4, '第二開発部', 'DEV2', NULL, NULL, 4, true, 1, NOW(), 1, NOW()),
(5, '第三開発部', 'DEV3', NULL, NULL, 5, true, 1, NOW(), 1, NOW());

-- 役職
INSERT INTO positions (id, position_name, position_code, hierarchy_level, is_manager, display_order, is_active, created_by, created_at, updated_by, updated_at) VALUES
(1, '部長', 'MGR', 1, true, 1, true, 1, NOW(), 1, NOW()),
(2, '課長', 'LEAD', 2, true, 2, true, 1, NOW(), 1, NOW()),
(3, '一般', 'STAFF', 3, false, 3, true, 1, NOW(), 1, NOW());

-- ユーザー
INSERT INTO users (id, username, password, email, name, department_id, position_id, supervisor_id, role, slack_id, active, approver, created_by, created_at, updated_by, updated_at) VALUES
(1, 'admin', '$2b$12$1EQFKu6OLH3CF337hfI4tepkzKwc8zCRC0ApfAwb8eJZuT1MpPWrC', 'admin@example.com', '管理者', 2, 1, NULL, 'ADMIN', 'U0001', true, true, 1, NOW(), 1, NOW()),
(2, 'manager', '$2b$12$iBN3BEgbNkAspzq7ZaRfo.BuK54fSx.tll0/E7HS4lmB9Pjs3uCZa', 'manager@example.com', 'マネージャー', 1, 2, 1, 'MANAGER', 'U0002', true, true, 1, NOW(), 1, NOW()),
(3, 'user1', '$2b$12$GyHc45dPjFb1628r2ETG/.lZLaY4Viq69V1SSwIRLdltaXlsvr.Tq', 'user1@example.com', '一般太郎', 3, 3, 2, 'USER', 'U0003', true, false, 1, NOW(), 1, NOW()),
(4, 'user2', '$2b$12$yfQh7.Gk5pL33qZm17xifuBLwL9lGomJK.G60L6/Iyz1t4YUEk3nq', 'user2@example.com', '一般花子', 4, 3, 2, 'USER', 'U0004', false, false, 1, NOW(), 1, NOW());
-- admin/admin123、manager/manager123、user1/user123、user2/user234

-- 部署責任者
INSERT INTO department_responsible_users (id, department_id, user_id) VALUES
(1, 1, 2),
(2, 2, 1),
(3, 3, 1),
(4, 3, 2);



-- 申請種別
INSERT INTO application_types (id, name, code, description, form_config, display_order, active, auto_approve, created_by, created_at, updated_by, updated_at) VALUES
(1, '出張申請', 'TRAVEL', '社員が出張する際に利用します', '{"fields":[{"name":"destination","type":"text"}]}', 1, true, false, 1, NOW(), 1, NOW()),
(2, '休暇申請', 'VACATION', '休暇取得申請用', '{"fields":[{"name":"days","type":"number"}]}', 2, true, false, 1, NOW(), 1, NOW()),
(3, '経費申請', 'EXPENSE', '経費精算用', '{"fields":[{"name":"amount","type":"number"}]}', 3, true, false, 1, NOW(), 1, NOW());

-- 承認ルート
INSERT INTO approval_routes (
    id,
    application_type_id,
    step_order,
    approver_role,
    approver_department_id,
    department_head,
    confirm_only,
    created_by,
    created_at,
    updated_by,
    updated_at)
VALUES
    (1, 1, 1, 'MANAGER', NULL, false, false, 1, NOW(), 1, NOW()),
    (2, 1, 2, 'ADMIN', NULL, false, false, 1, NOW(), 1, NOW()),
    (3, 2, 1, 'MANAGER', NULL, false, false, 1, NOW(), 1, NOW()),
    (4, 3, 1, 'MANAGER', 3, false, false, 1, NOW(), 1, NOW());

-- 申請
INSERT INTO applications (id, application_type_id, applicant_id, status, form_data, created_by, created_at, updated_by, updated_at) VALUES
(1, 1, 3, 'PENDING', '{"destination":"大阪"}', 3, NOW(), 3, NOW()),
(2, 2, 3, 'APPROVED', '{"days":3}', 3, NOW() - INTERVAL '10 days', 3, NOW() - INTERVAL '9 days'),
(3, 3, 2, 'REJECTED', '{"amount":5000}', 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days'),
(4, 2, 4, 'DRAFT', '{"days":1}', 4, NOW(), 4, NOW()),
(5, 1, 3, 'PENDING', '{"destination":"名古屋"}', 3, NOW() - INTERVAL '4 days', 3, NOW() - INTERVAL '4 days'),
(6, 1, 3, 'IN_REVIEW', '{"destination":"福岡"}', 3, NOW() - INTERVAL '3 days', 3, NOW() - INTERVAL '2 days'),
(7, 1, 2, 'APPROVED', '{"destination":"札幌"}', 2, NOW() - INTERVAL '20 days', 2, NOW() - INTERVAL '18 days'),
(8, 1, 3, 'REJECTED', '{"destination":"仙台"}', 3, NOW() - INTERVAL '15 days', 3, NOW() - INTERVAL '14 days');

-- 出張申請詳細
INSERT INTO travel_request_details (
    id,
    application_id,
    applicant_id,
    approver_id,
    applicant_name,
    destination,
    start_date,
    end_date,
    transport,
    need_advance,
    purpose,
    estimated_cost,
    status,
    current_step,
    created_at,
    updated_at
) VALUES
    (1, 1, 3, 2, '一般太郎', '大阪', CURRENT_DATE + INTERVAL '1 day', CURRENT_DATE + INTERVAL '2 days', '新幹線', false, '顧客訪問', 30000, 'SUBMITTED', 1, NOW(), NOW()),
    (2, 6, 3, 2, '一般太郎', '福岡', CURRENT_DATE + INTERVAL '3 days', CURRENT_DATE + INTERVAL '4 days', '飛行機', false, '支社打合せ', 50000, 'IN_PROGRESS', 1, NOW() - INTERVAL '3 days', NOW() - INTERVAL '2 days'),
    (3, 7, 2, 1, 'マネージャー', '札幌', CURRENT_DATE - INTERVAL '5 days', CURRENT_DATE - INTERVAL '3 days', '飛行機', true, '会議参加', 80000, 'APPROVED', 2, NOW() - INTERVAL '20 days', NOW() - INTERVAL '18 days'),
    (4, 8, 3, 2, '一般太郎', '仙台', CURRENT_DATE - INTERVAL '2 days', CURRENT_DATE - INTERVAL '1 day', '新幹線', false, '研修参加', 40000, 'REJECTED', 1, NOW() - INTERVAL '10 days', NOW() - INTERVAL '9 days');

-- 承認履歴
INSERT INTO application_approvals (id, application_id, step_order, approver_id, status, action, comment, created_by, created_at, updated_by, updated_at, is_delegated, notification_sent) VALUES
(1, 1, 1, 2, 'APPROVED', 'APPROVE', 'OK', 2, NOW(), 2, NOW(), false, true),
(2, 1, 2, 1, 'PENDING', NULL, NULL, 1, NOW(), 1, NOW(), false, false),
(3, 2, 1, 2, 'APPROVED', 'APPROVE', 'OK', 2, NOW() - INTERVAL '10 days', 2, NOW() - INTERVAL '10 days', false, true),
(4, 3, 1, 2, 'REJECTED', 'REJECT', 'NG', 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days', false, true);

-- 添付ファイル
INSERT INTO application_attachments (
    id,
    application_id,
    original_filename,
    stored_filename,
    file_path,
    file_size,
    content_type,
    file_extension,
    file_hash,
    hash_algorithm,
    description,
    display_order,
    downloadable,
    scan_status,
    scanned_at,
    created_by,
    created_at,
    updated_by,
    updated_at
) VALUES
    (1, 1, 'itinerary.pdf', 'itinerary.pdf', '/files/itinerary.pdf', 102400, 'application/pdf', 'pdf', NULL, NULL, '旅程表', 1, true, 'CLEAN', NOW(), 3, NOW(), 3, NOW()),
    (2, 3, 'receipt.png', 'receipt.png', '/files/receipt.png', 20480, 'image/png', 'png', NULL, NULL, '領収書', 1, true, 'CLEAN', NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days');

-- 監査ログ
INSERT INTO audit_logs (id, table_name, operation, record_id, old_values, new_values, user_id, created_at) VALUES
(1, 'applications', 'INSERT', 1, NULL, '{"status":"PENDING"}', 3, NOW()),
(2, 'applications', 'UPDATE', 2, '{"status":"PENDING"}', '{"status":"APPROVED"}', 2, NOW() - INTERVAL '9 days');

-- シーケンス調整
SELECT setval('departments_id_seq', 5, true);
SELECT setval('positions_id_seq', 3, true);
SELECT setval('users_id_seq', 4, true);
SELECT setval('application_types_id_seq', 3, true);
SELECT setval('approval_routes_id_seq', 4, true);
SELECT setval('applications_id_seq', 8, true);
SELECT setval('application_approvals_id_seq', 4, true);
SELECT setval('application_attachments_id_seq', 2, true);
SELECT setval('audit_logs_id_seq', 2, true);
SELECT setval('application_approval_routes_id_seq', 2, true);
SELECT setval('travel_request_details_id_seq', 4, true);
SELECT setval('expense_request_details_id_seq', 1, true);
SELECT setval('department_responsible_users_id_seq', 4, true);

-- 経費申請詳細
INSERT INTO expense_request_details (
    id,
    application_id,
    applicant_id,
    approver_id,
    applicant_name,
    expense_date,
    amount,
    description,
    status,
    current_step,
    created_at,
    updated_at
) VALUES
    (1, 3, 2, 1, 'マネージャー', CURRENT_DATE - INTERVAL '5 days', 5000, '交通費', 'REJECTED', 1, NOW() - INTERVAL '5 days', NOW() - INTERVAL '4 days');
