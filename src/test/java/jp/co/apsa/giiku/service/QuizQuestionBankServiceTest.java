package jp.co.apsa.giiku.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import jp.co.apsa.giiku.domain.entity.Chapter;
import jp.co.apsa.giiku.domain.entity.LectureChapterLink;
import jp.co.apsa.giiku.domain.entity.QuizQuestionBank;
import jp.co.apsa.giiku.domain.repository.ChapterRepository;
import jp.co.apsa.giiku.domain.repository.LectureChapterLinkRepository;
import jp.co.apsa.giiku.domain.repository.QuizQuestionBankRepository;

/**
 * QuizQuestionBankService のテストクラス。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class QuizQuestionBankServiceTest {

    @Autowired
    private QuizQuestionBankService quizQuestionBankService;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private QuizQuestionBankRepository quizQuestionBankRepository;

    @Autowired
    private LectureChapterLinkRepository lectureChapterLinkRepository;

    /**
     * 講義IDで有効なクイズ問題のみがチャプター順・問題番号順で取得できることを検証します。
     *
     * @author 株式会社アプサ
     * @version 1.0
     * @since 2025
     */
    @Test
    public void testFindByLectureIdReturnsActiveQuestions() {
        Chapter ch1 = new Chapter();
        ch1.setChapterNumber(1);
        ch1.setTitle("Ch1");
        ch1 = chapterRepository.saveAndFlush(ch1);

        Chapter ch2 = new Chapter();
        ch2.setChapterNumber(2);
        ch2.setTitle("Ch2");
        ch2 = chapterRepository.saveAndFlush(ch2);

        LectureChapterLink link1 = new LectureChapterLink();
        link1.setLectureId(1L);
        link1.setChapter(ch1);
        link1.setSortOrder(2);
        lectureChapterLinkRepository.save(link1);

        LectureChapterLink link2 = new LectureChapterLink();
        link2.setLectureId(1L);
        link2.setChapter(ch2);
        link2.setSortOrder(1);
        lectureChapterLinkRepository.save(link2);

        QuizQuestionBank qq1 = new QuizQuestionBank();
        qq1.setChapter(ch2);
        qq1.setQuestionNumber(1);
        qq1.setQuestionType("single");
        qq1.setQuestionText("QQ1");
        qq1.setCorrectAnswer("A");
        qq1.setIsActive(true);
        quizQuestionBankRepository.save(qq1);

        QuizQuestionBank qq2 = new QuizQuestionBank();
        qq2.setChapter(ch1);
        qq2.setQuestionNumber(1);
        qq2.setQuestionType("single");
        qq2.setQuestionText("QQ2");
        qq2.setCorrectAnswer("A");
        qq2.setIsActive(true);
        quizQuestionBankRepository.save(qq2);

        QuizQuestionBank qq3 = new QuizQuestionBank();
        qq3.setChapter(ch1);
        qq3.setQuestionNumber(2);
        qq3.setQuestionType("single");
        qq3.setQuestionText("QQ3");
        qq3.setCorrectAnswer("A");
        qq3.setIsActive(false);
        quizQuestionBankRepository.save(qq3);

        List<LectureChapterLink> links = lectureChapterLinkRepository.findByLectureIdOrderBySortOrder(1L);
        assertEquals(2, links.size());

        List<QuizQuestionBank> result = quizQuestionBankService.findByLectureId(1L);
        assertEquals(2, result.size());
        assertEquals("QQ1", result.get(0).getQuestionText());
        assertEquals("QQ2", result.get(1).getQuestionText());
    }
}
