package praksa.zadatak.service;

import praksa.zadatak.dto.CreateVacationRequestDTO;
import praksa.zadatak.dto.VacationDTO;
import praksa.zadatak.enums.VacationStatus;

public interface VacationService {
    VacationDTO create(CreateVacationRequestDTO request);
    VacationDTO changeStatus(Long id, VacationStatus status);
}
