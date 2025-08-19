-- V002: Create Training Management Tables
-- Creates training programs, schedules, assignments, and user role extensions
-- Supports instructor and student management with course copying functionality

-- Training Programs (course definitions that can be copied/reused)
CREATE TABLE training_programs (
    id SERIAL PRIMARY KEY,
    program_name VARCHAR(200) NOT NULL COMMENT 'プログラム名（研修プログラムの名称）',
    description TEXT COMMENT '説明（プログラムの詳細説明）',
    company_id INTEGER REFERENCES companies(id) COMMENT '会社ID（プログラムを実施する会社）',
    duration_months INTEGER DEFAULT 3 CHECK (duration_months > 0) COMMENT '期間月数（プログラムの実施期間）',
    max_students INTEGER DEFAULT 30 CHECK (max_students > 0) COMMENT '最大受講者数（プログラムの定員）',
    is_template BOOLEAN DEFAULT false COMMENT 'テンプレート（他社コピー用テンプレートか）',
    is_active BOOLEAN DEFAULT true COMMENT '有効状態（プログラムの使用可否）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）',
    updated_by INTEGER REFERENCES users(id) COMMENT '更新者（レコード更新したユーザーID）'
);

-- Training Schedules (specific scheduled instances of programs)
CREATE TABLE training_schedules (
    id SERIAL PRIMARY KEY,
    training_program_id INTEGER NOT NULL REFERENCES training_programs(id),
    schedule_name VARCHAR(200) NOT NULL COMMENT 'スケジュール名（実施スケジュールの名称）',
    start_date DATE NOT NULL COMMENT '開始日（研修開始日）',
    end_date DATE NOT NULL COMMENT '終了日（研修終了日）',
    instructor_id INTEGER REFERENCES users(id) COMMENT '講師ID（メイン講師のユーザーID）',
    status VARCHAR(20) DEFAULT 'planned' CHECK (status IN ('planned', 'active', 'completed', 'cancelled')) COMMENT 'ステータス（スケジュールの状態）',
    actual_students INTEGER DEFAULT 0 COMMENT '実際受講者数（実際の参加者数）',
    notes TEXT COMMENT '備考（スケジュールの備考情報）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）',
    updated_by INTEGER REFERENCES users(id) COMMENT '更新者（レコード更新したユーザーID）'
);

-- Instructors (extends users table for instructor-specific information)
CREATE TABLE instructors (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE REFERENCES users(id),
    instructor_code VARCHAR(50) UNIQUE COMMENT '講師コード（講師の識別コード）',
    specialties JSON COMMENT '専門分野（講師の専門分野リスト）',
    bio TEXT COMMENT '経歴（講師の経歴・プロフィール）',
    certifications JSON COMMENT '資格（講師の保有資格リスト）',
    hourly_rate DECIMAL(10,2) COMMENT '時間単価（講師の時間あたり報酬）',
    is_active BOOLEAN DEFAULT true COMMENT '有効状態（講師の活動状態）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）',
    updated_by INTEGER REFERENCES users(id) COMMENT '更新者（レコード更新したユーザーID）'
);

-- Students (extends users table for student-specific information)
CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE REFERENCES users(id),
    student_code VARCHAR(50) UNIQUE COMMENT '受講者コード（受講者の識別コード）',
    company_id INTEGER REFERENCES companies(id) COMMENT '所属会社ID（受講者の所属会社）',
    department VARCHAR(100) COMMENT '部署（受講者の所属部署）',
    position VARCHAR(100) COMMENT '役職（受講者の役職）',
    experience_years INTEGER DEFAULT 0 CHECK (experience_years >= 0) COMMENT '経験年数（プログラミング経験年数）',
    education_background VARCHAR(200) COMMENT '学歴（受講者の学歴）',
    motivation TEXT COMMENT '受講動機（受講の動機・目標）',
    is_active BOOLEAN DEFAULT true COMMENT '有効状態（受講者の活動状態）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）',
    updated_by INTEGER REFERENCES users(id) COMMENT '更新者（レコード更新したユーザーID）'
);

-- Training Assignments (assigns students to specific training schedules)
CREATE TABLE training_assignments (
    id SERIAL PRIMARY KEY,
    training_schedule_id INTEGER NOT NULL REFERENCES training_schedules(id),
    student_id INTEGER NOT NULL REFERENCES students(id),
    assignment_date DATE DEFAULT CURRENT_DATE COMMENT '配属日（研修への配属日）',
    status VARCHAR(20) DEFAULT 'assigned' CHECK (status IN ('assigned', 'active', 'completed', 'dropped', 'transferred')) COMMENT 'ステータス（配属の状態）',
    completion_date DATE COMMENT '完了日（研修完了日）',
    final_score DECIMAL(5,2) COMMENT '最終得点（研修の最終得点）',
    certificate_issued BOOLEAN DEFAULT false COMMENT '修了証発行（修了証の発行可否）',
    notes TEXT COMMENT '備考（配属に関する備考）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）',
    updated_by INTEGER REFERENCES users(id) COMMENT '更新者（レコード更新したユーザーID）'
);

-- Schedule Instructors (allows multiple instructors per schedule)
CREATE TABLE schedule_instructors (
    id SERIAL PRIMARY KEY,
    training_schedule_id INTEGER NOT NULL REFERENCES training_schedules(id),
    instructor_id INTEGER NOT NULL REFERENCES instructors(id),
    role VARCHAR(50) DEFAULT 'assistant' CHECK (role IN ('main', 'assistant', 'guest')) COMMENT '役割（講師の役割）',
    assigned_lectures JSON COMMENT '担当講義（担当する講義のリスト）',
    start_date DATE COMMENT '開始日（担当開始日）',
    end_date DATE COMMENT '終了日（担当終了日）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）'
);

-- Performance indexes for optimization
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
