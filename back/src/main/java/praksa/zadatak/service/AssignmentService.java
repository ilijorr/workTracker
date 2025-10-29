package praksa.zadatak.service;

import praksa.zadatak.dto.AssignmentDTO;
import praksa.zadatak.dto.request.CreateAssignmentRequestDTO;
import praksa.zadatak.dto.response.EmployeeAssignmentsResponseDTO;
import praksa.zadatak.dto.response.ProjectAssignmentsResponseDTO;
import praksa.zadatak.model.Assignment;
import praksa.zadatak.model.AssignmentId;

public interface AssignmentService {
    AssignmentDTO assign(CreateAssignmentRequestDTO request);
    Boolean unassign(Long employeeId, Long projectId);

    Assignment getReference(AssignmentId id);
    Assignment getReference(Long employeeId, Long projectId);

    ProjectAssignmentsResponseDTO getAllActiveAssignmentsForProject(Long projectId, Integer page, Integer size);
    EmployeeAssignmentsResponseDTO getAllActiveAssignmentsForEmployee(Long employeeId, Integer page, Integer size);
}
