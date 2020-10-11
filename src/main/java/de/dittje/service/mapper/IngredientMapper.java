package de.dittje.service.mapper;


import de.dittje.domain.*;
import de.dittje.service.dto.IngredientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ingredient} and its DTO {@link IngredientDTO}.
 */
@Mapper(componentModel = "spring", uses = {FoodItemMapper.class, ServingMapper.class, RecipeMapper.class})
public interface IngredientMapper extends EntityMapper<IngredientDTO, Ingredient> {

    @Mapping(source = "food.id", target = "foodId")
    @Mapping(source = "food.name", target = "foodName")
    @Mapping(source = "serving.id", target = "servingId")
    @Mapping(source = "serving.unit", target = "servingUnit")
    @Mapping(source = "recipe.id", target = "recipeId")
    IngredientDTO toDto(Ingredient ingredient);

    @Mapping(source = "foodId", target = "food")
    @Mapping(source = "servingId", target = "serving")
    @Mapping(source = "recipeId", target = "recipe")
    Ingredient toEntity(IngredientDTO ingredientDTO);

    default Ingredient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        return ingredient;
    }
}
