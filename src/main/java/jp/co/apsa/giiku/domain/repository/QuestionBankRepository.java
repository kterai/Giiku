package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
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
     * 企業IDで検索し、作成日時の降順で取得します。
     *
     * @param companyId 企業ID
     * @return 該当する問題一覧
     */
    List<QuestionBank> findByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(Long companyId);

    /**
     * カテゴリで検索し、作成日時の降順で取得します。
     *
     * @param category カテゴリ
     * @return 該当する問題一覧
     */
    List<QuestionBank> findByCategoryAndIsActiveTrueOrderByCreatedAtDesc(String category);

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
     * タグに指定文字列を含む有効な問題を検索します。
     *
     * @param tag タグ文字列
     * @return 該当する問題一覧
     */
    List<QuestionBank> findByTagsContainingIgnoreCaseAndIsActiveTrue(String tag);

    /**
     * カテゴリ別の問題数を取得します。
     *
     * @return カテゴリと件数の配列リスト
     */
    @Query("SELECT q.category, COUNT(q) FROM QuestionBank q GROUP BY q.category")
    List<Object[]> findQuestionCountByCategory();

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

    /**
     * 企業別の有効な問題数を取得します。
     *
     * @param companyId 企業ID
     * @return 問題数
     */
    long countByCompanyIdAndIsActiveTrue(Long companyId);

    /**
     * カテゴリ別の有効な問題数を取得します。
     *
     * @param category カテゴリ
     * @return 問題数
     */
    long countByCategoryAndIsActiveTrue(String category);
}
