package praksa.zadatak.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private final Integer DEFAULT_VACATION_DAYS = 30;

    @Transactional
    public EmployeeDTO create(CreateEmployeeRequestDTO request) {
        if (userService.existsByUsername(request.getUsername())) {
            throw new UsernameTakenException(request.getUsername());
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(hashedPassword);

        Employee employee = employeeMapper.toEntity(request);
        employee.setRole(UserRole.ROLE_EMPLOYEE);
        employee.setVacationDays(DEFAULT_VACATION_DAYS);
        employee = employeeRepository.save(employee);
        //TODO: return DTO vacationDays is always 0, even tho its 30 in the db
        return employeeMapper.toDTO(employee);
    }

    public Employee getReference(Long id) {
        return employeeRepository.getReferenceById(id);
    }

    public Optional<Employee> get(Long id) {
        return employeeRepository.findById(id);
    }

    public Page<EmployeeDTO> getAll(Integer page, Integer size) {
        Page<Employee> employees = employeeRepository.findAll(
                PageRequest.of(page, size)
        );
        return employees.map(employeeMapper::toDTO);
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
