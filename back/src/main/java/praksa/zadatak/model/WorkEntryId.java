package praksa.zadatak.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkEntryId implements Serializable {
    private AssignmentId assignment;
    private String yearMonth;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkEntryId that = (WorkEntryId) o;
        return Objects.equals(assignment, that.assignment) &&
                Objects.equals(yearMonth, that.yearMonth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignment, yearMonth);
    }
}