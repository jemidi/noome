package de.dittje.repository.search;

import de.dittje.domain.FoodItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link FoodItem} entity.
 */
public interface FoodItemSearchRepository extends ElasticsearchRepository<FoodItem, Long> {
}
