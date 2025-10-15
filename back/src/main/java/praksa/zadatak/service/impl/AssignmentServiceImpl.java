package praksa.zadatak.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.AssignmentDTO;
import praksa.zadatak.dto.CreateAssignmentRequestDTO;
import praksa.zadatak.exception.InvalidDateRangeException;
import praksa.zadatak.exception.InvalidRequestException;
import praksa.zadatak.exception.ResourceNotFoundException;
import praksa.zadatak.mapper.AssignmentMapper;
import praksa.zadatak.model.Assignment;
import praksa.zadatak.model.AssignmentId;
import praksa.zadatak.model.Employee;
import praksa.zadatak.model.Project;
import praksa.zadatak.repository.AssignmentRepository;
import praksa.zadatak.repository.EmployeeRepository;
import praksa.zadatak.repository.ProjectRepository;
import praksa.zadatak.service.AssignmentService;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    public AssignmentDTO create(CreateAssignmentRequestDTO request) {
        try {
            Long projectId = request.getProjectId();
            Long employeeId = request.getEmployeeId();
            ensureProjectAndEmployeeExist(projectId, employeeId);

            Project project = projectRepository.getReferenceById(projectId);
            Employee employee = employeeRepository.getReferenceById(employeeId);

            Assignment assignment = new Assignment(
                    employee, project, request.getHourRate(), true);
            assignment = assignmentRepository.save(assignment);
            return assignmentMapper.toDTO(assignment);
        } catch (EntityNotFoundException ex) {
            throw new InvalidRequestException();
        }
    }

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

    private void ensureProjectAndEmployeeExist(Long projectId, Long employeeId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }

        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee", "id", employeeId);
        }
    }
}
