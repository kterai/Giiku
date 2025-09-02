package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.QuestionBank;
import jp.co.apsa.giiku.domain.repository.QuestionBankRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.github.dozermapper.core.Mapper;

/**
 * QuestionBankサービスクラス
 * 演習問題バンクの基本的な管理機能を提供します。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class QuestionBankService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionBankService.class);

    @Autowired
    private QuestionBankRepository questionBankRepository;

    @Autowired
    private Mapper mapper;

    @Transactional(readOnly = true)
    public List<QuestionBank> findAll() {
        return questionBankRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<QuestionBank> findAll(Pageable pageable) {
        return questionBankRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<QuestionBank> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }
        return questionBankRepository.findById(id);
    }

    public QuestionBank save(QuestionBank question) {
        validateQuestion(question);
        if (question.getId() == null) {
            question.setCreatedAt(LocalDateTime.now());
        }
        question.setUpdatedAt(LocalDateTime.now());
        return questionBankRepository.save(question);
    }

    public QuestionBank update(Long id, QuestionBank question) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        QuestionBank existing = questionBankRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("問題が見つかりません: " + id));

        validateQuestion(question);

        mapper.map(question, existing);
        existing.setUpdatedAt(LocalDateTime.now());

        return questionBankRepository.save(existing);
    }

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

    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }
        if (!questionBankRepository.existsById(id)) {
            throw new RuntimeException("問題が見つかりません: " + id);
        }
        questionBankRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<QuestionBank> searchByQuestionText(String text) {
        if (!StringUtils.hasText(text)) {
            return List.of();
        }
        return questionBankRepository.findByQuestionTextContainingIgnoreCaseAndIsActiveTrue(text);
    }

    @Transactional(readOnly = true)
    public List<QuestionBank> findByQuestionType(String type) {
        if (!StringUtils.hasText(type)) {
            return List.of();
        }
        return questionBankRepository.findByQuestionTypeAndIsActiveTrueOrderByCreatedAtDesc(type);
    }

    @Transactional(readOnly = true)
    public List<QuestionBank> findByDifficultyLevel(String level) {
        if (!StringUtils.hasText(level)) {
            return List.of();
        }
        return questionBankRepository.findByDifficultyLevelAndIsActiveTrueOrderByCreatedAtDesc(level);
    }

    @Transactional(readOnly = true)
    public List<QuestionBank> findByChapterIdOrderByQuestionNumber(Long chapterId) {
        if (chapterId == null) {
            throw new IllegalArgumentException("チャプターIDは必須です");
        }
        return questionBankRepository.findByChapterIdOrderByQuestionNumber(chapterId);
    }

    /**
     * 講義IDで問題を取得します。
     *
     * @param lectureId 講義ID
     * @return 問題一覧
     */
    @Transactional(readOnly = true)
    public List<QuestionBank> findByLectureId(Long lectureId) {
        if (lectureId == null) {
            throw new IllegalArgumentException("講義IDは必須です");
        }
        logger.debug("Fetching exercise questions for lectureId={}", lectureId);
        List<QuestionBank> result = questionBankRepository.findByLectureIdOrderByChapterAndQuestionNumber(lectureId);
        logger.debug("Retrieved {} exercise questions for lectureId={}", result.size(), lectureId);
        return result;
    }

    @Transactional(readOnly = true)
    public long countAll() {
        return questionBankRepository.count();
    }

    @Transactional(readOnly = true)
    public long countActive() {
        return questionBankRepository.countByIsActiveTrue();
    }

    private void validateQuestion(QuestionBank question) {
        if (question == null) {
            throw new IllegalArgumentException("問題は必須です");
        }
        if (question.getChapterId() == null) {
            throw new IllegalArgumentException("チャプターIDは必須です");
        }
        if (question.getQuestionNumber() == null) {
            throw new IllegalArgumentException("問題番号は必須です");
        }
        if (!StringUtils.hasText(question.getQuestionText())) {
            throw new IllegalArgumentException("問題文は必須です");
        }
        if (!StringUtils.hasText(question.getQuestionType())) {
            throw new IllegalArgumentException("問題タイプは必須です");
        }
        if (!StringUtils.hasText(question.getDifficultyLevel())) {
            throw new IllegalArgumentException("難易度レベルは必須です");
        }
        if (("multiple_choice".equalsIgnoreCase(question.getQuestionType()) ||
                "fill_blank".equalsIgnoreCase(question.getQuestionType())) &&
            !StringUtils.hasText(question.getQuestionOptions())) {
            throw new IllegalArgumentException("選択式問題では選択肢は必須です");
        }
        if (question.getPoints() != null && question.getPoints() <= 0) {
            throw new IllegalArgumentException("ポイントは正の値で入力してください");
        }
    }
}
