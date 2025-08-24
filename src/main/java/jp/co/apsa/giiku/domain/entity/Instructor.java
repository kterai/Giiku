package jp.co.apsa.giiku.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 講師プロファイル拡張エンティティ
 * 既存のUserエンティティに関連付けられる講師専用の情報を管理
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "instructors")
@Data
@EqualsAndHashCode(callSuper = true)
public class Instructor extends AuditableEntity {

    /** 講師ID（主キー） */
    // ID はBaseEntityから継承

    /**
     * 講師IDを取得します。
     *
     * @return 講師ID
     */
    public Long getInstructorId() {
        return getId();
    }

    /**
     * 講師IDを設定します。
     *
     * @param instructorId 講師ID
     */
    public void setInstructorId(Long instructorId) {
        setId(instructorId);
    }

    /**
     * ユーザーID（外部キー）
     * 既存のUserエンティティとの1:1関係
     */
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    /**
     * 講師番号
     * 企業内での一意識別子
     */
    @NotBlank(message = "講師番号は必須です")
    @Size(max = 20, message = "講師番号は20文字以内で入力してください")
    @Column(name = "instructor_number", length = 20, nullable = false)
    private String instructorNumber;

    /**
     * 所属部署ID
     * 講師が所属する部署の識別子
     */
    @Column(name = "department_id")
    private Long departmentId;

    /**
     * 講師資格取得日
     * 講師として認定された日付
     */
    @Column(name = "certification_date")
    private LocalDate certificationDate;

    /**
     * 専門分野
     * 講師の専門領域（IT、ビジネス、語学など）
     */
    @Size(max = 100, message = "専門分野は100文字以内で入力してください")
    @Column(name = "specialization", length = 100)
    private String specialization;

    /**
     * 講師レベル
     * 1: 新任, 2: 一般, 3: 上級, 4: エキスパート, 5: マスター
     */
    @Min(value = 1, message = "講師レベルは1以上である必要があります")
    @Max(value = 5, message = "講師レベルは5以下である必要があります")
    @Column(name = "instructor_level", nullable = false)
    private Integer instructorLevel = 1;

    /**
     * 担当コース数
     * 現在担当しているコースの総数
     */
    @Min(value = 0, message = "担当コース数は0以上である必要があります")
    @Column(name = "assigned_courses_count", nullable = false)
    private Integer assignedCoursesCount = 0;

    /**
     * 担当学生数
     * 現在指導している学生の総数
     */
    @Min(value = 0, message = "担当学生数は0以上である必要があります")
    @Column(name = "assigned_students_count", nullable = false)
    private Integer assignedStudentsCount = 0;

    /**
     * 累積指導時間（分）
     * 講師として指導した総時間
     */
    @Min(value = 0, message = "指導時間は0以上である必要があります")
    @Column(name = "total_teaching_minutes", nullable = false)
    private Integer totalTeachingMinutes = 0;

    /**
     * 講師評価スコア
     * 学生からの評価の平均値（1-5）
     */
    @DecimalMin(value = "1.0", message = "評価スコアは1.0以上である必要があります")
    @DecimalMax(value = "5.0", message = "評価スコアは5.0以下である必要があります")
    @Column(name = "rating_score", precision = 3, scale = 2)
    private BigDecimal ratingScore = BigDecimal.ZERO;

    /**
     * 評価件数
     * 受けた評価の総件数
     */
    @Min(value = 0, message = "評価件数は0以上である必要があります")
    @Column(name = "rating_count", nullable = false)
    private Integer ratingCount = 0;

    /**
     * 最終指導日時
     * 講師が最後に指導を行った日時
     */
    @Column(name = "last_teaching_date")
    private LocalDateTime lastTeachingDate;

    /**
     * 自己紹介
     * 講師の経歴や指導方針などの自己紹介文
     */
    @Size(max = 1000, message = "自己紹介は1000文字以内で入力してください")
    @Column(name = "bio", length = 1000)
    private String bio;

    /**
     * 講師ステータス
     * ACTIVE: 稼働中, INACTIVE: 非稼働, SUSPENDED: 停止, RETIRED: 退任
     */
    @NotBlank(message = "講師ステータスは必須です")
    @Pattern(regexp = "^(ACTIVE|INACTIVE|SUSPENDED|RETIRED)$", 
             message = "講師ステータスは ACTIVE, INACTIVE, SUSPENDED, RETIRED のいずれかである必要があります")
    @Column(name = "instructor_status", length = 20, nullable = false)
    private String instructorStatus = "ACTIVE";

    /**
     * 可用性情報
     * 講師の勤務可能時間や曜日などの情報
     */
    @Size(max = 500, message = "可用性情報は500文字以内で入力してください")
    @Column(name = "availability", length = 500)
    private String availability;

    /**
     * プロファイル更新日時
     * 管理用タイムスタンプ
     */
    @Column(name = "profile_updated_at")
    private LocalDateTime profileUpdatedAt;

    /**
     * エンティティ保存前処理
     * プロファイル更新日時を自動設定
     */
    @PrePersist
    @PreUpdate
    protected void onProfileUpdate() {
        this.profileUpdatedAt = LocalDateTime.now();
    }

    /**
     * 指導時間を追加
     *
     * @param minutes 追加する指導時間（分）
     */
    public void addTeachingTime(int minutes) {
        if (minutes > 0) {
            this.totalTeachingMinutes += minutes;
            this.lastTeachingDate = LocalDateTime.now();
        }
    }

    /** 担当コース数を増加 */
    public void incrementAssignedCourses() {
        this.assignedCoursesCount++;
    }

    /** 担当コース数を減少 */
    public void decrementAssignedCourses() {
        if (this.assignedCoursesCount > 0) {
            this.assignedCoursesCount--;
        }
    }

    /** 担当学生数を増加 */
    public void incrementAssignedStudents() {
        this.assignedStudentsCount++;
    }

    /** 担当学生数を減少 */
    public void decrementAssignedStudents() {
        if (this.assignedStudentsCount > 0) {
            this.assignedStudentsCount--;
        }
    }

    /**
     * 評価を追加（平均計算）
     *
     * @param newRating 新しい評価（1-5）
     */
    public void addRating(double newRating) {
        if (newRating >= 1.0 && newRating <= 5.0) {
            BigDecimal currentTotal = this.ratingScore.multiply(BigDecimal.valueOf(this.ratingCount));
            this.ratingCount++;
            BigDecimal newTotal = currentTotal.add(BigDecimal.valueOf(newRating));
            this.ratingScore = newTotal.divide(BigDecimal.valueOf(this.ratingCount), 2, RoundingMode.HALF_UP);
        }
    }

    /**
     * 講師ステータスがアクティブかどうかを判定
     *
     * @return アクティブの場合true
     */
    public boolean isActive() {
        return "ACTIVE".equals(this.instructorStatus);
    }

    /**
     * 講師レベルの文字列表現を取得
     *
     * @return 講師レベルの日本語表記
     */
    public String getInstructorLevelDisplay() {
        switch (this.instructorLevel) {
            case 1: return "新任";
            case 2: return "一般";
            case 3: return "上級";
            case 4: return "エキスパート";
            case 5: return "マスター";
            default: return "未設定";
        }
    }

    /**
     * 評価スコアの星形式表示を取得
     *
     * @return 星の数（★☆）形式の文字列
     */
    public String getRatingStarsDisplay() {
        if (this.ratingScore == null || BigDecimal.ZERO.compareTo(this.ratingScore) == 0) {
            return "☆☆☆☆☆ (未評価)";
        }

        int fullStars = this.ratingScore.setScale(0, RoundingMode.DOWN).intValue();
        boolean halfStar = this.ratingScore.subtract(BigDecimal.valueOf(fullStars))
                             .compareTo(new BigDecimal("0.5")) >= 0;

        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < fullStars; i++) {
            stars.append("★");
        }
        if (halfStar) {
            stars.append("☆");
            fullStars++;
        }
        for (int i = fullStars; i < 5; i++) {
            stars.append("☆");
        }

        return stars.toString() + String.format(" (%.1f)", this.ratingScore.doubleValue());
    }
}
