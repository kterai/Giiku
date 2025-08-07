-- 雲流システム 開発用テストデータ
-- 各テーブルに少量のサンプルレコードを挿入します。

-- 部署
INSERT INTO departments (id, name, code, parent_id, manager_id, display_order, active, created_by, created_at, updated_by, updated_at) VALUES
(1, '営業部', 'SALES', NULL, NULL, 1, true, 1, NOW(), 1, NOW()),
(2, '総務部', 'ADMIN', NULL, NULL, 2, true, 1, NOW(), 1, NOW()),
(3, '第一開発部', 'DEV1', NULL, NULL, 3, true, 1, NOW(), 1, NOW()),
(4, '第二開発部', 'DEV2', NULL, NULL, 4, true, 1, NOW(), 1, NOW()),
(5, '第三開発部', 'DEV3', NULL, NULL, 5, true, 1, NOW(), 1, NOW());
(101, '営業部1', 'SALES1', NULL, NULL, 1, true, 1, NOW(), 1, NOW())
(102, '総務部1', 'ADMIN1', NULL, NULL, 2, true, 1, NOW(), 1, NOW())
(103, '第一開発部1', 'DEV11', NULL, NULL, 3, true, 1, NOW(), 1, NOW())
(104, '第二開発部1', 'DEV21', NULL, NULL, 4, true, 1, NOW(), 1, NOW())
(105, '第三開発部1', 'DEV31', NULL, NULL, 5, true, 1, NOW(), 1, NOW());
(201, '営業部2', 'SALES2', NULL, NULL, 1, true, 1, NOW(), 1, NOW())
(202, '総務部2', 'ADMIN2', NULL, NULL, 2, true, 1, NOW(), 1, NOW())
(203, '第一開発部2', 'DEV12', NULL, NULL, 3, true, 1, NOW(), 1, NOW())
(204, '第二開発部2', 'DEV22', NULL, NULL, 4, true, 1, NOW(), 1, NOW())
(205, '第三開発部2', 'DEV32', NULL, NULL, 5, true, 1, NOW(), 1, NOW());
(301, '営業部3', 'SALES3', NULL, NULL, 1, true, 1, NOW(), 1, NOW())
(302, '総務部3', 'ADMIN3', NULL, NULL, 2, true, 1, NOW(), 1, NOW())
(303, '第一開発部3', 'DEV13', NULL, NULL, 3, true, 1, NOW(), 1, NOW())
(304, '第二開発部3', 'DEV23', NULL, NULL, 4, true, 1, NOW(), 1, NOW())
(305, '第三開発部3', 'DEV33', NULL, NULL, 5, true, 1, NOW(), 1, NOW());
(401, '営業部4', 'SALES4', NULL, NULL, 1, true, 1, NOW(), 1, NOW())
(402, '総務部4', 'ADMIN4', NULL, NULL, 2, true, 1, NOW(), 1, NOW())
(403, '第一開発部4', 'DEV14', NULL, NULL, 3, true, 1, NOW(), 1, NOW())
(404, '第二開発部4', 'DEV24', NULL, NULL, 4, true, 1, NOW(), 1, NOW())
(405, '第三開発部4', 'DEV34', NULL, NULL, 5, true, 1, NOW(), 1, NOW());
INSERT INTO positions (id, position_name, position_code, hierarchy_level, is_manager, display_order, is_active, created_by, created_at, updated_by, updated_at) VALUES
(1, '部長', 'MGR', 1, true, 1, true, 1, NOW(), 1, NOW()),
(2, '課長', 'LEAD', 2, true, 2, true, 1, NOW(), 1, NOW()),
(3, '一般', 'STAFF', 3, false, 3, true, 1, NOW(), 1, NOW());
(101, '部長1', 'MGR1', 1, true, 1, true, 1, NOW(), 1, NOW())
(102, '課長1', 'LEAD1', 2, true, 2, true, 1, NOW(), 1, NOW())
(103, '一般1', 'STAFF1', 3, false, 3, true, 1, NOW(), 1, NOW());
(201, '部長2', 'MGR2', 1, true, 1, true, 1, NOW(), 1, NOW())
(202, '課長2', 'LEAD2', 2, true, 2, true, 1, NOW(), 1, NOW())
(203, '一般2', 'STAFF2', 3, false, 3, true, 1, NOW(), 1, NOW());
(301, '部長3', 'MGR3', 1, true, 1, true, 1, NOW(), 1, NOW())
(302, '課長3', 'LEAD3', 2, true, 2, true, 1, NOW(), 1, NOW())
(303, '一般3', 'STAFF3', 3, false, 3, true, 1, NOW(), 1, NOW());
(401, '部長4', 'MGR4', 1, true, 1, true, 1, NOW(), 1, NOW())
(402, '課長4', 'LEAD4', 2, true, 2, true, 1, NOW(), 1, NOW())
(403, '一般4', 'STAFF4', 3, false, 3, true, 1, NOW(), 1, NOW());
INSERT INTO users (id, username, password, email, name, department_id, position_id, supervisor_id, role, slack_id, active, approver, created_by, created_at, updated_by, updated_at) VALUES
(1, 'admin', '$2b$12$1EQFKu6OLH3CF337hfI4tepkzKwc8zCRC0ApfAwb8eJZuT1MpPWrC', 'admin@example.com', '管理者', 2, 1, NULL, 'ADMIN', 'U0001', true, true, 1, NOW(), 1, NOW()),
(2, 'manager', '$2b$12$iBN3BEgbNkAspzq7ZaRfo.BuK54fSx.tll0/E7HS4lmB9Pjs3uCZa', 'manager@example.com', 'マネージャー', 1, 2, 1, 'MANAGER', 'U0002', true, true, 1, NOW(), 1, NOW()),
(3, 'user1', '$2b$12$GyHc45dPjFb1628r2ETG/.lZLaY4Viq69V1SSwIRLdltaXlsvr.Tq', 'user1@example.com', '一般太郎', 3, 3, 2, 'USER', 'U0003', true, false, 1, NOW(), 1, NOW()),
(4, 'user2', '$2b$12$yfQh7.Gk5pL33qZm17xifuBLwL9lGomJK.G60L6/Iyz1t4YUEk3nq', 'user2@example.com', '一般花子', 4, 3, 2, 'USER', 'U0004', false, false, 1, NOW(), 1, NOW()),
(5, 'approver3', '$2b$12$iBN3BEgbNkAspzq7ZaRfo.BuK54fSx.tll0/E7HS4lmB9Pjs3uCZa', 'approver3@example.com', '承認三郎', 3, 2, 1, 'MANAGER', 'U0005', true, true, 1, NOW(), 1, NOW());
(101, 'admin1', '$2b$12$1EQFKu6OLH3CF337hfI4tepkzKwc8zCRC0ApfAwb8eJZuT1MpPWrC', 'admin+1@example.com', '管理者1', 2, 1, NULL, 'ADMIN', 'U101', true, true, 1, NOW(), 1, NOW())
(102, 'manager1', '$2b$12$iBN3BEgbNkAspzq7ZaRfo.BuK54fSx.tll0/E7HS4lmB9Pjs3uCZa', 'manager+1@example.com', 'マネージャー1', 1, 2, 1, 'MANAGER', 'U102', true, true, 1, NOW(), 1, NOW())
(103, 'user11', '$2b$12$GyHc45dPjFb1628r2ETG/.lZLaY4Viq69V1SSwIRLdltaXlsvr.Tq', 'user1+1@example.com', '一般太郎1', 3, 3, 2, 'USER', 'U103', true, false, 1, NOW(), 1, NOW())
(104, 'user21', '$2b$12$yfQh7.Gk5pL33qZm17xifuBLwL9lGomJK.G60L6/Iyz1t4YUEk3nq', 'user2+1@example.com', '一般花子1', 4, 3, 2, 'USER', 'U104', false, false, 1, NOW(), 1, NOW())
(105, 'approver31', '$2b$12$iBN3BEgbNkAspzq7ZaRfo.BuK54fSx.tll0/E7HS4lmB9Pjs3uCZa', 'approver3+1@example.com', '承認三郎1', 3, 2, 1, 'MANAGER', 'U105', true, true, 1, NOW(), 1, NOW());
(201, 'admin2', '$2b$12$1EQFKu6OLH3CF337hfI4tepkzKwc8zCRC0ApfAwb8eJZuT1MpPWrC', 'admin+2@example.com', '管理者2', 2, 1, NULL, 'ADMIN', 'U201', true, true, 1, NOW(), 1, NOW())
(202, 'manager2', '$2b$12$iBN3BEgbNkAspzq7ZaRfo.BuK54fSx.tll0/E7HS4lmB9Pjs3uCZa', 'manager+2@example.com', 'マネージャー2', 1, 2, 1, 'MANAGER', 'U202', true, true, 1, NOW(), 1, NOW())
(203, 'user12', '$2b$12$GyHc45dPjFb1628r2ETG/.lZLaY4Viq69V1SSwIRLdltaXlsvr.Tq', 'user1+2@example.com', '一般太郎2', 3, 3, 2, 'USER', 'U203', true, false, 1, NOW(), 1, NOW())
(204, 'user22', '$2b$12$yfQh7.Gk5pL33qZm17xifuBLwL9lGomJK.G60L6/Iyz1t4YUEk3nq', 'user2+2@example.com', '一般花子2', 4, 3, 2, 'USER', 'U204', false, false, 1, NOW(), 1, NOW())
(205, 'approver32', '$2b$12$iBN3BEgbNkAspzq7ZaRfo.BuK54fSx.tll0/E7HS4lmB9Pjs3uCZa', 'approver3+2@example.com', '承認三郎2', 3, 2, 1, 'MANAGER', 'U205', true, true, 1, NOW(), 1, NOW());
(301, 'admin3', '$2b$12$1EQFKu6OLH3CF337hfI4tepkzKwc8zCRC0ApfAwb8eJZuT1MpPWrC', 'admin+3@example.com', '管理者3', 2, 1, NULL, 'ADMIN', 'U301', true, true, 1, NOW(), 1, NOW())
(302, 'manager3', '$2b$12$iBN3BEgbNkAspzq7ZaRfo.BuK54fSx.tll0/E7HS4lmB9Pjs3uCZa', 'manager+3@example.com', 'マネージャー3', 1, 2, 1, 'MANAGER', 'U302', true, true, 1, NOW(), 1, NOW())
(303, 'user13', '$2b$12$GyHc45dPjFb1628r2ETG/.lZLaY4Viq69V1SSwIRLdltaXlsvr.Tq', 'user1+3@example.com', '一般太郎3', 3, 3, 2, 'USER', 'U303', true, false, 1, NOW(), 1, NOW())
(304, 'user23', '$2b$12$yfQh7.Gk5pL33qZm17xifuBLwL9lGomJK.G60L6/Iyz1t4YUEk3nq', 'user2+3@example.com', '一般花子3', 4, 3, 2, 'USER', 'U304', false, false, 1, NOW(), 1, NOW())
(305, 'approver33', '$2b$12$iBN3BEgbNkAspzq7ZaRfo.BuK54fSx.tll0/E7HS4lmB9Pjs3uCZa', 'approver3+3@example.com', '承認三郎3', 3, 2, 1, 'MANAGER', 'U305', true, true, 1, NOW(), 1, NOW());
(401, 'admin4', '$2b$12$1EQFKu6OLH3CF337hfI4tepkzKwc8zCRC0ApfAwb8eJZuT1MpPWrC', 'admin+4@example.com', '管理者4', 2, 1, NULL, 'ADMIN', 'U401', true, true, 1, NOW(), 1, NOW())
(402, 'manager4', '$2b$12$iBN3BEgbNkAspzq7ZaRfo.BuK54fSx.tll0/E7HS4lmB9Pjs3uCZa', 'manager+4@example.com', 'マネージャー4', 1, 2, 1, 'MANAGER', 'U402', true, true, 1, NOW(), 1, NOW())
(403, 'user14', '$2b$12$GyHc45dPjFb1628r2ETG/.lZLaY4Viq69V1SSwIRLdltaXlsvr.Tq', 'user1+4@example.com', '一般太郎4', 3, 3, 2, 'USER', 'U403', true, false, 1, NOW(), 1, NOW())
(404, 'user24', '$2b$12$yfQh7.Gk5pL33qZm17xifuBLwL9lGomJK.G60L6/Iyz1t4YUEk3nq', 'user2+4@example.com', '一般花子4', 4, 3, 2, 'USER', 'U404', false, false, 1, NOW(), 1, NOW())
(405, 'approver34', '$2b$12$iBN3BEgbNkAspzq7ZaRfo.BuK54fSx.tll0/E7HS4lmB9Pjs3uCZa', 'approver3+4@example.com', '承認三郎4', 3, 2, 1, 'MANAGER', 'U405', true, true, 1, NOW(), 1, NOW());
INSERT INTO department_responsible_users (id, department_id, user_id) VALUES
(1, 1, 2),
(2, 2, 1),
(3, 3, 1),
(4, 3, 2);
(101, 1, 2)
(102, 2, 1)
(103, 3, 1)
(104, 3, 2);
(201, 1, 2)
(202, 2, 1)
(203, 3, 1)
(204, 3, 2);
(301, 1, 2)
(302, 2, 1)
(303, 3, 1)
(304, 3, 2);
(401, 1, 2)
(402, 2, 1)
(403, 3, 1)
(404, 3, 2);
INSERT INTO application_types (id, name, code, description, form_config, display_order, active, auto_approve, created_by, created_at, updated_by, updated_at) VALUES
(1, '出張申請', 'TRAVEL', '社員が出張する際に利用します', '{"fields":[{"name":"destination","type":"text"}]}', 1, true, false, 1, NOW(), 1, NOW()),
(2, '休暇申請', 'VACATION', '休暇取得申請用', '{"fields":[{"name":"days","type":"number"}]}', 2, true, false, 1, NOW(), 1, NOW()),
(3, '経費申請', 'EXPENSE', '経費精算用', '{"fields":[{"name":"amount","type":"number"}]}', 3, true, false, 1, NOW(), 1, NOW());
(101, '出張申請1', 'TRAVEL1', '社員が出張する際に利用します', '{"fields":[{"name":"destination","type":"text"}]}', 1, true, false, 1, NOW(), 1, NOW())
(102, '休暇申請1', 'VACATION1', '休暇取得申請用', '{"fields":[{"name":"days","type":"number"}]}', 2, true, false, 1, NOW(), 1, NOW())
(103, '経費申請1', 'EXPENSE1', '経費精算用', '{"fields":[{"name":"amount","type":"number"}]}', 3, true, false, 1, NOW(), 1, NOW());
(201, '出張申請2', 'TRAVEL2', '社員が出張する際に利用します', '{"fields":[{"name":"destination","type":"text"}]}', 1, true, false, 1, NOW(), 1, NOW())
(202, '休暇申請2', 'VACATION2', '休暇取得申請用', '{"fields":[{"name":"days","type":"number"}]}', 2, true, false, 1, NOW(), 1, NOW())
(203, '経費申請2', 'EXPENSE2', '経費精算用', '{"fields":[{"name":"amount","type":"number"}]}', 3, true, false, 1, NOW(), 1, NOW());
(301, '出張申請3', 'TRAVEL3', '社員が出張する際に利用します', '{"fields":[{"name":"destination","type":"text"}]}', 1, true, false, 1, NOW(), 1, NOW())
(302, '休暇申請3', 'VACATION3', '休暇取得申請用', '{"fields":[{"name":"days","type":"number"}]}', 2, true, false, 1, NOW(), 1, NOW())
(303, '経費申請3', 'EXPENSE3', '経費精算用', '{"fields":[{"name":"amount","type":"number"}]}', 3, true, false, 1, NOW(), 1, NOW());
(401, '出張申請4', 'TRAVEL4', '社員が出張する際に利用します', '{"fields":[{"name":"destination","type":"text"}]}', 1, true, false, 1, NOW(), 1, NOW())
(402, '休暇申請4', 'VACATION4', '休暇取得申請用', '{"fields":[{"name":"days","type":"number"}]}', 2, true, false, 1, NOW(), 1, NOW())
(403, '経費申請4', 'EXPENSE4', '経費精算用', '{"fields":[{"name":"amount","type":"number"}]}', 3, true, false, 1, NOW(), 1, NOW());
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
    id
    application_type_id
    step_order
    approver_role
    approver_department_id
    department_head
    confirm_only
    created_by
    created_at
    updated_by
    updated_at)
VALUES
    (1, 1, 1, 'MANAGER', NULL, false, false, 1, NOW(), 1, NOW())
    (2, 1, 2, 'ADMIN', NULL, false, false, 1, NOW(), 1, NOW())
    (3, 2, 1, 'MANAGER', NULL, false, false, 1, NOW(), 1, NOW())
    (4, 3, 1, 'MANAGER', 3, false, false, 1, NOW(), 1, NOW());
    id
    application_type_id
    step_order
    approver_role
    approver_department_id
    department_head
    confirm_only
    created_by
    created_at
    updated_by
    updated_at)
VALUES
    (1, 1, 1, 'MANAGER', NULL, false, false, 1, NOW(), 1, NOW())
    (2, 1, 2, 'ADMIN', NULL, false, false, 1, NOW(), 1, NOW())
    (3, 2, 1, 'MANAGER', NULL, false, false, 1, NOW(), 1, NOW())
    (4, 3, 1, 'MANAGER', 3, false, false, 1, NOW(), 1, NOW());
    id
    application_type_id
    step_order
    approver_role
    approver_department_id
    department_head
    confirm_only
    created_by
    created_at
    updated_by
    updated_at)
VALUES
    (1, 1, 1, 'MANAGER', NULL, false, false, 1, NOW(), 1, NOW())
    (2, 1, 2, 'ADMIN', NULL, false, false, 1, NOW(), 1, NOW())
    (3, 2, 1, 'MANAGER', NULL, false, false, 1, NOW(), 1, NOW())
    (4, 3, 1, 'MANAGER', 3, false, false, 1, NOW(), 1, NOW());
    id
    application_type_id
    step_order
    approver_role
    approver_department_id
    department_head
    confirm_only
    created_by
    created_at
    updated_by
    updated_at)
VALUES
    (1, 1, 1, 'MANAGER', NULL, false, false, 1, NOW(), 1, NOW())
    (2, 1, 2, 'ADMIN', NULL, false, false, 1, NOW(), 1, NOW())
    (3, 2, 1, 'MANAGER', NULL, false, false, 1, NOW(), 1, NOW())
    (4, 3, 1, 'MANAGER', 3, false, false, 1, NOW(), 1, NOW());
INSERT INTO applications (id, application_type_id, applicant_id, status, form_data, created_by, created_at, updated_by, updated_at) VALUES
(1, 1, 3, 'PENDING', '{"destination":"大阪"}', 3, NOW(), 3, NOW()),
(2, 2, 3, 'APPROVED', '{"days":3}', 3, NOW() - INTERVAL '10 days', 3, NOW() - INTERVAL '9 days'),
(3, 3, 2, 'REJECTED', '{"amount":5000}', 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days'),
(4, 2, 4, 'DRAFT', '{"days":1}', 4, NOW(), 4, NOW()),
(5, 1, 3, 'PENDING', '{"destination":"名古屋"}', 3, NOW() - INTERVAL '4 days', 3, NOW() - INTERVAL '4 days'),
(6, 1, 3, 'IN_REVIEW', '{"destination":"福岡"}', 3, NOW() - INTERVAL '3 days', 3, NOW() - INTERVAL '2 days'),
(7, 1, 2, 'APPROVED', '{"destination":"札幌"}', 2, NOW() - INTERVAL '20 days', 2, NOW() - INTERVAL '18 days'),
(8, 1, 3, 'REJECTED', '{"destination":"仙台"}', 3, NOW() - INTERVAL '15 days', 3, NOW() - INTERVAL '14 days');
(101, 1, 3, 'PENDING', '{"destination":"大阪"}', 3, NOW(), 3, NOW())
(102, 2, 3, 'APPROVED', '{"days":3}', 3, NOW() - INTERVAL '10 days', 3, NOW() - INTERVAL '9 days')
(103, 3, 2, 'REJECTED', '{"amount":5000}', 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days')
(104, 2, 4, 'DRAFT', '{"days":1}', 4, NOW(), 4, NOW())
(105, 1, 3, 'PENDING', '{"destination":"名古屋"}', 3, NOW() - INTERVAL '4 days', 3, NOW() - INTERVAL '4 days')
(106, 1, 3, 'IN_REVIEW', '{"destination":"福岡"}', 3, NOW() - INTERVAL '3 days', 3, NOW() - INTERVAL '2 days')
(107, 1, 2, 'APPROVED', '{"destination":"札幌"}', 2, NOW() - INTERVAL '20 days', 2, NOW() - INTERVAL '18 days')
(108, 1, 3, 'REJECTED', '{"destination":"仙台"}', 3, NOW() - INTERVAL '15 days', 3, NOW() - INTERVAL '14 days');
(201, 1, 3, 'PENDING', '{"destination":"大阪"}', 3, NOW(), 3, NOW())
(202, 2, 3, 'APPROVED', '{"days":3}', 3, NOW() - INTERVAL '10 days', 3, NOW() - INTERVAL '9 days')
(203, 3, 2, 'REJECTED', '{"amount":5000}', 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days')
(204, 2, 4, 'DRAFT', '{"days":1}', 4, NOW(), 4, NOW())
(205, 1, 3, 'PENDING', '{"destination":"名古屋"}', 3, NOW() - INTERVAL '4 days', 3, NOW() - INTERVAL '4 days')
(206, 1, 3, 'IN_REVIEW', '{"destination":"福岡"}', 3, NOW() - INTERVAL '3 days', 3, NOW() - INTERVAL '2 days')
(207, 1, 2, 'APPROVED', '{"destination":"札幌"}', 2, NOW() - INTERVAL '20 days', 2, NOW() - INTERVAL '18 days')
(208, 1, 3, 'REJECTED', '{"destination":"仙台"}', 3, NOW() - INTERVAL '15 days', 3, NOW() - INTERVAL '14 days');
(301, 1, 3, 'PENDING', '{"destination":"大阪"}', 3, NOW(), 3, NOW())
(302, 2, 3, 'APPROVED', '{"days":3}', 3, NOW() - INTERVAL '10 days', 3, NOW() - INTERVAL '9 days')
(303, 3, 2, 'REJECTED', '{"amount":5000}', 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days')
(304, 2, 4, 'DRAFT', '{"days":1}', 4, NOW(), 4, NOW())
(305, 1, 3, 'PENDING', '{"destination":"名古屋"}', 3, NOW() - INTERVAL '4 days', 3, NOW() - INTERVAL '4 days')
(306, 1, 3, 'IN_REVIEW', '{"destination":"福岡"}', 3, NOW() - INTERVAL '3 days', 3, NOW() - INTERVAL '2 days')
(307, 1, 2, 'APPROVED', '{"destination":"札幌"}', 2, NOW() - INTERVAL '20 days', 2, NOW() - INTERVAL '18 days')
(308, 1, 3, 'REJECTED', '{"destination":"仙台"}', 3, NOW() - INTERVAL '15 days', 3, NOW() - INTERVAL '14 days');
(401, 1, 3, 'PENDING', '{"destination":"大阪"}', 3, NOW(), 3, NOW())
(402, 2, 3, 'APPROVED', '{"days":3}', 3, NOW() - INTERVAL '10 days', 3, NOW() - INTERVAL '9 days')
(403, 3, 2, 'REJECTED', '{"amount":5000}', 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days')
(404, 2, 4, 'DRAFT', '{"days":1}', 4, NOW(), 4, NOW())
(405, 1, 3, 'PENDING', '{"destination":"名古屋"}', 3, NOW() - INTERVAL '4 days', 3, NOW() - INTERVAL '4 days')
(406, 1, 3, 'IN_REVIEW', '{"destination":"福岡"}', 3, NOW() - INTERVAL '3 days', 3, NOW() - INTERVAL '2 days')
(407, 1, 2, 'APPROVED', '{"destination":"札幌"}', 2, NOW() - INTERVAL '20 days', 2, NOW() - INTERVAL '18 days')
(408, 1, 3, 'REJECTED', '{"destination":"仙台"}', 3, NOW() - INTERVAL '15 days', 3, NOW() - INTERVAL '14 days');
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
    id
    application_id
    applicant_id
    approver_id
    applicant_name
    destination
    start_date
    end_date
    transport
    need_advance
    purpose
    estimated_cost
    status
    current_step
    created_at
    updated_at
) VALUES
    (1, 1, 3, 2, '一般太郎', '大阪', CURRENT_DATE + INTERVAL '1 day', CURRENT_DATE + INTERVAL '2 days', '新幹線', false, '顧客訪問', 30000, 'SUBMITTED', 1, NOW(), NOW())
    (2, 6, 3, 2, '一般太郎', '福岡', CURRENT_DATE + INTERVAL '3 days', CURRENT_DATE + INTERVAL '4 days', '飛行機', false, '支社打合せ', 50000, 'IN_PROGRESS', 1, NOW() - INTERVAL '3 days', NOW() - INTERVAL '2 days')
    (3, 7, 2, 1, 'マネージャー', '札幌', CURRENT_DATE - INTERVAL '5 days', CURRENT_DATE - INTERVAL '3 days', '飛行機', true, '会議参加', 80000, 'APPROVED', 2, NOW() - INTERVAL '20 days', NOW() - INTERVAL '18 days')
    (4, 8, 3, 2, '一般太郎', '仙台', CURRENT_DATE - INTERVAL '2 days', CURRENT_DATE - INTERVAL '1 day', '新幹線', false, '研修参加', 40000, 'REJECTED', 1, NOW() - INTERVAL '10 days', NOW() - INTERVAL '9 days');
    id
    application_id
    applicant_id
    approver_id
    applicant_name
    destination
    start_date
    end_date
    transport
    need_advance
    purpose
    estimated_cost
    status
    current_step
    created_at
    updated_at
) VALUES
    (1, 1, 3, 2, '一般太郎', '大阪', CURRENT_DATE + INTERVAL '1 day', CURRENT_DATE + INTERVAL '2 days', '新幹線', false, '顧客訪問', 30000, 'SUBMITTED', 1, NOW(), NOW())
    (2, 6, 3, 2, '一般太郎', '福岡', CURRENT_DATE + INTERVAL '3 days', CURRENT_DATE + INTERVAL '4 days', '飛行機', false, '支社打合せ', 50000, 'IN_PROGRESS', 1, NOW() - INTERVAL '3 days', NOW() - INTERVAL '2 days')
    (3, 7, 2, 1, 'マネージャー', '札幌', CURRENT_DATE - INTERVAL '5 days', CURRENT_DATE - INTERVAL '3 days', '飛行機', true, '会議参加', 80000, 'APPROVED', 2, NOW() - INTERVAL '20 days', NOW() - INTERVAL '18 days')
    (4, 8, 3, 2, '一般太郎', '仙台', CURRENT_DATE - INTERVAL '2 days', CURRENT_DATE - INTERVAL '1 day', '新幹線', false, '研修参加', 40000, 'REJECTED', 1, NOW() - INTERVAL '10 days', NOW() - INTERVAL '9 days');
    id
    application_id
    applicant_id
    approver_id
    applicant_name
    destination
    start_date
    end_date
    transport
    need_advance
    purpose
    estimated_cost
    status
    current_step
    created_at
    updated_at
) VALUES
    (1, 1, 3, 2, '一般太郎', '大阪', CURRENT_DATE + INTERVAL '1 day', CURRENT_DATE + INTERVAL '2 days', '新幹線', false, '顧客訪問', 30000, 'SUBMITTED', 1, NOW(), NOW())
    (2, 6, 3, 2, '一般太郎', '福岡', CURRENT_DATE + INTERVAL '3 days', CURRENT_DATE + INTERVAL '4 days', '飛行機', false, '支社打合せ', 50000, 'IN_PROGRESS', 1, NOW() - INTERVAL '3 days', NOW() - INTERVAL '2 days')
    (3, 7, 2, 1, 'マネージャー', '札幌', CURRENT_DATE - INTERVAL '5 days', CURRENT_DATE - INTERVAL '3 days', '飛行機', true, '会議参加', 80000, 'APPROVED', 2, NOW() - INTERVAL '20 days', NOW() - INTERVAL '18 days')
    (4, 8, 3, 2, '一般太郎', '仙台', CURRENT_DATE - INTERVAL '2 days', CURRENT_DATE - INTERVAL '1 day', '新幹線', false, '研修参加', 40000, 'REJECTED', 1, NOW() - INTERVAL '10 days', NOW() - INTERVAL '9 days');
    id
    application_id
    applicant_id
    approver_id
    applicant_name
    destination
    start_date
    end_date
    transport
    need_advance
    purpose
    estimated_cost
    status
    current_step
    created_at
    updated_at
) VALUES
    (1, 1, 3, 2, '一般太郎', '大阪', CURRENT_DATE + INTERVAL '1 day', CURRENT_DATE + INTERVAL '2 days', '新幹線', false, '顧客訪問', 30000, 'SUBMITTED', 1, NOW(), NOW())
    (2, 6, 3, 2, '一般太郎', '福岡', CURRENT_DATE + INTERVAL '3 days', CURRENT_DATE + INTERVAL '4 days', '飛行機', false, '支社打合せ', 50000, 'IN_PROGRESS', 1, NOW() - INTERVAL '3 days', NOW() - INTERVAL '2 days')
    (3, 7, 2, 1, 'マネージャー', '札幌', CURRENT_DATE - INTERVAL '5 days', CURRENT_DATE - INTERVAL '3 days', '飛行機', true, '会議参加', 80000, 'APPROVED', 2, NOW() - INTERVAL '20 days', NOW() - INTERVAL '18 days')
    (4, 8, 3, 2, '一般太郎', '仙台', CURRENT_DATE - INTERVAL '2 days', CURRENT_DATE - INTERVAL '1 day', '新幹線', false, '研修参加', 40000, 'REJECTED', 1, NOW() - INTERVAL '10 days', NOW() - INTERVAL '9 days');
INSERT INTO application_approvals (id, application_id, step_order, approver_id, status, action, comment, approved_at, created_by, created_at, updated_by, updated_at, is_delegated, notification_sent) VALUES
(1, 1, 1, 2, 'APPROVED', 'APPROVE', 'OK', NOW(), 2, NOW(), 2, NOW(), false, true),
(2, 1, 2, 1, 'PENDING', NULL, NULL, NULL, 1, NOW(), 1, NOW(), false, false),
(3, 2, 1, 2, 'APPROVED', 'APPROVE', 'OK', NOW() - INTERVAL '10 days', 2, NOW() - INTERVAL '10 days', 2, NOW() - INTERVAL '10 days', false, true),
(4, 3, 1, 2, 'REJECTED', 'REJECT', 'NG', NULL, 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days', false, true);
(101, 1, 1, 2, 'APPROVED', 'APPROVE', 'OK', NOW(), 2, NOW(), 2, NOW(), false, true)
(102, 1, 2, 1, 'PENDING', NULL, NULL, NULL, 1, NOW(), 1, NOW(), false, false)
(103, 2, 1, 2, 'APPROVED', 'APPROVE', 'OK', NOW() - INTERVAL '10 days', 2, NOW() - INTERVAL '10 days', 2, NOW() - INTERVAL '10 days', false, true)
(104, 3, 1, 2, 'REJECTED', 'REJECT', 'NG', NULL, 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days', false, true);
(201, 1, 1, 2, 'APPROVED', 'APPROVE', 'OK', NOW(), 2, NOW(), 2, NOW(), false, true)
(202, 1, 2, 1, 'PENDING', NULL, NULL, NULL, 1, NOW(), 1, NOW(), false, false)
(203, 2, 1, 2, 'APPROVED', 'APPROVE', 'OK', NOW() - INTERVAL '10 days', 2, NOW() - INTERVAL '10 days', 2, NOW() - INTERVAL '10 days', false, true)
(204, 3, 1, 2, 'REJECTED', 'REJECT', 'NG', NULL, 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days', false, true);
(301, 1, 1, 2, 'APPROVED', 'APPROVE', 'OK', NOW(), 2, NOW(), 2, NOW(), false, true)
(302, 1, 2, 1, 'PENDING', NULL, NULL, NULL, 1, NOW(), 1, NOW(), false, false)
(303, 2, 1, 2, 'APPROVED', 'APPROVE', 'OK', NOW() - INTERVAL '10 days', 2, NOW() - INTERVAL '10 days', 2, NOW() - INTERVAL '10 days', false, true)
(304, 3, 1, 2, 'REJECTED', 'REJECT', 'NG', NULL, 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days', false, true);
(401, 1, 1, 2, 'APPROVED', 'APPROVE', 'OK', NOW(), 2, NOW(), 2, NOW(), false, true)
(402, 1, 2, 1, 'PENDING', NULL, NULL, NULL, 1, NOW(), 1, NOW(), false, false)
(403, 2, 1, 2, 'APPROVED', 'APPROVE', 'OK', NOW() - INTERVAL '10 days', 2, NOW() - INTERVAL '10 days', 2, NOW() - INTERVAL '10 days', false, true)
(404, 3, 1, 2, 'REJECTED', 'REJECT', 'NG', NULL, 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days', false, true);
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
    id
    application_id
    original_filename
    stored_filename
    file_path
    file_size
    content_type
    file_extension
    file_hash
    hash_algorithm
    description
    display_order
    downloadable
    scan_status
    scanned_at
    created_by
    created_at
    updated_by
    updated_at
) VALUES
    (1, 1, 'itinerary.pdf', 'itinerary.pdf', '/files/itinerary.pdf', 102400, 'application/pdf', 'pdf', NULL, NULL, '旅程表', 1, true, 'CLEAN', NOW(), 3, NOW(), 3, NOW())
    (2, 3, 'receipt.png', 'receipt.png', '/files/receipt.png', 20480, 'image/png', 'png', NULL, NULL, '領収書', 1, true, 'CLEAN', NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days');
    id
    application_id
    original_filename
    stored_filename
    file_path
    file_size
    content_type
    file_extension
    file_hash
    hash_algorithm
    description
    display_order
    downloadable
    scan_status
    scanned_at
    created_by
    created_at
    updated_by
    updated_at
) VALUES
    (1, 1, 'itinerary.pdf', 'itinerary.pdf', '/files/itinerary.pdf', 102400, 'application/pdf', 'pdf', NULL, NULL, '旅程表', 1, true, 'CLEAN', NOW(), 3, NOW(), 3, NOW())
    (2, 3, 'receipt.png', 'receipt.png', '/files/receipt.png', 20480, 'image/png', 'png', NULL, NULL, '領収書', 1, true, 'CLEAN', NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days');
    id
    application_id
    original_filename
    stored_filename
    file_path
    file_size
    content_type
    file_extension
    file_hash
    hash_algorithm
    description
    display_order
    downloadable
    scan_status
    scanned_at
    created_by
    created_at
    updated_by
    updated_at
) VALUES
    (1, 1, 'itinerary.pdf', 'itinerary.pdf', '/files/itinerary.pdf', 102400, 'application/pdf', 'pdf', NULL, NULL, '旅程表', 1, true, 'CLEAN', NOW(), 3, NOW(), 3, NOW())
    (2, 3, 'receipt.png', 'receipt.png', '/files/receipt.png', 20480, 'image/png', 'png', NULL, NULL, '領収書', 1, true, 'CLEAN', NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days');
    id
    application_id
    original_filename
    stored_filename
    file_path
    file_size
    content_type
    file_extension
    file_hash
    hash_algorithm
    description
    display_order
    downloadable
    scan_status
    scanned_at
    created_by
    created_at
    updated_by
    updated_at
) VALUES
    (1, 1, 'itinerary.pdf', 'itinerary.pdf', '/files/itinerary.pdf', 102400, 'application/pdf', 'pdf', NULL, NULL, '旅程表', 1, true, 'CLEAN', NOW(), 3, NOW(), 3, NOW())
    (2, 3, 'receipt.png', 'receipt.png', '/files/receipt.png', 20480, 'image/png', 'png', NULL, NULL, '領収書', 1, true, 'CLEAN', NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '5 days', 2, NOW() - INTERVAL '4 days');
INSERT INTO audit_logs (id, table_name, operation, record_id, old_values, new_values, user_id, created_at) VALUES
(1, 'applications', 'INSERT', 1, NULL, '{"status":"PENDING"}', 3, NOW()),
(2, 'applications', 'UPDATE', 2, '{"status":"PENDING"}', '{"status":"APPROVED"}', 2, NOW() - INTERVAL '9 days');
(101, 'applications', 'INSERT', 1, NULL, '{"status":"PENDING"}', 3, NOW())
(102, 'applications', 'UPDATE', 2, '{"status":"PENDING"}', '{"status":"APPROVED"}', 2, NOW() - INTERVAL '9 days');
(201, 'applications', 'INSERT', 1, NULL, '{"status":"PENDING"}', 3, NOW())
(202, 'applications', 'UPDATE', 2, '{"status":"PENDING"}', '{"status":"APPROVED"}', 2, NOW() - INTERVAL '9 days');
(301, 'applications', 'INSERT', 1, NULL, '{"status":"PENDING"}', 3, NOW())
(302, 'applications', 'UPDATE', 2, '{"status":"PENDING"}', '{"status":"APPROVED"}', 2, NOW() - INTERVAL '9 days');
(401, 'applications', 'INSERT', 1, NULL, '{"status":"PENDING"}', 3, NOW())
(402, 'applications', 'UPDATE', 2, '{"status":"PENDING"}', '{"status":"APPROVED"}', 2, NOW() - INTERVAL '9 days');
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
    id
    application_id
    applicant_id
    approver_id
    applicant_name
    expense_date
    amount
    description
    status
    current_step
    created_at
    updated_at
) VALUES
    (1, 3, 2, 1, 'マネージャー', CURRENT_DATE - INTERVAL '5 days', 5000, '交通費', 'REJECTED', 1, NOW() - INTERVAL '5 days', NOW() - INTERVAL '4 days');
    id
    application_id
    applicant_id
    approver_id
    applicant_name
    expense_date
    amount
    description
    status
    current_step
    created_at
    updated_at
) VALUES
    (1, 3, 2, 1, 'マネージャー', CURRENT_DATE - INTERVAL '5 days', 5000, '交通費', 'REJECTED', 1, NOW() - INTERVAL '5 days', NOW() - INTERVAL '4 days');
    id
    application_id
    applicant_id
    approver_id
    applicant_name
    expense_date
    amount
    description
    status
    current_step
    created_at
    updated_at
) VALUES
    (1, 3, 2, 1, 'マネージャー', CURRENT_DATE - INTERVAL '5 days', 5000, '交通費', 'REJECTED', 1, NOW() - INTERVAL '5 days', NOW() - INTERVAL '4 days');
    id
    application_id
    applicant_id
    approver_id
    applicant_name
    expense_date
    amount
    description
    status
    current_step
    created_at
    updated_at
) VALUES
    (1, 3, 2, 1, 'マネージャー', CURRENT_DATE - INTERVAL '5 days', 5000, '交通費', 'REJECTED', 1, NOW() - INTERVAL '5 days', NOW() - INTERVAL '4 days');
-- シーケンス調整
SELECT setval('departments_id_seq', 405, true);
SELECT setval('positions_id_seq', 403, true);
SELECT setval('users_id_seq', 405, true);
SELECT setval('application_types_id_seq', 403, true);
SELECT setval('approval_routes_id_seq', 404, true);
SELECT setval('applications_id_seq', 408, true);
SELECT setval('application_approvals_id_seq', 404, true);
SELECT setval('application_attachments_id_seq', 402, true);
SELECT setval('audit_logs_id_seq', 402, true);
SELECT setval('application_approval_routes_id_seq', 2, true);
SELECT setval('travel_request_details_id_seq', 404, true);
SELECT setval('expense_request_details_id_seq', 401, true);
SELECT setval('department_responsible_users_id_seq', 404, true);
