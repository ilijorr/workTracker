package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import praksa.zadatak.dto.CreateWorkEntryRequestDTO;
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
        return null;
    }
}
