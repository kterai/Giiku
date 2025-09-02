package jp.co.apsa.giiku.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.co.apsa.giiku.domain.entity.StudentAnswer;
import jp.co.apsa.giiku.domain.repository.StudentAnswerRepository;

/**
 * 学生回答サービス
 * クイズ回答の保存・取得機能を提供します。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class StudentAnswerService {

    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    /**
     * 学生回答を保存
     *
     * @param quizId クイズID
     * @param questionId 質問ID
     * @param studentId 学生ID
     * @param answerText 回答内容
     * @return 保存された回答
     */
    public StudentAnswer saveAnswer(Long quizId, Long questionId, Long studentId, String answerText) {
        StudentAnswer answer = new StudentAnswer();
        answer.setQuizId(quizId);
        answer.setQuestionId(questionId);
        answer.setStudentId(studentId);
        answer.setAnswerText(answerText);
        answer.setSubmittedAt(LocalDateTime.now());
        return studentAnswerRepository.save(answer);
    }

    /**
     * 演習回答を保存
     *
     * @param questionId 質問ID
     * @param studentId 学生ID
     * @param answerText 回答内容
     * @return 保存された回答
     */
    public StudentAnswer saveExerciseAnswer(Long questionId, Long studentId, String answerText) {
        return saveAnswer(0L, questionId, studentId, answerText);
    }

    /**
     * クイズIDと学生IDで回答を取得
     *
     * @param quizId クイズID
     * @param studentId 学生ID
     * @return 回答一覧
     */
    @Transactional(readOnly = true)
    public List<StudentAnswer> getAnswers(Long quizId, Long studentId) {
        return studentAnswerRepository.findByQuizIdAndStudentId(quizId, studentId);
    }

    /**
     * 質問IDで回答一覧を取得
     *
     * @param questionId 質問ID
     * @return 回答一覧
     */
    @Transactional(readOnly = true)
    public List<StudentAnswer> getAnswersByQuestionId(Long questionId) {
        return studentAnswerRepository.findAllByQuestionId(questionId);
    }
}
