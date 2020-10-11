package de.dittje.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.dittje.web.rest.TestUtil;

public class ServingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServingDTO.class);
        ServingDTO servingDTO1 = new ServingDTO();
        servingDTO1.setId(1L);
        ServingDTO servingDTO2 = new ServingDTO();
        assertThat(servingDTO1).isNotEqualTo(servingDTO2);
        servingDTO2.setId(servingDTO1.getId());
        assertThat(servingDTO1).isEqualTo(servingDTO2);
        servingDTO2.setId(2L);
        assertThat(servingDTO1).isNotEqualTo(servingDTO2);
        servingDTO1.setId(null);
        assertThat(servingDTO1).isNotEqualTo(servingDTO2);
    }
}
