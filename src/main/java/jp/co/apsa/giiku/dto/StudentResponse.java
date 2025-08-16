package jp.co.apsa.giiku.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学生情報レスポンス用DTOクラス
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
    public StudentResponse() {}

    // すべてのフィールドを含むコンストラクタ
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
    public boolean isEnrolled() {
        return "ENROLLED".equals(enrollmentStatus);
    }

    public boolean isGraduated() {
        return actualGraduationDate != null;
    }

    public String getFullDisplayInfo() {
        return String.format("%s (%s) - %s", studentNumber, className != null ? className : "未設定", enrollmentStatus);
    }

    // Getter and Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public LocalDate getActualGraduationDate() { return actualGraduationDate; }
    public void setActualGraduationDate(LocalDate actualGraduationDate) { this.actualGraduationDate = actualGraduationDate; }

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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

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
