package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praksa.zadatak.dto.CreateVacationRequestDTO;
import praksa.zadatak.dto.VacationDTO;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vacation")
public class VacationController {

    @PostMapping
    public ResponseEntity<VacationDTO> create(
            @RequestBody CreateVacationRequestDTO request
            ) {
        return null;
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<VacationDTO> approve(
            @PathVariable("id") Integer id
    ) {
        return null;
    }

    @PatchMapping("/decline/{id}")
    public ResponseEntity<VacationDTO> decline(
            @PathVariable("id") Integer id
    ) {
        return null;
    }
}
