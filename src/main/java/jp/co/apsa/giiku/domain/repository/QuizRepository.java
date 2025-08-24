package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.Quiz;

import java.util.List;

/**
 * Quizのリポジトリインターフェース
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>, JpaSpecificationExecutor<Quiz> {

    /**
     * 学生IDでクイズを取得
     *
     * @param studentId 学生ID
     * @return クイズリスト
     */
    List<Quiz> findByStudentIdOrderByStartTimeDesc(Long studentId);

    /**
     * トレーニングプログラムIDでクイズを取得
     *
     * @param trainingProgramId トレーニングプログラムID
     * @return クイズリスト
     */
    List<Quiz> findByTrainingProgramIdOrderByStartTimeDesc(Long trainingProgramId);

    /**
     * クイズステータスでクイズを取得（開始時刻順）
     *
     * @param quizStatus クイズステータス
     * @return クイズリスト
     */
    List<Quiz> findByQuizStatusOrderByStartTimeDesc(String quizStatus);

    /**
     * クイズステータスでクイズを取得（終了時刻順）
     *
     * @param quizStatus クイズステータス
     * @return クイズリスト
     */
    List<Quiz> findByQuizStatusOrderByEndTimeDesc(String quizStatus);

    /**
     * 学生IDとクイズステータスでクイズを取得（開始時刻順）
     *
     * @param studentId 学生ID
     * @param quizStatus クイズステータス
     * @return クイズリスト
     */
    List<Quiz> findByStudentIdAndQuizStatusOrderByStartTimeDesc(Long studentId, String quizStatus);

    /**
     * 学生IDとクイズステータスでクイズを取得（終了時刻順）
     *
     * @param studentId 学生ID
     * @param quizStatus クイズステータス
     * @return クイズリスト
     */
    List<Quiz> findByStudentIdAndQuizStatusOrderByEndTimeDesc(Long studentId, String quizStatus);

    /**
     * プログラム別平均スコアを取得
     *
     * @param programId プログラムID
     * @return 平均スコア
     */
    @Query("SELECT AVG(q.percentageScore) FROM Quiz q WHERE q.trainingProgramId = :programId")
    Double findAverageScoreByProgramId(Long programId);

    /**
     * 学生とプログラム別平均スコアを取得
     *
     * @param studentId 学生ID
     * @param programId プログラムID
     * @return 平均スコア
     */
    @Query("SELECT AVG(q.percentageScore) FROM Quiz q WHERE q.studentId = :studentId AND q.trainingProgramId = :programId")
    Double findAverageScoreByStudentIdAndProgramId(Long studentId, Long programId);

    /**
     * ステータス別件数を取得
     *
     * @param status クイズステータス
     * @return 件数
     */
    long countByQuizStatus(String quizStatus);

    /**
     * 学生別件数を取得
     *
     * @param studentId 学生ID
     * @return 件数
     */
    long countByStudentId(Long studentId);
}
