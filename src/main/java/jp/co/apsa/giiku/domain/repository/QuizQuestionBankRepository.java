package jp.co.apsa.giiku.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.QuizQuestionBank;

/**
 * QuizQuestionBank リポジトリインターフェース。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface QuizQuestionBankRepository extends JpaRepository<QuizQuestionBank, Long> {

    /**
     * 指定されたチャプターIDのクイズ問題を問題番号順に取得します。
     *
     * @param chapterId チャプターID
     * @return クイズ問題一覧
     */
    @Query("SELECT q FROM QuizQuestionBank q WHERE q.chapter.id = :chapterId ORDER BY q.questionNumber")
    List<QuizQuestionBank> findByChapterIdOrderByQuestionNumber(@Param("chapterId") Long chapterId);

    /**
     * 指定された講義IDの有効なクイズ問題をチャプター順・問題番号順に取得します。
     *
     * @param lectureId 講義ID
     * @return クイズ問題一覧
     */
    @Query("SELECT q FROM QuizQuestionBank q JOIN q.chapter c JOIN LectureChapterLink l ON c = l.chapter WHERE l.lectureId = :lectureId AND q.isActive = true ORDER BY l.sortOrder, q.questionNumber")
    List<QuizQuestionBank> findByLectureIdOrderByChapterAndQuestionNumber(@Param("lectureId") Long lectureId);
}
