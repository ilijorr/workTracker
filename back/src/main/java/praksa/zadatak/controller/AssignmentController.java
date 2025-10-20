package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praksa.zadatak.dto.AssignmentDTO;
import praksa.zadatak.dto.request.CreateAssignmentRequestDTO;
import praksa.zadatak.dto.response.EmployeeAssignmentsResponseDTO;
import praksa.zadatak.service.AssignmentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<AssignmentDTO> create(
            @RequestBody CreateAssignmentRequestDTO request
            ) {
        return ResponseEntity.ok(
                assignmentService.assign(request)
        );
    }

    @PatchMapping("/{employeeId}/{projectId}")
    public ResponseEntity<Boolean> unassign(
            @PathVariable Long employeeId,
            @PathVariable Long projectId
    ) {
        return ResponseEntity.ok(assignmentService.unassign(employeeId, projectId));
    }

    /** TODO: the response doesn't really need every assignment to have the employee info
              maybe create a new response which looks something like
        {
            employee: {employee data here}
            assignments: [{proj1, hourRate1}, {proj2, hourRate2}, {proj3, hourRate3}]
        }
    **/
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeAssignmentsResponseDTO> getEmployeeAssignments(
            @PathVariable Long employeeId
    ) {
        return ResponseEntity.ok(
                assignmentService.getAllActiveAssignmentsForEmployee(employeeId)
        );
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<AssignmentDTO>> getProjectAssignments(
            @PathVariable Long projectId
    ) {
        return ResponseEntity.ok(
                assignmentService.getAllActiveAssignmentsForProject(projectId)
        );
    }

}
