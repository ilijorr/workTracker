package praksa.zadatak.mapper;

import org.mapstruct.Mapper;
import praksa.zadatak.dto.request.CreateEmployeeRequestDTO;
import praksa.zadatak.dto.EmployeeDTO;
import praksa.zadatak.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDTO toDTO(Employee employee);

    Employee toEntity(EmployeeDTO employeeDTO);
    Employee toEntity(CreateEmployeeRequestDTO dto);
}