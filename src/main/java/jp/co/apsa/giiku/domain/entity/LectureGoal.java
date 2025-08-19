package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 講義目標エンティティ
 * 講義に設定された学習目標を保持する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "lecture_goals")
@Data
@EqualsAndHashCode(callSuper = true)
public class LectureGoal extends BaseEntity {

    /** 講義ID */
    @NotNull
    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    /** 目標説明 */
    @NotBlank
    @Column(name = "goal_description", nullable = false, columnDefinition = "TEXT")
    private String goalDescription;

    /** 表示順 */
    @Column(name = "sort_order")
    private Integer sortOrder;
}
