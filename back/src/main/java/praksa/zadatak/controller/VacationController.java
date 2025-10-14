package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praksa.zadatak.dto.CreateVacationRequestDTO;
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
}
