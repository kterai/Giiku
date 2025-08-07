/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.unryu.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 申請添付ファイルエンティティクラス
 * 
 * 申請に添付されたファイルの情報を管理します。
 * ファイルのメタデータ、保存場所、セキュリティ情報を記録し、
 * ファイルの完全性とアクセス制御を提供します。
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Entity
@Table(name = "application_attachments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"application"})
@EntityListeners(AuditingEntityListener.class)
public class ApplicationAttachment {

    /**
     * 添付ファイルID（主キー）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 申請ID
     */
    @NotNull(message = "申請IDは必須です")
    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    /**
     * 元のファイル名
     */
    @NotBlank(message = "ファイル名は必須です")
    @Size(max = 255, message = "ファイル名は255文字以内で入力してください")
    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    /**
     * 保存時のファイル名（UUID等で生成）
     */
    @NotBlank(message = "保存ファイル名は必須です")
    @Size(max = 255, message = "保存ファイル名は255文字以内で入力してください")
    @Column(name = "stored_filename", nullable = false)
    private String storedFilename;

    /**
     * ファイルパス
     */
    @NotBlank(message = "ファイルパスは必須です")
    @Size(max = 500, message = "ファイルパスは500文字以内で入力してください")
    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath;

    /**
     * ファイルサイズ（バイト）
     */
    @NotNull(message = "ファイルサイズは必須です")
    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    /**
     * コンテンツタイプ（MIME Type）
     */
    @Size(max = 100, message = "コンテンツタイプは100文字以内で入力してください")
    @Column(name = "content_type", length = 100)
    private String contentType;

    /**
     * ファイル拡張子
     */
    @Size(max = 10, message = "ファイル拡張子は10文字以内で入力してください")
    @Column(name = "file_extension", length = 10)
    private String fileExtension;

    /**
     * ファイルハッシュ値（整合性チェック用）
     */
    @Size(max = 128, message = "ハッシュ値は128文字以内で入力してください")
    @Column(name = "file_hash", length = 128)
    private String fileHash;

    /**
     * ハッシュアルゴリズム（SHA-256等）
     */
    @Size(max = 20, message = "ハッシュアルゴリズムは20文字以内で入力してください")
    @Column(name = "hash_algorithm", length = 20)
    private String hashAlgorithm;

    /**
     * ファイル説明
     */
    @Size(max = 500, message = "ファイル説明は500文字以内で入力してください")
    @Column(name = "description", length = 500)
    private String description;

    /**
     * 表示順序
     */
    @Column(name = "display_order")
    private Integer displayOrder;

    /**
     * ダウンロード可能フラグ
     */
    @Column(name = "downloadable", nullable = false)
    private Boolean downloadable = true;

    /**
     * ウイルススキャン状態（PENDING, CLEAN, INFECTED, ERROR）
     */
    @Size(max = 20, message = "スキャン状態は20文字以内で入力してください")
    @Column(name = "scan_status", length = 20)
    private String scanStatus;

    /**
     * スキャン実行日時
     */
    @Column(name = "scanned_at")
    private LocalDateTime scannedAt;

    /**
     * 作成者ID
     */
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    /**
     * 作成日時
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 更新者ID
     */
    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;

    /**
     * 更新日時
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // リレーション

    /**
     * 申請
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", insertable = false, updatable = false)
    private Application application;
}
