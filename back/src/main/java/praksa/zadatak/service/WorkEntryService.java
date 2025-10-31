package praksa.zadatak.service;

import org.springframework.data.domain.Page;
import praksa.zadatak.dto.request.CreateWorkEntryRequestDTO;
import praksa.zadatak.dto.request.UpdateWorkEntryRequestDTO;
import praksa.zadatak.dto.WorkEntryDTO;
import praksa.zadatak.dto.response.EmployeeWorkEntriesResponseDTO;
import praksa.zadatak.dto.response.ProjectWorkEntriesResponseDTO;

import java.time.YearMonth;

public interface WorkEntryService {
    WorkEntryDTO create(CreateWorkEntryRequestDTO request, Long employeeId);
    WorkEntryDTO update(UpdateWorkEntryRequestDTO request, Long employeeId);
    void delete(Long employeeId, Long projectId, YearMonth yearMonth);

    Page<WorkEntryDTO> getByYearMonth(
            YearMonth yearMonth, Integer page, Integer size);
    EmployeeWorkEntriesResponseDTO getByEmployee(
            Long employeeId, YearMonth yearMonth, Integer page, Integer size);
    ProjectWorkEntriesResponseDTO getByProject(
            Long projectId, YearMonth yearMonth, Integer page, Integer size);
}
