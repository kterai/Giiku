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
import jp.co.apsa.giiku.domain.entity.QuestionBank;
import jp.co.apsa.giiku.domain.repository.ChapterRepository;
import jp.co.apsa.giiku.domain.repository.LectureChapterLinkRepository;
import jp.co.apsa.giiku.domain.repository.QuestionBankRepository;

/**
 * QuestionBankService のテストクラス。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class QuestionBankServiceTest {

    @Autowired
    private QuestionBankService questionBankService;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private QuestionBankRepository questionBankRepository;

    @Autowired
    private LectureChapterLinkRepository lectureChapterLinkRepository;

    /**
     * 講義IDで有効な問題のみがチャプター順・問題番号順で取得できることを検証します。
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

        Chapter ch3 = new Chapter();
        ch3.setChapterNumber(3);
        ch3.setTitle("Ch3");
        ch3 = chapterRepository.saveAndFlush(ch3);

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

        QuestionBank q1 = new QuestionBank();
        q1.setChapter(ch2);
        q1.setQuestionNumber(1);
        q1.setQuestionType("multiple_choice");
        q1.setQuestionText("Q1");
        q1.setDifficultyLevel("easy");
        q1.setIsActive(true);
        questionBankRepository.save(q1);

        QuestionBank q2 = new QuestionBank();
        q2.setChapter(ch1);
        q2.setQuestionNumber(1);
        q2.setQuestionType("multiple_choice");
        q2.setQuestionText("Q2");
        q2.setDifficultyLevel("easy");
        q2.setIsActive(true);
        questionBankRepository.save(q2);

        QuestionBank q3 = new QuestionBank();
        q3.setChapter(ch1);
        q3.setQuestionNumber(2);
        q3.setQuestionType("multiple_choice");
        q3.setQuestionText("Q3");
        q3.setDifficultyLevel("easy");
        q3.setIsActive(false);
        questionBankRepository.save(q3);

        QuestionBank q4 = new QuestionBank();
        q4.setChapter(ch3);
        q4.setQuestionNumber(1);
        q4.setQuestionType("multiple_choice");
        q4.setQuestionText("Q4");
        q4.setDifficultyLevel("easy");
        q4.setIsActive(true);
        questionBankRepository.save(q4);

        List<LectureChapterLink> links = lectureChapterLinkRepository.findByLectureIdOrderBySortOrder(1L);
        assertEquals(2, links.size());

        List<QuestionBank> result = questionBankService.findByLectureId(1L);
        assertEquals(2, result.size());
        assertEquals("Q1", result.get(0).getQuestionText());
        assertEquals("Q2", result.get(1).getQuestionText());
    }
}
