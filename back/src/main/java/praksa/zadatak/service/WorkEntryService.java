package praksa.zadatak.service;

import praksa.zadatak.dto.request.CreateWorkEntryRequestDTO;
import praksa.zadatak.dto.request.UpdateWorkEntryRequestDTO;
import praksa.zadatak.dto.WorkEntryDTO;

import java.time.YearMonth;
import java.util.List;

public interface WorkEntryService {
    WorkEntryDTO create(CreateWorkEntryRequestDTO request, Long employeeId);
    WorkEntryDTO update(UpdateWorkEntryRequestDTO request, Long employeeId);
    void delete(Long employeeId, Long projectId, YearMonth yearMonth);

    List<WorkEntryDTO> getByYearMonth(YearMonth yearMonth);
    List<WorkEntryDTO> getByEmployee(Long employeeId, YearMonth yearMonth);
    List<WorkEntryDTO> getByProject(Long projectId, YearMonth yearMonth);
}
