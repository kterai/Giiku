package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.MockTest;

import java.util.List;
import java.util.Optional;

/**
 * MockTestのリポジトリインターフェース
 */
@Repository
public interface MockTestRepository extends JpaRepository<MockTest, Long> {

    // カスタムクエリメソッドをここに追加

}
