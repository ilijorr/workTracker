package praksa.zadatak.service;

import praksa.zadatak.dto.AssignmentDTO;
import praksa.zadatak.dto.CreateAssignmentRequestDTO;

public interface AssignmentService {
    AssignmentDTO assign(CreateAssignmentRequestDTO request);
    Boolean unassign(Long employeeId, Long projectId);
}
