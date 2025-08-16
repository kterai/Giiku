package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.QuestionBank;
import jp.co.apsa.giiku.domain.entity.Company;
import jp.co.apsa.giiku.domain.repository.QuestionBankRepository;
import jp.co.apsa.giiku.domain.repository.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * QuestionBankサービスクラス
 * 問題バンク管理機能を提供
 */
@Service
@Transactional
public class QuestionBankService {

    @Autowired
    private QuestionBankRepository questionBankRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private final Random random = new Random();

    /**
     * 全ての問題を取得
     */
    @Transactional(readOnly = true)
    public List<QuestionBank> findAll() {
        return questionBankRepository.findAll();
    }

    /**
     * IDで問題を取得
     */
    @Transactional(readOnly = true)
    public Optional<QuestionBank> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }
        return questionBankRepository.findById(id);
    }

    /**
     * 問題を保存
     */
    public QuestionBank save(QuestionBank question) {
        validateQuestion(question);

        if (question.getId() == null) {
            question.setCreatedAt(LocalDateTime.now());
        }
        question.setUpdatedAt(LocalDateTime.now());

        return questionBankRepository.save(question);
    }

    /**
     * 問題を更新
     */
    public QuestionBank update(Long id, QuestionBank question) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        QuestionBank existing = questionBankRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("問題が見つかりません: " + id));

        validateQuestion(question);

        // 基本情報の更新
        existing.setQuestionText(question.getQuestionText());
        existing.setQuestionType(question.getQuestionType());
        existing.setOptions(question.getOptions());
        existing.setCorrectAnswer(question.getCorrectAnswer());
        existing.setExplanation(question.getExplanation());
        existing.setCategory(question.getCategory());
        existing.setDifficultyLevel(question.getDifficultyLevel());
        existing.setPoints(question.getPoints());
        existing.setIsActive(question.getIsActive());
        existing.setTags(question.getTags());
        existing.setUpdatedAt(LocalDateTime.now());

        return questionBankRepository.save(existing);
    }

    /**
     * 問題を論理削除
     */
    public void deactivate(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        QuestionBank question = questionBankRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("問題が見つかりません: " + id));

        question.setIsActive(false);
        question.setUpdatedAt(LocalDateTime.now());
        questionBankRepository.save(question);
    }

    /**
     * 問題を物理削除
     */
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        if (!questionBankRepository.existsById(id)) {
            throw new RuntimeException("問題が見つかりません: " + id);
        }

        questionBankRepository.deleteById(id);
    }

    /**
     * 企業IDで問題を検索
     */
    @Transactional(readOnly = true)
    public List<QuestionBank> findByCompanyId(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("企業IDは必須です");
        }
        return questionBankRepository.findByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(companyId);
    }

    /**
     * カテゴリで検索
     */
    @Transactional(readOnly = true)
    public List<QuestionBank> findByCategory(String category) {
        if (!StringUtils.hasText(category)) {
            throw new IllegalArgumentException("カテゴリは必須です");
        }
        return questionBankRepository.findByCategoryAndIsActiveTrueOrderByCreatedAtDesc(category);
    }

    /**
     * 難易度レベルで検索
     */
    @Transactional(readOnly = true)
    public List<QuestionBank> findByDifficultyLevel(String difficultyLevel) {
        if (!StringUtils.hasText(difficultyLevel)) {
            throw new IllegalArgumentException("難易度レベルは必須です");
        }
        return questionBankRepository.findByDifficultyLevelAndIsActiveTrueOrderByCreatedAtDesc(difficultyLevel);
    }

    /**
     * 問題タイプで検索
     */
    @Transactional(readOnly = true)
    public List<QuestionBank> findByQuestionType(String questionType) {
        if (!StringUtils.hasText(questionType)) {
            throw new IllegalArgumentException("問題タイプは必須です");
        }
        return questionBankRepository.findByQuestionTypeAndIsActiveTrueOrderByCreatedAtDesc(questionType);
    }

    /**
     * アクティブな問題を取得
     */
    @Transactional(readOnly = true)
    public List<QuestionBank> findActiveQuestions() {
        return questionBankRepository.findByIsActiveTrueOrderByCreatedAtDesc();
    }

    /**
     * 問題テキストで検索（部分一致）
     */
    @Transactional(readOnly = true)
    public List<QuestionBank> findByQuestionTextContaining(String searchText) {
        if (!StringUtils.hasText(searchText)) {
            throw new IllegalArgumentException("検索テキストは必須です");
        }
        return questionBankRepository.findByQuestionTextContainingIgnoreCaseAndIsActiveTrue(searchText);
    }

    /**
     * タグで検索
     */
    @Transactional(readOnly = true)
    public List<QuestionBank> findByTagsContaining(String tag) {
        if (!StringUtils.hasText(tag)) {
            throw new IllegalArgumentException("タグは必須です");
        }
        return questionBankRepository.findByTagsContainingIgnoreCaseAndIsActiveTrue(tag);
    }

    /**
     * ランダムに問題を選択
     */
    @Transactional(readOnly = true)
    public List<QuestionBank> findRandomQuestions(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("問題数は正の値で指定してください");
        }

        List<QuestionBank> allQuestions = questionBankRepository.findByIsActiveTrueOrderByCreatedAtDesc();
        if (allQuestions.size() <= count) {
            return allQuestions;
        }

        Collections.shuffle(allQuestions, random);
        return allQuestions.subList(0, count);
    }

    /**
     * 条件指定でランダムに問題を選択
     */
    @Transactional(readOnly = true)
    public List<QuestionBank> findRandomQuestionsByFilters(Long companyId, String category, 
                                                          String difficultyLevel, String questionType, 
                                                          int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("問題数は正の値で指定してください");
        }

        Page<QuestionBank> questions = findWithFilters(companyId, category, difficultyLevel, 
                                                      questionType, null, true, 
                                                      Pageable.unpaged());

        List<QuestionBank> questionList = questions.getContent();

        if (questionList.size() <= count) {
            return questionList;
        }

        Collections.shuffle(questionList, random);
        return questionList.subList(0, count);
    }

    /**
     * 複合条件での検索
     */
    @Transactional(readOnly = true)
    public Page<QuestionBank> findWithFilters(Long companyId, String category, String difficultyLevel,
                                             String questionType, String searchText, Boolean isActive,
                                             Pageable pageable) {

        Specification<QuestionBank> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (companyId != null) {
                predicates.add(criteriaBuilder.equal(root.get("companyId"), companyId));
            }

            if (StringUtils.hasText(category)) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
            }

            if (StringUtils.hasText(difficultyLevel)) {
                predicates.add(criteriaBuilder.equal(root.get("difficultyLevel"), difficultyLevel));
            }

            if (StringUtils.hasText(questionType)) {
                predicates.add(criteriaBuilder.equal(root.get("questionType"), questionType));
            }

            if (StringUtils.hasText(searchText)) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("questionText")), 
                    "%" + searchText.toLowerCase() + "%"));
            }

            if (isActive != null) {
                predicates.add(criteriaBuilder.equal(root.get("isActive"), isActive));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return questionBankRepository.findAll(spec, pageable);
    }

    /**
     * カテゴリ別の問題数統計を取得
     */
    @Transactional(readOnly = true)
    public List<Object[]> getCategoryStatistics() {
        return questionBankRepository.findQuestionCountByCategory();
    }

    /**
     * 難易度レベル別の問題数統計を取得
     */
    @Transactional(readOnly = true)
    public List<Object[]> getDifficultyStatistics() {
        return questionBankRepository.findQuestionCountByDifficultyLevel();
    }

    /**
     * 問題数をカウント
     */
    @Transactional(readOnly = true)
    public long countAll() {
        return questionBankRepository.count();
    }

    /**
     * アクティブな問題数をカウント
     */
    @Transactional(readOnly = true)
    public long countActive() {
        return questionBankRepository.countByIsActiveTrue();
    }

    /**
     * 企業別の問題数をカウント
     */
    @Transactional(readOnly = true)
    public long countByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("企業IDは必須です");
        }
        return questionBankRepository.countByCompanyIdAndIsActiveTrue(companyId);
    }

    /**
     * カテゴリ別の問題数をカウント
     */
    @Transactional(readOnly = true)
    public long countByCategory(String category) {
        if (!StringUtils.hasText(category)) {
            throw new IllegalArgumentException("カテゴリは必須です");
        }
        return questionBankRepository.countByCategoryAndIsActiveTrue(category);
    }

    /**
     * 問題のバリデーション
     */
    private void validateQuestion(QuestionBank question) {
        if (question == null) {
            throw new IllegalArgumentException("問題は必須です");
        }

        if (!StringUtils.hasText(question.getQuestionText())) {
            throw new IllegalArgumentException("問題文は必須です");
        }

        if (question.getQuestionText().length() > 2000) {
            throw new IllegalArgumentException("問題文は2000文字以内で入力してください");
        }

        if (!StringUtils.hasText(question.getQuestionType())) {
            throw new IllegalArgumentException("問題タイプは必須です");
        }

        if (!StringUtils.hasText(question.getCategory())) {
            throw new IllegalArgumentException("カテゴリは必須です");
        }

        if (!StringUtils.hasText(question.getDifficultyLevel())) {
            throw new IllegalArgumentException("難易度レベルは必須です");
        }

        // 問題タイプが選択式の場合、選択肢と正解のチェック
        if ("MULTIPLE_CHOICE".equals(question.getQuestionType()) || 
            "SINGLE_CHOICE".equals(question.getQuestionType())) {

            if (!StringUtils.hasText(question.getOptions())) {
                throw new IllegalArgumentException("選択式問題では選択肢は必須です");
            }

            if (!StringUtils.hasText(question.getCorrectAnswer())) {
                throw new IllegalArgumentException("選択式問題では正解は必須です");
            }
        }

        // ポイントの範囲チェック
        if (question.getPoints() != null && question.getPoints() <= 0) {
            throw new IllegalArgumentException("ポイントは正の値で入力してください");
        }

        // 企業IDが指定されている場合の存在チェック
        if (question.getCompanyId() != null) {
            if (!companyRepository.existsById(question.getCompanyId())) {
                throw new IllegalArgumentException("指定された企業が存在しません");
            }
        }

        // 問題タイプの有効性チェック
        if (!isValidQuestionType(question.getQuestionType())) {
            throw new IllegalArgumentException("無効な問題タイプです: " + question.getQuestionType());
        }

        // 難易度レベルの有効性チェック
        if (!isValidDifficultyLevel(question.getDifficultyLevel())) {
            throw new IllegalArgumentException("無効な難易度レベルです: " + question.getDifficultyLevel());
        }
    }

    /**
     * 有効な問題タイプかチェック
     */
    private boolean isValidQuestionType(String questionType) {
        List<String> validTypes = List.of(
            "MULTIPLE_CHOICE", "SINGLE_CHOICE", "TRUE_FALSE", "FILL_IN_BLANK", 
            "ESSAY", "CODING", "MATCHING"
        );
        return validTypes.contains(questionType);
    }

    /**
     * 有効な難易度レベルかチェック
     */
    private boolean isValidDifficultyLevel(String difficultyLevel) {
        List<String> validLevels = List.of("BEGINNER", "INTERMEDIATE", "ADVANCED", "EXPERT");
        return validLevels.contains(difficultyLevel);
    }
}
