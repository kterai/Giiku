/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information of
 * 株式会社アプサ ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with 株式会社アプサ.
 */
package jp.co.apsa.giiku.domain.valueobject;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * ファイルパスを表すValue Objectクラス。
 * <p>
 * このクラスは不変オブジェクトとして設計されており、ファイルパスの検証、
 * 操作、セキュリティチェック機能を提供します。
 * </p>
 * 
 * <h3>主な機能:</h3>
 * <ul>
 *   <li>ファイルパスの形式検証</li>
 *   <li>ファイル拡張子の抽出</li>
 *   <li>ファイル名の抽出</li>
 *   <li>ディレクトリパスの抽出</li>
 *   <li>相対パス・絶対パスの判定</li>
 *   <li>パストラバーサル攻撃対策</li>
 * </ul>
 * 
 * <h3>使用例:</h3>
 * <pre>{@code
 * FilePath filePath = new FilePath("/home/user/documents/report.pdf");
 * String fileName = filePath.getFileName();        // "report.pdf"
 * String extension = filePath.getExtension();      // "pdf"
 * String directory = filePath.getDirectoryPath();  // "/home/user/documents"
 * boolean isAbsolute = filePath.isAbsolute();      // true
 * }</pre>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public final class FilePath {

    /** ファイルパス文字列 */
    private final String path;

    /** 正規化されたPathオブジェクト */
    private final Path normalizedPath;

    /** 危険な文字パターン（パストラバーサル対策） */
    private static final Pattern DANGEROUS_PATTERN = Pattern.compile(
        ".*(\\.\\./|\\.\\.\\\\|%2e%2e%2f|%2e%2e%5c|\\.\\.%2f|\\.\\.%5c).*",
        Pattern.CASE_INSENSITIVE
    );

    /** 無効な文字パターン */
    private static final Pattern INVALID_CHARS_PATTERN = Pattern.compile("[\\x00-\\x1f\\x7f<>:\"|?*]");
    /** 最大パス長 */
    private static final int MAX_PATH_LENGTH = 260;

    /**
     * FilePathオブジェクトを構築します。
     * 
     * @param path ファイルパス文字列
     * @throws IllegalArgumentException パスが無効な場合
     * @throws NullPointerException パスがnullの場合
     */
    public FilePath(String path) {
        this.path = validateAndNormalizePath(path);
        this.normalizedPath = Paths.get(this.path).normalize();
    }

    /**
     * パスの検証と正規化を行います。
     * 
     * @param path 検証対象のパス
     * @return 正規化されたパス
     * @throws IllegalArgumentException パスが無効な場合
     * @throws NullPointerException パスがnullの場合
     */
    private String validateAndNormalizePath(String path) {
        Objects.requireNonNull(path, "ファイルパスはnullにできません");

        if (path.trim().isEmpty()) {
            throw new IllegalArgumentException("ファイルパスは空文字列にできません");
        }

        if (path.length() > MAX_PATH_LENGTH) {
            throw new IllegalArgumentException(
                String.format("ファイルパスが最大長(%d文字)を超えています: %d文字", 
                    MAX_PATH_LENGTH, path.length())
            );
        }

        // パストラバーサル攻撃のチェック
        if (DANGEROUS_PATTERN.matcher(path).matches()) {
            throw new IllegalArgumentException(
                "パストラバーサル攻撃の可能性があるパスが検出されました: " + path
            );
        }

        // 無効な文字のチェック
        if (INVALID_CHARS_PATTERN.matcher(path).find()) {
            throw new IllegalArgumentException(
                "無効な文字が含まれています: " + path
            );
        }

        // Pathオブジェクトとして有効かチェック
        try {
            Paths.get(path);
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException(
                "無効なファイルパス形式です: " + path, e
            );
        }

        return path.replace('\\', '/'); // パス区切り文字を統一
    }

    /**
     * ファイル名（拡張子含む）を取得します。
     * 
     * @return ファイル名。ディレクトリのみの場合は空文字列
     */
    public String getFileName() {
        Path fileName = normalizedPath.getFileName();
        return fileName != null ? fileName.toString() : "";
    }

    /**
     * ファイル拡張子を取得します。
     * 
     * @return ファイル拡張子（ドット無し）。拡張子がない場合は空文字列
     */
    public String getExtension() {
        String fileName = getFileName();
        if (fileName.isEmpty()) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }

        return "";
    }

    /**
     * ファイル名（拡張子除く）を取得します。
     * 
     * @return ファイル名（拡張子除く）
     */
    public String getFileNameWithoutExtension() {
        String fileName = getFileName();
        if (fileName.isEmpty()) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }

        return fileName;
    }

    /**
     * ディレクトリパスを取得します。
     * 
     * @return ディレクトリパス。ルートディレクトリの場合は空文字列
     */
    public String getDirectoryPath() {
        Path parent = normalizedPath.getParent();
        return parent != null ? parent.toString() : "";
    }

    /**
     * 絶対パスかどうかを判定します。
     * 
     * @return 絶対パスの場合true、相対パスの場合false
     */
    public boolean isAbsolute() {
        return normalizedPath.isAbsolute();
    }

    /**
     * 相対パスかどうかを判定します。
     * 
     * @return 相対パスの場合true、絶対パスの場合false
     */
    public boolean isRelative() {
        return !isAbsolute();
    }

    /**
     * 指定された拡張子を持つかどうかを判定します。
     * 
     * @param extension 判定する拡張子（ドット無し）
     * @return 指定された拡張子を持つ場合true
     * @throws NullPointerException extensionがnullの場合
     */
    public boolean hasExtension(String extension) {
        Objects.requireNonNull(extension, "拡張子はnullにできません");
        return getExtension().equalsIgnoreCase(extension.toLowerCase());
    }

    /**
     * ファイルパスが安全かどうかを判定します。
     * <p>
     * パストラバーサル攻撃や無効な文字が含まれていないかをチェックします。
     * </p>
     * 
     * @return 安全な場合true
     */
    public boolean isSafe() {
        try {
            validateAndNormalizePath(this.path);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 正規化されたパス文字列を取得します。
     * 
     * @return 正規化されたパス文字列
     */
    public String getNormalizedPath() {
        return normalizedPath.toString();
    }

    /**
     * 元のパス文字列を取得します。
     * 
     * @return 元のパス文字列
     */
    public String getOriginalPath() {
        return path;
    }

    /**
     * 指定されたベースパスからの相対パスを取得します。
     * 
     * @param basePath ベースパス
     * @return 相対パス
     * @throws IllegalArgumentException 相対パスを計算できない場合
     * @throws NullPointerException basePathがnullの場合
     */
    public FilePath relativize(FilePath basePath) {
        Objects.requireNonNull(basePath, "ベースパスはnullにできません");

        try {
            Path relativePath = basePath.normalizedPath.relativize(this.normalizedPath);
            return new FilePath(relativePath.toString());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "相対パスを計算できません: " + this.path + " from " + basePath.path, e
            );
        }
    }

    /**
     * 指定されたパスを結合します。
     * 
     * @param other 結合するパス
     * @return 結合されたFilePath
     * @throws NullPointerException otherがnullの場合
     */
    public FilePath resolve(String other) {
        Objects.requireNonNull(other, "結合するパスはnullにできません");

        Path resolvedPath = this.normalizedPath.resolve(other);
        return new FilePath(resolvedPath.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FilePath filePath = (FilePath) obj;
        return Objects.equals(normalizedPath, filePath.normalizedPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalizedPath);
    }

    @Override
    public String toString() {
        return String.format("FilePath{path='%s', normalized='%s', absolute=%s}", 
            path, normalizedPath.toString(), isAbsolute());
    }

    /**
     * ファイルパスの詳細情報を文字列として取得します。
     * 
     * @return ファイルパスの詳細情報
     */
    public String toDetailString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FilePath Details:\n");
        sb.append("  Original Path: ").append(path).append("\n");
        sb.append("  Normalized Path: ").append(getNormalizedPath()).append("\n");
        sb.append("  File Name: ").append(getFileName()).append("\n");
        sb.append("  Extension: ").append(getExtension()).append("\n");
        sb.append("  Directory: ").append(getDirectoryPath()).append("\n");
        sb.append("  Is Absolute: ").append(isAbsolute()).append("\n");
        sb.append("  Is Safe: ").append(isSafe());
        return sb.toString();
    }
}
