package praksa.zadatak.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import praksa.zadatak.dto.request.CreateWorkEntryRequestDTO;
import praksa.zadatak.dto.WorkEntryDTO;
import praksa.zadatak.dto.WorkEntryWithoutEmployee;
import praksa.zadatak.dto.WorkEntryWithoutProject;
import praksa.zadatak.model.WorkEntry;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AssignmentMapper.class})
public interface WorkEntryMapper {

    @Mapping(target = "yearMonth", expression = "java(java.time.YearMonth.parse(workEntry.getYearMonth()))")
    WorkEntryDTO toDTO(WorkEntry workEntry);

    List<WorkEntryDTO> toDTOs(List<WorkEntry> workEntries);

    @Mapping(target = "yearMonth", expression = "java(workEntryDTO.getYearMonth().toString())")
    WorkEntry toEntity(WorkEntryDTO workEntryDTO);

    @Mapping(target = "assignment", ignore = true)
    @Mapping(target = "yearMonth", expression = "java(createWorkEntryRequestDTO.getYearMonth().toString())")
    WorkEntry toEntity(CreateWorkEntryRequestDTO createWorkEntryRequestDTO);

    @Mapping(target = "yearMonth", expression = "java(java.time.YearMonth.parse(workEntry.getYearMonth()))")
    WorkEntryWithoutEmployee toWorkEntryWithoutEmployeeDTO(WorkEntry workEntry);

    @Mapping(target = "yearMonth", expression = "java(java.time.YearMonth.parse(workEntry.getYearMonth()))")
    WorkEntryWithoutProject toWorkEntryWithoutProjectDTO(WorkEntry workEntry);
}