package praksa.zadatak.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import praksa.zadatak.dto.AssignmentWithoutEmployeeDTO;
import praksa.zadatak.dto.EmployeeDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAssignmentsResponseDTO {
    EmployeeDTO employee;
    Page<AssignmentWithoutEmployeeDTO> assignments;
}
