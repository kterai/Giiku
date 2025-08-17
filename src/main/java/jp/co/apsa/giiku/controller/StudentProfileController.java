package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.StudentProfile;
import jp.co.apsa.giiku.service.StudentProfileService;

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
 * 学生プロファイル管理コントローラ
 * LMS学生プロファイルのREST APIエンドポイントを提供
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api/lms/student-profiles")
@Validated
public class StudentProfileController {

    @Autowired
    private StudentProfileService studentProfileService;

    /**
     * 学生プロファイル一覧取得
     *
     * @param pageable ページング情報
     * @return 学生プロファイルのページ
     */
    @GetMapping
    public ResponseEntity<Page<StudentProfile>> getAllStudentProfiles(
            @PageableDefault(size = 20) Pageable pageable) {

        try {
            Page<StudentProfile> profiles = studentProfileService.getAllStudentProfiles(pageable);
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生プロファイル詳細取得
     *
     * @param id 学生プロファイルID
     * @return 学生プロファイル
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentProfile> getStudentProfile(
            @PathVariable @NotNull @Min(1) Long id) {

        try {
            StudentProfile profile = studentProfileService.getStudentProfileById(id);
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * ユーザーIDで学生プロファイル取得
     *
     * @param userId ユーザーID
     * @return 学生プロファイル
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<StudentProfile> getStudentProfileByUserId(
            @PathVariable @NotNull @Min(1) Long userId) {

        try {
            Optional<StudentProfile> profile = studentProfileService.getStudentProfileByUserId(userId);
            return profile.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生番号で学生プロファイル取得
     *
     * @param studentNumber 学生番号
     * @return 学生プロファイル
     */
    @GetMapping("/student-number/{studentNumber}")
    public ResponseEntity<StudentProfile> getStudentProfileByStudentNumber(
            @PathVariable @NotNull String studentNumber) {

        try {
            Optional<StudentProfile> profile = studentProfileService.getStudentProfileByStudentNumber(studentNumber);
            return profile.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 部署別学生プロファイル取得
     *
     * @param departmentId 部署ID
     * @param pageable ページング情報
     * @return 学生プロファイルのページ
     */
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<Page<StudentProfile>> getStudentProfilesByDepartment(
            @PathVariable @NotNull @Min(1) Long departmentId,
            @PageableDefault(size = 20) Pageable pageable) {

        try {
            Page<StudentProfile> profiles = studentProfileService.getStudentProfilesByDepartmentId(departmentId, pageable);
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学習ステータス別学生プロファイル取得
     *
     * @param status 学習ステータス
     * @param pageable ページング情報
     * @return 学生プロファイルのページ
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<StudentProfile>> getStudentProfilesByStatus(
            @PathVariable @NotNull String status,
            @PageableDefault(size = 20) Pageable pageable) {

        try {
            Page<StudentProfile> profiles = studentProfileService.getStudentProfilesByLearningStatus(status, pageable);
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学習レベル別学生プロファイル取得
     *
     * @param level 学習レベル
     * @param pageable ページング情報
     * @return 学生プロファイルのページ
     */
    @GetMapping("/level/{level}")
    public ResponseEntity<Page<StudentProfile>> getStudentProfilesByLevel(
            @PathVariable @NotNull @Min(1) Integer level,
            @PageableDefault(size = 20) Pageable pageable) {

        try {
            Page<StudentProfile> profiles = studentProfileService.getStudentProfilesByLearningLevel(level, pageable);
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生プロファイル作成
     *
     * @param profile 作成する学生プロファイル
     * @return 作成された学生プロファイル
     */
    @PostMapping
    public ResponseEntity<StudentProfile> createStudentProfile(
            @Valid @RequestBody StudentProfile profile) {

        try {
            StudentProfile createdProfile = studentProfileService.createStudentProfile(profile);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生プロファイル更新
     *
     * @param id 学生プロファイルID
     * @param profile 更新する学生プロファイル
     * @return 更新された学生プロファイル
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentProfile> updateStudentProfile(
            @PathVariable @NotNull @Min(1) Long id,
            @Valid @RequestBody StudentProfile profile) {

        try {
            profile.setStudentProfileId(id);
            StudentProfile updatedProfile = studentProfileService.updateStudentProfile(profile);
            return ResponseEntity.ok(updatedProfile);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学習時間追加
     *
     * @param id 学生プロファイルID
     * @param request 学習時間追加リクエスト
     * @return 成功レスポンス
     */
    @PostMapping("/{id}/learning-time")
    public ResponseEntity<Map<String, String>> addLearningTime(
            @PathVariable @NotNull @Min(1) Long id,
            @Valid @RequestBody AddLearningTimeRequest request) {

        try {
            studentProfileService.addLearningTime(id, request.getMinutes());
            Map<String, String> response = new HashMap<>();
            response.put("message", "学習時間が追加されました");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * コース完了数増加
     *
     * @param id 学生プロファイルID
     * @return 成功レスポンス
     */
    @PostMapping("/{id}/complete-course")
    public ResponseEntity<Map<String, String>> completeCourse(
            @PathVariable @NotNull @Min(1) Long id) {

        try {
            studentProfileService.incrementCompletedCourses(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "コース完了数が更新されました");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 現在受講中コース数増加
     *
     * @param id 学生プロファイルID
     * @return 成功レスポンス
     */
    @PostMapping("/{id}/enroll-course")
    public ResponseEntity<Map<String, String>> enrollCourse(
            @PathVariable @NotNull @Min(1) Long id) {

        try {
            studentProfileService.incrementCurrentCourses(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "受講中コース数が更新されました");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * スコア更新
     *
     * @param id 学生プロファイルID
     * @param request スコア更新リクエスト
     * @return 成功レスポンス
     */
    @PostMapping("/{id}/update-score")
    public ResponseEntity<Map<String, String>> updateScore(
            @PathVariable @NotNull @Min(1) Long id,
            @Valid @RequestBody UpdateScoreRequest request) {

        try {
            studentProfileService.updateAverageScore(id, request.getNewScore(), request.getTotalTests());
            Map<String, String> response = new HashMap<>();
            response.put("message", "スコアが更新されました");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学習ステータス更新
     *
     * @param id 学生プロファイルID
     * @param request ステータス更新リクエスト
     * @return 成功レスポンス
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, String>> updateLearningStatus(
            @PathVariable @NotNull @Min(1) Long id,
            @Valid @RequestBody UpdateStatusRequest request) {

        try {
            studentProfileService.updateLearningStatus(id, request.getLearningStatus());
            Map<String, String> response = new HashMap<>();
            response.put("message", "学習ステータスが更新されました");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生プロファイル削除
     *
     * @param id 学生プロファイルID
     * @return 成功レスポンス
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStudentProfile(
            @PathVariable @NotNull @Min(1) Long id) {

        try {
            studentProfileService.deleteStudentProfile(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "学生プロファイルが削除されました");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * アクティブな学生数取得
     *
     * @return アクティブな学生数
     */
    @GetMapping("/stats/active-count")
    public ResponseEntity<Map<String, Long>> getActiveStudentCount() {
        try {
            long count = studentProfileService.getActiveStudentCount();
            Map<String, Long> response = new HashMap<>();
            response.put("activeStudentCount", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 部署別アクティブな学生数取得
     *
     * @param departmentId 部署ID
     * @return アクティブな学生数
     */
    @GetMapping("/stats/active-count/department/{departmentId}")
    public ResponseEntity<Map<String, Long>> getActiveStudentCountByDepartment(
            @PathVariable @NotNull @Min(1) Long departmentId) {
        try {
            long count = studentProfileService.getActiveStudentCountByDepartment(departmentId);
            Map<String, Long> response = new HashMap<>();
            response.put("activeStudentCount", count);
            response.put("departmentId", departmentId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // リクエストクラス
    public static class AddLearningTimeRequest {
        @Min(value = 1, message = "学習時間は1分以上である必要があります")
        private int minutes;
        /** getMinutes メソッド */
        public int getMinutes() { return minutes; }
        /** setMinutes メソッド */
        public void setMinutes(int minutes) { this.minutes = minutes; }
    }

    public static class UpdateScoreRequest {
        @Min(value = 0, message = "スコアは0以上である必要があります")
        private double newScore;

        @Min(value = 1, message = "総テスト数は1以上である必要があります")
        private int totalTests;
        /** getNewScore メソッド */
        public double getNewScore() { return newScore; }
        /** setNewScore メソッド */
        public void setNewScore(double newScore) { this.newScore = newScore; }
        /** getTotalTests メソッド */
        public int getTotalTests() { return totalTests; }
        /** setTotalTests メソッド */
        public void setTotalTests(int totalTests) { this.totalTests = totalTests; }
    }

    public static class UpdateStatusRequest {
        @NotNull(message = "学習ステータスは必須です")
        private String learningStatus;
        /** getLearningStatus メソッド */
        public String getLearningStatus() { return learningStatus; }
        /** setLearningStatus メソッド */
        public void setLearningStatus(String learningStatus) { this.learningStatus = learningStatus; }
    }
}
