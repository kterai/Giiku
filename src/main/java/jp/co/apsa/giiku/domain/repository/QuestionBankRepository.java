package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.QuestionBank;

import java.util.List;

/**
 * QuestionBankのリポジトリインターフェース。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface QuestionBankRepository extends JpaRepository<QuestionBank, Long>,
        JpaSpecificationExecutor<QuestionBank> {

    /**
     * 難易度で検索し、作成日時の降順で取得します。
     *
     * @param difficultyLevel 難易度
     * @return 該当する問題一覧
     */
    List<QuestionBank> findByDifficultyLevelAndIsActiveTrueOrderByCreatedAtDesc(String difficultyLevel);

    /**
     * 問題種別で検索し、作成日時の降順で取得します。
     *
     * @param questionType 問題種別
     * @return 該当する問題一覧
     */
    List<QuestionBank> findByQuestionTypeAndIsActiveTrueOrderByCreatedAtDesc(String questionType);

    /**
     * 有効な問題を作成日時の降順で取得します。
     *
     * @return 有効な問題一覧
     */
    List<QuestionBank> findByIsActiveTrueOrderByCreatedAtDesc();

    /**
     * 問題文に指定文字列を含む有効な問題を検索します。
     *
     * @param text 検索文字列
     * @return 該当する問題一覧
     */
    List<QuestionBank> findByQuestionTextContainingIgnoreCaseAndIsActiveTrue(String text);

    /**
     * チャプターIDで検索し、問題番号の昇順で取得します。
     *
     * @param chapterId チャプターID
     * @return 該当する問題一覧
     */
    @Query("SELECT q FROM QuestionBank q WHERE q.chapter.id = :chapterId ORDER BY q.questionNumber")
    List<QuestionBank> findByChapterIdOrderByQuestionNumber(@Param("chapterId") Long chapterId);

    /**
     * 講義IDで有効な問題を検索し、チャプターの並び順と問題番号の昇順で取得します。
     *
     * @param lectureId 講義ID
     * @return 該当する有効な問題一覧
     */
    @Query("SELECT q FROM QuestionBank q JOIN LectureChapterLink l ON q.chapter = l.chapter WHERE l.lectureId = :lectureId AND q.isActive = true AND l.sortOrder = (SELECT MIN(l2.sortOrder) FROM LectureChapterLink l2 WHERE l2.chapter = q.chapter AND l2.lectureId = :lectureId) ORDER BY l.sortOrder, q.questionNumber")
    List<QuestionBank> findByLectureIdOrderByChapterAndQuestionNumber(@Param("lectureId") Long lectureId);

    /**
     * 難易度別の問題数を取得します。
     *
     * @return 難易度と件数の配列リスト
     */
    @Query("SELECT q.difficultyLevel, COUNT(q) FROM QuestionBank q GROUP BY q.difficultyLevel")
    List<Object[]> findQuestionCountByDifficultyLevel();

    /**
     * 有効な問題の件数を取得します。
     *
     * @return 有効な問題数
     */
    long countByIsActiveTrue();
}
