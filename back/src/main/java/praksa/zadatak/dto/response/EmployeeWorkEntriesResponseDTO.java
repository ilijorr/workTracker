package praksa.zadatak.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import praksa.zadatak.dto.EmployeeDTO;
import praksa.zadatak.dto.WorkEntryWithoutEmployee;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeWorkEntriesResponseDTO {
    private EmployeeDTO employee;
    private Page<WorkEntryWithoutEmployee> workEntries;
}
