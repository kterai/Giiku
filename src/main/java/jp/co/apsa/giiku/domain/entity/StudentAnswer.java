package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 学生回答エンティティ
 * クイズの各設問に対する学生の回答を管理します。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "student_answers", indexes = {
    @Index(name = "idx_quiz_question_student", columnList = "quiz_id,question_id,student_id", unique = true)
})
@Data
public class StudentAnswer {

    /** 識別ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 質問ID */
    @Column(name = "question_id", nullable = false)
    private Long questionId;

    /** クイズID */
    @Column(name = "quiz_id", nullable = false)
    private Long quizId;

    /** 学生ID */
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    /** 回答内容 */
    @Column(name = "answer_text", columnDefinition = "TEXT")
    private String answerText;

    /** 回答日時 */
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
}
