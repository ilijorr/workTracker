package praksa.zadatak.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateVacationRequestDTO {
    @NotNull
    @Future
    private Date startDate;

    @NotNull
    @Future
    private Date endDate;
}