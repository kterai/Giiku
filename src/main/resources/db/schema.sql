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

COMMENT ON TABLE public.companies IS '会社マスタ（会社情報を管理）';
COMMENT ON COLUMN public.companies.id IS '会社ID（連番）';
COMMENT ON COLUMN public.companies.name IS '会社名（会社の名称）';
COMMENT ON COLUMN public.companies.code IS '会社コード（ユニークな識別コード）';
COMMENT ON COLUMN public.companies.active IS '有効フラグ（利用可否）';
COMMENT ON COLUMN public.companies.created_by IS '作成者ID（レコード作成ユーザー）';
COMMENT ON COLUMN public.companies.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN public.companies.updated_by IS '更新者ID（最終更新ユーザー）';
COMMENT ON COLUMN public.companies.updated_at IS '更新日時（最終更新時刻）';

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

COMMENT ON TABLE public.users IS 'ユーザーマスタ（ユーザー情報を管理）';
COMMENT ON COLUMN public.users.id IS 'ユーザーID（連番）';
COMMENT ON COLUMN public.users.username IS 'ユーザー名（ログインID）';
COMMENT ON COLUMN public.users.password IS 'パスワード（ハッシュ化済み）';
COMMENT ON COLUMN public.users.email IS 'メールアドレス（連絡先）';
COMMENT ON COLUMN public.users.name IS '氏名（フルネーム）';
COMMENT ON COLUMN public.users.company_id IS '会社ID（所属会社）';
COMMENT ON COLUMN public.users.role IS 'ロール（利用者権限：ADMIN/INSTRUCTOR/TRAINEE）';
COMMENT ON COLUMN public.users.gender IS '性別（1:男性、2:女性、3:無選択）';
COMMENT ON COLUMN public.users.birthday IS '誕生日（生年月日）';
COMMENT ON COLUMN public.users.active IS '有効フラグ（利用可否）';
COMMENT ON COLUMN public.users.created_by IS '作成者ID（レコード作成ユーザー）';
COMMENT ON COLUMN public.users.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN public.users.updated_by IS '更新者ID（最終更新ユーザー）';
COMMENT ON COLUMN public.users.updated_at IS '更新日時（最終更新時刻）';

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
-- user_roles（ユーザーロール）テーブル定義
-- ========================================
CREATE TABLE user_roles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    role_name VARCHAR(50) NOT NULL,
    company_id BIGINT REFERENCES companies(id),
    role_description VARCHAR(255),
    permission_level INTEGER NOT NULL CHECK (permission_level BETWEEN 1 AND 5),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    valid_from TIMESTAMP WITH TIME ZONE,
    valid_until TIMESTAMP WITH TIME ZONE,
    granted_by_user_id BIGINT REFERENCES users(id),
    special_permissions VARCHAR(1000),
    notes VARCHAR(500),
    version BIGINT DEFAULT 0 NOT NULL,
    created_by BIGINT REFERENCES users(id) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id) NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Indexes
CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX idx_user_roles_role_name ON user_roles(role_name);
CREATE INDEX idx_user_roles_company_id ON user_roles(company_id);
CREATE INDEX idx_user_roles_active ON user_roles(active);
CREATE UNIQUE INDEX idx_user_roles_user_role_unique ON user_roles(user_id, role_name);

-- Comments
COMMENT ON TABLE user_roles IS 'ユーザーロール（ユーザーごとのロール情報）';
COMMENT ON COLUMN user_roles.id IS 'ユーザーロールID（連番）';
COMMENT ON COLUMN user_roles.user_id IS 'ユーザーID（users.id）';
COMMENT ON COLUMN user_roles.role_name IS 'ロール名';
COMMENT ON COLUMN user_roles.company_id IS '会社ID（適用される会社）';
COMMENT ON COLUMN user_roles.role_description IS 'ロールの説明';
COMMENT ON COLUMN user_roles.permission_level IS '権限レベル';
COMMENT ON COLUMN user_roles.active IS 'アクティブ状態';
COMMENT ON COLUMN user_roles.valid_from IS '有効開始日時';
COMMENT ON COLUMN user_roles.valid_until IS '有効終了日時';
COMMENT ON COLUMN user_roles.granted_by_user_id IS '付与者ユーザーID';
COMMENT ON COLUMN user_roles.special_permissions IS '特別権限';
COMMENT ON COLUMN user_roles.notes IS '備考';
COMMENT ON COLUMN user_roles.version IS 'バージョン（楽観ロック用）';
COMMENT ON COLUMN user_roles.created_by IS '作成者ID（レコード作成ユーザー）';
COMMENT ON COLUMN user_roles.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN user_roles.updated_by IS '更新者ID（最終更新ユーザー）';
COMMENT ON COLUMN user_roles.updated_at IS '更新日時（最終更新時刻）';

-- ========================================
-- V001: Create Base Tables for Learning Management System
-- Creates core hierarchy: months -> weeks -> days -> lectures
-- Preserves existing users and companies tables
-- Months table (12 months total)
CREATE TABLE months (
    id SERIAL PRIMARY KEY,
    month_number INTEGER NOT NULL UNIQUE CHECK (month_number BETWEEN 1 AND 12),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    objectives TEXT,
    duration_weeks INTEGER,
    start_date DATE,
    end_date DATE,
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id) NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE months IS '月マスタ（学習カリキュラムの月情報を管理）';
COMMENT ON COLUMN months.id IS '月ID（連番）';
COMMENT ON COLUMN months.month_number IS '月番号（1-12の月番号）';
COMMENT ON COLUMN months.title IS '月タイトル（表示名）';
COMMENT ON COLUMN months.description IS '説明（月の学習内容説明）';
COMMENT ON COLUMN months.objectives IS '学習目標（その月で達成するべき内容）';
COMMENT ON COLUMN months.duration_weeks IS '学習期間（週数）';
COMMENT ON COLUMN months.start_date IS '開始日（月の開始予定日）';
COMMENT ON COLUMN months.end_date IS '終了日（月の終了予定日）';
COMMENT ON COLUMN months.is_active IS '有効状態（月の使用可否）';
COMMENT ON COLUMN months.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN months.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN months.updated_at IS '更新日時（レコード更新時刻）';
COMMENT ON COLUMN months.updated_by IS '更新者（レコード更新したユーザーID）';

-- Weeks table (18 weeks total, 6 weeks per month)
CREATE TABLE weeks (
    id SERIAL PRIMARY KEY,
    month_id INTEGER NOT NULL REFERENCES months(id),
    week_number INTEGER NOT NULL CHECK (week_number >= 1 AND week_number <= 18),
    week_name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id) NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE weeks IS '週マスタ（月に属する週情報を管理）';
COMMENT ON COLUMN weeks.id IS '週ID（連番）';
COMMENT ON COLUMN weeks.month_id IS '月ID（紐づく月）';
COMMENT ON COLUMN weeks.week_number IS '週番号（1-18の週番号）';
COMMENT ON COLUMN weeks.week_name IS '週名称（週の表示名）';
COMMENT ON COLUMN weeks.description IS '説明（週の学習内容説明）';
COMMENT ON COLUMN weeks.start_date IS '開始日（週の開始予定日）';
COMMENT ON COLUMN weeks.end_date IS '終了日（週の終了予定日）';
COMMENT ON COLUMN weeks.is_active IS '有効状態（週の使用可否）';
COMMENT ON COLUMN weeks.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN weeks.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN weeks.updated_at IS '更新日時（レコード更新時刻）';
COMMENT ON COLUMN weeks.updated_by IS '更新者（レコード更新したユーザーID）';

-- Days table (54 days total, 3 days per week)  
CREATE TABLE days (
    id SERIAL PRIMARY KEY,
    week_id INTEGER NOT NULL REFERENCES weeks(id),
    day_number INTEGER NOT NULL CHECK (day_number >= 1 AND day_number <= 54),
    day_name VARCHAR(100) NOT NULL,
    description TEXT,
    scheduled_date DATE,
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id) NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE days IS '日マスタ（週に属する日情報を管理）';
COMMENT ON COLUMN days.id IS '日ID（連番）';
COMMENT ON COLUMN days.week_id IS '週ID（紐づく週）';
COMMENT ON COLUMN days.day_number IS '日番号（1-54の日番号）';
COMMENT ON COLUMN days.day_name IS '日名称（日の表示名）';
COMMENT ON COLUMN days.description IS '説明（日の学習内容説明）';
COMMENT ON COLUMN days.scheduled_date IS '予定日（日の実施予定日）';
COMMENT ON COLUMN days.is_active IS '有効状態（日の使用可否）';
COMMENT ON COLUMN days.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN days.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN days.updated_at IS '更新日時（レコード更新時刻）';
COMMENT ON COLUMN days.updated_by IS '更新者（レコード更新したユーザーID）';

CREATE TABLE lectures (
    id SERIAL PRIMARY KEY,
    day_id INTEGER REFERENCES days(id),
    lecture_number INTEGER,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    goals JSONB,
    content_chapters JSONB,
    content_blocks JSONB,
    duration_minutes INTEGER NOT NULL,
    difficulty_level VARCHAR(50),
    is_active BOOLEAN DEFAULT true,
    version BIGINT DEFAULT 0 NOT NULL,
    created_by BIGINT REFERENCES users(id) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id) NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE lectures IS '講義マスタ（各日の講義情報を管理）';
COMMENT ON COLUMN lectures.id IS '講義ID（連番）';
COMMENT ON COLUMN lectures.day_id IS '紐づく日ID（days.id）';
COMMENT ON COLUMN lectures.lecture_number IS '講義番号（日内での順序）';
COMMENT ON COLUMN lectures.title IS '講義タイトル（講義の題名）';
COMMENT ON COLUMN lectures.description IS '説明（講義の詳細説明）';
COMMENT ON COLUMN lectures.goals IS '学習目標（JSON配列）';
COMMENT ON COLUMN lectures.content_chapters IS 'チャプター一覧（JSON配列）';
COMMENT ON COLUMN lectures.content_blocks IS 'コンテンツブロック一覧（JSON配列）';
COMMENT ON COLUMN lectures.duration_minutes IS '想定時間（講義の予定時間（分））';
COMMENT ON COLUMN lectures.difficulty_level IS '難易度レベル（BEGINNER/INTERMEDIATE/ADVANCED）';
COMMENT ON COLUMN lectures.is_active IS '有効状態（講義の使用可否）';
COMMENT ON COLUMN lectures.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN lectures.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN lectures.updated_at IS '更新日時（レコード更新時刻）';
COMMENT ON COLUMN lectures.version IS 'バージョン（楽観ロック用）';
COMMENT ON COLUMN lectures.updated_by IS '更新者（レコード更新したユーザーID）';

-- Lecture chapters table
CREATE TABLE lecture_chapters (
    id SERIAL PRIMARY KEY,
    lecture_id INTEGER NOT NULL REFERENCES lectures(id),
    chapter_number INTEGER NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    duration_minutes INTEGER,
    sort_order INTEGER,
    is_active BOOLEAN DEFAULT true,
    version BIGINT DEFAULT 0 NOT NULL,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN lecture_chapters.lecture_id IS '講義ID（lectures.id）';
COMMENT ON COLUMN lecture_chapters.chapter_number IS 'チャプター番号';
COMMENT ON COLUMN lecture_chapters.title IS 'チャプタータイトル';
COMMENT ON COLUMN lecture_chapters.description IS 'チャプター説明';
COMMENT ON COLUMN lecture_chapters.duration_minutes IS 'チャプター想定時間（分）';
COMMENT ON COLUMN lecture_chapters.sort_order IS '並び順';
COMMENT ON COLUMN lecture_chapters.is_active IS '有効状態（チャプターの使用可否）';
COMMENT ON COLUMN lecture_chapters.version IS 'バージョン（楽観ロック用）';
COMMENT ON COLUMN lecture_chapters.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN lecture_chapters.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN lecture_chapters.updated_by IS '更新者（レコード更新したユーザーID）';
COMMENT ON COLUMN lecture_chapters.updated_at IS '更新日時（レコード更新時刻）';

-- Lecture goals table
CREATE TABLE lecture_goals (
    id SERIAL PRIMARY KEY,
    lecture_id INTEGER NOT NULL REFERENCES lectures(id),
    goal_description TEXT NOT NULL,
    sort_order INTEGER,
    version BIGINT DEFAULT 0 NOT NULL,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN lecture_goals.lecture_id IS '講義ID（lectures.id）';
COMMENT ON COLUMN lecture_goals.goal_description IS '学習目標の説明';
COMMENT ON COLUMN lecture_goals.sort_order IS '並び順';
COMMENT ON COLUMN lecture_goals.version IS 'バージョン（楽観ロック用）';
COMMENT ON COLUMN lecture_goals.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN lecture_goals.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN lecture_goals.updated_by IS '更新者（レコード更新したユーザーID）';
COMMENT ON COLUMN lecture_goals.updated_at IS '更新日時（レコード更新時刻）';

-- Lecture content blocks table
CREATE TABLE lecture_content_blocks (
    id SERIAL PRIMARY KEY,
    chapter_id INTEGER NOT NULL REFERENCES lecture_chapters(id),
    block_type VARCHAR(50) NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    sort_order INTEGER,
    version BIGINT DEFAULT 0 NOT NULL,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN lecture_content_blocks.chapter_id IS 'チャプターID（lecture_chapters.id）';
COMMENT ON COLUMN lecture_content_blocks.block_type IS 'ブロック種別';
COMMENT ON COLUMN lecture_content_blocks.title IS 'ブロックタイトル';
COMMENT ON COLUMN lecture_content_blocks.content IS 'ブロック内容';
COMMENT ON COLUMN lecture_content_blocks.sort_order IS '並び順';
COMMENT ON COLUMN lecture_content_blocks.version IS 'バージョン（楽観ロック用）';
COMMENT ON COLUMN lecture_content_blocks.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN lecture_content_blocks.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN lecture_content_blocks.updated_by IS '更新者（レコード更新したユーザーID）';
COMMENT ON COLUMN lecture_content_blocks.updated_at IS '更新日時（レコード更新時刻）';

-- Performance indexes
CREATE INDEX idx_weeks_month ON weeks(month_id);
CREATE INDEX idx_days_week ON days(week_id);
CREATE INDEX idx_lectures_day ON lectures(day_id);
CREATE INDEX idx_lectures_active ON lectures(is_active);
CREATE INDEX idx_lecture_chapters_lecture ON lecture_chapters(lecture_id);
CREATE INDEX idx_lecture_goals_lecture ON lecture_goals(lecture_id);
CREATE INDEX idx_content_blocks_chapter ON lecture_content_blocks(chapter_id);

-- Unique constraints
ALTER TABLE weeks ADD CONSTRAINT unique_week_per_month UNIQUE(month_id, week_number);
ALTER TABLE days ADD CONSTRAINT unique_day_per_week UNIQUE(week_id, day_number);

-- Comments on tables
COMMENT ON TABLE months IS '月テーブル（3ヶ月のカリキュラム月を管理）';
COMMENT ON TABLE weeks IS '週テーブル（18週のカリキュラム週を管理）';
COMMENT ON TABLE days IS '日テーブル（54日のカリキュラム日を管理）';
COMMENT ON TABLE lectures IS '講義テーブル（講義の基本情報を管理）';
COMMENT ON TABLE lecture_chapters IS '講義チャプターテーブル（講義内の章情報を管理）';
COMMENT ON TABLE lecture_goals IS '講義目標テーブル（講義の学習目標を管理）';
COMMENT ON TABLE lecture_content_blocks IS '講義コンテンツブロックテーブル（チャプター内のコンテンツを管理）';
-- V002: Create Training Management Tables
-- Creates training programs, schedules, assignments, and user role extensions
-- Supports instructor and student management with course copying functionality

-- Training Programs (course definitions that can be copied/reused)
CREATE TABLE training_programs (
    id SERIAL PRIMARY KEY,
    program_name VARCHAR(200) NOT NULL,
    description TEXT,
    company_id INTEGER REFERENCES companies(id),
    duration_months INTEGER DEFAULT 3 CHECK (duration_months > 0),
    total_hours INTEGER DEFAULT 0,
    difficulty_level VARCHAR(20),
    prerequisites TEXT,
    max_students INTEGER DEFAULT 30 CHECK (max_students > 0),
    is_template BOOLEAN DEFAULT false,
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN training_programs.program_name IS 'プログラム名（研修プログラムの名称）';
COMMENT ON COLUMN training_programs.description IS '説明（プログラムの詳細説明）';
COMMENT ON COLUMN training_programs.company_id IS '会社ID（プログラムを実施する会社）';
COMMENT ON COLUMN training_programs.duration_months IS '期間月数（プログラムの実施期間）';
COMMENT ON COLUMN training_programs.total_hours IS '総学習時間（総時間数）';
COMMENT ON COLUMN training_programs.difficulty_level IS '難易度レベル';
COMMENT ON COLUMN training_programs.prerequisites IS '受講前提条件（事前に必要なスキル）';
COMMENT ON COLUMN training_programs.max_students IS '最大受講者数（プログラムの定員）';
COMMENT ON COLUMN training_programs.is_template IS 'テンプレート（他社コピー用テンプレートか）';
COMMENT ON COLUMN training_programs.is_active IS '有効状態（プログラムの使用可否）';
COMMENT ON COLUMN training_programs.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN training_programs.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN training_programs.updated_at IS '更新日時（レコード更新時刻）';
COMMENT ON COLUMN training_programs.updated_by IS '更新者（レコード更新したユーザーID）';

-- Training Schedules (specific scheduled instances of programs)
CREATE TABLE training_schedules (
    id SERIAL PRIMARY KEY,
    training_program_id INTEGER NOT NULL REFERENCES training_programs(id),
    schedule_name VARCHAR(200) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    instructor_id INTEGER REFERENCES users(id),
    status VARCHAR(20) DEFAULT 'planned' CHECK (status IN ('planned', 'active', 'completed', 'cancelled')),
    actual_students INTEGER DEFAULT 0,
    notes TEXT,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN training_schedules.schedule_name IS 'スケジュール名（実施スケジュールの名称）';
COMMENT ON COLUMN training_schedules.start_date IS '開始日（研修開始日）';
COMMENT ON COLUMN training_schedules.end_date IS '終了日（研修終了日）';
COMMENT ON COLUMN training_schedules.instructor_id IS '講師ID（メイン講師のユーザーID）';
COMMENT ON COLUMN training_schedules.status IS 'ステータス（スケジュールの状態）';
COMMENT ON COLUMN training_schedules.actual_students IS '実際受講者数（実際の参加者数）';
COMMENT ON COLUMN training_schedules.notes IS '備考（スケジュールの備考情報）';
COMMENT ON COLUMN training_schedules.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN training_schedules.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN training_schedules.updated_at IS '更新日時（レコード更新時刻）';
COMMENT ON COLUMN training_schedules.updated_by IS '更新者（レコード更新したユーザーID）';

-- Instructors (extends users table for instructor-specific information)
CREATE TABLE instructors (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE REFERENCES users(id),
    instructor_code VARCHAR(50) UNIQUE,
    specialties JSON,
    specialization TEXT,
    experience_years INTEGER,
    bio TEXT,
    certifications JSON,
    hourly_rate DECIMAL(10,2),
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN instructors.instructor_code IS '講師コード（講師の識別コード）';
COMMENT ON COLUMN instructors.specialties IS '専門分野（講師の専門分野リスト）';
COMMENT ON COLUMN instructors.specialization IS '専門分野（主な指導領域）';
COMMENT ON COLUMN instructors.experience_years IS '経験年数（講師としての実務年数）';
COMMENT ON COLUMN instructors.bio IS '経歴（講師の経歴・プロフィール）';
COMMENT ON COLUMN instructors.certifications IS '資格（講師の保有資格リスト）';
COMMENT ON COLUMN instructors.hourly_rate IS '時間単価（講師の時間あたり報酬）';
COMMENT ON COLUMN instructors.is_active IS '有効状態（講師の活動状態）';
COMMENT ON COLUMN instructors.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN instructors.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN instructors.updated_at IS '更新日時（レコード更新時刻）';
COMMENT ON COLUMN instructors.updated_by IS '更新者（レコード更新したユーザーID）';

-- Students (extends users table for student-specific information)
CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE REFERENCES users(id),
    student_code VARCHAR(50) UNIQUE,
    company_id INTEGER REFERENCES companies(id),
    department VARCHAR(100),
    position VARCHAR(100),
    experience_years INTEGER DEFAULT 0 CHECK (experience_years >= 0),
    education_background VARCHAR(200),
    motivation TEXT,
    enrollment_date DATE,
    student_status VARCHAR(20),
    learning_goals TEXT,
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN students.student_code IS '受講者コード（受講者の識別コード）';
COMMENT ON COLUMN students.company_id IS '所属会社ID（受講者の所属会社）';
COMMENT ON COLUMN students.department IS '部署（受講者の所属部署）';
COMMENT ON COLUMN students.position IS '役職（受講者の役職）';
COMMENT ON COLUMN students.experience_years IS '経験年数（プログラミング経験年数）';
COMMENT ON COLUMN students.education_background IS '学歴（受講者の学歴）';
COMMENT ON COLUMN students.motivation IS '受講動機（受講の動機・目標）';
COMMENT ON COLUMN students.enrollment_date IS '登録日（受講開始日）';
COMMENT ON COLUMN students.student_status IS '受講状態（active等）';
COMMENT ON COLUMN students.learning_goals IS '学習目標（受講者の目標）';
COMMENT ON COLUMN students.is_active IS '有効状態（受講者の活動状態）';
COMMENT ON COLUMN students.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN students.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN students.updated_at IS '更新日時（レコード更新時刻）';
COMMENT ON COLUMN students.updated_by IS '更新者（レコード更新したユーザーID）';

-- Training Assignments (assigns students to specific training schedules)
CREATE TABLE training_assignments (
    id SERIAL PRIMARY KEY,
    training_schedule_id INTEGER NOT NULL REFERENCES training_schedules(id),
    student_id INTEGER NOT NULL REFERENCES students(id),
    assignment_date DATE DEFAULT CURRENT_DATE,
    status VARCHAR(20) DEFAULT 'assigned' CHECK (status IN ('assigned', 'active', 'completed', 'dropped', 'transferred')),
    completion_date DATE,
    final_score DECIMAL(5,2),
    certificate_issued BOOLEAN DEFAULT false,
    notes TEXT,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN training_assignments.assignment_date IS '配属日（研修への配属日）';
COMMENT ON COLUMN training_assignments.status IS 'ステータス（配属の状態）';
COMMENT ON COLUMN training_assignments.completion_date IS '完了日（研修完了日）';
COMMENT ON COLUMN training_assignments.final_score IS '最終得点（研修の最終得点）';
COMMENT ON COLUMN training_assignments.certificate_issued IS '修了証発行（修了証の発行可否）';
COMMENT ON COLUMN training_assignments.notes IS '備考（配属に関する備考）';
COMMENT ON COLUMN training_assignments.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN training_assignments.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN training_assignments.updated_at IS '更新日時（レコード更新時刻）';
COMMENT ON COLUMN training_assignments.updated_by IS '更新者（レコード更新したユーザーID）';

-- Schedule Instructors (allows multiple instructors per schedule)
CREATE TABLE schedule_instructors (
    id SERIAL PRIMARY KEY,
    training_schedule_id INTEGER NOT NULL REFERENCES training_schedules(id),
    instructor_id INTEGER NOT NULL REFERENCES instructors(id),
    role VARCHAR(50) DEFAULT 'assistant' CHECK (role IN ('main', 'assistant', 'guest')),
    assigned_lectures JSON,
    start_date DATE,
    end_date DATE,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN schedule_instructors.role IS '役割（講師の役割）';
COMMENT ON COLUMN schedule_instructors.assigned_lectures IS '担当講義（担当する講義のリスト）';
COMMENT ON COLUMN schedule_instructors.start_date IS '開始日（担当開始日）';
COMMENT ON COLUMN schedule_instructors.end_date IS '終了日（担当終了日）';
COMMENT ON COLUMN schedule_instructors.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN schedule_instructors.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN schedule_instructors.updated_by IS '更新者（レコード更新したユーザーID）';
COMMENT ON COLUMN schedule_instructors.updated_at IS '更新日時（レコード更新時刻）';

-- Program Schedules (overall schedule per training program)
CREATE TABLE program_schedules (
    id SERIAL PRIMARY KEY,
    program_id INTEGER NOT NULL REFERENCES training_programs(id),
    instructor_id INTEGER REFERENCES instructors(id),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    max_students INTEGER DEFAULT 0,
    current_students INTEGER DEFAULT 0,
    schedule_status VARCHAR(20) DEFAULT 'scheduled',
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN program_schedules.program_id IS '研修プログラムID（training_programs.id）';
COMMENT ON COLUMN program_schedules.instructor_id IS '講師ID（instructors.id）';
COMMENT ON COLUMN program_schedules.start_date IS '開始日';
COMMENT ON COLUMN program_schedules.end_date IS '終了日';
COMMENT ON COLUMN program_schedules.max_students IS '最大受講者数';
COMMENT ON COLUMN program_schedules.current_students IS '現在の受講者数';
COMMENT ON COLUMN program_schedules.schedule_status IS 'スケジュール状態';
COMMENT ON COLUMN program_schedules.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN program_schedules.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN program_schedules.updated_by IS '更新者（レコード更新したユーザーID）';
COMMENT ON COLUMN program_schedules.updated_at IS '更新日時（レコード更新時刻）';

-- Daily Schedules (daily breakdown for a program schedule)
CREATE TABLE daily_schedules (
    id SERIAL PRIMARY KEY,
    program_schedule_id INTEGER NOT NULL REFERENCES program_schedules(id),
    day_id INTEGER NOT NULL REFERENCES days(id),
    scheduled_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    classroom VARCHAR(50),
    schedule_status VARCHAR(20) DEFAULT 'scheduled',
    notes TEXT,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN daily_schedules.program_schedule_id IS 'プログラムスケジュールID（program_schedules.id）';
COMMENT ON COLUMN daily_schedules.day_id IS '日ID（days.id）';
COMMENT ON COLUMN daily_schedules.scheduled_date IS '実施日';
COMMENT ON COLUMN daily_schedules.start_time IS '開始時間';
COMMENT ON COLUMN daily_schedules.end_time IS '終了時間';
COMMENT ON COLUMN daily_schedules.classroom IS '教室';
COMMENT ON COLUMN daily_schedules.schedule_status IS 'スケジュール状態';
COMMENT ON COLUMN daily_schedules.notes IS '備考';
COMMENT ON COLUMN daily_schedules.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN daily_schedules.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN daily_schedules.updated_by IS '更新者（レコード更新したユーザーID）';
COMMENT ON COLUMN daily_schedules.updated_at IS '更新日時（レコード更新時刻）';

-- Performance indexes for optimization
CREATE INDEX idx_program_schedules_program ON program_schedules(program_id);
CREATE INDEX idx_program_schedules_instructor ON program_schedules(instructor_id);
CREATE INDEX idx_daily_schedules_program ON daily_schedules(program_schedule_id);
CREATE INDEX idx_daily_schedules_day ON daily_schedules(day_id);
CREATE INDEX idx_training_programs_company ON training_programs(company_id);
CREATE INDEX idx_training_programs_active ON training_programs(is_active);
CREATE INDEX idx_training_schedules_program ON training_schedules(training_program_id);
CREATE INDEX idx_training_schedules_instructor ON training_schedules(instructor_id);
CREATE INDEX idx_training_schedules_dates ON training_schedules(start_date, end_date);
CREATE INDEX idx_instructors_user ON instructors(user_id);
CREATE INDEX idx_instructors_active ON instructors(is_active);
CREATE INDEX idx_students_user ON students(user_id);
CREATE INDEX idx_students_company ON students(company_id);
CREATE INDEX idx_students_active ON students(is_active);
CREATE INDEX idx_training_assignments_schedule ON training_assignments(training_schedule_id);
CREATE INDEX idx_training_assignments_student ON training_assignments(student_id);
CREATE INDEX idx_training_assignments_status ON training_assignments(status);
CREATE INDEX idx_schedule_instructors_schedule ON schedule_instructors(training_schedule_id);
CREATE INDEX idx_schedule_instructors_instructor ON schedule_instructors(instructor_id);

-- Unique constraints for data integrity  
ALTER TABLE training_assignments ADD CONSTRAINT unique_student_schedule 
    UNIQUE(training_schedule_id, student_id);
ALTER TABLE schedule_instructors ADD CONSTRAINT unique_schedule_instructor_role
    UNIQUE(training_schedule_id, instructor_id, role);

-- Check constraints for data validation
ALTER TABLE program_schedules ADD CONSTRAINT check_program_schedule_dates
    CHECK (end_date >= start_date);
ALTER TABLE training_schedules ADD CONSTRAINT check_schedule_dates
    CHECK (end_date >= start_date);
ALTER TABLE training_assignments ADD CONSTRAINT check_completion_date
    CHECK (completion_date IS NULL OR completion_date >= assignment_date);
ALTER TABLE schedule_instructors ADD CONSTRAINT check_instructor_dates
    CHECK (end_date IS NULL OR end_date >= start_date);

-- Comments on tables
COMMENT ON TABLE training_programs IS '研修プログラム（研修コースの定義を管理）';
COMMENT ON TABLE training_schedules IS '研修スケジュール（プログラムの実施スケジュールを管理）';
COMMENT ON TABLE instructors IS '講師（講師の詳細情報を管理）';
COMMENT ON TABLE students IS '受講者（受講者の詳細情報を管理）';
COMMENT ON TABLE training_assignments IS '研修配属（受講者の研修配属を管理）';
COMMENT ON TABLE schedule_instructors IS 'スケジュール講師（スケジュールと講師の関係を管理）';
COMMENT ON TABLE program_schedules IS 'プログラムスケジュール（研修プログラムの日程を管理）';
COMMENT ON TABLE daily_schedules IS '日次スケジュール（各プログラムスケジュールの1日単位の予定を管理）';


        -- 模擬試験の受験結果を格納するテーブル

CREATE TABLE mock_test_results (
                                   id SERIAL PRIMARY KEY,
                                   test_id BIGINT NOT NULL,
                                   student_id BIGINT NOT NULL,
                                   company_id BIGINT NOT NULL,
                                   status VARCHAR(50) NOT NULL,
                                   score NUMERIC(5,2),
                                   start_time TIMESTAMP,
                                   end_time TIMESTAMP,
                                   time_spent_minutes INTEGER,
                                   correct_answers INTEGER,
                                   total_questions INTEGER,
                                   feedback VARCHAR(2000),
                                   is_passed BOOLEAN NOT NULL DEFAULT false,
                                   attempt_number INTEGER NOT NULL DEFAULT 1,
                                   passing_score NUMERIC(5,2),
                                   time_limit_minutes INTEGER,
                                   test_title VARCHAR(100),
                                   student_name VARCHAR(100),
                                   remarks VARCHAR(500),
                                   version BIGINT NOT NULL DEFAULT 0,
                                   created_by BIGINT REFERENCES users(id),
                                   created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                   updated_by BIGINT REFERENCES users(id),
                                   updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- インデックス
CREATE INDEX idx_mock_test_results_test_id ON mock_test_results(test_id);
CREATE INDEX idx_mock_test_results_student_id ON mock_test_results(student_id);
CREATE INDEX idx_mock_test_results_company_id ON mock_test_results(company_id);
CREATE INDEX idx_mock_test_results_status ON mock_test_results(status);
CREATE INDEX idx_mock_test_results_created_at ON mock_test_results(created_at);

-- ユニーク制約
ALTER TABLE mock_test_results
    ADD CONSTRAINT uk_test_student_attempt UNIQUE (test_id, student_id, attempt_number);
-- V003: Question Banking and Grade Management System
-- Creates comprehensive question bank, submission tracking, and grade management
-- Supports exercises, quizzes, and mock tests with detailed scoring

-- Exercise Question Bank (20+ questions per lecture)
CREATE TABLE exercise_question_bank (
    id SERIAL PRIMARY KEY,
    lecture_id INTEGER NOT NULL REFERENCES lectures(id),
    question_number INTEGER NOT NULL,
    question_type VARCHAR(20) DEFAULT 'multiple_choice' NOT NULL CHECK (question_type IN ('multiple_choice', 'essay', 'code', 'fill_blank')),
    question_text TEXT NOT NULL,
    question_options JSON,
    correct_answer TEXT,
    explanation TEXT,
    difficulty_level VARCHAR(20) DEFAULT 'basic',
    points INTEGER DEFAULT 5 CHECK (points > 0),
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN exercise_question_bank.question_number IS '問題番号（講義内での順序）';
COMMENT ON COLUMN exercise_question_bank.question_text IS '問題文（演習問題の内容）';
COMMENT ON COLUMN exercise_question_bank.question_options IS '選択肢（多択問題の場合）';
COMMENT ON COLUMN exercise_question_bank.correct_answer IS '正解（多択・穴埋め問題の正答）';
COMMENT ON COLUMN exercise_question_bank.explanation IS '解説（問題の解答説明）';
COMMENT ON COLUMN exercise_question_bank.difficulty_level IS '難易度（basic/intermediate/advanced）';
COMMENT ON COLUMN exercise_question_bank.points IS '配点（問題の得点）';
COMMENT ON COLUMN exercise_question_bank.is_active IS '有効状態（問題の使用可否）';
COMMENT ON COLUMN exercise_question_bank.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN exercise_question_bank.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN exercise_question_bank.updated_at IS '更新日時（レコード更新時刻）';
COMMENT ON COLUMN exercise_question_bank.updated_by IS '更新者（レコード更新したユーザーID）';

-- Quiz Question Bank (per lecture)
CREATE TABLE quiz_question_bank (
    id SERIAL PRIMARY KEY,
    lecture_id INTEGER NOT NULL REFERENCES lectures(id),
    question_number INTEGER NOT NULL,
    question_type VARCHAR(20) DEFAULT 'multiple_choice' NOT NULL CHECK (question_type IN ('multiple_choice', 'true_false', 'short_answer')),
    question_text TEXT NOT NULL,
    option_a TEXT,
    option_b TEXT,
    option_c TEXT,
    option_d TEXT,
    option_e TEXT,
    option_f TEXT,
    correct_answer TEXT NOT NULL,
    explanation TEXT,
    difficulty_level VARCHAR(20) DEFAULT 'basic',
    time_limit INTEGER DEFAULT 60,
    points INTEGER DEFAULT 10 CHECK (points > 0),
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN quiz_question_bank.question_number IS '問題番号（講義内での順序）';
COMMENT ON COLUMN quiz_question_bank.question_text IS '問題文（クイズ問題の内容）';
COMMENT ON COLUMN quiz_question_bank.option_a IS '選択肢A';
COMMENT ON COLUMN quiz_question_bank.option_b IS '選択肢B';
COMMENT ON COLUMN quiz_question_bank.option_c IS '選択肢C';
COMMENT ON COLUMN quiz_question_bank.option_d IS '選択肢D';
COMMENT ON COLUMN quiz_question_bank.option_e IS '選択肢E';
COMMENT ON COLUMN quiz_question_bank.option_f IS '選択肢F';
COMMENT ON COLUMN quiz_question_bank.correct_answer IS '正解（クイズ問題の正答）';
COMMENT ON COLUMN quiz_question_bank.explanation IS '解説（問題の解答説明）';
COMMENT ON COLUMN quiz_question_bank.difficulty_level IS '難易度（basic/intermediate/advanced）';
COMMENT ON COLUMN quiz_question_bank.time_limit IS '制限時間（秒単位）';
COMMENT ON COLUMN quiz_question_bank.points IS '配点（問題の得点）';
COMMENT ON COLUMN quiz_question_bank.is_active IS '有効状態（問題の使用可否）';
COMMENT ON COLUMN quiz_question_bank.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN quiz_question_bank.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN quiz_question_bank.updated_at IS '更新日時（レコード更新時刻）';
COMMENT ON COLUMN quiz_question_bank.updated_by IS '更新者（レコード更新したユーザーID）';

-- Mock Test Question Bank (comprehensive assessment)
CREATE TABLE mock_test_bank (
    id SERIAL PRIMARY KEY,
    test_name VARCHAR(200) NOT NULL,
    description TEXT,
    duration_minutes INTEGER DEFAULT 120,
    total_points INTEGER DEFAULT 100,
    difficulty_level VARCHAR(20) DEFAULT 'basic',
    passing_score INTEGER DEFAULT 60,
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN mock_test_bank.test_name IS 'テスト名（模擬試験の名称）';
COMMENT ON COLUMN mock_test_bank.description IS '説明（テストの詳細説明）';
COMMENT ON COLUMN mock_test_bank.duration_minutes IS '試験時間（分単位）';
COMMENT ON COLUMN mock_test_bank.total_points IS '総得点（テストの満点）';
COMMENT ON COLUMN mock_test_bank.difficulty_level IS '難易度（basic/intermediate/advanced）';
COMMENT ON COLUMN mock_test_bank.passing_score IS '合格点（合格に必要な得点）';
COMMENT ON COLUMN mock_test_bank.is_active IS '有効状態（テストの使用可否）';
COMMENT ON COLUMN mock_test_bank.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN mock_test_bank.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN mock_test_bank.updated_at IS '更新日時（レコード更新時刻）';
COMMENT ON COLUMN mock_test_bank.updated_by IS '更新者（レコード更新したユーザーID）';

-- Mock Test Questions (links to question bank)
CREATE TABLE mock_test_questions (
    id SERIAL PRIMARY KEY,
    mock_test_id INTEGER NOT NULL REFERENCES mock_test_bank(id),
    question_type VARCHAR(20) NOT NULL CHECK (question_type IN ('multiple_choice', 'essay', 'code', 'true_false')),
    question_text TEXT NOT NULL,
    question_options JSON,
    correct_answer TEXT,
    answer_explanation TEXT,
    points INTEGER DEFAULT 5 CHECK (points > 0),
    question_order INTEGER NOT NULL,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN mock_test_questions.question_text IS '問題文（模擬試験問題の内容）';
COMMENT ON COLUMN mock_test_questions.question_options IS '選択肢（多択問題の場合）';
COMMENT ON COLUMN mock_test_questions.correct_answer IS '正解（問題の正答）';
COMMENT ON COLUMN mock_test_questions.answer_explanation IS '解説（問題の解答説明）';
COMMENT ON COLUMN mock_test_questions.points IS '配点（問題の得点）';
COMMENT ON COLUMN mock_test_questions.question_order IS '問題順序（テスト内での順番）';
COMMENT ON COLUMN mock_test_questions.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN mock_test_questions.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN mock_test_questions.updated_by IS '更新者（レコード更新したユーザーID）';
COMMENT ON COLUMN mock_test_questions.updated_at IS '更新日時（レコード更新時刻）';
COMMENT ON COLUMN mock_test_questions.updated_by IS '更新者（レコード更新したユーザーID）';

-- Exercise Submissions (student exercise attempts)
CREATE TABLE exercise_submissions (
    id SERIAL PRIMARY KEY,
    training_assignment_id INTEGER NOT NULL REFERENCES training_assignments(id),
    lecture_id INTEGER NOT NULL REFERENCES lectures(id),
    question_id INTEGER NOT NULL REFERENCES exercise_question_bank(id),
    student_answer TEXT,
    is_correct BOOLEAN,
    points_earned INTEGER DEFAULT 0,
    submission_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    graded_at TIMESTAMP,
    graded_by INTEGER REFERENCES users(id),
    feedback TEXT,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN exercise_submissions.student_answer IS '解答（学生の回答内容）';
COMMENT ON COLUMN exercise_submissions.is_correct IS '正誤（回答の正誤判定）';
COMMENT ON COLUMN exercise_submissions.points_earned IS '獲得点（得られた得点）';
COMMENT ON COLUMN exercise_submissions.submission_time IS '提出時刻（解答提出時刻）';
COMMENT ON COLUMN exercise_submissions.graded_at IS '採点時刻（採点完了時刻）';
COMMENT ON COLUMN exercise_submissions.graded_by IS '採点者（採点したユーザーID）';
COMMENT ON COLUMN exercise_submissions.feedback IS 'フィードバック（採点者からのコメント）';
COMMENT ON COLUMN exercise_submissions.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN exercise_submissions.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN exercise_submissions.updated_by IS '更新者（レコード更新したユーザーID）';
COMMENT ON COLUMN exercise_submissions.updated_at IS '更新日時（レコード更新時刻）';

-- Quiz Submissions (student quiz attempts)
CREATE TABLE quiz_submissions (
    id SERIAL PRIMARY KEY,
    training_assignment_id INTEGER NOT NULL REFERENCES training_assignments(id),
    lecture_id INTEGER NOT NULL REFERENCES lectures(id),
    question_id INTEGER NOT NULL REFERENCES quiz_question_bank(id),
    student_answer TEXT NOT NULL,
    is_correct BOOLEAN NOT NULL,
    points_earned INTEGER DEFAULT 0,
    time_taken INTEGER,
    submission_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN quiz_submissions.student_answer IS '解答（学生の回答内容）';
COMMENT ON COLUMN quiz_submissions.is_correct IS '正誤（回答の正誤判定）';
COMMENT ON COLUMN quiz_submissions.points_earned IS '獲得点（得られた得点）';
COMMENT ON COLUMN quiz_submissions.time_taken IS '回答時間（秒単位）';
COMMENT ON COLUMN quiz_submissions.submission_time IS '提出時刻（解答提出時刻）';
COMMENT ON COLUMN quiz_submissions.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN quiz_submissions.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN quiz_submissions.updated_by IS '更新者（レコード更新したユーザーID）';
COMMENT ON COLUMN quiz_submissions.updated_at IS '更新日時（レコード更新時刻）';

-- Mock Test Submissions (student mock test attempts)
CREATE TABLE mock_test_submissions (
    id SERIAL PRIMARY KEY,
    training_assignment_id INTEGER NOT NULL REFERENCES training_assignments(id),
    mock_test_id INTEGER NOT NULL REFERENCES mock_test_bank(id),
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP,
    total_score INTEGER DEFAULT 0,
    is_passed BOOLEAN,
    is_completed BOOLEAN DEFAULT false,
    submission_time TIMESTAMP,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN mock_test_submissions.start_time IS '開始時刻（テスト開始時刻）';
COMMENT ON COLUMN mock_test_submissions.end_time IS '終了時刻（テスト終了時刻）';
COMMENT ON COLUMN mock_test_submissions.total_score IS '総得点（テストの総得点）';
COMMENT ON COLUMN mock_test_submissions.is_passed IS '合格状態（合格可否）';
COMMENT ON COLUMN mock_test_submissions.is_completed IS '完了状態（テスト完了可否）';
COMMENT ON COLUMN mock_test_submissions.submission_time IS '提出時刻（テスト提出時刻）';
COMMENT ON COLUMN mock_test_submissions.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN mock_test_submissions.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN mock_test_submissions.updated_by IS '更新者（レコード更新したユーザーID）';
COMMENT ON COLUMN mock_test_submissions.updated_at IS '更新日時（レコード更新時刻）';

-- Mock Test Question Answers (individual question responses)
CREATE TABLE mock_test_answers (
    id SERIAL PRIMARY KEY,
    mock_test_submission_id INTEGER NOT NULL REFERENCES mock_test_submissions(id),
    question_id INTEGER NOT NULL REFERENCES mock_test_questions(id),
    student_answer TEXT,
    is_correct BOOLEAN,
    points_earned INTEGER DEFAULT 0,
    answer_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    graded_at TIMESTAMP,
    graded_by INTEGER REFERENCES users(id),
    feedback TEXT,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN mock_test_answers.student_answer IS '解答（学生の回答内容）';
COMMENT ON COLUMN mock_test_answers.is_correct IS '正誤（回答の正誤判定）';
COMMENT ON COLUMN mock_test_answers.points_earned IS '獲得点（得られた得点）';
COMMENT ON COLUMN mock_test_answers.answer_time IS '回答時刻（問題回答時刻）';
COMMENT ON COLUMN mock_test_answers.graded_at IS '採点時刻（採点完了時刻）';
COMMENT ON COLUMN mock_test_answers.graded_by IS '採点者（採点したユーザーID）';
COMMENT ON COLUMN mock_test_answers.feedback IS 'フィードバック（採点者からのコメント）';
COMMENT ON COLUMN mock_test_answers.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN mock_test_answers.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN mock_test_answers.updated_by IS '更新者（レコード更新したユーザーID）';
COMMENT ON COLUMN mock_test_answers.updated_at IS '更新日時（レコード更新時刻）';

-- Lecture Grades (per lecture assessment summary)
CREATE TABLE lecture_grades (
    id SERIAL PRIMARY KEY,
    training_assignment_id INTEGER NOT NULL REFERENCES training_assignments(id),
    lecture_id INTEGER NOT NULL REFERENCES lectures(id),
    exercise_score INTEGER DEFAULT 0,
    exercise_max_score INTEGER DEFAULT 100,
    quiz_score INTEGER DEFAULT 0,
    quiz_max_score INTEGER DEFAULT 100,
    attendance_status VARCHAR(20) DEFAULT 'absent' CHECK (attendance_status IN ('present', 'absent', 'late', 'excused')),
    completion_status VARCHAR(20) DEFAULT 'not_started' CHECK (completion_status IN ('not_started', 'in_progress', 'completed')),
    completion_date TIMESTAMP,
    grade_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    instructor_feedback TEXT,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN lecture_grades.exercise_score IS '演習得点（演習問題の合計得点）';
COMMENT ON COLUMN lecture_grades.exercise_max_score IS '演習満点（演習問題の満点）';
COMMENT ON COLUMN lecture_grades.quiz_score IS 'クイズ得点（クイズの合計得点）';
COMMENT ON COLUMN lecture_grades.quiz_max_score IS 'クイズ満点（クイズの満点）';
COMMENT ON COLUMN lecture_grades.attendance_status IS '出席状況（出席の状態）';
COMMENT ON COLUMN lecture_grades.completion_status IS '完了状況（講義の進捗状態）';
COMMENT ON COLUMN lecture_grades.completion_date IS '完了日時（講義完了時刻）';
COMMENT ON COLUMN lecture_grades.grade_date IS '採点日時（成績記録時刻）';
COMMENT ON COLUMN lecture_grades.instructor_feedback IS '講師コメント（講師からのフィードバック）';
COMMENT ON COLUMN lecture_grades.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN lecture_grades.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN lecture_grades.updated_by IS '更新者（レコード更新したユーザーID）';
COMMENT ON COLUMN lecture_grades.updated_at IS '更新日時（レコード更新時刻）';

-- Student Grade Summaries (overall progress tracking)
CREATE TABLE student_grade_summaries (
    id SERIAL PRIMARY KEY,
    student_id INTEGER NOT NULL REFERENCES students(id),
    lecture_id INTEGER NOT NULL REFERENCES lectures(id),
    exercise_score NUMERIC(5,2) DEFAULT 0,
    quiz_score NUMERIC(5,2) DEFAULT 0,
    mock_test_score NUMERIC(5,2) DEFAULT 0,
    total_score NUMERIC(5,2) DEFAULT 0,
    grade_status VARCHAR(20),
    calculated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN student_grade_summaries.student_id IS '学生ID（students.id）';
COMMENT ON COLUMN student_grade_summaries.lecture_id IS '講義ID（lectures.id）';
COMMENT ON COLUMN student_grade_summaries.exercise_score IS '演習得点';
COMMENT ON COLUMN student_grade_summaries.quiz_score IS 'クイズ得点';
COMMENT ON COLUMN student_grade_summaries.mock_test_score IS '模擬試験得点';
COMMENT ON COLUMN student_grade_summaries.total_score IS '総合得点';
COMMENT ON COLUMN student_grade_summaries.grade_status IS '成績評価ステータス';
COMMENT ON COLUMN student_grade_summaries.calculated_at IS '集計日時';
COMMENT ON COLUMN student_grade_summaries.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN student_grade_summaries.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN student_grade_summaries.updated_by IS '更新者（レコード更新したユーザーID）';
COMMENT ON COLUMN student_grade_summaries.updated_at IS '更新日時（レコード更新時刻）';

-- Grade Calculation Settings (configurable scoring weights)
CREATE TABLE grade_settings (
    id SERIAL PRIMARY KEY,
    setting_name VARCHAR(100) NOT NULL UNIQUE,
    exercise_weight NUMERIC(3,2) DEFAULT 0.40 CHECK (exercise_weight BETWEEN 0 AND 1),
    quiz_weight NUMERIC(3,2) DEFAULT 0.30 CHECK (quiz_weight BETWEEN 0 AND 1),
    mock_test_weight NUMERIC(3,2) DEFAULT 0.30 CHECK (mock_test_weight BETWEEN 0 AND 1),
    passing_threshold NUMERIC(5,2) DEFAULT 60.00,
    is_active BOOLEAN DEFAULT true,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT REFERENCES users(id),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN grade_settings.setting_name IS '設定名（成績設定の名称）';
COMMENT ON COLUMN grade_settings.exercise_weight IS '演習比重（演習問題の重み）';
COMMENT ON COLUMN grade_settings.quiz_weight IS 'クイズ比重（クイズの重み）';
COMMENT ON COLUMN grade_settings.mock_test_weight IS '模擬試験比重（模擬試験の重み）';
COMMENT ON COLUMN grade_settings.passing_threshold IS '合格閾値（合格に必要な得点）';
COMMENT ON COLUMN grade_settings.is_active IS '有効状態（設定の使用可否）';
COMMENT ON COLUMN grade_settings.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN grade_settings.created_by IS '作成者（レコード作成したユーザーID）';
COMMENT ON COLUMN grade_settings.updated_at IS '更新日時（レコード更新時刻）';
COMMENT ON COLUMN grade_settings.updated_by IS '更新者（レコード更新したユーザーID）';

-- Performance Indexes for optimization
CREATE INDEX idx_exercise_questions_lecture ON exercise_question_bank(lecture_id);
CREATE INDEX idx_exercise_questions_active ON exercise_question_bank(is_active);
CREATE INDEX idx_quiz_questions_lecture ON quiz_question_bank(lecture_id);
CREATE INDEX idx_quiz_questions_active ON quiz_question_bank(is_active);
CREATE INDEX idx_mock_test_active ON mock_test_bank(is_active);
CREATE INDEX idx_exercise_submissions_assignment ON exercise_submissions(training_assignment_id);
CREATE INDEX idx_exercise_submissions_lecture ON exercise_submissions(lecture_id);
CREATE INDEX idx_quiz_submissions_assignment ON quiz_submissions(training_assignment_id);
CREATE INDEX idx_quiz_submissions_lecture ON quiz_submissions(lecture_id);
CREATE INDEX idx_mock_submissions_assignment ON mock_test_submissions(training_assignment_id);
CREATE INDEX idx_lecture_grades_assignment ON lecture_grades(training_assignment_id);
CREATE INDEX idx_lecture_grades_lecture ON lecture_grades(lecture_id);
CREATE INDEX idx_student_summaries_student ON student_grade_summaries(student_id);
CREATE INDEX idx_student_summaries_lecture ON student_grade_summaries(lecture_id);

-- Unique constraints for data integrity
ALTER TABLE exercise_question_bank ADD CONSTRAINT unique_exercise_question_order
    UNIQUE(lecture_id, question_number);
ALTER TABLE quiz_question_bank ADD CONSTRAINT unique_quiz_question_order
    UNIQUE(lecture_id, question_number);
ALTER TABLE mock_test_questions ADD CONSTRAINT unique_mock_question_order 
    UNIQUE(mock_test_id, question_order);
ALTER TABLE lecture_grades ADD CONSTRAINT unique_lecture_assignment_grade
    UNIQUE(training_assignment_id, lecture_id);
ALTER TABLE student_grade_summaries ADD CONSTRAINT unique_student_summary
    UNIQUE(student_id, lecture_id);

-- Add constraint to ensure grade weights sum to 1.0
ALTER TABLE grade_settings ADD CONSTRAINT check_weights_sum 
    CHECK (exercise_weight + quiz_weight + mock_test_weight = 1.0);

-- Comments on tables
COMMENT ON TABLE exercise_question_bank IS '演習問題バンク（各講義の演習問題を管理）';
COMMENT ON TABLE quiz_question_bank IS 'クイズ問題バンク（各講義のクイズ問題を管理）';
COMMENT ON TABLE mock_test_bank IS '模擬試験バンク（模擬試験の定義を管理）';
COMMENT ON TABLE mock_test_questions IS '模擬試験問題（模擬試験の個別問題を管理）';
COMMENT ON TABLE exercise_submissions IS '演習提出（学生の演習問題解答を管理）';
COMMENT ON TABLE quiz_submissions IS 'クイズ提出（学生のクイズ解答を管理）';
COMMENT ON TABLE mock_test_submissions IS '模擬試験提出（学生の模擬試験受験を管理）';
COMMENT ON TABLE mock_test_answers IS '模擬試験解答（模擬試験の個別問題解答を管理）';
COMMENT ON TABLE lecture_grades IS '講義成績（各講義の成績を管理）';
COMMENT ON TABLE student_grade_summaries IS '学生成績集計（学生と講義ごとの成績を管理）';
COMMENT ON TABLE grade_settings IS '成績設定（成績計算の重み設定を管理）';

-- Additional tables needed for the basic data

CREATE TABLE IF NOT EXISTS system_settings (
    id BIGSERIAL PRIMARY KEY,
    setting_key VARCHAR(100) UNIQUE NOT NULL,
    setting_value TEXT NOT NULL,
    description TEXT,
    created_by bigint NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE system_settings IS 'システム設定（キー・値形式の設定を管理）';
COMMENT ON COLUMN system_settings.id IS '設定ID（連番）';
COMMENT ON COLUMN system_settings.setting_key IS '設定キー（設定の識別子）';
COMMENT ON COLUMN system_settings.setting_value IS '設定値（設定の内容）';
COMMENT ON COLUMN system_settings.description IS '説明（設定の説明）';
COMMENT ON COLUMN system_settings.created_by IS '作成者ID（レコード作成ユーザー）';
COMMENT ON COLUMN system_settings.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN system_settings.updated_by IS '更新者ID（最終更新ユーザー）';
COMMENT ON COLUMN system_settings.updated_at IS '更新日時（最終更新時刻）';

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
    created_by bigint NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE audit_logs IS '監査ログ（操作履歴を管理）';
COMMENT ON COLUMN audit_logs.id IS 'ログID（連番）';
COMMENT ON COLUMN audit_logs.table_name IS 'テーブル名（対象テーブル）';
COMMENT ON COLUMN audit_logs.operation_type IS '操作タイプ（INSERT/UPDATE/DELETE）';
COMMENT ON COLUMN audit_logs.record_id IS 'レコードID（対象レコード）';
COMMENT ON COLUMN audit_logs.old_values IS '旧値（変更前の値）';
COMMENT ON COLUMN audit_logs.new_values IS '新値（変更後の値）';
COMMENT ON COLUMN audit_logs.changed_by IS '変更者（操作ユーザー）';
COMMENT ON COLUMN audit_logs.change_timestamp IS '変更日時（操作時刻）';
COMMENT ON COLUMN audit_logs.created_by IS '作成者ID（レコード作成ユーザー）';
COMMENT ON COLUMN audit_logs.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN audit_logs.updated_by IS '更新者ID（最終更新ユーザー）';
COMMENT ON COLUMN audit_logs.updated_at IS '更新日時（最終更新時刻）';

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
    created_by bigint NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by bigint NOT NULL,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
COMMENT ON TABLE notifications IS '通知（ユーザーへの通知を管理）';
COMMENT ON COLUMN notifications.id IS '通知ID（連番）';
COMMENT ON COLUMN notifications.user_id IS 'ユーザーID（対象ユーザー）';
COMMENT ON COLUMN notifications.notification_type IS '通知タイプ（種類）';
COMMENT ON COLUMN notifications.title IS 'タイトル（通知タイトル）';
COMMENT ON COLUMN notifications.message IS 'メッセージ（通知本文）';
COMMENT ON COLUMN notifications.is_read IS '既読フラグ（既読かどうか）';
COMMENT ON COLUMN notifications.priority IS '優先度（low/medium/high）';
COMMENT ON COLUMN notifications.expires_at IS '有効期限（通知の期限）';
COMMENT ON COLUMN notifications.created_by IS '作成者ID（レコード作成ユーザー）';
COMMENT ON COLUMN notifications.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN notifications.updated_by IS '更新者ID（最終更新ユーザー）';
COMMENT ON COLUMN notifications.updated_at IS '更新日時（最終更新時刻）';

-- Indexes for performance
CREATE INDEX IF NOT EXISTS idx_audit_logs_table_record ON audit_logs(table_name, record_id);
CREATE INDEX IF NOT EXISTS idx_notifications_user_read ON notifications(user_id, is_read);
CREATE INDEX IF NOT EXISTS idx_notifications_type_priority ON notifications(notification_type, priority);



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
    (4, 'trainee_ext', '$2b$12$wSzqgNX0.nsNqrAvyGyJbudYp8wkjOdv52kZTSSFz0Sj2gCA7v4I2', 'trainee2@trainee.co.jp', '受講者 二郎', 2, 'TRAINEE', 3, '1996-04-04', true, 1, CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP);


-- ========================================
-- シーケンスの調整（次は5から）
-- ========================================
SELECT setval('public.users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('public.companies_id_seq', (SELECT MAX(id) FROM companies));
