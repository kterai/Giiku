package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.StudentProfile;
import jp.co.apsa.giiku.domain.repository.StudentProfileRepository;
import jp.co.apsa.giiku.exception.StudentNotFoundException;
import jp.co.apsa.giiku.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * 学生プロフィールサービス。
 * 学生プロフィールに関するビジネスロジックを提供します。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class StudentProfileService {

    private static final Logger logger = LoggerFactory.getLogger(StudentProfileService.class);

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    /**
     * 学生プロフィールを作成
     */
    public StudentProfile create(StudentProfile studentProfile) {
        logger.info("Creating student profile for student number: {}", studentProfile.getStudentNumber());

        validateStudentProfile(studentProfile);

        // 学生番号の重複チェック
        if (existsByStudentNumber(studentProfile.getStudentNumber())) {
            throw new ValidationException("studentNumber", studentProfile.getStudentNumber(), 
                "Student number already exists");
        }

        return studentProfileRepository.save(studentProfile);
    }

    /**
     * 学生プロフィールを更新
     */
    public StudentProfile update(Long id, StudentProfile studentProfile) {
        logger.info("Updating student profile with id: {}", id);

        StudentProfile existingProfile = findById(id);

        // 更新可能なフィールドのみ更新
        existingProfile.setEnrollmentStatus(studentProfile.getEnrollmentStatus());
        existingProfile.setGradeLevel(studentProfile.getGradeLevel());
        existingProfile.setClassName(studentProfile.getClassName());
        existingProfile.setMajorField(studentProfile.getMajorField());
        existingProfile.setEmergencyContactName(studentProfile.getEmergencyContactName());
        existingProfile.setEmergencyContactPhone(studentProfile.getEmergencyContactPhone());
        existingProfile.setEmergencyContactRelationship(studentProfile.getEmergencyContactRelationship());
        existingProfile.setAddress(studentProfile.getAddress());
        existingProfile.setPhoneNumber(studentProfile.getPhoneNumber());

        return studentProfileRepository.save(existingProfile);
    }

    /**
     * IDで学生プロフィールを取得
     */
    @Transactional(readOnly = true)
    public StudentProfile findById(Long id) {
        logger.debug("Finding student profile by id: {}", id);

        return studentProfileRepository.findById(id)
            .orElseThrow(() -> StudentNotFoundException.byId(id));
    }

    /**
     * 学生番号で学生プロフィールを取得
     */
    @Transactional(readOnly = true)
    public StudentProfile findByStudentNumber(String studentNumber) {
        logger.debug("Finding student profile by student number: {}", studentNumber);

        return studentProfileRepository.findByStudentNumber(studentNumber)
            .orElseThrow(() -> StudentNotFoundException.byStudentNumber(studentNumber));
    }

    /**
     * 会社IDで学生プロフィール一覧を取得
     */
    @Transactional(readOnly = true)
    public List<StudentProfile> findByCompanyId(Long companyId) {
        logger.debug("Finding student profiles by company id: {}", companyId);

        return studentProfileRepository.findByCompanyId(companyId);
    }

    /**
     * 在籍状況で学生プロフィール一覧を取得
     */
    @Transactional(readOnly = true)
    public List<StudentProfile> findByEnrollmentStatus(String status) {
        logger.debug("Finding student profiles by enrollment status: {}", status);

        return studentProfileRepository.findByEnrollmentStatus(status);
    }

    /**
     * 学生プロフィール一覧をページングで取得
     */
    @Transactional(readOnly = true)
    public Page<StudentProfile> findAll(Pageable pageable) {
        logger.debug("Finding all student profiles with pagination");

        return studentProfileRepository.findAll(pageable);
    }

    /**
     * 会社IDでページング取得
     */
    @Transactional(readOnly = true)
    public Page<StudentProfile> findByCompanyId(Long companyId, Pageable pageable) {
        logger.debug("Finding student profiles by company id: {} with pagination", companyId);

        return studentProfileRepository.findByCompanyId(companyId, pageable);
    }

    /**
     * 学生プロフィールを削除
     */
    public void delete(Long id) {
        logger.info("Deleting student profile with id: {}", id);

        StudentProfile studentProfile = findById(id);
        studentProfileRepository.delete(studentProfile);
    }

    /**
     * 学生番号の存在チェック
     */
    @Transactional(readOnly = true)
    public boolean existsByStudentNumber(String studentNumber) {
        return studentProfileRepository.existsByStudentNumber(studentNumber);
    }

    /**
     * 在籍中の学生数を取得
     */
    @Transactional(readOnly = true)
    public long countActiveStudents(Long companyId) {
        return studentProfileRepository.countByCompanyIdAndEnrollmentStatus(
            companyId, StudentProfile.EnrollmentStatus.ENROLLED);
    }

    /**
     * 学年別学生数を取得
     */
    @Transactional(readOnly = true)
    public List<Object[]> countByGradeAndCompany(Long companyId) {
        return studentProfileRepository.countByGradeLevelAndCompanyId(companyId);
    }

    /**
     * バリデーション
     */
    private void validateStudentProfile(StudentProfile studentProfile) {
        if (studentProfile == null) {
            throw new ValidationException("Student profile cannot be null");
        }

        if (studentProfile.getStudentNumber() == null || studentProfile.getStudentNumber().trim().isEmpty()) {
            throw new ValidationException("studentNumber", null, "Student number is required");
        }

        if (studentProfile.getCompanyId() == null) {
            throw new ValidationException("companyId", null, "Company ID is required");
        }

        // 入学日が未来日でないことをチェック
        if (studentProfile.getAdmissionDate() != null &&
            studentProfile.getAdmissionDate().isAfter(LocalDate.now())) {
            throw new ValidationException("admissionDate", studentProfile.getAdmissionDate(),
                "Admission date cannot be in the future");
        }

        // 卒業予定日が入学日より後であることをチェック
        if (studentProfile.getAdmissionDate() != null &&
            studentProfile.getExpectedGraduationDate() != null &&
            studentProfile.getExpectedGraduationDate().isBefore(studentProfile.getAdmissionDate())) {
            throw new ValidationException("expectedGraduationDate", studentProfile.getExpectedGraduationDate(),
                "Expected graduation date must be after admission date");
        }
    }
}