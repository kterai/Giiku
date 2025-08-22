package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.StudentGradeSummary;

/**
 * StudentGradeSummaryのリポジトリインターフェース
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface StudentGradeSummaryRepository extends JpaRepository<StudentGradeSummary, Long> {
    // カスタムクエリメソッドをここに追加
}

