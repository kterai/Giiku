package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.QuestionBank;
import jp.co.apsa.giiku.domain.repository.QuestionBankRepository;
import jp.co.apsa.giiku.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("QuestionBankService Tests")
class QuestionBankServiceTest {

    @Mock
    private QuestionBankRepository questionBankRepository;

    @InjectMocks
    private QuestionBankService questionBankService;

    private QuestionBank testQuestion1;
    private QuestionBank testQuestion2;
    private List<QuestionBank> testQuestions;

    @BeforeEach
    void setUp() {
        testQuestion1 = new QuestionBank();
        testQuestion1.setId(1L);
        testQuestion1.setQuestionText("Java is object-oriented language?");
        testQuestion1.setQuestionType("TRUE_FALSE");
        testQuestion1.setCategory("Java Basics");
        testQuestion1.setDifficultyLevel("EASY");
        testQuestion1.setCorrectAnswer("true");
        testQuestion1.setCompanyId(100L);
        testQuestion1.setInstructorId(10L);
        testQuestion1.setIsActive(true);

        testQuestion2 = new QuestionBank();
        testQuestion2.setId(2L);
        testQuestion2.setQuestionText("What is polymorphism?");
        testQuestion2.setQuestionType("MULTIPLE_CHOICE");
        testQuestion2.setCategory("OOP Concepts");
        testQuestion2.setDifficultyLevel("MEDIUM");
        testQuestion2.setCorrectAnswer("Dynamic method binding");
        testQuestion2.setCompanyId(100L);
        testQuestion2.setInstructorId(11L);
        testQuestion2.setIsActive(true);

        testQuestions = Arrays.asList(testQuestion1, testQuestion2);
    }

    @Test
    @DisplayName("全問題をページング形式で取得できる")
    void testFindAllWithPagination() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<QuestionBank> page = new PageImpl<>(testQuestions, pageable, testQuestions.size());

        when(questionBankRepository.findAll(pageable)).thenReturn(page);

        Page<QuestionBank> result = questionBankService.findAll(pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).containsExactly(testQuestion1, testQuestion2);
        verify(questionBankRepository).findAll(pageable);
    }

    @Test
    @DisplayName("全問題をリスト形式で取得できる")
    void testFindAll() {
        when(questionBankRepository.findAll()).thenReturn(testQuestions);

        List<QuestionBank> result = questionBankService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(testQuestion1, testQuestion2);
        verify(questionBankRepository).findAll();
    }

    @Test
    @DisplayName("IDで問題を取得できる")
    void testFindById() {
        when(questionBankRepository.findById(1L)).thenReturn(Optional.of(testQuestion1));

        QuestionBank result = questionBankService.findById(1L);

        assertThat(result).isEqualTo(testQuestion1);
        verify(questionBankRepository).findById(1L);
    }

    @Test
    @DisplayName("存在しないIDで問題を取得する場合例外が発生する")
    void testFindByIdNotFound() {
        when(questionBankRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> questionBankService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("問題が見つかりません");

        verify(questionBankRepository).findById(99L);
    }

    @Test
    @DisplayName("カテゴリで問題を取得できる")
    void testFindByCategory() {
        List<QuestionBank> javaQuestions = Arrays.asList(testQuestion1);
        when(questionBankRepository.findByCategory("Java Basics")).thenReturn(javaQuestions);

        List<QuestionBank> result = questionBankService.findByCategory("Java Basics");

        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(testQuestion1);
        verify(questionBankRepository).findByCategory("Java Basics");
    }

    @Test
    @DisplayName("難易度で問題を取得できる")
    void testFindByDifficultyLevel() {
        List<QuestionBank> easyQuestions = Arrays.asList(testQuestion1);
        when(questionBankRepository.findByDifficultyLevel("EASY")).thenReturn(easyQuestions);

        List<QuestionBank> result = questionBankService.findByDifficultyLevel("EASY");

        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(testQuestion1);
        verify(questionBankRepository).findByDifficultyLevel("EASY");
    }

    @Test
    @DisplayName("問題タイプで問題を取得できる")
    void testFindByQuestionType() {
        List<QuestionBank> trueFalseQuestions = Arrays.asList(testQuestion1);
        when(questionBankRepository.findByQuestionType("TRUE_FALSE")).thenReturn(trueFalseQuestions);

        List<QuestionBank> result = questionBankService.findByQuestionType("TRUE_FALSE");

        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(testQuestion1);
        verify(questionBankRepository).findByQuestionType("TRUE_FALSE");
    }

    @Test
    @DisplayName("ランダムに問題を取得できる")
    void testFindRandomQuestions() {
        when(questionBankRepository.findRandomQuestions(5)).thenReturn(testQuestions);

        List<QuestionBank> result = questionBankService.findRandomQuestions(5);

        assertThat(result).hasSize(2);
        verify(questionBankRepository).findRandomQuestions(5);
    }

    @Test
    @DisplayName("問題を作成できる")
    void testCreate() {
        QuestionBank newQuestion = new QuestionBank();
        newQuestion.setQuestionText("New question");
        newQuestion.setQuestionType("MULTIPLE_CHOICE");
        newQuestion.setCategory("Test Category");
        newQuestion.setDifficultyLevel("MEDIUM");
        newQuestion.setCorrectAnswer("answer");
        newQuestion.setCompanyId(100L);
        newQuestion.setInstructorId(10L);

        QuestionBank savedQuestion = new QuestionBank();
        savedQuestion.setId(3L);
        savedQuestion.setQuestionText("New question");
        savedQuestion.setCreatedAt(LocalDateTime.now());

        when(questionBankRepository.save(any(QuestionBank.class))).thenReturn(savedQuestion);

        QuestionBank result = questionBankService.create(newQuestion);

        assertThat(result.getId()).isEqualTo(3L);
        verify(questionBankRepository).save(any(QuestionBank.class));
    }

    @Test
    @DisplayName("無効な問題作成時に例外が発生する")
    void testCreateWithInvalidData() {
        QuestionBank invalidQuestion = new QuestionBank();
        // 必須フィールドが空

        assertThatThrownBy(() -> questionBankService.create(invalidQuestion))
                .isInstanceOf(IllegalArgumentException.class);

        verify(questionBankRepository, never()).save(any());
    }

    @Test
    @DisplayName("問題を更新できる")
    void testUpdate() {
        QuestionBank updatedData = new QuestionBank();
        updatedData.setQuestionText("Updated question");
        updatedData.setQuestionType("ESSAY");
        updatedData.setCategory("Updated Category");
        updatedData.setDifficultyLevel("HARD");
        updatedData.setCorrectAnswer("updated answer");
        updatedData.setIsActive(true);

        when(questionBankRepository.findById(1L)).thenReturn(Optional.of(testQuestion1));
        when(questionBankRepository.save(any(QuestionBank.class))).thenReturn(testQuestion1);

        QuestionBank result = questionBankService.update(1L, updatedData);

        assertThat(result.getQuestionText()).isEqualTo("Updated question");
        verify(questionBankRepository).findById(1L);
        verify(questionBankRepository).save(any(QuestionBank.class));
    }

    @Test
    @DisplayName("問題を削除できる")
    void testDelete() {
        when(questionBankRepository.findById(1L)).thenReturn(Optional.of(testQuestion1));
        doNothing().when(questionBankRepository).delete(testQuestion1);

        questionBankService.delete(1L);

        verify(questionBankRepository).findById(1L);
        verify(questionBankRepository).delete(testQuestion1);
    }

    @Test
    @DisplayName("問題を非アクティブ化できる")
    void testDeactivate() {
        when(questionBankRepository.findById(1L)).thenReturn(Optional.of(testQuestion1));
        when(questionBankRepository.save(any(QuestionBank.class))).thenReturn(testQuestion1);

        QuestionBank result = questionBankService.deactivate(1L);

        assertThat(result.getIsActive()).isFalse();
        verify(questionBankRepository).findById(1L);
        verify(questionBankRepository).save(any(QuestionBank.class));
    }

    @Test
    @DisplayName("アクティブな問題数を取得できる")
    void testCountActiveQuestions() {
        when(questionBankRepository.countByIsActiveTrue()).thenReturn(5L);

        long count = questionBankService.countActiveQuestions();

        assertThat(count).isEqualTo(5L);
        verify(questionBankRepository).countByIsActiveTrue();
    }

    @Test
    @DisplayName("カテゴリ別問題数を取得できる")
    void testCountByCategory() {
        when(questionBankRepository.countByCategory("Java Basics")).thenReturn(3L);

        long count = questionBankService.countByCategory("Java Basics");

        assertThat(count).isEqualTo(3L);
        verify(questionBankRepository).countByCategory("Java Basics");
    }

    @Test
    @DisplayName("難易度別問題数を取得できる")
    void testCountByDifficultyLevel() {
        when(questionBankRepository.countByDifficultyLevel("EASY")).thenReturn(2L);

        long count = questionBankService.countByDifficultyLevel("EASY");

        assertThat(count).isEqualTo(2L);
        verify(questionBankRepository).countByDifficultyLevel("EASY");
    }

    @Test
    @DisplayName("講師別問題数を取得できる")
    void testCountByInstructorId() {
        when(questionBankRepository.countByInstructorId(10L)).thenReturn(4L);

        long count = questionBankService.countByInstructorId(10L);

        assertThat(count).isEqualTo(4L);
        verify(questionBankRepository).countByInstructorId(10L);
    }

    @Test
    @DisplayName("企業別問題数を取得できる")
    void testCountByCompanyId() {
        when(questionBankRepository.countByCompanyId(100L)).thenReturn(8L);

        long count = questionBankService.countByCompanyId(100L);

        assertThat(count).isEqualTo(8L);
        verify(questionBankRepository).countByCompanyId(100L);
    }

    @Test
    @DisplayName("問題テキストで検索できる")
    void testFindByQuestionTextContaining() {
        List<QuestionBank> searchResults = Arrays.asList(testQuestion1);
        when(questionBankRepository.findByQuestionTextContainingIgnoreCase("Java")).thenReturn(searchResults);

        List<QuestionBank> result = questionBankService.findByQuestionTextContaining("Java");

        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(testQuestion1);
        verify(questionBankRepository).findByQuestionTextContainingIgnoreCase("Java");
    }

    @Test
    @DisplayName("企業別問題を取得できる")
    void testFindByCompanyId() {
        when(questionBankRepository.findByCompanyId(100L)).thenReturn(testQuestions);

        List<QuestionBank> result = questionBankService.findByCompanyId(100L);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(testQuestion1, testQuestion2);
        verify(questionBankRepository).findByCompanyId(100L);
    }

    @Test
    @DisplayName("講師別問題を取得できる")
    void testFindByInstructorId() {
        List<QuestionBank> instructorQuestions = Arrays.asList(testQuestion1);
        when(questionBankRepository.findByInstructorId(10L)).thenReturn(instructorQuestions);

        List<QuestionBank> result = questionBankService.findByInstructorId(10L);

        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(testQuestion1);
        verify(questionBankRepository).findByInstructorId(10L);
    }

    @Test
    @DisplayName("アクティブな問題を取得できる")
    void testFindActiveQuestions() {
        when(questionBankRepository.findByIsActiveTrue()).thenReturn(testQuestions);

        List<QuestionBank> result = questionBankService.findActiveQuestions();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(testQuestion1, testQuestion2);
        verify(questionBankRepository).findByIsActiveTrue();
    }
}
