package jp.co.apsa.giiku.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.StudentAnswer;

/**
 * 学生回答リポジトリ
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {

    /**
     * クイズIDと学生IDで回答を取得
     *
     * @param quizId クイズID
     * @param studentId 学生ID
     * @return 回答一覧
     */
    List<StudentAnswer> findByQuizIdAndStudentId(Long quizId, Long studentId);

    /**
     * 質問IDで回答を取得
     *
     * @param questionId 質問ID
     * @return 回答一覧
     */
    List<StudentAnswer> findByQuestionId(Long questionId);
}
