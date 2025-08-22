package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.LectureGrade;
import jp.co.apsa.giiku.service.LectureGradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 講義成績コントローラー
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api/lecture-grades")
public class LectureGradeController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LectureGradeController.class);

    @Autowired
    private LectureGradeService lectureGradeService;

    /**
     * すべての講義成績を取得します。
     *
     * @param pageable ページ情報
     * @return 成績のページ
     */
    @GetMapping
    public ResponseEntity<Page<LectureGrade>> getAll(Pageable pageable) {
        Page<LectureGrade> grades = lectureGradeService.findAll(pageable);
        return ResponseEntity.ok(grades);
    }

    /**
     * IDで講義成績を取得します。
     *
     * @param id 成績ID
     * @return 成績
     */
    @GetMapping("/{id}")
    public ResponseEntity<LectureGrade> getById(@PathVariable Long id) {
        return lectureGradeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 講義成績を作成します。
     *
     * @param lectureGrade 成績エンティティ
     * @return 作成された成績
     */
    @PostMapping
    public ResponseEntity<LectureGrade> create(@Valid @RequestBody LectureGrade lectureGrade) {
        LectureGrade saved = lectureGradeService.save(lectureGrade);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * 講義成績を更新します。
     *
     * @param id 成績ID
     * @param lectureGrade 更新する成績
     * @return 更新された成績
     */
    @PutMapping("/{id}")
    public ResponseEntity<LectureGrade> update(@PathVariable Long id,
                                               @Valid @RequestBody LectureGrade lectureGrade) {
        lectureGrade.setId(id);
        LectureGrade updated = lectureGradeService.save(lectureGrade);
        return ResponseEntity.ok(updated);
    }

    /**
     * 講義成績を削除します。
     *
     * @param id 成績ID
     * @return レスポンス
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lectureGradeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

