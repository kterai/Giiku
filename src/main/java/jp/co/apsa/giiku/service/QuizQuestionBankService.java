package jp.co.apsa.giiku.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.co.apsa.giiku.domain.entity.QuizQuestionBank;
import jp.co.apsa.giiku.domain.repository.QuizQuestionBankRepository;

/**
 * QuizQuestionBank サービスクラス。
 * クイズ問題の取得機能を提供します。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class QuizQuestionBankService {

    @Autowired
    private QuizQuestionBankRepository quizQuestionBankRepository;

    /**
     * 指定された講義IDのクイズ問題を取得します。
     *
     * @param lectureId 講義ID
     * @return クイズ問題一覧
     */
    @Transactional(readOnly = true)
    public List<QuizQuestionBank> findByLectureId(Long lectureId) {
        return quizQuestionBankRepository.findByLectureIdOrderByQuestionNumber(lectureId);
    }
}
