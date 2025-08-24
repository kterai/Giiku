package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.Instructor;
import jp.co.apsa.giiku.domain.repository.InstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

/**
 * Service class for managing Instructor entities.
 * Provides comprehensive CRUD operations and specialized instructor management.
 *
 * @author 株式会社アプサ
 * @version 1.0
 * @since 2025
 */
@Service
@Transactional
public class InstructorService {

    private static final Logger logger = LoggerFactory.getLogger(InstructorService.class);

    @Autowired
    private InstructorRepository instructorRepository;

    /**
     * Retrieve all Instructor entities.
     *
     * @return List of all Instructor entities
     */
    @Transactional(readOnly = true)
    public List<Instructor> findAll() {
        logger.debug("Finding all Instructor entities");
        List<Instructor> instructors = instructorRepository.findAll();
        logger.info("Found {} Instructor entities", instructors.size());
        return instructors;
    }

    /**
     * Find Instructor by ID.
     *
     * @param id The ID to search for
     * @return Optional containing the Instructor if found
     */
    @Transactional(readOnly = true)
    public Optional<Instructor> findById(Long id) {
        if (id == null) {
            logger.warn("Attempted to find Instructor with null ID");
            return Optional.empty();
        }

        logger.debug("Finding Instructor with ID: {}", id);
        Optional<Instructor> instructor = instructorRepository.findById(id);

        if (instructor.isPresent()) {
            logger.info("Found Instructor with ID: {}", id);
        } else {
            logger.warn("Instructor not found with ID: {}", id);
        }

        return instructor;
    }

    /**
     * Find Instructor by User ID.
     *
     * @param userId The User ID to search for
     * @return Optional containing the Instructor if found
     */
    @Transactional(readOnly = true)
    public Optional<Instructor> findByUserId(Long userId) {
        if (userId == null) {
            logger.warn("Attempted to find Instructor with null User ID");
            return Optional.empty();
        }

        logger.debug("Finding Instructor with User ID: {}", userId);
        Optional<Instructor> instructor = instructorRepository.findByUserId(userId);

        if (instructor.isPresent()) {
            logger.info("Found Instructor with User ID: {}", userId);
        } else {
            logger.warn("Instructor not found with User ID: {}", userId);
        }

        return instructor;
    }

    /**
     * Find Instructor by instructor number.
     *
     * @param instructorNumber The instructor number to search for
     * @return Optional containing the Instructor if found
     */
    @Transactional(readOnly = true)
    public Optional<Instructor> findByInstructorNumber(String instructorNumber) {
        if (!StringUtils.hasText(instructorNumber)) {
            logger.warn("Attempted to find Instructor with empty instructor number");
            return Optional.empty();
        }

        logger.debug("Finding Instructor with number: {}", instructorNumber);
        Optional<Instructor> instructor = instructorRepository.findByInstructorNumber(instructorNumber);

        if (instructor.isPresent()) {
            logger.info("Found Instructor with number: {}", instructorNumber);
        } else {
            logger.warn("Instructor not found with number: {}", instructorNumber);
        }

        return instructor;
    }

    /**
     * Find all Instructors by department ID.
     *
     * @param departmentId The department ID to search for
     * @return List of Instructors in the specified department
     */
    @Transactional(readOnly = true)
    public List<Instructor> findByDepartmentId(Long departmentId) {
        if (departmentId == null) {
            logger.warn("Attempted to find Instructors with null department ID");
            return List.of();
        }

        logger.debug("Finding Instructors for department ID: {}", departmentId);
        List<Instructor> instructors = instructorRepository.findByDepartmentId(departmentId);
        logger.info("Found {} Instructors for department ID: {}", instructors.size(), departmentId);
        return instructors;
    }

    /**
     * Find all active Instructors.
     *
     * @return List of active Instructors
     */
    @Transactional(readOnly = true)
    public List<Instructor> findActiveInstructors() {
        logger.debug("Finding all active Instructors");
        List<Instructor> instructors = instructorRepository.findByInstructorStatus("ACTIVE");
        logger.info("Found {} active Instructors", instructors.size());
        return instructors;
    }

    /**
     * Find Instructors by specialization.
     *
     * @param specialization The specialization to search for
     * @return List of Instructors with matching specialization
     */
    @Transactional(readOnly = true)
    public List<Instructor> findBySpecialization(String specialization) {
        if (!StringUtils.hasText(specialization)) {
            logger.warn("Attempted to find Instructors with empty specialization");
            return List.of();
        }

        logger.debug("Finding Instructors with specialization: {}", specialization);
        List<Instructor> instructors = instructorRepository.findBySpecializationContaining(specialization);
        logger.info("Found {} Instructors with specialization: {}", instructors.size(), specialization);
        return instructors;
    }

    /**
     * Find Instructors by instructor level.
     *
     * @param level The instructor level to search for
     * @return List of Instructors with the specified level
     */
    @Transactional(readOnly = true)
    public List<Instructor> findByInstructorLevel(Integer level) {
        if (level == null) {
            logger.warn("Attempted to find Instructors with null level");
            return List.of();
        }

        logger.debug("Finding Instructors with level: {}", level);
        List<Instructor> instructors = instructorRepository.findByInstructorLevel(level);
        logger.info("Found {} Instructors with level: {}", instructors.size(), level);
        return instructors;
    }

    /**
     * Find high-rated Instructors.
     *
     * @param minRating Minimum rating score
     * @param minRatingCount Minimum number of ratings
     * @return List of high-rated Instructors
     */
    @Transactional(readOnly = true)
    public List<Instructor> findHighRatedInstructors(Double minRating, Integer minRatingCount) {
        if (minRating == null || minRatingCount == null) {
            logger.warn("Attempted to find high-rated Instructors with null parameters");
            return List.of();
        }

        logger.debug("Finding high-rated Instructors with min rating: {} and min count: {}", minRating, minRatingCount);
        List<Instructor> instructors = instructorRepository.findHighRatedInstructors(minRating, minRatingCount);
        logger.info("Found {} high-rated Instructors", instructors.size());
        return instructors;
    }

    /**
     * Find experienced Instructors.
     *
     * @param minLevel Minimum instructor level
     * @param minTeachingMinutes Minimum teaching minutes
     * @return List of experienced Instructors
     */
    @Transactional(readOnly = true)
    public List<Instructor> findExperiencedInstructors(Integer minLevel, Integer minTeachingMinutes) {
        if (minLevel == null || minTeachingMinutes == null) {
            logger.warn("Attempted to find experienced Instructors with null parameters");
            return List.of();
        }

        logger.debug("Finding experienced Instructors with min level: {} and min minutes: {}", minLevel, minTeachingMinutes);
        List<Instructor> instructors = instructorRepository.findExperiencedInstructors(minLevel, minTeachingMinutes);
        logger.info("Found {} experienced Instructors", instructors.size());
        return instructors;
    }

    /**
     * Find specialized Instructors.
     *
     * @param specialization The specialization to search for
     * @param minLevel Minimum instructor level
     * @return List of specialized Instructors
     */
    @Transactional(readOnly = true)
    public List<Instructor> findSpecializedInstructors(String specialization, Integer minLevel) {
        if (!StringUtils.hasText(specialization) || minLevel == null) {
            logger.warn("Attempted to find specialized Instructors with invalid parameters");
            return List.of();
        }

        logger.debug("Finding specialized Instructors in: {} with min level: {}", specialization, minLevel);
        List<Instructor> instructors = instructorRepository.findSpecializedInstructors(specialization, minLevel);
        logger.info("Found {} specialized Instructors", instructors.size());
        return instructors;
    }

    /**
     * ページング付きで全講師を取得します。
     *
     * @param pageable ページング情報
     * @return ページングされた講師一覧
     */
    @Transactional(readOnly = true)
    public Page<Instructor> getAllInstructors(Pageable pageable) {
        return instructorRepository.findAll(pageable);
    }

    /**
     * IDから講師を取得します。
     *
     * @param id 講師ID
     * @return 講師情報
     */
    @Transactional(readOnly = true)
    public Instructor getInstructorById(Long id) {
        return findById(id).orElse(null);
    }

    /**
     * 講師を新規作成します。
     *
     * @param instructor 作成する講師
     * @return 保存された講師
     */
    public Instructor createInstructor(Instructor instructor) {
        return save(instructor);
    }

    /**
     * 講師を更新します。
     *
     * @param instructor 更新対象の講師
     * @return 更新された講師
     */
    public Instructor updateInstructor(Instructor instructor) {
        if (instructor.getId() == null) {
            throw new RuntimeException("Instructor ID cannot be null");
        }
        return update(instructor.getId(), instructor);
    }

    /**
     * 講師に評価を追加します。
     *
     * @param instructorId 講師ID
     * @param rating 評価値
     */
    public void addRating(Long instructorId, double rating) {
        Instructor instructor = instructorRepository.findById(instructorId)
            .orElseThrow(() -> new RuntimeException("Instructor not found with ID: " + instructorId));
        instructor.addRating(rating);
        instructorRepository.save(instructor);
    }

    /**
     * Save a new Instructor entity.
     *
     * @param instructor The Instructor entity to save
     * @return The saved Instructor entity
     * @throws RuntimeException if validation fails or save operation fails
     */
    public Instructor save(Instructor instructor) {
        if (instructor == null) {
            logger.error("Attempted to save null Instructor");
            throw new RuntimeException("Instructor cannot be null");
        }

        logger.debug("Saving new Instructor for user ID: {}", instructor.getUserId());

        // Validate required fields
        validateInstructor(instructor);

        // Set default values if not provided
        if (instructor.getInstructorLevel() == null) {
            instructor.setInstructorLevel(1);
        }
        if (instructor.getAssignedCoursesCount() == null) {
            instructor.setAssignedCoursesCount(0);
        }
        if (instructor.getAssignedStudentsCount() == null) {
            instructor.setAssignedStudentsCount(0);
        }
        if (instructor.getTotalTeachingMinutes() == null) {
            instructor.setTotalTeachingMinutes(0);
        }
        if (instructor.getRatingScore() == null) {
            instructor.setRatingScore(BigDecimal.ZERO);
        }
        if (instructor.getRatingCount() == null) {
            instructor.setRatingCount(0);
        }
        if (!StringUtils.hasText(instructor.getInstructorStatus())) {
            instructor.setInstructorStatus("ACTIVE");
        }
        if (instructor.getProfileUpdatedAt() == null) {
            instructor.setProfileUpdatedAt(LocalDateTime.now());
        }

        try {
            Instructor savedInstructor = instructorRepository.save(instructor);
            logger.info("Successfully saved Instructor with ID: {} for user: {}", 
                       savedInstructor.getInstructorId(), savedInstructor.getUserId());
            return savedInstructor;
        } catch (Exception e) {
            logger.error("Failed to save Instructor for user ID: {}", instructor.getUserId(), e);
            throw new RuntimeException("Failed to save Instructor: " + e.getMessage(), e);
        }
    }

    /**
     * Update an existing Instructor entity.
     *
     * @param id The ID of the Instructor to update
     * @param updatedInstructor The updated Instructor data
     * @return The updated Instructor entity
     * @throws RuntimeException if the entity is not found or update fails
     */
    public Instructor update(Long id, Instructor updatedInstructor) {
        if (id == null) {
            logger.error("Attempted to update Instructor with null ID");
            throw new RuntimeException("Instructor ID cannot be null");
        }

        if (updatedInstructor == null) {
            logger.error("Attempted to update Instructor with null data");
            throw new RuntimeException("Updated Instructor data cannot be null");
        }

        logger.debug("Updating Instructor with ID: {}", id);

        Optional<Instructor> existingInstructorOpt = instructorRepository.findById(id);
        if (!existingInstructorOpt.isPresent()) {
            logger.error("Instructor not found for update with ID: {}", id);
            throw new RuntimeException("Instructor not found with ID: " + id);
        }

        Instructor existingInstructor = existingInstructorOpt.get();

        // Update fields while preserving ID and creation timestamp
        existingInstructor.setUserId(updatedInstructor.getUserId());
        existingInstructor.setInstructorNumber(updatedInstructor.getInstructorNumber());
        existingInstructor.setDepartmentId(updatedInstructor.getDepartmentId());
        existingInstructor.setCertificationDate(updatedInstructor.getCertificationDate());
        existingInstructor.setSpecialization(updatedInstructor.getSpecialization());
        existingInstructor.setInstructorLevel(updatedInstructor.getInstructorLevel());
        existingInstructor.setBio(updatedInstructor.getBio());
        existingInstructor.setInstructorStatus(updatedInstructor.getInstructorStatus());
        existingInstructor.setAvailability(updatedInstructor.getAvailability());
        existingInstructor.setProfileUpdatedAt(LocalDateTime.now());

        // Validate updated instructor
        validateInstructor(existingInstructor);

        try {
            Instructor savedInstructor = instructorRepository.save(existingInstructor);
            logger.info("Successfully updated Instructor with ID: {}", id);
            return savedInstructor;
        } catch (Exception e) {
            logger.error("Failed to update Instructor with ID: {}", id, e);
            throw new RuntimeException("Failed to update Instructor: " + e.getMessage(), e);
        }
    }

    /**
     * Update instructor statistics.
     *
     * @param instructorId The instructor ID
     * @param coursesCount New assigned courses count
     * @param studentsCount New assigned students count
     * @param teachingMinutes Additional teaching minutes
     * @return The updated Instructor entity
     */
    public Instructor updateStatistics(Long instructorId, Integer coursesCount, Integer studentsCount, Integer teachingMinutes) {
        if (instructorId == null) {
            logger.error("Attempted to update statistics with null instructor ID");
            throw new RuntimeException("Instructor ID cannot be null");
        }

        logger.debug("Updating statistics for Instructor ID: {}", instructorId);

        Optional<Instructor> instructorOpt = instructorRepository.findById(instructorId);
        if (!instructorOpt.isPresent()) {
            logger.error("Instructor not found for statistics update with ID: {}", instructorId);
            throw new RuntimeException("Instructor not found with ID: " + instructorId);
        }

        Instructor instructor = instructorOpt.get();

        if (coursesCount != null) {
            instructor.setAssignedCoursesCount(coursesCount);
        }
        if (studentsCount != null) {
            instructor.setAssignedStudentsCount(studentsCount);
        }
        if (teachingMinutes != null) {
            instructor.setTotalTeachingMinutes(instructor.getTotalTeachingMinutes() + teachingMinutes);
        }
        instructor.setLastTeachingDate(LocalDateTime.now());
        instructor.setProfileUpdatedAt(LocalDateTime.now());

        try {
            Instructor savedInstructor = instructorRepository.save(instructor);
            logger.info("Successfully updated statistics for Instructor ID: {}", instructorId);
            return savedInstructor;
        } catch (Exception e) {
            logger.error("Failed to update statistics for Instructor ID: {}", instructorId, e);
            throw new RuntimeException("Failed to update instructor statistics: " + e.getMessage(), e);
        }
    }

    /**
     * Update instructor rating.
     *
     * @param instructorId The instructor ID
     * @param newRating The new rating score
     * @return The updated Instructor entity
     */
    public Instructor updateRating(Long instructorId, Double newRating) {
        if (instructorId == null || newRating == null) {
            logger.error("Attempted to update rating with null parameters");
            throw new RuntimeException("Instructor ID and rating cannot be null");
        }

        if (newRating < 0.0 || newRating > 5.0) {
            logger.error("Invalid rating score: {}", newRating);
            throw new RuntimeException("Rating must be between 0.0 and 5.0");
        }

        logger.debug("Updating rating for Instructor ID: {} to {}", instructorId, newRating);

        Optional<Instructor> instructorOpt = instructorRepository.findById(instructorId);
        if (!instructorOpt.isPresent()) {
            logger.error("Instructor not found for rating update with ID: {}", instructorId);
            throw new RuntimeException("Instructor not found with ID: " + instructorId);
        }

        Instructor instructor = instructorOpt.get();

        // Calculate new average rating
        BigDecimal currentRating = instructor.getRatingScore() != null ? instructor.getRatingScore() : BigDecimal.ZERO;
        Integer currentCount = instructor.getRatingCount() != null ? instructor.getRatingCount() : 0;

        BigDecimal newAverageRating = currentRating.multiply(BigDecimal.valueOf(currentCount))
            .add(BigDecimal.valueOf(newRating))
            .divide(BigDecimal.valueOf(currentCount + 1), 2, RoundingMode.HALF_UP);

        instructor.setRatingScore(newAverageRating);
        instructor.setRatingCount(currentCount + 1);
        instructor.setProfileUpdatedAt(LocalDateTime.now());

        try {
            Instructor savedInstructor = instructorRepository.save(instructor);
            logger.info("Successfully updated rating for Instructor ID: {} to {}", instructorId, newAverageRating);
            return savedInstructor;
        } catch (Exception e) {
            logger.error("Failed to update rating for Instructor ID: {}", instructorId, e);
            throw new RuntimeException("Failed to update instructor rating: " + e.getMessage(), e);
        }
    }

    /**
     * Delete an Instructor entity by ID.
     *
     * @param id The ID of the Instructor to delete
     * @throws RuntimeException if the entity is not found or deletion fails
     */
    public void delete(Long id) {
        if (id == null) {
            logger.error("Attempted to delete Instructor with null ID");
            throw new RuntimeException("Instructor ID cannot be null");
        }

        logger.debug("Deleting Instructor with ID: {}", id);

        if (!instructorRepository.existsById(id)) {
            logger.error("Instructor not found for deletion with ID: {}", id);
            throw new RuntimeException("Instructor not found with ID: " + id);
        }

        try {
            instructorRepository.deleteById(id);
            logger.info("Successfully deleted Instructor with ID: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete Instructor with ID: {}", id, e);
            throw new RuntimeException("Failed to delete Instructor: " + e.getMessage(), e);
        }
    }

    /**
     * Get instructor statistics for a department.
     *
     * @param departmentId The department ID to get statistics for
     * @return Map containing instructor statistics
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getDepartmentInstructorStats(Long departmentId) {
        if (departmentId == null) {
            logger.warn("Attempted to get instructor stats with null department ID");
            return new HashMap<>();
        }

        logger.debug("Getting instructor statistics for department ID: {}", departmentId);

        List<Instructor> allInstructors = instructorRepository.findByDepartmentId(departmentId);
        List<Instructor> activeInstructors = instructorRepository.findByDepartmentIdAndInstructorStatus(departmentId, "ACTIVE");

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalInstructors", allInstructors.size());
        stats.put("activeInstructors", activeInstructors.size());
        stats.put("inactiveInstructors", allInstructors.size() - activeInstructors.size());
        stats.put("departmentId", departmentId);
        stats.put("generatedAt", LocalDateTime.now());

        // Calculate average statistics for active instructors
        if (!activeInstructors.isEmpty()) {
            double avgRating = activeInstructors.stream()
                .mapToDouble(i -> i.getRatingScore() != null ? i.getRatingScore().doubleValue() : 0.0)
                .average().orElse(0.0);
            double avgLevel = activeInstructors.stream()
                .mapToInt(i -> i.getInstructorLevel() != null ? i.getInstructorLevel() : 1)
                .average().orElse(1.0);

            stats.put("averageRating", Math.round(avgRating * 100.0) / 100.0);
            stats.put("averageLevel", Math.round(avgLevel * 100.0) / 100.0);
        } else {
            stats.put("averageRating", 0.0);
            stats.put("averageLevel", 0.0);
        }

        logger.info("Generated instructor statistics for department ID: {} - Total: {}, Active: {}", 
                   departmentId, allInstructors.size(), activeInstructors.size());

        return stats;
    }

    /**
     * Validate Instructor entity data.
     *
     * @param instructor The Instructor entity to validate
     * @throws RuntimeException if validation fails
     */
    private void validateInstructor(Instructor instructor) {
        if (instructor.getUserId() == null) {
            logger.error("Validation failed: Instructor user ID is required");
            throw new RuntimeException("User ID is required");
        }

        if (!StringUtils.hasText(instructor.getInstructorNumber())) {
            logger.error("Validation failed: Instructor number is required");
            throw new RuntimeException("Instructor number is required");
        }

        if (instructor.getInstructorLevel() != null && 
            (instructor.getInstructorLevel() < 1 || instructor.getInstructorLevel() > 5)) {
            logger.error("Validation failed: Invalid instructor level: {}", instructor.getInstructorLevel());
            throw new RuntimeException("Instructor level must be between 1 and 5");
        }

        logger.debug("Instructor validation passed for user ID: {}", instructor.getUserId());
    }
}
