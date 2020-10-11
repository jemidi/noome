package de.dittje.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.dittje.web.rest.TestUtil;

public class FoodItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodItem.class);
        FoodItem foodItem1 = new FoodItem();
        foodItem1.setId(1L);
        FoodItem foodItem2 = new FoodItem();
        foodItem2.setId(foodItem1.getId());
        assertThat(foodItem1).isEqualTo(foodItem2);
        foodItem2.setId(2L);
        assertThat(foodItem1).isNotEqualTo(foodItem2);
        foodItem1.setId(null);
        assertThat(foodItem1).isNotEqualTo(foodItem2);
    }
}
