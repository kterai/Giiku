/*
 * Copyright (c) 2024 株式会社アプサ
 * All rights reserved.
 */
package jp.co.apsa.unryu.domain.valueobject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;

/**
 * フォームデータを表すValue Objectクラス
 * 
 * <p>このクラスは、Webフォームから送信されたデータをJSON形式で保持し、
 * 型安全な方法でデータの取得・検証を行います。</p>
 * 
 * <p>主な機能:</p>
 * <ul>
 *   <li>JSON形式でのフォームデータ保存</li>
 *   <li>個別フィールドの取得・検証</li>
 *   <li>データ型の自動変換（String, Integer, Boolean, Date等）</li>
 *   <li>必須フィールドの検証</li>
 *   <li>フォームスキーマとの整合性チェック</li>
 *   <li>セキュリティ対策（XSS、SQLインジェクション対策）</li>
 * </ul>
 * 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
public final class FormData {

    /** JSONデータを解析するためのObjectMapper */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** XSS攻撃を検出するための正規表現パターン */
    private static final Pattern XSS_PATTERN = Pattern.compile(
            ".*(<script[^>]*>.*?</script>|javascript:|on\\w+\\s*=|<iframe|<object|<embed|<link|<meta).*",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    /** SQLインジェクション攻撃を検出するための正規表現パターン */
    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
            ".*(union\\s+select|insert\\s+into|delete\\s+from|update\\s+set|drop\\s+table|exec\\s*\\(|execute\\s*\\().*",
            Pattern.CASE_INSENSITIVE);
    /** 日付フォーマットのパターン */
    private static final DateTimeFormatter[] DATE_FORMATTERS = {
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("yyyy/MM/dd"),
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("MM/dd/yyyy")
    };

    /** 日時フォーマットのパターン */
    private static final DateTimeFormatter[] DATETIME_FORMATTERS = {
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
        DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    };

    /** フォームデータのJSON文字列（不変） */
    private final String jsonData;

    /** パースされたJSONノード（不変） */
    private final JsonNode jsonNode;

    /** フィールド名のセット（不変） */
    private final Set<String> fieldNames;

    /**
     * JSON文字列からFormDataインスタンスを作成します。
     * 
     * @param jsonData JSON形式のフォームデータ
     * @throws IllegalArgumentException JSON文字列が無効な場合
     * @throws NullPointerException jsonDataがnullの場合
     */
    public FormData(String jsonData) {
        Objects.requireNonNull(jsonData, "JSON data cannot be null");

        try {
            this.jsonNode = OBJECT_MAPPER.readTree(jsonData);
            this.jsonData = jsonData;
            this.fieldNames = Collections.unmodifiableSet(
                StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(jsonNode.fieldNames(), Spliterator.ORDERED),
                        false)
                    .collect(Collectors.toSet())
            );
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid JSON format: " + e.getMessage(), e);
        }
    }

    /**
     * MapからFormDataインスタンスを作成します。
     * 
     * @param dataMap フォームデータのMap
     * @throws IllegalArgumentException Mapの変換に失敗した場合
     * @throws NullPointerException dataMapがnullの場合
     */
    public FormData(Map<String, Object> dataMap) {
        Objects.requireNonNull(dataMap, "Data map cannot be null");

        try {
            this.jsonData = OBJECT_MAPPER.writeValueAsString(dataMap);
            this.jsonNode = OBJECT_MAPPER.valueToTree(dataMap);
            this.fieldNames = Collections.unmodifiableSet(new HashSet<>(dataMap.keySet()));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert map to JSON: " + e.getMessage(), e);
        }
    }

    /**
     * 指定されたフィールドが存在するかチェックします。
     * 
     * @param fieldName フィールド名
     * @return フィールドが存在する場合true
     */
    public boolean hasField(String fieldName) {
        return fieldNames.contains(fieldName);
    }

    /**
     * 全てのフィールド名を取得します。
     * 
     * @return フィールド名のセット（不変）
     */
    public Set<String> getFieldNames() {
        return fieldNames;
    }

    /**
     * 指定されたフィールドの値を文字列として取得します。
     * セキュリティチェックを実行します。
     * 
     * @param fieldName フィールド名
     * @return フィールドの値（存在しない場合はnull）
     * @throws SecurityException セキュリティ脅威が検出された場合
     */
    public String getString(String fieldName) {
        JsonNode node = jsonNode.get(fieldName);
        if (node == null || node.isNull()) {
            return null;
        }

        String value = node.asText();
        validateSecurity(value, fieldName);
        return value;
    }

    /**
     * 指定されたフィールドの値を文字列として取得します（デフォルト値付き）。
     * 
     * @param fieldName フィールド名
     * @param defaultValue デフォルト値
     * @return フィールドの値（存在しない場合はデフォルト値）
     */
    public String getString(String fieldName, String defaultValue) {
        String value = getString(fieldName);
        return value != null ? value : defaultValue;
    }

    /**
     * 指定されたフィールドの値を整数として取得します。
     * 
     * @param fieldName フィールド名
     * @return フィールドの値（存在しない場合はnull）
     * @throws NumberFormatException 数値変換に失敗した場合
     */
    public Integer getInteger(String fieldName) {
        JsonNode node = jsonNode.get(fieldName);
        if (node == null || node.isNull()) {
            return null;
        }

        if (node.isInt()) {
            return node.asInt();
        }

        String value = node.asText().trim();
        if (value.isEmpty()) {
            return null;
        }

        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Cannot convert field '" + fieldName + "' to Integer: " + value);
        }
    }

    /**
     * 指定されたフィールドの値を整数として取得します（デフォルト値付き）。
     * 
     * @param fieldName フィールド名
     * @param defaultValue デフォルト値
     * @return フィールドの値（存在しない場合はデフォルト値）
     */
    public Integer getInteger(String fieldName, Integer defaultValue) {
        Integer value = getInteger(fieldName);
        return value != null ? value : defaultValue;
    }

    /**
     * 指定されたフィールドの値をブール値として取得します。
     * 
     * @param fieldName フィールド名
     * @return フィールドの値（存在しない場合はnull）
     */
    public Boolean getBoolean(String fieldName) {
        JsonNode node = jsonNode.get(fieldName);
        if (node == null || node.isNull()) {
            return null;
        }

        if (node.isBoolean()) {
            return node.asBoolean();
        }

        String value = node.asText().trim().toLowerCase();
        if (value.isEmpty()) {
            return null;
        }

        switch (value) {
            case "true":
            case "1":
            case "yes":
            case "on":
                return Boolean.TRUE;
            case "false":
            case "0":
            case "no":
            case "off":
                return Boolean.FALSE;
            default:
                throw new IllegalArgumentException("Cannot convert field '" + fieldName + "' to Boolean: " + value);
        }
    }

    /**
     * 指定されたフィールドの値をブール値として取得します（デフォルト値付き）。
     * 
     * @param fieldName フィールド名
     * @param defaultValue デフォルト値
     * @return フィールドの値（存在しない場合はデフォルト値）
     */
    public Boolean getBoolean(String fieldName, Boolean defaultValue) {
        Boolean value = getBoolean(fieldName);
        return value != null ? value : defaultValue;
    }

    /**
     * 指定されたフィールドの値を日付として取得します。
     * 
     * @param fieldName フィールド名
     * @return フィールドの値（存在しない場合はnull）
     * @throws DateTimeParseException 日付変換に失敗した場合
     */
    public LocalDate getDate(String fieldName) {
        String value = getString(fieldName);
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        value = value.trim();
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(value, formatter);
            } catch (DateTimeParseException ignored) {
                // 次のフォーマットを試す
            }
        }

        throw new DateTimeParseException("Cannot parse date field '" + fieldName + "': " + value, value, 0);
    }

    /**
     * 指定されたフィールドの値を日時として取得します。
     * 
     * @param fieldName フィールド名
     * @return フィールドの値（存在しない場合はnull）
     * @throws DateTimeParseException 日時変換に失敗した場合
     */
    public LocalDateTime getDateTime(String fieldName) {
        String value = getString(fieldName);
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        value = value.trim();
        for (DateTimeFormatter formatter : DATETIME_FORMATTERS) {
            try {
                return LocalDateTime.parse(value, formatter);
            } catch (DateTimeParseException ignored) {
                // 次のフォーマットを試す
            }
        }

        throw new DateTimeParseException("Cannot parse datetime field '" + fieldName + "': " + value, value, 0);
    }

    /**
     * 必須フィールドの検証を行います。
     * 
     * @param requiredFields 必須フィールド名のリスト
     * @throws IllegalArgumentException 必須フィールドが不足している場合
     */
    public void validateRequiredFields(List<String> requiredFields) {
        Objects.requireNonNull(requiredFields, "Required fields list cannot be null");

        List<String> missingFields = requiredFields.stream()
            .filter(field -> !hasField(field) || getString(field) == null || getString(field).trim().isEmpty())
            .collect(Collectors.toList());

        if (!missingFields.isEmpty()) {
            throw new IllegalArgumentException("Missing required fields: " + String.join(", ", missingFields));
        }
    }

    /**
     * フォームスキーマとの整合性をチェックします。
     * 
     * @param allowedFields 許可されたフィールド名のセット
     * @throws IllegalArgumentException 許可されていないフィールドが含まれている場合
     */
    public void validateSchema(Set<String> allowedFields) {
        Objects.requireNonNull(allowedFields, "Allowed fields set cannot be null");

        Set<String> invalidFields = fieldNames.stream()
            .filter(field -> !allowedFields.contains(field))
            .collect(Collectors.toSet());

        if (!invalidFields.isEmpty()) {
            throw new IllegalArgumentException("Invalid fields found: " + String.join(", ", invalidFields));
        }
    }

    /**
     * セキュリティ脅威の検証を行います。
     * 
     * @param value 検証する値
     * @param fieldName フィールド名（エラーメッセージ用）
     * @throws SecurityException セキュリティ脅威が検出された場合
     */
    private void validateSecurity(String value, String fieldName) {
        if (value == null) {
            return;
        }

        if (XSS_PATTERN.matcher(value).matches()) {
            throw new SecurityException("XSS attack detected in field '" + fieldName + "'");
        }

        if (SQL_INJECTION_PATTERN.matcher(value).matches()) {
            throw new SecurityException("SQL injection attack detected in field '" + fieldName + "'");
        }
    }

    /**
     * 元のJSON文字列を取得します。
     * 
     * @return JSON文字列
     */
    public String getJsonData() {
        return jsonData;
    }

    /**
     * フォームデータをMapとして取得します。
     * 
     * @return フォームデータのMap（不変）
     */
    public Map<String, Object> toMap() {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = OBJECT_MAPPER.readValue(jsonData, Map.class);
            return Collections.unmodifiableMap(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to Map", e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FormData formData = (FormData) obj;
        return Objects.equals(jsonData, formData.jsonData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jsonData);
    }

    @Override
    public String toString() {
        return "FormData{" +
                "fieldCount=" + fieldNames.size() +
                ", fields=" + fieldNames +
                '}';
    }
}
