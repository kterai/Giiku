package jp.co.apsa.giiku.dto;

/**
 * 演習回答DTO
 *
 * 学生が演習問題に回答した内容を表します。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public class ExerciseAnswer {

    /** 学生ID */
    private Long studentId;

    /** 講義ID */
    private Long lectureId;

    /** 回答内容 */
    private String answerText;

    /** 正解かどうか */
    private Boolean correct;

    /** デフォルトコンストラクタ */
    public ExerciseAnswer() {
    }

    /**
     * @return 学生ID
     */
    public Long getStudentId() {
        return studentId;
    }

    /**
     * @param studentId 学生ID
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    /**
     * @return 講義ID
     */
    public Long getLectureId() {
        return lectureId;
    }

    /**
     * @param lectureId 講義ID
     */
    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    /**
     * @return 回答内容
     */
    public String getAnswerText() {
        return answerText;
    }

    /**
     * @param answerText 回答内容
     */
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    /**
     * @return 正解かどうか
     */
    public Boolean getCorrect() {
        return correct;
    }

    /**
     * @param correct 正解かどうか
     */
    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
