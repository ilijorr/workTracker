package praksa.zadatak.service;

import praksa.zadatak.dto.request.CreateWorkEntryRequestDTO;
import praksa.zadatak.dto.request.UpdateWorkEntryRequestDTO;
import praksa.zadatak.dto.WorkEntryDTO;

import java.time.YearMonth;
import java.util.List;

public interface WorkEntryService {
    WorkEntryDTO create(CreateWorkEntryRequestDTO request);
    WorkEntryDTO update(UpdateWorkEntryRequestDTO request);
    void delete(Long employeeId, Long projectId, YearMonth yearMonth);

    List<WorkEntryDTO> getByMonthForEmployee(YearMonth yearMonth, Long employeeId);
    List<WorkEntryDTO> getByMonthForProject(YearMonth yearMonth, Long projectId);
}
