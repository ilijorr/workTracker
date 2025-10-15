package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praksa.zadatak.dto.AssignmentDTO;
import praksa.zadatak.dto.CreateAssignmentRequestDTO;
import praksa.zadatak.service.AssignmentService;

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

    @PatchMapping("/assignment/{employeeId}/{projectId}")
    public ResponseEntity<Boolean> unassign(
            @PathVariable Long employeeId,
            @PathVariable Long projectId
    ) {
        return ResponseEntity.ok(assignmentService.unassign(employeeId, projectId));
    }

}
