package praksa.zadatak.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import praksa.zadatak.model.Assignment;
import praksa.zadatak.model.AssignmentId;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, AssignmentId> {
    List<Assignment> findByEmployeeIdAndIsActiveTrue(Long employeeId);
    Page<Assignment> findByEmployeeIdAndIsActiveTrue(Long employeeId, Pageable pageable);
    List<Assignment> findByProjectIdAndIsActiveTrue(Long projectId);
    Page<Assignment> findByProjectIdAndIsActiveTrue(Long projectId, Pageable pageable);
}
