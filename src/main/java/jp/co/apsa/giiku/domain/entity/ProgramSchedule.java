package jp.co.apsa.giiku.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * プログラムスケジュールエンティティ
 * 研修プログラム全体のスケジュール管理
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "program_schedules")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProgramSchedule extends BaseEntity {

    // ID はBaseEntityから継承

    /**
     * 研修プログラムID（外部キー）
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "training_program_id", nullable = false)
    private Long trainingProgramId;

    /**
     * スケジュール名
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @NotBlank(message = "スケジュール名は必須です")
    @Size(max = 100, message = "スケジュール名は100文字以内で入力してください")
    @Column(name = "schedule_name", length = 100, nullable = false)
    private String scheduleName;

    /**
     * スケジュール説明
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Size(max = 500, message = "スケジュール説明は500文字以内で入力してください")
    @Column(name = "schedule_description", length = 500)
    private String scheduleDescription;

    /**
     * 開始日
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /**
     * 終了日
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /**
     * スケジュールステータス
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @NotBlank(message = "スケジュールステータスは必須です")
    @Pattern(regexp = "^(DRAFT|ACTIVE|COMPLETED|CANCELLED)$")
    @Column(name = "schedule_status", length = 20, nullable = false)
    private String scheduleStatus = "DRAFT";

    /**
     * 総日数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Min(value = 1, message = "総日数は1以上である必要があります")
    @Column(name = "total_days", nullable = false)
    private Integer totalDays;

    /**
     * 更新日時
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Column(name = "schedule_updated_at")
    private LocalDateTime scheduleUpdatedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.scheduleUpdatedAt = LocalDateTime.now();
        if (this.startDate != null && this.endDate != null) {
            this.totalDays = (int) (this.endDate.toEpochDay() - this.startDate.toEpochDay() + 1);
        }
    }
    /**
     * isActive メソッド
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */

    public boolean isActive() {
        return "ACTIVE".equals(this.scheduleStatus);
    }
}
