package praksa.zadatak.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import praksa.zadatak.enums.VacationStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Employee employee;

    @NotNull
    private VacationStatus status;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;
}
