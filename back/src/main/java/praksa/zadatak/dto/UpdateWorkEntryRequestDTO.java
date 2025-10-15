package praksa.zadatak.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWorkEntryRequestDTO {
    @NotNull
    private Long employeeId;
    @NotNull
    private Long projectId;
    @NotNull
    private YearMonth yearMonth;
    @Positive
    @NotNull
    private Integer hourCount;
}

