package de.dittje.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import de.dittje.domain.enumeration.Unit;

/**
 * A DTO for the {@link de.dittje.domain.Serving} entity.
 */
public class ServingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Float quantity;

    @NotNull
    private Unit unit;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServingDTO)) {
            return false;
        }

        return id != null && id.equals(((ServingDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServingDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unit='" + getUnit() + "'" +
            "}";
    }
}
