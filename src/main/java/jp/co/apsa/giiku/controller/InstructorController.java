package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.Instructor;
import jp.co.apsa.giiku.service.InstructorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 講師管理コントローラ
 * LMS講師のREST APIエンドポイントを提供
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api/lms/instructors")
@Validated
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    /** 講師一覧取得 */
    @GetMapping
    public ResponseEntity<Page<Instructor>> getAllInstructors(
            @PageableDefault(size = 20) Pageable pageable) {
        try {
            Page<Instructor> instructors = instructorService.getAllInstructors(pageable);
            return ResponseEntity.ok(instructors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /** 講師詳細取得 */
    @GetMapping("/{id}")
    public ResponseEntity<Instructor> getInstructor(@PathVariable @NotNull @Min(1) Long id) {
        try {
            Instructor instructor = instructorService.getInstructorById(id);
            return ResponseEntity.ok(instructor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /** 講師作成 */
    @PostMapping
    public ResponseEntity<Instructor> createInstructor(@Valid @RequestBody Instructor instructor) {
        try {
            Instructor createdInstructor = instructorService.createInstructor(instructor);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInstructor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /** 講師更新 */
    @PutMapping("/{id}")
    public ResponseEntity<Instructor> updateInstructor(
            @PathVariable @NotNull @Min(1) Long id,
            @Valid @RequestBody Instructor instructor) {
        try {
            instructor.setInstructorId(id);
            Instructor updatedInstructor = instructorService.updateInstructor(instructor);
            return ResponseEntity.ok(updatedInstructor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /** 評価追加 */
    @PostMapping("/{id}/rating")
    public ResponseEntity<Map<String, String>> addRating(
            @PathVariable @NotNull @Min(1) Long id,
            @Valid @RequestBody AddRatingRequest request) {
        try {
            instructorService.addRating(id, request.getRating());
            Map<String, String> response = new HashMap<>();
            response.put("message", "評価が追加されました");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // リクエストクラス
    public static class AddRatingRequest {
        @DecimalMin(value = "1.0", message = "評価は1.0以上である必要があります")
        @DecimalMax(value = "5.0", message = "評価は5.0以下である必要があります")
        private double rating;
        /** getRating メソッド */
        public double getRating() { return rating; }
        /** setRating メソッド */
        public void setRating(double rating) { this.rating = rating; }
    }
}
