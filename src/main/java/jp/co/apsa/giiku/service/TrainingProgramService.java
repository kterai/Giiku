package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.TrainingProgram;
import jp.co.apsa.giiku.domain.entity.Company;
import jp.co.apsa.giiku.domain.entity.User;
import jp.co.apsa.giiku.domain.repository.TrainingProgramRepository;
import jp.co.apsa.giiku.domain.repository.CompanyRepository;
import jp.co.apsa.giiku.domain.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageImpl;

/**
 * TrainingProgram（研修プログラム）に関するビジネスロジックを提供するサービスクラス。
 *
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

    @Autowired
    private UserRepository userRepository;

    /**
     * 全ての研修プログラムを取得
     * 
     * @return 研修プログラムのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<TrainingProgram> findAll() {
        return trainingProgramRepository.findAll();
    }

    /** キーワードで研修プログラムを検索 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Page<TrainingProgram> findAll(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return trainingProgramRepository.findAll(pageable);
        }
        Specification<TrainingProgram> spec = (root, query, cb) ->
                cb.like(cb.lower(root.get("programName")), "%" + keyword.toLowerCase() + "%");
        return trainingProgramRepository.findAll(spec, pageable);
    }

    /** ページングのみの取得 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Page<TrainingProgram> findAll(Pageable pageable) {
        return trainingProgramRepository.findAll(pageable);
    }

    /**
     * IDで研修プログラムを取得
     * 
     * @param id 研修プログラムID
     * @return Optional<TrainingProgram>
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Optional<TrainingProgram> findById(Long id) {
        return trainingProgramRepository.findById(id);
    }

    /**
     * 企業IDで研修プログラムを取得
     * 
     * @param companyId 企業ID
     * @return 研修プログラムのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<TrainingProgram> findByCompanyId(Long companyId) {
        return trainingProgramRepository.findByCompanyIdAndProgramStatus(companyId, TrainingProgram.ProgramStatus.ACTIVE);
    }

    /**
     * アクティブな研修プログラムを取得
     * 
     * @return アクティブな研修プログラムのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<TrainingProgram> findActivePrograms() {
        return trainingProgramRepository.findByProgramStatusOrderByStartDateAsc(TrainingProgram.ProgramStatus.ACTIVE);
    }

    /**
     * カテゴリで研修プログラムを検索
     * 
     * @param category カテゴリ
     * @return 研修プログラムのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<TrainingProgram> findByCategory(String category) {
        return trainingProgramRepository.findByCategoryAndProgramStatusOrderByProgramNameAsc(category, TrainingProgram.ProgramStatus.ACTIVE);
    }

    /**
     * レベルで研修プログラムを検索
     * 
     * @param level レベル
     * @return 研修プログラムのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<TrainingProgram> findByLevel(String level) {
        return trainingProgramRepository.findByLevelAndProgramStatusOrderByProgramNameAsc(level, TrainingProgram.ProgramStatus.ACTIVE);
    }

    /**
     * 複合条件で研修プログラムを検索
     * 
     * @param companyId 企業ID（オプション）
     * @param category カテゴリ（オプション）
     * @param level レベル（オプション）
     * @param instructorId 講師ID（オプション）
     * @param isActive アクティブフラグ（オプション）
     * @param pageable ページング情報
     * @return ページング対応の研修プログラム
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Page<TrainingProgram> searchPrograms(Long companyId, String category, String level,
                                              Long instructorId, Boolean isActive, Pageable pageable) {
        Specification<TrainingProgram> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (companyId != null) {
                predicates.add(criteriaBuilder.equal(root.get("companyId"), companyId));
            }

            if (category != null && !category.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
            }

            if (level != null && !level.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("level"), level));
            }

            if (isActive != null) {
                String status = isActive ? TrainingProgram.ProgramStatus.ACTIVE : TrainingProgram.ProgramStatus.INACTIVE;
                predicates.add(criteriaBuilder.equal(root.get("programStatus"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return trainingProgramRepository.findAll(spec, pageable);
    }

    /** キーワード検索のエイリアス 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public Page<TrainingProgram> searchPrograms(String keyword, Pageable pageable) {
        return findAll(keyword, pageable);
    }

    /**
     * 研修プログラムを作成
     * 
     * @param trainingProgram 研修プログラム
     * @return 保存された研修プログラム
     * @throws IllegalArgumentException バリデーションエラー
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public TrainingProgram save(TrainingProgram trainingProgram) {
        validateTrainingProgram(trainingProgram);

        // 企業存在チェック
        if (trainingProgram.getCompanyId() != null) {
            Optional<Company> company = companyRepository.findById(trainingProgram.getCompanyId());
            if (!company.isPresent()) {
                throw new IllegalArgumentException("指定された企業が存在しません: " + trainingProgram.getCompanyId());
            }
        }

        return trainingProgramRepository.save(trainingProgram);
    }

    /**
     * 研修プログラムを更新
     * 
     * @param id 研修プログラムID
     * @param trainingProgram 更新する研修プログラム
     * @return 更新された研修プログラム
     * @throws IllegalArgumentException IDが存在しない場合
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public TrainingProgram update(Long id, TrainingProgram trainingProgram) {
        Optional<TrainingProgram> existingProgram = trainingProgramRepository.findById(id);
        if (!existingProgram.isPresent()) {
            throw new IllegalArgumentException("指定された研修プログラムが存在しません: " + id);
        }

        trainingProgram.setId(id);
        validateTrainingProgram(trainingProgram);

        return trainingProgramRepository.save(trainingProgram);
    }

    /**
     * 研修プログラムを論理削除（非アクティブ化）
     * 
     * @param id 研修プログラムID
     * @throws IllegalArgumentException IDが存在しない場合
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void deactivate(Long id) {
        Optional<TrainingProgram> trainingProgram = trainingProgramRepository.findById(id);
        if (!trainingProgram.isPresent()) {
            throw new IllegalArgumentException("指定された研修プログラムが存在しません: " + id);
        }

        TrainingProgram program = trainingProgram.get();
        program.setProgramStatus(TrainingProgram.ProgramStatus.INACTIVE);
        trainingProgramRepository.save(program);
    }

    /** IDで削除（エイリアス） 
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void deleteById(Long id) {
        trainingProgramRepository.deleteById(id);
    }

    /**
     * 研修プログラムを物理削除
     * 
     * @param id 研修プログラムID
     * @throws IllegalArgumentException IDが存在しない場合
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    public void delete(Long id) {
        if (!trainingProgramRepository.existsById(id)) {
            throw new IllegalArgumentException("指定された研修プログラムが存在しません: " + id);
        }
        trainingProgramRepository.deleteById(id);
    }

    /**
     * 企業の研修プログラム数を取得
     * 
     * @param companyId 企業ID
     * @return プログラム数
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public long countByCompanyId(Long companyId) {
        return trainingProgramRepository.countByCompanyIdAndProgramStatus(companyId, TrainingProgram.ProgramStatus.ACTIVE);
    }

    /**
     * 期間内の研修プログラムを取得
     * 
     * @param startDate 開始日
     * @param endDate 終了日
     * @return 研修プログラムのリスト
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    @Transactional(readOnly = true)
    public List<TrainingProgram> findProgramsWithinPeriod(LocalDate startDate, LocalDate endDate) {
        return trainingProgramRepository.findProgramsWithinPeriod(startDate, endDate);
    }

    /**
     * 研修プログラムのバリデーション
     * 
     * @param trainingProgram 検証対象の研修プログラム
     * @throws IllegalArgumentException バリデーションエラー
     
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
    private void validateTrainingProgram(TrainingProgram trainingProgram) {
        if (trainingProgram == null) {
            throw new IllegalArgumentException("研修プログラムが null です");
        }

        if (trainingProgram.getProgramName() == null || trainingProgram.getProgramName().trim().isEmpty()) {
            throw new IllegalArgumentException("研修プログラム名は必須です");
        }

        if (trainingProgram.getStartDate() == null) {
            throw new IllegalArgumentException("開始日は必須です");
        }

        if (trainingProgram.getEndDate() == null) {
            throw new IllegalArgumentException("終了日は必須です");
        }

        if (trainingProgram.getStartDate().isAfter(trainingProgram.getEndDate())) {
            throw new IllegalArgumentException("開始日は終了日より前である必要があります");
        }

        if (trainingProgram.getMaxParticipants() != null && trainingProgram.getMaxParticipants() <= 0) {
            throw new IllegalArgumentException("最大受講者数は正の数である必要があります");
        }

        if (trainingProgram.getDurationDays() != null && trainingProgram.getDurationDays() <= 0) {
            throw new IllegalArgumentException("研修期間は正の数である必要があります");
        }
    }
}
