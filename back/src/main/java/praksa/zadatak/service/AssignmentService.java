package praksa.zadatak.service;

import praksa.zadatak.dto.AssignmentDTO;
import praksa.zadatak.dto.request.CreateAssignmentRequestDTO;
import praksa.zadatak.dto.response.EmployeeAssignmentsResponseDTO;
import praksa.zadatak.model.Assignment;
import praksa.zadatak.model.AssignmentId;

import java.util.List;

public interface AssignmentService {
    AssignmentDTO assign(CreateAssignmentRequestDTO request);
    Boolean unassign(Long employeeId, Long projectId);

    Assignment getReference(AssignmentId id);
    Assignment getReference(Long employeeId, Long projectId);

    List<AssignmentDTO> getAllActiveAssignmentsForProject(Long projectId);
    EmployeeAssignmentsResponseDTO getAllActiveAssignmentsForEmployee(Long employeeId);
}
