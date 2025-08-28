package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * クイズ問題バンクエンティティ
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "quiz_question_bank")
public class QuizQuestionBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @Column(name = "question_number", nullable = false)
    private Integer questionNumber;

    @Column(name = "question_type", nullable = false, length = 20)
    private String questionType;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "option_a", columnDefinition = "TEXT")
    private String optionA;

    @Column(name = "option_b", columnDefinition = "TEXT")
    private String optionB;

    @Column(name = "option_c", columnDefinition = "TEXT")
    private String optionC;

    @Column(name = "option_d", columnDefinition = "TEXT")
    private String optionD;

    @Column(name = "option_e", columnDefinition = "TEXT")
    private String optionE;

    @Column(name = "option_f", columnDefinition = "TEXT")
    private String optionF;

    @Column(name = "correct_answer", nullable = false, columnDefinition = "TEXT")
    private String correctAnswer;

    @Column(name = "explanation", columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "difficulty_level", length = 20)
    private String difficultyLevel;

    @Column(name = "time_limit")
    private Integer timeLimit;

    @Column(name = "points")
    private Integer points;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** デフォルトコンストラクタ */
    public QuizQuestionBank() {
    }

    /** getId メソッド */
    public Long getId() {
        return id;
    }

    /** setId メソッド */
    public void setId(Long id) {
        this.id = id;
    }

    /** getChapter メソッド */
    public Chapter getChapter() {
        return chapter;
    }

    /** setChapter メソッド */
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    /**
     * chapterId のエイリアス getter
     *
     * @return chapterId
     */
    public Long getChapterId() {
        return chapter != null ? chapter.getId() : null;
    }

    /**
     * chapterId のエイリアス setter
     *
     * @param chapterId chapterId
     */
    public void setChapterId(Long chapterId) {
        if (this.chapter == null) {
            this.chapter = new Chapter();
        }
        this.chapter.setId(chapterId);
    }

    /** getQuestionNumber メソッド */
    public Integer getQuestionNumber() {
        return questionNumber;
    }

    /** setQuestionNumber メソッド */
    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    /** getQuestionType メソッド */
    public String getQuestionType() {
        return questionType;
    }

    /** setQuestionType メソッド */
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    /** getQuestionText メソッド */
    public String getQuestionText() {
        return questionText;
    }

    /** setQuestionText メソッド */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /** getOptionA メソッド */
    public String getOptionA() {
        return optionA;
    }

    /** setOptionA メソッド */
    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    /** getOptionB メソッド */
    public String getOptionB() {
        return optionB;
    }

    /** setOptionB メソッド */
    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    /** getOptionC メソッド */
    public String getOptionC() {
        return optionC;
    }

    /** setOptionC メソッド */
    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    /** getOptionD メソッド */
    public String getOptionD() {
        return optionD;
    }

    /** setOptionD メソッド */
    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    /** getOptionE メソッド */
    public String getOptionE() {
        return optionE;
    }

    /** setOptionE メソッド */
    public void setOptionE(String optionE) {
        this.optionE = optionE;
    }

    /** getOptionF メソッド */
    public String getOptionF() {
        return optionF;
    }

    /** setOptionF メソッド */
    public void setOptionF(String optionF) {
        this.optionF = optionF;
    }

    /** getCorrectAnswer メソッド */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /** setCorrectAnswer メソッド */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /** getExplanation メソッド */
    public String getExplanation() {
        return explanation;
    }

    /** setExplanation メソッド */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    /** getDifficultyLevel メソッド */
    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    /** setDifficultyLevel メソッド */
    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /** getTimeLimit メソッド */
    public Integer getTimeLimit() {
        return timeLimit;
    }

    /** setTimeLimit メソッド */
    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    /** getPoints メソッド */
    public Integer getPoints() {
        return points;
    }

    /** setPoints メソッド */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /** getIsActive メソッド */
    public Boolean getIsActive() {
        return isActive;
    }

    /** setIsActive メソッド */
    public void setIsActive(Boolean active) {
        isActive = active;
    }

    /** getCreatedBy メソッド */
    public Long getCreatedBy() {
        return createdBy;
    }

    /** setCreatedBy メソッド */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /** getCreatedAt メソッド */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** setCreatedAt メソッド */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /** getUpdatedBy メソッド */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /** setUpdatedBy メソッド */
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /** getUpdatedAt メソッド */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /** setUpdatedAt メソッド */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (isActive == null) {
            isActive = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
