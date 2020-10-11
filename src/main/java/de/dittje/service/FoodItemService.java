package de.dittje.service;

import de.dittje.domain.FoodItem;
import de.dittje.repository.FoodItemRepository;
import de.dittje.repository.search.FoodItemSearchRepository;
import de.dittje.service.dto.FoodItemDTO;
import de.dittje.service.mapper.FoodItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link FoodItem}.
 */
@Service
@Transactional
public class FoodItemService {

    private final Logger log = LoggerFactory.getLogger(FoodItemService.class);

    private final FoodItemRepository foodItemRepository;

    private final FoodItemMapper foodItemMapper;

    private final FoodItemSearchRepository foodItemSearchRepository;

    public FoodItemService(FoodItemRepository foodItemRepository, FoodItemMapper foodItemMapper, FoodItemSearchRepository foodItemSearchRepository) {
        this.foodItemRepository = foodItemRepository;
        this.foodItemMapper = foodItemMapper;
        this.foodItemSearchRepository = foodItemSearchRepository;
    }

    /**
     * Save a foodItem.
     *
     * @param foodItemDTO the entity to save.
     * @return the persisted entity.
     */
    public FoodItemDTO save(FoodItemDTO foodItemDTO) {
        log.debug("Request to save FoodItem : {}", foodItemDTO);
        FoodItem foodItem = foodItemMapper.toEntity(foodItemDTO);
        foodItem = foodItemRepository.save(foodItem);
        FoodItemDTO result = foodItemMapper.toDto(foodItem);
        foodItemSearchRepository.save(foodItem);
        return result;
    }

    /**
     * Get all the foodItems.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FoodItemDTO> findAll() {
        log.debug("Request to get all FoodItems");
        return foodItemRepository.findAll().stream()
            .map(foodItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one foodItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FoodItemDTO> findOne(Long id) {
        log.debug("Request to get FoodItem : {}", id);
        return foodItemRepository.findById(id)
            .map(foodItemMapper::toDto);
    }

    /**
     * Delete the foodItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FoodItem : {}", id);
        foodItemRepository.deleteById(id);
        foodItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the foodItem corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FoodItemDTO> search(String query) {
        log.debug("Request to search FoodItems for query {}", query);
        return StreamSupport
            .stream(foodItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(foodItemMapper::toDto)
        .collect(Collectors.toList());
    }
}
