package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praksa.zadatak.dto.CreateProjectRequestDTO;
import praksa.zadatak.dto.ProjectDTO;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @PostMapping
    public ResponseEntity<ProjectDTO> create(
            @RequestBody CreateProjectRequestDTO request
            ) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> get() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> get(
            @PathVariable("id") Integer id
    ) {
        return null;
    }
}
