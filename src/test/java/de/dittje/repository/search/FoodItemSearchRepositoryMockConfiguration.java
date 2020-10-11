package de.dittje.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link FoodItemSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class FoodItemSearchRepositoryMockConfiguration {

    @MockBean
    private FoodItemSearchRepository mockFoodItemSearchRepository;

}
