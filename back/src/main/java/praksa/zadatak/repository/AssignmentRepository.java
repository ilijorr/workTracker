package praksa.zadatak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import praksa.zadatak.model.Assignment;
import praksa.zadatak.model.AssignmentId;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, AssignmentId> {
    List<Assignment> findByEmployeeIdAndIsActiveTrue(Long employeeId);
}
