package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.MockTest;
import jp.co.apsa.giiku.domain.entity.TrainingProgram;
import jp.co.apsa.giiku.domain.entity.Company;
import jp.co.apsa.giiku.domain.repository.MockTestRepository;
import jp.co.apsa.giiku.domain.repository.TrainingProgramRepository;
import jp.co.apsa.giiku.domain.repository.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MockTestサービスクラス
 * モックテストの管理機能を提供
 */
@Service
@Transactional
public class MockTestService {

    @Autowired
    private MockTestRepository mockTestRepository;

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * 全てのモックテストを取得
     */
    @Transactional(readOnly = true)
    public List<MockTest> findAll() {
        return mockTestRepository.findAll();
    }

    /**
     * IDでモックテストを取得
     */
    @Transactional(readOnly = true)
    public Optional<MockTest> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }
        return mockTestRepository.findById(id);
    }

    /**
     * モックテストを保存
     */
    public MockTest save(MockTest mockTest) {
        validateMockTest(mockTest);

        if (mockTest.getId() == null) {
            mockTest.setCreatedAt(LocalDateTime.now());
        }
        mockTest.setUpdatedAt(LocalDateTime.now());

        return mockTestRepository.save(mockTest);
    }

    /**
     * モックテストを更新
     */
    public MockTest update(Long id, MockTest mockTest) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        MockTest existingTest = mockTestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("モックテストが見つかりません: " + id));

        validateMockTest(mockTest);

        // 基本情報の更新
        existingTest.setTitle(mockTest.getTitle());
        existingTest.setDescription(mockTest.getDescription());
        existingTest.setTestType(mockTest.getTestType());
        existingTest.setDuration(mockTest.getDuration());
        existingTest.setTotalQuestions(mockTest.getTotalQuestions());
        existingTest.setPassingScore(mockTest.getPassingScore());
        existingTest.setDifficultyLevel(mockTest.getDifficultyLevel());
        existingTest.setIsActive(mockTest.getIsActive());
        existingTest.setUpdatedAt(LocalDateTime.now());

        return mockTestRepository.save(existingTest);
    }

    /**
     * モックテストを論理削除
     */
    public void deactivate(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        MockTest mockTest = mockTestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("モックテストが見つかりません: " + id));

        mockTest.setIsActive(false);
        mockTest.setUpdatedAt(LocalDateTime.now());
        mockTestRepository.save(mockTest);
    }

    /**
     * モックテストを物理削除
     */
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        if (!mockTestRepository.existsById(id)) {
            throw new RuntimeException("モックテストが見つかりません: " + id);
        }

        mockTestRepository.deleteById(id);
    }

    /**
     * 企業IDでモックテストを検索
     */
    @Transactional(readOnly = true)
    public List<MockTest> findByCompanyId(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("企業IDは必須です");
        }
        return mockTestRepository.findByCompanyIdAndIsActiveTrue(companyId);
    }

    /**
     * プログラムIDでモックテストを検索
     */
    @Transactional(readOnly = true)
    public List<MockTest> findByProgramId(Long programId) {
        if (programId == null) {
            throw new IllegalArgumentException("プログラムIDは必須です");
        }
        return mockTestRepository.findByProgramIdAndIsActiveTrue(programId);
    }

    /**
     * テストタイプで検索
     */
    @Transactional(readOnly = true)
    public List<MockTest> findByTestType(String testType) {
        if (!StringUtils.hasText(testType)) {
            throw new IllegalArgumentException("テストタイプは必須です");
        }
        return mockTestRepository.findByTestTypeAndIsActiveTrue(testType);
    }

    /**
     * 難易度レベルで検索
     */
    @Transactional(readOnly = true)
    public List<MockTest> findByDifficultyLevel(String difficultyLevel) {
        if (!StringUtils.hasText(difficultyLevel)) {
            throw new IllegalArgumentException("難易度レベルは必須です");
        }
        return mockTestRepository.findByDifficultyLevelAndIsActiveTrue(difficultyLevel);
    }

    /**
     * アクティブなモックテストを取得
     */
    @Transactional(readOnly = true)
    public List<MockTest> findActiveTests() {
        return mockTestRepository.findByIsActiveTrueOrderByCreatedAtDesc();
    }

    /**
     * タイトルで検索（部分一致）
     */
    @Transactional(readOnly = true)
    public List<MockTest> findByTitleContaining(String title) {
        if (!StringUtils.hasText(title)) {
            throw new IllegalArgumentException("タイトルは必須です");
        }
        return mockTestRepository.findByTitleContainingIgnoreCaseAndIsActiveTrue(title);
    }

    /**
     * 複合条件での検索
     */
    @Transactional(readOnly = true)
    public Page<MockTest> findWithFilters(Long companyId, Long programId, String testType, 
                                         String difficultyLevel, Boolean isActive, 
                                         Pageable pageable) {

        Specification<MockTest> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (companyId != null) {
                predicates.add(criteriaBuilder.equal(root.get("companyId"), companyId));
            }

            if (programId != null) {
                predicates.add(criteriaBuilder.equal(root.get("programId"), programId));
            }

            if (StringUtils.hasText(testType)) {
                predicates.add(criteriaBuilder.equal(root.get("testType"), testType));
            }

            if (StringUtils.hasText(difficultyLevel)) {
                predicates.add(criteriaBuilder.equal(root.get("difficultyLevel"), difficultyLevel));
            }

            if (isActive != null) {
                predicates.add(criteriaBuilder.equal(root.get("isActive"), isActive));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return mockTestRepository.findAll(spec, pageable);
    }

    /**
     * モックテスト数をカウント
     */
    @Transactional(readOnly = true)
    public long countAll() {
        return mockTestRepository.count();
    }

    /**
     * アクティブなモックテスト数をカウント
     */
    @Transactional(readOnly = true)
    public long countActive() {
        return mockTestRepository.countByIsActiveTrue();
    }

    /**
     * 企業別のモックテスト数をカウント
     */
    @Transactional(readOnly = true)
    public long countByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("企業IDは必須です");
        }
        return mockTestRepository.countByCompanyIdAndIsActiveTrue(companyId);
    }

    /**
     * モックテストのバリデーション
     */
    private void validateMockTest(MockTest mockTest) {
        if (mockTest == null) {
            throw new IllegalArgumentException("モックテストは必須です");
        }

        if (!StringUtils.hasText(mockTest.getTitle())) {
            throw new IllegalArgumentException("タイトルは必須です");
        }

        if (mockTest.getTitle().length() > 200) {
            throw new IllegalArgumentException("タイトルは200文字以内で入力してください");
        }

        if (!StringUtils.hasText(mockTest.getTestType())) {
            throw new IllegalArgumentException("テストタイプは必須です");
        }

        if (mockTest.getDuration() != null && mockTest.getDuration() <= 0) {
            throw new IllegalArgumentException("制限時間は正の値で入力してください");
        }

        if (mockTest.getTotalQuestions() != null && mockTest.getTotalQuestions() <= 0) {
            throw new IllegalArgumentException("問題数は正の値で入力してください");
        }

        if (mockTest.getPassingScore() != null && 
            (mockTest.getPassingScore() < 0 || mockTest.getPassingScore() > 100)) {
            throw new IllegalArgumentException("合格点は0-100の範囲で入力してください");
        }

        // プログラムIDが指定されている場合の存在チェック
        if (mockTest.getProgramId() != null) {
            if (!trainingProgramRepository.existsById(mockTest.getProgramId())) {
                throw new IllegalArgumentException("指定されたプログラムが存在しません");
            }
        }

        // 企業IDが指定されている場合の存在チェック
        if (mockTest.getCompanyId() != null) {
            if (!companyRepository.existsById(mockTest.getCompanyId())) {
                throw new IllegalArgumentException("指定された企業が存在しません");
            }
        }
    }
}
