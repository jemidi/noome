package de.dittje.web.rest;

import de.dittje.NooMeApp;
import de.dittje.domain.Serving;
import de.dittje.repository.ServingRepository;
import de.dittje.repository.search.ServingSearchRepository;
import de.dittje.service.ServingService;
import de.dittje.service.dto.ServingDTO;
import de.dittje.service.mapper.ServingMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.dittje.domain.enumeration.Unit;
/**
 * Integration tests for the {@link ServingResource} REST controller.
 */
@SpringBootTest(classes = NooMeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServingResourceIT {

    private static final Float DEFAULT_QUANTITY = 1F;
    private static final Float UPDATED_QUANTITY = 2F;

    private static final Unit DEFAULT_UNIT = Unit.TSP;
    private static final Unit UPDATED_UNIT = Unit.TBSP;

    @Autowired
    private ServingRepository servingRepository;

    @Autowired
    private ServingMapper servingMapper;

    @Autowired
    private ServingService servingService;

    /**
     * This repository is mocked in the de.dittje.repository.search test package.
     *
     * @see de.dittje.repository.search.ServingSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServingSearchRepository mockServingSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServingMockMvc;

    private Serving serving;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Serving createEntity(EntityManager em) {
        Serving serving = new Serving()
            .quantity(DEFAULT_QUANTITY)
            .unit(DEFAULT_UNIT);
        return serving;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Serving createUpdatedEntity(EntityManager em) {
        Serving serving = new Serving()
            .quantity(UPDATED_QUANTITY)
            .unit(UPDATED_UNIT);
        return serving;
    }

    @BeforeEach
    public void initTest() {
        serving = createEntity(em);
    }

    @Test
    @Transactional
    public void createServing() throws Exception {
        int databaseSizeBeforeCreate = servingRepository.findAll().size();
        // Create the Serving
        ServingDTO servingDTO = servingMapper.toDto(serving);
        restServingMockMvc.perform(post("/api/servings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servingDTO)))
            .andExpect(status().isCreated());

        // Validate the Serving in the database
        List<Serving> servingList = servingRepository.findAll();
        assertThat(servingList).hasSize(databaseSizeBeforeCreate + 1);
        Serving testServing = servingList.get(servingList.size() - 1);
        assertThat(testServing.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testServing.getUnit()).isEqualTo(DEFAULT_UNIT);

        // Validate the Serving in Elasticsearch
        verify(mockServingSearchRepository, times(1)).save(testServing);
    }

    @Test
    @Transactional
    public void createServingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servingRepository.findAll().size();

        // Create the Serving with an existing ID
        serving.setId(1L);
        ServingDTO servingDTO = servingMapper.toDto(serving);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServingMockMvc.perform(post("/api/servings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Serving in the database
        List<Serving> servingList = servingRepository.findAll();
        assertThat(servingList).hasSize(databaseSizeBeforeCreate);

        // Validate the Serving in Elasticsearch
        verify(mockServingSearchRepository, times(0)).save(serving);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = servingRepository.findAll().size();
        // set the field null
        serving.setQuantity(null);

        // Create the Serving, which fails.
        ServingDTO servingDTO = servingMapper.toDto(serving);


        restServingMockMvc.perform(post("/api/servings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servingDTO)))
            .andExpect(status().isBadRequest());

        List<Serving> servingList = servingRepository.findAll();
        assertThat(servingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = servingRepository.findAll().size();
        // set the field null
        serving.setUnit(null);

        // Create the Serving, which fails.
        ServingDTO servingDTO = servingMapper.toDto(serving);


        restServingMockMvc.perform(post("/api/servings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servingDTO)))
            .andExpect(status().isBadRequest());

        List<Serving> servingList = servingRepository.findAll();
        assertThat(servingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServings() throws Exception {
        // Initialize the database
        servingRepository.saveAndFlush(serving);

        // Get all the servingList
        restServingMockMvc.perform(get("/api/servings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serving.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())));
    }
    
    @Test
    @Transactional
    public void getServing() throws Exception {
        // Initialize the database
        servingRepository.saveAndFlush(serving);

        // Get the serving
        restServingMockMvc.perform(get("/api/servings/{id}", serving.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serving.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingServing() throws Exception {
        // Get the serving
        restServingMockMvc.perform(get("/api/servings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServing() throws Exception {
        // Initialize the database
        servingRepository.saveAndFlush(serving);

        int databaseSizeBeforeUpdate = servingRepository.findAll().size();

        // Update the serving
        Serving updatedServing = servingRepository.findById(serving.getId()).get();
        // Disconnect from session so that the updates on updatedServing are not directly saved in db
        em.detach(updatedServing);
        updatedServing
            .quantity(UPDATED_QUANTITY)
            .unit(UPDATED_UNIT);
        ServingDTO servingDTO = servingMapper.toDto(updatedServing);

        restServingMockMvc.perform(put("/api/servings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servingDTO)))
            .andExpect(status().isOk());

        // Validate the Serving in the database
        List<Serving> servingList = servingRepository.findAll();
        assertThat(servingList).hasSize(databaseSizeBeforeUpdate);
        Serving testServing = servingList.get(servingList.size() - 1);
        assertThat(testServing.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testServing.getUnit()).isEqualTo(UPDATED_UNIT);

        // Validate the Serving in Elasticsearch
        verify(mockServingSearchRepository, times(1)).save(testServing);
    }

    @Test
    @Transactional
    public void updateNonExistingServing() throws Exception {
        int databaseSizeBeforeUpdate = servingRepository.findAll().size();

        // Create the Serving
        ServingDTO servingDTO = servingMapper.toDto(serving);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServingMockMvc.perform(put("/api/servings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Serving in the database
        List<Serving> servingList = servingRepository.findAll();
        assertThat(servingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Serving in Elasticsearch
        verify(mockServingSearchRepository, times(0)).save(serving);
    }

    @Test
    @Transactional
    public void deleteServing() throws Exception {
        // Initialize the database
        servingRepository.saveAndFlush(serving);

        int databaseSizeBeforeDelete = servingRepository.findAll().size();

        // Delete the serving
        restServingMockMvc.perform(delete("/api/servings/{id}", serving.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Serving> servingList = servingRepository.findAll();
        assertThat(servingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Serving in Elasticsearch
        verify(mockServingSearchRepository, times(1)).deleteById(serving.getId());
    }

    @Test
    @Transactional
    public void searchServing() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        servingRepository.saveAndFlush(serving);
        when(mockServingSearchRepository.search(queryStringQuery("id:" + serving.getId())))
            .thenReturn(Collections.singletonList(serving));

        // Search the serving
        restServingMockMvc.perform(get("/api/_search/servings?query=id:" + serving.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serving.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())));
    }
}
