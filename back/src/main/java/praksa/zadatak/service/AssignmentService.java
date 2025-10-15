package praksa.zadatak.service;

import praksa.zadatak.dto.AssignmentDTO;
import praksa.zadatak.dto.CreateAssignmentRequestDTO;

public interface AssignmentService {
    AssignmentDTO create(CreateAssignmentRequestDTO request);
    Boolean unassign(Long employeeId, Long projectId);
}
