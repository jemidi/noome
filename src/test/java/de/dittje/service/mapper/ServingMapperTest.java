package de.dittje.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ServingMapperTest {

    private ServingMapper servingMapper;

    @BeforeEach
    public void setUp() {
        servingMapper = new ServingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(servingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(servingMapper.fromId(null)).isNull();
    }
}
