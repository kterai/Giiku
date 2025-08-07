-- ========================================
-- companies テストデータ
-- ========================================
INSERT INTO public.companies (id, name, code, active, created_by, created_at, updated_by, updated_at)
VALUES
    (1, '株式会社アプサ', 'APSA', true, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP),
    (2, '株式会社トレイニー', 'TRAINEE', true, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

-- ========================================
-- users テストデータ
-- ========================================
INSERT INTO public.users (
    id, username, password, email, name, company_id, role, gender, birthday,
    active, created_by, created_at, updated_by, updated_at
)
VALUES
    (1, 'admin', '$2b$12$wSzqgNX0.nsNqrAvyGyJbudYp8wkjOdv52kZTSSFz0Sj2gCA7v4I2', 'admin@apsa.co.jp', '管理者 太郎', 1, 'ADMIN', 1, '1980-01-01', true, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP),
    (2, 'instructor', '$2b$12$wSzqgNX0.nsNqrAvyGyJbudYp8wkjOdv52kZTSSFz0Sj2gCA7v4I2', 'instructor@apsa.co.jp', '講師 花子', 1, 'INSTRUCTOR', 2, '1985-05-05', true, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP),
    (3, 'trainee_apsa', '$2b$12$wSzqgNX0.nsNqrAvyGyJbudYp8wkjOdv52kZTSSFz0Sj2gCA7v4I2', 'trainee1@apsa.co.jp', '受講者 一郎', 1, 'TRAINEE', 1, '1995-03-03', true, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP),
    (4, 'trainee_ext', '$2b$12$wSzqgNX0.nsNqrAvyGyJbudYp8wkjOdv52kZTSSFz0Sj2gCA7v4I2', 'trainee2@trainee.co.jp', '受講者 二郎', 3, 'TRAINEE', 2, '1996-04-04', true, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

-- ========================================
-- シーケンスの調整（次は5から）
-- ========================================
SELECT setval('public.users_id_seq', 5, false);
SELECT setval('public.companies_id_seq', 3, false);
