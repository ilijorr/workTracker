package praksa.zadatak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWorkEntryRequestDTO {
    private Long employeeId;
    private Long projectId;
    private YearMonth yearMonth;
    private Integer hourCount;
}