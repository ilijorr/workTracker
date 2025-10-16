package praksa.zadatak.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.CreateWorkEntryRequestDTO;
import praksa.zadatak.dto.UpdateWorkEntryRequestDTO;
import praksa.zadatak.dto.WorkEntryDTO;
import praksa.zadatak.exception.ResourceNotFoundException;
import praksa.zadatak.mapper.WorkEntryMapper;
import praksa.zadatak.model.AssignmentId;
import praksa.zadatak.model.WorkEntry;
import praksa.zadatak.model.WorkEntryId;
import praksa.zadatak.repository.WorkEntryRepository;
import praksa.zadatak.service.AssignmentService;
import praksa.zadatak.service.WorkEntryService;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkEntryServiceImpl implements WorkEntryService {
    private final WorkEntryRepository workEntryRepository;
    private final WorkEntryMapper workEntryMapper;

    private final AssignmentService assignmentService;

    @Transactional
    public WorkEntryDTO create(CreateWorkEntryRequestDTO request) {
        Long employeeId = request.getEmployeeId(); // will be read from auth later
        Long projectId = request.getProjectId();
        AssignmentId assignmentId = new AssignmentId(employeeId, projectId);

        try {
            WorkEntry workEntry = new WorkEntry(
                    assignmentService.getReference(assignmentId),
                    request.getYearMonth().toString(),
                    request.getHourCount()
            );
            workEntry = workEntryRepository.save(workEntry);

            return workEntryMapper.toDTO(workEntry);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Assignment", "id", assignmentId);
        }
    }

    @Transactional
    public WorkEntryDTO update(UpdateWorkEntryRequestDTO request) {
        Long employeeId = request.getEmployeeId(); // will be read from auth later
        Long projectId = request.getProjectId();
        AssignmentId assignmentId = new AssignmentId(employeeId, projectId);
        YearMonth yearMonth = request.getYearMonth();
        WorkEntryId workEntryId = new WorkEntryId(assignmentId, yearMonth.toString());

        try {
            WorkEntry workEntry = workEntryRepository.getReferenceById(workEntryId);
            workEntry.setHourCount(request.getHourCount());
            workEntry = workEntryRepository.save(workEntry);

            return workEntryMapper.toDTO(workEntry);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Work entry", "id", workEntryId);
        }
    }

    // brisanje

    public List<WorkEntryDTO> getByMonthForEmployee(YearMonth yearMonth, Long employeeId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    public List<WorkEntryDTO> getByMonthForProject(YearMonth yearMonth, Long projectId) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
