package praksa.zadatak.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import praksa.zadatak.dto.AssignmentDTO;
import praksa.zadatak.dto.AssignmentWithoutEmployeeDTO;
import praksa.zadatak.dto.request.CreateAssignmentRequestDTO;
import praksa.zadatak.model.Assignment;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, ProjectMapper.class})
public interface AssignmentMapper {

    AssignmentDTO toDTO(Assignment assignment);
    List<AssignmentDTO> toDTOs(List<Assignment> assignment);

    Assignment toEntity(AssignmentDTO assignmentDTO);

    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "project", ignore = true)
    Assignment toEntity(CreateAssignmentRequestDTO createAssignmentRequestDTO);

    @Mapping(source = "project", target = "projectDTO")
    AssignmentWithoutEmployeeDTO toAssignmentWithoutEmployeeDTO(Assignment assignment);

    List<AssignmentWithoutEmployeeDTO> toAssignmentWithoutEmployeeDTOs(List<Assignment> assignments);
}