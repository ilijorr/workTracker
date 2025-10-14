package praksa.zadatak.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import praksa.zadatak.dto.CreateWorkEntryRequestDTO;
import praksa.zadatak.dto.WorkEntryDTO;
import praksa.zadatak.model.WorkEntry;

@Mapper(componentModel = "spring", uses = {AssignmentMapper.class})
public interface WorkEntryMapper {

    WorkEntryDTO toDTO(WorkEntry workEntry);

    WorkEntry toEntity(WorkEntryDTO workEntryDTO);

    @Mapping(target = "assignment", ignore = true)
    WorkEntry toEntity(CreateWorkEntryRequestDTO createWorkEntryRequestDTO);
}