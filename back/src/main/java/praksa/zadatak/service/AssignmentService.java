package praksa.zadatak.service;

import praksa.zadatak.dto.AssignmentDTO;
import praksa.zadatak.dto.CreateAssignmentRequestDTO;

import java.util.List;

public interface AssignmentService {
    AssignmentDTO assign(CreateAssignmentRequestDTO request);
    Boolean unassign(Long employeeId, Long projectId);

    List<AssignmentDTO> getAllActiveAssignmentsForProject(Long projectId);
    List<AssignmentDTO> getAllActiveAssignmentsForEmployee(Long employeeId);
}
