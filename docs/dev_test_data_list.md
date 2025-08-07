# 開発環境用テストデータ一覧

開発環境で利用する `dev_sample_data.sql` に含まれるレコード数と主なユーザー情報を整理しました。

## テーブル別レコード数

| テーブル名 | レコード数 |
|-------------|-----------|
| departments | 25 |
| positions | 15 |
| users | 25 |
| department_responsible_users | 20 |
| application_types | 15 |
| approval_routes | 20 |
| applications | 40 |
| travel_request_details | 20 |
| application_approvals | 20 |
| application_attachments | 10 |
| audit_logs | 10 |
| expense_request_details | 5 |

## ユーザーデータ概要

| ユーザー名 (username) | 役割 | パスワード(平文) | 備考 |
|-----------------------|------|-----------------|------|
| admin, admin1-4 | ADMIN | `admin123` | 管理者アカウント |
| manager, manager1-4 | MANAGER | `manager123` | 承認者兼管理者 |
| user1, user11-14 | USER | `user123` | 一般ユーザー |
| user2, user21-24 | USER | `user234` | 一般ユーザー (承認不要) |
| approver3, approver31-34 | MANAGER | `manager123` | 承認専用アカウント |

開発環境でもパスワードは上記の平文を BCrypt で暗号化した値が登録されています。メールアドレスや ID は連番で付与されています。

## 申請種別と承認ルート

`application_types` テーブルには以下の申請種別が登録されています。承認ルートは `approval_routes` に定義されています。

| 申請種別名 | コード | 承認フロー |
|------------|-------|------------|
| 出張申請 | TRAVEL | 1. MANAGER → 2. ADMIN |
| 休暇申請 | VACATION | 1. MANAGER |
| 経費申請 | EXPENSE | 1. 部署ID 3 の MANAGER |

番号付き (`TRAVEL1` など) の申請種別もテスト用に複数登録されていますが、承認フローは上記と同様です。
