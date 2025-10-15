package praksa.zadatak.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(AssignmentId.class)
public class Assignment {
    @Id
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(nullable = false)
    private Employee employee;

    @Id
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(nullable = false)
    private Project project;

    @Column(nullable = false)
    @Positive
    private float hourRate;

    @Column(nullable = false)
    private Boolean isActive;
}
