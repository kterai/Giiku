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
    public StudentRequest() {}

    // すべてのフィールドを含むコンストラクタ
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
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getEnrollmentStatus() { return enrollmentStatus; }
    public void setEnrollmentStatus(String enrollmentStatus) { this.enrollmentStatus = enrollmentStatus; }

    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }

    public LocalDate getExpectedGraduationDate() { return expectedGraduationDate; }
    public void setExpectedGraduationDate(LocalDate expectedGraduationDate) { this.expectedGraduationDate = expectedGraduationDate; }

    public Integer getGradeLevel() { return gradeLevel; }
    public void setGradeLevel(Integer gradeLevel) { this.gradeLevel = gradeLevel; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getMajorField() { return majorField; }
    public void setMajorField(String majorField) { this.majorField = majorField; }

    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }

    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

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
