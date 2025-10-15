package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praksa.zadatak.dto.CreateWorkEntryRequestDTO;
import praksa.zadatak.dto.UpdateWorkEntryRequestDTO;
import praksa.zadatak.dto.WorkEntryDTO;
import praksa.zadatak.service.WorkEntryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/work")
public class WorkEntryController {
    private final WorkEntryService workEntryService;

    @PostMapping
    public ResponseEntity<WorkEntryDTO> create(
            @RequestBody CreateWorkEntryRequestDTO request
            ) {
        return ResponseEntity.ok(
                workEntryService.create(request)
        );
    }

    @PatchMapping
    public ResponseEntity<WorkEntryDTO> update(
            @RequestBody UpdateWorkEntryRequestDTO request
            ) {
        return ResponseEntity.ok(
                workEntryService.update(request)
        );
    }
}
