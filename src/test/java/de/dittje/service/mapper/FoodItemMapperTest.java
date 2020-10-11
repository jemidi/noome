package de.dittje.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FoodItemMapperTest {

    private FoodItemMapper foodItemMapper;

    @BeforeEach
    public void setUp() {
        foodItemMapper = new FoodItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(foodItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(foodItemMapper.fromId(null)).isNull();
    }
}
