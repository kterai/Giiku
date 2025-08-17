package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.CompanyLmsConfig;
import jp.co.apsa.giiku.service.CompanyLmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 企業LMS設定管理コントローラー。
 * 企業ごとのLMS設定に関するREST APIを提供する。
 *
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api/lms/company-configs")
@Validated
public class CompanyLmsConfigController {

    @Autowired
    private CompanyLmsConfigService companyLmsConfigService;

    /**
     * 企業LMS設定一覧取得。
     *
     * @param pageable ページング情報
     * @return 設定一覧
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping
    public ResponseEntity<Page<CompanyLmsConfig>> getAllCompanyLmsConfigs(
            @PageableDefault(size = 20) Pageable pageable) {
        try {
            Page<CompanyLmsConfig> configs = companyLmsConfigService.getAllCompanyLmsConfigs(pageable);
            return ResponseEntity.ok(configs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 企業LMS設定詳細取得。
     *
     * @param id 設定ID
     * @return 企業LMS設定
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/{id}")
    public ResponseEntity<CompanyLmsConfig> getCompanyLmsConfig(@PathVariable @NotNull @Min(1) Long id) {
        try {
            CompanyLmsConfig config = companyLmsConfigService.getCompanyLmsConfigById(id);
            return ResponseEntity.ok(config);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 企業IDで設定取得。
     *
     * @param companyId 企業ID
     * @return 企業LMS設定
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/company/{companyId}")
    public ResponseEntity<CompanyLmsConfig> getCompanyLmsConfigByCompanyId(
            @PathVariable @NotNull @Min(1) Long companyId) {
        try {
            Optional<CompanyLmsConfig> config = companyLmsConfigService.getCompanyLmsConfigByCompanyId(companyId);
            return config.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 企業LMS設定作成。
     *
     * @param config 設定エンティティ
     * @return 作成された設定
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @PostMapping
    public ResponseEntity<CompanyLmsConfig> createCompanyLmsConfig(@Valid @RequestBody CompanyLmsConfig config) {
        try {
            CompanyLmsConfig createdConfig = companyLmsConfigService.createCompanyLmsConfig(config);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdConfig);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 企業LMS設定更新。
     *
     * @param id 設定ID
     * @param config 更新する設定
     * @return 更新された設定
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @PutMapping("/{id}")
    public ResponseEntity<CompanyLmsConfig> updateCompanyLmsConfig(
            @PathVariable @NotNull @Min(1) Long id,
            @Valid @RequestBody CompanyLmsConfig config) {
        try {
            config.setId(id);
            CompanyLmsConfig updatedConfig = companyLmsConfigService.updateCompanyLmsConfig(config);
            return ResponseEntity.ok(updatedConfig);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * LMS機能有効化/無効化。
     *
     * @param id 設定ID
     * @param request 有効フラグ
     * @return メッセージ
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @PutMapping("/{id}/lms-enabled")
    public ResponseEntity<Map<String, String>> updateLmsEnabled(
            @PathVariable @NotNull @Min(1) Long id,
            @Valid @RequestBody UpdateEnabledRequest request) {
        try {
            companyLmsConfigService.updateLmsEnabled(id, request.isEnabled());
            Map<String, String> response = new HashMap<>();
            response.put("message", "LMS機能設定が更新されました");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 企業のLMS機能アクティブ状態確認。
     *
     * @param companyId 企業ID
     * @return アクティブ状態
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @GetMapping("/company/{companyId}/active-status")
    public ResponseEntity<Map<String, Boolean>> isLmsActiveForCompany(
            @PathVariable @NotNull @Min(1) Long companyId) {
        try {
            boolean isActive = companyLmsConfigService.isLmsActiveForCompany(companyId);
            Map<String, Boolean> response = new HashMap<>();
            response.put("isActive", isActive);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // リクエストクラス
    public static class UpdateEnabledRequest {
        private boolean enabled;
        /**
         * isEnabled メソッド
         * @author 株式会社アプサ
         * @version 1.0
         * @since 2025
         */

        public boolean isEnabled() { return enabled; }
        /**
         * setEnabled メソッド
         * @author 株式会社アプサ
         * @version 1.0
         * @since 2025
         */
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
    }
}

