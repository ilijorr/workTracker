package praksa.zadatak.service;

import praksa.zadatak.dto.CreateVacationRequestDTO;
import praksa.zadatak.dto.VacationDTO;
import praksa.zadatak.enums.VacationStatus;

import java.util.List;

public interface VacationService {
    VacationDTO create(CreateVacationRequestDTO request);
    VacationDTO changeStatus(Long id, VacationStatus status);

    List<VacationDTO> getAll();
    List<VacationDTO> getFutureRequests();
}
