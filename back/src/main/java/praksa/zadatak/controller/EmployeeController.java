package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import praksa.zadatak.dto.request.CreateEmployeeRequestDTO;
import praksa.zadatak.dto.EmployeeDTO;
import praksa.zadatak.dto.request.SingleFieldRequestDTO;
import praksa.zadatak.service.EmployeeService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeDTO> create(
            @RequestBody CreateEmployeeRequestDTO request
            ) {
        return ResponseEntity.ok(
                employeeService.create(request)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/vacation-days")
    public ResponseEntity<Void> setVacationDays(
            @PathVariable Long id,
            @RequestBody SingleFieldRequestDTO<Integer> request
    ) {
        employeeService.setVacationDays(id, request.getValue());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size
    ) {
        return ResponseEntity.ok(
                employeeService.getAll(page, size)
        );
    }
}
