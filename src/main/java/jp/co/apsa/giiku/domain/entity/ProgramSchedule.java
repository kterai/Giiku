package jp.co.apsa.giiku.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * プログラムスケジュールエンティティ
 * 研修プログラム全体のスケジュール管理
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

    /** 研修プログラムID（外部キー） */
    @NotNull(message = "研修プログラムIDは必須です")
    @Column(name = "program_id", nullable = false)
    private Long programId;

    /** 講師ID（外部キー） */
    @Column(name = "instructor_id")
    private Long instructorId;

    /** 開始日 */
    @NotNull(message = "開始日は必須です")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /** 終了日 */
    @NotNull(message = "終了日は必須です")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /** 最大受講者数 */
    @Min(value = 0, message = "最大受講者数は0以上である必要があります")
    @Column(name = "max_students", nullable = false)
    private Integer maxStudents = 0;

    /** 現在の受講者数 */
    @Min(value = 0, message = "現在の受講者数は0以上である必要があります")
    @Column(name = "current_students", nullable = false)
    private Integer currentStudents = 0;

    /** スケジュールステータス */
    @NotBlank(message = "スケジュールステータスは必須です")
    @Pattern(regexp = "^(scheduled|active|completed|cancelled)$")
    @Column(name = "schedule_status", length = 20, nullable = false)
    private String scheduleStatus = "scheduled";

    /** 作成者（ユーザーID） */
    @Column(name = "created_by")
    private Long createdBy;

    /** 更新者（ユーザーID） */
    @Column(name = "updated_by")
    private Long updatedBy;

    /** isActive メソッド */
    public boolean isActive() {
        return "active".equals(this.scheduleStatus);
    }
}
