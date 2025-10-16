package praksa.zadatak.service;

import org.springframework.data.domain.Page;
import praksa.zadatak.dto.CreateVacationRequestDTO;
import praksa.zadatak.dto.VacationDTO;
import praksa.zadatak.enums.VacationStatus;

public interface VacationService {
    VacationDTO create(CreateVacationRequestDTO request);
    VacationDTO changeStatus(Long id, VacationStatus status);

    Page<VacationDTO> getAll(Integer page, Integer size);

    Page<VacationDTO> getByStatus(VacationStatus status, Integer page, Integer size);
    Page<VacationDTO> getByEmployee(Long employeeId, Integer page, Integer size);
}
