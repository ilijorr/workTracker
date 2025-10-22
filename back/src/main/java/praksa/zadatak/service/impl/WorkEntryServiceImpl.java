package praksa.zadatak.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.request.CreateWorkEntryRequestDTO;
import praksa.zadatak.dto.request.UpdateWorkEntryRequestDTO;
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
    private static final Logger log = LogManager.getLogger(WorkEntryServiceImpl.class);
    private final WorkEntryRepository workEntryRepository;
    private final WorkEntryMapper workEntryMapper;

    private final AssignmentService assignmentService;

    @Transactional
    public WorkEntryDTO create(
            CreateWorkEntryRequestDTO request,
            Long employeeId) {
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
            log.warn("Employee {} is not assigned to project {}", employeeId, projectId);
            throw new ResourceNotFoundException("Assignment", "id", assignmentId);
        }
    }

    @Transactional
    public WorkEntryDTO update(
            UpdateWorkEntryRequestDTO request,
            Long employeeId) {
        WorkEntryId workEntryId = new WorkEntryId(
                new AssignmentId(employeeId, request.getProjectId()),
                request.getYearMonth().toString()
        );

        try {
            WorkEntry workEntry = workEntryRepository.getReferenceById(workEntryId);
            workEntry.setHourCount(request.getHourCount());
            workEntry = workEntryRepository.save(workEntry);

            return workEntryMapper.toDTO(workEntry);
        } catch (EntityNotFoundException ex) {
            log.warn("Employee {} is not assigned to project {}, or hasn't worked on it in the month {}",
                    employeeId, request.getProjectId(), request.getYearMonth());
            throw new ResourceNotFoundException("Work entry", "id", workEntryId);
        }
    }

    @Transactional
    public void delete(Long employeeId, Long projectId, YearMonth yearMonth) {
        workEntryRepository.deleteById(new WorkEntryId(
                new AssignmentId(employeeId, projectId),
                yearMonth.toString()
        ));
    }

    public List<WorkEntryDTO> getByYearMonth(YearMonth yearMonth) {
        return workEntryMapper.toDTOs(
                workEntryRepository.findByYearMonth(yearMonth.toString())
        );
    }

    public List<WorkEntryDTO> getByEmployee(Long employeeId, YearMonth yearMonth) {
        List<WorkEntry> workEntries = (yearMonth == null) ?
                workEntryRepository.findByAssignment_EmployeeId(employeeId) :
                workEntryRepository.findByAssignment_EmployeeIdAndYearMonth(employeeId, yearMonth.toString());

        return workEntryMapper.toDTOs(workEntries);
    }

    public List<WorkEntryDTO> getByProject(Long projectId, YearMonth yearMonth) {
        List<WorkEntry> workEntries = (yearMonth == null) ?
                workEntryRepository.findByAssignment_ProjectId(projectId) :
                workEntryRepository.findByAssignment_ProjectIdAndYearMonth(projectId, yearMonth.toString());

        return workEntryMapper.toDTOs(workEntries);
    }
}
