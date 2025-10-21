package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import praksa.zadatak.dto.AssignmentDTO;
import praksa.zadatak.dto.request.CreateAssignmentRequestDTO;
import praksa.zadatak.dto.response.EmployeeAssignmentsResponseDTO;
import praksa.zadatak.dto.response.ProjectAssignmentsResponseDTO;
import praksa.zadatak.service.AssignmentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AssignmentDTO> create(
            @RequestBody CreateAssignmentRequestDTO request
            ) {
        return ResponseEntity.ok(
                assignmentService.assign(request)
        );
    }

    @PatchMapping("/{employeeId}/{projectId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> unassign(
            @PathVariable Long employeeId,
            @PathVariable Long projectId
    ) {
        return ResponseEntity.ok(assignmentService.unassign(employeeId, projectId));
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeAssignmentsResponseDTO> getEmployeeAssignments(
            @PathVariable Long employeeId
    ) {
        return ResponseEntity.ok(
                assignmentService.getAllActiveAssignmentsForEmployee(employeeId)
        );
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<EmployeeAssignmentsResponseDTO> getMyAssignments(
            @AuthenticationPrincipal String employeeId
    ) {
        return ResponseEntity.ok(
                assignmentService.getAllActiveAssignmentsForEmployee(Long.parseLong(employeeId))
        );
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectAssignmentsResponseDTO> getProjectAssignments(
            @PathVariable Long projectId
    ) {
        return ResponseEntity.ok(
                assignmentService.getAllActiveAssignmentsForProject(projectId)
        );
    }

}
