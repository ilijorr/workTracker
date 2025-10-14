package praksa.zadatak.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(WorkEntryId.class)
public class WorkEntry {
    @Id
    @ManyToOne(optional = false)
    private Assignment assignment;

    @Id
    @NotNull
    private YearMonth yearMonth;

    @Positive
    @NotNull
    private Integer hourCount;
}