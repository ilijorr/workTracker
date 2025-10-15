package praksa.zadatak.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssignmentRequestDTO {
    @NotNull
    private Long employeeId;

    @NotNull
    private Long projectId;

    @Positive
    private float hourRate;
}