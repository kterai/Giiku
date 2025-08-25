package jp.co.apsa.giiku.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.apsa.giiku.domain.entity.Week;
import jp.co.apsa.giiku.domain.repository.WeekRepository;

/**
 * 週サービス
 * 週情報の取得を提供する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class WeekService {

    @Autowired
    private WeekRepository weekRepository;

    /** 全件取得 */
    @Transactional(readOnly = true)
    public List<Week> findAll() {
        return weekRepository.findAll();
    }

    /** 週番号で取得 */
    @Transactional(readOnly = true)
    public Optional<Week> findByWeekNumber(int weekNumber) {
        return weekRepository.findByWeekNumber(weekNumber);
    }

    /** 月IDで取得 */
    @Transactional(readOnly = true)
    public List<Week> findByMonthId(Long monthId) {
        return weekRepository.findByMonthId(monthId);
    }

    /** IDで取得 */
    @Transactional(readOnly = true)
    public Optional<Week> findById(Long id) {
        return weekRepository.findById(id);
    }
}
