package praksa.zadatak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import praksa.zadatak.dto.CreateEmployeeRequestDTO;
import praksa.zadatak.dto.EmployeeDTO;
import praksa.zadatak.service.EmployeeService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping()
    public ResponseEntity<EmployeeDTO> create(
            @RequestBody CreateEmployeeRequestDTO request
            ) {
        return ResponseEntity.ok(
                employeeService.create(request)
        );
    }
}
