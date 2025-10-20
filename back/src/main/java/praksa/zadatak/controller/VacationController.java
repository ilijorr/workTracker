package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praksa.zadatak.dto.request.CreateVacationRequestDTO;
import praksa.zadatak.dto.VacationDTO;
import praksa.zadatak.enums.VacationStatus;
import praksa.zadatak.service.VacationService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vacation")
public class VacationController {
    private final VacationService vacationService;

    @PostMapping
    public ResponseEntity<VacationDTO> create(
            @RequestBody CreateVacationRequestDTO request
            ) {
        return ResponseEntity.ok(
                vacationService.create(request)
        );
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<VacationDTO> approve(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(
                vacationService.changeStatus(id, VacationStatus.APPROVED)
        );
    }

    @PatchMapping("/decline/{id}")
    public ResponseEntity<VacationDTO> decline(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(
                vacationService.changeStatus(id, VacationStatus.DECLINED)
        );
    }

    @GetMapping
    public ResponseEntity<Page<VacationDTO>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size
    ) {
        return ResponseEntity.ok(
                vacationService.getAll(page, size)
        );
    }

    @GetMapping("/pending")
    public ResponseEntity<Page<VacationDTO>> getPending(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size
    ) {
        return ResponseEntity.ok(
                vacationService.getByStatus(VacationStatus.PENDING, page, size)
        );
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Page<VacationDTO>> getByEmployee(
            @PathVariable Long employeeId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer size
    ) {
        return ResponseEntity.ok(
                vacationService.getByEmployee(employeeId, page, size)
        );
    }

    // TODO: brisanje

}
