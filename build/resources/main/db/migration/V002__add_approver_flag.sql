ALTER TABLE users ADD COLUMN approver boolean NOT NULL DEFAULT false;
COMMENT ON COLUMN users.approver IS '承認者フラグ';
