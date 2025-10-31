package praksa.zadatak.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("EMPLOYEE")
public class Employee extends BaseUser {
    @Column(nullable = false)
    private int vacationDays = 30;
}
