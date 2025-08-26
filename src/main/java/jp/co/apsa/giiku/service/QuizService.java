package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.Quiz;
import jp.co.apsa.giiku.domain.entity.QuestionBank;
import jp.co.apsa.giiku.domain.entity.StudentProfile;
import jp.co.apsa.giiku.domain.entity.TrainingProgram;
import jp.co.apsa.giiku.domain.repository.QuizRepository;
import jp.co.apsa.giiku.domain.repository.QuestionBankRepository;
import jp.co.apsa.giiku.domain.repository.StudentProfileRepository;
import jp.co.apsa.giiku.domain.repository.TrainingProgramRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Predicate;
import com.github.dozermapper.core.Mapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.math.BigDecimal;

/**
 * Quizサービスクラス
 * クイズ管理機能を提供
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionBankRepository questionBankRepository;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    @Autowired
    private Mapper mapper;

    /** 全てのクイズを取得 */
    @Transactional(readOnly = true)
    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Quiz> findAll(Pageable pageable) {
        return quizRepository.findAll(pageable);
    }

    /** IDでクイズを取得 */
    @Transactional(readOnly = true)
    public Optional<Quiz> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }
        return quizRepository.findById(id);
    }

    /** クイズを保存 */
    public Quiz save(Quiz quiz) {
        validateQuiz(quiz);

        if (quiz.getId() == null) {
            quiz.setStartTime(LocalDateTime.now());
            quiz.setStatus("IN_PROGRESS");
        }
        quiz.setUpdatedAt(LocalDateTime.now());

        return quizRepository.save(quiz);
    }

    /** クイズを更新 */
    public Quiz update(Long id, Quiz quiz) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        Quiz existing = quizRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("クイズが見つかりません: " + id));

        validateQuiz(quiz);

        // 基本情報の更新
        mapper.map(quiz, existing);
        existing.setUpdatedAt(LocalDateTime.now());

        return quizRepository.save(existing);
    }

    /** クイズを削除 */
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("IDは必須です");
        }

        if (!quizRepository.existsById(id)) {
            throw new RuntimeException("クイズが見つかりません: " + id);
        }

        quizRepository.deleteById(id);
    }

    /** 学生IDでクイズを検索 */
    @Transactional(readOnly = true)
    public List<Quiz> findByStudentId(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("学生IDは必須です");
        }
        return quizRepository.findByStudentIdOrderByStartTimeDesc(studentId);
    }

    /** プログラムIDでクイズを検索 */
    @Transactional(readOnly = true)
    public List<Quiz> findByProgramId(Long programId) {
        if (programId == null) {
            throw new IllegalArgumentException("プログラムIDは必須です");
        }
        return quizRepository.findByTrainingProgramIdOrderByStartTimeDesc(programId);
    }

    /** ステータスでクイズを検索 */
    @Transactional(readOnly = true)
    public List<Quiz> findByStatus(String status) {
        if (!StringUtils.hasText(status)) {
            throw new IllegalArgumentException("ステータスは必須です");
        }
        return quizRepository.findByQuizStatusOrderByStartTimeDesc(status);
    }

    /** 進行中のクイズを取得 */
    @Transactional(readOnly = true)
    public List<Quiz> findInProgressQuizzes() {
        return quizRepository.findByQuizStatusOrderByStartTimeDesc("IN_PROGRESS");
    }

    /** 完了したクイズを取得 */
    @Transactional(readOnly = true)
    public List<Quiz> findCompletedQuizzes() {
        return quizRepository.findByQuizStatusOrderByEndTimeDesc("COMPLETED");
    }

    /** 学生の進行中クイズを取得 */
    @Transactional(readOnly = true)
    public List<Quiz> findStudentInProgressQuizzes(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("学生IDは必須です");
        }
        return quizRepository.findByStudentIdAndQuizStatusOrderByStartTimeDesc(studentId, "IN_PROGRESS");
    }

    /** 学生の完了クイズを取得 */
    @Transactional(readOnly = true)
    public List<Quiz> findStudentCompletedQuizzes(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("学生IDは必須です");
        }
        return quizRepository.findByStudentIdAndQuizStatusOrderByEndTimeDesc(studentId, "COMPLETED");
    }

    /** プログラムの平均スコアを取得 */
    @Transactional(readOnly = true)
    public Double getAverageScoreByProgram(Long programId) {
        if (programId == null) {
            throw new IllegalArgumentException("プログラムIDは必須です");
        }
        return quizRepository.findAverageScoreByProgramId(programId);
    }

    /** 学生のプログラム別平均スコアを取得 */
    @Transactional(readOnly = true)
    public Double getStudentAverageScore(Long studentId, Long programId) {
        if (studentId == null || programId == null) {
            throw new IllegalArgumentException("学生IDとプログラムIDは必須です");
        }
        return quizRepository.findAverageScoreByStudentIdAndProgramId(studentId, programId);
    }

    /** 複合条件での検索 */
    @Transactional(readOnly = true)
    public Page<Quiz> findWithFilters(Long studentId, Long programId, String status,
                                     LocalDateTime startTimeFrom, LocalDateTime startTimeTo,
                                     Double scoreMin, Double scoreMax,
                                     Pageable pageable) {

        Specification<Quiz> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (studentId != null) {
                predicates.add(criteriaBuilder.equal(root.get("studentId"), studentId));
            }

            if (programId != null) {
                predicates.add(criteriaBuilder.equal(root.get("trainingProgramId"), programId));
            }

            if (StringUtils.hasText(status)) {
                predicates.add(criteriaBuilder.equal(root.get("quizStatus"), status));
            }

            if (startTimeFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("startTime"), startTimeFrom));
            }

            if (startTimeTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("startTime"), startTimeTo));
            }

            if (scoreMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("score"), scoreMin));
            }

            if (scoreMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("score"), scoreMax));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return quizRepository.findAll(spec, pageable);
    }

    /** クイズを開始 */
    public Quiz startQuiz(Long studentId, Long programId, List<Long> questionIds) {
        if (studentId == null || programId == null || questionIds == null || questionIds.isEmpty()) {
            throw new IllegalArgumentException("学生ID、プログラムID、問題IDリストは必須です");
        }

        // 学生の進行中クイズチェック
        List<Quiz> inProgressQuizzes = findStudentInProgressQuizzes(studentId);
        if (!inProgressQuizzes.isEmpty()) {
            throw new IllegalArgumentException("この学生には既に進行中のクイズがあります");
        }

        Quiz quiz = new Quiz();
        quiz.setStudentId(studentId);
        quiz.setProgramId(programId);
        quiz.setQuestionIds(String.join(",", questionIds.stream().map(String::valueOf).toArray(String[]::new)));
        quiz.setStartTime(LocalDateTime.now());
        quiz.setStatus("IN_PROGRESS");
        quiz.setScore(0.0);
        quiz.setTimeSpent(0);
        quiz.setCreatedAt(LocalDateTime.now());
        quiz.setUpdatedAt(LocalDateTime.now());

        return quizRepository.save(quiz);
    }

    public Quiz startQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        quiz.setStartTime(LocalDateTime.now());
        quiz.setStatus("IN_PROGRESS");
        quiz.setUpdatedAt(LocalDateTime.now());
        return quizRepository.save(quiz);
    }

    /** クイズを提出・採点 */
    public Quiz submitQuiz(Long quizId, String studentAnswers) {
        if (quizId == null) {
            throw new IllegalArgumentException("クイズIDは必須です");
        }

        Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new RuntimeException("クイズが見つかりません: " + quizId));

        if (!"IN_PROGRESS".equals(quiz.getStatus())) {
            throw new IllegalArgumentException("このクイズは既に提出済みです");
        }

        quiz.setStudentAnswers(studentAnswers);
        quiz.setEndTime(LocalDateTime.now());
        quiz.setStatus("COMPLETED");

        // 経過時間を計算
        long timeSpent = java.time.Duration.between(quiz.getStartTime(), quiz.getEndTime()).getSeconds();
        quiz.setTimeSpent((int) timeSpent);

        // 自動採点
        double score = calculateScore(quiz);
        quiz.setScore(score);

        quiz.setUpdatedAt(LocalDateTime.now());

        return quizRepository.save(quiz);
    }

    public Quiz submitQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        return submitQuiz(id, quiz.getStudentAnswers());
    }

    /** クイズを手動採点 */
    public Quiz manualGradeQuiz(Long quizId, Double score) {
        if (quizId == null || score == null) {
            throw new IllegalArgumentException("クイズIDとスコアは必須です");
        }

        if (score < 0.0 || score > 100.0) {
            throw new IllegalArgumentException("スコアは0-100の範囲で入力してください");
        }

        Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new RuntimeException("クイズが見つかりません: " + quizId));

        quiz.setScore(score);
        quiz.setUpdatedAt(LocalDateTime.now());

        return quizRepository.save(quiz);
    }

    public Quiz gradeQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        quiz.setScore(calculateScore(quiz));
        quiz.setStatus("GRADED");
        quiz.setGradedTime(LocalDateTime.now());
        return quizRepository.save(quiz);
    }

    /** クイズ数をカウント */
    @Transactional(readOnly = true)
    public long countAll() {
        return quizRepository.count();
    }

    /** ステータス別のクイズ数をカウント */
    @Transactional(readOnly = true)
    public long countByStatus(String status) {
        if (!StringUtils.hasText(status)) {
            throw new IllegalArgumentException("ステータスは必須です");
        }
        return quizRepository.countByQuizStatus(status);
    }

    /** 学生別のクイズ数をカウント */
    @Transactional(readOnly = true)
    public long countByStudent(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("学生IDは必須です");
        }
        return quizRepository.countByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public List<Quiz> findByInstructorId(Long instructorId) {
        return quizRepository.findAll().stream()
                .filter(q -> instructorId != null && instructorId.equals(q.getInstructorId()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Quiz> findByQuizStatus(String status) {
        return quizRepository.findByQuizStatusOrderByStartTimeDesc(status);
    }

    @Transactional(readOnly = true)
    public List<Quiz> findInProgressQuizzesByStudent(Long studentId) {
        return findStudentInProgressQuizzes(studentId);
    }

    @Transactional(readOnly = true)
    public List<Quiz> findCompletedQuizzesByStudent(Long studentId) {
        return findStudentCompletedQuizzes(studentId);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getQuizStatistics(Long companyId) {
        return List.of();
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateAverageScore(Long companyId) {
        return BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateStudentAverageScore(Long studentId) {
        return BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public List<Quiz> findOverdueQuizzes(Long companyId) {
        return List.of();
    }

    @Transactional(readOnly = true)
    public List<Quiz> findTopScoringQuizzes(Long companyId, Integer limit) {
        return List.of();
    }

    public Quiz saveAnswers(Long id, Map<String, Object> answers) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        quiz.setStudentAnswers(answers != null ? answers.toString() : null);
        quiz.setAnsweredQuestions(answers != null ? answers.size() : 0);
        quiz.setUpdatedAt(LocalDateTime.now());
        return quizRepository.save(quiz);
    }

    /** 自動採点処理 */
    private double calculateScore(Quiz quiz) {
        try {
            if (!StringUtils.hasText(quiz.getQuestionIds()) || !StringUtils.hasText(quiz.getStudentAnswers())) {
                return 0.0;
            }

            String[] questionIds = quiz.getQuestionIds().split(",");
            String[] studentAnswers = quiz.getStudentAnswers().split(",");

            if (questionIds.length != studentAnswers.length) {
                return 0.0;
            }

            int correctCount = 0;
            int totalQuestions = questionIds.length;

            for (int i = 0; i < questionIds.length; i++) {
                Long questionId = Long.parseLong(questionIds[i].trim());
                String studentAnswer = studentAnswers[i].trim();

                Optional<QuestionBank> questionOpt = questionBankRepository.findById(questionId);
                if (questionOpt.isPresent()) {
                    QuestionBank question = questionOpt.get();
                    if (question.getCorrectAnswer() != null && 
                        question.getCorrectAnswer().trim().equalsIgnoreCase(studentAnswer)) {
                        correctCount++;
                    }
                }
            }

            return totalQuestions > 0 ? (double) correctCount / totalQuestions * 100.0 : 0.0;

        } catch (Exception e) {
            // 採点エラーの場合は0点を返す
            return 0.0;
        }
    }

    /** クイズのバリデーション */
    private void validateQuiz(Quiz quiz) {
        if (quiz == null) {
            throw new IllegalArgumentException("クイズは必須です");
        }

        if (quiz.getStudentId() == null) {
            throw new IllegalArgumentException("学生IDは必須です");
        }

        if (quiz.getProgramId() == null) {
            throw new IllegalArgumentException("プログラムIDは必須です");
        }

        // 学生存在チェック
        if (!studentProfileRepository.existsById(quiz.getStudentId())) {
            throw new IllegalArgumentException("指定された学生が存在しません");
        }

        // プログラム存在チェック
        if (!trainingProgramRepository.existsById(quiz.getProgramId())) {
            throw new IllegalArgumentException("指定されたプログラムが存在しません");
        }

        // スコアの範囲チェック
        if (quiz.getScore() != null && (quiz.getScore() < 0.0 || quiz.getScore() > 100.0)) {
            throw new IllegalArgumentException("スコアは0-100の範囲で入力してください");
        }

        // 時間の整合性チェック
        if (quiz.getStartTime() != null && quiz.getEndTime() != null) {
            if (quiz.getStartTime().isAfter(quiz.getEndTime())) {
                throw new IllegalArgumentException("開始時刻は終了時刻より前に設定してください");
            }
        }
    }
}
