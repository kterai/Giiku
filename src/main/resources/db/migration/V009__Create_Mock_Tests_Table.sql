-- V009: Create Mock Tests table
-- 模擬試験管理テーブルを追加し、MockTestエンティティに対応

CREATE TABLE mock_tests (
    test_id SERIAL PRIMARY KEY,
    company_id BIGINT REFERENCES companies(id),
    program_id BIGINT NOT NULL REFERENCES training_programs(id),
    test_type VARCHAR(50) NOT NULL,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(1000),
    duration_minutes INTEGER NOT NULL,
    total_questions INTEGER NOT NULL,
    passing_score NUMERIC(5,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    max_attempts INTEGER,
    show_correct_answers BOOLEAN NOT NULL DEFAULT false,
    randomize_questions BOOLEAN NOT NULL DEFAULT false,
    time_limit_per_question INTEGER,
    difficulty_level VARCHAR(20),
    question_types VARCHAR(200),
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE,
    created_by BIGINT REFERENCES users(id),
    updated_by BIGINT REFERENCES users(id)
);

CREATE INDEX idx_mock_tests_program_id ON mock_tests(program_id);
CREATE INDEX idx_mock_tests_company_id ON mock_tests(company_id);
CREATE INDEX idx_mock_tests_status ON mock_tests(status);
CREATE INDEX idx_mock_tests_active ON mock_tests(is_active);
CREATE INDEX idx_mock_tests_created_at ON mock_tests(created_at);

COMMENT ON TABLE mock_tests IS '模擬試験（研修プログラム内のテストを管理）';
COMMENT ON COLUMN mock_tests.test_id IS 'テストID（連番）';
COMMENT ON COLUMN mock_tests.company_id IS '会社ID（実施会社）';
COMMENT ON COLUMN mock_tests.program_id IS '研修プログラムID';
COMMENT ON COLUMN mock_tests.test_type IS 'テストタイプ（PRACTICE/MIDTERM/FINAL/QUIZ/ASSESSMENT）';
COMMENT ON COLUMN mock_tests.title IS 'テストタイトル';
COMMENT ON COLUMN mock_tests.description IS '説明（テストの詳細）';
COMMENT ON COLUMN mock_tests.duration_minutes IS '制限時間（分）';
COMMENT ON COLUMN mock_tests.total_questions IS '問題数';
COMMENT ON COLUMN mock_tests.passing_score IS '合格点';
COMMENT ON COLUMN mock_tests.status IS 'ステータス';
COMMENT ON COLUMN mock_tests.is_active IS '有効フラグ';
COMMENT ON COLUMN mock_tests.max_attempts IS '最大受験回数';
COMMENT ON COLUMN mock_tests.show_correct_answers IS '正答表示フラグ';
COMMENT ON COLUMN mock_tests.randomize_questions IS '問題ランダム出題フラグ';
COMMENT ON COLUMN mock_tests.time_limit_per_question IS '問題別制限時間（秒）';
COMMENT ON COLUMN mock_tests.difficulty_level IS '難易度レベル';
COMMENT ON COLUMN mock_tests.question_types IS '問題タイプ';
COMMENT ON COLUMN mock_tests.start_date IS '開始日時';
COMMENT ON COLUMN mock_tests.end_date IS '終了日時';
COMMENT ON COLUMN mock_tests.created_at IS '作成日時';
COMMENT ON COLUMN mock_tests.updated_at IS '更新日時';
COMMENT ON COLUMN mock_tests.created_by IS '作成者ID';
COMMENT ON COLUMN mock_tests.updated_by IS '更新者ID';
