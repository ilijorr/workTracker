package praksa.zadatak.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import praksa.zadatak.model.WorkEntry;
import praksa.zadatak.model.WorkEntryId;

import java.util.List;

public interface WorkEntryRepository extends JpaRepository<WorkEntry, WorkEntryId> {
    List<WorkEntry> findByYearMonth(String yearMonth);
    Page<WorkEntry> findByYearMonth(String yearMonth, Pageable pageable);
    List<WorkEntry> findByAssignment_EmployeeId(Long employeeId);
    List<WorkEntry> findByAssignment_EmployeeIdAndYearMonth(Long employeeId, String yearMonth);
    List<WorkEntry> findByAssignment_ProjectId(Long projectId);
    List<WorkEntry> findByAssignment_ProjectIdAndYearMonth(Long projectId, String yearMonth);
}
