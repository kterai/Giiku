package jp.co.apsa.giiku.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.apsa.giiku.domain.entity.Day;
import jp.co.apsa.giiku.domain.repository.DayRepository;

/**
 * 日サービス
 * 日情報の取得を提供する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class DayService {

    @Autowired
    private DayRepository dayRepository;

    /** 全件取得 */
    @Transactional(readOnly = true)
    public List<Day> findAll() {
        return dayRepository.findAll();
    }

    /** 日番号で取得 */
    @Transactional(readOnly = true)
    public Optional<Day> findByDayNumber(int dayNumber) {
        return dayRepository.findByDayNumber(dayNumber);
    }

    /** 週IDで取得 */
    @Transactional(readOnly = true)
    public List<Day> findByWeekId(Long weekId) {
        return dayRepository.findByWeekId(weekId);
    }

    /** IDで取得 */
    @Transactional(readOnly = true)
    public Optional<Day> findById(Long id) {
        return dayRepository.findById(id);
    }
}
