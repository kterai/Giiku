package jp.co.apsa.giiku.application.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * 承認履歴を画面表示用に変換したDTO。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Data
@Builder
public class ApplicationApprovalDto {
    /** 承認ステップ */
    private Integer stepOrder;
    /** 承認者名 */
    private String approverName;
    /** ステータス表示名 */
    private String status;
    /** 承認日時 */
    private LocalDateTime approvedAt;
}
