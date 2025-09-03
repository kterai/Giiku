package jp.co.apsa.giiku.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * クイズ回答リクエスト用DTOクラス
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class QuizAnswerRequest {

    @NotNull(message = "クイズIDは必須です")
    private Long quizId;

    @NotNull(message = "学生IDは必須です")
    private Long studentId;

    @NotBlank(message = "回答は必須です")
    private String answer;

    /** QuizAnswerRequest メソッド */
    public QuizAnswerRequest() {}

    /** QuizAnswerRequest メソッド */
    public QuizAnswerRequest(Long quizId, Long studentId, String answer) {
        this.quizId = quizId;
        this.studentId = studentId;
        this.answer = answer;
    }

    /** getQuizId メソッド */
    public Long getQuizId() {
        return quizId;
    }

    /** setQuizId メソッド */
    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    /** getStudentId メソッド */
    public Long getStudentId() {
        return studentId;
    }

    /** setStudentId メソッド */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    /** getAnswer メソッド */
    public String getAnswer() {
        return answer;
    }

    /** setAnswer メソッド */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /** toString メソッド */
    @Override
    public String toString() {
        return "QuizAnswerRequest{" +
                "quizId=" + quizId +
                ", studentId=" + studentId +
                ", answer='" + answer + '\'' +
                '}';
    }
}

