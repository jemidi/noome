package de.dittje.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link de.dittje.domain.Ingredient} entity.
 */
public class IngredientDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Float quantity;


    private Long foodId;

    private String foodName;

    private Long servingId;

    private String servingUnit;

    private Long recipeId;
    
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

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodItemId) {
        this.foodId = foodItemId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodItemName) {
        this.foodName = foodItemName;
    }

    public Long getServingId() {
        return servingId;
    }

    public void setServingId(Long servingId) {
        this.servingId = servingId;
    }

    public String getServingUnit() {
        return servingUnit;
    }

    public void setServingUnit(String servingUnit) {
        this.servingUnit = servingUnit;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngredientDTO)) {
            return false;
        }

        return id != null && id.equals(((IngredientDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IngredientDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", foodId=" + getFoodId() +
            ", foodName='" + getFoodName() + "'" +
            ", servingId=" + getServingId() +
            ", servingUnit='" + getServingUnit() + "'" +
            ", recipeId=" + getRecipeId() +
            "}";
    }
}
