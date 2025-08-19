-- V001: Create Base Tables for Learning Management System
-- Creates core hierarchy: months -> weeks -> days -> lectures
-- Preserves existing users and companies tables

-- Months table (3 months total)
CREATE TABLE months (
    id SERIAL PRIMARY KEY,
    month_number INTEGER NOT NULL UNIQUE CHECK (month_number >= 1 AND month_number <= 3) COMMENT '月番号（1-3の月番号）',
    month_name VARCHAR(100) NOT NULL COMMENT '月名称（月の表示名）',
    description TEXT COMMENT '説明（月の学習内容説明）',
    start_date DATE COMMENT '開始日（月の開始予定日）',
    end_date DATE COMMENT '終了日（月の終了予定日）',
    is_active BOOLEAN DEFAULT true COMMENT '有効状態（月の使用可否）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）',
    updated_by INTEGER REFERENCES users(id) COMMENT '更新者（レコード更新したユーザーID）'
);

-- Weeks table (18 weeks total, 6 weeks per month)
CREATE TABLE weeks (
    id SERIAL PRIMARY KEY,
    month_id INTEGER NOT NULL REFERENCES months(id),
    week_number INTEGER NOT NULL CHECK (week_number >= 1 AND week_number <= 18) COMMENT '週番号（1-18の週番号）',
    week_name VARCHAR(100) NOT NULL COMMENT '週名称（週の表示名）',
    description TEXT COMMENT '説明（週の学習内容説明）',
    start_date DATE COMMENT '開始日（週の開始予定日）',
    end_date DATE COMMENT '終了日（週の終了予定日）',
    is_active BOOLEAN DEFAULT true COMMENT '有効状態（週の使用可否）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）',
    updated_by INTEGER REFERENCES users(id) COMMENT '更新者（レコード更新したユーザーID）'
);

-- Days table (54 days total, 3 days per week)  
CREATE TABLE days (
    id SERIAL PRIMARY KEY,
    week_id INTEGER NOT NULL REFERENCES weeks(id),
    day_number INTEGER NOT NULL CHECK (day_number >= 1 AND day_number <= 54) COMMENT '日番号（1-54の日番号）',
    day_name VARCHAR(100) NOT NULL COMMENT '日名称（日の表示名）',
    description TEXT COMMENT '説明（日の学習内容説明）',
    scheduled_date DATE COMMENT '予定日（日の実施予定日）',
    is_active BOOLEAN DEFAULT true COMMENT '有効状態（日の使用可否）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）',
    updated_by INTEGER REFERENCES users(id) COMMENT '更新者（レコード更新したユーザーID）'
);

-- Lectures table (54 lectures total, 1 lecture per day)
CREATE TABLE lectures (
    id SERIAL PRIMARY KEY,
    day_id INTEGER NOT NULL REFERENCES days(id),
    lecture_number INTEGER NOT NULL UNIQUE CHECK (lecture_number >= 1 AND lecture_number <= 54) COMMENT '講義番号（1-54の講義番号）',
    lecture_title VARCHAR(200) NOT NULL COMMENT '講義タイトル（講義の題名）',
    description TEXT COMMENT '説明（講義の詳細説明）',
    goals JSON COMMENT '学習目標（講義の学習目標リスト）',
    content_chapters JSON COMMENT '章構成（講義の章立てリスト）',
    content_blocks JSON COMMENT '内容ブロック（講義の詳細内容ブロックリスト）',
    estimated_duration INTEGER DEFAULT 180 COMMENT '予定時間（講義の予定時間（分））',
    materials_url TEXT COMMENT '教材URL（講義教材のURL）',
    video_url TEXT COMMENT '動画URL（講義動画のURL）',
    is_active BOOLEAN DEFAULT true COMMENT '有効状態（講義の使用可否）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）',
    updated_by INTEGER REFERENCES users(id) COMMENT '更新者（レコード更新したユーザーID）'
);

-- Performance indexes
CREATE INDEX idx_weeks_month ON weeks(month_id);
CREATE INDEX idx_days_week ON days(week_id);
CREATE INDEX idx_lectures_day ON lectures(day_id);
CREATE INDEX idx_lectures_number ON lectures(lecture_number);
CREATE INDEX idx_lectures_active ON lectures(is_active);

-- Unique constraints
ALTER TABLE weeks ADD CONSTRAINT unique_week_per_month UNIQUE(month_id, week_number);
ALTER TABLE days ADD CONSTRAINT unique_day_per_week UNIQUE(week_id, day_number);
ALTER TABLE lectures ADD CONSTRAINT unique_lecture_per_day UNIQUE(day_id, lecture_number);

-- Comments on tables
COMMENT ON TABLE months IS '月テーブル（3ヶ月のカリキュラム月を管理）';
COMMENT ON TABLE weeks IS '週テーブル（18週のカリキュラム週を管理）';
COMMENT ON TABLE days IS '日テーブル（54日のカリキュラム日を管理）';
COMMENT ON TABLE lectures IS '講義テーブル（54講義の詳細内容を管理）';
