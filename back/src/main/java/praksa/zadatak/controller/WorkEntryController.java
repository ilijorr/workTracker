package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import praksa.zadatak.dto.request.CreateWorkEntryRequestDTO;
import praksa.zadatak.dto.request.UpdateWorkEntryRequestDTO;
import praksa.zadatak.dto.WorkEntryDTO;
import praksa.zadatak.service.WorkEntryService;

import java.time.YearMonth;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/work")
public class WorkEntryController {
    private final WorkEntryService workEntryService;

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<WorkEntryDTO> create(
            @RequestBody CreateWorkEntryRequestDTO request,
            @AuthenticationPrincipal String employeeId
            ) {
        return ResponseEntity.ok(
                workEntryService.create(request, Long.parseLong(employeeId))
        );
    }

    @PatchMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<WorkEntryDTO> update(
            @RequestBody UpdateWorkEntryRequestDTO request,
            @AuthenticationPrincipal String employeeId
            ) {
        return ResponseEntity.ok(
                workEntryService.update(request, Long.parseLong(employeeId))
        );
    }

    @DeleteMapping("/employee/{employeeId}/project/{projectId}/year-month/{yearMonth}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> delete(
            @PathVariable Long employeeId,
            @PathVariable Long projectId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth
            ) {
        workEntryService.delete(employeeId, projectId, yearMonth);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/year-month/{yearMonth}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<WorkEntryDTO>> getByYearMonth(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth
    ) {
        return ResponseEntity.ok(
                workEntryService.getByYearMonth(yearMonth)
        );
    }

    // TODO: refactor 2 endpoints underneath to be getByEmployee/getByProject, with an optional dateMonth query

    @GetMapping("/year-month/{yearMonth}/employee/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<WorkEntryDTO>> getByMonthForEmployee(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
            @PathVariable Long employeeId
    ) {
        return ResponseEntity.ok(
                workEntryService.getByMonthForEmployee(yearMonth, employeeId)
        );
    }

    @GetMapping("/year-month/{yearMonth}/project/{projectId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<WorkEntryDTO>> getByMonthForProject(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
            @PathVariable Long projectId
    ) {
        return ResponseEntity.ok(
                workEntryService.getByMonthForProject(yearMonth, projectId)
        );
    }

}
