package praksa.zadatak.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.CreateEmployeeRequestDTO;
import praksa.zadatak.dto.EmployeeDTO;
import praksa.zadatak.mapper.EmployeeMapper;
import praksa.zadatak.model.Employee;
import praksa.zadatak.repository.EmployeeRepository;
import praksa.zadatak.service.EmployeeService;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public EmployeeDTO create(CreateEmployeeRequestDTO request) {
        Employee employee = employeeMapper.toEntity(request);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDTO(employee);
    }

    public Employee getReference(Long id) {
        return employeeRepository.getReferenceById(id);
    }
}
