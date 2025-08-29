# Giiku - ITエンジニア育成プラットフォーム

🚀 **データベース駆動型の学習管理システム（LMS）で、未経験者を即戦力エンジニアに育成**

Giikuは、学習コンテンツ、受講者、講師を統合的にデータベースで管理し、動的にWebページを生成するモダンな学習プラットフォームです。デフォルトで3ヶ月の体系的カリキュラムを提供しながら、柔軟なカスタマイズが可能です。

![License](https://img.shields.io/github/license/kterai/Giiku)
![Java](https://img.shields.io/badge/Java-17+-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue)
![React](https://img.shields.io/badge/React-18+-61DAFB)

## 🎯 主な特徴

### 🗄️ フルDBドリブンアーキテクチャ
- **学習コンテンツの動的管理**: 講義、演習、テストをすべてデータベースで管理
- **ユーザー管理**: 受講者、講師、管理者の権限別機能提供
- **進捗トラッキング**: リアルタイムでの学習進捗可視化
- **カスタマイズ可能**: 学習内容や日程を企業・個人のニーズに合わせて調整

### 📚 デフォルト3ヶ月カリキュラム
1. **1ヶ月目**: Oracle Java Silver 17 試験対策
2. **2ヶ月目**: 基本情報技術者試験対策  
3. **3ヶ月目**: フルスタックWeb開発（基礎～応用）

### 🏗️ エンタープライズ対応設計
- **マルチテナント対応**: 複数企業での同時運用
- **ロールベースアクセス制御**: 管理者/講師/受講者の権限分離
- **監査ログ**: 全活動の追跡とコンプライアンス対応
- **Slack連携**: 学習進捗の自動通知

## 🛠️ 技術スタック

### バックエンド
```
Spring Boot 3.x
├── Spring Data JPA (データアクセス)
├── Spring Security (認証・認可)
├── Flyway (DBマイグレーション)
└── Lombok (ボイラープレートコード削減)
```

### フロントエンド
```
React 18+
├── Bootstrap 5 (UIフレームワーク)
├── Thymeleaf (サーバーサイドレンダリング)
└── TypeScript (型安全性)
```

### データベース・インフラ
```
PostgreSQL 15+
├── Docker & Docker Compose
├── Gradle (ビルドツール)
└── GitHub Actions (CI/CD)
```

### 品質保証・テスト
```
Testing Stack
├── JUnit 5 (単体テスト)
├── Playwright (E2Eテスト)
└── Quality Reports (コード品質測定)
```

## 🏛️ アーキテクチャ

### ドメイン駆動設計（DDD）
```
jp.co.apsa.giiku/
├── domain/          # ドメインロジック
│   ├── entity/      # エンティティ（26クラス）
│   ├── repository/  # リポジトリインターフェース
│   ├── port/        # ポート（外部システム連携）
│   └── valueobject/ # 値オブジェクト
├── application/     # アプリケーションサービス
│   └── service/     # アプリケーション層サービス
├── infrastructure/  # インフラストラクチャ層
│   ├── audit/       # 監査機能
│   ├── config/      # 設定管理
│   └── notification/# 通知機能
├── controller/      # プレゼンテーション層（22コントローラー）
│   └── admin/       # 管理者専用コントローラー
├── dto/            # データ転送オブジェクト
├── service/        # ビジネスロジック
├── exception/      # 例外処理
└── config/         # アプリケーション設定
```

### 主要エンティティ（26クラス）

#### コア学習管理
| エンティティ | 説明 |
|-------------|------|
| `TrainingProgram` | 研修プログラム全体の管理 |
| `Month` / `Week` / `Day` | 階層的カリキュラム構造 |
| `Lecture` / `LectureChapter` | 講義・章構成管理 |
| `LectureContentBlock` / `LectureGoal` | 詳細コンテンツとゴール設定 |

#### ユーザー・権限管理
| エンティティ | 説明 |
|-------------|------|
| `User` / `UserRole` | ユーザー基本情報と権限 |
| `StudentProfile` / `StudentEnrollment` | 受講者プロファイルと受講登録 |
| `Instructor` | 講師情報管理 |
| `Company` / `CompanyLmsConfig` | 企業情報とLMS設定 |

#### 学習評価・進捗
| エンティティ | 説明 |
|-------------|------|
| `Quiz` / `MockTest` | クイズとモックテスト |
| `QuestionBank` / `MockTestResult` | 問題バンクとテスト結果 |
| `LectureGrade` / `StudentGradeSummary` | 個別・総合成績管理 |

#### システム基盤
| エンティティ | 説明 |
|-------------|------|
| `BaseEntity` / `AuditableEntity` | 基底エンティティと監査機能 |
| `AuditLog` | システム操作ログ |
| `DailySchedule` / `ProgramSchedule` | 日別・プログラム別スケジュール |

### 主要コントローラー（22クラス）

#### 学習管理系
- `LectureController` - 講義管理
- `LectureChapterController` - 章管理  
- `LectureViewController` - 講義表示
- `MonthController` / `WeekController` / `DayController` - カリキュラム階層管理

#### ユーザー管理系
- `StudentController` / `StudentProfileController` - 受講者管理
- `InstructorController` - 講師管理
- `UserRoleController` - 権限管理
- `LoginController` - 認証機能

#### 評価・テスト系
- `QuizController` / `MockTestController` - テスト実行
- `LectureGradeController` - 成績管理
- `QuestionBankController` - 問題管理

#### システム管理系
- `DashboardController` - ダッシュボード
- `CompanyLmsConfigController` - 企業設定
- `TrainingProgramController` - プログラム管理
- `DailyScheduleController` / `ProgramScheduleController` - スケジュール管理
- `AbstractController` - 共通基底クラス
- `GlobalExceptionHandler` - 例外処理

## 🚀 クイックスタート

### 前提条件
- Java 17以上（推奨: Amazon Corretto）
- Docker & Docker Compose
- Git
- Node.js 16以上（フロントエンド開発時）

### セットアップ
```bash
# 1. リポジトリのクローン
git clone https://github.com/kterai/Giiku.git
cd Giiku

# 2. 環境変数の設定
cp .env.example .env
# .envファイルを編集して必要な設定を記入

# 3. データベースとアプリケーションの起動
docker-compose up -d

# 4. データベースマイグレーション（自動実行）
# Flywayによってスキーマとマスタデータが自動投入されます

# 5. アプリケーション確認
# http://localhost:8080 でアクセス可能
```

### 開発環境セットアップ
```bash
# フロントエンド依存関係のインストール
npm install

# 開発サーバーの起動（ホットリロード有効）
npm run dev

# バックエンドの開発実行
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### アクセス先
- **メインアプリケーション**: http://localhost:8080
- **管理画面**: http://localhost:8080/admin
- **API ドキュメント**: http://localhost:8080/swagger-ui.html
- **データベース管理**: http://localhost:5050 (pgAdmin)

## 📊 システム管理

### 初期データセットアップ
```sql
-- 企業の登録
INSERT INTO company (name, domain, created_at) 
VALUES ('サンプル企業', 'sample.com', NOW());

-- 管理者ユーザーの作成
INSERT INTO user (username, email, password_hash, role_id) 
VALUES ('admin', 'admin@sample.com', '$2a$10$...', 1);

-- デフォルト研修プログラムの作成
INSERT INTO training_program (name, description, duration_weeks, company_id) 
VALUES ('Java基礎コース', '3ヶ月でJavaエンジニアを育成', 12, 1);
```

### カリキュラムのカスタマイズ
```java
// カスタム講義コンテンツの追加例
@Entity
@Table(name = "lecture")
public class Lecture extends AuditableEntity {
    private String title;
    private String description;
    private Integer estimatedMinutes;
    private String difficultyLevel;

    // 企業固有のカスタムフィールド
    private String companySpecificNotes;
    private Boolean isCompanyCustomized;
}
```

### API利用例
```javascript
// 学習進捗の取得
fetch('/api/students/{studentId}/progress')
  .then(response => response.json())
  .then(data => {
    console.log('現在の進捗:', data.completionPercentage);
  });

// 新しい講義の作成
fetch('/api/lectures', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    title: 'Spring Boot入門',
    description: 'Spring Bootの基本概念',
    chapterId: 1,
    estimatedMinutes: 90
  })
});
```

## 🧪 テスト・品質管理

### テスト実行
```bash
# 全テストの実行
./gradlew test

# 単体テストのみ
./gradlew unitTest

# 統合テストのみ  
./gradlew integrationTest

# E2Eテスト（Playwright）
npm run test:e2e

# テストカバレッジレポート
./gradlew jacocoTestReport
```

### コード品質チェック
```bash
# 静的解析
./gradlew checkstyleMain

# 品質レポート生成
./gradlew sonarqube

# 依存関係の脆弱性チェック
./gradlew dependencyCheckAnalyze
```

## 📈 開発・運用状況

### システム完成度
- ✅ **コアシステム**: 100%（DB設計、API、認証・認可）
- ✅ **ユーザー管理**: 100%（マルチロール、権限制御）
- ✅ **カリキュラム管理**: 100%（動的生成、カスタマイズ対応）
- ✅ **進捗管理**: 100%（リアルタイム追跡、分析機能）
- 🔄 **UI/UX**: 継続改善中
- 📋 **コンテンツ拡充**: 進行中

### パフォーマンス指標
- **応答時間**: 平均200ms以下
- **同時接続**: 1000ユーザー対応
- **データベース**: 10万レコード以上の学習データ管理
- **可用性**: 99.9%アップタイム目標

### 最近の更新
- 2025-08-30: 講義IDで演習・クイズ問題を取得するRESTエンドポイントを追加
- 2025-08-29: 講義IDでクイズと演習問題を取得し、チャプターごとに表示
- 2025-08-26: DozerのJAXB依存関係を追加し、Java 17環境での起動エラーを解消

## 🗺️ ロードマップ

### Phase 1 (現在) - コアシステム完成
- [x] データベース設計とAPI実装
- [x] ユーザー認証・認可システム
- [x] 基本的な学習管理機能
- [x] 管理画面の実装

### Phase 2 (次期) - 高度化・自動化
- [ ] AI駆動型学習推奨システム
- [ ] 自動成績評価・フィードバック
- [ ] 学習分析ダッシュボード拡張
- [ ] モバイルアプリ対応

### Phase 3 (将来) - エンタープライズ強化
- [ ] 多言語対応（i18n）
- [ ] ビデオ学習コンテンツ統合
- [ ] 外部システム連携（HR、給与系）
- [ ] クラウドネイティブ対応

## 🔧 開発・メンテナンス

### 依存関係の更新
```bash
# Gradle依存関係の更新確認
./gradlew dependencyUpdates

# npm依存関係の更新
npm audit
npm update
```

### データベースマイグレーション
```bash
# 新しいマイグレーションファイルの作成
# src/main/resources/db/migration/V{version}__{description}.sql

# マイグレーション実行（起動時に自動実行）
./gradlew flywayMigrate

# マイグレーション情報確認
./gradlew flywayInfo
```

### 本番デプロイ
```bash
# 本番用ビルド
./gradlew bootJar

# Docker イメージ作成
docker build -t giiku:latest .

# 本番環境デプロイ（docker-compose）
docker-compose -f docker-compose.prod.yml up -d
```

## 🤝 コントリビューション

### 開発フロー
1. このリポジトリをフォーク
2. フィーチャーブランチを作成 (`git checkout -b feature/amazing-feature`)
3. 変更をコミット (`git commit -m 'Add amazing feature'`)
4. テストを実行して全て通ることを確認
5. ブランチにプッシュ (`git push origin feature/amazing-feature`)
6. プルリクエストを作成

### コーディング規約
- **Java**: Google Java Style Guide準拠
- **JavaScript/TypeScript**: Prettier + ESLint設定
- **SQL**: アッパーケース + スネークケース
- **コミットメッセージ**: Conventional Commits形式

### Issue・PR テンプレート
- バグレポート、機能要望は専用テンプレートを使用
- セキュリティに関する問題は非公開でご連絡ください

## 📚 ドキュメント

- **[API仕様書](./docs/api.md)** - REST API の詳細仕様
- **[データベース設計](./docs/database.md)** - ERD とテーブル定義
- **[セットアップガイド](./docs/setup.md)** - 詳細な環境構築手順
- **[運用マニュアル](./docs/operations.md)** - 本番環境での運用方法

## 📞 サポート・コンタクト

- **Issues**: [GitHub Issues](https://github.com/kterai/Giiku/issues) - バグ報告・機能要望
- **Discussions**: [GitHub Discussions](https://github.com/kterai/Giiku/discussions) - 質問・アイデア交換
- **Developer**: [@kterai](https://github.com/kterai) - プロジェクトオーナー
- **Email**: support@giiku-lms.com - 直接サポート

## 📜 ライセンス

このプロジェクトは [LICENSE](LICENSE) ファイルに記載されたライセンスの下で配布されています。

## 🙏 謝辞

Giikuの開発にご協力いただいた全ての貢献者の皆様に感謝いたします。

---

**Giiku** - *技術力で未来を切り拓く人材を育成する*

> システム化により、従来の静的な学習コンテンツから、
> データ駆動型の柔軟で効果的な学習体験へと進化しました。
