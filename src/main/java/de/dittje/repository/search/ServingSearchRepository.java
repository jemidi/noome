package de.dittje.repository.search;

import de.dittje.domain.Serving;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Serving} entity.
 */
public interface ServingSearchRepository extends ElasticsearchRepository<Serving, Long> {
}
