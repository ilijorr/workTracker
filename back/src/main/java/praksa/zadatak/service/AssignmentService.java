package praksa.zadatak.service;

import praksa.zadatak.dto.AssignmentDTO;
import praksa.zadatak.dto.CreateAssignmentRequestDTO;

public interface AssignmentService {
    public AssignmentDTO create(CreateAssignmentRequestDTO request);
}
