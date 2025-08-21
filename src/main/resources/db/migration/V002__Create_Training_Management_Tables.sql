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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER REFERENCES users(id)
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER REFERENCES users(id)
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER REFERENCES users(id)
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER REFERENCES users(id)
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER REFERENCES users(id)
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id)
);

COMMENT ON COLUMN schedule_instructors.role IS '役割（講師の役割）';
COMMENT ON COLUMN schedule_instructors.assigned_lectures IS '担当講義（担当する講義のリスト）';
COMMENT ON COLUMN schedule_instructors.start_date IS '開始日（担当開始日）';
COMMENT ON COLUMN schedule_instructors.end_date IS '終了日（担当終了日）';
COMMENT ON COLUMN schedule_instructors.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN schedule_instructors.created_by IS '作成者（レコード作成したユーザーID）';

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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN program_schedules.program_id IS '研修プログラムID（training_programs.id）';
COMMENT ON COLUMN program_schedules.instructor_id IS '講師ID（instructors.id）';
COMMENT ON COLUMN program_schedules.start_date IS '開始日';
COMMENT ON COLUMN program_schedules.end_date IS '終了日';
COMMENT ON COLUMN program_schedules.max_students IS '最大受講者数';
COMMENT ON COLUMN program_schedules.current_students IS '現在の受講者数';
COMMENT ON COLUMN program_schedules.schedule_status IS 'スケジュール状態';

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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN daily_schedules.program_schedule_id IS 'プログラムスケジュールID（program_schedules.id）';
COMMENT ON COLUMN daily_schedules.day_id IS '日ID（days.id）';
COMMENT ON COLUMN daily_schedules.scheduled_date IS '実施日';
COMMENT ON COLUMN daily_schedules.start_time IS '開始時間';
COMMENT ON COLUMN daily_schedules.end_time IS '終了時間';
COMMENT ON COLUMN daily_schedules.classroom IS '教室';
COMMENT ON COLUMN daily_schedules.schedule_status IS 'スケジュール状態';
COMMENT ON COLUMN daily_schedules.notes IS '備考';

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
