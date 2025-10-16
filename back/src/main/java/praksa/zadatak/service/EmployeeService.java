package praksa.zadatak.service;

import praksa.zadatak.dto.CreateEmployeeRequestDTO;
import praksa.zadatak.dto.EmployeeDTO;
import praksa.zadatak.model.Employee;

public interface EmployeeService {
    EmployeeDTO create(CreateEmployeeRequestDTO request);
    Employee getReference(Long id);
}
