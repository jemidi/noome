package de.dittje.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ServingSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ServingSearchRepositoryMockConfiguration {

    @MockBean
    private ServingSearchRepository mockServingSearchRepository;

}
