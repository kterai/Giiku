package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.TrainingProgram;
import jp.co.apsa.giiku.domain.repository.TrainingProgramRepository;
import jp.co.apsa.giiku.domain.repository.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * TrainingProgram（研修プログラム）に関するビジネスロジックを提供するサービスクラス。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class TrainingProgramService {

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    @Autowired
    private CompanyRepository companyRepository;


    /** 全ての研修プログラムを取得 */
    @Transactional(readOnly = true)
    public List<TrainingProgram> findAll() {
        return trainingProgramRepository.findAll();
    }

    /** ページング対応の研修プログラムを取得 */
    @Transactional(readOnly = true)
    public Page<TrainingProgram> findAll(Pageable pageable) {
        return trainingProgramRepository.findAll(pageable);
    }

    /** キーワード検索付きページング取得 */
    @Transactional(readOnly = true)
    public Page<TrainingProgram> findAll(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return trainingProgramRepository.findAll(pageable);
        }
        Specification<TrainingProgram> spec = (root, query, cb) ->
                cb.like(cb.lower(root.get("programName")), "%" + keyword.toLowerCase() + "%");
        return trainingProgramRepository.findAll(spec, pageable);
    }

    /** キーワードで研修プログラムを検索 */
    @Transactional(readOnly = true)
    public Page<TrainingProgram> searchPrograms(String keyword, Pageable pageable) {
        return findAll(keyword, pageable);
    }

    /** IDで研修プログラムを取得 */
    @Transactional(readOnly = true)
    public Optional<TrainingProgram> findById(Long id) {
        return trainingProgramRepository.findById(id);
    }

    /** 企業IDで研修プログラムを取得 */
    @Transactional(readOnly = true)
    public List<TrainingProgram> findByCompanyId(Long companyId) {
        return trainingProgramRepository.findByCompanyIdAndIsActiveTrue(companyId);
    }

    /** アクティブな研修プログラムを取得 */
    @Transactional(readOnly = true)
    public List<TrainingProgram> findActivePrograms() {
        return trainingProgramRepository.findByIsActiveTrue();
    }

    /** 研修プログラムを保存 */
    public TrainingProgram save(TrainingProgram trainingProgram) {
        validateTrainingProgram(trainingProgram);

        if (trainingProgram.getCompanyId() != null &&
                !companyRepository.existsById(trainingProgram.getCompanyId())) {
            throw new IllegalArgumentException("指定された企業が存在しません: " + trainingProgram.getCompanyId());
        }

        return trainingProgramRepository.save(trainingProgram);
    }

    /** 研修プログラムを更新 */
    public TrainingProgram update(Long id, TrainingProgram trainingProgram) {
        if (!trainingProgramRepository.existsById(id)) {
            throw new IllegalArgumentException("指定された研修プログラムが存在しません: " + id);
        }
        trainingProgram.setId(id);
        return save(trainingProgram);
    }

    /** 研修プログラムを論理削除（非アクティブ化） */
    public void deactivate(Long id) {
        TrainingProgram program = trainingProgramRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("指定された研修プログラムが存在しません: " + id));
        program.setIsActive(false);
        trainingProgramRepository.save(program);
    }

    /** IDで研修プログラムを削除 */
    public void deleteById(Long id) {
        trainingProgramRepository.deleteById(id);
    }

    /** 研修プログラムを物理削除 */
    public void delete(Long id) {
        if (!trainingProgramRepository.existsById(id)) {
            throw new IllegalArgumentException("指定された研修プログラムが存在しません: " + id);
        }
        trainingProgramRepository.deleteById(id);
    }

    /** 企業の研修プログラム数を取得 */
    @Transactional(readOnly = true)
    public long countByCompanyId(Long companyId) {
        return trainingProgramRepository.countByCompanyId(companyId);
    }

    /** 研修プログラムのバリデーション */
    private void validateTrainingProgram(TrainingProgram trainingProgram) {
        if (trainingProgram == null) {
            throw new IllegalArgumentException("研修プログラムが null です");
        }
        if (trainingProgram.getProgramName() == null || trainingProgram.getProgramName().trim().isEmpty()) {
            throw new IllegalArgumentException("研修プログラム名は必須です");
        }
        if (trainingProgram.getDurationMonths() != null && trainingProgram.getDurationMonths() <= 0) {
            throw new IllegalArgumentException("期間月数は正の数である必要があります");
        }
        if (trainingProgram.getTotalHours() != null && trainingProgram.getTotalHours() < 0) {
            throw new IllegalArgumentException("総学習時間は0以上である必要があります");
        }
        if (trainingProgram.getMaxStudents() != null && trainingProgram.getMaxStudents() <= 0) {
            throw new IllegalArgumentException("最大受講者数は正の数である必要があります");
        }
    }
}
