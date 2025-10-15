package praksa.zadatak.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.CreateVacationRequestDTO;
import praksa.zadatak.dto.VacationDTO;
import praksa.zadatak.enums.VacationStatus;
import praksa.zadatak.exception.InvalidDateRangeException;
import praksa.zadatak.exception.ResourceNotFoundException;
import praksa.zadatak.mapper.VacationMapper;
import praksa.zadatak.model.Employee;
import praksa.zadatak.model.Vacation;
import praksa.zadatak.repository.EmployeeRepository;
import praksa.zadatak.repository.VacationRepository;
import praksa.zadatak.service.VacationService;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {
    private final VacationRepository vacationRepository;
    private final VacationMapper vacationMapper;

    private final EmployeeRepository employeeRepository;

    public VacationDTO create(CreateVacationRequestDTO request) {
        ensureValidRequest(request);

        Employee employee = employeeRepository.getReferenceById(request.getEmployeeId());
        Vacation vacation = vacationMapper.toEntity(request);
        vacation.setEmployee(employee);
        vacation.setStatus(VacationStatus.PENDING);

        vacation = vacationRepository.save(vacation);

        return vacationMapper.toDTO(vacation);
    }

    public VacationDTO changeStatus(Long id, VacationStatus status) {
        try {
            Vacation vacation = vacationRepository.getReferenceById(id);
            if (vacation.getStatus() != VacationStatus.PENDING) {
                throw new IllegalStateException("Can only change the status of pending vacations");
            }
            vacation.setStatus(status);
            vacation = vacationRepository.save(vacation);
            return vacationMapper.toDTO(vacation);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Vacation", "id", id);
        }
    }

    private void ensureValidRequest(CreateVacationRequestDTO request) {
        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();
        if (startDate.after(endDate)) { throw new InvalidDateRangeException(); }

        Long employeeId = request.getEmployeeId();
        if (!employeeRepository.existsById(employeeId)) { throw new ResourceNotFoundException(
                "Employee", "id", employeeId
        ); }
    }
}
