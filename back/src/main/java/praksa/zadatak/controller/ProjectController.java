package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praksa.zadatak.dto.CreateProjectRequestDTO;
import praksa.zadatak.dto.ProjectDTO;
import praksa.zadatak.service.ProjectService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/project")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDTO> create(
            @RequestBody CreateProjectRequestDTO request
            ) {
        return ResponseEntity.ok(
                projectService.create(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> get() {
        return ResponseEntity.ok(
                projectService.get()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> get(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(
                projectService.get(id)
        );
    }
}
