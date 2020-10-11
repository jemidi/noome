package de.dittje.web.rest;

import de.dittje.service.FoodItemService;
import de.dittje.web.rest.errors.BadRequestAlertException;
import de.dittje.service.dto.FoodItemDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link de.dittje.domain.FoodItem}.
 */
@RestController
@RequestMapping("/api")
public class FoodItemResource {

    private final Logger log = LoggerFactory.getLogger(FoodItemResource.class);

    private static final String ENTITY_NAME = "foodItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoodItemService foodItemService;

    public FoodItemResource(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    /**
     * {@code POST  /food-items} : Create a new foodItem.
     *
     * @param foodItemDTO the foodItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foodItemDTO, or with status {@code 400 (Bad Request)} if the foodItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/food-items")
    public ResponseEntity<FoodItemDTO> createFoodItem(@Valid @RequestBody FoodItemDTO foodItemDTO) throws URISyntaxException {
        log.debug("REST request to save FoodItem : {}", foodItemDTO);
        if (foodItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new foodItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoodItemDTO result = foodItemService.save(foodItemDTO);
        return ResponseEntity.created(new URI("/api/food-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /food-items} : Updates an existing foodItem.
     *
     * @param foodItemDTO the foodItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodItemDTO,
     * or with status {@code 400 (Bad Request)} if the foodItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foodItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/food-items")
    public ResponseEntity<FoodItemDTO> updateFoodItem(@Valid @RequestBody FoodItemDTO foodItemDTO) throws URISyntaxException {
        log.debug("REST request to update FoodItem : {}", foodItemDTO);
        if (foodItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FoodItemDTO result = foodItemService.save(foodItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, foodItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /food-items} : get all the foodItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foodItems in body.
     */
    @GetMapping("/food-items")
    public List<FoodItemDTO> getAllFoodItems() {
        log.debug("REST request to get all FoodItems");
        return foodItemService.findAll();
    }

    /**
     * {@code GET  /food-items/:id} : get the "id" foodItem.
     *
     * @param id the id of the foodItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foodItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/food-items/{id}")
    public ResponseEntity<FoodItemDTO> getFoodItem(@PathVariable Long id) {
        log.debug("REST request to get FoodItem : {}", id);
        Optional<FoodItemDTO> foodItemDTO = foodItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foodItemDTO);
    }

    /**
     * {@code DELETE  /food-items/:id} : delete the "id" foodItem.
     *
     * @param id the id of the foodItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/food-items/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
        log.debug("REST request to delete FoodItem : {}", id);
        foodItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/food-items?query=:query} : search for the foodItem corresponding
     * to the query.
     *
     * @param query the query of the foodItem search.
     * @return the result of the search.
     */
    @GetMapping("/_search/food-items")
    public List<FoodItemDTO> searchFoodItems(@RequestParam String query) {
        log.debug("REST request to search FoodItems for query {}", query);
        return foodItemService.search(query);
    }
}
