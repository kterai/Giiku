package jp.co.apsa.giiku.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import jp.co.apsa.giiku.domain.entity.ProgramSchedule;
import jp.co.apsa.giiku.domain.entity.TrainingProgram;
import jp.co.apsa.giiku.domain.repository.ProgramScheduleRepository;
import jp.co.apsa.giiku.domain.repository.TrainingProgramRepository;
import jp.co.apsa.giiku.dto.ProgramScheduleCreateDto;
import jp.co.apsa.giiku.dto.ProgramScheduleResponseDto;
import jp.co.apsa.giiku.dto.ProgramScheduleSearchDto;
import jp.co.apsa.giiku.dto.ProgramScheduleStatsDto;
import jp.co.apsa.giiku.dto.ProgramScheduleUpdateDto;

/**
 * ProgramSchedule（プログラムスケジュール）に関するビジネスロジックを提供するサービスクラス。
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
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
        return programScheduleRepository.findByTrainingProgramIdOrderByStartDateAsc(trainingProgramId);
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
        return programScheduleRepository.findByStartDateBetween(startDate, endDate);
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
        Specification<ProgramSchedule> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (trainingProgramId != null) {
                predicates.add(cb.equal(root.get("trainingProgramId"), trainingProgramId));
            }

            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), startDate));
            }

            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), endDate));
            }

            if (status != null && !status.trim().isEmpty()) {
                predicates.add(cb.equal(root.get("scheduleStatus"), status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
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
        schedule.setScheduleStatus(status);
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
        return programScheduleRepository.countByTrainingProgramIdAndScheduleStatus(trainingProgramId, "COMPLETED");
    }

    // ---- Additional methods for controller compatibility ----

    @Transactional(readOnly = true)
    public Page<ProgramScheduleResponseDto> getAllProgramSchedules(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Page<ProgramSchedule> schedules = programScheduleRepository.findAll(PageRequest.of(page, size, sort));
        List<ProgramScheduleResponseDto> content = schedules.map(this::toDto).getContent();
        return new PageImpl<>(content, schedules.getPageable(), schedules.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Optional<ProgramScheduleResponseDto> getProgramScheduleById(Long id) {
        return programScheduleRepository.findById(id).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public List<ProgramScheduleResponseDto> getSchedulesByProgramId(Long programId, String sortBy, String sortDir) {
        List<ProgramSchedule> list = programScheduleRepository.findByTrainingProgramIdOrderByStartDateAsc(programId);
        return list.stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Page<ProgramScheduleResponseDto> getSchedulesByPeriod(String startDate, String endDate, Long programId, int page, int size) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<ProgramSchedule> list = programScheduleRepository.findByStartDateBetween(start, end);
        List<ProgramScheduleResponseDto> content = list.stream().map(this::toDto).toList();
        return new PageImpl<>(content, PageRequest.of(page, size), content.size());
    }

    public ProgramScheduleResponseDto createProgramSchedule(ProgramScheduleCreateDto dto) {
        ProgramSchedule entity = new ProgramSchedule();
        entity.setTrainingProgramId(dto.getProgramId());
        entity.setScheduleName(dto.getTitle());
        entity.setScheduleDescription(dto.getDescription());
        entity.setStartDate(dto.getStartDateTime().toLocalDate());
        entity.setEndDate(dto.getEndDateTime().toLocalDate());
        entity.setScheduleStatus("ACTIVE");
        ProgramSchedule saved = programScheduleRepository.save(entity);
        return toDto(saved);
    }

    public Optional<ProgramScheduleResponseDto> updateProgramSchedule(Long id, ProgramScheduleUpdateDto dto) {
        Optional<ProgramSchedule> opt = programScheduleRepository.findById(id);
        if (opt.isEmpty()) {
            return Optional.empty();
        }
        ProgramSchedule entity = opt.get();
        if (dto.getTitle() != null) entity.setScheduleName(dto.getTitle());
        if (dto.getDescription() != null) entity.setScheduleDescription(dto.getDescription());
        if (dto.getStartDateTime() != null) entity.setStartDate(dto.getStartDateTime().toLocalDate());
        if (dto.getEndDateTime() != null) entity.setEndDate(dto.getEndDateTime().toLocalDate());
        ProgramSchedule saved = programScheduleRepository.save(entity);
        return Optional.of(toDto(saved));
    }

    public boolean deleteProgramSchedule(Long id) {
        if (!programScheduleRepository.existsById(id)) {
            return false;
        }
        programScheduleRepository.deleteById(id);
        return true;
    }

    public List<ProgramScheduleResponseDto> batchCreateSchedules(List<ProgramScheduleCreateDto> dtos) {
        List<ProgramScheduleResponseDto> result = new ArrayList<>();
        for (ProgramScheduleCreateDto dto : dtos) {
            result.add(createProgramSchedule(dto));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public Page<ProgramScheduleResponseDto> searchProgramSchedules(ProgramScheduleSearchDto searchDto, int page, int size, String sortBy, String sortDir) {
        return getAllProgramSchedules(page, size, sortBy, sortDir);
    }

    @Transactional(readOnly = true)
    public ProgramScheduleStatsDto getProgramScheduleStats(Long programId, String period) {
        ProgramScheduleStatsDto dto = new ProgramScheduleStatsDto();
        long total = programScheduleRepository.countByTrainingProgramId(programId);
        dto.setTotalSchedules(total);
        dto.setCompletedSchedules(programScheduleRepository.countByTrainingProgramIdAndScheduleStatus(programId, "COMPLETED"));
        if (total > 0) {
            dto.setCompletionRate(dto.getCompletedSchedules() / (double) total);
        }
        return dto;
    }

    public List<ProgramScheduleResponseDto> checkScheduleConflicts(ProgramScheduleCreateDto dto) {
        // 実際の競合チェックは未実装のため空リストを返す
        return List.of();
    }

    public ProgramScheduleResponseDto duplicateSchedule(Long id, String newStartDate, String newEndDate) {
        ProgramSchedule original = programScheduleRepository.findById(id).orElseThrow();
        ProgramSchedule copy = new ProgramSchedule();
        copy.setTrainingProgramId(original.getTrainingProgramId());
        copy.setScheduleName(original.getScheduleName());
        copy.setScheduleDescription(original.getScheduleDescription());
        copy.setScheduleStatus(original.getScheduleStatus());
        if (newStartDate != null) {
            copy.setStartDate(LocalDate.parse(newStartDate));
        }
        if (newEndDate != null) {
            copy.setEndDate(LocalDate.parse(newEndDate));
        }
        ProgramSchedule saved = programScheduleRepository.save(copy);
        return toDto(saved);
    }

    private ProgramScheduleResponseDto toDto(ProgramSchedule schedule) {
        ProgramScheduleResponseDto dto = new ProgramScheduleResponseDto();
        dto.setId(schedule.getId());
        dto.setProgramId(schedule.getTrainingProgramId());
        dto.setTitle(schedule.getScheduleName());
        dto.setDescription(schedule.getScheduleDescription());
        dto.setStartDateTime(schedule.getStartDate() != null ? schedule.getStartDate().atStartOfDay() : null);
        dto.setEndDateTime(schedule.getEndDate() != null ? schedule.getEndDate().atStartOfDay() : null);
        dto.setStatus(schedule.getScheduleStatus());
        return dto;
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

        if (programSchedule.getScheduleName() == null || programSchedule.getScheduleName().trim().isEmpty()) {
            throw new IllegalArgumentException("スケジュール名は必須です");
        }

        if (programSchedule.getStartDate() == null || programSchedule.getEndDate() == null) {
            throw new IllegalArgumentException("開始日と終了日は必須です");
        }

        if (programSchedule.getEndDate().isBefore(programSchedule.getStartDate())) {
            throw new IllegalArgumentException("終了日は開始日以降である必要があります");
        }
    }
}
