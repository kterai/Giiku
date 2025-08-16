package jp.co.apsa.giiku.service;

import jp.co.apsa.giiku.domain.entity.CompanyLmsConfig;
import jp.co.apsa.giiku.domain.repository.CompanyLmsConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

/**
 * Service class for managing CompanyLmsConfig entities.
 * Provides comprehensive CRUD operations and specialized LMS configuration management.
 * 
 * @author Giiku System
 * @version 1.0
 * @since 2024-01-01
 */
@Service
@Transactional
public class CompanyLmsConfigService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyLmsConfigService.class);

    @Autowired
    private CompanyLmsConfigRepository companyLmsConfigRepository;

    /**
     * Retrieve all CompanyLmsConfig entities.
     * 
     * @return List of all CompanyLmsConfig entities
     */
    @Transactional(readOnly = true)
    public List<CompanyLmsConfig> findAll() {
        logger.debug("Finding all CompanyLmsConfig entities");
        List<CompanyLmsConfig> configs = companyLmsConfigRepository.findAll();
        logger.info("Found {} CompanyLmsConfig entities", configs.size());
        return configs;
    }

    /**
     * Find CompanyLmsConfig by ID.
     * 
     * @param id The ID to search for
     * @return Optional containing the CompanyLmsConfig if found
     */
    @Transactional(readOnly = true)
    public Optional<CompanyLmsConfig> findById(Long id) {
        if (id == null) {
            logger.warn("Attempted to find CompanyLmsConfig with null ID");
            return Optional.empty();
        }

        logger.debug("Finding CompanyLmsConfig with ID: {}", id);
        Optional<CompanyLmsConfig> config = companyLmsConfigRepository.findById(id);

        if (config.isPresent()) {
            logger.info("Found CompanyLmsConfig with ID: {}", id);
        } else {
            logger.warn("CompanyLmsConfig not found with ID: {}", id);
        }

        return config;
    }

    /**
     * Find all CompanyLmsConfig entities by company ID.
     * 
     * @param companyId The company ID to search for
     * @return List of CompanyLmsConfig entities for the specified company
     */
    @Transactional(readOnly = true)
    public List<CompanyLmsConfig> findByCompanyId(Long companyId) {
        if (companyId == null) {
            logger.warn("Attempted to find CompanyLmsConfig with null company ID");
            return List.of();
        }

        logger.debug("Finding CompanyLmsConfig entities for company ID: {}", companyId);
        List<CompanyLmsConfig> configs = companyLmsConfigRepository.findByCompanyId(companyId);
        logger.info("Found {} CompanyLmsConfig entities for company ID: {}", configs.size(), companyId);
        return configs;
    }

    /**
     * Find all active CompanyLmsConfig entities.
     * 
     * @return List of active CompanyLmsConfig entities
     */
    @Transactional(readOnly = true)
    public List<CompanyLmsConfig> findActiveConfigs() {
        logger.debug("Finding all active CompanyLmsConfig entities");
        List<CompanyLmsConfig> configs = companyLmsConfigRepository.findByActiveTrue();
        logger.info("Found {} active CompanyLmsConfig entities", configs.size());
        return configs;
    }

    /**
     * Find active CompanyLmsConfig by company ID.
     * 
     * @param companyId The company ID to search for
     * @return Optional containing the active CompanyLmsConfig if found
     */
    @Transactional(readOnly = true)
    public Optional<CompanyLmsConfig> findActiveByCompanyId(Long companyId) {
        if (companyId == null) {
            logger.warn("Attempted to find active CompanyLmsConfig with null company ID");
            return Optional.empty();
        }

        logger.debug("Finding active CompanyLmsConfig for company ID: {}", companyId);
        Optional<CompanyLmsConfig> config = companyLmsConfigRepository.findByCompanyIdAndActiveTrue(companyId);

        if (config.isPresent()) {
            logger.info("Found active CompanyLmsConfig for company ID: {}", companyId);
        } else {
            logger.warn("No active CompanyLmsConfig found for company ID: {}", companyId);
        }

        return config;
    }

    /**
     * Save a new CompanyLmsConfig entity.
     * 
     * @param companyLmsConfig The CompanyLmsConfig entity to save
     * @return The saved CompanyLmsConfig entity
     * @throws RuntimeException if validation fails or save operation fails
     */
    public CompanyLmsConfig save(CompanyLmsConfig companyLmsConfig) {
        if (companyLmsConfig == null) {
            logger.error("Attempted to save null CompanyLmsConfig");
            throw new RuntimeException("CompanyLmsConfig cannot be null");
        }

        logger.debug("Saving new CompanyLmsConfig for company ID: {}", companyLmsConfig.getCompanyId());

        // Validate required fields
        validateCompanyLmsConfig(companyLmsConfig);

        // Set creation timestamp
        if (companyLmsConfig.getCreatedAt() == null) {
            companyLmsConfig.setCreatedAt(LocalDateTime.now());
        }
        companyLmsConfig.setUpdatedAt(LocalDateTime.now());

        try {
            CompanyLmsConfig savedConfig = companyLmsConfigRepository.save(companyLmsConfig);
            logger.info("Successfully saved CompanyLmsConfig with ID: {} for company: {}", 
                       savedConfig.getId(), savedConfig.getCompanyId());
            return savedConfig;
        } catch (Exception e) {
            logger.error("Failed to save CompanyLmsConfig for company ID: {}", 
                        companyLmsConfig.getCompanyId(), e);
            throw new RuntimeException("Failed to save CompanyLmsConfig: " + e.getMessage(), e);
        }
    }

    /**
     * Update an existing CompanyLmsConfig entity.
     * 
     * @param id The ID of the CompanyLmsConfig to update
     * @param updatedConfig The updated CompanyLmsConfig data
     * @return The updated CompanyLmsConfig entity
     * @throws RuntimeException if the entity is not found or update fails
     */
    public CompanyLmsConfig update(Long id, CompanyLmsConfig updatedConfig) {
        if (id == null) {
            logger.error("Attempted to update CompanyLmsConfig with null ID");
            throw new RuntimeException("CompanyLmsConfig ID cannot be null");
        }

        if (updatedConfig == null) {
            logger.error("Attempted to update CompanyLmsConfig with null data");
            throw new RuntimeException("Updated CompanyLmsConfig data cannot be null");
        }

        logger.debug("Updating CompanyLmsConfig with ID: {}", id);

        Optional<CompanyLmsConfig> existingConfigOpt = companyLmsConfigRepository.findById(id);
        if (!existingConfigOpt.isPresent()) {
            logger.error("CompanyLmsConfig not found for update with ID: {}", id);
            throw new RuntimeException("CompanyLmsConfig not found with ID: " + id);
        }

        CompanyLmsConfig existingConfig = existingConfigOpt.get();

        // Update fields while preserving ID and creation timestamp
        existingConfig.setCompanyId(updatedConfig.getCompanyId());
        existingConfig.setConfigName(updatedConfig.getConfigName());
        existingConfig.setConfigValue(updatedConfig.getConfigValue());
        existingConfig.setConfigType(updatedConfig.getConfigType());
        existingConfig.setDescription(updatedConfig.getDescription());
        existingConfig.setActive(updatedConfig.getActive());
        existingConfig.setUpdatedAt(LocalDateTime.now());

        // Validate updated configuration
        validateCompanyLmsConfig(existingConfig);

        try {
            CompanyLmsConfig savedConfig = companyLmsConfigRepository.save(existingConfig);
            logger.info("Successfully updated CompanyLmsConfig with ID: {}", id);
            return savedConfig;
        } catch (Exception e) {
            logger.error("Failed to update CompanyLmsConfig with ID: {}", id, e);
            throw new RuntimeException("Failed to update CompanyLmsConfig: " + e.getMessage(), e);
        }
    }

    /**
     * Delete a CompanyLmsConfig entity by ID.
     * 
     * @param id The ID of the CompanyLmsConfig to delete
     * @throws RuntimeException if the entity is not found or deletion fails
     */
    public void delete(Long id) {
        if (id == null) {
            logger.error("Attempted to delete CompanyLmsConfig with null ID");
            throw new RuntimeException("CompanyLmsConfig ID cannot be null");
        }

        logger.debug("Deleting CompanyLmsConfig with ID: {}", id);

        if (!companyLmsConfigRepository.existsById(id)) {
            logger.error("CompanyLmsConfig not found for deletion with ID: {}", id);
            throw new RuntimeException("CompanyLmsConfig not found with ID: " + id);
        }

        try {
            companyLmsConfigRepository.deleteById(id);
            logger.info("Successfully deleted CompanyLmsConfig with ID: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete CompanyLmsConfig with ID: {}", id, e);
            throw new RuntimeException("Failed to delete CompanyLmsConfig: " + e.getMessage(), e);
        }
    }

    /**
     * Activate a CompanyLmsConfig by setting active flag to true.
     * 
     * @param id The ID of the CompanyLmsConfig to activate
     * @return The activated CompanyLmsConfig entity
     * @throws RuntimeException if the entity is not found or activation fails
     */
    public CompanyLmsConfig activate(Long id) {
        if (id == null) {
            logger.error("Attempted to activate CompanyLmsConfig with null ID");
            throw new RuntimeException("CompanyLmsConfig ID cannot be null");
        }

        logger.debug("Activating CompanyLmsConfig with ID: {}", id);

        Optional<CompanyLmsConfig> configOpt = companyLmsConfigRepository.findById(id);
        if (!configOpt.isPresent()) {
            logger.error("CompanyLmsConfig not found for activation with ID: {}", id);
            throw new RuntimeException("CompanyLmsConfig not found with ID: " + id);
        }

        CompanyLmsConfig config = configOpt.get();
        config.setActive(true);
        config.setUpdatedAt(LocalDateTime.now());

        try {
            CompanyLmsConfig savedConfig = companyLmsConfigRepository.save(config);
            logger.info("Successfully activated CompanyLmsConfig with ID: {}", id);
            return savedConfig;
        } catch (Exception e) {
            logger.error("Failed to activate CompanyLmsConfig with ID: {}", id, e);
            throw new RuntimeException("Failed to activate CompanyLmsConfig: " + e.getMessage(), e);
        }
    }

    /**
     * Deactivate a CompanyLmsConfig by setting active flag to false.
     * 
     * @param id The ID of the CompanyLmsConfig to deactivate
     * @return The deactivated CompanyLmsConfig entity
     * @throws RuntimeException if the entity is not found or deactivation fails
     */
    public CompanyLmsConfig deactivate(Long id) {
        if (id == null) {
            logger.error("Attempted to deactivate CompanyLmsConfig with null ID");
            throw new RuntimeException("CompanyLmsConfig ID cannot be null");
        }

        logger.debug("Deactivating CompanyLmsConfig with ID: {}", id);

        Optional<CompanyLmsConfig> configOpt = companyLmsConfigRepository.findById(id);
        if (!configOpt.isPresent()) {
            logger.error("CompanyLmsConfig not found for deactivation with ID: {}", id);
            throw new RuntimeException("CompanyLmsConfig not found with ID: " + id);
        }

        CompanyLmsConfig config = configOpt.get();
        config.setActive(false);
        config.setUpdatedAt(LocalDateTime.now());

        try {
            CompanyLmsConfig savedConfig = companyLmsConfigRepository.save(config);
            logger.info("Successfully deactivated CompanyLmsConfig with ID: {}", id);
            return savedConfig;
        } catch (Exception e) {
            logger.error("Failed to deactivate CompanyLmsConfig with ID: {}", id, e);
            throw new RuntimeException("Failed to deactivate CompanyLmsConfig: " + e.getMessage(), e);
        }
    }

    /**
     * Get configuration statistics for a specific company.
     * 
     * @param companyId The company ID to get statistics for
     * @return Map containing configuration statistics
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getConfigurationStats(Long companyId) {
        if (companyId == null) {
            logger.warn("Attempted to get configuration stats with null company ID");
            return new HashMap<>();
        }

        logger.debug("Getting configuration statistics for company ID: {}", companyId);

        List<CompanyLmsConfig> allConfigs = companyLmsConfigRepository.findByCompanyId(companyId);
        List<CompanyLmsConfig> activeConfigs = companyLmsConfigRepository.findByCompanyIdAndActiveTrue(companyId)
                                                                        .map(List::of)
                                                                        .orElse(List.of());

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalConfigs", allConfigs.size());
        stats.put("activeConfigs", activeConfigs.size());
        stats.put("inactiveConfigs", allConfigs.size() - activeConfigs.size());
        stats.put("companyId", companyId);
        stats.put("generatedAt", LocalDateTime.now());

        logger.info("Generated configuration statistics for company ID: {} - Total: {}, Active: {}", 
                   companyId, allConfigs.size(), activeConfigs.size());

        return stats;
    }

    /**
     * Duplicate a CompanyLmsConfig for another company.
     * 
     * @param configId The ID of the configuration to duplicate
     * @param targetCompanyId The ID of the target company
     * @return The duplicated CompanyLmsConfig entity
     * @throws RuntimeException if the source config is not found or duplication fails
     */
    public CompanyLmsConfig duplicate(Long configId, Long targetCompanyId) {
        if (configId == null) {
            logger.error("Attempted to duplicate CompanyLmsConfig with null config ID");
            throw new RuntimeException("Source CompanyLmsConfig ID cannot be null");
        }

        if (targetCompanyId == null) {
            logger.error("Attempted to duplicate CompanyLmsConfig with null target company ID");
            throw new RuntimeException("Target company ID cannot be null");
        }

        logger.debug("Duplicating CompanyLmsConfig ID: {} for target company: {}", configId, targetCompanyId);

        Optional<CompanyLmsConfig> sourceConfigOpt = companyLmsConfigRepository.findById(configId);
        if (!sourceConfigOpt.isPresent()) {
            logger.error("Source CompanyLmsConfig not found for duplication with ID: {}", configId);
            throw new RuntimeException("Source CompanyLmsConfig not found with ID: " + configId);
        }

        CompanyLmsConfig sourceConfig = sourceConfigOpt.get();

        // Create new configuration based on source
        CompanyLmsConfig duplicatedConfig = new CompanyLmsConfig();
        duplicatedConfig.setCompanyId(targetCompanyId);
        duplicatedConfig.setConfigName(sourceConfig.getConfigName() + " (Copy)");
        duplicatedConfig.setConfigValue(sourceConfig.getConfigValue());
        duplicatedConfig.setConfigType(sourceConfig.getConfigType());
        duplicatedConfig.setDescription("Duplicated from company " + sourceConfig.getCompanyId() + ": " + 
                                       sourceConfig.getDescription());
        duplicatedConfig.setActive(false); // Start as inactive for safety
        duplicatedConfig.setCreatedAt(LocalDateTime.now());
        duplicatedConfig.setUpdatedAt(LocalDateTime.now());

        try {
            CompanyLmsConfig savedConfig = companyLmsConfigRepository.save(duplicatedConfig);
            logger.info("Successfully duplicated CompanyLmsConfig ID: {} to new ID: {} for company: {}", 
                       configId, savedConfig.getId(), targetCompanyId);
            return savedConfig;
        } catch (Exception e) {
            logger.error("Failed to duplicate CompanyLmsConfig ID: {} for target company: {}", 
                        configId, targetCompanyId, e);
            throw new RuntimeException("Failed to duplicate CompanyLmsConfig: " + e.getMessage(), e);
        }
    }

    /**
     * Validate CompanyLmsConfig entity data.
     * 
     * @param config The CompanyLmsConfig entity to validate
     * @throws RuntimeException if validation fails
     */
    private void validateCompanyLmsConfig(CompanyLmsConfig config) {
        if (config.getCompanyId() == null) {
            logger.error("Validation failed: CompanyLmsConfig company ID is required");
            throw new RuntimeException("Company ID is required");
        }

        if (!StringUtils.hasText(config.getConfigName())) {
            logger.error("Validation failed: CompanyLmsConfig config name is required");
            throw new RuntimeException("Configuration name is required");
        }

        if (!StringUtils.hasText(config.getConfigValue())) {
            logger.error("Validation failed: CompanyLmsConfig config value is required");
            throw new RuntimeException("Configuration value is required");
        }

        if (!StringUtils.hasText(config.getConfigType())) {
            logger.error("Validation failed: CompanyLmsConfig config type is required");
            throw new RuntimeException("Configuration type is required");
        }

        logger.debug("CompanyLmsConfig validation passed for company ID: {}", config.getCompanyId());
    }
}