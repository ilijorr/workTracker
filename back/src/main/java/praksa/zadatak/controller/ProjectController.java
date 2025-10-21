package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import praksa.zadatak.dto.request.CreateProjectRequestDTO;
import praksa.zadatak.dto.ProjectDTO;
import praksa.zadatak.service.ProjectService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/project")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDTO> create(
            @RequestBody CreateProjectRequestDTO request
            ) {
        return ResponseEntity.ok(
                projectService.create(request)
        );
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ProjectDTO>> get() {
        return ResponseEntity.ok(
                projectService.get()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProjectDTO> get(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(
                projectService.get(id)
        );
    }
}
