package praksa.zadatak.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.CreateWorkEntryRequestDTO;
import praksa.zadatak.dto.WorkEntryDTO;
import praksa.zadatak.exception.NotAssignedException;
import praksa.zadatak.exception.ResourceNotFoundException;
import praksa.zadatak.mapper.WorkEntryMapper;
import praksa.zadatak.model.Assignment;
import praksa.zadatak.model.AssignmentId;
import praksa.zadatak.model.WorkEntry;
import praksa.zadatak.repository.AssignmentRepository;
import praksa.zadatak.repository.WorkEntryRepository;
import praksa.zadatak.service.WorkEntryService;

import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class WorkEntryServiceImpl implements WorkEntryService {
    private final WorkEntryRepository workEntryRepository;
    private final WorkEntryMapper workEntryMapper;

    private final AssignmentRepository assignmentRepository;

    public WorkEntryDTO create(CreateWorkEntryRequestDTO request) {
        Long employeeId = request.getEmployeeId(); // will be read from auth later
        Long projectId = request.getProjectId();
        AssignmentId assignmentId = new AssignmentId(employeeId, projectId);

        try {
            Assignment assignment = assignmentRepository.getReferenceById(assignmentId);
            YearMonth yearMonth = request.getYearMonth();
            Integer hourCount = request.getHourCount();

            WorkEntry workEntry = new WorkEntry(assignment, yearMonth.toString(), hourCount);
            workEntry = workEntryRepository.save(workEntry);

            return workEntryMapper.toDTO(workEntry);
        } catch (EntityNotFoundException) {
            throw new ResourceNotFoundException("Assignment", "id", assignmentId);
        }
    }
}
