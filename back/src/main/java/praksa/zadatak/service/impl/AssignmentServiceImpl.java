package praksa.zadatak.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.AssignmentDTO;
import praksa.zadatak.dto.CreateAssignmentRequestDTO;
import praksa.zadatak.exception.InvalidRequestException;
import praksa.zadatak.exception.ResourceNotFoundException;
import praksa.zadatak.mapper.AssignmentMapper;
import praksa.zadatak.model.Assignment;
import praksa.zadatak.model.AssignmentId;
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

    public List<AssignmentDTO> getAllActiveAssignmentsForProject(Long projectId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    public List<AssignmentDTO> getAllActiveAssignmentsForEmployee(Long employeeId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

}
