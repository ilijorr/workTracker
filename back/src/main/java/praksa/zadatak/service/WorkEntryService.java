package praksa.zadatak.service;

import praksa.zadatak.dto.CreateWorkEntryRequestDTO;
import praksa.zadatak.dto.WorkEntryDTO;

import java.time.YearMonth;
import java.util.List;

public interface WorkEntryService {
    WorkEntryDTO create(CreateWorkEntryRequestDTO request);
    WorkEntryDTO update(CreateWorkEntryRequestDTO request);

    List<WorkEntryDTO> getByMonthForEmployee(YearMonth yearMonth, Long employeeId);
    List<WorkEntryDTO> getByMonthForProject(YearMonth yearMonth, Long projectId);
}
