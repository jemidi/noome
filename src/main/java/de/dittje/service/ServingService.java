package de.dittje.service;

import de.dittje.domain.Serving;
import de.dittje.repository.ServingRepository;
import de.dittje.repository.search.ServingSearchRepository;
import de.dittje.service.dto.ServingDTO;
import de.dittje.service.mapper.ServingMapper;
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
 * Service Implementation for managing {@link Serving}.
 */
@Service
@Transactional
public class ServingService {

    private final Logger log = LoggerFactory.getLogger(ServingService.class);

    private final ServingRepository servingRepository;

    private final ServingMapper servingMapper;

    private final ServingSearchRepository servingSearchRepository;

    public ServingService(ServingRepository servingRepository, ServingMapper servingMapper, ServingSearchRepository servingSearchRepository) {
        this.servingRepository = servingRepository;
        this.servingMapper = servingMapper;
        this.servingSearchRepository = servingSearchRepository;
    }

    /**
     * Save a serving.
     *
     * @param servingDTO the entity to save.
     * @return the persisted entity.
     */
    public ServingDTO save(ServingDTO servingDTO) {
        log.debug("Request to save Serving : {}", servingDTO);
        Serving serving = servingMapper.toEntity(servingDTO);
        serving = servingRepository.save(serving);
        ServingDTO result = servingMapper.toDto(serving);
        servingSearchRepository.save(serving);
        return result;
    }

    /**
     * Get all the servings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServingDTO> findAll() {
        log.debug("Request to get all Servings");
        return servingRepository.findAll().stream()
            .map(servingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one serving by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServingDTO> findOne(Long id) {
        log.debug("Request to get Serving : {}", id);
        return servingRepository.findById(id)
            .map(servingMapper::toDto);
    }

    /**
     * Delete the serving by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Serving : {}", id);
        servingRepository.deleteById(id);
        servingSearchRepository.deleteById(id);
    }

    /**
     * Search for the serving corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServingDTO> search(String query) {
        log.debug("Request to search Servings for query {}", query);
        return StreamSupport
            .stream(servingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(servingMapper::toDto)
        .collect(Collectors.toList());
    }
}
