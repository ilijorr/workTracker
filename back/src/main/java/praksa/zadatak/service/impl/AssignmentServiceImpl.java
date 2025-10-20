package praksa.zadatak.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.AssignmentDTO;
import praksa.zadatak.dto.request.CreateAssignmentRequestDTO;
import praksa.zadatak.dto.response.EmployeeAssignmentsResponseDTO;
import praksa.zadatak.dto.response.ProjectAssignmentsResponseDTO;
import praksa.zadatak.exception.InvalidRequestException;
import praksa.zadatak.exception.ResourceNotFoundException;
import praksa.zadatak.mapper.AssignmentMapper;
import praksa.zadatak.mapper.EmployeeMapper;
import praksa.zadatak.mapper.ProjectMapper;
import praksa.zadatak.model.Assignment;
import praksa.zadatak.model.AssignmentId;
import praksa.zadatak.model.Employee;
import praksa.zadatak.model.Project;
import praksa.zadatak.repository.AssignmentRepository;
import praksa.zadatak.service.AssignmentService;
import praksa.zadatak.service.EmployeeService;
import praksa.zadatak.service.ProjectService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;

    @Transactional
    public AssignmentDTO assign(CreateAssignmentRequestDTO request) {
        try {
            Assignment assignment = new Assignment(
                    employeeService.getReference(request.getEmployeeId()),
                    projectService.getReference(request.getProjectId()),
                    request.getHourRate(),
                    true
            );
            assignment = assignmentRepository.save(assignment);
            return assignmentMapper.toDTO(assignment);
        } catch (EntityNotFoundException ex) {
            throw new InvalidRequestException();
        }
    }

    @Transactional
    public Boolean unassign(Long employeeId, Long projectId) {
        AssignmentId id = new AssignmentId(employeeId, projectId);
        try {
            Assignment assignment = assignmentRepository.getReferenceById(id);
            assignment.setIsActive(false);
            assignmentRepository.save(assignment);
            return Boolean.TRUE;
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Assignment", "id", id);
        }
    }

    public Assignment getReference(AssignmentId id) {
        return assignmentRepository.getReferenceById(id);
    }

    public Assignment getReference(Long employeeId, Long projectId) {
        AssignmentId id = new AssignmentId(employeeId, projectId);
        return this.getReference(id);
    }

    public ProjectAssignmentsResponseDTO getAllActiveAssignmentsForProject(Long projectId) {
        List<Assignment> assignments = assignmentRepository.findByProjectIdAndIsActiveTrue(projectId);
        if (assignments == null || assignments.isEmpty()) { return null; }
        Project project = projectService.getReference(projectId);
        return ProjectAssignmentsResponseDTO.builder()
                .projectDTO(projectMapper.toDTO(project))
                .assignmentDTOs(assignmentMapper.toAssignmentWithoutProjectDTOs(assignments))
                .build();
    }

    public EmployeeAssignmentsResponseDTO getAllActiveAssignmentsForEmployee(Long employeeId) {
        List<Assignment> assignments = assignmentRepository.findByEmployeeIdAndIsActiveTrue(employeeId);
        if (assignments == null || assignments.isEmpty()) { return null; }
        Employee employee = employeeService.get(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("employee", "id", employeeId));
        return EmployeeAssignmentsResponseDTO.builder()
                .employeeDTO(employeeMapper.toDTO(employee))
                .assignmentDTOS(assignmentMapper.toAssignmentWithoutEmployeeDTOs(assignments))
                .build();
    }

}
