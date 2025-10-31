package praksa.zadatak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkEntryDTO {
    private AssignmentDTO assignment;
    private YearMonth yearMonth;
    private Integer hourCount;
}