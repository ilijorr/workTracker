package praksa.zadatak.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import praksa.zadatak.dto.request.CreateVacationRequestDTO;
import praksa.zadatak.dto.VacationDTO;
import praksa.zadatak.model.Vacation;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface VacationMapper {

    VacationDTO toDTO(Vacation vacation);

    Vacation toEntity(VacationDTO vacationDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    Vacation toEntity(CreateVacationRequestDTO createVacationRequestDTO);
}