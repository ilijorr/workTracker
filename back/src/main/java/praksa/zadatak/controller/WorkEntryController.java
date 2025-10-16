package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praksa.zadatak.dto.CreateWorkEntryRequestDTO;
import praksa.zadatak.dto.UpdateWorkEntryRequestDTO;
import praksa.zadatak.dto.WorkEntryDTO;
import praksa.zadatak.service.WorkEntryService;

import java.time.YearMonth;

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

    @DeleteMapping("/employee/{employeeId}/project/{projectId}/year-month/{yearMonth}")
    public ResponseEntity<Void> delete(
            @PathVariable Long employeeId,
            @PathVariable Long projectId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth
            ) {
        workEntryService.delete(employeeId, projectId, yearMonth);
        return ResponseEntity.noContent().build();
    }
}
