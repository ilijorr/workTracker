package praksa.zadatak.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import praksa.zadatak.dto.AssignmentDTO;
import praksa.zadatak.dto.CreateAssignmentRequestDTO;
import praksa.zadatak.model.Assignment;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, ProjectMapper.class})
public interface AssignmentMapper {

    AssignmentDTO toDTO(Assignment assignment);

    Assignment toEntity(AssignmentDTO assignmentDTO);

    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "project", ignore = true)
    Assignment toEntity(CreateAssignmentRequestDTO createAssignmentRequestDTO);
}