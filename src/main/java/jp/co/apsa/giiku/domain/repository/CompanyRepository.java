package jp.co.apsa.giiku.domain.repository;

import jp.co.apsa.giiku.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * 会社リポジトリインターフェース。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Repository
@Validated
public interface CompanyRepository extends JpaRepository<Company, Long> {

    /**
     * コードで会社を検索します。
     *
     * @param code 会社コード
     * @return 該当する会社（存在しない場合はEmpty）
     */
    Optional<Company> findByCode(@jakarta.validation.constraints.NotBlank(message = "会社コードは必須です") String code);

    /**
     * 有効な会社を取得します。
     *
     * @return 有効な会社のリスト
     */
    List<Company> findByActiveTrue();
}
