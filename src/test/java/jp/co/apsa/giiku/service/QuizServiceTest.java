package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.Quiz;
import jp.co.apsa.giiku.domain.entity.QuestionBank;
import jp.co.apsa.giiku.domain.entity.TrainingProgram;
import jp.co.apsa.giiku.domain.entity.StudentEnrollment;
import jp.co.apsa.giiku.domain.entity.Grade;
import jp.co.apsa.giiku.domain.entity.Student;
import jp.co.apsa.giiku.domain.repository.QuizRepository;
import jp.co.apsa.giiku.domain.repository.QuestionBankRepository;
import jp.co.apsa.giiku.domain.repository.TrainingProgramRepository;
import jp.co.apsa.giiku.domain.repository.StudentEnrollmentRepository;
import jp.co.apsa.giiku.domain.repository.GradeRepository;
import jp.co.apsa.giiku.domain.repository.StudentRepository;
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

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("QuizService Tests")
class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionBankRepository questionBankRepository;

    @Mock
    private TrainingProgramRepository trainingProgramRepository;

    @Mock
    private StudentEnrollmentRepository studentEnrollmentRepository;

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private QuizService quizService;

    private Quiz testQuiz;
    private QuestionBank testQuestionBank;
    private TrainingProgram testTrainingProgram;
    private Student testStudent;
    private StudentEnrollment testEnrollment;
    private Grade testGrade;

    @BeforeEach
    void setUp() {
        testTrainingProgram = new TrainingProgram();
        testTrainingProgram.setId(1L);
        testTrainingProgram.setProgramName("Java基礎研修");
        testTrainingProgram.setDescription("Java基礎研修プログラム");
        testTrainingProgram.setDurationDays(30);
        testTrainingProgram.setStatus("ACTIVE");

        testQuestionBank = new QuestionBank();
        testQuestionBank.setId(1L);
        testQuestionBank.setTitle("Java基礎問題集");
        testQuestionBank.setDescription("Java基礎問題集説明");
        testQuestionBank.setSubject("Java");
        testQuestionBank.setDifficulty("INTERMEDIATE");
        testQuestionBank.setQuestionCount(50);
        testQuestionBank.setCreatedAt(LocalDateTime.now());

        testQuiz = new Quiz();
        testQuiz.setId(1L);
        testQuiz.setQuizName("Java基礎テスト");
        testQuiz.setDescription("Java基礎知識確認テスト");
        testQuiz.setTrainingProgram(testTrainingProgram);
        testQuiz.setQuestionBank(testQuestionBank);
        testQuiz.setQuestionCount(20);
        testQuiz.setTimeLimit(60);
        testQuiz.setPassingScore(new BigDecimal("70.00"));
        testQuiz.setStatus("ACTIVE");
        testQuiz.setCreatedAt(LocalDateTime.now());
        testQuiz.setUpdatedAt(LocalDateTime.now());

        testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setStudentId("STU001");
        testStudent.setFirstName("太郎");
        testStudent.setLastName("田中");
        testStudent.setEmail("tanaka@example.com");

        testEnrollment = new StudentEnrollment();
        testEnrollment.setId(1L);
        testEnrollment.setStudent(testStudent);
        testEnrollment.setTrainingProgram(testTrainingProgram);
        testEnrollment.setEnrollmentDate(LocalDateTime.now());
        testEnrollment.setStatus("ACTIVE");

        testGrade = new Grade();
        testGrade.setId(1L);
        testGrade.setStudent(testStudent);
        testGrade.setQuiz(testQuiz);
        testGrade.setScore(new BigDecimal("85.50"));
        testGrade.setMaxScore(new BigDecimal("100.00"));
        testGrade.setPassed(true);
        testGrade.setCompletedAt(LocalDateTime.now());
    }

    @Nested
    @DisplayName("Quiz CRUD Operations")
    class QuizCrudOperations {

        @Test
        @DisplayName("should create quiz successfully")
        void shouldCreateQuiz() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(testTrainingProgram));
            when(questionBankRepository.findById(1L)).thenReturn(Optional.of(testQuestionBank));
            when(quizRepository.save(any(Quiz.class))).thenReturn(testQuiz);

            // When
            Quiz result = quizService.createQuiz(testQuiz);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getQuizName()).isEqualTo("Java基礎テスト");
            assertThat(result.getStatus()).isEqualTo("ACTIVE");
            verify(quizRepository).save(any(Quiz.class));
        }

        @Test
        @DisplayName("should throw exception when creating quiz with invalid training program")
        void shouldThrowExceptionWhenCreatingQuizWithInvalidTrainingProgram() {
            // Given
            when(trainingProgramRepository.findById(1L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> quizService.createQuiz(testQuiz))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Training program not found");
        }

        @Test
        @DisplayName("should find quiz by id")
        void shouldFindQuizById() {
            // Given
            when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));

            // When
            Optional<Quiz> result = quizService.findById(1L);

            // Then
            assertThat(result).isPresent();
            assertThat(result.get().getQuizName()).isEqualTo("Java基礎テスト");
            verify(quizRepository).findById(1L);
        }

        @Test
        @DisplayName("should return empty when quiz not found")
        void shouldReturnEmptyWhenQuizNotFound() {
            // Given
            when(quizRepository.findById(1L)).thenReturn(Optional.empty());

            // When
            Optional<Quiz> result = quizService.findById(1L);

            // Then
            assertThat(result).isEmpty();
            verify(quizRepository).findById(1L);
        }

        @Test
        @DisplayName("should update quiz successfully")
        void shouldUpdateQuiz() {
            // Given
            when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));
            when(quizRepository.save(any(Quiz.class))).thenReturn(testQuiz);

            testQuiz.setQuizName("更新されたJava基礎テスト");
            testQuiz.setTimeLimit(90);

            // When
            Quiz result = quizService.updateQuiz(1L, testQuiz);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getQuizName()).isEqualTo("更新されたJava基礎テスト");
            assertThat(result.getTimeLimit()).isEqualTo(90);
            verify(quizRepository).save(any(Quiz.class));
        }

        @Test
        @DisplayName("should throw exception when updating non-existent quiz")
        void shouldThrowExceptionWhenUpdatingNonExistentQuiz() {
            // Given
            when(quizRepository.findById(1L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> quizService.updateQuiz(1L, testQuiz))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Quiz not found");
        }

        @Test
        @DisplayName("should delete quiz successfully")
        void shouldDeleteQuiz() {
            // Given
            when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));

            // When
            quizService.deleteQuiz(1L);

            // Then
            verify(quizRepository).delete(testQuiz);
        }

        @Test
        @DisplayName("should throw exception when deleting non-existent quiz")
        void shouldThrowExceptionWhenDeletingNonExistentQuiz() {
            // Given
            when(quizRepository.findById(1L)).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> quizService.deleteQuiz(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Quiz not found");
        }
    }

    @Nested
    @DisplayName("Quiz Search and Listing")
    class QuizSearchAndListing {

        @Test
        @DisplayName("should find all quizzes with pagination")
        void shouldFindAllQuizzesWithPagination() {
            // Given
            Pageable pageable = PageRequest.of(0, 10);
            List<Quiz> quizzes = Arrays.asList(testQuiz);
            Page<Quiz> page = new PageImpl<>(quizzes, pageable, 1);
            when(quizRepository.findAll(pageable)).thenReturn(page);

            // When
            Page<Quiz> result = quizService.findAllQuizzes(pageable);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getContent()).hasSize(1);
            assertThat(result.getContent().get(0).getQuizName()).isEqualTo("Java基礎テスト");
            verify(quizRepository).findAll(pageable);
        }

        @Test
        @DisplayName("should find quizzes by training program")
        void shouldFindQuizzesByTrainingProgram() {
            // Given
            List<Quiz> quizzes = Arrays.asList(testQuiz);
            when(quizRepository.findByTrainingProgramId(1L)).thenReturn(quizzes);

            // When
            List<Quiz> result = quizService.findQuizzesByTrainingProgram(1L);

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getQuizName()).isEqualTo("Java基礎テスト");
            verify(quizRepository).findByTrainingProgramId(1L);
        }

        @Test
        @DisplayName("should find active quizzes")
        void shouldFindActiveQuizzes() {
            // Given
            List<Quiz> quizzes = Arrays.asList(testQuiz);
            when(quizRepository.findByStatus("ACTIVE")).thenReturn(quizzes);

            // When
            List<Quiz> result = quizService.findActiveQuizzes();

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getStatus()).isEqualTo("ACTIVE");
            verify(quizRepository).findByStatus("ACTIVE");
        }

        @Test
        @DisplayName("should search quizzes by name")
        void shouldSearchQuizzesByName() {
            // Given
            String searchTerm = "Java";
            List<Quiz> quizzes = Arrays.asList(testQuiz);
            when(quizRepository.findByQuizNameContainingIgnoreCase(searchTerm)).thenReturn(quizzes);

            // When
            List<Quiz> result = quizService.searchQuizzesByName(searchTerm);

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getQuizName()).contains("Java");
            verify(quizRepository).findByQuizNameContainingIgnoreCase(searchTerm);
        }
    }

    @Nested
    @DisplayName("Quiz Execution and Grading")
    class QuizExecutionAndGrading {

        @Test
        @DisplayName("should start quiz session for student")
        void shouldStartQuizSessionForStudent() {
            // Given
            when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));
            when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
            when(studentEnrollmentRepository.findByStudentIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.of(testEnrollment));

            // When
            Quiz result = quizService.startQuizSession(1L, 1L);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getQuizName()).isEqualTo("Java基礎テスト");
            verify(quizRepository).findById(1L);
            verify(studentRepository).findById(1L);
        }

        @Test
        @DisplayName("should throw exception when student not enrolled in training program")
        void shouldThrowExceptionWhenStudentNotEnrolled() {
            // Given
            when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));
            when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
            when(studentEnrollmentRepository.findByStudentIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> quizService.startQuizSession(1L, 1L))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Student not enrolled");
        }

        @Test
        @DisplayName("should submit quiz answers and calculate grade")
        void shouldSubmitQuizAnswersAndCalculateGrade() {
            // Given
            Map<String, Object> answers = new HashMap<>();
            answers.put("question1", "A");
            answers.put("question2", "B");
            answers.put("question3", "C");

            when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));
            when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
            when(gradeRepository.save(any(Grade.class))).thenReturn(testGrade);

            // When
            Grade result = quizService.submitQuizAnswers(1L, 1L, answers);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getScore()).isEqualTo(new BigDecimal("85.50"));
            assertThat(result.isPassed()).isTrue();
            verify(gradeRepository).save(any(Grade.class));
        }

        @Test
        @DisplayName("should calculate correct score based on answers")
        void shouldCalculateCorrectScoreBasedOnAnswers() {
            // Given
            Map<String, Object> answers = new HashMap<>();
            answers.put("question1", "A");
            answers.put("question2", "B");

            // When
            BigDecimal score = quizService.calculateScore(answers, testQuiz);

            // Then
            assertThat(score).isNotNull();
            assertThat(score).isGreaterThanOrEqualTo(BigDecimal.ZERO);
            assertThat(score).isLessThanOrEqualTo(new BigDecimal("100.00"));
        }

        @Test
        @DisplayName("should determine pass/fail based on passing score")
        void shouldDeterminePassFailBasedOnPassingScore() {
            // Given
            BigDecimal score = new BigDecimal("75.00");
            BigDecimal passingScore = new BigDecimal("70.00");

            // When
            boolean passed = quizService.isPassed(score, passingScore);

            // Then
            assertThat(passed).isTrue();
        }

        @Test
        @DisplayName("should return false for score below passing threshold")
        void shouldReturnFalseForScoreBelowPassingThreshold() {
            // Given
            BigDecimal score = new BigDecimal("65.00");
            BigDecimal passingScore = new BigDecimal("70.00");

            // When
            boolean passed = quizService.isPassed(score, passingScore);

            // Then
            assertThat(passed).isFalse();
        }
    }

    @Nested
    @DisplayName("Grade Management")
    class GradeManagement {

        @Test
        @DisplayName("should find grades by student")
        void shouldFindGradesByStudent() {
            // Given
            List<Grade> grades = Arrays.asList(testGrade);
            when(gradeRepository.findByStudentId(1L)).thenReturn(grades);

            // When
            List<Grade> result = quizService.findGradesByStudent(1L);

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getScore()).isEqualTo(new BigDecimal("85.50"));
            verify(gradeRepository).findByStudentId(1L);
        }

        @Test
        @DisplayName("should find grades by quiz")
        void shouldFindGradesByQuiz() {
            // Given
            List<Grade> grades = Arrays.asList(testGrade);
            when(gradeRepository.findByQuizId(1L)).thenReturn(grades);

            // When
            List<Grade> result = quizService.findGradesByQuiz(1L);

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getQuiz().getId()).isEqualTo(1L);
            verify(gradeRepository).findByQuizId(1L);
        }

        @Test
        @DisplayName("should find grade by student and quiz")
        void shouldFindGradeByStudentAndQuiz() {
            // Given
            when(gradeRepository.findByStudentIdAndQuizId(1L, 1L)).thenReturn(Optional.of(testGrade));

            // When
            Optional<Grade> result = quizService.findGradeByStudentAndQuiz(1L, 1L);

            // Then
            assertThat(result).isPresent();
            assertThat(result.get().getScore()).isEqualTo(new BigDecimal("85.50"));
            verify(gradeRepository).findByStudentIdAndQuizId(1L, 1L);
        }

        @Test
        @DisplayName("should calculate average score for quiz")
        void shouldCalculateAverageScoreForQuiz() {
            // Given
            when(gradeRepository.findAverageScoreByQuizId(1L)).thenReturn(new BigDecimal("78.25"));

            // When
            BigDecimal result = quizService.calculateAverageScoreForQuiz(1L);

            // Then
            assertThat(result).isEqualTo(new BigDecimal("78.25"));
            verify(gradeRepository).findAverageScoreByQuizId(1L);
        }

        @Test
        @DisplayName("should get passing rate for quiz")
        void shouldGetPassingRateForQuiz() {
            // Given
            when(gradeRepository.countByQuizIdAndPassed(1L, true)).thenReturn(8L);
            when(gradeRepository.countByQuizId(1L)).thenReturn(10L);

            // When
            BigDecimal result = quizService.getPassingRateForQuiz(1L);

            // Then
            assertThat(result).isEqualTo(new BigDecimal("80.00"));
            verify(gradeRepository).countByQuizIdAndPassed(1L, true);
            verify(gradeRepository).countByQuizId(1L);
        }
    }

    @Nested
    @DisplayName("Quiz Validation")
    class QuizValidation {

        @Test
        @DisplayName("should validate quiz data successfully")
        void shouldValidateQuizDataSuccessfully() {
            // Given
            Quiz validQuiz = new Quiz();
            validQuiz.setQuizName("有効なクイズ");
            validQuiz.setDescription("有効な説明");
            validQuiz.setQuestionCount(10);
            validQuiz.setTimeLimit(60);
            validQuiz.setPassingScore(new BigDecimal("70.00"));

            // When & Then
            assertThatCode(() -> quizService.validateQuizData(validQuiz))
                .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("should throw exception for invalid quiz name")
        void shouldThrowExceptionForInvalidQuizName() {
            // Given
            testQuiz.setQuizName("");

            // When & Then
            assertThatThrownBy(() -> quizService.validateQuizData(testQuiz))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Quiz name cannot be empty");
        }

        @Test
        @DisplayName("should throw exception for invalid question count")
        void shouldThrowExceptionForInvalidQuestionCount() {
            // Given
            testQuiz.setQuestionCount(0);

            // When & Then
            assertThatThrownBy(() -> quizService.validateQuizData(testQuiz))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Question count must be positive");
        }

        @Test
        @DisplayName("should throw exception for invalid time limit")
        void shouldThrowExceptionForInvalidTimeLimit() {
            // Given
            testQuiz.setTimeLimit(-10);

            // When & Then
            assertThatThrownBy(() -> quizService.validateQuizData(testQuiz))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Time limit must be positive");
        }

        @Test
        @DisplayName("should throw exception for invalid passing score")
        void shouldThrowExceptionForInvalidPassingScore() {
            // Given
            testQuiz.setPassingScore(new BigDecimal("150.00"));

            // When & Then
            assertThatThrownBy(() -> quizService.validateQuizData(testQuiz))
                .isInstanceOf(InvalidDataException.class)
                .hasMessageContaining("Passing score must be between 0 and 100");
        }
    }

    @Nested
    @DisplayName("Business Logic Tests")
    class BusinessLogicTests {

        @Test
        @DisplayName("should check if quiz is available for student")
        void shouldCheckIfQuizIsAvailableForStudent() {
            // Given
            when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));
            when(studentEnrollmentRepository.findByStudentIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.of(testEnrollment));

            // When
            boolean result = quizService.isQuizAvailableForStudent(1L, 1L);

            // Then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("should return false when quiz not available for student")
        void shouldReturnFalseWhenQuizNotAvailableForStudent() {
            // Given
            when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));
            when(studentEnrollmentRepository.findByStudentIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.empty());

            // When
            boolean result = quizService.isQuizAvailableForStudent(1L, 1L);

            // Then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should check if student already completed quiz")
        void shouldCheckIfStudentAlreadyCompletedQuiz() {
            // Given
            when(gradeRepository.findByStudentIdAndQuizId(1L, 1L)).thenReturn(Optional.of(testGrade));

            // When
            boolean result = quizService.hasStudentCompletedQuiz(1L, 1L);

            // Then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("should return false when student has not completed quiz")
        void shouldReturnFalseWhenStudentHasNotCompletedQuiz() {
            // Given
            when(gradeRepository.findByStudentIdAndQuizId(1L, 1L)).thenReturn(Optional.empty());

            // When
            boolean result = quizService.hasStudentCompletedQuiz(1L, 1L);

            // Then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should get quiz statistics")
        void shouldGetQuizStatistics() {
            // Given
            when(gradeRepository.countByQuizId(1L)).thenReturn(10L);
            when(gradeRepository.countByQuizIdAndPassed(1L, true)).thenReturn(7L);
            when(gradeRepository.findAverageScoreByQuizId(1L)).thenReturn(new BigDecimal("73.50"));

            // When
            Map<String, Object> stats = quizService.getQuizStatistics(1L);

            // Then
            assertThat(stats).isNotNull();
            assertThat(stats.get("totalAttempts")).isEqualTo(10L);
            assertThat(stats.get("passedCount")).isEqualTo(7L);
            assertThat(stats.get("averageScore")).isEqualTo(new BigDecimal("73.50"));
            assertThat(stats.get("passingRate")).isEqualTo(new BigDecimal("70.00"));
        }
    }
}
