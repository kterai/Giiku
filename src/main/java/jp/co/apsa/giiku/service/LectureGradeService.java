package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.LectureGrade;
import jp.co.apsa.giiku.domain.repository.LectureGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * LectureGradeサービスクラス
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
public class LectureGradeService {

    @Autowired
    private LectureGradeRepository lectureGradeRepository;

    /**
     * すべての講義成績を取得します。
     *
     * @return 成績リスト
     */
    public List<LectureGrade> findAll() {
        return lectureGradeRepository.findAll();
    }

    /**
     * ページングされた講義成績を取得します。
     *
     * @param pageable ページ情報
     * @return ページングされた成績
     */
    public Page<LectureGrade> findAll(Pageable pageable) {
        return lectureGradeRepository.findAll(pageable);
    }

    /**
     * IDで講義成績を検索します。
     *
     * @param id 成績ID
     * @return 成績のOptional
     */
    public Optional<LectureGrade> findById(Long id) {
        return lectureGradeRepository.findById(id);
    }

    /**
     * 成績を保存します。
     *
     * @param lectureGrade 成績エンティティ
     * @return 保存された成績
     */
    public LectureGrade save(LectureGrade lectureGrade) {
        return lectureGradeRepository.save(lectureGrade);
    }

    /**
     * 成績を削除します。
     *
     * @param id 成績ID
     */
    public void delete(Long id) {
        lectureGradeRepository.deleteById(id);
    }
}

