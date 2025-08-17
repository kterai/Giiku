package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.domain.entity.Lecture;
import jp.co.apsa.giiku.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 講義管理コントローラー
 * 講義エンティティの基本CRUD操作を提供する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api/lectures")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    /**
     * 講義一覧取得
     *
     * @param pageable ページング情報
     * @return 講義一覧
     */
    @GetMapping
    public ResponseEntity<Page<Lecture>> getAllLectures(@PageableDefault(size = 20) Pageable pageable) {
        Page<Lecture> lectures = lectureService.searchLectures(null, null, null, null, null, pageable);
        return ResponseEntity.ok(lectures);
    }

    /**
     * 講義詳細取得
     *
     * @param id 講義ID
     * @return 講義詳細
     */
    @GetMapping("/{id}")
    public ResponseEntity<Lecture> getLectureById(@PathVariable Long id) {
        return lectureService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * プログラム別講義取得
     *
     * @param programId 研修プログラムID
     * @return 講義一覧
     */
    @GetMapping("/program/{programId}")
    public ResponseEntity<List<Lecture>> getLecturesByProgramId(@PathVariable Long programId) {
        List<Lecture> lectures = lectureService.findByTrainingProgramId(programId);
        return ResponseEntity.ok(lectures);
    }

    /**
     * 講義作成
     *
     * @param lecture 講義エンティティ
     * @return 作成された講義
     */
    @PostMapping
    public ResponseEntity<Lecture> createLecture(@Valid @RequestBody Lecture lecture) {
        Lecture createdLecture = lectureService.save(lecture);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLecture);
    }

    /**
     * 講義更新
     *
     * @param id 講義ID
     * @param lecture 更新する講義エンティティ
     * @return 更新された講義
     */
    @PutMapping("/{id}")
    public ResponseEntity<Lecture> updateLecture(@PathVariable Long id, @Valid @RequestBody Lecture lecture) {
        Lecture updatedLecture = lectureService.update(id, lecture);
        return ResponseEntity.ok(updatedLecture);
    }

    /**
     * 講義削除
     *
     * @param id 講義ID
     * @return レスポンスステータス
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecture(@PathVariable Long id) {
        lectureService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

