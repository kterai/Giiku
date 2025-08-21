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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER REFERENCES users(id)
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER REFERENCES users(id)
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER REFERENCES users(id)
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER REFERENCES users(id)
);

COMMENT ON COLUMN mock_test_questions.question_text IS '問題文（模擬試験問題の内容）';
COMMENT ON COLUMN mock_test_questions.question_options IS '選択肢（多択問題の場合）';
COMMENT ON COLUMN mock_test_questions.correct_answer IS '正解（問題の正答）';
COMMENT ON COLUMN mock_test_questions.answer_explanation IS '解説（問題の解答説明）';
COMMENT ON COLUMN mock_test_questions.points IS '配点（問題の得点）';
COMMENT ON COLUMN mock_test_questions.question_order IS '問題順序（テスト内での順番）';
COMMENT ON COLUMN mock_test_questions.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN mock_test_questions.created_by IS '作成者（レコード作成したユーザーID）';
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
    feedback TEXT
);

COMMENT ON COLUMN exercise_submissions.student_answer IS '解答（学生の回答内容）';
COMMENT ON COLUMN exercise_submissions.is_correct IS '正誤（回答の正誤判定）';
COMMENT ON COLUMN exercise_submissions.points_earned IS '獲得点（得られた得点）';
COMMENT ON COLUMN exercise_submissions.submission_time IS '提出時刻（解答提出時刻）';
COMMENT ON COLUMN exercise_submissions.graded_at IS '採点時刻（採点完了時刻）';
COMMENT ON COLUMN exercise_submissions.graded_by IS '採点者（採点したユーザーID）';
COMMENT ON COLUMN exercise_submissions.feedback IS 'フィードバック（採点者からのコメント）';

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
    submission_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN quiz_submissions.student_answer IS '解答（学生の回答内容）';
COMMENT ON COLUMN quiz_submissions.is_correct IS '正誤（回答の正誤判定）';
COMMENT ON COLUMN quiz_submissions.points_earned IS '獲得点（得られた得点）';
COMMENT ON COLUMN quiz_submissions.time_taken IS '回答時間（秒単位）';
COMMENT ON COLUMN quiz_submissions.submission_time IS '提出時刻（解答提出時刻）';

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
    submission_time TIMESTAMP
);

COMMENT ON COLUMN mock_test_submissions.start_time IS '開始時刻（テスト開始時刻）';
COMMENT ON COLUMN mock_test_submissions.end_time IS '終了時刻（テスト終了時刻）';
COMMENT ON COLUMN mock_test_submissions.total_score IS '総得点（テストの総得点）';
COMMENT ON COLUMN mock_test_submissions.is_passed IS '合格状態（合格可否）';
COMMENT ON COLUMN mock_test_submissions.is_completed IS '完了状態（テスト完了可否）';
COMMENT ON COLUMN mock_test_submissions.submission_time IS '提出時刻（テスト提出時刻）';

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
    feedback TEXT
);

COMMENT ON COLUMN mock_test_answers.student_answer IS '解答（学生の回答内容）';
COMMENT ON COLUMN mock_test_answers.is_correct IS '正誤（回答の正誤判定）';
COMMENT ON COLUMN mock_test_answers.points_earned IS '獲得点（得られた得点）';
COMMENT ON COLUMN mock_test_answers.answer_time IS '回答時刻（問題回答時刻）';
COMMENT ON COLUMN mock_test_answers.graded_at IS '採点時刻（採点完了時刻）';
COMMENT ON COLUMN mock_test_answers.graded_by IS '採点者（採点したユーザーID）';
COMMENT ON COLUMN mock_test_answers.feedback IS 'フィードバック（採点者からのコメント）';

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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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
COMMENT ON COLUMN lecture_grades.created_at IS '作成日時（レコード作成時刻）';
COMMENT ON COLUMN lecture_grades.updated_at IS '更新日時（レコード更新時刻）';

-- Student Grade Summaries (overall progress tracking)
CREATE TABLE student_grade_summaries (
    id SERIAL PRIMARY KEY,
    training_assignment_id INTEGER NOT NULL REFERENCES training_assignments(id),
    total_exercise_score INTEGER DEFAULT 0,
    total_exercise_max_score INTEGER DEFAULT 0,
    total_quiz_score INTEGER DEFAULT 0,
    total_quiz_max_score INTEGER DEFAULT 0,
    mock_test_best_score INTEGER DEFAULT 0,
    mock_test_max_score INTEGER DEFAULT 100,
    final_grade NUMERIC(5,2),
    grade_letter VARCHAR(2),
    lectures_completed INTEGER DEFAULT 0,
    total_lectures INTEGER DEFAULT 54,
    attendance_rate NUMERIC(5,2),
    progress_percentage NUMERIC(5,2),
    last_activity_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN student_grade_summaries.total_exercise_score IS '演習総得点（全演習問題の合計得点）';
COMMENT ON COLUMN student_grade_summaries.total_exercise_max_score IS '演習総満点（全演習問題の満点）';
COMMENT ON COLUMN student_grade_summaries.total_quiz_score IS 'クイズ総得点（全クイズの合計得点）';
COMMENT ON COLUMN student_grade_summaries.total_quiz_max_score IS 'クイズ総満点（全クイズの満点）';
COMMENT ON COLUMN student_grade_summaries.mock_test_best_score IS '模擬試験最高得点（模擬試験の最高得点）';
COMMENT ON COLUMN student_grade_summaries.mock_test_max_score IS '模擬試験満点（模擬試験の満点）';
COMMENT ON COLUMN student_grade_summaries.final_grade IS '最終成績（40%演習+30%クイズ+30%模擬試験）';
COMMENT ON COLUMN student_grade_summaries.grade_letter IS '成績評価（A、B、C、D、Fの評価）';
COMMENT ON COLUMN student_grade_summaries.lectures_completed IS '完了講義数（完了した講義の数）';
COMMENT ON COLUMN student_grade_summaries.total_lectures IS '総講義数（全講義の数）';
COMMENT ON COLUMN student_grade_summaries.attendance_rate IS '出席率（出席講義数/総講義数）';
COMMENT ON COLUMN student_grade_summaries.progress_percentage IS '進捗率（完了度のパーセンテージ）';
COMMENT ON COLUMN student_grade_summaries.last_activity_date IS '最終活動日（最後の学習活動日時）';
COMMENT ON COLUMN student_grade_summaries.created_at IS '作成日時（レコード作成時刻）';
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER REFERENCES users(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER REFERENCES users(id)
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
CREATE INDEX idx_student_summaries_assignment ON student_grade_summaries(training_assignment_id);

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
    UNIQUE(training_assignment_id);

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
COMMENT ON TABLE student_grade_summaries IS '学生成績概要（学生の全体成績を管理）';
COMMENT ON TABLE grade_settings IS '成績設定（成績計算の重み設定を管理）';
