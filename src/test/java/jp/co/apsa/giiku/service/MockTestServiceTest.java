package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.MockTest;
import jp.co.apsa.giiku.domain.repository.MockTestRepository;
import jp.co.apsa.giiku.domain.repository.TrainingProgramRepository;
import jp.co.apsa.giiku.domain.repository.CompanyRepository;

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
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * {@link MockTestService} のテストクラス。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("MockTestService Tests")
class MockTestServiceTest {

    @Mock
    private MockTestRepository mockTestRepository;

    @Mock
    private TrainingProgramRepository trainingProgramRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private MockTestService mockTestService;

    private MockTest mockTest;

    @BeforeEach
    void setUp() {
        mockTest = new MockTest();
        mockTest.setTestId(1L);
        mockTest.setProgramId(10L);
        mockTest.setCompanyId(100L);
        mockTest.setTestType("PRACTICE");
        mockTest.setTitle("Java Mock Test");
        mockTest.setDurationMinutes(60);
        mockTest.setTotalQuestions(20);
        mockTest.setPassingScore(new BigDecimal("70.00"));
        mockTest.setStatus("ACTIVE");
        mockTest.setIsActive(true);
    }

    @Test
    @DisplayName("全てのモックテストを取得できる")
    void testFindAll() {
        when(mockTestRepository.findAll()).thenReturn(Arrays.asList(mockTest));

        List<MockTest> result = mockTestService.findAll();

        assertThat(result).hasSize(1);
        verify(mockTestRepository).findAll();
    }

    @Test
    @DisplayName("ページングでモックテストを取得できる")
    void testFindAllWithPage() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<MockTest> page = new PageImpl<>(Arrays.asList(mockTest), pageable, 1);
        when(mockTestRepository.findAll(pageable)).thenReturn(page);

        Page<MockTest> result = mockTestService.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(mockTestRepository).findAll(pageable);
    }

    @Test
    @DisplayName("IDでモックテストを取得できる")
    void testFindById() {
        when(mockTestRepository.findById(1L)).thenReturn(Optional.of(mockTest));

        Optional<MockTest> result = mockTestService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Java Mock Test");
        verify(mockTestRepository).findById(1L);
    }

    @Test
    @DisplayName("null ID 指定時に例外が発生する")
    void testFindByIdWithNull() {
        assertThatThrownBy(() -> mockTestService.findById(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("モックテストを保存できる")
    void testSave() {
        when(trainingProgramRepository.existsById(10L)).thenReturn(true);
        when(companyRepository.existsById(100L)).thenReturn(true);
        when(mockTestRepository.save(any(MockTest.class))).thenReturn(mockTest);

        MockTest toSave = new MockTest();
        toSave.setProgramId(10L);
        toSave.setCompanyId(100L);
        toSave.setTestType("PRACTICE");
        toSave.setTitle("New Test");
        toSave.setDurationMinutes(60);
        toSave.setTotalQuestions(20);
        toSave.setPassingScore(new BigDecimal("70.00"));
        toSave.setStatus("ACTIVE");

        MockTest result = mockTestService.save(toSave);

        assertThat(result).isNotNull();
        verify(mockTestRepository).save(any(MockTest.class));
    }

    @Test
    @DisplayName("モックテストを更新できる")
    void testUpdate() {
        MockTest updateData = new MockTest();
        updateData.setProgramId(10L);
        updateData.setCompanyId(100L);
        updateData.setTestType("FINAL");
        updateData.setTitle("Updated Test");
        updateData.setDurationMinutes(90);
        updateData.setTotalQuestions(30);
        updateData.setPassingScore(new BigDecimal("80.00"));
        updateData.setStatus("ACTIVE");
        updateData.setIsActive(true);

        when(mockTestRepository.findById(1L)).thenReturn(Optional.of(mockTest));
        when(mockTestRepository.save(any(MockTest.class))).thenReturn(mockTest);
        when(trainingProgramRepository.existsById(anyLong())).thenReturn(true);
        when(companyRepository.existsById(anyLong())).thenReturn(true);

        MockTest result = mockTestService.update(1L, updateData);

        assertThat(result.getTitle()).isEqualTo("Updated Test");
        verify(mockTestRepository).findById(1L);
        verify(mockTestRepository).save(any(MockTest.class));
    }

    @Test
    @DisplayName("モックテストを非アクティブ化できる")
    void testDeactivate() {
        when(mockTestRepository.findById(1L)).thenReturn(Optional.of(mockTest));
        when(mockTestRepository.save(any(MockTest.class))).thenReturn(mockTest);

        mockTestService.deactivate(1L);

        assertThat(mockTest.getIsActive()).isFalse();
        verify(mockTestRepository).save(mockTest);
    }

    @Test
    @DisplayName("モックテストを削除できる")
    void testDelete() {
        when(mockTestRepository.existsById(1L)).thenReturn(true);

        mockTestService.delete(1L);

        verify(mockTestRepository).deleteById(1L);
    }

    @Test
    @DisplayName("存在しないID削除時に例外が発生する")
    void testDeleteNotFound() {
        when(mockTestRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> mockTestService.delete(1L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("モックテストが見つかりません");
    }

    @Test
    @DisplayName("企業IDでモックテストを取得できる")
    void testFindByCompanyId() {
        when(mockTestRepository.findByCompanyIdAndIsActiveTrue(100L))
            .thenReturn(Arrays.asList(mockTest));

        List<MockTest> result = mockTestService.findByCompanyId(100L);

        assertThat(result).hasSize(1);
        verify(mockTestRepository).findByCompanyIdAndIsActiveTrue(100L);
    }

    @Test
    @DisplayName("フィルター条件でモックテストを検索できる")
    void testFindWithFilters() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<MockTest> page = new PageImpl<>(Arrays.asList(mockTest), pageable, 1);
        when(mockTestRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<MockTest> result = mockTestService.findWithFilters(100L, 10L, "PRACTICE", "BEGINNER", true, pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(mockTestRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    @DisplayName("モックテスト数をカウントできる")
    void testCountMethods() {
        when(mockTestRepository.count()).thenReturn(5L);
        when(mockTestRepository.countByIsActiveTrue()).thenReturn(3L);
        when(mockTestRepository.countByCompanyIdAndIsActiveTrue(100L)).thenReturn(2L);

        assertThat(mockTestService.countAll()).isEqualTo(5L);
        assertThat(mockTestService.countActive()).isEqualTo(3L);
        assertThat(mockTestService.countByCompany(100L)).isEqualTo(2L);
    }
}
