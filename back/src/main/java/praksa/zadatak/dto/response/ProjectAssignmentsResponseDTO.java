package praksa.zadatak.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import praksa.zadatak.dto.AssignmentWithoutProjectDTO;
import praksa.zadatak.dto.ProjectDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectAssignmentsResponseDTO {
    ProjectDTO projectDTO;
    List<AssignmentWithoutProjectDTO> assignmentDTOs;
}
