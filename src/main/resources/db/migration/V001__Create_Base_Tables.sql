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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER REFERENCES users(id)
);

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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER REFERENCES users(id)
);

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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER REFERENCES users(id)
);

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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0 NOT NULL
);

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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0 NOT NULL
);

COMMENT ON COLUMN lecture_chapters.lecture_id IS '講義ID（lectures.id）';
COMMENT ON COLUMN lecture_chapters.chapter_number IS 'チャプター番号';
COMMENT ON COLUMN lecture_chapters.title IS 'チャプタータイトル';
COMMENT ON COLUMN lecture_chapters.description IS 'チャプター説明';
COMMENT ON COLUMN lecture_chapters.duration_minutes IS 'チャプター想定時間（分）';
COMMENT ON COLUMN lecture_chapters.sort_order IS '並び順';
COMMENT ON COLUMN lecture_chapters.is_active IS '有効状態（チャプターの使用可否）';

-- Lecture goals table
CREATE TABLE lecture_goals (
    id SERIAL PRIMARY KEY,
    lecture_id INTEGER NOT NULL REFERENCES lectures(id),
    goal_description TEXT NOT NULL,
    sort_order INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0 NOT NULL
);

COMMENT ON COLUMN lecture_goals.lecture_id IS '講義ID（lectures.id）';
COMMENT ON COLUMN lecture_goals.goal_description IS '学習目標の説明';
COMMENT ON COLUMN lecture_goals.sort_order IS '並び順';

-- Lecture content blocks table
CREATE TABLE lecture_content_blocks (
    id SERIAL PRIMARY KEY,
    chapter_id INTEGER NOT NULL REFERENCES lecture_chapters(id),
    block_type VARCHAR(50) NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    sort_order INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0 NOT NULL
);

COMMENT ON COLUMN lecture_content_blocks.chapter_id IS 'チャプターID（lecture_chapters.id）';
COMMENT ON COLUMN lecture_content_blocks.block_type IS 'ブロック種別';
COMMENT ON COLUMN lecture_content_blocks.title IS 'ブロックタイトル';
COMMENT ON COLUMN lecture_content_blocks.content IS 'ブロック内容';
COMMENT ON COLUMN lecture_content_blocks.sort_order IS '並び順';

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
