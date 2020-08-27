package com.gok.ambulanceactivity.web.rest;

import com.gok.ambulanceactivity.service.AmbulanceActivityService;
import com.gok.ambulanceactivity.web.rest.errors.BadRequestAlertException;
import com.gok.ambulanceactivity.service.dto.AmbulanceActivityDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.gok.ambulanceactivity.domain.AmbulanceActivity}.
 */
@RestController
@RequestMapping("/api")
public class AmbulanceActivityResource {

    private final Logger log = LoggerFactory.getLogger(AmbulanceActivityResource.class);

    private static final String ENTITY_NAME = "ambulanceactivityAmbulanceActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmbulanceActivityService ambulanceActivityService;

    public AmbulanceActivityResource(AmbulanceActivityService ambulanceActivityService) {
        this.ambulanceActivityService = ambulanceActivityService;
    }

    /**
     * {@code POST  /ambulance-activities} : Create a new ambulanceActivity.
     *
     * @param ambulanceActivityDTO the ambulanceActivityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ambulanceActivityDTO, or with status {@code 400 (Bad Request)} if the ambulanceActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ambulance-activities")
    public List<ResponseEntity<AmbulanceActivityDTO>> createAmbulanceActivity(@RequestBody List<AmbulanceActivityDTO> ambulanceActivityDTO) throws URISyntaxException {
        log.debug("REST request to save AmbulanceActivity : {}", ambulanceActivityDTO);
        ambulanceActivityDTO.forEach(ambulanceActivityDTO1 -> {
            if (ambulanceActivityDTO1.getId() != null) {
                throw new BadRequestAlertException("A new medicalPractitioner cannot already have an ID", ENTITY_NAME, "idexists");
            }
        });
        List<AmbulanceActivityDTO> result = ambulanceActivityService.save(ambulanceActivityDTO);
        return (List<ResponseEntity<AmbulanceActivityDTO>>) ResponseEntity.created(new URI("/api/ambulance-activities/"))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,"saved objects"))
            .body(result);
    }

    /**
     * {@code PUT  /ambulance-activities} : Updates an existing ambulanceActivity.
     *
     * @param ambulanceActivityDTO the ambulanceActivityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ambulanceActivityDTO,
     * or with status {@code 400 (Bad Request)} if the ambulanceActivityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ambulanceActivityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ambulance-activities")
    public List<ResponseEntity<AmbulanceActivityDTO>> updateAmbulanceActivity(@RequestBody List<AmbulanceActivityDTO> ambulanceActivityDTO) throws URISyntaxException {
        log.debug("REST request to update AmbulanceActivity : {}", ambulanceActivityDTO);
        ambulanceActivityDTO.forEach(ambulanceActivityDTO1 -> {
            if (ambulanceActivityDTO1.getId() != null) {
                throw new BadRequestAlertException("A new medicalPractitioner cannot already have an ID", ENTITY_NAME, "idexists");
            }
        });
        List<AmbulanceActivityDTO> result = ambulanceActivityService.save(ambulanceActivityDTO);
        return (List<ResponseEntity<AmbulanceActivityDTO>>) ResponseEntity.created(new URI("/api/ambulance-activities/"))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,"saved objects"))
            .body(result);
    }

    /**
     * {@code GET  /ambulance-activities} : get all the ambulanceActivities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ambulanceActivities in body.
     */
    @GetMapping("/ambulance-activities")
    public ResponseEntity<List<AmbulanceActivityDTO>> getAllAmbulanceActivities(Pageable pageable) {
        log.debug("REST request to get a page of AmbulanceActivities");
        Page<AmbulanceActivityDTO> page = ambulanceActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ambulance-activities/:id} : get the "id" ambulanceActivity.
     *
     * @param id the id of the ambulanceActivityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ambulanceActivityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ambulance-activities/{id}")
    public ResponseEntity<AmbulanceActivityDTO> getAmbulanceActivity(@PathVariable Long id) {
        log.debug("REST request to get AmbulanceActivity : {}", id);
        Optional<AmbulanceActivityDTO> ambulanceActivityDTO = ambulanceActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ambulanceActivityDTO);
    }

    /**
     * {@code DELETE  /ambulance-activities/:id} : delete the "id" ambulanceActivity.
     *
     * @param id the id of the ambulanceActivityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ambulance-activities/{id}")
    public ResponseEntity<Void> deleteAmbulanceActivity(@PathVariable Long id) {
        log.debug("REST request to delete AmbulanceActivity : {}", id);
        ambulanceActivityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
