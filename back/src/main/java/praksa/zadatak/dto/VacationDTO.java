package praksa.zadatak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import praksa.zadatak.enums.VacationStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacationDTO {
    private Long id;
    private EmployeeDTO employee;
    private VacationStatus status;
    private Date startDate;
    private Date endDate;
}