package de.dittje.service.mapper;


import de.dittje.domain.*;
import de.dittje.service.dto.ServingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Serving} and its DTO {@link ServingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServingMapper extends EntityMapper<ServingDTO, Serving> {


    @Mapping(target = "ingredients", ignore = true)
    @Mapping(target = "removeIngredient", ignore = true)
    Serving toEntity(ServingDTO servingDTO);

    default Serving fromId(Long id) {
        if (id == null) {
            return null;
        }
        Serving serving = new Serving();
        serving.setId(id);
        return serving;
    }
}
