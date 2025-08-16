package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.Quiz;

import java.util.List;
import java.util.Optional;

/**
 * Quizのリポジトリインターフェース
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // カスタムクエリメソッドをここに追加

}
