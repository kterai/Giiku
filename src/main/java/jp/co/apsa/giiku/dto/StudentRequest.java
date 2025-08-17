package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * 学生情報の作成・更新リクエスト用DTOクラス
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class StudentRequest {

    @NotBlank(message = "学生番号は必須です")
    @Size(max = 20, message = "学生番号は20文字以内で入力してください")
    private String studentNumber;

    @NotNull(message = "会社IDは必須です")
    private Long companyId;

    @NotBlank(message = "在籍状況は必須です")
    @Size(max = 20, message = "在籍状況は20文字以内で入力してください")
    private String enrollmentStatus;

    private LocalDate admissionDate;

    private LocalDate expectedGraduationDate;

    @Min(value = 1, message = "学年は1以上で入力してください")
    @Max(value = 4, message = "学年は4以下で入力してください")
    private Integer gradeLevel;

    @Size(max = 50, message = "クラス名は50文字以内で入力してください")
    private String className;

    @Size(max = 100, message = "専攻分野は100文字以内で入力してください")
    private String majorField;

    @Size(max = 100, message = "緊急連絡先名は100文字以内で入力してください")
    private String emergencyContactName;

    @Size(max = 20, message = "緊急連絡先電話番号は20文字以内で入力してください")
    private String emergencyContactPhone;

    @Size(max = 200, message = "住所は200文字以内で入力してください")
    private String address;

    @Size(max = 20, message = "電話番号は20文字以内で入力してください")
    private String phoneNumber;

    private LocalDate birthDate;

    @Size(max = 10, message = "性別は10文字以内で入力してください")
    private String gender;

    @Size(max = 500, message = "備考は500文字以内で入力してください")
    private String notes;

    // デフォルトコンストラクタ
    /** StudentRequest メソッド */
    public StudentRequest() {}

    // すべてのフィールドを含むコンストラクタ
    /** StudentRequest メソッド */
    public StudentRequest(String studentNumber, Long companyId, String enrollmentStatus,
                         LocalDate admissionDate, LocalDate expectedGraduationDate,
                         Integer gradeLevel, String className, String majorField,
                         String emergencyContactName, String emergencyContactPhone,
                         String address, String phoneNumber, LocalDate birthDate,
                         String gender, String notes) {
        this.studentNumber = studentNumber;
        this.companyId = companyId;
        this.enrollmentStatus = enrollmentStatus;
        this.admissionDate = admissionDate;
        this.expectedGraduationDate = expectedGraduationDate;
        this.gradeLevel = gradeLevel;
        this.className = className;
        this.majorField = majorField;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.notes = notes;
    }

    // Getter and Setter methods
    /** getStudentNumber メソッド */
    public String getStudentNumber() { return studentNumber; }
    /** setStudentNumber メソッド */
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    /** getCompanyId メソッド */
    public Long getCompanyId() { return companyId; }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    /** getEnrollmentStatus メソッド */
    public String getEnrollmentStatus() { return enrollmentStatus; }
    /** setEnrollmentStatus メソッド */
    public void setEnrollmentStatus(String enrollmentStatus) { this.enrollmentStatus = enrollmentStatus; }
    /** getAdmissionDate メソッド */
    public LocalDate getAdmissionDate() { return admissionDate; }
    /** setAdmissionDate メソッド */
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
    /** getExpectedGraduationDate メソッド */
    public LocalDate getExpectedGraduationDate() { return expectedGraduationDate; }
    /** setExpectedGraduationDate メソッド */
    public void setExpectedGraduationDate(LocalDate expectedGraduationDate) { this.expectedGraduationDate = expectedGraduationDate; }
    /** getGradeLevel メソッド */
    public Integer getGradeLevel() { return gradeLevel; }
    /** setGradeLevel メソッド */
    public void setGradeLevel(Integer gradeLevel) { this.gradeLevel = gradeLevel; }
    /** getClassName メソッド */
    public String getClassName() { return className; }
    /** setClassName メソッド */
    public void setClassName(String className) { this.className = className; }
    /** getMajorField メソッド */
    public String getMajorField() { return majorField; }
    /** setMajorField メソッド */
    public void setMajorField(String majorField) { this.majorField = majorField; }
    /** getEmergencyContactName メソッド */
    public String getEmergencyContactName() { return emergencyContactName; }
    /** setEmergencyContactName メソッド */
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    /** getEmergencyContactPhone メソッド */
    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    /** setEmergencyContactPhone メソッド */
    public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }
    /** getAddress メソッド */
    public String getAddress() { return address; }
    /** setAddress メソッド */
    public void setAddress(String address) { this.address = address; }
    /** getPhoneNumber メソッド */
    public String getPhoneNumber() { return phoneNumber; }
    /** setPhoneNumber メソッド */
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    /** getBirthDate メソッド */
    public LocalDate getBirthDate() { return birthDate; }
    /** setBirthDate メソッド */
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    /** getGender メソッド */
    public String getGender() { return gender; }
    /** setGender メソッド */
    public void setGender(String gender) { this.gender = gender; }
    /** getNotes メソッド */
    public String getNotes() { return notes; }
    /** setNotes メソッド */
    public void setNotes(String notes) { this.notes = notes; }

    /** toString メソッド */
    @Override
    public String toString() {
        return "StudentRequest{" +
                "studentNumber='" + studentNumber + "'" +
                ", companyId=" + companyId +
                ", enrollmentStatus='" + enrollmentStatus + "'" +
                ", admissionDate=" + admissionDate +
                ", expectedGraduationDate=" + expectedGraduationDate +
                ", gradeLevel=" + gradeLevel +
                ", className='" + className + "'" +
                ", majorField='" + majorField + "'" +
                ", gender='" + gender + "'" +
                "}";
    }
}
