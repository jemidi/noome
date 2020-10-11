package de.dittje.service.mapper;


import de.dittje.domain.*;
import de.dittje.service.dto.FoodItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FoodItem} and its DTO {@link FoodItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FoodItemMapper extends EntityMapper<FoodItemDTO, FoodItem> {


    @Mapping(target = "ingredients", ignore = true)
    @Mapping(target = "removeIngredient", ignore = true)
    FoodItem toEntity(FoodItemDTO foodItemDTO);

    default FoodItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        FoodItem foodItem = new FoodItem();
        foodItem.setId(id);
        return foodItem;
    }
}
