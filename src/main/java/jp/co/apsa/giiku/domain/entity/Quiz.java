package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * クイズエンティティ
 * LMS機能におけるクイズ実施管理を行う
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "training_program_id")
    private Long trainingProgramId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "instructor_id")
    private Long instructorId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "quiz_status", nullable = false, length = 20)
    private String quizStatus; // NOT_STARTED, IN_PROGRESS, COMPLETED, SUBMITTED, GRADED

    @Column(name = "total_questions")
    private Integer totalQuestions;

    @Column(name = "answered_questions")
    private Integer answeredQuestions = 0;

    @Column(name = "total_points")
    private Integer totalPoints;

    @Column(name = "earned_points")
    private Integer earnedPoints = 0;

    @Column(name = "percentage_score")
    private Double percentageScore;

    @Column(name = "passing_score")
    private Double passingScore = 70.0;

    @Column(name = "is_passed")
    private Boolean isPassed = false;

    @Column(name = "time_limit_minutes")
    private Integer timeLimitMinutes;

    @Column(name = "time_spent_minutes")
    private Integer timeSpentMinutes = 0;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "submission_time")
    private LocalDateTime submissionTime;

    @Column(name = "graded_time")
    private LocalDateTime gradedTime;

    @Column(name = "student_answers", columnDefinition = "TEXT")
    private String studentAnswers; // JSON形式で学生の回答を格納

    @Column(name = "question_ids", columnDefinition = "TEXT")
    private String questionIds; // JSON形式で問題IDリストを格納

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // デフォルトコンストラクタ
    /** Quiz メソッド */
    public Quiz() {}

    // Getter and Setter methods
    /** getId メソッド */
    public Long getId() {
        return id;
    }
    /** setId メソッド */
    public void setId(Long id) {
        this.id = id;
    }
    /** getTitle メソッド */
    public String getTitle() {
        return title;
    }
    /** setTitle メソッド */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * title のエイリアス getter
     *
     * @return quizTitle
     */
    public String getQuizTitle() {
        return title;
    }

    /**
     * title のエイリアス setter
     *
     * @param quizTitle quizTitle
     */
    public void setQuizTitle(String quizTitle) {
        this.title = quizTitle;
    }
    /** getDescription メソッド */
    public String getDescription() {
        return description;
    }
    /** setDescription メソッド */
    public void setDescription(String description) {
        this.description = description;
    }
    /** getTrainingProgramId メソッド */
    public Long getTrainingProgramId() {
        return trainingProgramId;
    }
    /** setTrainingProgramId メソッド */
    public void setTrainingProgramId(Long trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
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
    /** getStudentId メソッド */
    public Long getStudentId() {
        return studentId;
    }
    /** setStudentId メソッド */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    /** getInstructorId メソッド */
    public Long getInstructorId() {
        return instructorId;
    }
    /** setInstructorId メソッド */
    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
    /** getCompanyId メソッド */
    public Long getCompanyId() {
        return companyId;
    }
    /** setCompanyId メソッド */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    /** getQuizStatus メソッド */
    public String getQuizStatus() {
        return quizStatus;
    }
    /** setQuizStatus メソッド */
    public void setQuizStatus(String quizStatus) {
        this.quizStatus = quizStatus;
    }
    /** getTotalQuestions メソッド */
    public Integer getTotalQuestions() {
        return totalQuestions;
    }
    /** setTotalQuestions メソッド */
    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
    /** getAnsweredQuestions メソッド */
    public Integer getAnsweredQuestions() {
        return answeredQuestions;
    }
    /** setAnsweredQuestions メソッド */
    public void setAnsweredQuestions(Integer answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }
    /** getTotalPoints メソッド */
    public Integer getTotalPoints() {
        return totalPoints;
    }
    /** setTotalPoints メソッド */
    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }
    /** getEarnedPoints メソッド */
    public Integer getEarnedPoints() {
        return earnedPoints;
    }
    /** setEarnedPoints メソッド */
    public void setEarnedPoints(Integer earnedPoints) {
        this.earnedPoints = earnedPoints;
    }
    /** getPercentageScore メソッド */
    public Double getPercentageScore() {
        return percentageScore;
    }
    /** setPercentageScore メソッド */
    public void setPercentageScore(Double percentageScore) {
        this.percentageScore = percentageScore;
    }
    /** getPassingScore メソッド */
    public Double getPassingScore() {
        return passingScore;
    }
    /** setPassingScore メソッド */
    public void setPassingScore(Double passingScore) {
        this.passingScore = passingScore;
    }
    /** getIsPassed メソッド */
    public Boolean getIsPassed() {
        return isPassed;
    }
    /** setIsPassed メソッド */
    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }
    /** getTimeLimitMinutes メソッド */
    public Integer getTimeLimitMinutes() {
        return timeLimitMinutes;
    }
    /** setTimeLimitMinutes メソッド */
    public void setTimeLimitMinutes(Integer timeLimitMinutes) {
        this.timeLimitMinutes = timeLimitMinutes;
    }
    /** getTimeSpentMinutes メソッド */
    public Integer getTimeSpentMinutes() {
        return timeSpentMinutes;
    }
    /** setTimeSpentMinutes メソッド */
    public void setTimeSpentMinutes(Integer timeSpentMinutes) {
        this.timeSpentMinutes = timeSpentMinutes;
    }
    /** getStartTime メソッド */
    public LocalDateTime getStartTime() {
        return startTime;
    }
    /** setStartTime メソッド */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    /** getEndTime メソッド */
    public LocalDateTime getEndTime() {
        return endTime;
    }
    /** setEndTime メソッド */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    /** getSubmissionTime メソッド */
    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }
    /** setSubmissionTime メソッド */
    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }
    /** getGradedTime メソッド */
    public LocalDateTime getGradedTime() {
        return gradedTime;
    }
    /** setGradedTime メソッド */
    public void setGradedTime(LocalDateTime gradedTime) {
        this.gradedTime = gradedTime;
    }
    /** getStudentAnswers メソッド */
    public String getStudentAnswers() {
        return studentAnswers;
    }
    /** setStudentAnswers メソッド */
    public void setStudentAnswers(String studentAnswers) {
        this.studentAnswers = studentAnswers;
    }
    /** getQuestionIds メソッド */
    public String getQuestionIds() {
        return questionIds;
    }
    /** setQuestionIds メソッド */
    public void setQuestionIds(String questionIds) {
        this.questionIds = questionIds;
    }
    /** getFeedback メソッド */
    public String getFeedback() {
        return feedback;
    }
    /** setFeedback メソッド */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
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

    /**
     * TrainingProgramId のエイリアス getter
     *
     * @return programId
     */
    public Long getProgramId() {
        return trainingProgramId;
    }

    /**
     * TrainingProgramId のエイリアス setter
     *
     * @param programId programId
     */
    public void setProgramId(Long programId) {
        this.trainingProgramId = programId;
    }

    /**
     * quizStatus のエイリアス getter
     *
     * @return status
     */
    public String getStatus() {
        return quizStatus;
    }

    /**
     * quizStatus のエイリアス setter
     *
     * @param status status
     */
    public void setStatus(String status) {
        this.quizStatus = status;
    }

    /**
     * percentageScore のエイリアス getter
     *
     * @return score
     */
    public Double getScore() {
        return percentageScore;
    }

    /**
     * percentageScore のエイリアス setter
     *
     * @param score score
     */
    public void setScore(Double score) {
        this.percentageScore = score;
    }

    /**
     * timeSpentMinutes のエイリアス getter
     *
     * @return timeSpent
     */
    public Integer getTimeSpent() {
        return timeSpentMinutes;
    }

    /**
     * timeSpentMinutes のエイリアス setter
     *
     * @param timeSpent timeSpent
     */
    public void setTimeSpent(Integer timeSpent) {
        this.timeSpentMinutes = timeSpent;
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
        if (answeredQuestions == null) {
            answeredQuestions = 0;
        }
        if (earnedPoints == null) {
            earnedPoints = 0;
        }
        if (timeSpentMinutes == null) {
            timeSpentMinutes = 0;
        }
        if (isPassed == null) {
            isPassed = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /** toString メソッド */
    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", studentId=" + studentId +
                ", quizStatus='" + quizStatus + "'" +
                ", totalQuestions=" + totalQuestions +
                ", answeredQuestions=" + answeredQuestions +
                ", percentageScore=" + percentageScore +
                ", isPassed=" + isPassed +
                '}';
    }
}
