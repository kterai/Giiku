package jp.co.apsa.giiku.application.service;

import org.springframework.stereotype.Service;

import jp.co.apsa.giiku.domain.repository.UserRepository;

/**
 * ダッシュボード表示用のアプリケーションサービス。
 * 現在はユーザー数の取得のみを提供します。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
public class DashboardService {

    private final UserRepository userRepository;
    /** DashboardService メソッド */
    public DashboardService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 登録されているユーザー数を返します。
     *
     * @return ユーザー数
     */
    public long countUsers() {
        return userRepository.count();
    }
}
