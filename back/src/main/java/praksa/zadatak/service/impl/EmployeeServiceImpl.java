package praksa.zadatak.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.request.CreateEmployeeRequestDTO;
import praksa.zadatak.dto.EmployeeDTO;
import praksa.zadatak.enums.UserRole;
import praksa.zadatak.exception.InvalidRequestException;
import praksa.zadatak.exception.ResourceNotFoundException;
import praksa.zadatak.exception.UsernameTakenException;
import praksa.zadatak.mapper.EmployeeMapper;
import praksa.zadatak.model.Employee;
import praksa.zadatak.repository.EmployeeRepository;
import praksa.zadatak.service.BaseUserService;
import praksa.zadatak.service.EmployeeService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    private final BaseUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public EmployeeDTO create(CreateEmployeeRequestDTO request) {
        if (userService.existsByUsername(request.getUsername())) {
            throw new UsernameTakenException(request.getUsername());
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(hashedPassword);

        Employee employee = employeeMapper.toEntity(request);
        employee.setRole(UserRole.ROLE_EMPLOYEE);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDTO(employee);
    }

    public Employee getReference(Long id) {
        return employeeRepository.getReferenceById(id);
    }

    public Optional<Employee> get(Long id) {
        return employeeRepository.findById(id);
    }

    @Transactional
    public void setVacationDays(Long id, Integer days) {
        if (days < 0) { throw new InvalidRequestException("Number of vacation days must be non-negative"); }
        try {
            Employee employee = employeeRepository.getReferenceById(id);
            employee.setVacationDays(days);
            employeeRepository.save(employee);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Employee", "id", id);
        }
    }

}
