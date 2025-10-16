package praksa.zadatak.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import praksa.zadatak.enums.VacationStatus;
import praksa.zadatak.model.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    Page<Vacation> findByStatus(VacationStatus status, Pageable pageable);
    Page<Vacation> findByEmployeeId(Long employeeId, Pageable pageable);
}
