package de.dittje.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.dittje.web.rest.TestUtil;

public class ServingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Serving.class);
        Serving serving1 = new Serving();
        serving1.setId(1L);
        Serving serving2 = new Serving();
        serving2.setId(serving1.getId());
        assertThat(serving1).isEqualTo(serving2);
        serving2.setId(2L);
        assertThat(serving1).isNotEqualTo(serving2);
        serving1.setId(null);
        assertThat(serving1).isNotEqualTo(serving2);
    }
}
