package praksa.zadatak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.CreateEmployeeRequestDTO;
import praksa.zadatak.dto.EmployeeDTO;
import praksa.zadatak.service.EmployeeService;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    public EmployeeDTO create(CreateEmployeeRequestDTO request) {
        return null;
    }
}
