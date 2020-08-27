package com.gok.ambulanceactivity.service.impl;

import com.gok.ambulanceactivity.service.AmbulanceActivityService;
import com.gok.ambulanceactivity.domain.AmbulanceActivity;
import com.gok.ambulanceactivity.repository.AmbulanceActivityRepository;
import com.gok.ambulanceactivity.service.dto.AmbulanceActivityDTO;
import com.gok.ambulanceactivity.service.mapper.AmbulanceActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AmbulanceActivity}.
 */
@Service
@Transactional
public class AmbulanceActivityServiceImpl implements AmbulanceActivityService {

    private final Logger log = LoggerFactory.getLogger(AmbulanceActivityServiceImpl.class);

    private final AmbulanceActivityRepository ambulanceActivityRepository;

    private final AmbulanceActivityMapper ambulanceActivityMapper;

    public AmbulanceActivityServiceImpl(AmbulanceActivityRepository ambulanceActivityRepository, AmbulanceActivityMapper ambulanceActivityMapper) {
        this.ambulanceActivityRepository = ambulanceActivityRepository;
        this.ambulanceActivityMapper = ambulanceActivityMapper;
    }

    @Override
    public List<AmbulanceActivityDTO> save(List<AmbulanceActivityDTO> ambulanceActivityDTO) {
        log.debug("Request to save AmbulanceActivity : {}", ambulanceActivityDTO);
        List<AmbulanceActivity> ambulanceActivity = ambulanceActivityMapper.toEntity(ambulanceActivityDTO);
        ambulanceActivity = ambulanceActivityRepository.saveAll(ambulanceActivity);
        return ambulanceActivityMapper.toDto(ambulanceActivity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AmbulanceActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AmbulanceActivities");
        return ambulanceActivityRepository.findAll(pageable)
            .map(ambulanceActivityMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AmbulanceActivityDTO> findOne(Long id) {
        log.debug("Request to get AmbulanceActivity : {}", id);
        return ambulanceActivityRepository.findById(id)
            .map(ambulanceActivityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AmbulanceActivity : {}", id);
        ambulanceActivityRepository.deleteById(id);
    }
}
