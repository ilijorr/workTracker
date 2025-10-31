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
    public ResponseEntity<AssignmentDTO> assign(
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
            @PathVariable Long employeeId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size
    ) {
        return ResponseEntity.ok(
                assignmentService.getAllActiveAssignmentsForEmployee(employeeId, page, size)
        );
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<EmployeeAssignmentsResponseDTO> getMyAssignments(
            @AuthenticationPrincipal String employeeId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size
    ) {
        return ResponseEntity.ok(
                assignmentService.getAllActiveAssignmentsForEmployee(Long.parseLong(employeeId), page, size)
        );
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectAssignmentsResponseDTO> getProjectAssignments(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size
    ) {
        return ResponseEntity.ok(
                assignmentService.getAllActiveAssignmentsForProject(projectId, page, size)
        );
    }

}
