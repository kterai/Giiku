package jp.co.apsa.giiku.application.service;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.apsa.giiku.domain.entity.Position;
import jp.co.apsa.giiku.domain.repository.PositionRepository;

/**
 * 役職を管理するサービス。
 *
 * <p>マスタメンテナンス画面用のCRUD処理を提供します。</p>
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class PositionService {

    private final PositionRepository positionRepository;

    /**
     * コンストラクタ。
     *
     * @param positionRepository 役職リポジトリ
     */
    @Autowired
    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    /**
     * 全ての役職を表示順に取得します。
     *
     * @return 役職リスト
     */
    @Transactional(readOnly = true)
    public List<Position> findAll() {
        return positionRepository.findByIsActiveTrueOrderByDisplayOrderAsc();
    }

    /**
     * IDで役職を取得します。
     *
     * @param id 役職ID
     * @return 役職、存在しない場合はnull
     */
    @Transactional(readOnly = true)
    public Position findById(Long id) {
        return positionRepository.findById(id).orElse(null);
    }

    /**
     * 役職を新規作成します。
     *
     * @param position 役職
     * @return 作成された役職
     */
    public Position create(@Valid Position position) {
        return positionRepository.save(position);
    }

    /**
     * 役職を更新します。
     *
     * @param position 役職
     * @return 更新された役職
     */
    public Position update(@Valid Position position) {
        return positionRepository.save(position);
    }

    /**
     * 役職を削除します。関連ユーザーが存在する場合は削除しません。
     *
     * @param id 役職ID
     * @return 削除できた場合true
     */
    public boolean delete(Long id) {
        if (positionRepository.isUsedByUsers(id)) {
            return false;
        }
        positionRepository.deleteById(id);
        return true;
    }
}
