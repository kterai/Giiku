-- V003: Question Banking and Grade Management System
-- Creates comprehensive question bank, submission tracking, and grade management
-- Supports exercises, quizzes, and mock tests with detailed scoring

-- Exercise Question Bank (20+ questions per lecture)
CREATE TABLE exercise_question_bank (
    id SERIAL PRIMARY KEY,
    lecture_id INTEGER NOT NULL REFERENCES lectures(id),
    question_type VARCHAR(20) NOT NULL CHECK (question_type IN ('multiple_choice', 'essay', 'code', 'fill_blank')),
    question_text TEXT NOT NULL COMMENT '問題文（演習問題の内容）',
    question_options JSON COMMENT '選択肢（多択問題の場合）',
    correct_answer TEXT COMMENT '正解（多択・穴埋め問題の正答）',
    answer_explanation TEXT COMMENT '解説（問題の解答説明）',
    difficulty_level INTEGER DEFAULT 1 CHECK (difficulty_level BETWEEN 1 AND 5) COMMENT '難易度（1-5の5段階評価）',
    points INTEGER DEFAULT 5 CHECK (points > 0) COMMENT '配点（問題の得点）',
    is_active BOOLEAN DEFAULT true COMMENT '有効状態（問題の使用可否）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）',
    updated_by INTEGER REFERENCES users(id) COMMENT '更新者（レコード更新したユーザーID）'
);

-- Quiz Question Bank (per lecture)
CREATE TABLE quiz_question_bank (
    id SERIAL PRIMARY KEY,
    lecture_id INTEGER NOT NULL REFERENCES lectures(id),
    question_type VARCHAR(20) NOT NULL CHECK (question_type IN ('multiple_choice', 'true_false', 'short_answer')),
    question_text TEXT NOT NULL COMMENT '問題文（クイズ問題の内容）',
    question_options JSON COMMENT '選択肢（多択問題の場合）',
    correct_answer TEXT NOT NULL COMMENT '正解（クイズ問題の正答）',
    answer_explanation TEXT COMMENT '解説（問題の解答説明）',
    time_limit INTEGER DEFAULT 60 COMMENT '制限時間（秒単位）',
    points INTEGER DEFAULT 10 CHECK (points > 0) COMMENT '配点（問題の得点）',
    is_active BOOLEAN DEFAULT true COMMENT '有効状態（問題の使用可否）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）',
    updated_by INTEGER REFERENCES users(id) COMMENT '更新者（レコード更新したユーザーID）'
);

-- Mock Test Question Bank (comprehensive assessment)
CREATE TABLE mock_test_bank (
    id SERIAL PRIMARY KEY,
    test_name VARCHAR(200) NOT NULL COMMENT 'テスト名（模擬試験の名称）',
    description TEXT COMMENT '説明（テストの詳細説明）',
    duration_minutes INTEGER DEFAULT 120 COMMENT '試験時間（分単位）',
    total_points INTEGER DEFAULT 100 COMMENT '総得点（テストの満点）',
    passing_score INTEGER DEFAULT 60 COMMENT '合格点（合格に必要な得点）',
    is_active BOOLEAN DEFAULT true COMMENT '有効状態（テストの使用可否）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）',
    updated_by INTEGER REFERENCES users(id) COMMENT '更新者（レコード更新したユーザーID）'
);

-- Mock Test Questions (links to question bank)
CREATE TABLE mock_test_questions (
    id SERIAL PRIMARY KEY,
    mock_test_id INTEGER NOT NULL REFERENCES mock_test_bank(id),
    question_type VARCHAR(20) NOT NULL CHECK (question_type IN ('multiple_choice', 'essay', 'code', 'true_false')),
    question_text TEXT NOT NULL COMMENT '問題文（模擬試験問題の内容）',
    question_options JSON COMMENT '選択肢（多択問題の場合）',
    correct_answer TEXT COMMENT '正解（問題の正答）',
    answer_explanation TEXT COMMENT '解説（問題の解答説明）',
    points INTEGER DEFAULT 5 CHECK (points > 0) COMMENT '配点（問題の得点）',
    question_order INTEGER NOT NULL COMMENT '問題順序（テスト内での順番）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）',
    updated_by INTEGER REFERENCES users(id) COMMENT '更新者（レコード更新したユーザーID）'
);

-- Exercise Submissions (student exercise attempts)
CREATE TABLE exercise_submissions (
    id SERIAL PRIMARY KEY,
    training_assignment_id INTEGER NOT NULL REFERENCES training_assignments(id),
    lecture_id INTEGER NOT NULL REFERENCES lectures(id),
    question_id INTEGER NOT NULL REFERENCES exercise_question_bank(id),
    student_answer TEXT COMMENT '解答（学生の回答内容）',
    is_correct BOOLEAN COMMENT '正誤（回答の正誤判定）',
    points_earned INTEGER DEFAULT 0 COMMENT '獲得点（得られた得点）',
    submission_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '提出時刻（解答提出時刻）',
    graded_at TIMESTAMP COMMENT '採点時刻（採点完了時刻）',
    graded_by INTEGER REFERENCES users(id) COMMENT '採点者（採点したユーザーID）',
    feedback TEXT COMMENT 'フィードバック（採点者からのコメント）'
);

-- Quiz Submissions (student quiz attempts)
CREATE TABLE quiz_submissions (
    id SERIAL PRIMARY KEY,
    training_assignment_id INTEGER NOT NULL REFERENCES training_assignments(id),
    lecture_id INTEGER NOT NULL REFERENCES lectures(id),
    question_id INTEGER NOT NULL REFERENCES quiz_question_bank(id),
    student_answer TEXT NOT NULL COMMENT '解答（学生の回答内容）',
    is_correct BOOLEAN NOT NULL COMMENT '正誤（回答の正誤判定）',
    points_earned INTEGER DEFAULT 0 COMMENT '獲得点（得られた得点）',
    time_taken INTEGER COMMENT '回答時間（秒単位）',
    submission_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '提出時刻（解答提出時刻）'
);

-- Mock Test Submissions (student mock test attempts)
CREATE TABLE mock_test_submissions (
    id SERIAL PRIMARY KEY,
    training_assignment_id INTEGER NOT NULL REFERENCES training_assignments(id),
    mock_test_id INTEGER NOT NULL REFERENCES mock_test_bank(id),
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '開始時刻（テスト開始時刻）',
    end_time TIMESTAMP COMMENT '終了時刻（テスト終了時刻）',
    total_score INTEGER DEFAULT 0 COMMENT '総得点（テストの総得点）',
    is_passed BOOLEAN COMMENT '合格状態（合格可否）',
    is_completed BOOLEAN DEFAULT false COMMENT '完了状態（テスト完了可否）',
    submission_time TIMESTAMP COMMENT '提出時刻（テスト提出時刻）'
);

-- Mock Test Question Answers (individual question responses)
CREATE TABLE mock_test_answers (
    id SERIAL PRIMARY KEY,
    mock_test_submission_id INTEGER NOT NULL REFERENCES mock_test_submissions(id),
    question_id INTEGER NOT NULL REFERENCES mock_test_questions(id),
    student_answer TEXT COMMENT '解答（学生の回答内容）',
    is_correct BOOLEAN COMMENT '正誤（回答の正誤判定）',
    points_earned INTEGER DEFAULT 0 COMMENT '獲得点（得られた得点）',
    answer_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '回答時刻（問題回答時刻）',
    graded_at TIMESTAMP COMMENT '採点時刻（採点完了時刻）',
    graded_by INTEGER REFERENCES users(id) COMMENT '採点者（採点したユーザーID）',
    feedback TEXT COMMENT 'フィードバック（採点者からのコメント）'
);

-- Lecture Grades (per lecture assessment summary)
CREATE TABLE lecture_grades (
    id SERIAL PRIMARY KEY,
    training_assignment_id INTEGER NOT NULL REFERENCES training_assignments(id),
    lecture_id INTEGER NOT NULL REFERENCES lectures(id),
    exercise_score INTEGER DEFAULT 0 COMMENT '演習得点（演習問題の合計得点）',
    exercise_max_score INTEGER DEFAULT 100 COMMENT '演習満点（演習問題の満点）',
    quiz_score INTEGER DEFAULT 0 COMMENT 'クイズ得点（クイズの合計得点）',
    quiz_max_score INTEGER DEFAULT 100 COMMENT 'クイズ満点（クイズの満点）',
    attendance_status VARCHAR(20) DEFAULT 'absent' CHECK (attendance_status IN ('present', 'absent', 'late', 'excused')) COMMENT '出席状況（出席の状態）',
    completion_status VARCHAR(20) DEFAULT 'not_started' CHECK (completion_status IN ('not_started', 'in_progress', 'completed')) COMMENT '完了状況（講義の進捗状態）',
    completion_date TIMESTAMP COMMENT '完了日時（講義完了時刻）',
    grade_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '採点日時（成績記録時刻）',
    instructor_feedback TEXT COMMENT '講師コメント（講師からのフィードバック）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）'
);

-- Student Grade Summaries (overall progress tracking)
CREATE TABLE student_grade_summaries (
    id SERIAL PRIMARY KEY,
    training_assignment_id INTEGER NOT NULL REFERENCES training_assignments(id),
    total_exercise_score INTEGER DEFAULT 0 COMMENT '演習総得点（全演習問題の合計得点）',
    total_exercise_max_score INTEGER DEFAULT 0 COMMENT '演習総満点（全演習問題の満点）',
    total_quiz_score INTEGER DEFAULT 0 COMMENT 'クイズ総得点（全クイズの合計得点）',
    total_quiz_max_score INTEGER DEFAULT 0 COMMENT 'クイズ総満点（全クイズの満点）',
    mock_test_best_score INTEGER DEFAULT 0 COMMENT '模擬試験最高得点（模擬試験の最高得点）',
    mock_test_max_score INTEGER DEFAULT 100 COMMENT '模擬試験満点（模擬試験の満点）',
    final_grade NUMERIC(5,2) COMMENT '最終成績（40%演習+30%クイズ+30%模擬試験）',
    grade_letter VARCHAR(2) COMMENT '成績評価（A、B、C、D、Fの評価）',
    lectures_completed INTEGER DEFAULT 0 COMMENT '完了講義数（完了した講義の数）',
    total_lectures INTEGER DEFAULT 54 COMMENT '総講義数（全講義の数）',
    attendance_rate NUMERIC(5,2) COMMENT '出席率（出席講義数/総講義数）',
    progress_percentage NUMERIC(5,2) COMMENT '進捗率（完了度のパーセンテージ）',
    last_activity_date TIMESTAMP COMMENT '最終活動日（最後の学習活動日時）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）'
);

-- Grade Calculation Settings (configurable scoring weights)
CREATE TABLE grade_settings (
    id SERIAL PRIMARY KEY,
    setting_name VARCHAR(100) NOT NULL UNIQUE COMMENT '設定名（成績設定の名称）',
    exercise_weight NUMERIC(3,2) DEFAULT 0.40 CHECK (exercise_weight BETWEEN 0 AND 1) COMMENT '演習比重（演習問題の重み）',
    quiz_weight NUMERIC(3,2) DEFAULT 0.30 CHECK (quiz_weight BETWEEN 0 AND 1) COMMENT 'クイズ比重（クイズの重み）',
    mock_test_weight NUMERIC(3,2) DEFAULT 0.30 CHECK (mock_test_weight BETWEEN 0 AND 1) COMMENT '模擬試験比重（模擬試験の重み）',
    passing_threshold NUMERIC(5,2) DEFAULT 60.00 COMMENT '合格閾値（合格に必要な得点）',
    is_active BOOLEAN DEFAULT true COMMENT '有効状態（設定の使用可否）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時（レコード作成時刻）',
    created_by INTEGER REFERENCES users(id) COMMENT '作成者（レコード作成したユーザーID）',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時（レコード更新時刻）',
    updated_by INTEGER REFERENCES users(id) COMMENT '更新者（レコード更新したユーザーID）'
);

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
    UNIQUE(lecture_id, question_text);
ALTER TABLE quiz_question_bank ADD CONSTRAINT unique_quiz_question_order 
    UNIQUE(lecture_id, question_text);
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
