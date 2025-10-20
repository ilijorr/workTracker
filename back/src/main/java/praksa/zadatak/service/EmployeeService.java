package praksa.zadatak.service;

import praksa.zadatak.dto.request.CreateEmployeeRequestDTO;
import praksa.zadatak.dto.EmployeeDTO;
import praksa.zadatak.model.Employee;

import java.util.Optional;

public interface EmployeeService {
    EmployeeDTO create(CreateEmployeeRequestDTO request);
    Employee getReference(Long id);
    Optional<Employee> get(Long id);
}
