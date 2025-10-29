package praksa.zadatak.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import praksa.zadatak.dto.AssignmentWithoutProjectDTO;
import praksa.zadatak.dto.ProjectDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectAssignmentsResponseDTO {
    ProjectDTO projectDTO;
    Page<AssignmentWithoutProjectDTO> assignmentDTOs;
}
