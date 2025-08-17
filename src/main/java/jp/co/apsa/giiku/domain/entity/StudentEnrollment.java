package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 学生登録エンティティ
 * 学生の研修プログラム登録情報を管理するエンティティ
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "student_enrollments", indexes = {
    @Index(name = "idx_student_id", columnList = "student_id"),
    @Index(name = "idx_program_id", columnList = "program_id"),
    @Index(name = "idx_company_id", columnList = "company_id"),
    @Index(name = "idx_enrollment_status", columnList = "enrollment_status"),
    @Index(name = "idx_enrollment_date", columnList = "enrollment_date"),
    @Index(name = "idx_student_program_unique", columnList = "student_id,program_id", unique = true)
})
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentEnrollment extends BaseEntity {

    /** 学生ID（Userテーブルとの外部キー） */
    @NotNull(message = "学生IDは必須です")
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    /** 研修プログラムID（TrainingProgramテーブルとの外部キー） */
    @NotNull(message = "研修プログラムIDは必須です")
    @Column(name = "program_id", nullable = false)
    private Long programId;

    /** 会社ID（学生が所属する会社） */
    @NotNull(message = "会社IDは必須です")
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    /**
     * 登録状況
     * ENROLLED: 登録済み
     * COMPLETED: 修了
     * SUSPENDED: 一時停止
     * CANCELLED: キャンセル
     * FAILED: 不合格
     */
    @NotBlank(message = "登録状況は必須です")
    @Size(max = 20, message = "登録状況は20文字以下で入力してください")
    @Column(name = "enrollment_status", nullable = false, length = 20)
    private String enrollmentStatus;

    /** 登録日 */
    @NotNull(message = "登録日は必須です")
    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    /** 開始日 */
    @Column(name = "start_date")
    private LocalDate startDate;

    /** 修了日 */
    @Column(name = "completion_date")
    private LocalDate completionDate;

    /** 進捗率（0.0-100.0） */
    @DecimalMin(value = "0.0", message = "進捗率は0.0以上で入力してください")
    @DecimalMax(value = "100.0", message = "進捗率は100.0以下で入力してください")
    @Column(name = "progress_percentage", precision = 5, scale = 2)
    private BigDecimal progressPercentage = BigDecimal.ZERO;

    /** 最終スコア */
    @DecimalMin(value = "0.0", message = "スコアは0.0以上で入力してください")
    @DecimalMax(value = "100.0", message = "スコアは100.0以下で入力してください")
    @Column(name = "final_score", precision = 5, scale = 2)
    private BigDecimal finalScore;

    /** 合格フラグ */
    @Column(name = "passed", nullable = false)
    private Boolean passed = false;

    /** 試験回数 */
    @Min(value = 0, message = "試験回数は0以上で入力してください")
    @Column(name = "attempt_count", nullable = false)
    private Integer attemptCount = 0;

    /** 最大試験回数 */
    @Min(value = 1, message = "最大試験回数は1以上で入力してください")
    @Column(name = "max_attempts")
    private Integer maxAttempts = 3;

    /** 講師ID（担当講師） */
    @Column(name = "instructor_id")
    private Long instructorId;

    /** 修了証明書番号 */
    @Size(max = 50, message = "修了証明書番号は50文字以下で入力してください")
    @Column(name = "certificate_number", length = 50)
    private String certificateNumber;

    /** 修了証明書発行日 */
    @Column(name = "certificate_issued_date")
    private LocalDate certificateIssuedDate;

    /** 登録料金 */
    @DecimalMin(value = "0.0", message = "登録料金は0.0以上で入力してください")
    @Column(name = "enrollment_fee", precision = 10, scale = 2)
    private BigDecimal enrollmentFee;

    /**
     * 支払い状況
     * UNPAID: 未払い
     * PAID: 支払い済み
     * REFUNDED: 返金済み
     * PARTIAL: 一部支払い
     */
    @Size(max = 20, message = "支払い状況は20文字以下で入力してください")
    @Column(name = "payment_status", length = 20)
    private String paymentStatus = "UNPAID";

    /** 支払い日 */
    @Column(name = "payment_date")
    private LocalDate paymentDate;

    /** 特記事項・備考 */
    @Size(max = 1000, message = "特記事項は1000文字以下で入力してください")
    @Column(name = "notes", length = 1000)
    private String notes;

    /** 最終アクセス日時 */
    @Column(name = "last_access_date")
    private java.time.LocalDateTime lastAccessDate;

    // デフォルトコンストラクタ
    /** StudentEnrollment メソッド */
    public StudentEnrollment() {
        super();
        this.enrollmentStatus = "ENROLLED";
        this.progressPercentage = BigDecimal.ZERO;
        this.passed = false;
        this.attemptCount = 0;
        this.maxAttempts = 3;
        this.paymentStatus = "UNPAID";
    }

    // コンストラクタ（必須フィールド）
    /** StudentEnrollment メソッド */
    public StudentEnrollment(Long studentId, Long programId, Long companyId, LocalDate enrollmentDate) {
        this();
        this.studentId = studentId;
        this.programId = programId;
        this.companyId = companyId;
        this.enrollmentDate = enrollmentDate;
    }

    /** 登録状況の列挙型定数 */
    public static class EnrollmentStatus {
        public static final String ENROLLED = "ENROLLED";
        public static final String COMPLETED = "COMPLETED";
        public static final String SUSPENDED = "SUSPENDED";
        public static final String CANCELLED = "CANCELLED";
        public static final String FAILED = "FAILED";
    }

    /** 支払い状況の列挙型定数 */
    public static class PaymentStatus {
        public static final String UNPAID = "UNPAID";
        public static final String PAID = "PAID";
        public static final String REFUNDED = "REFUNDED";
        public static final String PARTIAL = "PARTIAL";
    }

    /** 登録が有効かどうかを判定 */
    public boolean isActive() {
        return EnrollmentStatus.ENROLLED.equals(this.enrollmentStatus) ||
               EnrollmentStatus.COMPLETED.equals(this.enrollmentStatus);
    }

    /** 修了済みかどうかを判定 */
    public boolean isCompleted() {
        return EnrollmentStatus.COMPLETED.equals(this.enrollmentStatus);
    }

    /** 試験可能かどうかを判定 */
    public boolean canTakeExam() {
        return isActive() && 
               (this.maxAttempts == null || this.attemptCount < this.maxAttempts);
    }

    /** 進捗率をパーセンテージで取得 */
    public double getProgressPercentageAsDouble() {
        return this.progressPercentage != null ? this.progressPercentage.doubleValue() : 0.0;
    }

    /** 進捗率を設定（double値から） */
    public void setProgressPercentageFromDouble(double percentage) {
        this.progressPercentage = BigDecimal.valueOf(percentage);
    }

    /** 修了処理を実行 */
    public void complete(BigDecimal finalScore, String certificateNumber) {
        this.enrollmentStatus = EnrollmentStatus.COMPLETED;
        this.completionDate = LocalDate.now();
        this.finalScore = finalScore;
        this.passed = true;
        this.progressPercentage = BigDecimal.valueOf(100.0);
        this.certificateNumber = certificateNumber;
        this.certificateIssuedDate = LocalDate.now();
    }

    /** 試験回数を増加 */
    public void incrementAttemptCount() {
        this.attemptCount++;
    }

    /** equals メソッド */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        StudentEnrollment that = (StudentEnrollment) obj;
        return Objects.equals(studentId, that.studentId) &&
               Objects.equals(programId, that.programId);
    }

    /** hashCode メソッド */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), studentId, programId);
    }

    /** toString メソッド */
    @Override
    public String toString() {
        return "StudentEnrollment{" +
               "id=" + getId() +
               ", studentId=" + studentId +
               ", programId=" + programId +
               ", companyId=" + companyId +
               ", enrollmentStatus='" + enrollmentStatus + "'" +
               ", enrollmentDate=" + enrollmentDate +
               ", progressPercentage=" + progressPercentage +
               ", passed=" + passed +
               ", attemptCount=" + attemptCount +
               "}";
    }
}
