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
-- audit_logs（監査ログテーブル）定義
-- ========================================
CREATE TABLE public.audit_logs (
                                   id bigint NOT NULL,
                                   table_name character varying(100) NOT NULL,
                                   operation character varying(10) NOT NULL,
                                   record_id character varying(100),
                                   old_values text,
                                   new_values text,
                                   user_id bigint,
                                   created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                   changed_columns character varying(1000),
                                   username character varying(100),
                                   session_id character varying(100),
                                   ip_address character varying(45),
                                   user_agent character varying(500),
                                   reason character varying(1000),
                                   transaction_id character varying(100),
                                   executed_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                   application_name character varying(50),
                                   function_name character varying(100),
                                   severity_level character varying(10),
                                   event_type character varying(50)
);

COMMENT ON TABLE public.audit_logs IS '監査ログテーブル';
COMMENT ON COLUMN public.audit_logs.id IS '監査ログID';
COMMENT ON COLUMN public.audit_logs.table_name IS 'テーブル名';
COMMENT ON COLUMN public.audit_logs.operation IS '操作種別（INSERT/UPDATE/DELETE）';
COMMENT ON COLUMN public.audit_logs.record_id IS 'レコードID';
COMMENT ON COLUMN public.audit_logs.old_values IS '変更前の値（JSON形式）';
COMMENT ON COLUMN public.audit_logs.new_values IS '変更後の値（JSON形式）';
COMMENT ON COLUMN public.audit_logs.user_id IS '操作ユーザーID';
COMMENT ON COLUMN public.audit_logs.created_at IS '作成日時';
COMMENT ON COLUMN public.audit_logs.changed_columns IS '変更カラム一覧';
COMMENT ON COLUMN public.audit_logs.username IS 'ユーザー名';
COMMENT ON COLUMN public.audit_logs.session_id IS 'セッションID';
COMMENT ON COLUMN public.audit_logs.ip_address IS 'IPアドレス';
COMMENT ON COLUMN public.audit_logs.user_agent IS 'ユーザーエージェント';
COMMENT ON COLUMN public.audit_logs.reason IS '操作理由';
COMMENT ON COLUMN public.audit_logs.transaction_id IS 'トランザクションID';
COMMENT ON COLUMN public.audit_logs.executed_at IS '実行日時';
COMMENT ON COLUMN public.audit_logs.application_name IS 'アプリケーション名';
COMMENT ON COLUMN public.audit_logs.function_name IS '関数名';
COMMENT ON COLUMN public.audit_logs.severity_level IS '重要度';
COMMENT ON COLUMN public.audit_logs.event_type IS 'イベント種別';

CREATE SEQUENCE public.audit_logs_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
ALTER TABLE public.audit_logs ALTER COLUMN id SET DEFAULT nextval('public.audit_logs_id_seq'::regclass);
ALTER TABLE ONLY public.audit_logs ADD CONSTRAINT audit_logs_pkey PRIMARY KEY (id);
