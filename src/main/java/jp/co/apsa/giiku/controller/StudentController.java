package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.service.StudentService;
import jp.co.apsa.giiku.dto.Student;
import jp.co.apsa.giiku.dto.StudentRequest;
import jp.co.apsa.giiku.dto.StudentResponse;
import jp.co.apsa.giiku.dto.StudentStatistics;
import jp.co.apsa.giiku.exception.StudentNotFoundException;
import jp.co.apsa.giiku.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 学生管理コントローラー
 * 学生の登録、更新、削除、検索機能を提供
 */
@RestController
@RequestMapping("/api/students")
@Validated
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    /**
     * 全学生一覧取得
     */
    @GetMapping
    public ResponseEntity<Page<StudentResponse>> getAllStudents(Pageable pageable) {
        logger.info("全学生一覧取得要求 - Page: {}, Size: {}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<StudentResponse> students = studentService.getAllStudents(pageable);
            logger.info("学生一覧取得成功 - 取得件数: {}", students.getTotalElements());
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            logger.error("学生一覧取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生ID指定取得
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id) {
        logger.info("学生ID指定取得要求 - ID: {}", id);
        try {
            StudentResponse student = studentService.getStudentById(id);
            logger.info("学生取得成功 - ID: {}, 氏名: {}", id, student.getName());
            return ResponseEntity.ok(student);
        } catch (StudentNotFoundException e) {
            logger.warn("学生が見つかりません - ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("学生取得エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生新規登録
     */
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        logger.info("学生新規登録要求 - 氏名: {}, メール: {}", request.getName(), request.getEmail());
        try {
            StudentResponse student = studentService.createStudent(request);
            logger.info("学生登録成功 - ID: {}, 氏名: {}", student.getId(), student.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(student);
        } catch (ValidationException e) {
            logger.warn("学生登録バリデーションエラー: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("学生登録エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生情報更新
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        logger.info("学生情報更新要求 - ID: {}, 氏名: {}", id, request.getName());
        try {
            StudentResponse student = studentService.updateStudent(id, request);
            logger.info("学生更新成功 - ID: {}, 氏名: {}", id, student.getName());
            return ResponseEntity.ok(student);
        } catch (StudentNotFoundException e) {
            logger.warn("更新対象の学生が見つかりません - ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (ValidationException e) {
            logger.warn("学生更新バリデーションエラー - ID: {}, エラー: {}", id, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("学生更新エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生削除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        logger.info("学生削除要求 - ID: {}", id);
        try {
            studentService.deleteStudent(id);
            logger.info("学生削除成功 - ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (StudentNotFoundException e) {
            logger.warn("削除対象の学生が見つかりません - ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("学生削除エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生検索（名前・メール）
     */
    @GetMapping("/search")
    public ResponseEntity<Page<StudentResponse>> searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String department,
            Pageable pageable) {
        logger.info("学生検索要求 - 氏名: {}, メール: {}, 部署: {}", name, email, department);
        try {
            Page<StudentResponse> students = studentService.searchStudents(name, email, department, pageable);
            logger.info("学生検索成功 - 検索結果件数: {}", students.getTotalElements());
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            logger.error("学生検索エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生フィルタリング（ステータス・登録日範囲）
     */
    @GetMapping("/filter")
    public ResponseEntity<Page<StudentResponse>> filterStudents(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Boolean active,
            Pageable pageable) {
        logger.info("学生フィルタリング要求 - ステータス: {}, 開始日: {}, 終了日: {}, アクティブ: {}", 
                    status, startDate, endDate, active);
        try {
            Page<StudentResponse> students = studentService.filterStudents(status, startDate, endDate, active, pageable);
            logger.info("学生フィルタリング成功 - フィルタ結果件数: {}", students.getTotalElements());
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            logger.error("学生フィルタリングエラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生進捗情報取得
     */
    @GetMapping("/{id}/progress")
    public ResponseEntity<Map<String, Object>> getStudentProgress(@PathVariable Long id) {
        logger.info("学生進捗情報取得要求 - ID: {}", id);
        try {
            Map<String, Object> progress = studentService.getStudentProgress(id);
            logger.info("学生進捗取得成功 - ID: {}", id);
            return ResponseEntity.ok(progress);
        } catch (StudentNotFoundException e) {
            logger.warn("進捗取得対象の学生が見つかりません - ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("学生進捗取得エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生進捗更新
     */
    @PutMapping("/{id}/progress")
    public ResponseEntity<Map<String, Object>> updateStudentProgress(
            @PathVariable Long id, 
            @RequestBody Map<String, Object> progressData) {
        logger.info("学生進捗更新要求 - ID: {}", id);
        try {
            Map<String, Object> updatedProgress = studentService.updateStudentProgress(id, progressData);
            logger.info("学生進捗更新成功 - ID: {}", id);
            return ResponseEntity.ok(updatedProgress);
        } catch (StudentNotFoundException e) {
            logger.warn("進捗更新対象の学生が見つかりません - ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (ValidationException e) {
            logger.warn("進捗更新バリデーションエラー - ID: {}, エラー: {}", id, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("学生進捗更新エラー - ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生統計情報取得
     */
    @GetMapping("/statistics")
    public ResponseEntity<StudentStatistics> getStudentStatistics() {
        logger.info("学生統計情報取得要求");
        try {
            StudentStatistics statistics = studentService.getStudentStatistics();
            logger.info("学生統計取得成功 - 総学生数: {}", statistics.getTotalStudents());
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            logger.error("学生統計取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 部門別学生統計
     */
    @GetMapping("/statistics/department")
    public ResponseEntity<Map<String, Long>> getDepartmentStatistics() {
        logger.info("部門別学生統計取得要求");
        try {
            Map<String, Long> departmentStats = studentService.getDepartmentStatistics();
            logger.info("部門別統計取得成功 - 部門数: {}", departmentStats.size());
            return ResponseEntity.ok(departmentStats);
        } catch (Exception e) {
            logger.error("部門別統計取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生一括登録
     */
    @PostMapping("/batch")
    public ResponseEntity<List<StudentResponse>> createStudentsBatch(@Valid @RequestBody List<StudentRequest> requests) {
        logger.info("学生一括登録要求 - 登録件数: {}", requests.size());
        try {
            List<StudentResponse> students = studentService.createStudentsBatch(requests);
            logger.info("学生一括登録成功 - 登録完了件数: {}", students.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(students);
        } catch (ValidationException e) {
            logger.warn("学生一括登録バリデーションエラー: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("学生一括登録エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生ステータス更新
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<StudentResponse> updateStudentStatus(
            @PathVariable Long id, 
            @RequestParam String status) {
        logger.info("学生ステータス更新要求 - ID: {}, ステータス: {}", id, status);
        try {
            StudentResponse student = studentService.updateStudentStatus(id, status);
            logger.info("学生ステータス更新成功 - ID: {}, 新ステータス: {}", id, status);
            return ResponseEntity.ok(student);
        } catch (StudentNotFoundException e) {
            logger.warn("ステータス更新対象の学生が見つかりません - ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (ValidationException e) {
            logger.warn("ステータス更新バリデーションエラー - ID: {}, ステータス: {}, エラー: {}", id, status, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("学生ステータス更新エラー - ID: {}, ステータス: {}", id, status, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}