package praksa.zadatak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssignmentRequestDTO {
    private Long employeeId;
    private Long projectId;
    private float hourRate;
}