package praksa.zadatak.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.EmployeeDTO;
import praksa.zadatak.dto.ProjectDTO;
import praksa.zadatak.dto.request.CreateWorkEntryRequestDTO;
import praksa.zadatak.dto.request.UpdateWorkEntryRequestDTO;
import praksa.zadatak.dto.WorkEntryDTO;
import praksa.zadatak.dto.response.EmployeeWorkEntriesResponseDTO;
import praksa.zadatak.dto.response.ProjectWorkEntriesResponseDTO;
import praksa.zadatak.exception.ResourceNotFoundException;
import praksa.zadatak.mapper.WorkEntryMapper;
import praksa.zadatak.model.AssignmentId;
import praksa.zadatak.model.WorkEntry;
import praksa.zadatak.model.WorkEntryId;
import praksa.zadatak.repository.WorkEntryRepository;
import praksa.zadatak.service.AssignmentService;
import praksa.zadatak.service.EmployeeService;
import praksa.zadatak.service.ProjectService;
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
    private final EmployeeService employeeService;
    private final ProjectService projectService;

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

    public Page<WorkEntryDTO> getByYearMonth(YearMonth yearMonth, Integer page, Integer size) {
        Page<WorkEntry> workEntries = workEntryRepository.findByYearMonth(
                yearMonth.toString(),
                PageRequest.of(page, size)
        );
        return workEntries.map(workEntryMapper::toDTO);
    }

    public EmployeeWorkEntriesResponseDTO getByEmployee(
            Long employeeId, YearMonth yearMonth, Integer page, Integer size) {
        EmployeeDTO employee = employeeService.getDTO(employeeId);

        Pageable pageable = PageRequest.of(page, size);
        Page<WorkEntry> workEntries = (yearMonth == null) ?
                workEntryRepository.findByAssignment_EmployeeId(
                        employeeId, pageable) :
                workEntryRepository.findByAssignment_EmployeeIdAndYearMonth(
                        employeeId, yearMonth.toString(), pageable);

        return EmployeeWorkEntriesResponseDTO.builder()
                .employee(employee)
                .workEntries(workEntries
                        .map(workEntryMapper::toWorkEntryWithoutEmployeeDTO))
                .build();
    }

    public ProjectWorkEntriesResponseDTO getByProject(
            Long projectId, YearMonth yearMonth, Integer page, Integer size) {
        ProjectDTO project = projectService.get(projectId);

        Pageable pageable = PageRequest.of(page, size);
        Page<WorkEntry> workEntries = (yearMonth == null) ?
                workEntryRepository.findByAssignment_ProjectId(
                        projectId, pageable) :
                workEntryRepository.findByAssignment_ProjectIdAndYearMonth(
                        projectId, yearMonth.toString(), pageable);

        return ProjectWorkEntriesResponseDTO.builder()
                .project(project)
                .workEntries(workEntries
                        .map(workEntryMapper::toWorkEntryWithoutProjectDTO))
                .build();
    }
}
