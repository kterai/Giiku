package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 研修プログラムエンティティ
 * 研修プログラムの詳細情報を管理するエンティティ
 * 
 * @author Giiku System
 * @version 1.0
 * @since 2025-08-16
 */
@Entity
@Table(name = "training_programs", indexes = {
    @Index(name = "idx_program_code", columnList = "program_code", unique = true),
    @Index(name = "idx_company_id", columnList = "company_id"),
    @Index(name = "idx_program_status", columnList = "program_status"),
    @Index(name = "idx_start_date", columnList = "start_date"),
    @Index(name = "idx_category", columnList = "category"),
    @Index(name = "idx_level", columnList = "level")
})
/**
 * The TrainingProgram class.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TrainingProgram extends BaseEntity {

    /**
     * プログラムコード（一意識別子）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @NotBlank(message = "プログラムコードは必須です")
    @Size(max = 20, message = "プログラムコードは20文字以下で入力してください")
    @Column(name = "program_code", nullable = false, unique = true, length = 20)
    private String programCode;

    /**
     * プログラム名
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @NotBlank(message = "プログラム名は必須です")
    @Size(max = 200, message = "プログラム名は200文字以下で入力してください")
    @Column(name = "program_name", nullable = false, length = 200)
    private String programName;

    /** programName のエイリアス getter 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public String getName() { return programName; }

    /** programName のエイリアス setter 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void setName(String name) { this.programName = name; }

    /**
     * プログラム説明
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 2000, message = "プログラム説明は2000文字以下で入力してください")
    @Column(name = "description", length = 2000)
    private String description;

    /**
     * 会社ID（プログラムを提供する会社）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @NotNull(message = "会社IDは必須です")
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    /**
     * カテゴリ
     * IT_BASIC: IT基礎
     * PROGRAMMING: プログラミング
     * DATABASE: データベース
     * NETWORK: ネットワーク
     * SECURITY: セキュリティ
     * PROJECT_MANAGEMENT: プロジェクト管理
     * SOFT_SKILLS: ソフトスキル
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @NotBlank(message = "カテゴリは必須です")
    @Size(max = 50, message = "カテゴリは50文字以下で入力してください")
    @Column(name = "category", nullable = false, length = 50)
    private String category;

    /**
     * レベル
     * BEGINNER: 初級
     * INTERMEDIATE: 中級
     * ADVANCED: 上級
     * EXPERT: エキスパート
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @NotBlank(message = "レベルは必須です")
    @Size(max = 20, message = "レベルは20文字以下で入力してください")
    @Column(name = "level", nullable = false, length = 20)
    private String level;

    /**
     * プログラム状況
     * DRAFT: 下書き
     * ACTIVE: アクティブ
     * INACTIVE: 非アクティブ
     * ARCHIVED: アーカイブ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @NotBlank(message = "プログラム状況は必須です")
    @Size(max = 20, message = "プログラム状況は20文字以下で入力してください")
    @Column(name = "program_status", nullable = false, length = 20)
    private String programStatus;

    /**
     * 開始日
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "start_date")
    private LocalDate startDate;

    /**
     * 終了日
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * 期間（日数）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Min(value = 1, message = "期間は1以上で入力してください")
    @Column(name = "duration_days")
    private Integer durationDays;

    /**
     * 予定時間数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @DecimalMin(value = "0.0", message = "予定時間数は0.0以上で入力してください")
    @Column(name = "estimated_hours", precision = 5, scale = 2)
    private BigDecimal estimatedHours;

    /**
     * 最大受講者数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Min(value = 1, message = "最大受講者数は1以上で入力してください")
    @Column(name = "max_participants")
    private Integer maxParticipants;

    /**
     * 最小受講者数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Min(value = 1, message = "最小受講者数は1以上で入力してください")
    @Column(name = "min_participants")
    private Integer minParticipants = 1;

    /**
     * 現在の受講者数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Min(value = 0, message = "現在の受講者数は0以上で入力してください")
    @Column(name = "current_participants", nullable = false)
    private Integer currentParticipants = 0;

    /**
     * 料金
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @DecimalMin(value = "0.0", message = "料金は0.0以上で入力してください")
    @Column(name = "fee", precision = 10, scale = 2)
    private BigDecimal fee;

    /**
     * 通貨コード
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 3, message = "通貨コードは3文字以下で入力してください")
    @Column(name = "currency_code", length = 3)
    private String currencyCode = "JPY";

    /**
     * 前提条件
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 1000, message = "前提条件は1000文字以下で入力してください")
    @Column(name = "prerequisites", length = 1000)
    private String prerequisites;

    /**
     * 学習目標
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 2000, message = "学習目標は2000文字以下で入力してください")
    @Column(name = "learning_objectives", length = 2000)
    private String learningObjectives;

    /**
     * 修了条件
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 1000, message = "修了条件は1000文字以下で入力してください")
    @Column(name = "completion_criteria", length = 1000)
    private String completionCriteria;

    /**
     * 合格基準（パーセンテージ）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @DecimalMin(value = "0.0", message = "合格基準は0.0以上で入力してください")
    @DecimalMax(value = "100.0", message = "合格基準は100.0以下で入力してください")
    @Column(name = "pass_threshold", precision = 5, scale = 2)
    private BigDecimal passThreshold = BigDecimal.valueOf(70.0);

    /**
     * 修了証明書発行フラグ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "certificate_enabled", nullable = false)
    private Boolean certificateEnabled = true;

    /**
     * 修了証明書テンプレート
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 100, message = "修了証明書テンプレートは100文字以下で入力してください")
    @Column(name = "certificate_template", length = 100)
    private String certificateTemplate;

    /**
     * タグ（検索用、カンマ区切り）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 500, message = "タグは500文字以下で入力してください")
    @Column(name = "tags", length = 500)
    private String tags;

    /**
     * 作成者ユーザーID
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    /**
     * 承認者ユーザーID
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "approved_by_user_id")
    private Long approvedByUserId;

    /**
     * 承認日
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "approved_date")
    private LocalDate approvedDate;

    // デフォルトコンストラクタ
    /**
     * TrainingProgram メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public TrainingProgram() {
        super();
        this.programStatus = "DRAFT";
        this.currentParticipants = 0;
        this.minParticipants = 1;
        this.currencyCode = "JPY";
        this.passThreshold = BigDecimal.valueOf(70.0);
        this.certificateEnabled = true;
    }

    // コンストラクタ（必須フィールド）
    /**
     * TrainingProgram メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public TrainingProgram(String programCode, String programName, Long companyId, String category, String level) {
        this();
        this.programCode = programCode;
        this.programName = programName;
        this.companyId = companyId;
        this.category = category;
        this.level = level;
    }

    /**
     * カテゴリの列挙型定数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static class Category {
        public static final String IT_BASIC = "IT_BASIC";
        public static final String PROGRAMMING = "PROGRAMMING";
        public static final String DATABASE = "DATABASE";
        public static final String NETWORK = "NETWORK";
        public static final String SECURITY = "SECURITY";
        public static final String PROJECT_MANAGEMENT = "PROJECT_MANAGEMENT";
        public static final String SOFT_SKILLS = "SOFT_SKILLS";
    }

    /**
     * レベルの列挙型定数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static class Level {
        public static final String BEGINNER = "BEGINNER";
        public static final String INTERMEDIATE = "INTERMEDIATE";
        public static final String ADVANCED = "ADVANCED";
        public static final String EXPERT = "EXPERT";
    }

    /**
     * プログラム状況の列挙型定数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static class ProgramStatus {
        public static final String DRAFT = "DRAFT";
        public static final String ACTIVE = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
        public static final String ARCHIVED = "ARCHIVED";
    }

    /**
     * アクティブかどうかを判定
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public boolean isActive() {
        return ProgramStatus.ACTIVE.equals(this.programStatus);
    }

    /**
     * 受講可能かどうかを判定
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public boolean isEnrollable() {
        return isActive() && 
               (this.maxParticipants == null || this.currentParticipants < this.maxParticipants);
    }

    /**
     * 開催可能かどうかを判定（最小受講者数チェック）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public boolean canStart() {
        return isActive() && 
               this.currentParticipants >= this.minParticipants;
    }

    /**
     * 受講者数を増加
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void incrementParticipants() {
        this.currentParticipants++;
    }

    /**
     * 受講者数を減少
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void decrementParticipants() {
        if (this.currentParticipants > 0) {
            this.currentParticipants--;
        }
    }

    /**
     * 残り受講可能人数を取得
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public Integer getRemainingCapacity() {
        if (this.maxParticipants == null) {
            return null;
        }
        return Math.max(0, this.maxParticipants - this.currentParticipants);
    }

    /**
     * プログラムを承認
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void approve(Long approvedByUserId) {
        this.programStatus = ProgramStatus.ACTIVE;
        this.approvedByUserId = approvedByUserId;
        this.approvedDate = LocalDate.now();
    }

    /**
     * equals メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        TrainingProgram that = (TrainingProgram) obj;
        return Objects.equals(programCode, that.programCode);
    }

    /**
     * hashCode メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), programCode);
    }

    /**
     * toString メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    @Override
    public String toString() {
        return "TrainingProgram{" +
               "id=" + getId() +
               ", programCode='" + programCode + "'" +
               ", programName='" + programName + "'" +
               ", companyId=" + companyId +
               ", category='" + category + "'" +
               ", level='" + level + "'" +
               ", programStatus='" + programStatus + "'" +
               ", currentParticipants=" + currentParticipants +
               ", maxParticipants=" + maxParticipants +
               "}";
    }
}
