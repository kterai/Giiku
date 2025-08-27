package jp.co.apsa.giiku.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
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
     * 指定された講義IDのクイズ問題を問題番号順に取得します。
     *
     * @param lectureId 講義ID
     * @return クイズ問題一覧
     */
    List<QuizQuestionBank> findByLectureIdOrderByQuestionNumber(Long lectureId);
}
