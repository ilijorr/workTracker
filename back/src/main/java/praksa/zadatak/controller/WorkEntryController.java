package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @DeleteMapping("/project/{projectId}/year-month/{yearMonth}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal String employeeId,
            @PathVariable Long projectId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth
            ) {
        workEntryService.delete(Long.parseLong(employeeId), projectId, yearMonth);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/year-month/{yearMonth}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<WorkEntryDTO>> getByYearMonth(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size
    ) {
        return ResponseEntity.ok(
                workEntryService.getByYearMonth(yearMonth, page, size)
        );
    }

    // TODO: change the response to only send the employee/project data once, and exclude it from the rest of assignments

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<WorkEntryDTO>> getByMonthForEmployee(
            @PathVariable Long employeeId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth
    ) {
        return ResponseEntity.ok(
                workEntryService.getByEmployee(employeeId, yearMonth)
        );
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<WorkEntryDTO>> getByMonthForProject(
            @PathVariable Long projectId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth
    ) {
        return ResponseEntity.ok(
                workEntryService.getByProject(projectId, yearMonth)
        );
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<WorkEntryDTO>> getMyWorkEntries(
            @AuthenticationPrincipal String employeeId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth
    ) {
        return ResponseEntity.ok(
                workEntryService.getByEmployee(Long.parseLong(employeeId), yearMonth)
        );
    }

}
