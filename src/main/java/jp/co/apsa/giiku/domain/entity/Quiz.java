package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * クイズエンティティ
 * LMS機能におけるクイズ実施管理を行う
 *
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

    @Column(name = "lecture_id")
    private Long lectureId;

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
    /**
     * Quiz メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public Quiz() {}

    // Getter and Setter methods
    /**
     * getId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    public Long getId() {
        return id;
    }
    /**
     * setId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setId(Long id) {
        this.id = id;
    }
    /**
     * getTitle メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getTitle() {
        return title;
    }
    /**
     * setTitle メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * title のエイリアス getter
     *
     * @return quizTitle
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public String getQuizTitle() {
        return title;
    }

    /**
     * title のエイリアス setter
     *
     * @param quizTitle quizTitle
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void setQuizTitle(String quizTitle) {
        this.title = quizTitle;
    }
    /**
     * getDescription メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getDescription() {
        return description;
    }
    /**
     * setDescription メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * getTrainingProgramId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getTrainingProgramId() {
        return trainingProgramId;
    }
    /**
     * setTrainingProgramId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setTrainingProgramId(Long trainingProgramId) {
        this.trainingProgramId = trainingProgramId;
    }
    /**
     * getLectureId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getLectureId() {
        return lectureId;
    }
    /**
     * setLectureId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }
    /**
     * getStudentId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getStudentId() {
        return studentId;
    }
    /**
     * setStudentId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    /**
     * getInstructorId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getInstructorId() {
        return instructorId;
    }
    /**
     * setInstructorId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
    /**
     * getCompanyId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getCompanyId() {
        return companyId;
    }
    /**
     * setCompanyId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    /**
     * getQuizStatus メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getQuizStatus() {
        return quizStatus;
    }
    /**
     * setQuizStatus メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setQuizStatus(String quizStatus) {
        this.quizStatus = quizStatus;
    }
    /**
     * getTotalQuestions メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getTotalQuestions() {
        return totalQuestions;
    }
    /**
     * setTotalQuestions メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
    /**
     * getAnsweredQuestions メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getAnsweredQuestions() {
        return answeredQuestions;
    }
    /**
     * setAnsweredQuestions メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setAnsweredQuestions(Integer answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }
    /**
     * getTotalPoints メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getTotalPoints() {
        return totalPoints;
    }
    /**
     * setTotalPoints メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }
    /**
     * getEarnedPoints メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getEarnedPoints() {
        return earnedPoints;
    }
    /**
     * setEarnedPoints メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setEarnedPoints(Integer earnedPoints) {
        this.earnedPoints = earnedPoints;
    }
    /**
     * getPercentageScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Double getPercentageScore() {
        return percentageScore;
    }
    /**
     * setPercentageScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setPercentageScore(Double percentageScore) {
        this.percentageScore = percentageScore;
    }
    /**
     * getPassingScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Double getPassingScore() {
        return passingScore;
    }
    /**
     * setPassingScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setPassingScore(Double passingScore) {
        this.passingScore = passingScore;
    }
    /**
     * getIsPassed メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Boolean getIsPassed() {
        return isPassed;
    }
    /**
     * setIsPassed メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }
    /**
     * getTimeLimitMinutes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getTimeLimitMinutes() {
        return timeLimitMinutes;
    }
    /**
     * setTimeLimitMinutes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setTimeLimitMinutes(Integer timeLimitMinutes) {
        this.timeLimitMinutes = timeLimitMinutes;
    }
    /**
     * getTimeSpentMinutes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getTimeSpentMinutes() {
        return timeSpentMinutes;
    }
    /**
     * setTimeSpentMinutes メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setTimeSpentMinutes(Integer timeSpentMinutes) {
        this.timeSpentMinutes = timeSpentMinutes;
    }
    /**
     * getStartTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getStartTime() {
        return startTime;
    }
    /**
     * setStartTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    /**
     * getEndTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getEndTime() {
        return endTime;
    }
    /**
     * setEndTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    /**
     * getSubmissionTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }
    /**
     * setSubmissionTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }
    /**
     * getGradedTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getGradedTime() {
        return gradedTime;
    }
    /**
     * setGradedTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setGradedTime(LocalDateTime gradedTime) {
        this.gradedTime = gradedTime;
    }
    /**
     * getStudentAnswers メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getStudentAnswers() {
        return studentAnswers;
    }
    /**
     * setStudentAnswers メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setStudentAnswers(String studentAnswers) {
        this.studentAnswers = studentAnswers;
    }
    /**
     * getQuestionIds メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getQuestionIds() {
        return questionIds;
    }
    /**
     * setQuestionIds メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setQuestionIds(String questionIds) {
        this.questionIds = questionIds;
    }
    /**
     * getFeedback メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getFeedback() {
        return feedback;
    }
    /**
     * setFeedback メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    /**
     * getIsActive メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Boolean getIsActive() {
        return isActive;
    }
    /**
     * setIsActive メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    /**
     * getCreatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    /**
     * setCreatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    /**
     * getUpdatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    /**
     * setUpdatedAt メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * TrainingProgramId のエイリアス getter
     *
     * @return programId
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public Long getProgramId() {
        return trainingProgramId;
    }

    /**
     * TrainingProgramId のエイリアス setter
     *
     * @param programId programId
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void setProgramId(Long programId) {
        this.trainingProgramId = programId;
    }

    /**
     * quizStatus のエイリアス getter
     *
     * @return status
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public String getStatus() {
        return quizStatus;
    }

    /**
     * quizStatus のエイリアス setter
     *
     * @param status status
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void setStatus(String status) {
        this.quizStatus = status;
    }

    /**
     * percentageScore のエイリアス getter
     *
     * @return score
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public Double getScore() {
        return percentageScore;
    }

    /**
     * percentageScore のエイリアス setter
     *
     * @param score score
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void setScore(Double score) {
        this.percentageScore = score;
    }

    /**
     * timeSpentMinutes のエイリアス getter
     *
     * @return timeSpent
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public Integer getTimeSpent() {
        return timeSpentMinutes;
    }

    /**
     * timeSpentMinutes のエイリアス setter
     *
     * @param timeSpent timeSpent
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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

    /**
     * toString メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
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
