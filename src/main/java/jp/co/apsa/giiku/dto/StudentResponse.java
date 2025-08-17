package jp.co.apsa.giiku.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学生情報レスポンス用DTOクラス
 *
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
    /**
     * StudentResponse メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public StudentResponse() {}

    // すべてのフィールドを含むコンストラクタ
    /**
     * StudentResponse メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
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
    /**
     * isEnrolled メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public boolean isEnrolled() {
        return "ENROLLED".equals(enrollmentStatus);
    }
    /**
     * isGraduated メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public boolean isGraduated() {
        return actualGraduationDate != null;
    }
    /**
     * getFullDisplayInfo メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getFullDisplayInfo() {
        return String.format("%s (%s) - %s", studentNumber, className != null ? className : "未設定", enrollmentStatus);
    }

    // Getter and Setter methods
    /**
     * getId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public Long getId() { return id; }
    /**
     * setId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setId(Long id) { this.id = id; }
    /**
     * getStudentNumber メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getStudentNumber() { return studentNumber; }
    /**
     * setStudentNumber メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    /**
     * getCompanyId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getCompanyId() { return companyId; }
    /**
     * setCompanyId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    /**
     * getEnrollmentStatus メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getEnrollmentStatus() { return enrollmentStatus; }
    /**
     * setEnrollmentStatus メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setEnrollmentStatus(String enrollmentStatus) { this.enrollmentStatus = enrollmentStatus; }
    /**
     * getAdmissionDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDate getAdmissionDate() { return admissionDate; }
    /**
     * setAdmissionDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
    /**
     * getExpectedGraduationDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDate getExpectedGraduationDate() { return expectedGraduationDate; }
    /**
     * setExpectedGraduationDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setExpectedGraduationDate(LocalDate expectedGraduationDate) { this.expectedGraduationDate = expectedGraduationDate; }
    /**
     * getActualGraduationDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDate getActualGraduationDate() { return actualGraduationDate; }
    /**
     * setActualGraduationDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setActualGraduationDate(LocalDate actualGraduationDate) { this.actualGraduationDate = actualGraduationDate; }
    /**
     * getGradeLevel メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getGradeLevel() { return gradeLevel; }
    /**
     * setGradeLevel メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setGradeLevel(Integer gradeLevel) { this.gradeLevel = gradeLevel; }
    /**
     * getClassName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getClassName() { return className; }
    /**
     * setClassName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setClassName(String className) { this.className = className; }
    /**
     * getMajorField メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getMajorField() { return majorField; }
    /**
     * setMajorField メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setMajorField(String majorField) { this.majorField = majorField; }
    /**
     * getEmergencyContactName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getEmergencyContactName() { return emergencyContactName; }
    /**
     * setEmergencyContactName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    /**
     * getEmergencyContactPhone メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    /**
     * setEmergencyContactPhone メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }
    /**
     * getAddress メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getAddress() { return address; }
    /**
     * setAddress メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setAddress(String address) { this.address = address; }
    /**
     * getPhoneNumber メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getPhoneNumber() { return phoneNumber; }
    /**
     * setPhoneNumber メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    /**
     * getBirthDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDate getBirthDate() { return birthDate; }
    /**
     * setBirthDate メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    /**
     * getGender メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getGender() { return gender; }
    /**
     * setGender メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setGender(String gender) { this.gender = gender; }
    /**
     * getNotes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getNotes() { return notes; }
    /**
     * setNotes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setNotes(String notes) { this.notes = notes; }
    /**
     * getCreatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getCreatedAt() { return createdAt; }
    /**
     * setCreatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    /**
     * getUpdatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    /**
     * setUpdatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    /**
     * getVersion メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getVersion() { return version; }
    /**
     * setVersion メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public void setVersion(Long version) { this.version = version; }

    /**
     * toString メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
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
