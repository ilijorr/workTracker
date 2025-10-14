package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
                assignmentService.create(request)
        );
    }
}
