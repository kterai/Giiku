package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.Grade;
import jp.co.apsa.giiku.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 成績コントローラー
 *
 * LMS成績管理機能のREST APIエンドポイントを提供します。
 * 成績の記録、更新、統計分析、GPA計算などの機能を含みます。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api/grades")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GradeController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(GradeController.class);

    @Autowired
    private GradeService gradeService;

    // ===== CRUD操作 =====

    /**
     * 成績一覧をページング形式で取得
     *
     * @param pageable ページング情報
     * @return 成績一覧のページ
     */
    @GetMapping
    public ResponseEntity<Page<Grade>> getAllGrades(Pageable pageable) {
        try {
            logger.debug("成績一覧取得リクエスト: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
            Page<Grade> grades = gradeService.findAll(pageable);
            return ResponseEntity.ok(grades);
        } catch (Exception e) {
            logger.error("成績一覧取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 成績IDで成績詳細を取得
     *
     * @param id 成績ID
     * @return 成績詳細
     */
    @GetMapping("/{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable Long id) {
        try {
            logger.debug("成績詳細取得リクエスト: id={}", id);
            Optional<Grade> grade = gradeService.findById(id);
            return grade.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("成績詳細取得エラー: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 新しい成績を記録
     *
     * @param grade 成績情報
     * @return 記録された成績
     */
    @PostMapping
    public ResponseEntity<Grade> createGrade(@Valid @RequestBody Grade grade) {
        try {
            logger.debug("成績記録リクエスト: studentId={}, assessmentType={}", 
                        grade.getStudentId(), grade.getAssessmentType());
            Grade savedGrade = gradeService.save(grade);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedGrade);
        } catch (Exception e) {
            logger.error("成績記録エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 成績情報を更新
     *
     * @param id 成績ID
     * @param grade 更新する成績情報
     * @return 更新された成績
     */
    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, 
                                            @Valid @RequestBody Grade grade) {
        try {
            logger.debug("成績更新リクエスト: id={}", id);
            grade.setId(id);
            Grade updatedGrade = gradeService.save(grade);
            return ResponseEntity.ok(updatedGrade);
        } catch (Exception e) {
            logger.error("成績更新エラー: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 成績を削除（論理削除）
     *
     * @param id 成績ID
     * @return 削除結果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        try {
            logger.debug("成績削除リクエスト: id={}", id);
            gradeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("成績削除エラー: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== 検索機能 =====

    /**
     * 学生IDで成績一覧を取得
     *
     * @param studentId 学生ID
     * @return 成績一覧
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Grade>> getGradesByStudent(@PathVariable Long studentId) {
        try {
            logger.debug("学生別成績取得リクエスト: studentId={}", studentId);
            List<Grade> grades = gradeService.findByStudentId(studentId);
            return ResponseEntity.ok(grades);
        } catch (Exception e) {
            logger.error("学生別成績取得エラー: studentId={}", studentId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 講師IDで成績一覧を取得
     *
     * @param instructorId 講師ID
     * @return 成績一覧
     */
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Grade>> getGradesByInstructor(@PathVariable Long instructorId) {
        try {
            logger.debug("講師別成績取得リクエスト: instructorId={}", instructorId);
            List<Grade> grades = gradeService.findByInstructorId(instructorId);
            return ResponseEntity.ok(grades);
        } catch (Exception e) {
            logger.error("講師別成績取得エラー: instructorId={}", instructorId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 評価タイプで成績一覧を取得
     *
     * @param assessmentType 評価タイプ
     * @return 成績一覧
     */
    @GetMapping("/assessment-type/{assessmentType}")
    public ResponseEntity<List<Grade>> getGradesByAssessmentType(@PathVariable String assessmentType) {
        try {
            logger.debug("評価タイプ別成績取得リクエスト: assessmentType={}", assessmentType);
            List<Grade> grades = gradeService.findByAssessmentType(assessmentType);
            return ResponseEntity.ok(grades);
        } catch (Exception e) {
            logger.error("評価タイプ別成績取得エラー: assessmentType={}", assessmentType, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 成績ステータスで成績一覧を取得
     *
     * @param gradeStatus 成績ステータス
     * @return 成績一覧
     */
    @GetMapping("/status/{gradeStatus}")
    public ResponseEntity<List<Grade>> getGradesByStatus(@PathVariable String gradeStatus) {
        try {
            logger.debug("成績ステータス別取得リクエスト: gradeStatus={}", gradeStatus);
            List<Grade> grades = gradeService.findByGradeStatus(gradeStatus);
            return ResponseEntity.ok(grades);
        } catch (Exception e) {
            logger.error("成績ステータス別取得エラー: gradeStatus={}", gradeStatus, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 期間範囲で成績一覧を取得
     *
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @return 成績一覧
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<Grade>> getGradesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            logger.debug("期間別成績取得リクエスト: start={}, end={}", startDate, endDate);
            List<Grade> grades = gradeService.findByDateRange(startDate, endDate);
            return ResponseEntity.ok(grades);
        } catch (Exception e) {
            logger.error("期間別成績取得エラー", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== GPA・統計機能 =====

    /**
     * 学生のGPAを計算
     *
     * @param studentId 学生ID
     * @return 学生のGPA
     */
    @GetMapping("/student/{studentId}/gpa")
    public ResponseEntity<BigDecimal> getStudentGPA(@PathVariable Long studentId) {
        try {
            logger.debug("学生GPA計算リクエスト: studentId={}", studentId);
            BigDecimal gpa = gradeService.calculateStudentGPA(studentId);
            return ResponseEntity.ok(gpa);
        } catch (Exception e) {
            logger.error("学生GPA計算エラー: studentId={}", studentId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学生の加重平均GPAを計算
     *
     * @param studentId 学生ID
     * @return 学生の加重平均GPA
     */
    @GetMapping("/student/{studentId}/weighted-gpa")
    public ResponseEntity<BigDecimal> getStudentWeightedGPA(@PathVariable Long studentId) {
        try {
            logger.debug("学生加重GPA計算リクエスト: studentId={}", studentId);
            BigDecimal weightedGpa = gradeService.calculateWeightedStudentGPA(studentId);
            return ResponseEntity.ok(weightedGpa);
        } catch (Exception e) {
            logger.error("学生加重GPA計算エラー: studentId={}", studentId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 企業内平均GPAを計算
     *
     * @param companyId 企業ID
     * @return 企業内平均GPA
     */
    @GetMapping("/company/{companyId}/average-gpa")
    public ResponseEntity<BigDecimal> getCompanyAverageGPA(@PathVariable Long companyId) {
        try {
            logger.debug("企業平均GPA計算リクエスト: companyId={}", companyId);
            BigDecimal averageGpa = gradeService.calculateCompanyAverageGPA(companyId);
            return ResponseEntity.ok(averageGpa);
        } catch (Exception e) {
            logger.error("企業平均GPA計算エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 企業内評価タイプ別平均スコア統計を取得
     *
     * @param companyId 企業ID
     * @return 評価タイプ別平均スコア統計
     */
    @GetMapping("/company/{companyId}/stats/assessment-type")
    public ResponseEntity<List<Map<String, Object>>> getAssessmentTypeStats(@PathVariable Long companyId) {
        try {
            logger.debug("評価タイプ統計取得リクエスト: companyId={}", companyId);
            List<Map<String, Object>> stats = gradeService.getAssessmentTypeStatistics(companyId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("評価タイプ統計取得エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 企業内文字成績分布統計を取得
     *
     * @param companyId 企業ID
     * @return 文字成績分布統計
     */
    @GetMapping("/company/{companyId}/stats/grade-distribution")
    public ResponseEntity<List<Map<String, Object>>> getGradeDistribution(@PathVariable Long companyId) {
        try {
            logger.debug("成績分布統計取得リクエスト: companyId={}", companyId);
            List<Map<String, Object>> distribution = gradeService.getGradeDistribution(companyId);
            return ResponseEntity.ok(distribution);
        } catch (Exception e) {
            logger.error("成績分布統計取得エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 企業内学生別成績統計を取得
     *
     * @param companyId 企業ID
     * @return 学生別成績統計
     */
    @GetMapping("/company/{companyId}/stats/student")
    public ResponseEntity<List<Map<String, Object>>> getStudentGradeStatistics(@PathVariable Long companyId) {
        try {
            logger.debug("学生別成績統計取得リクエスト: companyId={}", companyId);
            List<Map<String, Object>> stats = gradeService.getStudentGradeStatistics(companyId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("学生別成績統計取得エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 企業内講師別評価統計を取得
     *
     * @param companyId 企業ID
     * @return 講師別評価統計
     */
    @GetMapping("/company/{companyId}/stats/instructor")
    public ResponseEntity<List<Map<String, Object>>> getInstructorGradingStatistics(@PathVariable Long companyId) {
        try {
            logger.debug("講師別評価統計取得リクエスト: companyId={}", companyId);
            List<Map<String, Object>> stats = gradeService.getInstructorGradingStatistics(companyId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("講師別評価統計取得エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * トップ成績学生を取得
     *
     * @param companyId 企業ID
     * @param limit 取得件数
     * @return トップ成績学生リスト
     */
    @GetMapping("/company/{companyId}/top-students")
    public ResponseEntity<List<Map<String, Object>>> getTopPerformingStudents(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "10") Integer limit) {
        try {
            logger.debug("トップ成績学生取得リクエスト: companyId={}, limit={}", companyId, limit);
            List<Map<String, Object>> topStudents = gradeService.getTopPerformingStudents(companyId, limit);
            return ResponseEntity.ok(topStudents);
        } catch (Exception e) {
            logger.error("トップ成績学生取得エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 学習困難学生を識別
     *
     * @param companyId 企業ID
     * @param threshold GPA閾値
     * @return 困難学生リスト
     */
    @GetMapping("/company/{companyId}/at-risk-students")
    public ResponseEntity<List<Map<String, Object>>> getStudentsAtRisk(
            @PathVariable Long companyId,
            @RequestParam BigDecimal threshold) {
        try {
            logger.debug("困難学生識別リクエスト: companyId={}, threshold={}", companyId, threshold);
            List<Map<String, Object>> atRiskStudents = gradeService.getStudentsAtRisk(companyId, threshold);
            return ResponseEntity.ok(atRiskStudents);
        } catch (Exception e) {
            logger.error("困難学生識別エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 期間内成績推移統計を取得
     *
     * @param companyId 企業ID
     * @param startDate 開始日時
     * @param endDate 終了日時
     * @return 成績推移統計
     */
    @GetMapping("/company/{companyId}/trends")
    public ResponseEntity<List<Map<String, Object>>> getGradeTrendAnalysis(
            @PathVariable Long companyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            logger.debug("成績推移分析リクエスト: companyId={}, start={}, end={}", companyId, startDate, endDate);
            List<Map<String, Object>> trends = gradeService.getGradeTrendAnalysis(companyId, startDate, endDate);
            return ResponseEntity.ok(trends);
        } catch (Exception e) {
            logger.error("成績推移分析エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 未採点評価を取得
     *
     * @param companyId 企業ID
     * @return 未採点評価一覧
     */
    @GetMapping("/company/{companyId}/pending")
    public ResponseEntity<List<Grade>> getPendingGrades(@PathVariable Long companyId) {
        try {
            logger.debug("未採点評価取得リクエスト: companyId={}", companyId);
            List<Grade> pendingGrades = gradeService.findPendingGrades(companyId);
            return ResponseEntity.ok(pendingGrades);
        } catch (Exception e) {
            logger.error("未採点評価取得エラー: companyId={}", companyId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 講師の未採点評価を取得
     *
     * @param instructorId 講師ID
     * @return 講師の未採点評価一覧
     */
    @GetMapping("/instructor/{instructorId}/pending")
    public ResponseEntity<List<Grade>> getPendingGradesByInstructor(@PathVariable Long instructorId) {
        try {
            logger.debug("講師の未採点評価取得リクエスト: instructorId={}", instructorId);
            List<Grade> pendingGrades = gradeService.findPendingGradesByInstructor(instructorId);
            return ResponseEntity.ok(pendingGrades);
        } catch (Exception e) {
            logger.error("講師の未採点評価取得エラー: instructorId={}", instructorId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
