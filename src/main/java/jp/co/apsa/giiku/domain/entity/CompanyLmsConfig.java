package jp.co.apsa.giiku.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * 企業LMS設定エンティティ
 * 既存のCompanyエンティティに関連付けられる企業専用のLMS設定情報を管理
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "company_lms_configs")
@Data
@EqualsAndHashCode(callSuper = true)
public class CompanyLmsConfig extends BaseEntity {

    // ID はBaseEntityから継承

    /**
     * 企業ID（外部キー）
     * 既存のCompanyエンティティとの1:1関係
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "company_id", nullable = false, unique = true)
    private Long companyId;

    /**
     * LMS機能有効フラグ
     * LMS機能の全体的な有効/無効を制御
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "lms_enabled", nullable = false)
    private Boolean lmsEnabled = true;

    /**
     * 最大学生数
     * この企業で登録可能な学生の上限数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Min(value = 1, message = "最大学生数は1以上である必要があります")
    @Column(name = "max_students", nullable = false)
    private Integer maxStudents = 100;

    /**
     * 最大講師数
     * この企業で登録可能な講師の上限数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Min(value = 1, message = "最大講師数は1以上である必要があります")
    @Column(name = "max_instructors", nullable = false)
    private Integer maxInstructors = 10;

    /**
     * 最大コース数
     * この企業で作成可能なコースの上限数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Min(value = 1, message = "最大コース数は1以上である必要があります")
     @Column(name = "max_courses", nullable = false)
    private Integer maxCourses = 50;

    /**
     * セルフ登録許可フラグ
     * 学生の自己登録を許可するかどうか
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "self_registration_enabled", nullable = false)
    private Boolean selfRegistrationEnabled = false;

    /**
     * 講師評価機能有効フラグ
     * 学生による講師評価機能の有効/無効
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "instructor_rating_enabled", nullable = false)
    private Boolean instructorRatingEnabled = true;

    /**
     * 学習進捗通知有効フラグ
     * 学習進捗の自動通知機能の有効/無効
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "progress_notification_enabled", nullable = false)
    private Boolean progressNotificationEnabled = true;

    /**
     * 証明書発行有効フラグ
     * コース完了証明書の発行機能の有効/無効
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "certificate_enabled", nullable = false)
    private Boolean certificateEnabled = true;

    /**
     * レポート機能有効フラグ
     * 学習分析レポート機能の有効/無効
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "reporting_enabled", nullable = false)
    private Boolean reportingEnabled = true;

    /**
     * カスタムテーマ設定
     * LMS画面のカスタムテーマ設定（JSON形式）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 2000, message = "カスタムテーマ設定は2000文字以内で入力してください")
    @Column(name = "custom_theme", length = 2000)
    private String customTheme;

    /**
     * ロゴURL
     * 企業ロゴのURL
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 500, message = "ロゴURLは500文字以内で入力してください")
    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    /**
     * タイムゾーン
     * 企業の標準タイムゾーン設定
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 50, message = "タイムゾーンは50文字以内で入力してください")
    @Column(name = "timezone", length = 50)
    private String timezone = "Asia/Tokyo";

    /**
     * 言語設定
     * システムの表示言語設定
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 10, message = "言語設定は10文字以内で入力してください")
    @Column(name = "language", length = 10)
    private String language = "ja";

    /**
     * 通知メール送信元アドレス
     * システムからの通知メールの送信元アドレス
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Email(message = "有効なメールアドレスを入力してください")
    @Size(max = 100, message = "メールアドレスは100文字以内で入力してください")
    @Column(name = "notification_email", length = 100)
    private String notificationEmail;

    /**
     * セッション有効期限（分）
     * ユーザーセッションの有効期限
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Min(value = 30, message = "セッション有効期限は30分以上である必要があります")
    @Max(value = 1440, message = "セッション有効期限は1440分（24時間）以下である必要があります")
    @Column(name = "session_timeout_minutes", nullable = false)
    private Integer sessionTimeoutMinutes = 480; // 8時間

    /**
     * パスワード有効期限（日）
     * ユーザーパスワードの有効期限
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Min(value = 30, message = "パスワード有効期限は30日以上である必要があります")
    @Max(value = 365, message = "パスワード有効期限は365日以下である必要があります")
    @Column(name = "password_expiry_days")
    private Integer passwordExpiryDays = 90;

    /**
     * ファイルアップロード最大サイズ（MB）
     * ユーザーがアップロード可能なファイルの最大サイズ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Min(value = 1, message = "最大ファイルサイズは1MB以上である必要があります")
    @Max(value = 1000, message = "最大ファイルサイズは1000MB以下である必要があります")
    @Column(name = "max_file_size_mb", nullable = false)
    private Integer maxFileSizeMb = 50;

    /**
     * バックアップ頻度
     * データバックアップの実行頻度
     * DAILY: 毎日, WEEKLY: 毎週, MONTHLY: 毎月
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Pattern(regexp = "^(DAILY|WEEKLY|MONTHLY)$", 
             message = "バックアップ頻度は DAILY, WEEKLY, MONTHLY のいずれかである必要があります")
    @Column(name = "backup_frequency", length = 20)
    private String backupFrequency = "WEEKLY";

    /**
     * 設定有効開始日時
     * この設定が有効になる開始日時
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "config_effective_from")
    private LocalDateTime configEffectiveFrom;

    /**
     * 設定有効終了日時
     * この設定が無効になる終了日時
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "config_effective_to")
    private LocalDateTime configEffectiveTo;

    /**
     * 設定説明
     * この設定に関する説明や注意事項
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 1000, message = "設定説明は1000文字以内で入力してください")
    @Column(name = "config_description", length = 1000)
    private String configDescription;

    /**
     * 設定更新日時
     * 管理用タイムスタンプ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "config_updated_at")
    private LocalDateTime configUpdatedAt;

    /**
     * エンティティ保存前処理
     * 設定更新日時を自動設定
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @PrePersist
    @PreUpdate
    protected void onConfigUpdate() {
        this.configUpdatedAt = LocalDateTime.now();
        if (this.configEffectiveFrom == null) {
            this.configEffectiveFrom = LocalDateTime.now();
        }
    }

    /**
     * LMS機能が有効かどうかを判定
     * 
     * @return LMS機能が有効でかつ設定有効期間内の場合true
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public boolean isLmsActive() {
        if (!this.lmsEnabled) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        boolean afterStart = this.configEffectiveFrom == null || now.isAfter(this.configEffectiveFrom);
        boolean beforeEnd = this.configEffectiveTo == null || now.isBefore(this.configEffectiveTo);

        return afterStart && beforeEnd;
    }

    /**
     * 現在の学生数が上限を超えているかどうかを判定
     * 
     * @param currentStudentCount 現在の学生数
     * @return 上限を超えている場合true
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public boolean isStudentLimitExceeded(int currentStudentCount) {
        return currentStudentCount >= this.maxStudents;
    }

    /**
     * 現在の講師数が上限を超えているかどうかを判定
     * 
     * @param currentInstructorCount 現在の講師数
     * @return 上限を超えている場合true
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public boolean isInstructorLimitExceeded(int currentInstructorCount) {
        return currentInstructorCount >= this.maxInstructors;
    }

    /**
     * 現在のコース数が上限を超えているかどうかを判定
     * 
     * @param currentCourseCount 現在のコース数
     * @return 上限を超えている場合true
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public boolean isCourseLimitExceeded(int currentCourseCount) {
        return currentCourseCount >= this.maxCourses;
    }

    /**
     * ファイルサイズが上限を超えているかどうかを判定
     * 
     * @param fileSizeMb ファイルサイズ（MB）
     * @return 上限を超えている場合true
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public boolean isFileSizeExceeded(double fileSizeMb) {
        return fileSizeMb > this.maxFileSizeMb;
    }

    /**
     * セッションタイムアウト時間をミリ秒で取得
     * 
     * @return セッションタイムアウト時間（ミリ秒）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public long getSessionTimeoutMillis() {
        return this.sessionTimeoutMinutes * 60L * 1000L;
    }

    /**
     * バックアップ頻度の日本語表記を取得
     * 
     * @return バックアップ頻度の日本語表記
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public String getBackupFrequencyDisplay() {
        if (this.backupFrequency == null) {
            return "未設定";
        }
        switch (this.backupFrequency) {
            case "DAILY": return "毎日";
            case "WEEKLY": return "毎週";
            case "MONTHLY": return "毎月";
            default: return "未設定";
        }
    }
}
