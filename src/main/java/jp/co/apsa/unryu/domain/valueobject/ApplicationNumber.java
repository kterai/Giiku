/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.unryu.domain.valueobject;

import java.time.Year;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * 申請番号を表すValue Objectクラス
 * 
 * <p>申請番号は以下の形式で構成されます：</p>
 * <ul>
 *   <li>申請種別コード（2文字）</li>
 *   <li>ハイフン（-）</li>
 *   <li>年度（4桁）</li>
 *   <li>ハイフン（-）</li>
 *   <li>連番（6桁、ゼロパディング）</li>
 * </ul>
 * 
 * <p>例：UN-2024-001001</p>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public final class ApplicationNumber {

    /** 申請番号の正規表現パターン */
    private static final Pattern APPLICATION_NUMBER_PATTERN = 
        Pattern.compile("^([A-Z]{2})-(\\d{4})-(\\d{6})$");

    /** 申請種別コードの最大長 */
    private static final int TYPE_CODE_LENGTH = 2;

    /** 年度の桁数 */
    private static final int YEAR_LENGTH = 4;

    /** 連番の桁数 */
    private static final int SEQUENCE_LENGTH = 6;

    /** 申請番号の値 */
    private final String value;

    /** 申請種別コード */
    private final String typeCode;

    /** 年度 */
    private final int year;

    /** 連番 */
    private final int sequence;

    /**
     * 申請番号を指定してインスタンスを生成します。
     * 
     * @param value 申請番号文字列
     * @throws IllegalArgumentException 申請番号が無効な場合
     */
    public ApplicationNumber(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("申請番号は必須です");
        }

        String trimmedValue = value.trim();
        if (!isValidFormat(trimmedValue)) {
            throw new IllegalArgumentException(
                "申請番号の形式が正しくありません。正しい形式：XX-YYYY-NNNNNN（例：UN-2024-001001）");
        }

        this.value = trimmedValue;

        // 各部分を抽出
        Matcher matcher = APPLICATION_NUMBER_PATTERN.matcher(trimmedValue);
        if (matcher.matches()) {
            this.typeCode = matcher.group(1);
            this.year = Integer.parseInt(matcher.group(2));
            this.sequence = Integer.parseInt(matcher.group(3));
        } else {
            throw new IllegalArgumentException("申請番号の解析に失敗しました");
        }

        // ビジネスルールの検証
        validateBusinessRules();
    }

    /**
     * 申請種別コード、年度、連番を指定してインスタンスを生成します。
     * 
     * @param typeCode 申請種別コード（2文字の英大文字）
     * @param year 年度（4桁）
     * @param sequence 連番（1以上999999以下）
     * @throws IllegalArgumentException パラメータが無効な場合
     */
    public ApplicationNumber(String typeCode, int year, int sequence) {
        if (typeCode == null || typeCode.length() != TYPE_CODE_LENGTH) {
            throw new IllegalArgumentException("申請種別コードは2文字の英大文字である必要があります");
        }

        if (!typeCode.matches("^[A-Z]{2}$")) {
            throw new IllegalArgumentException("申請種別コードは英大文字のみ使用可能です");
        }

        if (year < 1900 || year > 9999) {
            throw new IllegalArgumentException("年度は1900年から9999年の間で指定してください");
        }

        if (sequence < 1 || sequence > 999999) {
            throw new IllegalArgumentException("連番は1から999999の間で指定してください");
        }

        this.typeCode = typeCode;
        this.year = year;
        this.sequence = sequence;
        this.value = String.format("%s-%04d-%06d", typeCode, year, sequence);

        // ビジネスルールの検証
        validateBusinessRules();
    }

    /**
     * 申請番号の形式が正しいかを検証します。
     * 
     * @param value 検証対象の申請番号
     * @return 形式が正しい場合はtrue、そうでなければfalse
     */
    private static boolean isValidFormat(String value) {
        return APPLICATION_NUMBER_PATTERN.matcher(value).matches();
    }

    /**
     * ビジネスルールを検証します。
     * 
     * @throws IllegalArgumentException ビジネスルールに違反している場合
     */
    private void validateBusinessRules() {
        // 年度が未来すぎないかチェック（現在年度+10年まで）
        int currentYear = Year.now().getValue();
        if (year > currentYear + 10) {
            throw new IllegalArgumentException(
                String.format("年度が未来すぎます。現在年度+10年（%d年）以下で指定してください", currentYear + 10));
        }

        // 年度が古すぎないかチェック（システム開始年度以降）
        if (year < 2020) {
            throw new IllegalArgumentException("年度は2020年以降で指定してください");
        }

        // 申請種別コードの妥当性チェック（例：UNは雲龍システム用）
        if (!isValidTypeCode(typeCode)) {
            throw new IllegalArgumentException(
                String.format("申請種別コード '%s' は使用できません", typeCode));
        }
    }

    /**
     * 申請種別コードが有効かを判定します。
     * 
     * @param typeCode 申請種別コード
     * @return 有効な場合はtrue、そうでなければfalse
     */
    private static boolean isValidTypeCode(String typeCode) {
        // 有効な申請種別コードのリスト
        return typeCode.equals("UN") ||  // 雲龍システム
               typeCode.equals("AP") ||  // 申請システム
               typeCode.equals("EV") ||  // 評価システム
               typeCode.equals("RP");    // レポートシステム
    }

    /**
     * 申請番号の値を取得します。
     * 
     * @return 申請番号
     */
    public String getValue() {
        return value;
    }

    /**
     * 申請種別コードを取得します。
     * 
     * @return 申請種別コード
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * 年度を取得します。
     * 
     * @return 年度
     */
    public int getYear() {
        return year;
    }

    /**
     * 連番を取得します。
     * 
     * @return 連番
     */
    public int getSequence() {
        return sequence;
    }

    /**
     * 申請種別名を取得します。
     * 
     * @return 申請種別名
     */
    public String getTypeName() {
        switch (typeCode) {
            case "UN":
                return "雲龍システム";
            case "AP":
                return "申請システム";
            case "EV":
                return "評価システム";
            case "RP":
                return "レポートシステム";
            default:
                return "不明";
        }
    }

    /**
     * 次の連番の申請番号を生成します。
     * 
     * @return 次の連番の申請番号
     * @throws IllegalStateException 連番が上限に達している場合
     */
    public ApplicationNumber nextSequence() {
        if (sequence >= 999999) {
            throw new IllegalStateException("連番が上限に達しています");
        }
        return new ApplicationNumber(typeCode, year, sequence + 1);
    }

    /**
     * 指定された年度の申請番号を生成します。
     * 
     * @param newYear 新しい年度
     * @return 指定年度の申請番号（連番は1）
     */
    public ApplicationNumber withYear(int newYear) {
        return new ApplicationNumber(typeCode, newYear, 1);
    }

    /**
     * 申請番号が指定された年度のものかを判定します。
     * 
     * @param targetYear 対象年度
     * @return 指定年度の申請番号の場合はtrue、そうでなければfalse
     */
    public boolean isYearOf(int targetYear) {
        return this.year == targetYear;
    }

    /**
     * 申請番号が現在年度のものかを判定します。
     * 
     * @return 現在年度の申請番号の場合はtrue、そうでなければfalse
     */
    public boolean isCurrentYear() {
        return isYearOf(Year.now().getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ApplicationNumber that = (ApplicationNumber) obj;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.format("ApplicationNumber{value='%s', typeCode='%s', year=%d, sequence=%d, typeName='%s'}", 
                           value, typeCode, year, sequence, getTypeName());
    }

    /**
     * 申請番号の文字列表現を取得します（値のみ）。
     * 
     * @return 申請番号の値
     */
    public String toDisplayString() {
        return value;
    }
}
