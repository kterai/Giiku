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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 企業LMS設定管理コントローラ
 * LMS企業設定のREST APIエンドポイントを提供
 * 
 * @author Giiku LMS Team
 * @version 1.0
 * @since 2024-01
 */
@RestController
@RequestMapping("/api/lms/company-configs")
@Validated
public class CompanyLmsConfigController {

    @Autowired
    private CompanyLmsConfigService companyLmsConfigService;

    /**
     * 企業LMS設定一覧取得
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
     * 企業LMS設定詳細取得
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
     * 企業IDで設定取得
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
     * 企業LMS設定作成
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
     * 企業LMS設定更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<CompanyLmsConfig> updateCompanyLmsConfig(
            @PathVariable @NotNull @Min(1) Long id,
            @Valid @RequestBody CompanyLmsConfig config) {
        try {
            config.setCompanyLmsConfigId(id);
            CompanyLmsConfig updatedConfig = companyLmsConfigService.updateCompanyLmsConfig(config);
            return ResponseEntity.ok(updatedConfig);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * LMS機能有効化/無効化
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
     * 企業のLMS機能アクティブ状態確認
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

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
    }
}