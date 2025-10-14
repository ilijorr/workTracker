package praksa.zadatak.service;

import praksa.zadatak.dto.CreateWorkEntryRequestDTO;
import praksa.zadatak.dto.WorkEntryDTO;

public interface WorkEntryService {
    WorkEntryDTO create(CreateWorkEntryRequestDTO request);
}
