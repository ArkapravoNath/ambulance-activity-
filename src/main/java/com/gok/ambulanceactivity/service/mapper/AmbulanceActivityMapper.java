package com.gok.ambulanceactivity.service.mapper;


import com.gok.ambulanceactivity.domain.*;
import com.gok.ambulanceactivity.service.dto.AmbulanceActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AmbulanceActivity} and its DTO {@link AmbulanceActivityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AmbulanceActivityMapper extends EntityMapper<AmbulanceActivityDTO, AmbulanceActivity> {



    default AmbulanceActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        AmbulanceActivity ambulanceActivity = new AmbulanceActivity();
        ambulanceActivity.setId(id);
        return ambulanceActivity;
    }
}
