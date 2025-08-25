package jp.co.apsa.giiku.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.apsa.giiku.domain.entity.Lecture;
import jp.co.apsa.giiku.domain.repository.LectureRepository;

/**
 * 講義サービス
 * 講義の基本CRUD操作を提供する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    /** すべての講義を取得 */
    @Transactional(readOnly = true)
    public List<Lecture> findAll() {
        return lectureRepository.findAll();
    }

    /** IDで講義を取得 */
    @Transactional(readOnly = true)
    public Optional<Lecture> findById(Long id) {
        return lectureRepository.findById(id);
    }

    /** 日IDで講義一覧を取得 */
    @Transactional(readOnly = true)
    public List<Lecture> findByDayId(Long dayId) {
        return lectureRepository.findByDayId(dayId);
    }

    /** 講義を保存 */
    public Lecture save(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    /** 講義を更新 */
    public Lecture update(Long id, Lecture lecture) {
        if (!lectureRepository.existsById(id)) {
            throw new IllegalArgumentException("指定された講義が存在しません: " + id);
        }
        lecture.setId(id);
        return lectureRepository.save(lecture);
    }

    /** 講義を削除 */
    public void delete(Long id) {
        lectureRepository.deleteById(id);
    }
}
