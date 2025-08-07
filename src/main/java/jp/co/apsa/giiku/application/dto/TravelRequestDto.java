package jp.co.apsa.giiku.application.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 出張申請詳細を画面表示用に変換したDTO。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Data
@Builder
public class TravelRequestDto {
    /** 申請ID */
    private Long id;
    /** 申請者名 */
    private String applicantName;
    /** 出張先 */
    private String destination;
    /** 出発日 */
    private LocalDate startDate;
    /** 帰着日 */
    private LocalDate endDate;
    /** 交通手段 */
    private String transport;
    /** 目的 */
    private String purpose;
    /** 概算費用 */
    private BigDecimal estimatedCost;
    /** 状態 */
    private String status;
}
