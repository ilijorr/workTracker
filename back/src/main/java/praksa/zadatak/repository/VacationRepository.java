package praksa.zadatak.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import praksa.zadatak.enums.VacationStatus;
import praksa.zadatak.model.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    Page<Vacation> findByStatus(VacationStatus status, Pageable pageable);
    Page<Vacation> findByEmployeeId(Long employeeId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Vacation v WHERE v.id = :vacationId AND v.employee.id = :employeeId")
    int deleteByIdAndEmployeeId(@Param("vacationId") Long vacationId, @Param("employeeId") Long employeeId);
}
