-- V000: Create Users and Companies tables migrated from schema.sql

-- ========================================
-- companies（会社マスタ）テーブル定義
-- ========================================
CREATE TABLE public.companies (
                                  id bigint NOT NULL,
                                  name character varying(100) NOT NULL,
                                  code character varying(20) UNIQUE,
                                  active boolean DEFAULT true NOT NULL,
                                  created_by bigint NOT NULL,
                                  created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
                                  updated_by bigint NOT NULL,
                                  updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE public.companies IS '会社マスタ';
COMMENT ON COLUMN public.companies.id IS '会社ID';
COMMENT ON COLUMN public.companies.name IS '会社名';
COMMENT ON COLUMN public.companies.code IS '会社コード';
COMMENT ON COLUMN public.companies.active IS '有効フラグ';
COMMENT ON COLUMN public.companies.created_by IS '作成者ID';
COMMENT ON COLUMN public.companies.created_at IS '作成日時';
COMMENT ON COLUMN public.companies.updated_by IS '更新者ID';
COMMENT ON COLUMN public.companies.updated_at IS '更新日時';

CREATE SEQUENCE public.companies_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER TABLE public.companies ALTER COLUMN id SET DEFAULT nextval('public.companies_id_seq'::regclass);
ALTER TABLE ONLY public.companies ADD CONSTRAINT companies_pkey PRIMARY KEY (id);

-- ========================================
-- users（ユーザーマスタ）テーブル定義
-- ========================================
CREATE TABLE public.users (
                              id bigint NOT NULL,
                              username character varying(50) NOT NULL,
                              password character varying(255) NOT NULL,
                              email character varying(255) NOT NULL,
                              name character varying(100) NOT NULL,
                              company_id bigint NOT NULL,
                              role character varying(20) NOT NULL CHECK (role IN ('ADMIN', 'INSTRUCTOR', 'TRAINEE')),
                              gender smallint NOT NULL CHECK (gender IN (1, 2, 3)),
                              birthday date NOT NULL DEFAULT DATE '1999-12-22',
                              active boolean DEFAULT true NOT NULL,
                              created_by bigint NOT NULL,
                              created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
                              updated_by bigint NOT NULL,
                              updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE public.users IS 'ユーザーマスタ';
COMMENT ON COLUMN public.users.id IS 'ユーザーID';
COMMENT ON COLUMN public.users.username IS 'ユーザー名';
COMMENT ON COLUMN public.users.password IS 'パスワード（ハッシュ化）';
COMMENT ON COLUMN public.users.email IS 'メールアドレス';
COMMENT ON COLUMN public.users.name IS '氏名';
COMMENT ON COLUMN public.users.company_id IS '会社ID';
COMMENT ON COLUMN public.users.role IS 'ロール（ADMIN/INSTRUCTOR/TRAINEE）';
COMMENT ON COLUMN public.users.gender IS '性別（1:男性、2:女性、3:無選択）';
COMMENT ON COLUMN public.users.birthday IS '誕生日';
COMMENT ON COLUMN public.users.active IS '有効フラグ';
COMMENT ON COLUMN public.users.created_by IS '作成者ID';
COMMENT ON COLUMN public.users.created_at IS '作成日時';
COMMENT ON COLUMN public.users.updated_by IS '更新者ID';
COMMENT ON COLUMN public.users.updated_at IS '更新日時';

CREATE SEQUENCE public.users_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER TABLE public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
ALTER TABLE ONLY public.users ADD CONSTRAINT users_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.users ADD CONSTRAINT users_email_key UNIQUE (email);
ALTER TABLE ONLY public.users ADD CONSTRAINT users_username_key UNIQUE (username);
ALTER TABLE ONLY public.users ADD CONSTRAINT fk_users_company_id
    FOREIGN KEY (company_id) REFERENCES public.companies(id) DEFERRABLE INITIALLY DEFERRED;

CREATE FUNCTION public.update_updated_at_column() RETURNS trigger
    LANGUAGE plpgsql AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$;

CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON public.users
    FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();


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
    (4, 'trainee_ext', '$2b$12$wSzqgNX0.nsNqrAvyGyJbudYp8wkjOdv52kZTSSFz0Sj2gCA7v4I2', 'trainee2@trainee.co.jp', '受講者 二郎', 2, 'TRAINEE', 3, '1996-04-04', true, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP),
    (5, 'm_hatae', '$2b$12$piaHg8VUkzYmYvp9XR22QuJjtQ4VZIntmXgpQAzXaJHYak0SJc8PS', 'm_hatae@apsa.co.jp', '波多江 桃子', 1, 'TRAINEE', 2, '1996-04-04', true, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);

-- ========================================
-- シーケンスの調整（次は5から）
-- ========================================
SELECT setval('public.users_id_seq', 6, false);
SELECT setval('public.companies_id_seq', 3, false);
