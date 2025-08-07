package jp.co.apsa.giiku.application.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 経費申請詳細を画面表示用に変換したDTO。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Data
@Builder
public class ExpenseRequestDto {
    /** 申請ID */
    private Long id;
    /** 申請者名 */
    private String applicantName;
    /** 発生日 */
    private LocalDate expenseDate;
    /** 金額 */
    private BigDecimal amount;
    /** 内容 */
    private String description;
    /** 状態 */
    private String status;
}
