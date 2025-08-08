package jp.co.apsa.giiku.application.service;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.apsa.giiku.domain.entity.User;
import jp.co.apsa.giiku.domain.repository.UserRepository;

/**
 * ユーザーマスタを管理するサービス。
 *
 * <p>管理画面で使用するCRUD処理を提供します。</p>
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class UserAdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * コンストラクタ。
     *
     * @param userRepository ユーザーリポジトリ
     * @param passwordEncoder パスワードエンコーダー
     */
    @Autowired
    public UserAdminService(UserRepository userRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 全ユーザーを取得します。
     *
     * @return ユーザー一覧
     */
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * IDでユーザーを取得します。
     *
     * @param id ユーザーID
     * @return ユーザー、存在しない場合はnull
     */
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * ユーザーを新規作成します。
     *
     * @param user ユーザー
     * @return 作成されたユーザー
     */
    public User create(@Valid User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * ユーザーを更新します。
     *
     * @param user ユーザー
     * @return 更新されたユーザー
     */
    public User update(@Valid User user) {
        if (!user.getPassword().startsWith("$2")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    /**
     * ユーザーを削除します。
     *
     * @param id ユーザーID
     */
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
