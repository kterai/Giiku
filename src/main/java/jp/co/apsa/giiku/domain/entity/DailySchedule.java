package jp.co.apsa.giiku.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 日次スケジュールエンティティ
 * 研修プログラムの日毎の詳細スケジュール管理
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "daily_schedules")
@Data
@EqualsAndHashCode(callSuper = true)
public class DailySchedule extends BaseEntity {

    // ID はBaseEntityから継承

    /** プログラムスケジュールID（外部キー） */
    @Column(name = "program_schedule_id", nullable = false)
    private Long programScheduleId;

    /** スケジュール日 */
    @Column(name = "scheduled_date", nullable = false)
    private LocalDate scheduledDate;

    /** 日ID */
    @Column(name = "day_id")
    private Long dayId;

    /** 開始時刻 */
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    /** 終了時刻 */
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    /** 場所・会場 */
    @Size(max = 100, message = "場所・会場は100文字以内で入力してください")
    @Column(name = "venue", length = 100)
    private String venue;

    /** 日次テーマ */
    @Size(max = 200, message = "日次テーマは200文字以内で入力してください")
    @Column(name = "daily_theme", length = 200)
    private String dailyTheme;

    /** 日次目標 */
    @Size(max = 500, message = "日次目標は500文字以内で入力してください")
    @Column(name = "daily_objectives", length = 500)
    private String dailyObjectives;

    /** 備考 */
    @Size(max = 500, message = "備考は500文字以内で入力してください")
    @Column(name = "notes", length = 500)
    private String notes;

    /** 日次ステータス */
    @NotBlank(message = "日次ステータスは必須です")
    @Pattern(regexp = "^(SCHEDULED|IN_PROGRESS|COMPLETED|CANCELLED)$")
    @Column(name = "daily_status", length = 20, nullable = false)
    private String dailyStatus = "SCHEDULED";

    /** 作成者ID */
    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    /** 更新者ID */
    @Column(name = "updated_by")
    private Long updatedBy;

    /** isCompleted メソッド */
    public boolean isCompleted() {
        return "COMPLETED".equals(this.dailyStatus);
    }
    /** getDurationMinutes メソッド */
    public int getDurationMinutes() {
        if (this.startTime != null && this.endTime != null) {
            return (int) java.time.Duration.between(this.startTime, this.endTime).toMinutes();
        }
        return 0;
    }

    /**
     * 互換用のスケジュール日取得メソッド。
     *
     * @return 対象日
     */
    public LocalDate getScheduleDate() {
        return this.scheduledDate;
    }

    /**
     * 互換用のスケジュール日設定メソッド。
     *
     * @param scheduleDate スケジュール日
     */
    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduledDate = scheduleDate;
    }

    /**
     * 互換用のタイトル取得メソッド。
     *
     * @return 日次テーマ
     */
    public String getTitle() {
        return this.dailyTheme;
    }

    /**
     * 互換用のタイトル設定メソッド。
     *
     * @param title 日次テーマ
     */
    public void setTitle(String title) {
        this.dailyTheme = title;
    }

    /**
     * 互換用のステータス取得メソッド。
     *
     * @return 日次ステータス
     */
    public String getStatus() {
        return this.dailyStatus;
    }

    /**
     * 互換用のステータス設定メソッド。
     *
     * @param status 日次ステータス
     */
    public void setStatus(String status) {
        this.dailyStatus = status;
    }
}
