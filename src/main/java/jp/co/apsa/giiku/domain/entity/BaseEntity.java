package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 全エンティティの基底クラス
 * 共通的なIDとバージョン情報を提供する
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@MappedSuperclass
@Data
public abstract class BaseEntity {

    /**
     * プライマリキー（ID）
     * 自動生成される一意の識別子
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * バージョン（楽観ロック）
     * 同時更新制御のためのバージョン番号
     */
    @Version
    @Column(name = "version", nullable = false)
    private Long version = 0L;
}
