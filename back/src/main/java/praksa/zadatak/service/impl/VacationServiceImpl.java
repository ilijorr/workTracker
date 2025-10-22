package praksa.zadatak.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.request.CreateVacationRequestDTO;
import praksa.zadatak.dto.VacationDTO;
import praksa.zadatak.enums.VacationStatus;
import praksa.zadatak.exception.InvalidDateRangeException;
import praksa.zadatak.exception.NotEnoughVacationDaysException;
import praksa.zadatak.exception.NotVacationAuthorized;
import praksa.zadatak.exception.ResourceNotFoundException;
import praksa.zadatak.mapper.VacationMapper;
import praksa.zadatak.model.Employee;
import praksa.zadatak.model.Vacation;
import praksa.zadatak.repository.VacationRepository;
import praksa.zadatak.service.EmployeeService;
import praksa.zadatak.service.VacationService;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {
    private static final Logger log = LogManager.getLogger(VacationServiceImpl.class);
    private final VacationRepository vacationRepository;
    private final VacationMapper vacationMapper;

    private final EmployeeService employeeService;

    @Transactional
    public VacationDTO create(Long employeeId, CreateVacationRequestDTO request) {
        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();
        int vacationLengthDays = getVacationLengthDays(startDate, endDate);
        if (vacationLengthDays <= 0) { throw new InvalidDateRangeException(); }

        try {
            Employee employee = employeeService.getReference(employeeId);

            if (employee.getVacationDays() < vacationLengthDays) {
                log.warn("Employee has {} vacation days but needs {}",
                        employee.getVacationDays(), vacationLengthDays);
                throw new NotEnoughVacationDaysException();
            }

            Vacation vacation = vacationMapper.toEntity(request);
            vacation.setEmployee(employee);
            vacation.setStatus(VacationStatus.PENDING);

            vacation = vacationRepository.save(vacation);

            return vacationMapper.toDTO(vacation);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Employee", "id", employeeId);
        }
    }

    @Transactional
    public VacationDTO changeStatus(Long id, VacationStatus status) {
        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacation", "id", id));
        if (vacation.getStatus() != VacationStatus.PENDING) {
            throw new IllegalStateException("Can only change the status of pending vacations");
        }
        if (status == VacationStatus.APPROVED) {
            int vacationLength = getVacationLengthDays(vacation.getStartDate(), vacation.getEndDate());
            employeeService.deductVacationDays(vacation.getEmployee(), vacationLength);
        }
        vacation.setStatus(status);
        vacation = vacationRepository.save(vacation);
        return vacationMapper.toDTO(vacation);
    }

    public Page<VacationDTO> getAll(Integer page, Integer size) {
        Page<Vacation> vacations = vacationRepository.findAll(
                PageRequest.of(page, size, Sort.by("startDate").descending())
        );
        return vacations.map(vacationMapper::toDTO);
    }

    public Page<VacationDTO> getByStatus(VacationStatus status, Integer page, Integer size) {
        Page<Vacation> vacations = vacationRepository.findByStatus(
                status,
                PageRequest.of(page, size, Sort.by("startDate").descending())
        );
        return vacations.map(vacationMapper::toDTO);
    }

    public Page<VacationDTO> getByEmployee(Long employeeId, Integer page, Integer size) {
        Page<Vacation> vacations = vacationRepository.findByEmployeeId(
                employeeId,
                PageRequest.of(page, size, Sort.by("startDate").descending())
        );
        return vacations.map(vacationMapper::toDTO);
    }

    @Transactional
    public void delete(Long vacationId, Long employeeId) {
        int deletedRows = vacationRepository.deleteByIdAndEmployeeId(vacationId, employeeId);
        if (deletedRows != 1) {
            log.error("Number of deleted rows: {}", deletedRows);
            throw new NotVacationAuthorized();
        }
    }

    private int getVacationLengthDays(Date start, Date end) {
        return (int) ChronoUnit.DAYS.between(
                start.toInstant(), end.toInstant()
        );
    }
}
