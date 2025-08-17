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
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    List<Quiz> findByStudentIdOrderByStartTimeDesc(Long studentId);

    /**
     * プログラムIDでクイズを取得
     *
     * @param programId プログラムID
     * @return クイズリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    List<Quiz> findByProgramIdOrderByStartTimeDesc(Long programId);

    /**
     * ステータスでクイズを取得（開始時刻順）
     *
     * @param status クイズステータス
     * @return クイズリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    List<Quiz> findByStatusOrderByStartTimeDesc(String status);

    /**
     * ステータスでクイズを取得（終了時刻順）
     *
     * @param status クイズステータス
     * @return クイズリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    List<Quiz> findByStatusOrderByEndTimeDesc(String status);

    /**
     * 学生IDとステータスでクイズを取得（開始時刻順）
     *
     * @param studentId 学生ID
     * @param status クイズステータス
     * @return クイズリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    List<Quiz> findByStudentIdAndStatusOrderByStartTimeDesc(Long studentId, String status);

    /**
     * 学生IDとステータスでクイズを取得（終了時刻順）
     *
     * @param studentId 学生ID
     * @param status クイズステータス
     * @return クイズリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    List<Quiz> findByStudentIdAndStatusOrderByEndTimeDesc(Long studentId, String status);

    /**
     * プログラム別平均スコアを取得
     *
     * @param programId プログラムID
     * @return 平均スコア
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Query("SELECT AVG(q.percentageScore) FROM Quiz q WHERE q.trainingProgramId = :programId")
    Double findAverageScoreByProgramId(Long programId);

    /**
     * 学生とプログラム別平均スコアを取得
     *
     * @param studentId 学生ID
     * @param programId プログラムID
     * @return 平均スコア
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Query("SELECT AVG(q.percentageScore) FROM Quiz q WHERE q.studentId = :studentId AND q.trainingProgramId = :programId")
    Double findAverageScoreByStudentIdAndProgramId(Long studentId, Long programId);

    /**
     * ステータス別件数を取得
     *
     * @param status クイズステータス
     * @return 件数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    long countByStatus(String status);

    /**
     * 学生別件数を取得
     *
     * @param studentId 学生ID
     * @return 件数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    long countByStudentId(Long studentId);
}
