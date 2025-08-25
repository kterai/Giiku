package jp.co.apsa.giiku.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.apsa.giiku.domain.entity.Month;
import jp.co.apsa.giiku.domain.repository.MonthRepository;

/**
 * 月サービス
 * 月情報の取得を提供する。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class MonthService {

    @Autowired
    private MonthRepository monthRepository;

    /** 全件取得 */
    @Transactional(readOnly = true)
    public List<Month> findAll() {
        return monthRepository.findAll();
    }

    /** 月番号で取得 */
    @Transactional(readOnly = true)
    public Optional<Month> findByMonthNumber(int monthNumber) {
        return monthRepository.findByMonthNumber(monthNumber);
    }

    /** IDで取得 */
    @Transactional(readOnly = true)
    public Optional<Month> findById(Long id) {
        return monthRepository.findById(id);
    }
}
