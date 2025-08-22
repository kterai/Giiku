package jp.co.apsa.giiku.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 模擬試験エンティティ
 * 研修プログラム内での練習・評価テスト管理
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "mock_tests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MockTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long testId;

    @Column(name = "company_id")
    private Long companyId;

    @NotNull(message = "プログラムIDは必須です")
    @Column(name = "program_id", nullable = false)
    private Long programId;

    @NotBlank(message = "テストタイプは必須です")
    @Size(max = 50, message = "テストタイプは50文字以内で入力してください")
    @Column(name = "test_type", nullable = false, length = 50)
    private String testType; // PRACTICE, MIDTERM, FINAL, QUIZ, ASSESSMENT

    @NotBlank(message = "テストタイトルは必須です")
    @Size(max = 200, message = "テストタイトルは200文字以内で入力してください")
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Size(max = 1000, message = "テスト説明は1000文字以内で入力してください")
    @Column(name = "description", length = 1000)
    private String description;

    @NotNull(message = "制限時間は必須です")
    @Min(value = 5, message = "制限時間は5分以上で設定してください")
    @Max(value = 300, message = "制限時間は300分以下で設定してください")
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @NotNull(message = "問題数は必須です")
    @Min(value = 1, message = "問題数は1問以上で設定してください")
    @Max(value = 200, message = "問題数は200問以下で設定してください")
    @Column(name = "total_questions", nullable = false)
    private Integer totalQuestions;

    @NotNull(message = "合格点は必須です")
    @DecimalMin(value = "0.0", message = "合格点は0点以上で設定してください")
    @DecimalMax(value = "100.0", message = "合格点は100点以下で設定してください")
    @Column(name = "passing_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal passingScore;

    @NotBlank(message = "ステータスは必須です")
    @Size(max = 20, message = "ステータスは20文字以内で入力してください")
    @Column(name = "status", nullable = false, length = 20)
    private String status; // DRAFT, ACTIVE, INACTIVE, ARCHIVED

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "max_attempts")
    @Min(value = 1, message = "最大受験回数は1回以上で設定してください")
    private Integer maxAttempts;

    @Column(name = "show_correct_answers", nullable = false)
    private Boolean showCorrectAnswers = false;

    @Column(name = "randomize_questions", nullable = false)
    private Boolean randomizeQuestions = false;

    @Column(name = "time_limit_per_question")
    @Min(value = 30, message = "問題別制限時間は30秒以上で設定してください")
    private Integer timeLimitPerQuestion;

    @Column(name = "difficulty_level")
    @Size(max = 20, message = "難易度は20文字以内で入力してください")
    private String difficultyLevel; // BEGINNER, INTERMEDIATE, ADVANCED

    @Column(name = "question_types")
    @Size(max = 200, message = "問題タイプは200文字以内で入力してください")
    private String questionTypes; // 複数選択, 単一選択, 記述式 etc.

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    // 監査フィールド
    @CreatedDate
    @NotNull(message = "作成日時は必須です")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @NotNull(message = "更新日時は必須です")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @NotNull(message = "作成者IDは必須です")
    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    @LastModifiedBy
    @NotNull(message = "更新者IDは必須です")
    @Column(name = "updated_by")
    private Long updatedBy;

    // ビジネスロジックメソッド

    /**
     * テストが現在受験可能かどうかを判定
     * @return 受験可能な場合true
     */
    public boolean isAvailableNow() {
        if (!Boolean.TRUE.equals(this.isActive)) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        boolean afterStart = (this.startDate == null || now.isAfter(this.startDate));
        boolean beforeEnd = (this.endDate == null || now.isBefore(this.endDate));

        return afterStart && beforeEnd;
    }

    /**
     * 1問あたりの平均時間を計算
     * @return 1問あたりの分数
     */
    public double getAverageTimePerQuestion() {
        if (this.totalQuestions == null || this.totalQuestions <= 0) {
            return 0.0;
        }
        return (double) this.durationMinutes / this.totalQuestions;
    }

    /**
     * テストタイプの表示名を取得
     * @return 表示用テストタイプ名
     */
    public String getTestTypeDisplayName() {
        if (this.testType == null) {
            return "未設定";
        }

        switch (this.testType.toUpperCase()) {
            case "PRACTICE":
                return "練習テスト";
            case "MIDTERM":
                return "中間テスト";
            case "FINAL":
                return "期末テスト";
            case "QUIZ":
                return "小テスト";
            case "ASSESSMENT":
                return "評価テスト";
            default:
                return this.testType;
        }
    }

    /**
     * 難易度レベルの表示名を取得
     * @return 表示用難易度名
     */
    public String getDifficultyDisplayName() {
        if (this.difficultyLevel == null) {
            return "未設定";
        }

        switch (this.difficultyLevel.toUpperCase()) {
            case "BEGINNER":
                return "初級";
            case "INTERMEDIATE":
                return "中級";
            case "ADVANCED":
                return "上級";
            default:
                return this.difficultyLevel;
        }
    }

    /**
     * ID のエイリアスアクセサ
     * @return テストID
     */
    public Long getId() {
        return this.testId;
    }

    /**
     * ID のエイリアスセッター
     * @param id テストID
     */
    public void setId(Long id) {
        this.testId = id;
    }

    /**
     * 制限時間のエイリアスアクセサ
     * @return 制限時間（分）
     */
    public Integer getDuration() {
        return this.durationMinutes;
    }

    /**
     * 制限時間のエイリアスセッター
     * @param duration 制限時間（分）
     */
    public void setDuration(Integer duration) {
        this.durationMinutes = duration;
    }

    /**
     * テスト時間の表示形式を取得
     * @return フォーマット済み時間表示
     */
    public String getFormattedDuration() {
        if (this.durationMinutes == null || this.durationMinutes <= 0) {
            return "未設定";
        }

        int hours = this.durationMinutes / 60;
        int minutes = this.durationMinutes % 60;

        if (hours > 0 && minutes > 0) {
            return String.format("%d時間%d分", hours, minutes);
        } else if (hours > 0) {
            return String.format("%d時間", hours);
        } else {
            return String.format("%d分", minutes);
        }
    }

    /** equals メソッド */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MockTest mockTest = (MockTest) o;
        return Objects.equals(testId, mockTest.testId);
    }

    /** hashCode メソッド */
    @Override
    public int hashCode() {
        return Objects.hash(testId);
    }

    /** toString メソッド */
    @Override
    public String toString() {
        return String.format("MockTest{id=%d, programId=%d, type='%s', title='%s', status='%s'}", 
                           testId, programId, testType, title, status);
    }
}
