package jp.co.apsa.giiku.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.apsa.giiku.domain.entity.UserRole;

import java.util.List;
import java.util.Optional;

/**
 * UserRoleのリポジトリインターフェース
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    // カスタムクエリメソッドをここに追加

}
