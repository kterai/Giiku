package jp.co.apsa.unryu.application.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * ダッシュボード用チャートデータを保持するDTO。
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Data
@Builder
public class ChartDto {
    /** ラベル一覧 */
    private List<String> labels;
    /** データポイント */
    private List<Long> data;
}
