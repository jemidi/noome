package de.dittje.web.rest;

import de.dittje.service.ServingService;
import de.dittje.web.rest.errors.BadRequestAlertException;
import de.dittje.service.dto.ServingDTO;

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
 * REST controller for managing {@link de.dittje.domain.Serving}.
 */
@RestController
@RequestMapping("/api")
public class ServingResource {

    private final Logger log = LoggerFactory.getLogger(ServingResource.class);

    private static final String ENTITY_NAME = "serving";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServingService servingService;

    public ServingResource(ServingService servingService) {
        this.servingService = servingService;
    }

    /**
     * {@code POST  /servings} : Create a new serving.
     *
     * @param servingDTO the servingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servingDTO, or with status {@code 400 (Bad Request)} if the serving has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/servings")
    public ResponseEntity<ServingDTO> createServing(@Valid @RequestBody ServingDTO servingDTO) throws URISyntaxException {
        log.debug("REST request to save Serving : {}", servingDTO);
        if (servingDTO.getId() != null) {
            throw new BadRequestAlertException("A new serving cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServingDTO result = servingService.save(servingDTO);
        return ResponseEntity.created(new URI("/api/servings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /servings} : Updates an existing serving.
     *
     * @param servingDTO the servingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servingDTO,
     * or with status {@code 400 (Bad Request)} if the servingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/servings")
    public ResponseEntity<ServingDTO> updateServing(@Valid @RequestBody ServingDTO servingDTO) throws URISyntaxException {
        log.debug("REST request to update Serving : {}", servingDTO);
        if (servingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServingDTO result = servingService.save(servingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, servingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /servings} : get all the servings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servings in body.
     */
    @GetMapping("/servings")
    public List<ServingDTO> getAllServings() {
        log.debug("REST request to get all Servings");
        return servingService.findAll();
    }

    /**
     * {@code GET  /servings/:id} : get the "id" serving.
     *
     * @param id the id of the servingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/servings/{id}")
    public ResponseEntity<ServingDTO> getServing(@PathVariable Long id) {
        log.debug("REST request to get Serving : {}", id);
        Optional<ServingDTO> servingDTO = servingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servingDTO);
    }

    /**
     * {@code DELETE  /servings/:id} : delete the "id" serving.
     *
     * @param id the id of the servingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/servings/{id}")
    public ResponseEntity<Void> deleteServing(@PathVariable Long id) {
        log.debug("REST request to delete Serving : {}", id);
        servingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/servings?query=:query} : search for the serving corresponding
     * to the query.
     *
     * @param query the query of the serving search.
     * @return the result of the search.
     */
    @GetMapping("/_search/servings")
    public List<ServingDTO> searchServings(@RequestParam String query) {
        log.debug("REST request to search Servings for query {}", query);
        return servingService.search(query);
    }
}
