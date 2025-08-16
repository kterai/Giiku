package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.QuestionBank;

import java.util.List;
import java.util.Optional;

/**
 * QuestionBankのリポジトリインターフェース
 */
@Repository
public interface QuestionBankRepository extends JpaRepository<QuestionBank, Long> {

    // カスタムクエリメソッドをここに追加

}
