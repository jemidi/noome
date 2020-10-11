package de.dittje.service;

import de.dittje.domain.Ingredient;
import de.dittje.repository.IngredientRepository;
import de.dittje.repository.search.IngredientSearchRepository;
import de.dittje.service.dto.IngredientDTO;
import de.dittje.service.mapper.IngredientMapper;
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
 * Service Implementation for managing {@link Ingredient}.
 */
@Service
@Transactional
public class IngredientService {

    private final Logger log = LoggerFactory.getLogger(IngredientService.class);

    private final IngredientRepository ingredientRepository;

    private final IngredientMapper ingredientMapper;

    private final IngredientSearchRepository ingredientSearchRepository;

    public IngredientService(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper, IngredientSearchRepository ingredientSearchRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.ingredientSearchRepository = ingredientSearchRepository;
    }

    /**
     * Save a ingredient.
     *
     * @param ingredientDTO the entity to save.
     * @return the persisted entity.
     */
    public IngredientDTO save(IngredientDTO ingredientDTO) {
        log.debug("Request to save Ingredient : {}", ingredientDTO);
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDTO);
        ingredient = ingredientRepository.save(ingredient);
        IngredientDTO result = ingredientMapper.toDto(ingredient);
        ingredientSearchRepository.save(ingredient);
        return result;
    }

    /**
     * Get all the ingredients.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<IngredientDTO> findAll() {
        log.debug("Request to get all Ingredients");
        return ingredientRepository.findAll().stream()
            .map(ingredientMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one ingredient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IngredientDTO> findOne(Long id) {
        log.debug("Request to get Ingredient : {}", id);
        return ingredientRepository.findById(id)
            .map(ingredientMapper::toDto);
    }

    /**
     * Delete the ingredient by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ingredient : {}", id);
        ingredientRepository.deleteById(id);
        ingredientSearchRepository.deleteById(id);
    }

    /**
     * Search for the ingredient corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<IngredientDTO> search(String query) {
        log.debug("Request to search Ingredients for query {}", query);
        return StreamSupport
            .stream(ingredientSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(ingredientMapper::toDto)
        .collect(Collectors.toList());
    }
}
