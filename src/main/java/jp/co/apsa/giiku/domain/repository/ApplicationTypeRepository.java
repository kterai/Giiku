package jp.co.apsa.giiku.domain.repository;

import jp.co.apsa.giiku.domain.entity.ApplicationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 申請種別リポジトリインターフェース
 * エンティティ定義に合わせたシンプルな検索メソッドのみ提供します。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
public interface ApplicationTypeRepository extends JpaRepository<ApplicationType, Long> {

    /** 申請種別名で検索 */
    Optional<ApplicationType> findByName(String name);

    /** 申請種別名の部分一致検索（表示順） */
    List<ApplicationType> findByNameContainingOrderByDisplayOrder(String name);

    /** アクティブフラグで検索（表示順） */
    List<ApplicationType> findByActiveOrderByDisplayOrder(Boolean active);

    /** コードで検索 */
    Optional<ApplicationType> findByCode(String code);

    /** 表示順序で全件取得 */
    List<ApplicationType> findAllByOrderByDisplayOrder();

    /** 名称重複チェック */
    boolean existsByName(String name);
}
