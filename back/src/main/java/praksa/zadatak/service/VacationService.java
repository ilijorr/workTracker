package praksa.zadatak.service;

import org.springframework.data.domain.Page;
import praksa.zadatak.dto.CreateVacationRequestDTO;
import praksa.zadatak.dto.VacationDTO;
import praksa.zadatak.enums.VacationStatus;

import java.util.List;

public interface VacationService {
    VacationDTO create(CreateVacationRequestDTO request);
    VacationDTO changeStatus(Long id, VacationStatus status);

    /*
    Page<VacationDTO> getAll(Integer page, Integer size);
    List<VacationDTO> getFutureRequests();
    */

    Page<VacationDTO> getByStatus(VacationStatus status, Integer page, Integer size);
}
