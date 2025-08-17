package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 学生プロフィールエンティティ
 * 学生の詳細情報を管理するエンティティ
 */
@Entity
@Table(name = "student_profiles", indexes = {
    @Index(name = "idx_student_id", columnList = "student_id", unique = true),
    @Index(name = "idx_student_number", columnList = "student_number", unique = true),
    @Index(name = "idx_company_id", columnList = "company_id"),
    @Index(name = "idx_enrollment_status", columnList = "enrollment_status"),
    @Index(name = "idx_admission_date", columnList = "admission_date")
})
/** The StudentProfile class. */
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentProfile extends BaseEntity {

    /** 学生ID（Userテーブルとの外部キー） */
    @NotNull(message = "学生IDは必須です")
    @Column(name = "student_id", nullable = false, unique = true)
    private Long studentId;

    /** 学生番号（一意識別子） */
    @NotBlank(message = "学生番号は必須です")
    @Size(max = 20, message = "学生番号は20文字以下で入力してください")
    @Column(name = "student_number", nullable = false, unique = true, length = 20)
    private String studentNumber;

    /** 会社ID（学生が所属する会社） */
    @NotNull(message = "会社IDは必須です")
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    /**
     * 在籍状況
     * ENROLLED: 在学中
     * GRADUATED: 卒業
     * SUSPENDED: 休学中
     * WITHDRAWN: 退学
     */
    @NotBlank(message = "在籍状況は必須です")
    @Size(max = 20, message = "在籍状況は20文字以下で入力してください")
    @Column(name = "enrollment_status", nullable = false, length = 20)
    private String enrollmentStatus;

    /** 入学日 */
    @NotNull(message = "入学日は必須です")
    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    /** 卒業予定日 */
    @Column(name = "expected_graduation_date")
    private LocalDate expectedGraduationDate;

    /** 実際の卒業日 */
    @Column(name = "actual_graduation_date")
    private LocalDate actualGraduationDate;

    /** 学年 */
    @Min(value = 1, message = "学年は1以上で入力してください")
    @Max(value = 4, message = "学年は4以下で入力してください")
    @Column(name = "grade_level")
    private Integer gradeLevel;

    /** クラス */
    @Size(max = 10, message = "クラスは10文字以下で入力してください")
    @Column(name = "class_name", length = 10)
    private String className;

    /** 専攻分野 */
    @Size(max = 100, message = "専攻分野は100文字以下で入力してください")
    @Column(name = "major_field", length = 100)
    private String majorField;

    /** 緊急連絡先名 */
    @Size(max = 100, message = "緊急連絡先名は100文字以下で入力してください")
    @Column(name = "emergency_contact_name", length = 100)
    private String emergencyContactName;

    /** 緊急連絡先電話番号 */
    @Size(max = 20, message = "緊急連絡先電話番号は20文字以下で入力してください")
    @Column(name = "emergency_contact_phone", length = 20)
    private String emergencyContactPhone;

    /** 緊急連絡先関係性 */
    @Size(max = 50, message = "緊急連絡先関係性は50文字以下で入力してください")
    @Column(name = "emergency_contact_relationship", length = 50)
    private String emergencyContactRelationship;

    /** 住所 */
    @Size(max = 500, message = "住所は500文字以下で入力してください")
    @Column(name = "address", length = 500)
    private String address;

    /** 電話番号 */
    @Size(max = 20, message = "電話番号は20文字以下で入力してください")
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    /** 生年月日 */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * 性別
     * MALE: 男性
     * FEMALE: 女性
     * OTHER: その他
     */
    @Size(max = 10, message = "性別は10文字以下で入力してください")
    @Column(name = "gender", length = 10)
    private String gender;

    /** 特記事項・備考 */
    @Size(max = 2000, message = "特記事項は2000文字以下で入力してください")
    @Column(name = "notes", length = 2000)
    private String notes;

    // デフォルトコンストラクタ
    /** StudentProfile メソッド */
    public StudentProfile() {
        super();
        this.enrollmentStatus = "ENROLLED";
    }

    // コンストラクタ（必須フィールド）
    /** StudentProfile メソッド */
    public StudentProfile(Long studentId, String studentNumber, Long companyId, LocalDate admissionDate) {
        this();
        this.studentId = studentId;
        this.studentNumber = studentNumber;
        this.companyId = companyId;
        this.admissionDate = admissionDate;
    }

    /** 在籍状況の列挙型定数 */
    public static class EnrollmentStatus {
        public static final String ENROLLED = "ENROLLED";
        public static final String GRADUATED = "GRADUATED";
        public static final String SUSPENDED = "SUSPENDED";
        public static final String WITHDRAWN = "WITHDRAWN";
    }

    /** 性別の列挙型定数 */
    public static class Gender {
        public static final String MALE = "MALE";
        public static final String FEMALE = "FEMALE";
        public static final String OTHER = "OTHER";
    }

    /** フルネームを取得（User情報から取得される場合の便利メソッド） */
    public String getDisplayName() {
        return this.studentNumber != null ? this.studentNumber : "学生番号未設定";
    }

    /** エイリアスメソッド：学生プロファイルIDを取得 */
    public Long getStudentProfileId() {
        return getId();
    }

    /** エイリアスメソッド：学生プロファイルIDを設定 */
    public void setStudentProfileId(Long id) {
        setId(id);
    }

    /** 在学中かどうかを判定 */
    public boolean isEnrolled() {
        return EnrollmentStatus.ENROLLED.equals(this.enrollmentStatus);
    }

    /** 卒業済みかどうかを判定 */
    public boolean isGraduated() {
        return EnrollmentStatus.GRADUATED.equals(this.enrollmentStatus);
    }

    /** equals メソッド */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        StudentProfile that = (StudentProfile) obj;
        return Objects.equals(studentId, that.studentId) &&
               Objects.equals(studentNumber, that.studentNumber);
    }

    /** hashCode メソッド */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), studentId, studentNumber);
    }

    /** toString メソッド */
    @Override
    public String toString() {
        return "StudentProfile{" +
               "id=" + getId() +
               ", studentId=" + studentId +
               ", studentNumber='" + studentNumber + "'" +
               ", companyId=" + companyId +
               ", enrollmentStatus='" + enrollmentStatus + "'" +
               ", admissionDate=" + admissionDate +
               ", gradeLevel=" + gradeLevel +
               ", className='" + className + "'" +
               "}";
    }
}
