package jp.co.apsa.giiku.controller;

import jp.co.apsa.giiku.dto.LectureChapterCreateDto;
import jp.co.apsa.giiku.dto.LectureChapterUpdateDto;
import jp.co.apsa.giiku.dto.LectureChapterResponseDto;
import jp.co.apsa.giiku.service.LectureChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * 講義チャプター管理コントローラー。
 * 基本的な CRUD と順序変更、複製機能を提供する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LectureChapterController {

    @Autowired
    private LectureChapterService lectureChapterService;

    /**
     * チャプター一覧を取得する。
     */
    @GetMapping("/lecture-chapters")
    public Page<LectureChapterResponseDto> getAllChapters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {
        return lectureChapterService.getAllChapters(page, size, sortBy, sortDir);
    }

    /**
     * ID でチャプターを取得する。
     */
    @GetMapping("/lecture-chapters/{id}")
    public ResponseEntity<LectureChapterResponseDto> getChapter(@PathVariable Long id) {
        Optional<LectureChapterResponseDto> chapter = lectureChapterService.getChapterById(id);
        return chapter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 講義別にチャプターを取得する。
     */
    @GetMapping("/lectures/{lectureId}/chapters")
    public List<LectureChapterResponseDto> getChaptersByLecture(
            @PathVariable Long lectureId,
            @RequestParam(defaultValue = "ASC") String sortDir) {
        return lectureChapterService.getChaptersByLectureId(lectureId, sortDir);
    }

    /**
     * 新しいチャプターを作成する。
     */
    @PostMapping("/lecture-chapters")
    public ResponseEntity<LectureChapterResponseDto> createChapter(
            @Valid @RequestBody LectureChapterCreateDto dto) {
        return ResponseEntity.ok(lectureChapterService.createChapter(dto));
    }

    /**
     * チャプターを更新する。
     */
    @PutMapping("/lecture-chapters/{id}")
    public ResponseEntity<LectureChapterResponseDto> updateChapter(
            @PathVariable Long id,
            @Valid @RequestBody LectureChapterUpdateDto dto) {
        Optional<LectureChapterResponseDto> updated = lectureChapterService.updateChapter(id, dto);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * チャプターを削除する。
     */
    @DeleteMapping("/lecture-chapters/{id}")
    public ResponseEntity<Void> deleteChapter(@PathVariable Long id) {
        lectureChapterService.deleteChapter(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * チャプターの並び順を更新する。
     */
    @PutMapping("/lectures/{lectureId}/chapters/reorder")
    public List<LectureChapterResponseDto> reorderChapters(
            @PathVariable Long lectureId,
            @RequestBody List<Long> chapterIds) {
        return lectureChapterService.reorderChapters(lectureId, chapterIds);
    }

    /**
     * チャプターを複製する。
     */
    @PostMapping("/lecture-chapters/{id}/duplicate")
    public ResponseEntity<LectureChapterResponseDto> duplicateChapter(
            @PathVariable Long id,
            @RequestParam(required = false) Long targetLectureId) {
        return ResponseEntity.ok(lectureChapterService.duplicateChapter(id, targetLectureId));
    }
}

