package jp.co.apsa.giiku.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 全エンティティの基底クラス
 * 共通的な属性とメタデータを提供する
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
     * 作成日時
     * レコードが最初に作成された日時を自動設定
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     * レコードが最後に更新された日時を自動設定
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * バージョン（楽観ロック）
     * 同時更新制御のためのバージョン番号
     */
    @Version
    @Column(name = "version", nullable = false)
    private Long version = 0L;
}
