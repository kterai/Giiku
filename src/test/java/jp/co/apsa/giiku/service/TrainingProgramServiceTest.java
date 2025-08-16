package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.TrainingProgram;
import jp.co.apsa.giiku.domain.entity.ProgramSchedule;
import jp.co.apsa.giiku.domain.entity.StudentEnrollment;
import jp.co.apsa.giiku.domain.entity.Quiz;
import jp.co.apsa.giiku.domain.entity.Student;
import jp.co.apsa.giiku.domain.entity.Instructor;
import jp.co.apsa.giiku.domain.repository.TrainingProgramRepository;
import jp.co.apsa.giiku.domain.repository.ProgramScheduleRepository;
import jp.co.apsa.giiku.domain.repository.StudentEnrollmentRepository;
import jp.co.apsa.giiku.domain.repository.QuizRepository;
import jp.co.apsa.giiku.domain.repository.InstructorRepository;
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
@DisplayName("TrainingProgramService Tests")
class TrainingProgramServiceTest {

    @Mock
    private TrainingProgramRepository trainingProgramRepository;

    @Mock
    private ProgramScheduleRepository programScheduleRepository;

    @Mock
    private StudentEnrollmentRepository studentEnrollmentRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private TrainingProgramService trainingProgramService;

    private TrainingProgram testProgram;
    private ProgramSchedule testSchedule;
    private StudentEnrollment testEnrollment;
    private Student testStudent;
    private Instructor testInstructor;
    private Quiz testQuiz;

    @BeforeEach
    void setUp() {
        testInstructor = new Instructor();
        testInstructor.setId(1L);
        testInstructor.setInstructorId("INS001");
        testInstructor.setFirstName("講師");
        testInstructor.setLastName("田中");
        testInstructor.setEmail("instructor@example.com");
        testInstructor.setSpecialty("Java");
        testInstructor.setStatus("ACTIVE");

        testProgram = new TrainingProgram();
        testProgram.setId(1L);
        testProgram.setProgramName("Java基礎研修");
        testProgram.setDescription("Java言語の基礎を学ぶ研修プログラム");
        testProgram.setDurationDays(30);
        testProgram.setMaxParticipants(20);
        testProgram.setStatus("ACTIVE");
        testProgram.setCreatedAt(LocalDateTime.now());
        testProgram.setUpdatedAt(LocalDateTime.now());

        testSchedule = new ProgramSchedule();
        testSchedule.setId(1L);
        testSchedule.setTrainingProgram(testProgram);
        testSchedule.setInstructor(testInstructor);
        testSchedule.setStartDate(LocalDate.now().plusDays(7));
        testSchedule.setEndDate(LocalDate.now().plusDays(37));
        testSchedule.setLocation("東京研修センター");
        testSchedule.setMaxParticipants(20);
        testSchedule.setCurrentParticipants(5);
        testSchedule.setStatus("SCHEDULED");

        testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setStudentId("STU001");
        testStudent.setFirstName("太郎");
        testStudent.setLastName("山田");
        testStudent.setEmail("yamada@example.com");

        testEnrollment = new StudentEnrollment();
        testEnrollment.setId(1L);
        testEnrollment.setStudent(testStudent);
        testEnrollment.setTrainingProgram(testProgram);
        testEnrollment.setEnrollmentDate(LocalDateTime.now());
        testEnrollment.setStatus("ACTIVE");

        testQuiz = new Quiz();
        testQuiz.setId(1L);
        testQuiz.setQuizName("Java基礎テスト");
        testQuiz.setTrainingProgram(testProgram);
        testQuiz.setQuestionCount(20);
        testQuiz.setTimeLimit(60);
        testQuiz.setPassingScore(new BigDecimal("70.00"));
        testQuiz.setStatus("ACTIVE");
    }

    @Nested
    @DisplayName("Training Program CRUD Operations")
    class TrainingProgramCrudOperations {

        @Test
        @DisplayName("should create training program successfully")
        void shouldCreateTrainingProgram() {
            // Given
            when(trainingProgramRepository.save(any(TrainingProgram.class))).thenReturn(testProgram);

            // When
            TrainingProgram result = trainingProgramService.createTrainingProgram(testProgram);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getProgramName()).isEqualTo("Java基礎研修");
            assertThat(result.getStatus()).isEqualTo("ACTIVE");
            verify(trainingProgramRepository).save(any(TrainingProgram.class));
        }

        @Test
        @DisplayName("should find training program by id")
        void shouldFindTrainingProgramById() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));

            // When
            Optional<TrainingProgram> result = trainingProgramService.findById(1L);

            // Then
            assertThat(result).isPresent();
            assertThat(result.get().getProgramName()).isEqualTo("Java基礎研修");
            verify(trainingProgramRepository).findById(1L);
        }

        @Test
        @DisplayName("should return empty when training program not found")
        void shouldReturnEmptyWhenTrainingProgramNotFound() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.empty());

            // When
            Optional<TrainingProgram> result = trainingProgramService.findById(1L);

            // Then
            assertThat(result).isEmpty();
            verify(trainingProgramRepository).findById(1L);
        }

        @Test
        @DisplayName("should update training program successfully")
        void shouldUpdateTrainingProgram() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));
            when(trainingProgramRepository.save(any(TrainingProgram.class))).thenReturn(testProgram);

            testProgram.setProgramName("更新されたJava基礎研修");
            testProgram.setDurationDays(45);

            // When
            TrainingProgram result = trainingProgramService.updateTrainingProgram(1L, testProgram);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getProgramName()).isEqualTo("更新されたJava基礎研修");
            assertThat(result.getDurationDays()).isEqualTo(45);
            verify(trainingProgramRepository).save(any(TrainingProgram.class));
        }

        @Test
        @DisplayName("should throw exception when updating non-existent training program")
        void shouldThrowExceptionWhenUpdatingNonExistentTrainingProgram() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> trainingProgramService.updateTrainingProgram(1L, testProgram))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Training program not found");
        }

        @Test
        @DisplayName("should delete training program successfully")
        void shouldDeleteTrainingProgram() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "ACTIVE")).thenReturn(0L);

            // When
            trainingProgramService.deleteTrainingProgram(1L);

            // Then
            verify(trainingProgramRepository).delete(testProgram);
        }

        @Test
        @DisplayName("should throw exception when deleting program with active enrollments")
        void shouldThrowExceptionWhenDeletingProgramWithActiveEnrollments() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "ACTIVE")).thenReturn(5L);

            // When & Then
            assertThatThrownBy(() -> trainingProgramService.deleteTrainingProgram(1L))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Cannot delete program with active enrollments");
        }
    }

    @Nested
    @DisplayName("Training Program Search and Listing")
    class TrainingProgramSearchAndListing {

        @Test
        @DisplayName("should find all training programs with pagination")
        void shouldFindAllTrainingProgramsWithPagination() {
            // Given
            Pageable pageable = PageRequest.of(0, 10);
            List<TrainingProgram> programs = Arrays.asList(testProgram);
            Page<TrainingProgram> page = new PageImpl<>(programs, pageable, 1);
            when(trainingProgramRepository.findAll(pageable)).thenReturn(page);

            // When
            Page<TrainingProgram> result = trainingProgramService.findAllTrainingPrograms(pageable);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getContent()).hasSize(1);
            assertThat(result.getContent().get(0).getProgramName()).isEqualTo("Java基礎研修");
            verify(trainingProgramRepository).findAll(pageable);
        }

        @Test
        @DisplayName("should find active training programs")
        void shouldFindActiveTrainingPrograms() {
            // Given
            List<TrainingProgram> programs = Arrays.asList(testProgram);
            when(trainingProgramRepository.findByStatus("ACTIVE")).thenReturn(programs);

            // When
            List<TrainingProgram> result = trainingProgramService.findActiveTrainingPrograms();

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getStatus()).isEqualTo("ACTIVE");
            verify(trainingProgramRepository).findByStatus("ACTIVE");
        }

        @Test
        @DisplayName("should search training programs by name")
        void shouldSearchTrainingProgramsByName() {
            // Given
            String searchTerm = "Java";
            List<TrainingProgram> programs = Arrays.asList(testProgram);
            when(trainingProgramRepository.findByProgramNameContainingIgnoreCase(searchTerm)).thenReturn(programs);

            // When
            List<TrainingProgram> result = trainingProgramService.searchByProgramName(searchTerm);

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getProgramName()).contains("Java");
            verify(trainingProgramRepository).findByProgramNameContainingIgnoreCase(searchTerm);
        }

        @Test
        @DisplayName("should find training programs by duration range")
        void shouldFindTrainingProgramsByDurationRange() {
            // Given
            when(trainingProgramRepository.findByDurationDaysBetween(20, 40)).thenReturn(Arrays.asList(testProgram));

            // When
            List<TrainingProgram> result = trainingProgramService.findByDurationRange(20, 40);

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getDurationDays()).isBetween(20, 40);
            verify(trainingProgramRepository).findByDurationDaysBetween(20, 40);
        }
    }

    @Nested
    @DisplayName("Program Schedule Management")
    class ProgramScheduleManagement {

        @Test
        @DisplayName("should create program schedule successfully")
        void shouldCreateProgramSchedule() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));
            when(instructorRepository.findById(1L)).thenReturn(Optional.of(testInstructor));
            when(programScheduleRepository.save(any(ProgramSchedule.class))).thenReturn(testSchedule);

            // When
            ProgramSchedule result = trainingProgramService.createProgramSchedule(testSchedule);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getStatus()).isEqualTo("SCHEDULED");
            verify(programScheduleRepository).save(any(ProgramSchedule.class));
        }

        @Test
        @DisplayName("should find schedules by training program")
        void shouldFindSchedulesByTrainingProgram() {
            // Given
            List<ProgramSchedule> schedules = Arrays.asList(testSchedule);
            when(programScheduleRepository.findByTrainingProgramId(1L)).thenReturn(schedules);

            // When
            List<ProgramSchedule> result = trainingProgramService.findSchedulesByProgram(1L);

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getTrainingProgram().getId()).isEqualTo(1L);
            verify(programScheduleRepository).findByTrainingProgramId(1L);
        }

        @Test
        @DisplayName("should find upcoming schedules")
        void shouldFindUpcomingSchedules() {
            // Given
            List<ProgramSchedule> schedules = Arrays.asList(testSchedule);
            when(programScheduleRepository.findByStartDateAfterAndStatus(any(LocalDate.class), eq("SCHEDULED")))
                .thenReturn(schedules);

            // When
            List<ProgramSchedule> result = trainingProgramService.findUpcomingSchedules();

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getStatus()).isEqualTo("SCHEDULED");
            verify(programScheduleRepository).findByStartDateAfterAndStatus(any(LocalDate.class), eq("SCHEDULED"));
        }

        @Test
        @DisplayName("should cancel program schedule")
        void shouldCancelProgramSchedule() {
            // Given
            when(programScheduleRepository.findById(1L)).thenReturn(Optional.of(testSchedule));
            when(programScheduleRepository.save(any(ProgramSchedule.class))).thenReturn(testSchedule);

            // When
            trainingProgramService.cancelProgramSchedule(1L);

            // Then
            verify(programScheduleRepository).save(argThat(schedule -> 
                "CANCELLED".equals(schedule.getStatus())
            ));
        }
    }

    @Nested
    @DisplayName("Student Enrollment Management")
    class StudentEnrollmentManagement {

        @Test
        @DisplayName("should get enrolled students for program")
        void shouldGetEnrolledStudentsForProgram() {
            // Given
            List<StudentEnrollment> enrollments = Arrays.asList(testEnrollment);
            when(studentEnrollmentRepository.findByTrainingProgramId(1L)).thenReturn(enrollments);

            // When
            List<StudentEnrollment> result = trainingProgramService.getEnrolledStudents(1L);

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getTrainingProgram().getId()).isEqualTo(1L);
            verify(studentEnrollmentRepository).findByTrainingProgramId(1L);
        }

        @Test
        @DisplayName("should count active enrollments")
        void shouldCountActiveEnrollments() {
            // Given
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "ACTIVE")).thenReturn(15L);

            // When
            long result = trainingProgramService.countActiveEnrollments(1L);

            // Then
            assertThat(result).isEqualTo(15L);
            verify(studentEnrollmentRepository).countByTrainingProgramIdAndStatus(1L, "ACTIVE");
        }

        @Test
        @DisplayName("should check if program is full")
        void shouldCheckIfProgramIsFull() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "ACTIVE")).thenReturn(20L);

            // When
            boolean result = trainingProgramService.isProgramFull(1L);

            // Then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("should return false when program is not full")
        void shouldReturnFalseWhenProgramIsNotFull() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "ACTIVE")).thenReturn(15L);

            // When
            boolean result = trainingProgramService.isProgramFull(1L);

            // Then
            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("Program Content Management")
    class ProgramContentManagement {

        @Test
        @DisplayName("should get quizzes for program")
        void shouldGetQuizzesForProgram() {
            // Given
            List<Quiz> quizzes = Arrays.asList(testQuiz);
            when(quizRepository.findByTrainingProgramId(1L)).thenReturn(quizzes);

            // When
            List<Quiz> result = trainingProgramService.getProgramQuizzes(1L);

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getTrainingProgram().getId()).isEqualTo(1L);
            verify(quizRepository).findByTrainingProgramId(1L);
        }

        @Test
        @DisplayName("should count program quizzes")
        void shouldCountProgramQuizzes() {
            // Given
            when(quizRepository.countByTrainingProgramId(1L)).thenReturn(5L);

            // When
            long result = trainingProgramService.countProgramQuizzes(1L);

            // Then
            assertThat(result).isEqualTo(5L);
            verify(quizRepository).countByTrainingProgramId(1L);
        }
    }

    @Nested
    @DisplayName("Program Statistics and Reporting")
    class ProgramStatisticsAndReporting {

        @Test
        @DisplayName("should get program statistics")
        void shouldGetProgramStatistics() {
            // Given
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "ACTIVE")).thenReturn(15L);
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "COMPLETED")).thenReturn(10L);
            when(programScheduleRepository.countByTrainingProgramId(1L)).thenReturn(3L);
            when(quizRepository.countByTrainingProgramId(1L)).thenReturn(5L);

            // When
            Map<String, Object> stats = trainingProgramService.getProgramStatistics(1L);

            // Then
            assertThat(stats).isNotNull();
            assertThat(stats.get("activeEnrollments")).isEqualTo(15L);
            assertThat(stats.get("completedEnrollments")).isEqualTo(10L);
            assertThat(stats.get("totalSchedules")).isEqualTo(3L);
            assertThat(stats.get("totalQuizzes")).isEqualTo(5L);
        }

        @Test
        @DisplayName("should calculate completion rate")
        void shouldCalculateCompletionRate() {
            // Given
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "COMPLETED")).thenReturn(12L);
            when(studentEnrollmentRepository.countByTrainingProgramId(1L)).thenReturn(20L);

            // When
            BigDecimal completionRate = trainingProgramService.calculateCompletionRate(1L);

            // Then
            assertThat(completionRate).isEqualTo(new BigDecimal("60.00"));
        }

        @Test
        @DisplayName("should get program capacity utilization")
        void shouldGetProgramCapacityUtilization() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "ACTIVE")).thenReturn(15L);

            // When
            BigDecimal utilization = trainingProgramService.getCapacityUtilization(1L);

            // Then
            assertThat(utilization).isEqualTo(new BigDecimal("75.00"));
        }
    }

    @Nested
    @DisplayName("Program Validation")
    class ProgramValidation {

        @Test
        @DisplayName("should validate program data successfully")
        void shouldValidateProgramDataSuccessfully() {
            // Given
            TrainingProgram validProgram = new TrainingProgram();
            validProgram.setProgramName("有効なプログラム");
            validProgram.setDescription("有効な説明");
            validProgram.setDurationDays(30);
            validProgram.setMaxParticipants(20);

            // When & Then
            assertThatCode(() -> trainingProgramService.validateProgramData(validProgram))
                .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("should throw exception for invalid program name")
        void shouldThrowExceptionForInvalidProgramName() {
            // Given
            testProgram.setProgramName("");

            // When & Then
            assertThatThrownBy(() -> trainingProgramService.validateProgramData(testProgram))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Program name cannot be empty");
        }

        @Test
        @DisplayName("should throw exception for invalid duration")
        void shouldThrowExceptionForInvalidDuration() {
            // Given
            testProgram.setDurationDays(0);

            // When & Then
            assertThatThrownBy(() -> trainingProgramService.validateProgramData(testProgram))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Duration must be positive");
        }

        @Test
        @DisplayName("should throw exception for invalid max participants")
        void shouldThrowExceptionForInvalidMaxParticipants() {
            // Given
            testProgram.setMaxParticipants(-1);

            // When & Then
            assertThatThrownBy(() -> trainingProgramService.validateProgramData(testProgram))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Max participants must be positive");
        }
    }

    @Nested
    @DisplayName("Business Logic Tests")
    class BusinessLogicTests {

        @Test
        @DisplayName("should check if program is available for enrollment")
        void shouldCheckIfProgramIsAvailableForEnrollment() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "ACTIVE")).thenReturn(15L);

            // When
            boolean result = trainingProgramService.isAvailableForEnrollment(1L);

            // Then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("should return false when program is full")
        void shouldReturnFalseWhenProgramIsFullForEnrollment() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));
            when(studentEnrollmentRepository.countByTrainingProgramIdAndStatus(1L, "ACTIVE")).thenReturn(20L);

            // When
            boolean result = trainingProgramService.isAvailableForEnrollment(1L);

            // Then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should check if program can be modified")
        void shouldCheckIfProgramCanBeModified() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));
            when(programScheduleRepository.existsByTrainingProgramIdAndStatus(1L, "IN_PROGRESS")).thenReturn(false);

            // When
            boolean result = trainingProgramService.canModifyProgram(1L);

            // Then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("should return false when program is in progress")
        void shouldReturnFalseWhenProgramIsInProgress() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testProgram));
            when(programScheduleRepository.existsByTrainingProgramIdAndStatus(1L, "IN_PROGRESS")).thenReturn(true);

            // When
            boolean result = trainingProgramService.canModifyProgram(1L);

            // Then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should find programs by instructor")
        void shouldFindProgramsByInstructor() {
            // Given
            when(programScheduleRepository.findByInstructorId(1L)).thenReturn(Arrays.asList(testSchedule));

            // When
            List<TrainingProgram> result = trainingProgramService.findProgramsByInstructor(1L);

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getId()).isEqualTo(1L);
        }
    }
}
