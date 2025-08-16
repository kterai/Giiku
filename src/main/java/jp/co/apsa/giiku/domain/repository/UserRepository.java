package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Userのリポジトリインターフェース
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // カスタムクエリメソッドをここに追加

}
