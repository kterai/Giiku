package jp.co.apsa.giiku.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jp.co.apsa.giiku.domain.entity.StudentProfile;
import jp.co.apsa.giiku.domain.repository.CompanyRepository;
import jp.co.apsa.giiku.domain.repository.StudentProfileRepository;
import jp.co.apsa.giiku.domain.repository.UserRepository;
import jp.co.apsa.giiku.dto.StudentRequest;
import jp.co.apsa.giiku.dto.StudentResponse;
import jp.co.apsa.giiku.dto.StudentStatistics;
import jp.co.apsa.giiku.exception.StudentNotFoundException;

/**
 * 学生プロフィールに関するビジネスロジックを提供します。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class StudentService {

    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    /** StudentService メソッド */
    @Autowired
    public StudentService(StudentProfileRepository studentProfileRepository,
                          UserRepository userRepository,
                          CompanyRepository companyRepository) {
        this.studentProfileRepository = studentProfileRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    /**
     * すべての学生プロフィールを取得します。
     *
     * @return 学生プロフィールのリスト
     */
    @Transactional(readOnly = true)
    public List<StudentProfile> findAll() {
        return studentProfileRepository.findAll();
    }

    /**
     * ID で学生プロフィールを取得します。
     *
     * @param id 学生プロフィールID
     * @return 該当する学生プロフィール（存在しない場合は空）
     */
    @Transactional(readOnly = true)
    public Optional<StudentProfile> findById(Long id) {
        return studentProfileRepository.findById(id);
    }

    /**
     * 学生ID（ユーザーID）で学生プロフィールを取得します。
     *
     * @param studentId 学生ID
     * @return 該当する学生プロフィール（存在しない場合は空）
     */
    @Transactional(readOnly = true)
    public Optional<StudentProfile> findByStudentId(Long studentId) {
        return studentProfileRepository.findByStudentId(studentId);
    }

    /**
     * 企業IDで学生プロフィールを検索します。
     *
     * @param companyId 企業ID
     * @return 学生プロフィールのリスト
     */
    @Transactional(readOnly = true)
    public List<StudentProfile> findByCompanyId(Long companyId) {
        return studentProfileRepository.findByCompanyId(companyId);
    }

    /**
     * 学生プロフィールを新規作成します。
     *
     * @param profile 保存する学生プロフィール
     * @return 保存された学生プロフィール
     */
    public StudentProfile save(StudentProfile profile) {
        validate(profile);
        checkReferences(profile);
        return studentProfileRepository.save(profile);
    }

    /**
     * 学生プロフィールを更新します。
     *
     * @param id      学生プロフィールID
     * @param profile 更新内容
     * @return 更新された学生プロフィール
     * @throws StudentNotFoundException ID が存在しない場合
     */
    public StudentProfile update(Long id, StudentProfile profile) {
        if (!studentProfileRepository.existsById(id)) {
            throw StudentNotFoundException.byId(id);
        }
        profile.setId(id);
        validate(profile);
        checkReferences(profile);
        return studentProfileRepository.save(profile);
    }

    /**
     * 学生プロフィールを退学扱いにします。
     *
     * @param id 学生プロフィールID
     * @throws StudentNotFoundException ID が存在しない場合
     */
    public void deactivate(Long id) {
        StudentProfile profile = studentProfileRepository.findById(id)
                .orElseThrow(() -> StudentNotFoundException.byId(id));
        profile.setEnrollmentStatus(StudentProfile.EnrollmentStatus.WITHDRAWN);
        studentProfileRepository.save(profile);
    }

    /**
     * 学生プロフィールを削除します。
     *
     * @param id 学生プロフィールID
     * @throws StudentNotFoundException ID が存在しない場合
     */
    public void delete(Long id) {
        if (!studentProfileRepository.existsById(id)) {
            throw StudentNotFoundException.byId(id);
        }
        studentProfileRepository.deleteById(id);
    }

    // ===== DTO ベースのメソッド =====

    /**
     * 学生一覧を取得します。
     *
     * @param pageable ページ情報
     * @return 学生レスポンスのページ
     */
    @Transactional(readOnly = true)
    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        return studentProfileRepository.findAll(pageable).map(this::toStudentResponse);
    }

    /**
     * ID を指定して学生情報を取得します。
     *
     * @param id 学生プロフィールID
     * @return 該当する学生レスポンス
     * @throws StudentNotFoundException 学生が存在しない場合
     */
    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {
        StudentProfile profile = studentProfileRepository.findById(id)
                .orElseThrow(() -> StudentNotFoundException.byId(id));
        return toStudentResponse(profile);
    }

    /**
     * 学生情報を新規登録します。
     *
     * @param request 登録する学生リクエスト
     * @return 登録された学生レスポンス
     */
    public StudentResponse createStudent(StudentRequest request) {
        StudentProfile profile = toStudentProfile(request);
        StudentProfile saved = studentProfileRepository.save(profile);
        return toStudentResponse(saved);
    }

    /**
     * 学生情報を更新します。
     *
     * @param id      学生プロフィールID
     * @param request 更新内容
     * @return 更新された学生レスポンス
     * @throws StudentNotFoundException 学生が存在しない場合
     */
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        StudentProfile profile = studentProfileRepository.findById(id)
                .orElseThrow(() -> StudentNotFoundException.byId(id));
        updateProfileFromRequest(profile, request);
        StudentProfile saved = studentProfileRepository.save(profile);
        return toStudentResponse(saved);
    }

    /**
     * 学生情報を削除します。
     *
     * @param id 学生プロフィールID
     * @throws StudentNotFoundException 学生が存在しない場合
     */
    public void deleteStudent(Long id) {
        if (!studentProfileRepository.existsById(id)) {
            throw StudentNotFoundException.byId(id);
        }
        studentProfileRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<StudentResponse> searchStudents(String name, String email, String department, Pageable pageable) {
        return Page.empty(pageable);
    }

    @Transactional(readOnly = true)
    public Page<StudentResponse> filterStudents(String status, String startDate, String endDate, Boolean active, Pageable pageable) {
        return Page.empty(pageable);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getStudentProgress(Long id) {
        return new HashMap<>();
    }

    public Map<String, Object> updateStudentProgress(Long id, Map<String, Object> progressData) {
        return new HashMap<>();
    }

    @Transactional(readOnly = true)
    public StudentStatistics getStudentStatistics() {
        return new StudentStatistics();
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getDepartmentStatistics() {
        return new HashMap<>();
    }

    public List<StudentResponse> createStudentsBatch(List<StudentRequest> requests) {
        return new ArrayList<>();
    }

    public StudentResponse updateStudentStatus(Long id, String status) {
        return new StudentResponse();
    }

    /**
     * StudentProfile を StudentResponse に変換します。
     *
     * @param profile 変換元の学生プロフィール
     * @return 変換された学生レスポンス
     */
    private StudentResponse toStudentResponse(StudentProfile profile) {
        StudentResponse response = new StudentResponse();
        response.setId(profile.getId());
        response.setStudentNumber(profile.getStudentNumber());
        response.setCompanyId(profile.getCompanyId());
        response.setEnrollmentStatus(profile.getEnrollmentStatus());
        response.setAdmissionDate(profile.getAdmissionDate());
        response.setExpectedGraduationDate(profile.getExpectedGraduationDate());
        response.setActualGraduationDate(profile.getActualGraduationDate());
        response.setGradeLevel(profile.getGradeLevel());
        response.setClassName(profile.getClassName());
        response.setMajorField(profile.getMajorField());
        response.setEmergencyContactName(profile.getEmergencyContactName());
        response.setEmergencyContactPhone(profile.getEmergencyContactPhone());
        response.setAddress(profile.getAddress());
        response.setPhoneNumber(profile.getPhoneNumber());
        response.setBirthDate(profile.getBirthDate());
        response.setGender(profile.getGender());
        response.setNotes(profile.getNotes());
        response.setCreatedAt(profile.getCreatedAt());
        response.setUpdatedAt(profile.getUpdatedAt());
        response.setVersion(profile.getVersion());
        return response;
    }

    /**
     * StudentRequest を StudentProfile に変換します。
     *
     * @param request 変換元の学生リクエスト
     * @return 変換された学生プロフィール
     */
    private StudentProfile toStudentProfile(StudentRequest request) {
        StudentProfile profile = new StudentProfile();
        profile.setStudentNumber(request.getStudentNumber());
        profile.setCompanyId(request.getCompanyId());
        profile.setEnrollmentStatus(request.getEnrollmentStatus());
        profile.setAdmissionDate(request.getAdmissionDate());
        profile.setExpectedGraduationDate(request.getExpectedGraduationDate());
        profile.setGradeLevel(request.getGradeLevel());
        profile.setClassName(request.getClassName());
        profile.setMajorField(request.getMajorField());
        profile.setEmergencyContactName(request.getEmergencyContactName());
        profile.setEmergencyContactPhone(request.getEmergencyContactPhone());
        profile.setAddress(request.getAddress());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setBirthDate(request.getBirthDate());
        profile.setGender(request.getGender());
        profile.setNotes(request.getNotes());
        return profile;
    }

    /**
     * リクエストの内容で学生プロフィールを更新します。
     *
     * @param profile  更新対象の学生プロフィール
     * @param request  リクエストデータ
     */
    private void updateProfileFromRequest(StudentProfile profile, StudentRequest request) {
        profile.setStudentNumber(request.getStudentNumber());
        profile.setCompanyId(request.getCompanyId());
        profile.setEnrollmentStatus(request.getEnrollmentStatus());
        profile.setAdmissionDate(request.getAdmissionDate());
        profile.setExpectedGraduationDate(request.getExpectedGraduationDate());
        profile.setGradeLevel(request.getGradeLevel());
        profile.setClassName(request.getClassName());
        profile.setMajorField(request.getMajorField());
        profile.setEmergencyContactName(request.getEmergencyContactName());
        profile.setEmergencyContactPhone(request.getEmergencyContactPhone());
        profile.setAddress(request.getAddress());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setBirthDate(request.getBirthDate());
        profile.setGender(request.getGender());
        profile.setNotes(request.getNotes());
    }

    /**
     * 学生プロフィールの必須項目を検証します。
     *
     * @param profile 検証対象の学生プロフィール
     */
    private void validate(StudentProfile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("学生プロフィールが null です");
        }
        if (profile.getStudentId() == null) {
            throw new IllegalArgumentException("学生IDは必須です");
        }
        if (profile.getCompanyId() == null) {
            throw new IllegalArgumentException("会社IDは必須です");
        }
        if (profile.getAdmissionDate() == null) {
            throw new IllegalArgumentException("入学日は必須です");
        }
        if (profile.getEnrollmentStatus() == null || profile.getEnrollmentStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("在籍状況は必須です");
        }
    }

    /**
     * 外部参照を検証します。
     *
     * @param profile 検証対象の学生プロフィール
     */
    private void checkReferences(StudentProfile profile) {
        if (!userRepository.existsById(profile.getStudentId())) {
            throw new IllegalArgumentException("指定されたユーザーが存在しません: " + profile.getStudentId());
        }
        if (!companyRepository.existsById(profile.getCompanyId())) {
            throw new IllegalArgumentException("指定された企業が存在しません: " + profile.getCompanyId());
        }
    }
}
