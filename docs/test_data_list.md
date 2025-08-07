# テストデータ一覧

本ドキュメントでは `dev_sample_data.sql` (開発環境) と `autotest_sample_data.sql` (自動テスト環境) に含まれるテストデータをまとめます。両ファイルのレコード数や内容は共通です。

## テーブル別レコード数

| テーブル名 | レコード数 |
|------------|-----------:|
| departments | 25 |
| positions | 15 |
| users | 25 |
| department_responsible_users | 20 |
| application_types | 15 |
| approval_routes | 20 |
| applications | 40 |
| travel_request_details | 20 |
| expense_request_details | 5 |
| application_approvals | 20 |
| application_attachments | 10 |
| audit_logs | 10 |

## ユーザーデータ概要

`users` テーブルに登録される代表的なユーザーと平文パスワードは以下のとおりです。登録時は BCrypt ハッシュが保存されます。

| ユーザー名 (username) | 役割 | パスワード(平文) | 備考 |
|-----------------------|------|-----------------|------|
| admin, admin1-4 | ADMIN | `admin123` | 管理者アカウント |
| manager, manager1-4 | MANAGER | `manager123` | 承認者兼管理者 |
| user1, user11-14 | USER | `user123` | 一般ユーザー |
| user2, user21-24 | USER | `user234` | 一般ユーザー (承認不要) |
| approver3, approver31-34 | MANAGER | `manager123` | 承認専用アカウント |

ID やメールアドレスはサンプルデータ内で連番が振られています。

## 申請種別と承認ルート

`application_types` に登録されている主な申請種別と、それぞれの承認ルートは以下のとおりです。

| 申請種別名 | コード | 承認フロー |
|------------|-------|------------|
| 出張申請 | TRAVEL | 1. MANAGER → 2. ADMIN |
| 休暇申請 | VACATION | 1. MANAGER |
| 経費申請 | EXPENSE | 1. 部署ID 3 の MANAGER |

番号付き (`TRAVEL1` など) の申請種別もテスト用に複数登録されていますが、承認フローは上記の基本形に準じます。
