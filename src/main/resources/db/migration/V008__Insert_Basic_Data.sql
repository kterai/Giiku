-- V008__Insert_Basic_Data.sql
-- 基本データ・その他のデータ
-- Essential supporting data for the Learning Management System

-- ===============================
-- INSTRUCTORS DATA (講師データ)
-- ===============================

INSERT INTO instructors (id, user_id, specialization, experience_years, bio, created_by, created_at, updated_by, updated_at) VALUES
(1, 2, 'データ構造,アルゴリズム,C++', 12, 'アルゴリズム設計の専門家として12年間の実務経験。効率的なプログラム設計と最適化技術の指導を得意とする。', 1, 1, 1, NOW());

-- ===============================
-- STUDENTS DATA (学生データ)
-- ===============================

INSERT INTO students (id, user_id, enrollment_date, student_status, learning_goals, created_by, created_at, updated_by, updated_at) VALUES
(1, 3, '2024-01-15', 'active', 'プログラミング基礎を習得し、Webアプリケーション開発ができるようになること', 1, 1, 1, NOW()),
(2, 4, '2024-01-15', 'active', 'データ構造とアルゴリズムを理解し、効率的なプログラムを作成できるようになること', 1, 1, 1, NOW());

INSERT INTO training_programs (id, program_name, description, duration_months, total_hours, difficulty_level, prerequisites, created_by, created_at, updated_by, updated_at) VALUES
(1, 'プログラミング基礎コース', '初心者向けプログラミング基礎学習プログラム', 12, 480, 'beginner', '特になし（完全初心者歓迎）', 1, 1, 1, NOW()),
(2, 'フルスタック開発者養成コース', 'Web開発の全体像を学ぶ包括的プログラム', 12, 600, 'intermediate', 'プログラミング基礎知識', 1, 1, 1, NOW()),
(3, 'データサイエンティスト準備コース', 'データ分析と機械学習の基礎を学ぶプログラム', 12, 520, 'intermediate', '数学基礎、統計学基礎', 1, 1, 1, NOW()),
(4, 'エンタープライズ開発者コース', '大規模システム開発のための高度なプログラム', 12, 640, 'advanced', '3年以上の開発経験', 1, 1, 1, NOW()),
(5, 'DevOpsエンジニア養成コース', 'CI/CD、インフラ自動化を学ぶ実践的プログラム', 12, 560, 'advanced', 'システム開発経験、Linux基礎', 1, 1, 1, NOW());

-- ===============================
-- PROGRAM SCHEDULES DATA (プログラムスケジュールデータ)
-- ===============================

INSERT INTO program_schedules (id, program_id, instructor_id, start_date, end_date, max_students, current_students, schedule_status, created_by, created_at, updated_by, updated_at) VALUES
(1, 1, 1, '2024-01-15', '2025-01-15', 30, 8, 'active', 1, 1, 1, NOW()),
(2, 2, 1, '2024-02-01', '2025-02-01', 25, 5, 'active', 1, 1, 1, NOW()),
(3, 3, 1, '2024-03-01', '2025-03-01', 20, 0, 'scheduled', 1, 1, 1, NOW()),
(4, 1, 1, '2024-06-01', '2025-06-01', 30, 0, 'scheduled', 1, 1, 1, NOW());

-- ===============================
-- DAILY SCHEDULES DATA (日次スケジュールデータ)
-- ===============================

INSERT INTO daily_schedules (id, program_schedule_id, day_id, scheduled_date, start_time, end_time, classroom, schedule_status, notes, created_by, created_at, updated_by, updated_at) VALUES
(1, 1, 1, '2024-01-15', '09:00:00', '12:00:00', 'A101', 'completed', '初回オリエンテーション実施', 1, 1, 1, NOW()),
(2, 1, 2, '2024-01-16', '09:00:00', '12:00:00', 'A101', 'completed', NULL, 1, 1, 1, NOW()),
(3, 1, 3, '2024-01-17', '09:00:00', '12:00:00', 'A101', 'completed', NULL, 1, 1, 1, NOW()),
(4, 1, 4, '2024-01-18', '09:00:00', '12:00:00', 'A101', 'completed', NULL, 1, 1, 1, NOW()),
(5, 1, 5, '2024-01-19', '09:00:00', '12:00:00', 'A101', 'completed', '週末課題説明', 1, 1, 1, NOW()),
(6, 1, 6, '2024-01-22', '09:00:00', '12:00:00', 'A101', 'scheduled', NULL, 1, 1, 1, NOW()),
(7, 1, 7, '2024-01-23', '09:00:00', '12:00:00', 'A101', 'scheduled', NULL, 1, 1, 1, NOW()),
(8, 1, 8, '2024-01-24', '09:00:00', '12:00:00', 'A101', 'scheduled', NULL, 1, 1, 1, NOW()),
(9, 1, 9, '2024-01-25', '09:00:00', '12:00:00', 'A101', 'scheduled', NULL, 1, 1, 1, NOW()),
(10, 1, 10, '2024-01-26', '09:00:00', '12:00:00', 'A101', 'scheduled', NULL, 1, 1, 1, NOW()),
(11, 2, 17, '2024-02-01', '13:00:00', '17:00:00', 'B201', 'scheduled', 'Web開発コース開始', 1, 1, 1, NOW()),
(12, 2, 18, '2024-02-02', '13:00:00', '17:00:00', 'B201', 'scheduled', NULL, 1, 1, 1, NOW()),
(13, 2, 19, '2024-02-05', '13:00:00', '17:00:00', 'B201', 'scheduled', NULL, 1, 1, 1, NOW()),
(14, 2, 20, '2024-02-06', '13:00:00', '17:00:00', 'B201', 'scheduled', NULL, 1, 1, 1, NOW()),
(15, 2, 21, '2024-02-07', '13:00:00', '17:00:00', 'B201', 'scheduled', NULL, 1, 1, 1, NOW());

-- ===============================
-- STUDENT GRADE SUMMARIES DATA (学生成績集計データ)
-- ===============================

INSERT INTO student_grade_summaries (id, student_id, lecture_id, exercise_score, quiz_score, mock_test_score, total_score, grade_status, calculated_at, created_by, created_at, updated_by, updated_at) VALUES
(1, 1, 1, 85.0, 78.0, 0.0, 81.5, 'excellent', NOW(), 1, 1, 1, NOW()),
(2, 1, 2, 92.0, 88.0, 0.0, 90.0, 'excellent', NOW(), 1, 1, 1, NOW()),
(3, 1, 3, 76.0, 82.0, 0.0, 79.0, 'good', NOW(), 1, 1, 1, NOW()),
(4, 1, 4, 88.0, 85.0, 0.0, 86.5, 'excellent', NOW(), 1, 1, 1, NOW()),
(5, 2, 1, 72.0, 68.0, 0.0, 70.0, 'good', NOW(), 1, 1, 1, NOW()),
(6, 2, 2, 79.0, 75.0, 0.0, 77.0, 'good', NOW(), 1, 1, 1, NOW()),
(7, 2, 3, 83.0, 80.0, 0.0, 81.5, 'excellent', NOW(), 1, 1, 1, NOW()),
(8, 2, 4, 75.0, 78.0, 0.0, 76.5, 'good', NOW(), 1, 1, 1, NOW());

-- ===============================
-- MAINTENANCE DATA (保守・管理データ)
-- ===============================

-- System configuration settings
INSERT INTO system_settings (setting_key, setting_value, description, created_by, created_at, updated_by, updated_at) VALUES
('system_name', 'Giiku Learning Management System', 'システム名称', 1, 1, 1, NOW()),
('version', '1.0.0', 'システムバージョン', 1, 1, 1, NOW()),
('max_students_per_class', '30', 'クラスあたりの最大学生数', 1, 1, 1, NOW()),
('default_session_duration', '180', 'デフォルト授業時間（分）', 1, 1, 1, NOW()),
('exercise_weight', '40', '演習問題の重み（％）', 1, 1, 1, NOW()),
('quiz_weight', '30', '理解度テストの重み（％）', 1, 1, 1, NOW()),
('mock_test_weight', '30', '模擬試験の重み（％）', 1, 1, 1, NOW()),
('passing_score', '70', '合格基準点', 1, 1, 1, NOW()),
('excellent_score', '85', '優秀評価の基準点', 1, 1, 1, NOW()),
('backup_retention_days', '30', 'バックアップ保持日数', 1, 1, 1, NOW()),
('session_timeout_minutes', '60', 'セッションタイムアウト（分）', 1, 1, 1, NOW()),
('max_login_attempts', '5', '最大ログイン試行回数', 1, 1, 1, NOW()),
('password_min_length', '8', 'パスワード最小文字数', 1, 1, 1, NOW()),
('email_notification_enabled', 'true', 'メール通知機能の有効/無効', 1, 1, 1, NOW()),
('maintenance_mode', 'false', 'メンテナンスモードの有効/無効', 1, 1, 1, NOW());

-- Audit log for system maintenance
INSERT INTO audit_logs (id, table_name, operation_type, record_id, old_values, new_values, changed_by, change_timestamp, created_by, created_at, updated_by, updated_at) VALUES
(1, 'students', 'INSERT', 1, NULL, '{"user_id": 3, "enrollment_date": "2024-01-15", "student_status": "active"}', 'system', NOW(), 1, 1, 1, NOW()),
(2, 'instructors', 'INSERT', 1, NULL, '{"user_id": 2, "specialization": "データ構造,アルゴリズム,C++"}', 'system', NOW(), 1, 1, 1, NOW()),
(3, 'training_programs', 'INSERT', 1, NULL, '{"program_name": "プログラミング基礎コース", "difficulty_level": "beginner"}', 'system', NOW(), 1, 1, 1, NOW());

-- ===============================
-- SAMPLE NOTIFICATION DATA (通知データサンプル)
-- ===============================

INSERT INTO notifications (id, user_id, notification_type, title, message, is_read, priority, expires_at, created_by, created_at, updated_by, updated_at) VALUES
(1, 3, 'course_start', 'コース開始のお知らせ', 'プログラミング基礎コースが開始されました。初回授業は2024年1月15日 9:00からです。', false, 'high', '2024-02-15 23:59:59', 1, 1, 1, NOW()),
(2, 4, 'assignment_due', '課題提出期限のお知らせ', '第1回演習課題の提出期限は2024年1月22日です。', false, 'medium', '2024-01-22 23:59:59', 1, 1, 1, NOW()),
(3, 3, 'grade_published', '成績公開のお知らせ', '第1回講義の成績が公開されました。マイページでご確認ください。', false, 'low', '2024-02-22 23:59:59', 1, 1, 1, NOW()),
(4, 2, 'instructor_schedule', '授業スケジュール変更', '2024年1月25日の授業時間が変更になりました。', false, 'high', '2024-01-25 08:00:00', 1, 1, 1, NOW()),
(5, 4, 'system_maintenance', 'システムメンテナンスのお知らせ', '2024年2月10日 2:00-4:00にシステムメンテナンスを実施します。', false, 'medium', '2024-02-10 23:59:59', 1, 1, 1, NOW());

-- ===============================
-- SEQUENCE UPDATES
-- ===============================

SELECT setval('instructors_id_seq', (SELECT MAX(id) FROM instructors));
SELECT setval('students_id_seq', (SELECT MAX(id) FROM students));
SELECT setval('training_programs_id_seq', (SELECT MAX(id) FROM training_programs));
SELECT setval('program_schedules_id_seq', (SELECT MAX(id) FROM program_schedules));
SELECT setval('daily_schedules_id_seq', (SELECT MAX(id) FROM daily_schedules));
SELECT setval('student_grade_summaries_id_seq', (SELECT MAX(id) FROM student_grade_summaries));
SELECT setval('audit_logs_id_seq', (SELECT MAX(id) FROM audit_logs));
SELECT setval('notifications_id_seq', (SELECT MAX(id) FROM notifications));
