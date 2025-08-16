package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.StudentEnrollment;
import jp.co.apsa.giiku.domain.entity.Student;
import jp.co.apsa.giiku.domain.entity.TrainingProgram;
import jp.co.apsa.giiku.domain.entity.ProgramSchedule;
import jp.co.apsa.giiku.domain.entity.Grade;
import jp.co.apsa.giiku.domain.repository.StudentEnrollmentRepository;
import jp.co.apsa.giiku.domain.repository.StudentRepository;
import jp.co.apsa.giiku.domain.repository.TrainingProgramRepository;
import jp.co.apsa.giiku.domain.repository.ProgramScheduleRepository;
import jp.co.apsa.giiku.domain.repository.GradeRepository;
import jp.co.apsa.giiku.exception.ResourceNotFoundException;
import jp.co.apsa.giiku.exception.InvalidDataException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("StudentEnrollmentService Tests")
class StudentEnrollmentServiceTest {

    @Mock
    private StudentEnrollmentRepository studentEnrollmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TrainingProgramRepository trainingProgramRepository;

    @Mock
    private ProgramScheduleRepository programScheduleRepository;

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private StudentEnrollmentService studentEnrollmentService;

    private StudentEnrollment testEnrollment;
    private Student testStudent;
    private TrainingProgram testProgram;
    private ProgramSchedule testSchedule;
    private Grade testGrade;

    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setStudentId("STU001");
        testStudent.setFirstName("太郎");
        testStudent.setLastName("山田");
        testStudent.setEmail("yamada@example.com");
        testStudent.setPhone("090-1234-5678");
        testStudent.setStatus("ACTIVE");
        testStudent.setCreatedAt(LocalDateTime.now());

        testProgram = new TrainingProgram();
        testProgram.setId(1L);
        testProgram.setProgramName("Java基礎研修");
        testProgram.setDescription("Java言語の基礎を学ぶ研修プログラム");
        testProgram.setDurationDays(30);
        testProgram.setMaxParticipants(20);
        testProgram.setStatus("ACTIVE");
        testProgram.setCreatedAt(LocalDateTime.now());

        testSchedule = new ProgramSchedule();
        testSchedule.setId(1L);
        testSchedule.setTrainingProgram(testProgram);
        testSchedule.setStartDate(LocalDate.now().plusDays(7));
        testSchedule.setEndDate(LocalDate.now().plusDays(37));
        testSchedule.setLocation("東京研修センター");
        testSchedule.setMaxParticipants(20);
        testSchedule.setCurrentParticipants(5);
        testSchedule.setStatus("SCHEDULED");

        testEnrollment = new StudentEnrollment();
        testEnrollment.setId(1L);
        testEnrollment.setStudent(testStudent);
        testEnrollment.setTrainingProgram(testProgram);
        testEnrollment.setEnrollmentDate(LocalDateTime.now());
        testEnrollment.setStatus("ACTIVE");
        testEnrollment.setProgress(new BigDecimal("25.50"));
        testEnrollment.setCompletionDate(null);

        testGrade = new Grade();
        testGrade.setId(1L);
        testGrade.setStudent(testStudent);
        testGrade.setScore(new BigDecimal("85.50"));
        testGrade.setMaxScore(new BigDecimal("100.00"));
        testGrade.setPassed(true);
        testGrade.setCompletedAt(LocalDateTime.now());
    }

    @Nested
    @DisplayName("Student Enrollment CRUD Operations")
    class StudentEnrollmentCrudOperations {

        @Test
        @DisplayName("should create student enrollment successfully")
        void shouldCreateStudentEnrollment() {
            // Given
            when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));
            when(studentEnrollmentRepository.existsByStudentIdAndTrainingProgramIdAndStatus(1L, 1L, "ACTIVE"))
                .thenReturn(false);
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "ACTIVE")).thenReturn(15L);
            when(studentEnrollmentRepository.save(any(StudentEnrollment.class))).thenReturn(testEnrollment);

            // When
            StudentEnrollment result = studentEnrollmentService.enrollStudent(1L, 1L);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getStudent().getId()).isEqualTo(1L);
            assertThat(result.getTrainingProgram().getId()).isEqualTo(1L);
            assertThat(result.getStatus()).isEqualTo("ACTIVE");
            verify(studentEnrollmentRepository).save(any(StudentEnrollment.class));
        }

        @Test
        @DisplayName("should throw exception when student already enrolled")
        void shouldThrowExceptionWhenStudentAlreadyEnrolled() {
            // Given
            when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));
            when(studentEnrollmentRepository.existsByStudentIdAndTrainingProgramIdAndStatus(1L, 1L, "ACTIVE"))
                .thenReturn(true);

            // When & Then
            assertThatThrownBy(() -> studentEnrollmentService.enrollStudent(1L, 1L))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Student already enrolled");
        }

        @Test
        @DisplayName("should throw exception when program is full")
        void shouldThrowExceptionWhenProgramIsFull() {
            // Given
            when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));
            when(studentEnrollmentRepository.existsByStudentIdAndTrainingProgramIdAndStatus(1L, 1L, "ACTIVE"))
                .thenReturn(false);
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "ACTIVE")).thenReturn(20L);

            // When & Then
            assertThatThrownBy(() -> studentEnrollmentService.enrollStudent(1L, 1L))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Program is full");
        }

        @Test
        @DisplayName("should find enrollment by id")
        void shouldFindEnrollmentById() {
            // Given
            when(studentEnrollmentRepository.findById(1L)).thenReturn(Optional.of(testEnrollment));

            // When
            Optional<StudentEnrollment> result = studentEnrollmentService.findById(1L);

            // Then
            assertThat(result).isPresent();
            assertThat(result.get().getStudent().getStudentId()).isEqualTo("STU001");
            verify(studentEnrollmentRepository).findById(1L);
        }

        @Test
        @DisplayName("should update enrollment successfully")
        void shouldUpdateEnrollment() {
            // Given
            when(studentEnrollmentRepository.findById(1L)).thenReturn(Optional.of(testEnrollment));
            when(studentEnrollmentRepository.save(any(StudentEnrollment.class))).thenReturn(testEnrollment);

            testEnrollment.setProgress(new BigDecimal("75.50"));
            testEnrollment.setStatus("IN_PROGRESS");

            // When
            StudentEnrollment result = studentEnrollmentService.updateEnrollment(1L, testEnrollment);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getProgress()).isEqualTo(new BigDecimal("75.50"));
            assertThat(result.getStatus()).isEqualTo("IN_PROGRESS");
            verify(studentEnrollmentRepository).save(any(StudentEnrollment.class));
        }

        @Test
        @DisplayName("should cancel enrollment successfully")
        void shouldCancelEnrollment() {
            // Given
            when(studentEnrollmentRepository.findById(1L)).thenReturn(Optional.of(testEnrollment));
            when(studentEnrollmentRepository.save(any(StudentEnrollment.class))).thenReturn(testEnrollment);

            // When
            studentEnrollmentService.cancelEnrollment(1L);

            // Then
            verify(studentEnrollmentRepository).save(argThat(enrollment -> 
                "CANCELLED".equals(enrollment.getStatus())
            ));
        }
    }

    @Nested
    @DisplayName("Enrollment Search and Listing")
    class EnrollmentSearchAndListing {

        @Test
        @DisplayName("should find all enrollments with pagination")
        void shouldFindAllEnrollmentsWithPagination() {
            // Given
            Pageable pageable = PageRequest.of(0, 10);
            List<StudentEnrollment> enrollments = Arrays.asList(testEnrollment);
            Page<StudentEnrollment> page = new PageImpl<>(enrollments, pageable, 1);
            when(studentEnrollmentRepository.findAll(pageable)).thenReturn(page);

            // When
            Page<StudentEnrollment> result = studentEnrollmentService.findAllEnrollments(pageable);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getContent()).hasSize(1);
            assertThat(result.getContent().get(0).getStudent().getStudentId()).isEqualTo("STU001");
            verify(studentEnrollmentRepository).findAll(pageable);
        }

        @Test
        @DisplayName("should find enrollments by student")
        void shouldFindEnrollmentsByStudent() {
            // Given
            List<StudentEnrollment> enrollments = Arrays.asList(testEnrollment);
            when(studentEnrollmentRepository.findByStudentId(1L)).thenReturn(enrollments);

            // When
            List<StudentEnrollment> result = studentEnrollmentService.findEnrollmentsByStudent(1L);

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getStudent().getId()).isEqualTo(1L);
            verify(studentEnrollmentRepository).findByStudentId(1L);
        }

        @Test
        @DisplayName("should find enrollments by training program")
        void shouldFindEnrollmentsByTrainingProgram() {
            // Given
            List<StudentEnrollment> enrollments = Arrays.asList(testEnrollment);
            when(studentEnrollmentRepository.findByTrainingProgramId(1L)).thenReturn(enrollments);

            // When
            List<StudentEnrollment> result = studentEnrollmentService.findEnrollmentsByProgram(1L);

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getTrainingProgram().getId()).isEqualTo(1L);
            verify(studentEnrollmentRepository).findByTrainingProgramId(1L);
        }

        @Test
        @DisplayName("should find active enrollments")
        void shouldFindActiveEnrollments() {
            // Given
            List<StudentEnrollment> enrollments = Arrays.asList(testEnrollment);
            when(studentEnrollmentRepository.findByStatus("ACTIVE")).thenReturn(enrollments);

            // When
            List<StudentEnrollment> result = studentEnrollmentService.findActiveEnrollments();

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getStatus()).isEqualTo("ACTIVE");
            verify(studentEnrollmentRepository).findByStatus("ACTIVE");
        }

        @Test
        @DisplayName("should find enrollments by status")
        void shouldFindEnrollmentsByStatus() {
            // Given
            String status = "COMPLETED";
            List<StudentEnrollment> enrollments = Arrays.asList(testEnrollment);
            when(studentEnrollmentRepository.findByStatus(status)).thenReturn(enrollments);

            // When
            List<StudentEnrollment> result = studentEnrollmentService.findEnrollmentsByStatus(status);

            // Then
            assertThat(result).hasSize(1);
            verify(studentEnrollmentRepository).findByStatus(status);
        }
    }

    @Nested
    @DisplayName("Progress Management")
    class ProgressManagement {

        @Test
        @DisplayName("should update student progress")
        void shouldUpdateStudentProgress() {
            // Given
            when(studentEnrollmentRepository.findByStudentIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.of(testEnrollment));
            when(studentEnrollmentRepository.save(any(StudentEnrollment.class))).thenReturn(testEnrollment);

            BigDecimal newProgress = new BigDecimal("50.75");

            // When
            StudentEnrollment result = studentEnrollmentService.updateProgress(1L, 1L, newProgress);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getProgress()).isEqualTo(newProgress);
            verify(studentEnrollmentRepository).save(any(StudentEnrollment.class));
        }

        @Test
        @DisplayName("should complete enrollment when progress reaches 100")
        void shouldCompleteEnrollmentWhenProgressReaches100() {
            // Given
            when(studentEnrollmentRepository.findByStudentIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.of(testEnrollment));
            when(studentEnrollmentRepository.save(any(StudentEnrollment.class))).thenReturn(testEnrollment);

            BigDecimal fullProgress = new BigDecimal("100.00");

            // When
            StudentEnrollment result = studentEnrollmentService.updateProgress(1L, 1L, fullProgress);

            // Then
            verify(studentEnrollmentRepository).save(argThat(enrollment -> 
                "COMPLETED".equals(enrollment.getStatus()) &&
                enrollment.getCompletionDate() != null
            ));
        }

        @Test
        @DisplayName("should get student progress")
        void shouldGetStudentProgress() {
            // Given
            when(studentEnrollmentRepository.findByStudentIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.of(testEnrollment));

            // When
            BigDecimal progress = studentEnrollmentService.getStudentProgress(1L, 1L);

            // Then
            assertThat(progress).isEqualTo(new BigDecimal("25.50"));
            verify(studentEnrollmentRepository).findByStudentIdAndTrainingProgramId(1L, 1L);
        }

        @Test
        @DisplayName("should calculate completion percentage")
        void shouldCalculateCompletionPercentage() {
            // Given
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "COMPLETED")).thenReturn(8L);
            when(studentEnrollmentRepository.countByTrainingProgramId(1L)).thenReturn(20L);

            // When
            BigDecimal completionRate = studentEnrollmentService.calculateCompletionRate(1L);

            // Then
            assertThat(completionRate).isEqualTo(new BigDecimal("40.00"));
            verify(studentEnrollmentRepository).countByTrainingProgramIdAndStatus(1L, "COMPLETED");
            verify(studentEnrollmentRepository).countByTrainingProgramId(1L);
        }
    }

    @Nested
    @DisplayName("Enrollment Statistics")
    class EnrollmentStatistics {

        @Test
        @DisplayName("should count enrollments by status")
        void shouldCountEnrollmentsByStatus() {
            // Given
            when(studentEnrollmentRepository.countByStatus("ACTIVE")).thenReturn(15L);

            // When
            long count = studentEnrollmentService.countEnrollmentsByStatus("ACTIVE");

            // Then
            assertThat(count).isEqualTo(15L);
            verify(studentEnrollmentRepository).countByStatus("ACTIVE");
        }

        @Test
        @DisplayName("should count enrollments by program")
        void shouldCountEnrollmentsByProgram() {
            // Given
            when(studentEnrollmentRepository.countByTrainingProgramId(1L)).thenReturn(25L);

            // When
            long count = studentEnrollmentService.countEnrollmentsByProgram(1L);

            // Then
            assertThat(count).isEqualTo(25L);
            verify(studentEnrollmentRepository).countByTrainingProgramId(1L);
        }

        @Test
        @DisplayName("should get enrollment statistics")
        void shouldGetEnrollmentStatistics() {
            // Given
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "ACTIVE")).thenReturn(12L);
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "COMPLETED")).thenReturn(8L);
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "CANCELLED")).thenReturn(2L);

            // When
            Map<String, Object> stats = studentEnrollmentService.getEnrollmentStatistics(1L);

            // Then
            assertThat(stats).isNotNull();
            assertThat(stats.get("activeCount")).isEqualTo(12L);
            assertThat(stats.get("completedCount")).isEqualTo(8L);
            assertThat(stats.get("cancelledCount")).isEqualTo(2L);
            assertThat(stats.get("totalCount")).isEqualTo(22L);
        }

        @Test
        @DisplayName("should get average progress for program")
        void shouldGetAverageProgressForProgram() {
            // Given
            when(studentEnrollmentRepository.findAverageProgressByTrainingProgramId(1L))
                .thenReturn(new BigDecimal("67.25"));

            // When
            BigDecimal avgProgress = studentEnrollmentService.getAverageProgress(1L);

            // Then
            assertThat(avgProgress).isEqualTo(new BigDecimal("67.25"));
            verify(studentEnrollmentRepository).findAverageProgressByTrainingProgramId(1L);
        }
    }

    @Nested
    @DisplayName("Enrollment Validation")
    class EnrollmentValidation {

        @Test
        @DisplayName("should validate enrollment successfully")
        void shouldValidateEnrollmentSuccessfully() {
            // Given
            when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));

            // When & Then
            assertThatCode(() -> studentEnrollmentService.validateEnrollment(1L, 1L))
                .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("should throw exception for invalid student")
        void shouldThrowExceptionForInvalidStudent() {
            // Given
            when(studentRepository.findById(1L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> studentEnrollmentService.validateEnrollment(1L, 1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Student not found");
        }

        @Test
        @DisplayName("should throw exception for invalid program")
        void shouldThrowExceptionForInvalidProgram() {
            // Given
            when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> studentEnrollmentService.validateEnrollment(1L, 1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Training program not found");
        }

        @Test
        @DisplayName("should throw exception for inactive student")
        void shouldThrowExceptionForInactiveStudent() {
            // Given
            testStudent.setStatus("INACTIVE");
            when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

            // When & Then
            assertThatThrownBy(() -> studentEnrollmentService.validateEnrollment(1L, 1L))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Student is not active");
        }

        @Test
        @DisplayName("should throw exception for inactive program")
        void shouldThrowExceptionForInactiveProgram() {
            // Given
            testProgram.setStatus("INACTIVE");
            when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));

            // When & Then
            assertThatThrownBy(() -> studentEnrollmentService.validateEnrollment(1L, 1L))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Training program is not active");
        }
    }

    @Nested
    @DisplayName("Business Logic Tests")
    class BusinessLogicTests {

        @Test
        @DisplayName("should check if student is enrolled in program")
        void shouldCheckIfStudentIsEnrolledInProgram() {
            // Given
            when(studentEnrollmentRepository.existsByStudentIdAndTrainingProgramIdAndStatus(1L, 1L, "ACTIVE"))
                .thenReturn(true);

            // When
            boolean result = studentEnrollmentService.isStudentEnrolled(1L, 1L);

            // Then
            assertThat(result).isTrue();
            verify(studentEnrollmentRepository).existsByStudentIdAndTrainingProgramIdAndStatus(1L, 1L, "ACTIVE");
        }

        @Test
        @DisplayName("should return false when student is not enrolled")
        void shouldReturnFalseWhenStudentIsNotEnrolled() {
            // Given
            when(studentEnrollmentRepository.existsByStudentIdAndTrainingProgramIdAndStatus(1L, 1L, "ACTIVE"))
                .thenReturn(false);

            // When
            boolean result = studentEnrollmentService.isStudentEnrolled(1L, 1L);

            // Then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should check if enrollment can be cancelled")
        void shouldCheckIfEnrollmentCanBeCancelled() {
            // Given
            when(studentEnrollmentRepository.findById(1L)).thenReturn(Optional.of(testEnrollment));
            when(programScheduleRepository.findByTrainingProgramId(1L)).thenReturn(Arrays.asList(testSchedule));

            // When
            boolean result = studentEnrollmentService.canCancelEnrollment(1L);

            // Then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("should return false when program has started")
        void shouldReturnFalseWhenProgramHasStarted() {
            // Given
            testSchedule.setStartDate(LocalDate.now().minusDays(1));
            testSchedule.setStatus("IN_PROGRESS");
            when(studentEnrollmentRepository.findById(1L)).thenReturn(Optional.of(testEnrollment));
            when(programScheduleRepository.findByTrainingProgramId(1L)).thenReturn(Arrays.asList(testSchedule));

            // When
            boolean result = studentEnrollmentService.canCancelEnrollment(1L);

            // Then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should find students with low progress")
        void shouldFindStudentsWithLowProgress() {
            // Given
            BigDecimal threshold = new BigDecimal("30.00");
            when(studentEnrollmentRepository.findByTrainingProgramIdAndProgressLessThan(1L, threshold))
                .thenReturn(Arrays.asList(testEnrollment));

            // When
            List<StudentEnrollment> result = studentEnrollmentService.findStudentsWithLowProgress(1L, threshold);

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getProgress()).isLessThan(threshold);
            verify(studentEnrollmentRepository).findByTrainingProgramIdAndProgressLessThan(1L, threshold);
        }

        @Test
        @DisplayName("should get enrollment timeline")
        void shouldGetEnrollmentTimeline() {
            // Given
            LocalDate startDate = LocalDate.now().minusDays(30);
            LocalDate endDate = LocalDate.now();
            when(studentEnrollmentRepository.countEnrollmentsByDateRange(startDate, endDate)).thenReturn(15L);

            // When
            long count = studentEnrollmentService.getEnrollmentCountByDateRange(startDate, endDate);

            // Then
            assertThat(count).isEqualTo(15L);
            verify(studentEnrollmentRepository).countEnrollmentsByDateRange(startDate, endDate);
        }

        @Test
        @DisplayName("should find upcoming program completions")
        void shouldFindUpcomingProgramCompletions() {
            // Given
            when(studentEnrollmentRepository.findByStatusAndTrainingProgramEndDateBefore(eq("IN_PROGRESS"), any(LocalDate.class)))
                .thenReturn(Arrays.asList(testEnrollment));

            // When
            List<StudentEnrollment> result = studentEnrollmentService.findUpcomingCompletions();

            // Then
            assertThat(result).hasSize(1);
            verify(studentEnrollmentRepository).findByStatusAndTrainingProgramEndDateBefore(eq("IN_PROGRESS"), any(LocalDate.class));
        }
    }
}
