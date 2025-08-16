package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.ProgramSchedule;
import jp.co.apsa.giiku.domain.entity.TrainingProgram;
import jp.co.apsa.giiku.domain.repository.ProgramScheduleRepository;
import jp.co.apsa.giiku.domain.repository.TrainingProgramRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ProgramSchedule（プログラムスケジュール）に関するビジネスロジックを提供するサービスクラス
 * 
 * @author Giiku LMS Team
 * @version 1.0
 * @since 2025-08-14
 */
@Service
@Transactional
public class ProgramScheduleService {

    @Autowired
    private ProgramScheduleRepository programScheduleRepository;

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    /**
     * 全てのプログラムスケジュールを取得
     * 
     * @return プログラムスケジュールのリスト
     */
    @Transactional(readOnly = true)
    public List<ProgramSchedule> findAll() {
        return programScheduleRepository.findAll();
    }

    /**
     * IDでプログラムスケジュールを取得
     * 
     * @param id プログラムスケジュールID
     * @return Optional<ProgramSchedule>
     */
    @Transactional(readOnly = true)
    public Optional<ProgramSchedule> findById(Long id) {
        return programScheduleRepository.findById(id);
    }

    /**
     * 研修プログラムIDでスケジュールを取得
     * 
     * @param trainingProgramId 研修プログラムID
     * @return プログラムスケジュールのリスト
     */
    @Transactional(readOnly = true)
    public List<ProgramSchedule> findByTrainingProgramId(Long trainingProgramId) {
        return programScheduleRepository.findByTrainingProgramIdOrderByScheduleDateAsc(trainingProgramId);
    }

    /**
     * 期間内のプログラムスケジュールを取得
     * 
     * @param startDate 開始日
     * @param endDate 終了日
     * @return プログラムスケジュールのリスト
     */
    @Transactional(readOnly = true)
    public List<ProgramSchedule> findSchedulesWithinPeriod(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        return programScheduleRepository.findByScheduleDateBetween(startDateTime, endDateTime);
    }

    /**
     * 今日のプログラムスケジュールを取得
     * 
     * @return 今日のプログラムスケジュールのリスト
     */
    @Transactional(readOnly = true)
    public List<ProgramSchedule> findTodaySchedules() {
        LocalDate today = LocalDate.now();
        return findSchedulesWithinPeriod(today, today);
    }

    /**
     * 今週のプログラムスケジュールを取得
     * 
     * @return 今週のプログラムスケジュールのリスト
     */
    @Transactional(readOnly = true)
    public List<ProgramSchedule> findThisWeekSchedules() {
        LocalDate startOfWeek = LocalDate.now().with(java.time.DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return findSchedulesWithinPeriod(startOfWeek, endOfWeek);
    }

    /**
     * 複合条件でプログラムスケジュールを検索
     * 
     * @param trainingProgramId 研修プログラムID（オプション）
     * @param startDate 開始日（オプション）
     * @param endDate 終了日（オプション）
     * @param status ステータス（オプション）
     * @param pageable ページング情報
     * @return ページング対応のプログラムスケジュール
     */
    @Transactional(readOnly = true)
    public Page<ProgramSchedule> searchSchedules(Long trainingProgramId, LocalDate startDate, 
                                               LocalDate endDate, String status, Pageable pageable) {
        Specification<ProgramSchedule> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (trainingProgramId != null) {
                predicates.add(criteriaBuilder.equal(root.get("trainingProgramId"), trainingProgramId));
            }

            if (startDate != null) {
                LocalDateTime startDateTime = startDate.atStartOfDay();
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("scheduleDate"), startDateTime));
            }

            if (endDate != null) {
                LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("scheduleDate"), endDateTime));
            }

            if (status != null && !status.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return programScheduleRepository.findAll(spec, pageable);
    }

    /**
     * プログラムスケジュールを作成
     * 
     * @param programSchedule プログラムスケジュール
     * @return 保存されたプログラムスケジュール
     * @throws IllegalArgumentException バリデーションエラー
     */
    public ProgramSchedule save(ProgramSchedule programSchedule) {
        validateProgramSchedule(programSchedule);

        // 研修プログラム存在チェック
        if (programSchedule.getTrainingProgramId() != null) {
            Optional<TrainingProgram> trainingProgram = trainingProgramRepository.findById(programSchedule.getTrainingProgramId());
            if (!trainingProgram.isPresent()) {
                throw new IllegalArgumentException("指定された研修プログラムが存在しません: " + programSchedule.getTrainingProgramId());
            }
        }

        return programScheduleRepository.save(programSchedule);
    }

    /**
     * プログラムスケジュールを更新
     * 
     * @param id プログラムスケジュールID
     * @param programSchedule 更新するプログラムスケジュール
     * @return 更新されたプログラムスケジュール
     * @throws IllegalArgumentException IDが存在しない場合
     */
    public ProgramSchedule update(Long id, ProgramSchedule programSchedule) {
        Optional<ProgramSchedule> existingSchedule = programScheduleRepository.findById(id);
        if (!existingSchedule.isPresent()) {
            throw new IllegalArgumentException("指定されたプログラムスケジュールが存在しません: " + id);
        }

        programSchedule.setId(id);
        validateProgramSchedule(programSchedule);

        return programScheduleRepository.save(programSchedule);
    }

    /**
     * プログラムスケジュールを削除
     * 
     * @param id プログラムスケジュールID
     * @throws IllegalArgumentException IDが存在しない場合
     */
    public void delete(Long id) {
        if (!programScheduleRepository.existsById(id)) {
            throw new IllegalArgumentException("指定されたプログラムスケジュールが存在しません: " + id);
        }
        programScheduleRepository.deleteById(id);
    }

    /**
     * スケジュールのステータスを更新
     * 
     * @param id プログラムスケジュールID
     * @param status 新しいステータス
     * @throws IllegalArgumentException IDが存在しない場合
     */
    public void updateStatus(Long id, String status) {
        Optional<ProgramSchedule> programSchedule = programScheduleRepository.findById(id);
        if (!programSchedule.isPresent()) {
            throw new IllegalArgumentException("指定されたプログラムスケジュールが存在しません: " + id);
        }

        ProgramSchedule schedule = programSchedule.get();
        schedule.setStatus(status);
        programScheduleRepository.save(schedule);
    }

    /**
     * 研修プログラムのスケジュール数を取得
     * 
     * @param trainingProgramId 研修プログラムID
     * @return スケジュール数
     */
    @Transactional(readOnly = true)
    public long countByTrainingProgramId(Long trainingProgramId) {
        return programScheduleRepository.countByTrainingProgramId(trainingProgramId);
    }

    /**
     * 完了済みスケジュール数を取得
     * 
     * @param trainingProgramId 研修プログラムID
     * @return 完了済みスケジュール数
     */
    @Transactional(readOnly = true)
    public long countCompletedSchedules(Long trainingProgramId) {
        return programScheduleRepository.countByTrainingProgramIdAndStatus(trainingProgramId, "COMPLETED");
    }

    /**
     * プログラムスケジュールのバリデーション
     * 
     * @param programSchedule 検証対象のプログラムスケジュール
     * @throws IllegalArgumentException バリデーションエラー
     */
    private void validateProgramSchedule(ProgramSchedule programSchedule) {
        if (programSchedule == null) {
            throw new IllegalArgumentException("プログラムスケジュールが null です");
        }

        if (programSchedule.getTrainingProgramId() == null) {
            throw new IllegalArgumentException("研修プログラムIDは必須です");
        }

        if (programSchedule.getScheduleDate() == null) {
            throw new IllegalArgumentException("スケジュール日時は必須です");
        }

        if (programSchedule.getTitle() == null || programSchedule.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("スケジュールタイトルは必須です");
        }

        if (programSchedule.getDurationMinutes() != null && programSchedule.getDurationMinutes() <= 0) {
            throw new IllegalArgumentException("実施時間は正の数である必要があります");
        }
    }
}