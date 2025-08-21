-- V009: Create mock_test_results table
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0
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
