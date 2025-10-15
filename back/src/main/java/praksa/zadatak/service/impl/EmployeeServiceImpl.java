package praksa.zadatak.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.CreateEmployeeRequestDTO;
import praksa.zadatak.dto.EmployeeDTO;
import praksa.zadatak.mapper.EmployeeMapper;
import praksa.zadatak.model.Employee;
import praksa.zadatak.repository.UserRepository;
import praksa.zadatak.service.EmployeeService;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;
    private final UserRepository userRepository;

    @Transactional
    public EmployeeDTO create(CreateEmployeeRequestDTO request) {
        Employee employee = employeeMapper.toEntity(request);
        employee = userRepository.save(employee);
        return employeeMapper.toDTO(employee);
    }
}
