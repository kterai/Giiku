package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.StudentProfile;
import jp.co.apsa.giiku.domain.repository.StudentProfileRepository;
import jp.co.apsa.giiku.domain.repository.UserRepository;
import jp.co.apsa.giiku.domain.repository.CompanyRepository;
import jp.co.apsa.giiku.dto.StudentRequest;
import jp.co.apsa.giiku.dto.StudentResponse;
import jp.co.apsa.giiku.exception.StudentNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * {@link StudentService} のテストクラス。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("StudentService Tests")
class StudentServiceTest {

    @Mock
    private StudentProfileRepository studentProfileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private StudentService studentService;

    private StudentProfile profile;
    private StudentRequest request;

    @BeforeEach
    void setUp() {
        profile = new StudentProfile(1L, "S001", 100L, LocalDate.now());
        profile.setId(1L);

        request = new StudentRequest();
        request.setStudentNumber("S001");
        request.setCompanyId(100L);
        request.setEnrollmentStatus("ENROLLED");
        request.setAdmissionDate(LocalDate.now());
    }

    @Test
    @DisplayName("IDで学生を取得できる")
    void testGetStudentById() {
        when(studentProfileRepository.findById(1L)).thenReturn(Optional.of(profile));

        StudentResponse result = studentService.getStudentById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStudentNumber()).isEqualTo("S001");
        verify(studentProfileRepository).findById(1L);
    }

    @Test
    @DisplayName("存在しないID取得時に例外が発生する")
    void testGetStudentByIdNotFound() {
        when(studentProfileRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getStudentById(1L))
            .isInstanceOf(StudentNotFoundException.class);
        verify(studentProfileRepository).findById(1L);
    }

    @Test
    @DisplayName("学生を作成できる")
    void testCreateStudent() {
        when(studentProfileRepository.save(any(StudentProfile.class))).thenAnswer(invocation -> {
            StudentProfile p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });

        StudentResponse result = studentService.createStudent(request);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStudentNumber()).isEqualTo("S001");
        verify(studentProfileRepository).save(any(StudentProfile.class));
    }

    @Test
    @DisplayName("nullリクエストで作成すると例外が発生する")
    void testCreateStudentWithNull() {
        assertThatThrownBy(() -> studentService.createStudent(null))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("学生情報を更新できる")
    void testUpdateStudent() {
        when(studentProfileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(studentProfileRepository.save(any(StudentProfile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StudentRequest update = new StudentRequest();
        update.setStudentNumber("S001");
        update.setCompanyId(100L);
        update.setEnrollmentStatus("GRADUATED");
        update.setAdmissionDate(profile.getAdmissionDate());

        StudentResponse result = studentService.updateStudent(1L, update);

        assertThat(result.getEnrollmentStatus()).isEqualTo("GRADUATED");
        verify(studentProfileRepository).findById(1L);
        verify(studentProfileRepository).save(any(StudentProfile.class));
    }

    @Test
    @DisplayName("存在しない学生情報更新時に例外が発生する")
    void testUpdateStudentNotFound() {
        when(studentProfileRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.updateStudent(1L, request))
            .isInstanceOf(StudentNotFoundException.class);
        verify(studentProfileRepository).findById(1L);
    }

    @Test
    @DisplayName("学生情報を削除できる")
    void testDeleteStudent() {
        when(studentProfileRepository.existsById(1L)).thenReturn(true);

        studentService.deleteStudent(1L);

        verify(studentProfileRepository).deleteById(1L);
    }

    @Test
    @DisplayName("存在しない学生情報削除時に例外が発生する")
    void testDeleteStudentNotFound() {
        when(studentProfileRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> studentService.deleteStudent(1L))
            .isInstanceOf(StudentNotFoundException.class);
        verify(studentProfileRepository).existsById(1L);
    }
}
