# データベースカラム配置ルール

各テーブルの管理情報カラム（`created_by`, `created_at`, `updated_by`, `updated_at`）は必ず最後に配置します。その他の業務カラムを定義した後、監査目的のカラムを末尾へ追加することでDDLの可読性と拡張性を保ちます。

## 本書の目的

DDL 作成時のカラム並び順を統一し、チーム内での開発効率を高めることが目的です。例示した配置を参考に、既存テーブルの見直しや新規テーブル作成時の指針としてください。

また、全てのテーブルはサロゲートキー `id` を主キーとし、外部キーのカラム名は `<テーブル名>_id` に統一します。

例:
```sql
CREATE TABLE sample_table (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
```
管理カラムを追加・更新する際も順序を崩さないよう注意してください。
