package praksa.zadatak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDTO {
    private EmployeeDTO employee;
    private ProjectDTO project;
    private float hourRate;
}