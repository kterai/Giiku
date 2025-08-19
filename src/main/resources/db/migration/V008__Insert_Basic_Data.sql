
-- Additional tables needed for the basic data

-- System Settings Table
CREATE TABLE IF NOT EXISTS system_settings (
    id BIGSERIAL PRIMARY KEY,
    setting_key VARCHAR(100) UNIQUE NOT NULL,
    setting_value TEXT NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Audit Logs Table
CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGSERIAL PRIMARY KEY,
    table_name VARCHAR(100) NOT NULL,
    operation_type VARCHAR(20) NOT NULL, -- INSERT, UPDATE, DELETE
    record_id BIGINT,
    old_values JSONB,
    new_values JSONB,
    changed_by VARCHAR(100),
    change_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Notifications Table
CREATE TABLE IF NOT EXISTS notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    notification_type VARCHAR(50) NOT NULL,
    title VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    priority VARCHAR(20) DEFAULT 'medium', -- low, medium, high
    expires_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Indexes for performance
CREATE INDEX IF NOT EXISTS idx_audit_logs_table_record ON audit_logs(table_name, record_id);
CREATE INDEX IF NOT EXISTS idx_notifications_user_read ON notifications(user_id, is_read);
CREATE INDEX IF NOT EXISTS idx_notifications_type_priority ON notifications(notification_type, priority);


-- V008__Insert_Basic_Data.sql
-- 基本データ・その他のデータ
-- Essential supporting data for the Learning Management System

-- ===============================
-- INSTRUCTORS DATA (講師データ)
-- ===============================

INSERT INTO instructors (id, user_id, specialization, experience_years, bio, created_at, updated_at) VALUES
(1, 1, 'プログラミング基礎,Java,オブジェクト指向', 8, '大学でコンピュータサイエンスを学び、8年間のソフトウェア開発経験を持つ。初心者向けプログラミング教育に情熱を注いでいる。', NOW(), NOW()),
(2, 2, 'データ構造,アルゴリズム,C++', 12, 'アルゴリズム設計の専門家として12年間の実務経験。効率的なプログラム設計と最適化技術の指導を得意とする。', NOW(), NOW()),
(3, 3, 'Web開発,JavaScript,HTML,CSS', 6, 'フロントエンド開発のスペシャリスト。モダンなWeb技術を活用した実践的な教育を提供。', NOW(), NOW()),
(4, 4, 'データベース,SQL,PostgreSQL,MySQL', 10, 'データベース設計とSQL最適化の専門家。大規模システムでの実務経験豊富。', NOW(), NOW()),
(5, 5, 'Spring Boot,フレームワーク,RESTful API', 7, 'エンタープライズアプリケーション開発の経験者。Spring Boot生態系の深い知識を持つ。', NOW(), NOW()),
(6, 6, 'テスト駆動開発,品質管理,DevOps', 9, 'ソフトウェア品質向上の専門家。TDDとCI/CDパイプラインの構築指導を得意とする。', NOW(), NOW()),
(7, 7, 'システム設計,アーキテクチャ,マイクロサービス', 15, '大規模システムアーキテクチャの設計経験15年。エンタープライズレベルの設計パターンを指導。', NOW(), NOW()),
(8, 8, 'クラウド,AWS,Docker,Kubernetes', 5, 'クラウドネイティブ技術の専門家。コンテナ技術とクラウドインフラの実践的指導。', NOW(), NOW());

-- ===============================
-- STUDENTS DATA (学生データ)
-- ===============================

INSERT INTO students (id, user_id, enrollment_date, student_status, learning_goals, created_at, updated_at) VALUES
(1, 9, '2024-01-15', 'active', 'プログラミング基礎を習得し、Webアプリケーション開発ができるようになること', NOW(), NOW()),
(2, 10, '2024-01-15', 'active', 'データ構造とアルゴリズムを理解し、効率的なプログラムを作成できるようになること', NOW(), NOW()),
(3, 11, '2024-01-22', 'active', 'フルスタック開発者として就職することを目指す', NOW(), NOW()),
(4, 12, '2024-01-22', 'active', 'データベース設計とSQL最適化のスキルを身につけること', NOW(), NOW()),
(5, 13, '2024-02-01', 'active', 'オブジェクト指向プログラミングをマスターすること', NOW(), NOW()),
(6, 14, '2024-02-01', 'active', 'エンタープライズアプリケーション開発の技術を習得すること', NOW(), NOW()),
(7, 15, '2024-02-05', 'active', 'システムアーキテクトとしてのキャリアを築くこと', NOW(), NOW()),
(8, 16, '2024-02-05', 'active', 'DevOpsエンジニアとして転職することを目指す', NOW(), NOW()),
(9, 17, '2024-02-10', 'active', 'クラウド技術を活用したモダンな開発手法を学ぶこと', NOW(), NOW()),
(10, 18, '2024-02-10', 'active', 'テスト駆動開発による高品質なソフトウェア開発を学ぶこと', NOW(), NOW()),
(11, 19, '2024-02-15', 'active', 'AI・機械学習分野への転職を目指すための基礎固め', NOW(), NOW()),
(12, 20, '2024-02-15', 'active', 'フリーランスエンジニアとして独立するためのスキル習得', NOW(), NOW()),
(13, 21, '2024-02-20', 'active', 'スタートアップでの開発経験を積むための技術力向上', NOW(), NOW()),
(14, 22, '2024-02-20', 'active', '大手IT企業への転職を目指すレベルの技術習得', NOW(), NOW()),
(15, 23, '2024-02-25', 'active', 'プロダクトマネージャーとしての技術理解を深めること', NOW(), NOW());

-- ===============================
-- TRAINING PROGRAMS DATA (研修プログラムデータ)
-- ===============================

INSERT INTO training_programs (id, program_name, description, duration_months, total_hours, difficulty_level, prerequisites, created_at, updated_at) VALUES
(1, 'プログラミング基礎コース', '初心者向けプログラミング基礎学習プログラム', 12, 480, 'beginner', '特になし（完全初心者歓迎）', NOW(), NOW()),
(2, 'フルスタック開発者養成コース', 'Web開発の全体像を学ぶ包括的プログラム', 12, 600, 'intermediate', 'プログラミング基礎知識', NOW(), NOW()),
(3, 'データサイエンティスト準備コース', 'データ分析と機械学習の基礎を学ぶプログラム', 12, 520, 'intermediate', '数学基礎、統計学基礎', NOW(), NOW()),
(4, 'エンタープライズ開発者コース', '大規模システム開発のための高度なプログラム', 12, 640, 'advanced', '3年以上の開発経験', NOW(), NOW()),
(5, 'DevOpsエンジニア養成コース', 'CI/CD、インフラ自動化を学ぶ実践的プログラム', 12, 560, 'advanced', 'システム開発経験、Linux基礎', NOW(), NOW());

-- ===============================
-- PROGRAM SCHEDULES DATA (プログラムスケジュールデータ)
-- ===============================

INSERT INTO program_schedules (id, program_id, instructor_id, start_date, end_date, max_students, current_students, schedule_status, created_at, updated_at) VALUES
(1, 1, 1, '2024-01-15', '2025-01-15', 30, 8, 'active', NOW(), NOW()),
(2, 2, 3, '2024-02-01', '2025-02-01', 25, 5, 'active', NOW(), NOW()),
(3, 3, 2, '2024-03-01', '2025-03-01', 20, 0, 'scheduled', NOW(), NOW()),
(4, 4, 7, '2024-04-01', '2025-04-01', 15, 0, 'scheduled', NOW(), NOW()),
(5, 5, 8, '2024-05-01', '2025-05-01', 20, 0, 'scheduled', NOW(), NOW()),
(6, 1, 4, '2024-06-01', '2025-06-01', 30, 0, 'scheduled', NOW(), NOW()),
(7, 2, 5, '2024-07-01', '2025-07-01', 25, 0, 'scheduled', NOW(), NOW()),
(8, 1, 6, '2024-08-01', '2025-08-01', 30, 0, 'scheduled', NOW(), NOW());

-- ===============================
-- DAILY SCHEDULES DATA (日次スケジュールデータ)
-- ===============================

INSERT INTO daily_schedules (id, program_schedule_id, day_id, scheduled_date, start_time, end_time, classroom, schedule_status, notes, created_at, updated_at) VALUES
(1, 1, 1, '2024-01-15', '09:00:00', '12:00:00', 'A101', 'completed', '初回オリエンテーション実施', NOW(), NOW()),
(2, 1, 2, '2024-01-16', '09:00:00', '12:00:00', 'A101', 'completed', NULL, NOW(), NOW()),
(3, 1, 3, '2024-01-17', '09:00:00', '12:00:00', 'A101', 'completed', NULL, NOW(), NOW()),
(4, 1, 4, '2024-01-18', '09:00:00', '12:00:00', 'A101', 'completed', NULL, NOW(), NOW()),
(5, 1, 5, '2024-01-19', '09:00:00', '12:00:00', 'A101', 'completed', '週末課題説明', NOW(), NOW()),
(6, 1, 6, '2024-01-22', '09:00:00', '12:00:00', 'A101', 'scheduled', NULL, NOW(), NOW()),
(7, 1, 7, '2024-01-23', '09:00:00', '12:00:00', 'A101', 'scheduled', NULL, NOW(), NOW()),
(8, 1, 8, '2024-01-24', '09:00:00', '12:00:00', 'A101', 'scheduled', NULL, NOW(), NOW()),
(9, 1, 9, '2024-01-25', '09:00:00', '12:00:00', 'A101', 'scheduled', NULL, NOW(), NOW()),
(10, 1, 10, '2024-01-26', '09:00:00', '12:00:00', 'A101', 'scheduled', NULL, NOW(), NOW()),
(11, 2, 17, '2024-02-01', '13:00:00', '17:00:00', 'B201', 'scheduled', 'Web開発コース開始', NOW(), NOW()),
(12, 2, 18, '2024-02-02', '13:00:00', '17:00:00', 'B201', 'scheduled', NULL, NOW(), NOW()),
(13, 2, 19, '2024-02-05', '13:00:00', '17:00:00', 'B201', 'scheduled', NULL, NOW(), NOW()),
(14, 2, 20, '2024-02-06', '13:00:00', '17:00:00', 'B201', 'scheduled', NULL, NOW(), NOW()),
(15, 2, 21, '2024-02-07', '13:00:00', '17:00:00', 'B201', 'scheduled', NULL, NOW(), NOW());

-- ===============================
-- STUDENT GRADE SUMMARIES DATA (学生成績集計データ)
-- ===============================

INSERT INTO student_grade_summaries (id, student_id, lecture_id, exercise_score, quiz_score, mock_test_score, total_score, grade_status, calculated_at, created_at, updated_at) VALUES
(1, 1, 1, 85.0, 78.0, 0.0, 81.5, 'excellent', NOW(), NOW(), NOW()),
(2, 1, 2, 92.0, 88.0, 0.0, 90.0, 'excellent', NOW(), NOW(), NOW()),
(3, 1, 3, 76.0, 82.0, 0.0, 79.0, 'good', NOW(), NOW(), NOW()),
(4, 1, 4, 88.0, 85.0, 0.0, 86.5, 'excellent', NOW(), NOW(), NOW()),
(5, 2, 1, 72.0, 68.0, 0.0, 70.0, 'good', NOW(), NOW(), NOW()),
(6, 2, 2, 79.0, 75.0, 0.0, 77.0, 'good', NOW(), NOW(), NOW()),
(7, 2, 3, 83.0, 80.0, 0.0, 81.5, 'excellent', NOW(), NOW(), NOW()),
(8, 2, 4, 75.0, 78.0, 0.0, 76.5, 'good', NOW(), NOW(), NOW()),
(9, 3, 1, 95.0, 92.0, 0.0, 93.5, 'excellent', NOW(), NOW(), NOW()),
(10, 3, 2, 88.0, 90.0, 0.0, 89.0, 'excellent', NOW(), NOW(), NOW()),
(11, 4, 1, 65.0, 70.0, 0.0, 67.5, 'satisfactory', NOW(), NOW(), NOW()),
(12, 4, 2, 70.0, 72.0, 0.0, 71.0, 'good', NOW(), NOW(), NOW()),
(13, 5, 1, 82.0, 79.0, 0.0, 80.5, 'good', NOW(), NOW(), NOW()),
(14, 5, 2, 86.0, 84.0, 0.0, 85.0, 'excellent', NOW(), NOW(), NOW()),
(15, 6, 1, 78.0, 75.0, 0.0, 76.5, 'good', NOW(), NOW(), NOW());

-- ===============================
-- MAINTENANCE DATA (保守・管理データ)
-- ===============================

-- System configuration settings
INSERT INTO system_settings (setting_key, setting_value, description, created_at, updated_at) VALUES
('system_name', 'Giiku Learning Management System', 'システム名称', NOW(), NOW()),
('version', '1.0.0', 'システムバージョン', NOW(), NOW()),
('max_students_per_class', '30', 'クラスあたりの最大学生数', NOW(), NOW()),
('default_session_duration', '180', 'デフォルト授業時間（分）', NOW(), NOW()),
('exercise_weight', '40', '演習問題の重み（％）', NOW(), NOW()),
('quiz_weight', '30', '理解度テストの重み（％）', NOW(), NOW()),
('mock_test_weight', '30', '模擬試験の重み（％）', NOW(), NOW()),
('passing_score', '70', '合格基準点', NOW(), NOW()),
('excellent_score', '85', '優秀評価の基準点', NOW(), NOW()),
('backup_retention_days', '30', 'バックアップ保持日数', NOW(), NOW()),
('session_timeout_minutes', '60', 'セッションタイムアウト（分）', NOW(), NOW()),
('max_login_attempts', '5', '最大ログイン試行回数', NOW(), NOW()),
('password_min_length', '8', 'パスワード最小文字数', NOW(), NOW()),
('email_notification_enabled', 'true', 'メール通知機能の有効/無効', NOW(), NOW()),
('maintenance_mode', 'false', 'メンテナンスモードの有効/無効', NOW(), NOW());

-- Audit log for system maintenance
INSERT INTO audit_logs (id, table_name, operation_type, record_id, old_values, new_values, changed_by, change_timestamp, created_at, updated_at) VALUES
(1, 'students', 'INSERT', 1, NULL, '{"user_id": 9, "enrollment_date": "2024-01-15", "student_status": "active"}', 'system', NOW(), NOW(), NOW()),
(2, 'instructors', 'INSERT', 1, NULL, '{"user_id": 1, "specialization": "プログラミング基礎,Java,オブジェクト指向"}', 'system', NOW(), NOW(), NOW()),
(3, 'training_programs', 'INSERT', 1, NULL, '{"program_name": "プログラミング基礎コース", "difficulty_level": "beginner"}', 'system', NOW(), NOW(), NOW());

-- ===============================
-- SAMPLE NOTIFICATION DATA (通知データサンプル)
-- ===============================

INSERT INTO notifications (id, user_id, notification_type, title, message, is_read, priority, expires_at, created_at, updated_at) VALUES
(1, 9, 'course_start', 'コース開始のお知らせ', 'プログラミング基礎コースが開始されました。初回授業は2024年1月15日 9:00からです。', false, 'high', '2024-02-15 23:59:59', NOW(), NOW()),
(2, 10, 'assignment_due', '課題提出期限のお知らせ', '第1回演習課題の提出期限は2024年1月22日です。', false, 'medium', '2024-01-22 23:59:59', NOW(), NOW()),
(3, 11, 'grade_published', '成績公開のお知らせ', '第1回講義の成績が公開されました。マイページでご確認ください。', false, 'low', '2024-02-22 23:59:59', NOW(), NOW()),
(4, 1, 'instructor_schedule', '授業スケジュール変更', '2024年1月25日の授業時間が変更になりました。', false, 'high', '2024-01-25 08:00:00', NOW(), NOW()),
(5, 12, 'system_maintenance', 'システムメンテナンスのお知らせ', '2024年2月10日 2:00-4:00にシステムメンテナンスを実施します。', false, 'medium', '2024-02-10 23:59:59', NOW(), NOW());

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
