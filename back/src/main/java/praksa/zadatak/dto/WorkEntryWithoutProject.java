package praksa.zadatak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkEntryWithoutProject {
    private AssignmentWithoutProjectDTO assignment;
    private YearMonth yearMonth;
    private Integer hourCount;
}
