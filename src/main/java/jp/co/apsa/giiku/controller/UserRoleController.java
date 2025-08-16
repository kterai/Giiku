package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.UserRole;
import jp.co.apsa.giiku.service.UserRoleService;
import jp.co.apsa.giiku.dto.UserRoleCreateDto;
import jp.co.apsa.giiku.dto.UserRoleUpdateDto;
import jp.co.apsa.giiku.dto.UserRoleResponseDto;
import jp.co.apsa.giiku.dto.UserRoleSearchDto;
import jp.co.apsa.giiku.dto.UserRoleStatsDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * ユーザー役割管理コントローラー
 * ユーザーの役割・権限管理のCRUD操作、検索、統計機能を提供
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserRoleController {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);

    @Autowired
    private UserRoleService userRoleService;

    /**
     * ユーザー役割一覧取得
     * GET /api/user-roles
     */
    @GetMapping("/user-roles")
    public ResponseEntity<?> getAllUserRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        try {
            logger.info("ユーザー役割一覧取得開始 - page: {}, size: {}, sortBy: {}, sortDir: {}", 
                       page, size, sortBy, sortDir);

            Page<UserRoleResponseDto> userRoles = userRoleService.getAllUserRoles(
                page, size, sortBy, sortDir);

            logger.info("ユーザー役割一覧取得成功 - 総件数: {}", userRoles.getTotalElements());
            return ResponseEntity.ok(userRoles);

        } catch (Exception e) {
            logger.error("ユーザー役割一覧取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ユーザー役割一覧の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * ユーザー役割詳細取得
     * GET /api/user-roles/{id}
     */
    @GetMapping("/user-roles/{id}")
    public ResponseEntity<?> getUserRoleById(@PathVariable Long id) {
        try {
            logger.info("ユーザー役割詳細取得開始 - ID: {}", id);

            Optional<UserRoleResponseDto> userRole = userRoleService.getUserRoleById(id);

            if (userRole.isPresent()) {
                logger.info("ユーザー役割詳細取得成功 - ID: {}", id);
                return ResponseEntity.ok(userRole.get());
            } else {
                logger.warn("ユーザー役割が見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("ユーザー役割詳細取得エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ユーザー役割詳細の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * ユーザー別役割取得
     * GET /api/users/{userId}/roles
     */
    @GetMapping("/users/{userId}/roles")
    public ResponseEntity<?> getRolesByUserId(@PathVariable Long userId) {
        try {
            logger.info("ユーザー別役割取得開始 - ユーザーID: {}", userId);

            List<UserRoleResponseDto> userRoles = userRoleService.getRolesByUserId(userId);

            logger.info("ユーザー別役割取得成功 - ユーザーID: {}, 役割数: {}", 
                       userId, userRoles.size());
            return ResponseEntity.ok(userRoles);

        } catch (Exception e) {
            logger.error("ユーザー別役割取得エラー - ユーザーID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ユーザー別役割の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 役割別ユーザー取得
     * GET /api/roles/{roleName}/users
     */
    @GetMapping("/roles/{roleName}/users")
    public ResponseEntity<?> getUsersByRoleName(
            @PathVariable String roleName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            logger.info("役割別ユーザー取得開始 - 役割名: {}", roleName);

            Page<UserRoleResponseDto> userRoles = userRoleService.getUsersByRoleName(
                roleName, page, size);

            logger.info("役割別ユーザー取得成功 - 役割名: {}, ユーザー数: {}", 
                       roleName, userRoles.getTotalElements());
            return ResponseEntity.ok(userRoles);

        } catch (Exception e) {
            logger.error("役割別ユーザー取得エラー - 役割名: {}", roleName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("役割別ユーザーの取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * ユーザー役割作成
     * POST /api/user-roles
     */
    @PostMapping("/user-roles")
    public ResponseEntity<?> createUserRole(@Valid @RequestBody UserRoleCreateDto createDto) {
        try {
            logger.info("ユーザー役割作成開始 - ユーザーID: {}, 役割名: {}", 
                       createDto.getUserId(), createDto.getRoleName());

            UserRoleResponseDto createdUserRole = userRoleService.createUserRole(createDto);

            logger.info("ユーザー役割作成成功 - ID: {}, ユーザーID: {}, 役割名: {}", 
                       createdUserRole.getId(), createdUserRole.getUserId(), 
                       createdUserRole.getRoleName());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserRole);

        } catch (IllegalArgumentException e) {
            logger.warn("ユーザー役割作成バリデーションエラー: {}", e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("ユーザー役割作成エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ユーザー役割の作成に失敗しました: " + e.getMessage());
        }
    }

    /**
     * ユーザー役割更新
     * PUT /api/user-roles/{id}
     */
    @PutMapping("/user-roles/{id}")
    public ResponseEntity<?> updateUserRole(
            @PathVariable Long id, 
            @Valid @RequestBody UserRoleUpdateDto updateDto) {

        try {
            logger.info("ユーザー役割更新開始 - ID: {}", id);

            Optional<UserRoleResponseDto> updatedUserRole = 
                userRoleService.updateUserRole(id, updateDto);

            if (updatedUserRole.isPresent()) {
                logger.info("ユーザー役割更新成功 - ID: {}", id);
                return ResponseEntity.ok(updatedUserRole.get());
            } else {
                logger.warn("更新対象ユーザー役割が見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (IllegalArgumentException e) {
            logger.warn("ユーザー役割更新バリデーションエラー - ID: {}, エラー: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("ユーザー役割更新エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ユーザー役割の更新に失敗しました: " + e.getMessage());
        }
    }

    /**
     * ユーザー役割削除
     * DELETE /api/user-roles/{id}
     */
    @DeleteMapping("/user-roles/{id}")
    public ResponseEntity<?> deleteUserRole(@PathVariable Long id) {
        try {
            logger.info("ユーザー役割削除開始 - ID: {}", id);

            boolean deleted = userRoleService.deleteUserRole(id);

            if (deleted) {
                logger.info("ユーザー役割削除成功 - ID: {}", id);
                return ResponseEntity.noContent().build();
            } else {
                logger.warn("削除対象ユーザー役割が見つかりません - ID: {}", id);
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("ユーザー役割削除エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ユーザー役割の削除に失敗しました: " + e.getMessage());
        }
    }

    /**
     * ユーザーに役割を一括割り当て
     * POST /api/users/{userId}/roles/batch-assign
     */
    @PostMapping("/users/{userId}/roles/batch-assign")
    public ResponseEntity<?> batchAssignRoles(
            @PathVariable Long userId,
            @RequestBody List<String> roleNames) {

        try {
            logger.info("ユーザー役割一括割り当て開始 - ユーザーID: {}, 役割数: {}", 
                       userId, roleNames.size());

            List<UserRoleResponseDto> assignedRoles = 
                userRoleService.batchAssignRoles(userId, roleNames);

            logger.info("ユーザー役割一括割り当て成功 - ユーザーID: {}, 割り当て済み役割数: {}", 
                       userId, assignedRoles.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(assignedRoles);

        } catch (IllegalArgumentException e) {
            logger.warn("ユーザー役割一括割り当てバリデーションエラー - ユーザーID: {}, エラー: {}", 
                       userId, e.getMessage());
            return ResponseEntity.badRequest().body("入力データエラー: " + e.getMessage());

        } catch (Exception e) {
            logger.error("ユーザー役割一括割り当てエラー - ユーザーID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ユーザー役割の一括割り当てに失敗しました: " + e.getMessage());
        }
    }

    /**
     * ユーザーから役割を一括削除
     * DELETE /api/users/{userId}/roles/batch-remove
     */
    @DeleteMapping("/users/{userId}/roles/batch-remove")
    public ResponseEntity<?> batchRemoveRoles(
            @PathVariable Long userId,
            @RequestBody List<String> roleNames) {

        try {
            logger.info("ユーザー役割一括削除開始 - ユーザーID: {}, 役割数: {}", 
                       userId, roleNames.size());

            boolean removed = userRoleService.batchRemoveRoles(userId, roleNames);

            if (removed) {
                logger.info("ユーザー役割一括削除成功 - ユーザーID: {}", userId);
                return ResponseEntity.noContent().build();
            } else {
                logger.warn("削除対象ユーザー役割が見つかりません - ユーザーID: {}", userId);
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            logger.error("ユーザー役割一括削除エラー - ユーザーID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ユーザー役割の一括削除に失敗しました: " + e.getMessage());
        }
    }

    /**
     * ユーザー役割検索
     * GET /api/user-roles/search
     */
    @GetMapping("/user-roles/search")
    public ResponseEntity<?> searchUserRoles(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String assignedDateFrom,
            @RequestParam(required = false) String assignedDateTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        try {
            logger.info("ユーザー役割検索開始 - ユーザーID: {}, 役割名: {}, ステータス: {}", 
                       userId, roleName, status);

            UserRoleSearchDto searchDto = new UserRoleSearchDto();
            searchDto.setUserId(userId);
            searchDto.setRoleName(roleName);
            searchDto.setStatus(status);
            searchDto.setAssignedDateFrom(assignedDateFrom);
            searchDto.setAssignedDateTo(assignedDateTo);

            Page<UserRoleResponseDto> searchResult = 
                userRoleService.searchUserRoles(searchDto, page, size, sortBy, sortDir);

            logger.info("ユーザー役割検索成功 - 検索結果件数: {}", searchResult.getTotalElements());
            return ResponseEntity.ok(searchResult);

        } catch (Exception e) {
            logger.error("ユーザー役割検索エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ユーザー役割検索に失敗しました: " + e.getMessage());
        }
    }

    /**
     * ユーザー役割統計情報取得
     * GET /api/user-roles/stats
     */
    @GetMapping("/user-roles/stats")
    public ResponseEntity<?> getUserRoleStats(
            @RequestParam(required = false) String period,
            @RequestParam(required = false) String roleName) {

        try {
            logger.info("ユーザー役割統計情報取得開始 - 期間: {}, 役割名: {}", period, roleName);

            UserRoleStatsDto stats = userRoleService.getUserRoleStats(period, roleName);

            logger.info("ユーザー役割統計情報取得成功 - 総役割数: {}, アクティブユーザー数: {}", 
                       stats.getTotalRoles(), stats.getActiveUsers());
            return ResponseEntity.ok(stats);

        } catch (Exception e) {
            logger.error("ユーザー役割統計情報取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ユーザー役割統計情報の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 利用可能な役割一覧取得
     * GET /api/roles/available
     */
    @GetMapping("/roles/available")
    public ResponseEntity<?> getAvailableRoles() {
        try {
            logger.info("利用可能な役割一覧取得開始");

            List<String> availableRoles = userRoleService.getAvailableRoles();

            logger.info("利用可能な役割一覧取得成功 - 役割数: {}", availableRoles.size());
            return ResponseEntity.ok(availableRoles);

        } catch (Exception e) {
            logger.error("利用可能な役割一覧取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("利用可能な役割一覧の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * ユーザーの権限チェック
     * GET /api/users/{userId}/permissions/check
     */
    @GetMapping("/users/{userId}/permissions/check")
    public ResponseEntity<?> checkUserPermissions(
            @PathVariable Long userId,
            @RequestParam String permission) {

        try {
            logger.info("ユーザー権限チェック開始 - ユーザーID: {}, 権限: {}", userId, permission);

            boolean hasPermission = userRoleService.checkUserPermission(userId, permission);

            logger.info("ユーザー権限チェック完了 - ユーザーID: {}, 権限: {}, 結果: {}", 
                       userId, permission, hasPermission);
            return ResponseEntity.ok(new PermissionCheckResponse(hasPermission));

        } catch (Exception e) {
            logger.error("ユーザー権限チェックエラー - ユーザーID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ユーザー権限チェックに失敗しました: " + e.getMessage());
        }
    }

    /**
     * 役割の階層情報取得
     * GET /api/roles/{roleName}/hierarchy
     */
    @GetMapping("/roles/{roleName}/hierarchy")
    public ResponseEntity<?> getRoleHierarchy(@PathVariable String roleName) {
        try {
            logger.info("役割階層情報取得開始 - 役割名: {}", roleName);

            List<String> hierarchy = userRoleService.getRoleHierarchy(roleName);

            logger.info("役割階層情報取得成功 - 役割名: {}, 階層レベル数: {}", 
                       roleName, hierarchy.size());
            return ResponseEntity.ok(hierarchy);

        } catch (Exception e) {
            logger.error("役割階層情報取得エラー - 役割名: {}", roleName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("役割階層情報の取得に失敗しました: " + e.getMessage());
        }
    }

    /**
     * 権限チェック結果レスポンス用クラス
     */
    public static class PermissionCheckResponse {
        private boolean hasPermission;

        public PermissionCheckResponse(boolean hasPermission) {
            this.hasPermission = hasPermission;
        }

        public boolean isHasPermission() {
            return hasPermission;
        }

        public void setHasPermission(boolean hasPermission) {
            this.hasPermission = hasPermission;
        }
    }
}