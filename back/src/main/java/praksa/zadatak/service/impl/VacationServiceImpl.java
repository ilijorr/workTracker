package praksa.zadatak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import praksa.zadatak.dto.CreateVacationRequestDTO;
import praksa.zadatak.dto.VacationDTO;
import praksa.zadatak.enums.VacationStatus;
import praksa.zadatak.service.VacationService;

@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {
    public VacationDTO create(CreateVacationRequestDTO request) {
        return null;
    }

    public VacationDTO changeStatus(Long id, VacationStatus status) {
        return null;
    }
}
