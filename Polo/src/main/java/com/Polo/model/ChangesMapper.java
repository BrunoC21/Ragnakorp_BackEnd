package com.Polo.model;

import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ChangesMapper {
    ChangesDTO changesToChangesDTO(Changes changes);
    Changes changesDTOToChanges(ChangesDTO changesDTO);
    List<ChangesDTO> changesListToChangesDTOList(List<Changes> changes);
    List<Changes> changesDTOListToChangesList(List<ChangesDTO> changesDTOs);
}
