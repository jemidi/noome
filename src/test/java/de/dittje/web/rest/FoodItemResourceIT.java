package de.dittje.web.rest;

import de.dittje.NooMeApp;
import de.dittje.domain.FoodItem;
import de.dittje.repository.FoodItemRepository;
import de.dittje.repository.search.FoodItemSearchRepository;
import de.dittje.service.FoodItemService;
import de.dittje.service.dto.FoodItemDTO;
import de.dittje.service.mapper.FoodItemMapper;

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

/**
 * Integration tests for the {@link FoodItemResource} REST controller.
 */
@SpringBootTest(classes = NooMeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FoodItemResourceIT {

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_CALORIES = 1D;
    private static final Double UPDATED_CALORIES = 2D;

    private static final Double DEFAULT_TOTAL_FAT = 1D;
    private static final Double UPDATED_TOTAL_FAT = 2D;

    private static final Double DEFAULT_SATURATED = 1D;
    private static final Double UPDATED_SATURATED = 2D;

    private static final Double DEFAULT_POLYUNSATURATED = 1D;
    private static final Double UPDATED_POLYUNSATURATED = 2D;

    private static final Double DEFAULT_MONOUNSATURATED = 1D;
    private static final Double UPDATED_MONOUNSATURATED = 2D;

    private static final Double DEFAULT_TRANS = 1D;
    private static final Double UPDATED_TRANS = 2D;

    private static final Double DEFAULT_TOTAL_CARBS = 1D;
    private static final Double UPDATED_TOTAL_CARBS = 2D;

    private static final Double DEFAULT_FIBRE = 1D;
    private static final Double UPDATED_FIBRE = 2D;

    private static final Double DEFAULT_SUGARS = 1D;
    private static final Double UPDATED_SUGARS = 2D;

    private static final Double DEFAULT_PROTEIN = 1D;
    private static final Double UPDATED_PROTEIN = 2D;

    private static final Double DEFAULT_CHOLESTEROL = 1D;
    private static final Double UPDATED_CHOLESTEROL = 2D;

    private static final Double DEFAULT_SODIUM = 1D;
    private static final Double UPDATED_SODIUM = 2D;

    private static final Double DEFAULT_POTASSIUM = 1D;
    private static final Double UPDATED_POTASSIUM = 2D;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private FoodItemMapper foodItemMapper;

    @Autowired
    private FoodItemService foodItemService;

    /**
     * This repository is mocked in the de.dittje.repository.search test package.
     *
     * @see de.dittje.repository.search.FoodItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private FoodItemSearchRepository mockFoodItemSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFoodItemMockMvc;

    private FoodItem foodItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodItem createEntity(EntityManager em) {
        FoodItem foodItem = new FoodItem()
            .brand(DEFAULT_BRAND)
            .name(DEFAULT_NAME)
            .calories(DEFAULT_CALORIES)
            .totalFat(DEFAULT_TOTAL_FAT)
            .saturated(DEFAULT_SATURATED)
            .polyunsaturated(DEFAULT_POLYUNSATURATED)
            .monounsaturated(DEFAULT_MONOUNSATURATED)
            .trans(DEFAULT_TRANS)
            .totalCarbs(DEFAULT_TOTAL_CARBS)
            .fibre(DEFAULT_FIBRE)
            .sugars(DEFAULT_SUGARS)
            .protein(DEFAULT_PROTEIN)
            .cholesterol(DEFAULT_CHOLESTEROL)
            .sodium(DEFAULT_SODIUM)
            .potassium(DEFAULT_POTASSIUM);
        return foodItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodItem createUpdatedEntity(EntityManager em) {
        FoodItem foodItem = new FoodItem()
            .brand(UPDATED_BRAND)
            .name(UPDATED_NAME)
            .calories(UPDATED_CALORIES)
            .totalFat(UPDATED_TOTAL_FAT)
            .saturated(UPDATED_SATURATED)
            .polyunsaturated(UPDATED_POLYUNSATURATED)
            .monounsaturated(UPDATED_MONOUNSATURATED)
            .trans(UPDATED_TRANS)
            .totalCarbs(UPDATED_TOTAL_CARBS)
            .fibre(UPDATED_FIBRE)
            .sugars(UPDATED_SUGARS)
            .protein(UPDATED_PROTEIN)
            .cholesterol(UPDATED_CHOLESTEROL)
            .sodium(UPDATED_SODIUM)
            .potassium(UPDATED_POTASSIUM);
        return foodItem;
    }

    @BeforeEach
    public void initTest() {
        foodItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createFoodItem() throws Exception {
        int databaseSizeBeforeCreate = foodItemRepository.findAll().size();
        // Create the FoodItem
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);
        restFoodItemMockMvc.perform(post("/api/food-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(foodItemDTO)))
            .andExpect(status().isCreated());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeCreate + 1);
        FoodItem testFoodItem = foodItemList.get(foodItemList.size() - 1);
        assertThat(testFoodItem.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testFoodItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFoodItem.getCalories()).isEqualTo(DEFAULT_CALORIES);
        assertThat(testFoodItem.getTotalFat()).isEqualTo(DEFAULT_TOTAL_FAT);
        assertThat(testFoodItem.getSaturated()).isEqualTo(DEFAULT_SATURATED);
        assertThat(testFoodItem.getPolyunsaturated()).isEqualTo(DEFAULT_POLYUNSATURATED);
        assertThat(testFoodItem.getMonounsaturated()).isEqualTo(DEFAULT_MONOUNSATURATED);
        assertThat(testFoodItem.getTrans()).isEqualTo(DEFAULT_TRANS);
        assertThat(testFoodItem.getTotalCarbs()).isEqualTo(DEFAULT_TOTAL_CARBS);
        assertThat(testFoodItem.getFibre()).isEqualTo(DEFAULT_FIBRE);
        assertThat(testFoodItem.getSugars()).isEqualTo(DEFAULT_SUGARS);
        assertThat(testFoodItem.getProtein()).isEqualTo(DEFAULT_PROTEIN);
        assertThat(testFoodItem.getCholesterol()).isEqualTo(DEFAULT_CHOLESTEROL);
        assertThat(testFoodItem.getSodium()).isEqualTo(DEFAULT_SODIUM);
        assertThat(testFoodItem.getPotassium()).isEqualTo(DEFAULT_POTASSIUM);

        // Validate the FoodItem in Elasticsearch
        verify(mockFoodItemSearchRepository, times(1)).save(testFoodItem);
    }

    @Test
    @Transactional
    public void createFoodItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = foodItemRepository.findAll().size();

        // Create the FoodItem with an existing ID
        foodItem.setId(1L);
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodItemMockMvc.perform(post("/api/food-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(foodItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the FoodItem in Elasticsearch
        verify(mockFoodItemSearchRepository, times(0)).save(foodItem);
    }


    @Test
    @Transactional
    public void checkBrandIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodItemRepository.findAll().size();
        // set the field null
        foodItem.setBrand(null);

        // Create the FoodItem, which fails.
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);


        restFoodItemMockMvc.perform(post("/api/food-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(foodItemDTO)))
            .andExpect(status().isBadRequest());

        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodItemRepository.findAll().size();
        // set the field null
        foodItem.setName(null);

        // Create the FoodItem, which fails.
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);


        restFoodItemMockMvc.perform(post("/api/food-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(foodItemDTO)))
            .andExpect(status().isBadRequest());

        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCaloriesIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodItemRepository.findAll().size();
        // set the field null
        foodItem.setCalories(null);

        // Create the FoodItem, which fails.
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);


        restFoodItemMockMvc.perform(post("/api/food-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(foodItemDTO)))
            .andExpect(status().isBadRequest());

        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFoodItems() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        // Get all the foodItemList
        restFoodItemMockMvc.perform(get("/api/food-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].calories").value(hasItem(DEFAULT_CALORIES.doubleValue())))
            .andExpect(jsonPath("$.[*].totalFat").value(hasItem(DEFAULT_TOTAL_FAT.doubleValue())))
            .andExpect(jsonPath("$.[*].saturated").value(hasItem(DEFAULT_SATURATED.doubleValue())))
            .andExpect(jsonPath("$.[*].polyunsaturated").value(hasItem(DEFAULT_POLYUNSATURATED.doubleValue())))
            .andExpect(jsonPath("$.[*].monounsaturated").value(hasItem(DEFAULT_MONOUNSATURATED.doubleValue())))
            .andExpect(jsonPath("$.[*].trans").value(hasItem(DEFAULT_TRANS.doubleValue())))
            .andExpect(jsonPath("$.[*].totalCarbs").value(hasItem(DEFAULT_TOTAL_CARBS.doubleValue())))
            .andExpect(jsonPath("$.[*].fibre").value(hasItem(DEFAULT_FIBRE.doubleValue())))
            .andExpect(jsonPath("$.[*].sugars").value(hasItem(DEFAULT_SUGARS.doubleValue())))
            .andExpect(jsonPath("$.[*].protein").value(hasItem(DEFAULT_PROTEIN.doubleValue())))
            .andExpect(jsonPath("$.[*].cholesterol").value(hasItem(DEFAULT_CHOLESTEROL.doubleValue())))
            .andExpect(jsonPath("$.[*].sodium").value(hasItem(DEFAULT_SODIUM.doubleValue())))
            .andExpect(jsonPath("$.[*].potassium").value(hasItem(DEFAULT_POTASSIUM.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getFoodItem() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        // Get the foodItem
        restFoodItemMockMvc.perform(get("/api/food-items/{id}", foodItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(foodItem.getId().intValue()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.calories").value(DEFAULT_CALORIES.doubleValue()))
            .andExpect(jsonPath("$.totalFat").value(DEFAULT_TOTAL_FAT.doubleValue()))
            .andExpect(jsonPath("$.saturated").value(DEFAULT_SATURATED.doubleValue()))
            .andExpect(jsonPath("$.polyunsaturated").value(DEFAULT_POLYUNSATURATED.doubleValue()))
            .andExpect(jsonPath("$.monounsaturated").value(DEFAULT_MONOUNSATURATED.doubleValue()))
            .andExpect(jsonPath("$.trans").value(DEFAULT_TRANS.doubleValue()))
            .andExpect(jsonPath("$.totalCarbs").value(DEFAULT_TOTAL_CARBS.doubleValue()))
            .andExpect(jsonPath("$.fibre").value(DEFAULT_FIBRE.doubleValue()))
            .andExpect(jsonPath("$.sugars").value(DEFAULT_SUGARS.doubleValue()))
            .andExpect(jsonPath("$.protein").value(DEFAULT_PROTEIN.doubleValue()))
            .andExpect(jsonPath("$.cholesterol").value(DEFAULT_CHOLESTEROL.doubleValue()))
            .andExpect(jsonPath("$.sodium").value(DEFAULT_SODIUM.doubleValue()))
            .andExpect(jsonPath("$.potassium").value(DEFAULT_POTASSIUM.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFoodItem() throws Exception {
        // Get the foodItem
        restFoodItemMockMvc.perform(get("/api/food-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFoodItem() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        int databaseSizeBeforeUpdate = foodItemRepository.findAll().size();

        // Update the foodItem
        FoodItem updatedFoodItem = foodItemRepository.findById(foodItem.getId()).get();
        // Disconnect from session so that the updates on updatedFoodItem are not directly saved in db
        em.detach(updatedFoodItem);
        updatedFoodItem
            .brand(UPDATED_BRAND)
            .name(UPDATED_NAME)
            .calories(UPDATED_CALORIES)
            .totalFat(UPDATED_TOTAL_FAT)
            .saturated(UPDATED_SATURATED)
            .polyunsaturated(UPDATED_POLYUNSATURATED)
            .monounsaturated(UPDATED_MONOUNSATURATED)
            .trans(UPDATED_TRANS)
            .totalCarbs(UPDATED_TOTAL_CARBS)
            .fibre(UPDATED_FIBRE)
            .sugars(UPDATED_SUGARS)
            .protein(UPDATED_PROTEIN)
            .cholesterol(UPDATED_CHOLESTEROL)
            .sodium(UPDATED_SODIUM)
            .potassium(UPDATED_POTASSIUM);
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(updatedFoodItem);

        restFoodItemMockMvc.perform(put("/api/food-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(foodItemDTO)))
            .andExpect(status().isOk());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeUpdate);
        FoodItem testFoodItem = foodItemList.get(foodItemList.size() - 1);
        assertThat(testFoodItem.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testFoodItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFoodItem.getCalories()).isEqualTo(UPDATED_CALORIES);
        assertThat(testFoodItem.getTotalFat()).isEqualTo(UPDATED_TOTAL_FAT);
        assertThat(testFoodItem.getSaturated()).isEqualTo(UPDATED_SATURATED);
        assertThat(testFoodItem.getPolyunsaturated()).isEqualTo(UPDATED_POLYUNSATURATED);
        assertThat(testFoodItem.getMonounsaturated()).isEqualTo(UPDATED_MONOUNSATURATED);
        assertThat(testFoodItem.getTrans()).isEqualTo(UPDATED_TRANS);
        assertThat(testFoodItem.getTotalCarbs()).isEqualTo(UPDATED_TOTAL_CARBS);
        assertThat(testFoodItem.getFibre()).isEqualTo(UPDATED_FIBRE);
        assertThat(testFoodItem.getSugars()).isEqualTo(UPDATED_SUGARS);
        assertThat(testFoodItem.getProtein()).isEqualTo(UPDATED_PROTEIN);
        assertThat(testFoodItem.getCholesterol()).isEqualTo(UPDATED_CHOLESTEROL);
        assertThat(testFoodItem.getSodium()).isEqualTo(UPDATED_SODIUM);
        assertThat(testFoodItem.getPotassium()).isEqualTo(UPDATED_POTASSIUM);

        // Validate the FoodItem in Elasticsearch
        verify(mockFoodItemSearchRepository, times(1)).save(testFoodItem);
    }

    @Test
    @Transactional
    public void updateNonExistingFoodItem() throws Exception {
        int databaseSizeBeforeUpdate = foodItemRepository.findAll().size();

        // Create the FoodItem
        FoodItemDTO foodItemDTO = foodItemMapper.toDto(foodItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodItemMockMvc.perform(put("/api/food-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(foodItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoodItem in the database
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FoodItem in Elasticsearch
        verify(mockFoodItemSearchRepository, times(0)).save(foodItem);
    }

    @Test
    @Transactional
    public void deleteFoodItem() throws Exception {
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);

        int databaseSizeBeforeDelete = foodItemRepository.findAll().size();

        // Delete the foodItem
        restFoodItemMockMvc.perform(delete("/api/food-items/{id}", foodItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        assertThat(foodItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FoodItem in Elasticsearch
        verify(mockFoodItemSearchRepository, times(1)).deleteById(foodItem.getId());
    }

    @Test
    @Transactional
    public void searchFoodItem() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        foodItemRepository.saveAndFlush(foodItem);
        when(mockFoodItemSearchRepository.search(queryStringQuery("id:" + foodItem.getId())))
            .thenReturn(Collections.singletonList(foodItem));

        // Search the foodItem
        restFoodItemMockMvc.perform(get("/api/_search/food-items?query=id:" + foodItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].calories").value(hasItem(DEFAULT_CALORIES.doubleValue())))
            .andExpect(jsonPath("$.[*].totalFat").value(hasItem(DEFAULT_TOTAL_FAT.doubleValue())))
            .andExpect(jsonPath("$.[*].saturated").value(hasItem(DEFAULT_SATURATED.doubleValue())))
            .andExpect(jsonPath("$.[*].polyunsaturated").value(hasItem(DEFAULT_POLYUNSATURATED.doubleValue())))
            .andExpect(jsonPath("$.[*].monounsaturated").value(hasItem(DEFAULT_MONOUNSATURATED.doubleValue())))
            .andExpect(jsonPath("$.[*].trans").value(hasItem(DEFAULT_TRANS.doubleValue())))
            .andExpect(jsonPath("$.[*].totalCarbs").value(hasItem(DEFAULT_TOTAL_CARBS.doubleValue())))
            .andExpect(jsonPath("$.[*].fibre").value(hasItem(DEFAULT_FIBRE.doubleValue())))
            .andExpect(jsonPath("$.[*].sugars").value(hasItem(DEFAULT_SUGARS.doubleValue())))
            .andExpect(jsonPath("$.[*].protein").value(hasItem(DEFAULT_PROTEIN.doubleValue())))
            .andExpect(jsonPath("$.[*].cholesterol").value(hasItem(DEFAULT_CHOLESTEROL.doubleValue())))
            .andExpect(jsonPath("$.[*].sodium").value(hasItem(DEFAULT_SODIUM.doubleValue())))
            .andExpect(jsonPath("$.[*].potassium").value(hasItem(DEFAULT_POTASSIUM.doubleValue())));
    }
}
