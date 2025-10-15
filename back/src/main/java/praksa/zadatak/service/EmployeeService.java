package praksa.zadatak.service;

import praksa.zadatak.dto.CreateEmployeeRequestDTO;
import praksa.zadatak.dto.EmployeeDTO;

public interface EmployeeService {
    EmployeeDTO create(CreateEmployeeRequestDTO request);
}
