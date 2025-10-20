package praksa.zadatak.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWorkEntryRequestDTO {
    @NotNull
    private Long employeeId;
    @NotNull
    private Long projectId;
    @NotNull
    private YearMonth yearMonth;
    @Positive
    @Max(720)
    @NotNull
    private Integer hourCount;
}