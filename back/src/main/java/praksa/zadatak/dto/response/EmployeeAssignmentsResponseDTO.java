package praksa.zadatak.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import praksa.zadatak.dto.AssignmentWithoutEmployeeDTO;
import praksa.zadatak.dto.EmployeeDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAssignmentsResponseDTO {
    EmployeeDTO employeeDTO;
    List<AssignmentWithoutEmployeeDTO> assignmentDTOS;
}
