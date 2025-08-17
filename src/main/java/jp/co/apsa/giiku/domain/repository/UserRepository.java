package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.User;

import java.util.Optional;

/**
 * Userのリポジトリインターフェース。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * ユーザー名でユーザーを検索します。
     *
     * @param username ユーザー名
     * @return 該当ユーザー（存在しない場合は空）
     */
    Optional<User> findByUsername(String username);

}
