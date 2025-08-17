package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Duration;

/**
 * モックテスト結果を管理するエンティティクラス
 * 学生のテスト受験結果、スコア、時間などの詳細情報を保持します
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "mock_test_results", indexes = {
    @Index(name = "idx_mock_test_results_test_id", columnList = "testId"),
    @Index(name = "idx_mock_test_results_student_id", columnList = "studentId"),
    @Index(name = "idx_mock_test_results_company_id", columnList = "companyId"),
    @Index(name = "idx_mock_test_results_status", columnList = "status"),
    @Index(name = "idx_mock_test_results_created_at", columnList = "createdAt")
}, uniqueConstraints = {
    @UniqueConstraint(name = "uk_test_student_attempt", 
                     columnNames = {"testId", "studentId", "attemptNumber"})
})
/**
 * The MockTestResult class.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class MockTestResult extends BaseEntity {

    @NotNull
    @Column(name = "test_id", nullable = false)
    private Long testId;

    @NotNull
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @NotNull
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @NotBlank
    @Size(max = 50)
    @Column(name = "status", length = 50, nullable = false)
    private String status;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(name = "score", precision = 5, scale = 2)
    private BigDecimal score;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Min(0)
    @Column(name = "time_spent_minutes")
    private Integer timeSpentMinutes;

    @Min(0)
    @Column(name = "correct_answers")
    private Integer correctAnswers;

    @Min(1)
    @Column(name = "total_questions")
    private Integer totalQuestions;

    @Size(max = 2000)
    @Column(name = "feedback", length = 2000)
    private String feedback;

    @Column(name = "is_passed", nullable = false)
    private Boolean isPassed = false;

    @Min(1)
    @Column(name = "attempt_number", nullable = false)
    private Integer attemptNumber = 1;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(name = "passing_score", precision = 5, scale = 2)
    private BigDecimal passingScore;

    @Column(name = "time_limit_minutes")
    private Integer timeLimitMinutes;

    @Size(max = 100)
    @Column(name = "test_title", length = 100)
    private String testTitle;

    @Size(max = 100)
    @Column(name = "student_name", length = 100)
    private String studentName;

    @Size(max = 500)
    @Column(name = "remarks", length = 500)
    private String remarks;

    /**
     * デフォルトコンストラクタ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public MockTestResult() {
        this.status = Status.NOT_STARTED;
        this.isPassed = false;
        this.attemptNumber = 1;
    }

    /**
     * 基本情報付きコンストラクタ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public MockTestResult(Long testId, Long studentId, Long companyId) {
        this();
        this.testId = testId;
        this.studentId = studentId;
        this.companyId = companyId;
    }

    /**
     * 完全コンストラクタ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public MockTestResult(Long testId, Long studentId, Long companyId, 
                         String testTitle, String studentName, Integer attemptNumber) {
        this(testId, studentId, companyId);
        this.testTitle = testTitle;
        this.studentName = studentName;
        this.attemptNumber = attemptNumber;
    }

    // ===== Getter Methods =====
    /**
     * getTestId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getTestId() {
        return testId;
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
     * getCompanyId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Long getCompanyId() {
        return companyId;
    }
    /**
     * getStatus メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getStatus() {
        return status;
    }
    /**
     * getScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getScore() {
        return score;
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
     * getEndTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public LocalDateTime getEndTime() {
        return endTime;
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
     * getCorrectAnswers メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getCorrectAnswers() {
        return correctAnswers;
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
     * getFeedback メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getFeedback() {
        return feedback;
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
     * getAttemptNumber メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public Integer getAttemptNumber() {
        return attemptNumber;
    }
    /**
     * getPassingScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public BigDecimal getPassingScore() {
        return passingScore;
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
     * getTestTitle メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getTestTitle() {
        return testTitle;
    }
    /**
     * getStudentName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getStudentName() {
        return studentName;
    }
    /**
     * getRemarks メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public String getRemarks() {
        return remarks;
    }

    // ===== Setter Methods =====
    /**
     * setTestId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setTestId(Long testId) {
        this.testId = testId;
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
     * setCompanyId メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    /**
     * setStatus メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * setScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setScore(BigDecimal score) {
        this.score = score;
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
     * setEndTime メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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
     * setCorrectAnswers メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
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
     * setFeedback メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setFeedback(String feedback) {
        this.feedback = feedback;
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
     * setAttemptNumber メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }
    /**
     * setPassingScore メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setPassingScore(BigDecimal passingScore) {
        this.passingScore = passingScore;
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
     * setTestTitle メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }
    /**
     * setStudentName メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    /**
     * setRemarks メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    // ===== Business Logic Methods =====

    /**
     * テストが完了しているかチェック
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public boolean isCompleted() {
        return Status.COMPLETED.equals(this.status);
    }

    /**
     * テストが進行中かチェック
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public boolean isInProgress() {
        return Status.IN_PROGRESS.equals(this.status);
    }

    /**
     * テストが開始されていないかチェック
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public boolean isNotStarted() {
        return Status.NOT_STARTED.equals(this.status);
    }

    /**
     * テストがキャンセルされたかチェック
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public boolean isCancelled() {
        return Status.CANCELLED.equals(this.status);
    }

    /**
     * テストが期限切れかチェック
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public boolean isExpired() {
        return Status.EXPIRED.equals(this.status);
    }

    /**
     * スコア率を計算（正答率）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public BigDecimal getScorePercentage() {
        if (totalQuestions == null || totalQuestions == 0 || correctAnswers == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(correctAnswers)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(totalQuestions), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 実際の受験時間を計算（分）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public Integer getActualTimeSpent() {
        if (startTime != null && endTime != null) {
            return (int) Duration.between(startTime, endTime).toMinutes();
        }
        return timeSpentMinutes;
    }

    /**
     * 残り時間を計算（分）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public Integer getRemainingTimeMinutes() {
        if (timeLimitMinutes == null || startTime == null) {
            return null;
        }
        if (endTime != null) {
            return 0; // Already completed
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = startTime.plusMinutes(timeLimitMinutes);
        if (now.isAfter(deadline)) {
            return 0;
        }
        return (int) Duration.between(now, deadline).toMinutes();
    }

    /**
     * 合格判定
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public boolean isPassed() {
        if (score == null || passingScore == null) {
            return false;
        }
        return score.compareTo(passingScore) >= 0;
    }

    /**
     * テストを開始
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void startTest() {
        this.status = Status.IN_PROGRESS;
        this.startTime = LocalDateTime.now();
    }

    /**
     * テストを完了
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void completeTest(Integer correctAnswers, Integer totalQuestions, BigDecimal score) {
        this.status = Status.COMPLETED;
        this.endTime = LocalDateTime.now();
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
        this.score = score;
        this.timeSpentMinutes = getActualTimeSpent();
        this.isPassed = isPassed();
    }

    /**
     * テストをキャンセル
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void cancelTest(String reason) {
        this.status = Status.CANCELLED;
        this.endTime = LocalDateTime.now();
        this.remarks = reason;
    }

    /**
     * テストを期限切れにする
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void expireTest() {
        this.status = Status.EXPIRED;
        this.endTime = LocalDateTime.now();
        this.timeSpentMinutes = getActualTimeSpent();
    }

    /**
     * フィードバックを設定
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void setFeedbackWithResult(String feedback, boolean passed) {
        this.feedback = feedback;
        this.isPassed = passed;
    }

    /**
     * 次回受験のための初期化
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public MockTestResult createNextAttempt() {
        MockTestResult nextAttempt = new MockTestResult(this.testId, this.studentId, this.companyId);
        nextAttempt.setTestTitle(this.testTitle);
        nextAttempt.setStudentName(this.studentName);
        nextAttempt.setAttemptNumber(this.attemptNumber + 1);
        nextAttempt.setPassingScore(this.passingScore);
        nextAttempt.setTimeLimitMinutes(this.timeLimitMinutes);
        return nextAttempt;
    }

    /**
     * toString メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    @Override
    public String toString() {
        return "MockTestResult{" +
                "id=" + getId() +
                ", testId=" + testId +
                ", studentId=" + studentId +
                ", status=" + status +
                ", score=" + score +
                ", isPassed=" + isPassed +
                ", attemptNumber=" + attemptNumber +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                "}";
    }

    /**
     * テスト結果ステータス定数クラス
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static class Status {
        public static final String NOT_STARTED = "NOT_STARTED";
        public static final String IN_PROGRESS = "IN_PROGRESS";
        public static final String COMPLETED = "COMPLETED";
        public static final String CANCELLED = "CANCELLED";
        public static final String EXPIRED = "EXPIRED";
        public static final String SUSPENDED = "SUSPENDED";
    }

    /**
     * テスト結果の評価レベル定数クラス
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public static class Grade {
        public static final String EXCELLENT = "EXCELLENT";
        public static final String GOOD = "GOOD";
        public static final String AVERAGE = "AVERAGE";
        public static final String BELOW_AVERAGE = "BELOW_AVERAGE";
        public static final String POOR = "POOR";
    }

    /**
     * スコアに基づく評価レベルを取得
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public String getGradeLevel() {
        if (score == null) {
            return null;
        }

        if (score.compareTo(BigDecimal.valueOf(90)) >= 0) {
            return Grade.EXCELLENT;
        } else if (score.compareTo(BigDecimal.valueOf(80)) >= 0) {
            return Grade.GOOD;
        } else if (score.compareTo(BigDecimal.valueOf(70)) >= 0) {
            return Grade.AVERAGE;
        } else if (score.compareTo(BigDecimal.valueOf(60)) >= 0) {
            return Grade.BELOW_AVERAGE;
        } else {
            return Grade.POOR;
        }
    }
}
