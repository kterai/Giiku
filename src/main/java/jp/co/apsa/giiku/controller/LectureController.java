package jp.co.apsa.giiku.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jp.co.apsa.giiku.domain.entity.Lecture;
import jp.co.apsa.giiku.service.LectureService;

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

    /** 講義一覧取得 */
    @GetMapping
    public ResponseEntity<List<Lecture>> getAllLectures() {
        return ResponseEntity.ok(lectureService.findAll());
    }

    /** 講義詳細取得 */
    @GetMapping("/{id}")
    public ResponseEntity<Lecture> getLectureById(@PathVariable Long id) {
        return lectureService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** 講義作成 */
    @PostMapping
    public ResponseEntity<Lecture> createLecture(@Valid @RequestBody Lecture lecture) {
        Lecture created = lectureService.save(lecture);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** 講義更新 */
    @PutMapping("/{id}")
    public ResponseEntity<Lecture> updateLecture(@PathVariable Long id, @Valid @RequestBody Lecture lecture) {
        Lecture updated = lectureService.update(id, lecture);
        return ResponseEntity.ok(updated);
    }

    /** 講義削除 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecture(@PathVariable Long id) {
        lectureService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
