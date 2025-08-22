package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 講義成績エンティティ
 * lecture_gradesテーブルと対応する
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "lecture_grades")
public class LectureGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "training_assignment_id", nullable = false)
    private Long trainingAssignmentId;

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @Column(name = "exercise_score")
    private Integer exerciseScore;

    @Column(name = "exercise_max_score")
    private Integer exerciseMaxScore;

    @Column(name = "quiz_score")
    private Integer quizScore;

    @Column(name = "quiz_max_score")
    private Integer quizMaxScore;

    @Column(name = "attendance_status", length = 20)
    private String attendanceStatus;

    @Column(name = "completion_status", length = 20)
    private String completionStatus;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @Column(name = "grade_date")
    private LocalDateTime gradeDate;

    @Column(name = "instructor_feedback", columnDefinition = "TEXT")
    private String instructorFeedback;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** デフォルトコンストラクタ */
    public LectureGrade() {
    }

    /** @return id */
    public Long getId() {
        return id;
    }

    /** @param id 設定するID */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return trainingAssignmentId */
    public Long getTrainingAssignmentId() {
        return trainingAssignmentId;
    }

    /** @param trainingAssignmentId トレーニング課題ID */
    public void setTrainingAssignmentId(Long trainingAssignmentId) {
        this.trainingAssignmentId = trainingAssignmentId;
    }

    /** @return lectureId */
    public Long getLectureId() {
        return lectureId;
    }

    /** @param lectureId 講義ID */
    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    /** @return exerciseScore */
    public Integer getExerciseScore() {
        return exerciseScore;
    }

    /** @param exerciseScore 演習得点 */
    public void setExerciseScore(Integer exerciseScore) {
        this.exerciseScore = exerciseScore;
    }

    /** @return exerciseMaxScore */
    public Integer getExerciseMaxScore() {
        return exerciseMaxScore;
    }

    /** @param exerciseMaxScore 演習満点 */
    public void setExerciseMaxScore(Integer exerciseMaxScore) {
        this.exerciseMaxScore = exerciseMaxScore;
    }

    /** @return quizScore */
    public Integer getQuizScore() {
        return quizScore;
    }

    /** @param quizScore クイズ得点 */
    public void setQuizScore(Integer quizScore) {
        this.quizScore = quizScore;
    }

    /** @return quizMaxScore */
    public Integer getQuizMaxScore() {
        return quizMaxScore;
    }

    /** @param quizMaxScore クイズ満点 */
    public void setQuizMaxScore(Integer quizMaxScore) {
        this.quizMaxScore = quizMaxScore;
    }

    /** @return attendanceStatus */
    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    /** @param attendanceStatus 出席状況 */
    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    /** @return completionStatus */
    public String getCompletionStatus() {
        return completionStatus;
    }

    /** @param completionStatus 完了状況 */
    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }

    /** @return completionDate */
    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    /** @param completionDate 完了日時 */
    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    /** @return gradeDate */
    public LocalDateTime getGradeDate() {
        return gradeDate;
    }

    /** @param gradeDate 採点日時 */
    public void setGradeDate(LocalDateTime gradeDate) {
        this.gradeDate = gradeDate;
    }

    /** @return instructorFeedback */
    public String getInstructorFeedback() {
        return instructorFeedback;
    }

    /** @param instructorFeedback 講師コメント */
    public void setInstructorFeedback(String instructorFeedback) {
        this.instructorFeedback = instructorFeedback;
    }

    /** @return createdBy */
    public Long getCreatedBy() {
        return createdBy;
    }

    /** @param createdBy 作成者ID */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /** @return createdAt */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** @param createdAt 作成日時 */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /** @return updatedBy */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /** @param updatedBy 更新者ID */
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /** @return updatedAt */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /** @param updatedAt 更新日時 */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

