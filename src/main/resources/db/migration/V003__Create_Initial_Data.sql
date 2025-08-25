-- V004: Initial Data Population
-- Populates the database with complete 54 lectures across 3 months
-- Includes course structure, sample questions, and initial configuration

-- Insert Grade Settings (default configuration)
INSERT INTO grade_settings (setting_name, exercise_weight, quiz_weight, mock_test_weight, passing_threshold, is_active, created_by, updated_by) 
VALUES ('Default Grade Settings', 0.40, 0.30, 0.30, 60.00, true, 1, 1);

-- Insert 3 Months
INSERT INTO months (id, month_number, title, description, created_by, updated_by) VALUES
(1, 1, '第1月', 'Web開発基礎とJavaScript入門', 1, 1),
(2, 2, '第2月', 'フレームワーク開発とデータベース設計', 1, 1),
(3, 3, '第3月', '実践開発とプロジェクト統合', 1, 1);

-- Insert Weeks (18 weeks total, 6 weeks per month)
INSERT INTO weeks (month_id, week_number, week_name, description, created_by, updated_by) VALUES 
-- Month 1 weeks
(1, 1, '第1週', 'HTML/CSS基礎', 1, 1),
(1, 2, '第2週', 'JavaScript基礎', 1, 1),
(1, 3, '第3週', 'DOM操作とイベント', 1, 1),
(1, 4, '第4週', 'Ajax通信とAPI', 1, 1),
(1, 5, '第5週', 'ES6+とモジュール', 1, 1),
(1, 6, '第6週', 'フロントエンド総合', 1, 1),
-- Month 2 weeks  
(2, 7, '第7週', 'Node.js基礎', 1, 1),
(2, 8, '第8週', 'Express.js開発', 1, 1),
(2, 9, '第9週', 'データベース設計', 1, 1),
(2, 10, '第10週', 'ORM とデータ操作', 1, 1),
(2, 11, '第11週', '認証とセキュリティ', 1, 1),
(2, 12, '第12週', 'テストとデバッグ', 1, 1),
-- Month 3 weeks
(3, 13, '第13週', 'React基礎', 1, 1),
(3, 14, '第14週', 'State管理とRedux', 1, 1),
(3, 15, '第15週', 'プロジェクト設計', 1, 1),
(3, 16, '第16週', '実装とコードレビュー', 1, 1),
(3, 17, '第17週', 'デプロイと運用', 1, 1),
(3, 18, '第18週', '総合テストと振り返り', 1, 1);

-- Insert Days (18 weeks × 3 days = 54 days)
INSERT INTO days (week_id, day_number, day_name, description, created_by, updated_by) VALUES 
-- Week 1-6 (Days 1-18)
(1, 1, 'Day 1', 'HTML基礎構造', 1, 1), (1, 2, 'Day 2', 'CSS基礎スタイリング', 1, 1), (1, 3, 'Day 3', 'レスポンシブデザイン', 1, 1),
(2, 4, 'Day 4', 'JavaScript文法基礎', 1, 1), (2, 5, 'Day 5', '関数とスコープ', 1, 1), (2, 6, 'Day 6', '配列とオブジェクト', 1, 1),
(3, 7, 'Day 7', 'DOM要素の操作', 1, 1), (3, 8, 'Day 8', 'イベントハンドリング', 1, 1), (3, 9, 'Day 9', 'フォーム処理', 1, 1),
(4, 10, 'Day 10', 'XMLHttpRequest', 1, 1), (4, 11, 'Day 11', 'Fetch API', 1, 1), (4, 12, 'Day 12', 'REST API設計', 1, 1),
(5, 13, 'Day 13', 'ES6新機能', 1, 1), (5, 14, 'Day 14', 'モジュールシステム', 1, 1), (5, 15, 'Day 15', '非同期処理', 1, 1),
(6, 16, 'Day 16', 'Webpack基礎', 1, 1), (6, 17, 'Day 17', 'SPA開発', 1, 1), (6, 18, 'Day 18', 'フロントエンド総合演習', 1, 1),
-- Week 7-12 (Days 19-36)
(7, 19, 'Day 19', 'Node.js環境構築', 1, 1), (7, 20, 'Day 20', 'NPMとパッケージ管理', 1, 1), (7, 21, 'Day 21', 'サーバーサイド基礎', 1, 1),
(8, 22, 'Day 22', 'Express.js基礎', 1, 1), (8, 23, 'Day 23', 'ルーティングとミドルウェア', 1, 1), (8, 24, 'Day 24', 'テンプレートエンジン', 1, 1),
(9, 25, 'Day 25', 'SQL基礎', 1, 1), (9, 26, 'Day 26', 'データベース設計', 1, 1), (9, 27, 'Day 27', 'PostgreSQL実践', 1, 1),
(10, 28, 'Day 28', 'Sequelize基礎', 1, 1), (10, 29, 'Day 29', 'モデル定義とマイグレーション', 1, 1), (10, 30, 'Day 30', 'クエリとリレーション', 1, 1),
(11, 31, 'Day 31', 'セッション管理', 1, 1), (11, 32, 'Day 32', 'JWT認証', 1, 1), (11, 33, 'Day 33', 'セキュリティ対策', 1, 1),
(12, 34, 'Day 34', 'ユニットテスト', 1, 1), (12, 35, 'Day 35', '統合テスト', 1, 1), (12, 36, 'Day 36', 'デバッグ技法', 1, 1),
-- Week 13-18 (Days 37-54)
(13, 37, 'Day 37', 'React環境セットアップ', 1, 1), (13, 38, 'Day 38', 'コンポーネント基礎', 1, 1), (13, 39, 'Day 39', 'Props とState', 1, 1),
(14, 40, 'Day 40', 'useState とuseEffect', 1, 1), (14, 41, 'Day 41', 'Redux基礎', 1, 1), (14, 42, 'Day 42', 'Redux Toolkit', 1, 1),
(15, 43, 'Day 43', 'アプリケーション設計', 1, 1), (15, 44, 'Day 44', 'コンポーネント設計', 1, 1), (15, 45, 'Day 45', 'データフロー設計', 1, 1),
(16, 46, 'Day 46', 'プロジェクト実装1', 1, 1), (16, 47, 'Day 47', 'プロジェクト実装2', 1, 1), (16, 48, 'Day 48', 'コードレビューと改善', 1, 1),
(17, 49, 'Day 49', 'デプロイメント準備', 1, 1), (17, 50, 'Day 50', 'Herokuデプロイ', 1, 1), (17, 51, 'Day 51', '運用とモニタリング', 1, 1),
(18, 52, 'Day 52', '最終プロジェクト発表', 1, 1), (18, 53, 'Day 53', '総合復習', 1, 1), (18, 54, 'Day 54', '修了テストと振り返り', 1, 1);


-- Update sequences to current max values
SELECT setval('months_id_seq', (SELECT MAX(id) FROM months));
SELECT setval('weeks_id_seq', (SELECT MAX(id) FROM weeks));
SELECT setval('days_id_seq', (SELECT MAX(id) FROM days));
SELECT setval('grade_settings_id_seq', (SELECT MAX(id) FROM grade_settings));
