package jp.co.apsa.giiku.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * 講義エンティティ
 * 個別の講義・授業の詳細情報を管理
 * 
 * @author Giiku LMS Team
 * @version 1.0
 * @since 2024-01
 */
@Entity
@Table(name = "lectures")
@Data
@EqualsAndHashCode(callSuper = true)
public class Lecture extends BaseEntity {

    // ID はBaseEntityから継承

    /**
     * 日次スケジュールID（外部キー）
     */
    @Column(name = "daily_schedule_id", nullable = false)
    private Long dailyScheduleId;

    /**
     * 講師ID（外部キー）
     */
    @Column(name = "instructor_id")
    private Long instructorId;

    /**
     * 講義タイトル
     */
    @NotBlank(message = "講義タイトルは必須です")
    @Size(max = 200, message = "講義タイトルは200文字以内で入力してください")
    @Column(name = "lecture_title", length = 200, nullable = false)
    private String lectureTitle;

    /**
     * 講義内容
     */
    @Size(max = 2000, message = "講義内容は2000文字以内で入力してください")
    @Column(name = "lecture_content", length = 2000)
    private String lectureContent;

    /**
     * 講義種別
     * THEORY: 理論, PRACTICE: 実習, EXERCISE: 演習, TEST: テスト, DISCUSSION: ディスカッション
     */
    @NotBlank(message = "講義種別は必須です")
    @Pattern(regexp = "^(THEORY|PRACTICE|EXERCISE|TEST|DISCUSSION)$")
    @Column(name = "lecture_type", length = 20, nullable = false)
    private String lectureType;

    /**
     * 想定時間（分）
     */
    @Min(value = 15, message = "想定時間は15分以上である必要があります")
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    /**
     * 順序
     */
    @Min(value = 1, message = "順序は1以上である必要があります")
    @Column(name = "sequence_order", nullable = false)
    private Integer sequenceOrder;

    /**
     * 講義目標
     */
    @Size(max = 500, message = "講義目標は500文字以内で入力してください")
    @Column(name = "lecture_objectives", length = 500)
    private String lectureObjectives;

    /**
     * 必要な教材・資料
     */
    @Size(max = 500, message = "必要な教材・資料は500文字以内で入力してください")
    @Column(name = "required_materials", length = 500)
    private String requiredMaterials;

    /**
     * 講義ステータス
     */
    @NotBlank(message = "講義ステータスは必須です")
    @Pattern(regexp = "^(SCHEDULED|IN_PROGRESS|COMPLETED|CANCELLED)$")
    @Column(name = "lecture_status", length = 20, nullable = false)
    private String lectureStatus = "SCHEDULED";

    /**
     * 実施開始日時
     */
    @Column(name = "actual_start_time")
    private LocalDateTime actualStartTime;

    /**
     * 実施終了日時
     */
    @Column(name = "actual_end_time")
    private LocalDateTime actualEndTime;

    /**
     * 更新日時
     */
    @Column(name = "lecture_updated_at")
    private LocalDateTime lectureUpdatedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lectureUpdatedAt = LocalDateTime.now();
    }

    public boolean isCompleted() {
        return "COMPLETED".equals(this.lectureStatus);
    }

    public String getLectureTypeDisplay() {
        switch (this.lectureType) {
            case "THEORY": return "理論";
            case "PRACTICE": return "実習";
            case "EXERCISE": return "演習";
            case "TEST": return "テスト";
            case "DISCUSSION": return "ディスカッション";
            default: return "未分類";
        }
    }
}