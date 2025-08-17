package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 問題バンクエンティティ
 * LMS機能における問題管理を行う
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "question_bank")
public class QuestionBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "question_type", nullable = false, length = 50)
    private String questionType; // MULTIPLE_CHOICE, TRUE_FALSE, SHORT_ANSWER, ESSAY

    @Column(name = "category", nullable = false, length = 100)
    private String category;

    @Column(name = "difficulty_level", nullable = false, length = 20)
    private String difficultyLevel; // BEGINNER, INTERMEDIATE, ADVANCED, EXPERT

    @Column(name = "correct_answer", columnDefinition = "TEXT")
    private String correctAnswer;

    @Column(name = "options", columnDefinition = "TEXT")
    private String options; // JSON形式で選択肢を格納（多肢選択問題用）

    @Column(name = "explanation", columnDefinition = "TEXT") 
    private String explanation;

    @Column(name = "points", nullable = false)
    private Integer points;

    @Column(name = "tags", length = 500)
    private String tags; // カンマ区切りのタグ

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "instructor_id")
    private Long instructorId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // デフォルトコンストラクタ
    /** QuestionBank メソッド */
    public QuestionBank() {}

    // Getter and Setter methods
    /** getId メソッド */
    public Long getId() {
        return id;
    }
    /** setId メソッド */
    public void setId(Long id) {
        this.id = id;
    }
    /** getQuestionText メソッド */
    public String getQuestionText() {
        return questionText;
    }
    /** setQuestionText メソッド */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    /** getQuestionType メソッド */
    public String getQuestionType() {
        return questionType;
    }
    /** setQuestionType メソッド */
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
    /** getCategory メソッド */
    public String getCategory() {
        return category;
    }
    /** setCategory メソッド */
    public void setCategory(String category) {
        this.category = category;
    }
    /** getDifficultyLevel メソッド */
    public String getDifficultyLevel() {
        return difficultyLevel;
    }
    /** setDifficultyLevel メソッド */
    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
    /** getCorrectAnswer メソッド */
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    /** setCorrectAnswer メソッド */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    /** getOptions メソッド */
    public String getOptions() {
        return options;
    }
    /** setOptions メソッド */
    public void setOptions(String options) {
        this.options = options;
    }
    /** getExplanation メソッド */
    public String getExplanation() {
        return explanation;
    }
    /** setExplanation メソッド */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    /** getPoints メソッド */
    public Integer getPoints() {
        return points;
    }
    /** setPoints メソッド */
    public void setPoints(Integer points) {
        this.points = points;
    }
    /** getTags メソッド */
    public String getTags() {
        return tags;
    }
    /** setTags メソッド */
    public void setTags(String tags) {
        this.tags = tags;
    }
    /** getCompanyId メソッド */
    public Long getCompanyId() {
        return companyId;
    }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    /** getInstructorId メソッド */
    public Long getInstructorId() {
        return instructorId;
    }
    /** setInstructorId メソッド */
    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
    /** getIsActive メソッド */
    public Boolean getIsActive() {
        return isActive;
    }
    /** setIsActive メソッド */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    /** getCreatedAt メソッド */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    /** setCreatedAt メソッド */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    /** toString メソッド */
    @Override
    public String toString() {
        return "QuestionBank{" +
                "id=" + id +
                ", questionText='" + questionText + "'" +
                ", questionType='" + questionType + "'" +
                ", category='" + category + "'" +
                ", difficultyLevel='" + difficultyLevel + "'" +
                ", points=" + points +
                ", isActive=" + isActive +
                '}';
    }
}
