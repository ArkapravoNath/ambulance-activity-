package com.gok.ambulanceactivity.service;

import com.gok.ambulanceactivity.service.dto.AmbulanceActivityDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.gok.ambulanceactivity.domain.AmbulanceActivity}.
 */
public interface AmbulanceActivityService {

    /**
     * Save a ambulanceActivity.
     *
     * @param ambulanceActivityDTO the entity to save.
     * @return the persisted entity.
     */
    List<AmbulanceActivityDTO> save(List<AmbulanceActivityDTO> ambulanceActivityDTO);

    /**
     * Get all the ambulanceActivities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AmbulanceActivityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ambulanceActivity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AmbulanceActivityDTO> findOne(Long id);

    /**
     * Delete the "id" ambulanceActivity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
