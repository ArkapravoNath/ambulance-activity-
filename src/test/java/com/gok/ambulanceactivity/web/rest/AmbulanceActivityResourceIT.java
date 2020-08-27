package com.gok.ambulanceactivity.web.rest;

import com.gok.ambulanceactivity.AmbulanceactivityApp;
import com.gok.ambulanceactivity.config.TestSecurityConfiguration;
import com.gok.ambulanceactivity.domain.AmbulanceActivity;
import com.gok.ambulanceactivity.repository.AmbulanceActivityRepository;
import com.gok.ambulanceactivity.service.AmbulanceActivityService;
import com.gok.ambulanceactivity.service.dto.AmbulanceActivityDTO;
import com.gok.ambulanceactivity.service.mapper.AmbulanceActivityMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AmbulanceActivityResource} REST controller.
 */
@SpringBootTest(classes = { AmbulanceactivityApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AmbulanceActivityResourceIT {

    private static final String DEFAULT_AMBULANCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_AMBULANCE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_AMBULANCE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_AMBULANCE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DRIVER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DRIVER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DRIVER_PRIMARY_TELEPHONE_NO = "AAAAAAAAAA";
    private static final String UPDATED_DRIVER_PRIMARY_TELEPHONE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_DRIVER_SECONDARY_TELEPHONE_NO = "AAAAAAAAAA";
    private static final String UPDATED_DRIVER_SECONDARY_TELEPHONE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGIN_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION_ADDRESS = "BBBBBBBBBB";

    private static final Instant DEFAULT_PICKUP_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PICKUP_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DROP_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DROP_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_FACILITY_PROVIDER_ID_REF = 1L;
    private static final Long UPDATED_FACILITY_PROVIDER_ID_REF = 2L;

    private static final Long DEFAULT_AMBULANCE_SERVICE_ID_REF = 1L;
    private static final Long UPDATED_AMBULANCE_SERVICE_ID_REF = 2L;

    private static final Long DEFAULT_PATIENT_ACTIVITY_ID_REF = 1L;
    private static final Long UPDATED_PATIENT_ACTIVITY_ID_REF = 2L;

    private static final Long DEFAULT_PERSON_ACTIVITY_ID_REF = 1L;
    private static final Long UPDATED_PERSON_ACTIVITY_ID_REF = 2L;

    @Autowired
    private AmbulanceActivityRepository ambulanceActivityRepository;

    @Autowired
    private AmbulanceActivityMapper ambulanceActivityMapper;

    @Autowired
    private AmbulanceActivityService ambulanceActivityService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAmbulanceActivityMockMvc;

    private AmbulanceActivity ambulanceActivity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmbulanceActivity createEntity(EntityManager em) {
        AmbulanceActivity ambulanceActivity = new AmbulanceActivity()
            .ambulanceNumber(DEFAULT_AMBULANCE_NUMBER)
            .ambulanceType(DEFAULT_AMBULANCE_TYPE)
            .driverName(DEFAULT_DRIVER_NAME)
            .driverPrimaryTelephoneNo(DEFAULT_DRIVER_PRIMARY_TELEPHONE_NO)
            .driverSecondaryTelephoneNo(DEFAULT_DRIVER_SECONDARY_TELEPHONE_NO)
            .profession(DEFAULT_PROFESSION)
            .originAddress(DEFAULT_ORIGIN_ADDRESS)
            .destinationAddress(DEFAULT_DESTINATION_ADDRESS)
            .pickupDateTime(DEFAULT_PICKUP_DATE_TIME)
            .dropDateTime(DEFAULT_DROP_DATE_TIME)
            .facilityProviderIdRef(DEFAULT_FACILITY_PROVIDER_ID_REF)
            .ambulanceServiceIdRef(DEFAULT_AMBULANCE_SERVICE_ID_REF)
            .patientActivityIdRef(DEFAULT_PATIENT_ACTIVITY_ID_REF)
            .personActivityIdRef(DEFAULT_PERSON_ACTIVITY_ID_REF);
        return ambulanceActivity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmbulanceActivity createUpdatedEntity(EntityManager em) {
        AmbulanceActivity ambulanceActivity = new AmbulanceActivity()
            .ambulanceNumber(UPDATED_AMBULANCE_NUMBER)
            .ambulanceType(UPDATED_AMBULANCE_TYPE)
            .driverName(UPDATED_DRIVER_NAME)
            .driverPrimaryTelephoneNo(UPDATED_DRIVER_PRIMARY_TELEPHONE_NO)
            .driverSecondaryTelephoneNo(UPDATED_DRIVER_SECONDARY_TELEPHONE_NO)
            .profession(UPDATED_PROFESSION)
            .originAddress(UPDATED_ORIGIN_ADDRESS)
            .destinationAddress(UPDATED_DESTINATION_ADDRESS)
            .pickupDateTime(UPDATED_PICKUP_DATE_TIME)
            .dropDateTime(UPDATED_DROP_DATE_TIME)
            .facilityProviderIdRef(UPDATED_FACILITY_PROVIDER_ID_REF)
            .ambulanceServiceIdRef(UPDATED_AMBULANCE_SERVICE_ID_REF)
            .patientActivityIdRef(UPDATED_PATIENT_ACTIVITY_ID_REF)
            .personActivityIdRef(UPDATED_PERSON_ACTIVITY_ID_REF);
        return ambulanceActivity;
    }

    @BeforeEach
    public void initTest() {
        ambulanceActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmbulanceActivity() throws Exception {
        int databaseSizeBeforeCreate = ambulanceActivityRepository.findAll().size();
        // Create the AmbulanceActivity
        AmbulanceActivityDTO ambulanceActivityDTO = ambulanceActivityMapper.toDto(ambulanceActivity);
        restAmbulanceActivityMockMvc.perform(post("/api/ambulance-activities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ambulanceActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the AmbulanceActivity in the database
        List<AmbulanceActivity> ambulanceActivityList = ambulanceActivityRepository.findAll();
        assertThat(ambulanceActivityList).hasSize(databaseSizeBeforeCreate + 1);
        AmbulanceActivity testAmbulanceActivity = ambulanceActivityList.get(ambulanceActivityList.size() - 1);
        assertThat(testAmbulanceActivity.getAmbulanceNumber()).isEqualTo(DEFAULT_AMBULANCE_NUMBER);
        assertThat(testAmbulanceActivity.getAmbulanceType()).isEqualTo(DEFAULT_AMBULANCE_TYPE);
        assertThat(testAmbulanceActivity.getDriverName()).isEqualTo(DEFAULT_DRIVER_NAME);
        assertThat(testAmbulanceActivity.getDriverPrimaryTelephoneNo()).isEqualTo(DEFAULT_DRIVER_PRIMARY_TELEPHONE_NO);
        assertThat(testAmbulanceActivity.getDriverSecondaryTelephoneNo()).isEqualTo(DEFAULT_DRIVER_SECONDARY_TELEPHONE_NO);
        assertThat(testAmbulanceActivity.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testAmbulanceActivity.getOriginAddress()).isEqualTo(DEFAULT_ORIGIN_ADDRESS);
        assertThat(testAmbulanceActivity.getDestinationAddress()).isEqualTo(DEFAULT_DESTINATION_ADDRESS);
        assertThat(testAmbulanceActivity.getPickupDateTime()).isEqualTo(DEFAULT_PICKUP_DATE_TIME);
        assertThat(testAmbulanceActivity.getDropDateTime()).isEqualTo(DEFAULT_DROP_DATE_TIME);
        assertThat(testAmbulanceActivity.getFacilityProviderIdRef()).isEqualTo(DEFAULT_FACILITY_PROVIDER_ID_REF);
        assertThat(testAmbulanceActivity.getAmbulanceServiceIdRef()).isEqualTo(DEFAULT_AMBULANCE_SERVICE_ID_REF);
        assertThat(testAmbulanceActivity.getPatientActivityIdRef()).isEqualTo(DEFAULT_PATIENT_ACTIVITY_ID_REF);
        assertThat(testAmbulanceActivity.getPersonActivityIdRef()).isEqualTo(DEFAULT_PERSON_ACTIVITY_ID_REF);
    }

    @Test
    @Transactional
    public void createAmbulanceActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ambulanceActivityRepository.findAll().size();

        // Create the AmbulanceActivity with an existing ID
        ambulanceActivity.setId(1L);
        AmbulanceActivityDTO ambulanceActivityDTO = ambulanceActivityMapper.toDto(ambulanceActivity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmbulanceActivityMockMvc.perform(post("/api/ambulance-activities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ambulanceActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmbulanceActivity in the database
        List<AmbulanceActivity> ambulanceActivityList = ambulanceActivityRepository.findAll();
        assertThat(ambulanceActivityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAmbulanceActivities() throws Exception {
        // Initialize the database
        ambulanceActivityRepository.saveAndFlush(ambulanceActivity);

        // Get all the ambulanceActivityList
        restAmbulanceActivityMockMvc.perform(get("/api/ambulance-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ambulanceActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].ambulanceNumber").value(hasItem(DEFAULT_AMBULANCE_NUMBER)))
            .andExpect(jsonPath("$.[*].ambulanceType").value(hasItem(DEFAULT_AMBULANCE_TYPE)))
            .andExpect(jsonPath("$.[*].driverName").value(hasItem(DEFAULT_DRIVER_NAME)))
            .andExpect(jsonPath("$.[*].driverPrimaryTelephoneNo").value(hasItem(DEFAULT_DRIVER_PRIMARY_TELEPHONE_NO)))
            .andExpect(jsonPath("$.[*].driverSecondaryTelephoneNo").value(hasItem(DEFAULT_DRIVER_SECONDARY_TELEPHONE_NO)))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].originAddress").value(hasItem(DEFAULT_ORIGIN_ADDRESS)))
            .andExpect(jsonPath("$.[*].destinationAddress").value(hasItem(DEFAULT_DESTINATION_ADDRESS)))
            .andExpect(jsonPath("$.[*].pickupDateTime").value(hasItem(DEFAULT_PICKUP_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].dropDateTime").value(hasItem(DEFAULT_DROP_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].facilityProviderIdRef").value(hasItem(DEFAULT_FACILITY_PROVIDER_ID_REF.intValue())))
            .andExpect(jsonPath("$.[*].ambulanceServiceIdRef").value(hasItem(DEFAULT_AMBULANCE_SERVICE_ID_REF.intValue())))
            .andExpect(jsonPath("$.[*].patientActivityIdRef").value(hasItem(DEFAULT_PATIENT_ACTIVITY_ID_REF.intValue())))
            .andExpect(jsonPath("$.[*].personActivityIdRef").value(hasItem(DEFAULT_PERSON_ACTIVITY_ID_REF.intValue())));
    }
    
    @Test
    @Transactional
    public void getAmbulanceActivity() throws Exception {
        // Initialize the database
        ambulanceActivityRepository.saveAndFlush(ambulanceActivity);

        // Get the ambulanceActivity
        restAmbulanceActivityMockMvc.perform(get("/api/ambulance-activities/{id}", ambulanceActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ambulanceActivity.getId().intValue()))
            .andExpect(jsonPath("$.ambulanceNumber").value(DEFAULT_AMBULANCE_NUMBER))
            .andExpect(jsonPath("$.ambulanceType").value(DEFAULT_AMBULANCE_TYPE))
            .andExpect(jsonPath("$.driverName").value(DEFAULT_DRIVER_NAME))
            .andExpect(jsonPath("$.driverPrimaryTelephoneNo").value(DEFAULT_DRIVER_PRIMARY_TELEPHONE_NO))
            .andExpect(jsonPath("$.driverSecondaryTelephoneNo").value(DEFAULT_DRIVER_SECONDARY_TELEPHONE_NO))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION))
            .andExpect(jsonPath("$.originAddress").value(DEFAULT_ORIGIN_ADDRESS))
            .andExpect(jsonPath("$.destinationAddress").value(DEFAULT_DESTINATION_ADDRESS))
            .andExpect(jsonPath("$.pickupDateTime").value(DEFAULT_PICKUP_DATE_TIME.toString()))
            .andExpect(jsonPath("$.dropDateTime").value(DEFAULT_DROP_DATE_TIME.toString()))
            .andExpect(jsonPath("$.facilityProviderIdRef").value(DEFAULT_FACILITY_PROVIDER_ID_REF.intValue()))
            .andExpect(jsonPath("$.ambulanceServiceIdRef").value(DEFAULT_AMBULANCE_SERVICE_ID_REF.intValue()))
            .andExpect(jsonPath("$.patientActivityIdRef").value(DEFAULT_PATIENT_ACTIVITY_ID_REF.intValue()))
            .andExpect(jsonPath("$.personActivityIdRef").value(DEFAULT_PERSON_ACTIVITY_ID_REF.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAmbulanceActivity() throws Exception {
        // Get the ambulanceActivity
        restAmbulanceActivityMockMvc.perform(get("/api/ambulance-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmbulanceActivity() throws Exception {
        // Initialize the database
        ambulanceActivityRepository.saveAndFlush(ambulanceActivity);

        int databaseSizeBeforeUpdate = ambulanceActivityRepository.findAll().size();

        // Update the ambulanceActivity
        AmbulanceActivity updatedAmbulanceActivity = ambulanceActivityRepository.findById(ambulanceActivity.getId()).get();
        // Disconnect from session so that the updates on updatedAmbulanceActivity are not directly saved in db
        em.detach(updatedAmbulanceActivity);
        updatedAmbulanceActivity
            .ambulanceNumber(UPDATED_AMBULANCE_NUMBER)
            .ambulanceType(UPDATED_AMBULANCE_TYPE)
            .driverName(UPDATED_DRIVER_NAME)
            .driverPrimaryTelephoneNo(UPDATED_DRIVER_PRIMARY_TELEPHONE_NO)
            .driverSecondaryTelephoneNo(UPDATED_DRIVER_SECONDARY_TELEPHONE_NO)
            .profession(UPDATED_PROFESSION)
            .originAddress(UPDATED_ORIGIN_ADDRESS)
            .destinationAddress(UPDATED_DESTINATION_ADDRESS)
            .pickupDateTime(UPDATED_PICKUP_DATE_TIME)
            .dropDateTime(UPDATED_DROP_DATE_TIME)
            .facilityProviderIdRef(UPDATED_FACILITY_PROVIDER_ID_REF)
            .ambulanceServiceIdRef(UPDATED_AMBULANCE_SERVICE_ID_REF)
            .patientActivityIdRef(UPDATED_PATIENT_ACTIVITY_ID_REF)
            .personActivityIdRef(UPDATED_PERSON_ACTIVITY_ID_REF);
        AmbulanceActivityDTO ambulanceActivityDTO = ambulanceActivityMapper.toDto(updatedAmbulanceActivity);

        restAmbulanceActivityMockMvc.perform(put("/api/ambulance-activities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ambulanceActivityDTO)))
            .andExpect(status().isOk());

        // Validate the AmbulanceActivity in the database
        List<AmbulanceActivity> ambulanceActivityList = ambulanceActivityRepository.findAll();
        assertThat(ambulanceActivityList).hasSize(databaseSizeBeforeUpdate);
        AmbulanceActivity testAmbulanceActivity = ambulanceActivityList.get(ambulanceActivityList.size() - 1);
        assertThat(testAmbulanceActivity.getAmbulanceNumber()).isEqualTo(UPDATED_AMBULANCE_NUMBER);
        assertThat(testAmbulanceActivity.getAmbulanceType()).isEqualTo(UPDATED_AMBULANCE_TYPE);
        assertThat(testAmbulanceActivity.getDriverName()).isEqualTo(UPDATED_DRIVER_NAME);
        assertThat(testAmbulanceActivity.getDriverPrimaryTelephoneNo()).isEqualTo(UPDATED_DRIVER_PRIMARY_TELEPHONE_NO);
        assertThat(testAmbulanceActivity.getDriverSecondaryTelephoneNo()).isEqualTo(UPDATED_DRIVER_SECONDARY_TELEPHONE_NO);
        assertThat(testAmbulanceActivity.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testAmbulanceActivity.getOriginAddress()).isEqualTo(UPDATED_ORIGIN_ADDRESS);
        assertThat(testAmbulanceActivity.getDestinationAddress()).isEqualTo(UPDATED_DESTINATION_ADDRESS);
        assertThat(testAmbulanceActivity.getPickupDateTime()).isEqualTo(UPDATED_PICKUP_DATE_TIME);
        assertThat(testAmbulanceActivity.getDropDateTime()).isEqualTo(UPDATED_DROP_DATE_TIME);
        assertThat(testAmbulanceActivity.getFacilityProviderIdRef()).isEqualTo(UPDATED_FACILITY_PROVIDER_ID_REF);
        assertThat(testAmbulanceActivity.getAmbulanceServiceIdRef()).isEqualTo(UPDATED_AMBULANCE_SERVICE_ID_REF);
        assertThat(testAmbulanceActivity.getPatientActivityIdRef()).isEqualTo(UPDATED_PATIENT_ACTIVITY_ID_REF);
        assertThat(testAmbulanceActivity.getPersonActivityIdRef()).isEqualTo(UPDATED_PERSON_ACTIVITY_ID_REF);
    }

    @Test
    @Transactional
    public void updateNonExistingAmbulanceActivity() throws Exception {
        int databaseSizeBeforeUpdate = ambulanceActivityRepository.findAll().size();

        // Create the AmbulanceActivity
        AmbulanceActivityDTO ambulanceActivityDTO = ambulanceActivityMapper.toDto(ambulanceActivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmbulanceActivityMockMvc.perform(put("/api/ambulance-activities").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ambulanceActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmbulanceActivity in the database
        List<AmbulanceActivity> ambulanceActivityList = ambulanceActivityRepository.findAll();
        assertThat(ambulanceActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAmbulanceActivity() throws Exception {
        // Initialize the database
        ambulanceActivityRepository.saveAndFlush(ambulanceActivity);

        int databaseSizeBeforeDelete = ambulanceActivityRepository.findAll().size();

        // Delete the ambulanceActivity
        restAmbulanceActivityMockMvc.perform(delete("/api/ambulance-activities/{id}", ambulanceActivity.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AmbulanceActivity> ambulanceActivityList = ambulanceActivityRepository.findAll();
        assertThat(ambulanceActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
