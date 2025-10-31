package praksa.zadatak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkEntryWithoutEmployee {
    private AssignmentWithoutEmployeeDTO assignment;
    private YearMonth yearMonth;
    private Integer hourCount;
}
