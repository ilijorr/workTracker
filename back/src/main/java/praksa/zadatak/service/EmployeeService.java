package praksa.zadatak.service;

import org.springframework.data.domain.Page;
import praksa.zadatak.dto.request.CreateEmployeeRequestDTO;
import praksa.zadatak.dto.EmployeeDTO;
import praksa.zadatak.model.Employee;

import java.util.Optional;

public interface EmployeeService {
    EmployeeDTO create(CreateEmployeeRequestDTO request);
    Employee getReference(Long id);
    Page<EmployeeDTO> getAll(Integer page, Integer size);
    Optional<Employee> get(Long id);
    void setVacationDays(Long id, Integer days);
    void deductVacationDays(Employee employee, Integer days);
}
