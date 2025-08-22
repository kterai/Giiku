package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 研修プログラムエンティティ
 * 研修プログラムの基本情報を管理する
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "training_programs")
@Data
@EqualsAndHashCode(callSuper = true)
public class TrainingProgram extends AuditableEntity {

    /** プログラム名 */
    @NotBlank(message = "プログラム名は必須です")
    @Size(max = 200, message = "プログラム名は200文字以下で入力してください")
    @Column(name = "program_name", nullable = false, length = 200)
    private String programName;

    /** プログラム説明 */
    @Column(name = "description")
    private String description;

    /** 会社ID（プログラムを提供する会社） */
    @Column(name = "company_id")
    private Long companyId;

    /** 期間（月数） */
    @Column(name = "duration_months")
    private Integer durationMonths = 3;

    /** 総学習時間 */
    @Column(name = "total_hours")
    private Integer totalHours = 0;

    /** 難易度レベル */
    @Size(max = 20, message = "難易度レベルは20文字以下で入力してください")
    @Column(name = "difficulty_level", length = 20)
    private String difficultyLevel;

    /** 受講前提条件 */
    @Column(name = "prerequisites")
    private String prerequisites;

    /** 最大受講者数 */
    @Column(name = "max_students")
    private Integer maxStudents = 30;

    /** テンプレートフラグ */
    @Column(name = "is_template")
    private Boolean isTemplate = false;

    /** 有効状態 */
    @Column(name = "is_active")
    private Boolean isActive = true;

    /** 作成者ユーザーID */
    @Column(name = "created_by")
    private Long createdBy;

    /** 更新者ユーザーID */
    @Column(name = "updated_by")
    private Long updatedBy;

    /** getName メソッド */
    public String getName() { return programName; }

    /** setName メソッド */
    public void setName(String name) { this.programName = name; }
}
