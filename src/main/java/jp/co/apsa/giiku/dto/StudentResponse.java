package jp.co.apsa.giiku.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学生情報レスポンス用DTOクラス
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class StudentResponse {

    private Long id;
    private String studentNumber;
    private Long companyId;
    private String enrollmentStatus;
    private LocalDate admissionDate;
    private LocalDate expectedGraduationDate;
    private LocalDate actualGraduationDate;
    private Integer gradeLevel;
    private String className;
    private String majorField;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String address;
    private String phoneNumber;
    private LocalDate birthDate;
    private String gender;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;

    // デフォルトコンストラクタ
    /** StudentResponse メソッド */
    public StudentResponse() {}

    // すべてのフィールドを含むコンストラクタ
    /** StudentResponse メソッド */
    public StudentResponse(Long id, String studentNumber, Long companyId, String enrollmentStatus,
                          LocalDate admissionDate, LocalDate expectedGraduationDate,
                          LocalDate actualGraduationDate, Integer gradeLevel, String className,
                          String majorField, String emergencyContactName, String emergencyContactPhone,
                          String address, String phoneNumber, LocalDate birthDate, String gender,
                          String notes, LocalDateTime createdAt, LocalDateTime updatedAt, Long version) {
        this.id = id;
        this.studentNumber = studentNumber;
        this.companyId = companyId;
        this.enrollmentStatus = enrollmentStatus;
        this.admissionDate = admissionDate;
        this.expectedGraduationDate = expectedGraduationDate;
        this.actualGraduationDate = actualGraduationDate;
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
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    // ユーティリティメソッド
    /** isEnrolled メソッド */
    public boolean isEnrolled() {
        return "ENROLLED".equals(enrollmentStatus);
    }
    /** isGraduated メソッド */
    public boolean isGraduated() {
        return actualGraduationDate != null;
    }
    /** getFullDisplayInfo メソッド */
    public String getFullDisplayInfo() {
        return String.format("%s (%s) - %s", studentNumber, className != null ? className : "未設定", enrollmentStatus);
    }

    // Getter and Setter methods
    /** getId メソッド */
    public Long getId() { return id; }
    /** setId メソッド */
    public void setId(Long id) { this.id = id; }
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
    /** getActualGraduationDate メソッド */
    public LocalDate getActualGraduationDate() { return actualGraduationDate; }
    /** setActualGraduationDate メソッド */
    public void setActualGraduationDate(LocalDate actualGraduationDate) { this.actualGraduationDate = actualGraduationDate; }
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
    /** getCreatedAt メソッド */
    public LocalDateTime getCreatedAt() { return createdAt; }
    /** setCreatedAt メソッド */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    /** getUpdatedAt メソッド */
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    /** setUpdatedAt メソッド */
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    /** getVersion メソッド */
    public Long getVersion() { return version; }
    /** setVersion メソッド */
    public void setVersion(Long version) { this.version = version; }

    /** toString メソッド */
    @Override
    public String toString() {
        return "StudentResponse{" +
                "id=" + id +
                ", studentNumber='" + studentNumber + "'" +
                ", companyId=" + companyId +
                ", enrollmentStatus='" + enrollmentStatus + "'" +
                ", gradeLevel=" + gradeLevel +
                ", className='" + className + "'" +
                ", majorField='" + majorField + "'" +
                ", createdAt=" + createdAt +
                "}";
    }
}
